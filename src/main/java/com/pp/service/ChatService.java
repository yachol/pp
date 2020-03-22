package com.pp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pp.entity.Chat;
import com.pp.entity.User;
import com.pp.model.Message;

import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSON;
import com.pp.dao.ChatMapper;
import com.pp.dao.UserMapper;

@Transactional
@Service
public class ChatService {
	@Autowired
	private ChatMapper chatMapper;
	@Autowired
	private UserMapper userMapper;
	private Jedis jedis = new Jedis();

	public ChatMapper getChatMapper() {
		return chatMapper;
	}

	public List<Chat> getbyTouid(Integer id1, Integer id2, String uname2) {
		if (id1 != 0) {
			String mid = null;
			// 查
			Example example = new Example(Chat.class);
			Criteria criteria = example.createCriteria();
			criteria.andCondition("Fuid=", id1);
			criteria.andCondition("Tuid=", id2);
			Chat chat = chatMapper.selectOneByExample(example);
			// 没有插入
			if (chat == null) {
				mid = UUID.randomUUID().toString();
				User user = new User();
				user.setId(id1);
				User user2 = userMapper.selectByPrimaryKey(user);
				String uname1 = user2.getUname();
				// 1
				Chat newchat1 = new Chat();
				newchat1.setFuid(id1);
				newchat1.setFuname(uname1);
				newchat1.setTuid(id2);
				newchat1.setTuname(uname2);
				newchat1.setMid(mid);
				newchat1.setUnread(0);
				chatMapper.insertSelective(newchat1);
				// 2
				Chat newchat2 = new Chat();
				newchat2.setFuid(id2);
				newchat2.setFuname(uname2);
				newchat2.setTuid(id1);
				newchat2.setTuname(uname1);
				newchat2.setMid(mid);
				newchat2.setUnread(0);
				chatMapper.insertSelective(newchat2);
			}
		}
		Chat record = new Chat();
		record.setTuid(id2);
		return chatMapper.select(record);
	}

	public List<Message> getmessages(Integer id1, Integer id2, String uname2) {
		String mid = null;
		// 查
		Example example = new Example(Chat.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("Fuid=", id1);
		criteria.andCondition("Tuid=", id2);
		Chat chat = chatMapper.selectOneByExample(example);
		if (chat == null) {
			return null;
		}
		mid = chat.getMid();
		//System.out.println(chat);
		//System.out.println("mid"+mid);
		jedis.select(1);
		List<String> list = jedis.lrange(mid, 0, -1);
		ArrayList<Message> messages = new ArrayList<Message>();
		for (String string : list) {
			messages.add(JSON.parseObject(string, Message.class));
		}
		return messages;
	}

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
	}

	public List<Message> getmessagesbymid(String mid) {
		jedis.select(1);
		List<String> list = jedis.lrange(mid, 0, -1);
		ArrayList<Message> messages = new ArrayList<Message>();
		for (String string : list) {
			messages.add(JSON.parseObject(string, Message.class));
		}
		return messages;
	}

	public Chat selectBtid0(Integer uid) {
		Example example = new Example(Chat.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("Tuid=", uid);
		criteria.andCondition("Fuid=",0);
		return chatMapper.selectOneByExample(example);
	}
}