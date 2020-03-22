package com.pp.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@ServerEndpoint("/sendhistory/{pid}")
@Component
public class WebsocketHistory {
	// static Log log=LogFactory.get(WebSocketServer.class);
	/** 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。 */
	// private static int onlineCount = 0;
	/** concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。 */
	private static ConcurrentHashMap<String, List<WebsocketHistory>> webSocketMap = new ConcurrentHashMap<>();
	/** 与某个客户端的连接会话，需要通过它来给客户端发送数据 */
	private Session session;
	/** 接收userId */
	private String pid = "";

	@OnOpen
	public void onOpen(Session session, @PathParam("pid") String pid) {
		this.session = session;
		this.pid = pid;
		List<WebsocketHistory> list =null;
		synchronized (pid.intern()) {
			if (webSocketMap.containsKey(pid)) {
				list = webSocketMap.get(pid);
			}else {
				list = new ArrayList<WebsocketHistory>();
				webSocketMap.put(pid, list);
			}
		}
		list.add(this);
		System.out.println("websocketHistory连接成功"+this);
		System.out.println(this instanceof WebsocketHistory);
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("这里是onmessage");
		// 可以群发消息
		// 消息保存到数据库、redis
		if (StringUtils.isNotBlank(message)) {
			System.out.println("接收到消息" + message);
			System.out.println(webSocketMap.get(pid));
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		List<WebsocketHistory> list = webSocketMap.get(pid);
		list.remove(this);
		if (list.size() == 0)
			webSocketMap.remove(pid);
		System.out.println("websocketHistory关闭");
	}

	/**
	 * 实现服务器主动推送
	 * @throws IOException 
	 */
	public void sendMessage(String message) throws IOException  {
		this.session.getBasicRemote().sendText(message);
	}

	/**
     * 发送自定义消息
	 * @throws IOException 
     * */
    public static void sendInfo(String pid) throws IOException{
//      System.out.println("这里是sendInfo"+pid);
//      System.out.println(webSocketMap);
//      System.out.println(webSocketMap.contains(pid));
    	if(webSocketMap.containsKey(pid)) {
//    		System.out.println("contains");
    	   List<WebsocketHistory> list = webSocketMap.get(pid);
    	   for (WebsocketHistory websocketHistory : list) {
//    		   System.out.println("发送消息给"+websocketHistory);
			websocketHistory.sendMessage("update");
		}
       }
    }
}
