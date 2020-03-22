package com.pp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pp.dao.PorderMapper;
import com.pp.entity.Porder;

import tk.mybatis.mapper.entity.Example;

@Transactional
@Service
public class PorderService {
	@Autowired
	private PorderMapper porderMapper;

	public PorderMapper getPorderMapper() {
		return porderMapper;
	}

	public int addPorder(Porder porder) {
		return porderMapper.insertSelective(porder);
	}

	public List<Porder> getPoder(Example example) {
		return porderMapper.selectByExample(example);
	}

}