package com.pp.log;


import javax.servlet.http.HttpServletRequest;

import com.pp.entity.User;


public class UserShiroUtil {

	public static String getCurrentUserId(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		return user.getId().toString();
	}

	public static String getCurrentUserName(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		return user.getUname();
	}

}
