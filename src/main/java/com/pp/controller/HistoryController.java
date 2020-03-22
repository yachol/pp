package com.pp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pp.entity.History;
import com.pp.entity.User;
import com.pp.log.OperLog;
import com.pp.service.HistoryService;
import com.pp.utils.BaseController;
import com.pp.ws.WebsocketHistory;

@Controller
@RequestMapping("/pp/History")
public class HistoryController extends BaseController {
	@Autowired
	private HistoryService historyService;

	@ResponseBody
	@RequestMapping("/gethistory")
	public Map<String, Object> getHistory(Integer pid) {
		// System.out.println("pid" + pid);
		List<History> list = historyService.getHistoryById(pid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historylist", list);
		// System.out.println(map);
		return map;
	}

//	@ResponseBody
//	@RequestMapping("/gethistory2")
//	public Map<String, Object> getHistory2(@RequestParam("pid")Integer pid){
//		List<History> list = historyService.getHistoryById(pid);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("list", list);
//		return map;
//	}
	@OperLog(operModul = "拍卖纪录", operType = "添加", operDesc = "拍卖功能")
	@ResponseBody
	@RequestMapping("/addHistory")
	public String addHistory(History history, HttpServletRequest req) {
		// System.out.println(history);
		Integer pid = history.getPid();
		if (pid == null || pid < 0) {
			return "错误的商品,请不要而恶意攻击";
		}
		Object user = super.getSessionVal(req, "user");
		if ("".equals(user)) {
			return "未登录";
		}
		User user2 = (User) user;
		history.setUid(user2.getId());
		history.setUname(user2.getUname());
		synchronized (pid.toString().intern()) {
			try {
				String addHistory = historyService.addHistory(history);
				WebsocketHistory.sendInfo(pid + "");
				return addHistory;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "出价失败";
	}

	@ResponseBody
	@RequestMapping("/getmyhistory")
	public List<History> getMyHistory(Integer uid) {
		// System.out.println("pid" + pid);
		return historyService.getMyPIdByUid(uid);
	}

}