package com.pp.model;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
@Component
public class MySessionContext {
	private HashMap<String,HttpSession> mymap;

	private MySessionContext() {
		this.mymap = new HashMap<String,HttpSession>();
	}

	public void AddSession(HttpSession session) {
		if (session != null) {
			mymap.put(session.getId(), session);
		}
		System.out.println(mymap);
	}

	public  void DelSession(HttpSession session) {
		if (session != null) {
			mymap.remove(session.getId());
		}
		System.out.println(mymap);
	}

	public  HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		return (HttpSession) mymap.get(session_id);
	}
}
