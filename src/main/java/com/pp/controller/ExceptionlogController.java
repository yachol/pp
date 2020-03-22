package com.pp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pp.utils.BaseController;
import com.pp.entity.Exceptionlog;
import com.pp.service.ExceptionlogService;

@Controller
@RequestMapping("/pp/Exceptionlog")
public class ExceptionlogController extends BaseController {
	@Autowired
	private ExceptionlogService exceptionlogService;

}