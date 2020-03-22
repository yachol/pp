package com.pp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pp.utils.BaseController;
import com.pp.utils.MyPage;
import com.pp.entity.Product;
import com.pp.entity.Star;
import com.pp.entity.User;
import com.pp.service.StarService;

@Controller
@RequestMapping("/pp/Star")
public class StarController extends BaseController {
	@Autowired
	private StarService starService;
	
	@RequestMapping("/updatestar")
	@ResponseBody
	public String star(Integer pid,HttpServletRequest req) {
		Object object = super.getSessionVal(req, "user");
		if(object instanceof User) {
			User user=(User)object;
			Integer uid = user.getId();
			return starService.update(uid,pid);
		}
		return "用户信息有异";
	}
	@RequestMapping("/findstar")
	@ResponseBody
	public Star findstar(Integer pid,HttpServletRequest req) {
		Object object = super.getSessionVal(req, "user");
		if(object instanceof User) {
			User user=(User)object;
			Integer uid = user.getId();
			Star record = new Star();
			record.setPid(pid);
			record.setUid(uid);
			return starService.selectByUidPid(record);
		}
		return null;
	}
	@RequestMapping("/getmystarpro")
	@ResponseBody
	public MyPage<Product> getmystarpro(HttpServletRequest req,String beginTime,String endTime,Integer page){
		System.out.println("getmystarpro+begin"+beginTime+"endtime"+endTime);
		Object object = super.getSessionVal(req, "user");
		if(object instanceof User) {
			User user=(User)object;
			Integer uid = user.getId();
			System.out.println("uid"+uid);
			MyPage<Product> selectMyStar = starService.selectMyStar(beginTime,endTime,page,uid);
			System.out.println(selectMyStar);
			return selectMyStar;
		}
		return null;
		
	}
}