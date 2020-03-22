package com.pp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pp.entity.Exceptionlog;
import com.pp.dao.ExceptionlogMapper;

@Transactional
@Service
public class ExceptionlogService {
	@Autowired
	private ExceptionlogMapper exceptionlogMapper;

	public ExceptionlogMapper getExceptionlogMapper() {
		return exceptionlogMapper;
	}

	public void insert(Exceptionlog excepLog) {
		exceptionlogMapper.insert(excepLog);
	}

}