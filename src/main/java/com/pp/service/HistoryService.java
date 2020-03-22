package com.pp.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pp.dao.HistoryMapper;
import com.pp.entity.Address;
import com.pp.entity.History;
import com.pp.entity.Porder;
import com.pp.entity.Product;
import com.pp.entity.User;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Transactional
@Service
public class HistoryService {
	@Autowired
	private UserService userService;
	@Autowired
	private HistoryMapper historyMapper;
	@Autowired
	private ProductService productService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private PorderService porderService;

	public HistoryMapper getHistoryMapper() {
		return historyMapper;
	}

	/**
	 * 查询历史出价记录
	 * 
	 * @param pid
	 * @return
	 */
	public List<History> getHistoryById(Integer pid) {
		Example example = new Example(History.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("pid=", pid);
		example.setOrderByClause("price desc");
		List<History> list = historyMapper.selectByExample(example);
		for (History history : list) {
			String uname = history.getUname();
			history.setUname(uname.substring(0,2)+"**");
		}
		return list;
	}

	/**
	 * 出价
	 * 
	 * @param history
	 * @return
	 */
	public String addHistory(History history) {
		Integer pid = history.getPid();
		Product product = productService.getProduct(pid);
		String state = product.getState();
		Integer ownerid = product.getOwnerid();
		 BigDecimal minimumAdd = product.getMinimumAdd();
//		先检查钱够不够
		 Integer uid = history.getUid();
		 User user = userService.selectByPrimarykey(uid);
		 BigDecimal balance = user.getBalance();
		 if (balance.compareTo(new BigDecimal("200"))<0) {
			return "余额不足";
		}
		if (history.getUid().compareTo(ownerid)==0) {
			return "不能竞拍自己的的商品";
		} 
		// 状态验证,不在拍卖状态
		if (!"拍卖".equals(state)) {
			return "您来迟了,该商品已" + state;
		}
		Date time = product.getTime();
		Date now = new Date();
		// 时间验证,不在时间内
		//System.out.println(now.compareTo(time));
		if (now.compareTo(time) > 0) {
			return "您来迟了,已超时";
			// throw new RuntimeException("时间错误");
		}
		//验证价格
		//if(history.getPrice().subtract(subtrahend))
		
		//历史最高价
		BigDecimal highprice =product.getPrice();
		List<History> list = getHistoryById(pid);
		if (list != null&&list.size()!=0 ) {
			highprice = list.get(0).getPrice();
		}
		//是最高价且
		if (history.getPrice().compareTo(highprice) < 1) {
			return "出价稍慢一步,低于最高价格,请再加价";
		}
		//小于最小加价幅度
		if (history.getPrice().compareTo(highprice.add(minimumAdd)) < 0) {
			return "加价幅度不够,请重新出价";
		}
		if("下架".equals(product.getShelf()))
			return "该商品下架了";
		
		
		
		
		
		//验证通过,可以拍了,
		history.setTime(now);
		if ((time.getTime())-now.getTime() < 300000) {
			productService.updateTime(new Date(now.getTime() + 300000), pid);
		}
		return historyMapper.insertSelective(history) > 0 ? "出价成功" : "出价失败";
	}

	/**
	 * 提交订单
	 */
	//@Scheduled(fixedDelay = 1000)
	//@Async
	public void addPoder() {
		// System.out.println("imhere");
		Product product = productService.getProductByStateOrderByTimeASC("拍卖");
		if (product == null)
			return;
		Date now = new Date();
		Date time = product.getTime();
		if (now.compareTo(time) < 0)
			return;
		Integer pid = product.getId();
		// 要更改这个商品了,需要加锁
		synchronized (pid.toString().intern()) {
			// 检查这个商品还需不需要更新
			Product product2 = productService.getProduct(pid);
			if (now.compareTo(product2.getTime())>0) {
				List<History> list = getHistoryById(pid);
				if (list == null || list.size() == 0) {
					productService.updateState("流拍", pid);
					System.out.println(pid + "流拍");
					return;
				}
				productService.updateState("卖出", pid);
				History history = list.get(0);
				Date paytime = new Date();
				String orderid = new SimpleDateFormat("yyyyMMdd").format(paytime);
				String no = orderid + product.getId().toString();
				Integer buyid = history.getUid();
				Integer saleid = product.getOwnerid();
				BigDecimal oldPricce = product.getPrice();
				BigDecimal payPrice = history.getPrice();
				Date ppTime = history.getTime();
				Date porderTime = now;
				Integer aid = history.getAid();
				Address address = addressService.gteAddress(aid);
				StringBuilder adress = new StringBuilder();
				adress.append(address.getAname()).append(" ").append(address.getPhone()).append(" ")
						.append(address.getAdress()).append(" ").append(address.getPostCode());
				Porder porder = new Porder(no, pid, product.getPname(), buyid, saleid, oldPricce, payPrice, ppTime,
						porderTime, aid, adress.toString(), "代付款");
				int addPorder = porderService.addPorder(porder);
				System.out.println(Thread.currentThread().getName() + "更新订单号" + porder.getId() + "状态:" + addPorder);
			}
		}
	}

	public List<History> getMyPIdByUid(Integer uid) {
		
		return null;
	}
}