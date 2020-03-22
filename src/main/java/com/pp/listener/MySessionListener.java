package com.pp.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.pp.model.MySessionContext;

@WebListener
public class MySessionListener implements HttpSessionListener {
	@Autowired
	private MySessionContext myc;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		myc.AddSession(se.getSession());
		System.out.println("sesion 监听器");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		myc.DelSession(session);
	}

}
