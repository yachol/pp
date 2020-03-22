package com.pp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pp.utils.BaseController;
import com.pp.utils.VerifyUtils;
import com.pp.entity.Address;
import com.pp.entity.User;
import com.pp.service.AddressService;

@Controller
@RequestMapping("/pp/Address")
public class AddressController extends BaseController {
	@Autowired
	private AddressService addressService;

	/**
	 * 	添加地址
	 * 
	 * @param address
	 * @param postCode
	 * @param province
	 * @param city
	 * @param district
	 * @param moren
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addaddress")
	public String addAddress(Address address, String province, String city, String district,
			String moren, HttpServletRequest req) {
		// 验证****
		
		if (VerifyUtils.isNullOrEmpty(address.getAdress())) {
			return "详细地址不能为空";
		}
		if (VerifyUtils.isNullOrEmpty(address.getPostCode())) {
			return "邮政编码不能为空";
		}
		if (VerifyUtils.isNullOrEmpty(address.getAname())) {
			return "名字不能为空";
		}
		if (VerifyUtils.isNullOrEmpty(address.getPhone())||!VerifyUtils.isMobile(address.getPhone())) {
			return "手机号不合法";
		}
		if ("".equals(province)||"".equals(city)||"".equals(district)) {
			return "信息不能为空";
		}
		
		System.out.println(address);
		String addressString = province + city + district + address.getAdress();
		address.setAdress(addressString);
		address.setIsdefault("on".equals(moren) ? "是" : "否");
		Object user = super.getSessionVal(req, "user");
		if ("".equals(user)) {
			return "未登录";
		}
		//
		User user2 = (User) user;
		address.setUid(user2.getId());
		try {
			return addressService.addAddress(address, user2.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}

	/**
	 * 展示登录状态的adress
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showaddress")
	public List<Address> showAddress(HttpServletRequest req) {
		Object user = super.getSessionVal(req, "user");
		if ("".equals(user)) {
			return null;
		}
		User user2 = (User) user;
		return addressService.showAddress(user2.getId());
	}
	
	@ResponseBody
	@RequestMapping("/delateaddress")
	public String delateAddress(Integer aid) {
		return addressService.delateAddress(aid);
		
		
		
		
//		Object user = super.getSessionVal(req, "user");
//		if ("".equals(user)) {
//			return null;
//		}
//		User user2 = (User) user;
//		return addressService.showAddress(user2.getId());
	}
}