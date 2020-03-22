package com.pp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pp.entity.Operationlog;
import com.pp.dao.OperationlogMapper;

@Transactional
@Service
public class OperationlogService {
	@Autowired
	private OperationlogMapper operationlogMapper;

	public OperationlogMapper getOperationlogMapper() {
		return operationlogMapper;
	}

	public void insert(Operationlog operlog) {
		operationlogMapper.insert(operlog);
	}

}