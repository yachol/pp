package com.pp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pp.utils.BaseController;

@Controller
public class HomeController extends BaseController {

	@RequestMapping("/")
	public String home() {
		return "pp/home";
	}

	@RequestMapping("/openWork/{work}")
	public String openWork(@PathVariable("work") String work) {
		System.out.println(work);
		return "pp/" + work;
	}

	@RequestMapping({"/success","/info"})
	public String success() {
		return "pp/success";
	}
	
	@RequestMapping({"/login"})
	public String login() {
		return "pp/login";
	}
	@RequestMapping({"/register"})
	public String regist() {
		return "pp/register";
	}
	@RequestMapping({"/updateInfo"})
	public String updateInfo() {
		return "pp/updateInfo";
	}
	@RequestMapping({"/addaddress"})
	public String addaddress() {
		return "pp/addaddress";
	}
	@RequestMapping({"/history"})
	public String history() {
		return "pp/history";
	}
}