package com.pp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pp.entity.Product;
import com.pp.utils.MyPage;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;
import com.pp.dao.ProductMapper;

@Transactional
@Service
public class ProductService {
	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private StringRedisTemplate redisTemplate;

	public ProductMapper getProductMapper() {
		return productMapper;
	}

	/**
	 * 首页页面拍卖商品展示
	 * 
	 * @param name  商品名字
	 * @param state 状态
	 * @param page  第几页
	 * @param area  区域
	 * @return
	 */
	public MyPage<Product> getOnePage(String name, String state, Integer page, String area) {
		Example example = new Example(Product.class);
		Criteria criteria = example.createCriteria();
		// 对搜索关键字分词
		JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
		List<SegToken> process = jiebaSegmenter.process(name, SegMode.SEARCH);
		//总的ids
		Set<String> ids = new TreeSet<String>();
		// 对于每个分词,都查出其对应的商品id,存入set集合
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		for (SegToken segToken : process) {
			ids.addAll(opsForSet.members(segToken.word));
		}
		// 区域筛选ids,如果area为空,则不能加进来
		if(area!=null&&!"".equals(area)) {
			Set<String> areaids = opsForSet.members(area);
			if(areaids.size()==0)//该地区没有,-1
				ids.add("-1");
			else //该地区有,求交集
				ids.retainAll(areaids);
		}
		//System.out.println(ids);
		// 关键词筛选ids
		for (String id : ids) {
			criteria.orCondition("id=", id);
		}
		// 状态筛选
		if (!"".equals(state))
			criteria.andCondition("state=", state);
		// 通过审核的上架状态
		criteria.andCondition("shelf!=", "下架");
		// 时间倒序
		example.setOrderByClause("time desc");
		if (page == null || page < 0) {
			page = 1;
		}
		return MyPage.selectByExampleAndPage(productMapper, example, page, 8);
	}
	public Product getProduct(Integer pid) {
		ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
		if(opsForZSet.rank("circusee", pid+"")==null)
			opsForZSet.add("circusee", pid+"", 1);
		else 
			opsForZSet.incrementScore("circusee", pid+"", 1);
		return productMapper.selectByPrimaryKey(pid);
	}

	public void updateTime(Date date, Integer pid) {
		Product product = new Product();
		// product.setId(pid);
		product.setTime(date);
		Example example = new Example(Product.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("id=", pid);
		// System.out.println("updateTime");
		productMapper.updateByExampleSelective(product, example);
	}

	public Product getProductByStateOrderByTime(String state) {
		Example example = new Example(Product.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("state=", state);
		example.setOrderByClause("time desc");
		PageHelper.startPage(1, 1);
		return productMapper.selectOneByExample(example);
		// return productMapper.selectByExample(example);
	}

	public Product getProductByStateOrderByTimeASC(String state) {
		Example example = new Example(Product.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("state=", state);
		example.setOrderByClause("time ASC");
		PageHelper.startPage(1, 1);
		return productMapper.selectOneByExample(example);
	}

	public String updateState(String state, Integer pid) {
		Product product = new Product();
		product.setState(state);
		Example example = new Example(Product.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("id=", pid);
		return productMapper.updateByExampleSelective(product, example) > 0 ? "更改成功" : "更改失败";

	}

	public int addProduct(Product product) {
		return productMapper.insert(product);
	}

	public List<Product> getProductsByUid(Integer uid) {
		Example example = new Example(Product.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("ownerid=", uid);
		return productMapper.selectByExample(example);
	}

	public Map<String, Object> getOnePage2(Integer page, String beginTime, String endTime, String state,String shelf) {
		Example example = new Example(Product.class);
		Criteria criteria = example.createCriteria();
		if (!"".equals(beginTime))
			criteria.andCondition("time>", beginTime);
		if (!"".equals(endTime))
			criteria.andCondition("time<", endTime);
		if (!"".equals(state))
			criteria.andCondition("state=", state);
		if(!"".equals(shelf)) 
			criteria.andCondition("shelf=", shelf);
		if (page == null || page < 0) {
			page = 1;
		}
		example.setOrderByClause("time desc");
		MyPage<Product> myPage = MyPage.selectByExampleAndPage(productMapper, example, page, 4);
		//System.out.println("pp" + myPage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", myPage);
		return map;
	}

	public String updateShelf(String shelf, Integer pid) {
		Product product = new Product();
		product.setShelf(shelf);
		Example example = new Example(Product.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("id=", pid);
		if("上架".equals(shelf)) {
			Product product2 = getProduct(pid);
			Date time = product2.getTime();
			ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
				opsForZSet.add("productcopy", pid+"", time.getTime());
		}
		return productMapper.updateByExampleSelective(product, example) > 0 ? "更改成功" : "更改失败";
	}

	public List<Product> getcircusee() {
		ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
		Set<String> set = opsForZSet.reverseRange("circusee", 0, 3);
		Example example = new Example(Product.class);
		Criteria criteria = example.createCriteria();
		if(set==null||set.size()==0)
			return null;
		for (String id : set) {
			criteria.orCondition("id=", id);
		}
		return productMapper.selectByExample(example);
	}

	public MyPage<Product> slectByids(HashSet<Integer> pids, Integer page) {
		Example example = new Example(Product.class);
		Criteria criteria = example.createCriteria();
		for (Integer id : pids) {
			criteria.orCondition("id=",id);
		}
		if (page != null && page > 0) {
			return MyPage.selectByExampleAndPage(productMapper, example, page, 8);
		}
		return null;
	}
}