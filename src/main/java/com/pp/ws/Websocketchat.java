package com.pp.ws;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;

@ServerEndpoint("/sendmessage/{uid}")
@Component
public class Websocketchat {
	// static Log log=LogFactory.get(WebSocketServer.class);
	/** 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。 */
	// private static int onlineCount = 0;
	/** concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。 */
	private static ConcurrentHashMap<String, Websocketchat> webSocketMap = new ConcurrentHashMap<>();
	/** 与某个客户端的连接会话，需要通过它来给客户端发送数据 */
	private Session session;
	/** 接收userId */
	private String uid = "";
	private static Jedis jedis = new Jedis();
	@OnOpen
	public void onOpen(Session session, @PathParam("uid") String uid) {
		this.session = session;
		this.uid = uid;
		if (webSocketMap.containsKey(uid)) {
			webSocketMap.remove(uid);
			webSocketMap.put(uid, this);
			// 加入set中
		} else {
			webSocketMap.put(uid, this);
		}
		System.out.println("Websocketchat连接成功" + webSocketMap);
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message 客户端发送过来的消息
	 * @throws IOException 
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		System.out.println("这里是onmessage");
		// 可以群发消息
		// 消息保存到数据库、redis
		if (StringUtils.isNotBlank(message)) {
			JSONObject jsonObject = JSON.parseObject(message);
			jsonObject.put("time", new Date());
			System.out.println(jsonObject);
			String tid = jsonObject.getString("tid");
			String mid = jsonObject.getString("mid");
			 if(StringUtils.isNotBlank(tid)&&webSocketMap.containsKey(tid)){
                 webSocketMap.get(tid).sendMessage(jsonObject.toJSONString());
             }
			 jedis.select(1);
			 jedis.rpush(mid, jsonObject.toString());
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		if(webSocketMap.containsKey(uid))
            webSocketMap.remove(uid);
		System.out.println("Websocketchat关闭");
	}
	 /**
    *
    * @param session
    * @param error
    */
   @OnError
   public void onError(Session session, Throwable error) {
       error.printStackTrace();
   }
	/**
	 * 实现服务器主动推送
	 * 
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	/**
	 * 发送自定义消息
	 * 
	 * @throws IOException
	 */
	public static void sendInfo(String uid,String message) throws IOException {
		if(webSocketMap.containsKey(uid))
			webSocketMap.get(uid).sendMessage(message);
	}
}