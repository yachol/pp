package com.pp.log;


import javax.servlet.http.HttpServletRequest;

public class IPUtil {

	public static String getRemortIP(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

}
