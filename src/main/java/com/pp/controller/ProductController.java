package com.pp.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pp.utils.BaseController;
import com.pp.utils.MyPage;
import com.pp.utils.UpdateRedis;
import com.pp.ws.Websocketchat;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.pp.entity.Chat;
import com.pp.entity.Product;
import com.pp.entity.Star;
import com.pp.entity.User;
import com.pp.model.Message;
import com.pp.service.ChatService;
import com.pp.service.ProductService;
import com.pp.service.StarService;

@Controller
@RequestMapping("/pp/Product")
public class ProductController extends BaseController {
	@Autowired
	private ProductService productService;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Value("${product.img}")
	private String uploadroot;
	@Autowired
	private StarService starService;
	@Autowired
	private ChatService chatService;
	private static Jedis jedis = new Jedis();
	@Cacheable(cacheNames = "cache0")
	@ResponseBody
	@RequestMapping("/getproducts")
	public Map<String, Object> getProducts(String name, String state, Integer page, String area) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		MyPage<Product> onePage = productService.getOnePage(name, state, page, area);
		map.put("page", onePage);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/getcircusee")
	public List<Product> getcircusee() {
		return productService.getcircusee();
	}
	@ResponseBody
	@RequestMapping("/getproducts2")
	Map<String, Object> getProducts(@RequestParam("page") Integer page, @RequestParam("beginTime") String beginTime,
			@RequestParam("endTime") String endTime, @RequestParam("state") String state,@RequestParam("shelf")String shelf) {
		return productService.getOnePage2(page, beginTime, endTime, state,shelf);
	}

	/**
	 * 展示具体商品
	 * 
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getdetail")
	public Product getDetail(@RequestParam("pid") Integer pid) {
		return productService.getProduct(pid);
	}

	/**
	 * 添加商品
	 * @param file1
	 * @param file2
	 * @param file3
	 * @param file4
	 * @param product
	 * @param req
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@CacheEvict(cacheNames = "cache0", allEntries = true)
	@RequestMapping("/addProduct")
	public String addProduct(HttpServletRequest req,MultipartFile[] files,Product product,String province,String city) throws IllegalStateException, IOException {
		String pname = product.getPname();
		if (pname==null||"".equals(pname))
			return "商品名称不能为空";
		if(province==null||"".equals(province))
			return "区域不能为空";
		String area = province+city;
		product.setArea(area);
		Date time = product.getTime();
		Object user = super.getSessionVal(req, "user");
		User user2 = (User) user;
		if(user2.getId()==25) {
			return "你已被剥夺添加商品的权利";
		}
		if(time==null)
			return "截止日期不能为空";
		BigDecimal price = product.getPrice();
		if (price==null||price.compareTo(new BigDecimal("0"))<1) 
			return "价格不能为空或小于0";
		BigDecimal minimumAdd = product.getMinimumAdd();
		if (minimumAdd==null||minimumAdd.compareTo(new BigDecimal("0"))<1) 
			return "最小价格不能为空或小于0";
		if(files.length!=4)
			return "需要四张照片";
		StringBuilder allImg = new StringBuilder();
		for (MultipartFile file : files){
			String img = file.getOriginalFilename();
			String suffix = ".jpg";
			if (!"".equals(img)) {
				suffix=img.substring(img.lastIndexOf("."));
			}
			img = UUID.randomUUID().toString() + suffix;
			file.transferTo(new File(uploadroot+img));
			allImg.append(img).append(",");
		}
		
		String img=allImg.substring(0, allImg.length()-1);
		if ("".equals(user)) {
			return "/error/404";
		}
		product.setState("拍卖");
		product.setOwnerid(user2.getId());
		product.setImg(img);
		product.setShelf("下架");
		int addProduct = productService.addProduct(product);
		Thread t1=new Thread(()->{
			UpdateRedis.update(province,city, pname, product.getId());
			//MailUtil.sendEmail("1350295310@qq.com", "0000","有商品待审核");
		});
		t1.start();
		return addProduct>0?"success":"failed";
	}
	@ResponseBody
	@RequestMapping("/getmyproduct")
	public List<Product> getMyProducts(HttpServletRequest req) {
		Object user = super.getSessionVal(req, "user");
		if ("".equals(user)) {
			return null;
		}
		User user2 = (User) user;
		return productService.getProductsByUid(user2.getId());
	}

	@CacheEvict(cacheNames = "cache0", allEntries = true)
	@ResponseBody
	@RequestMapping("/updateState")
	public String updateState(@RequestParam("shelf") String shelf, @RequestParam("pid") Integer pid) {
		if ("下架".equals(shelf)) {
			shelf = "上架";
		} else {
			shelf = "下架";
		}
		return productService.updateShelf(shelf, pid);
	}
	//@Scheduled(fixedDelay = 5000)
	public void sendmessagetostar() throws IOException {
		ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
		long time = new Date().getTime();
		Set<String> set = opsForZSet.rangeByScore("productcopy",time, time+1000*60*60*2);
		System.out.println(set);
		//需要发送消息的商品
		for (String pid : set) {
			synchronized (pid.intern()) {
				Integer pid1 = Integer.valueOf(pid);
				if(opsForZSet.rank("productcopy",pid)==null)
					break;
				opsForZSet.remove("productcopy", pid);
				//发送的消息
				Product product = getDetail(pid1);
				Message message = new Message();
				message.setText("您收藏的商品"+product.getPname()+"两个小时后就结束了,请抓紧时间哦");
				message.setName("小拍");
				message.setFid(product.getId());
				message.setTime(new Date());
				//所有收藏过的人
				List<Star> stars = starService.selectByPid(pid1);
				for (Star star : stars) {
					System.out.println("star"+star);
					Integer uid = star.getUid();
					message.setTid(uid);
					Chat chat=chatService.selectBtid0(uid);
					String mid = chat.getMid();
					message.setMid(mid);
					String messageString = JSON.toJSONString(message);
					Websocketchat.sendInfo(uid+"",messageString);
					jedis.select(1);
					jedis.rpush(mid,messageString);
				}
				//所有出过价的人
			}
			
		}
	}
}