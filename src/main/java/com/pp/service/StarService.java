package com.pp.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pp.entity.Product;
import com.pp.entity.Star;
import com.pp.utils.MyPage;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.pp.dao.StarMapper;

@Transactional
@Service
public class StarService {
	@Autowired
	private StarMapper starMapper;
	@Autowired
	private ProductService productService;

	public StarMapper getStarMapper() {
		return starMapper;
	}

	public String update(Integer uid, Integer pid) {
		Star record = new Star();
		record.setPid(pid);
		record.setUid(uid);
		System.out.println(pid);
		System.out.println(uid);
		synchronized (uid.toString().intern()) {
			Star one = selectByUidPid(record);
			System.out.println(one);
			if (one != null) {
				int delete = starMapper.delete(record);
				return delete > 0 ? "取消收藏成功" : "取消收藏失败";
			} else {
				record.setTime(new Date());
				int insert = starMapper.insertSelective(record);
				return insert > 0 ? "加入收藏成功" : "加入收藏失败";
			}
		}
	}

	public Star selectByUidPid(Star record) {
		return starMapper.selectOne(record);
	}

	public List<Star> selectByPid(Integer pid) {
		Star record = new Star();
		record.setPid(pid);
		return starMapper.select(record);
	}

	public MyPage<Product> selectMyStar(String beginTime, String endTime, Integer page, Integer uid) {
		Example example = new Example(Star.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("uid=",uid);
//		if (!"".equals(beginTime))
//			criteria.andCondition("time>", beginTime);
//		if (!"".equals(endTime))
//			criteria.andCondition("time<", endTime);
		List<Star> stars = starMapper.selectByExample(example);
		System.out.println("stars="+stars);
		HashSet<Integer> pids = new HashSet<Integer>();
		for (Star star : stars) {
			pids.add(star.getPid());
		}
		System.out.println("pids="+pids);
		return productService.slectByids(pids,page);
	}
}