package com.pp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pp.entity.Porder;
import com.pp.entity.User;
import com.pp.service.PorderService;
import com.pp.utils.BaseController;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Controller
@RequestMapping("/pp/Porder")
public class PorderController extends BaseController {
	@Autowired
	private PorderService porderService;

	@ResponseBody
	@RequestMapping("/getporderbuy")
	public List<Porder> getPorderBuy(HttpServletRequest req) {
		Object user = super.getSessionVal(req, "user");
		if ("".equals(user)) {
			return null;
		}
		User user2 = (User) user;

		Example example = new Example(Porder.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("buyid=", user2.getId());
		return porderService.getPoder(example);
	}

	@ResponseBody
	@RequestMapping("/getpordersale")
	public List<Porder> getPorderSale(HttpServletRequest req) {
		Object user = super.getSessionVal(req, "user");
		if ("".equals(user)) {
			return null;
		}
		User user2 = (User) user;

		Example example = new Example(Porder.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("saleid=", user2.getId());
		return porderService.getPoder(example);
	}
}