package com.pp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pp.utils.BaseController;
import com.pp.entity.Chat;
import com.pp.entity.User;
import com.pp.log.OperLog;
import com.pp.log.OprLogConst;
import com.pp.model.Message;
import com.pp.service.ChatService;

@Controller
@RequestMapping("/pp/Chat")
public class ChatController extends BaseController {
	@Autowired
	private ChatService chatService;
	@RequestMapping("/getbyTouid")
	@ResponseBody
	public List<Chat> getbyTouid(HttpServletRequest req,Integer id1){
		Object user = super.getSessionVal(req, "user");
		if ("".equals(user)) {
			return null;
		}
		User user2 = (User) user;
		Integer id2 = user2.getId();
		String uname = user2.getUname();
		return chatService.getbyTouid(id1, id2,uname);
	}
	@RequestMapping("/getmessagesbytid")
	@ResponseBody
	public List<Message> getmessages(HttpServletRequest req,Integer id1){
		Object user = super.getSessionVal(req, "user");
		if ("".equals(user)) {
			return null;
		}
		User user2 = (User) user;
		Integer id2 = user2.getId();
		String uname = user2.getUname();
		return chatService.getmessages(id1, id2,uname);
	}
	@RequestMapping("/getmessagesbymid")
	@ResponseBody
	public List<Message> getmessagesbymid(String mid){
		System.out.println(mid);
		return chatService.getmessagesbymid(mid);
	}
}