package com.pp.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pp.entity.Chat;
import com.pp.entity.User;
import com.pp.utils.MyPage;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.pp.dao.ChatMapper;
import com.pp.dao.UserMapper;

@Transactional
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ChatMapper chatMapper;
	public UserMapper getUserMapper() {
		return userMapper;
	}

	public String register(User user) {
		synchronized (user.getUname().trim().intern()) {
			if (userMapper.selectByUname(user.getUname().trim()) != null)
				return "用户名已被注册";
			int insert = userMapper.insert(user);
			if(insert>0) {
				Chat chat = new Chat();
				chat.setFuid(0);
				chat.setFuname("小拍");
				chat.setTuid(user.getId());
				chat.setTuname(user.getUname());
				chat.setUnread(0);
				chat.setMid(UUID.randomUUID().toString());
				return  chatMapper.insert(chat)> 0 ? "success" : "服务器忙，注册失败";
			}
			return "服务器忙，注册失败";
		}
	}

	public User login(User user) {
		return userMapper.selectOne(user);
	}

	public boolean update(User user) {
		return userMapper.updateByPrimaryKey(user) > 0 ? true : false;

	}

	public String updateSessionid(Integer id, String sessionid) {
		User selectByPrimaryKey = selectByPrimarykey(id);
		String sessionid2 = selectByPrimaryKey.getSessionid();
		if ("-1".equals(sessionid2))
			return "您刚被限制登录,如有不服,忍";
		Example example = new Example(User.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("id=", id);
		User user = new User();
		user.setSessionid(sessionid);
		return userMapper.updateByExampleSelective(user, example) > 0 ? "success" : "sessionid更新失败";
	}

	public User selectByPrimarykey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public MyPage<User> getOnePage(Integer page, String beginTime, String endTime) {
		Example example = new Example(User.class);
		Criteria criteria = example.createCriteria();
		if (!"".equals(beginTime))
			criteria.andCondition("regist_time>", beginTime);
		if (!"".equals(endTime))
			criteria.andCondition("regist_time<", endTime);
		if (page != null && page > 0) {
			return MyPage.selectByExampleAndPage(userMapper, example, page, 8);
		}
		return null;
	}

	public String unFrozen(Integer id, String sessionid) {
		Example example = new Example(User.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("id=", id);
		User user = new User();
		user.setSessionid(sessionid);
		synchronized (id.toString().intern()) {
			if ("0".equals(sessionid)) {
				if (userMapper.updateByExampleSelective(user, example) > 0)
					return "解冻成功";
				else
					return "解冻失败";
			} else {
				if (userMapper.updateByExampleSelective(user, example) > 0)
					return "冻结成功";
				else
					return "冻结失败";
			}
		}
	}

}