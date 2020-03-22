package com.pp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pp.utils.BaseController;
import com.pp.entity.Operationlog;
import com.pp.service.OperationlogService;

@Controller
@RequestMapping("/pp/Operationlog")
public class OperationlogController extends BaseController {
	@Autowired
	private OperationlogService operationlogService;

}