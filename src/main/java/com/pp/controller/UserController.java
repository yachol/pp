package com.pp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.pp.config.AlipayConfig;
import com.pp.entity.User;
import com.pp.model.MySessionContext;
import com.pp.service.UserService;
import com.pp.utils.BaseController;
import com.pp.utils.MailUtil;
import com.pp.utils.MyPage;
import com.pp.utils.VerifyUtils;

@Controller
@RequestMapping("/pp/User")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private MySessionContext myc;

	/**
	 * 注册
	 * 
	 * @param user
	 * @param repass
	 * @param code
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/register")
	public String register(User user, String repass, String code, HttpServletRequest req) {
		if (!VerifyUtils.isUsername(user.getUname()))
			return "登录名应为6-18位字母/数字";
		if (!VerifyUtils.isPassword(user.getPassword()))
			return "密码应为6-18位字母/数字";
		if (!user.getPassword().equals(repass))
			return "两次密码不一致";
		if (!VerifyUtils.isEmail(user.getEmail()))
			return "邮箱不合法";
		if (VerifyUtils.isNullOrEmpty(code) || !super.getSessionVal(req, "code").equals(code))
			return "验证码不正确";
		user.setRegistTime(new Date());
		String register = userService.register(user);
		if ("success".equals(register)) {
			HttpSession session = req.getSession(false);
			session.removeAttribute("code");
			return "注册成功，请登录";
		}
		return register;
	}

	/**
	 * 获取邮箱验证码
	 * 
	 * @param req
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getcode")
	public String getCode(HttpServletRequest req, String email) {
		if (VerifyUtils.isEmail(email)) {
			String code = MailUtil.getCheckCode();
			boolean sendFlag = MailUtil.sendEmail(email, code, "欢迎注册,验证码为:");
			if (sendFlag) {
				super.setSessionVal(req, "code", code);
				return "success";
			}
		}
		return "邮件发送失败";
	}

	@ResponseBody
	@RequestMapping("/login")
	public String login(User user, HttpServletRequest req, HttpSession session) {
		if (!VerifyUtils.isUsername(user.getUname()))
			return "登录名应为6-18位字母/数字";
		if (!VerifyUtils.isPassword(user.getPassword()))
			return "密码应为6-18位字母/数字";
		User login = userService.login(user);

		if (login != null) {
			if ("-1".equals(login.getSessionid())) {
				return "您被限制登录,如有不服,忍";
			}
			System.out.println("pp2" + session);
//			req.getSession().setAttribute("user", login);
			super.setSessionVal(req, "user", login);
			String sessionid = req.getSession().getId();
			// System.out.println(sessionid);//TnKaNk6UpeLSzEg003tlbQaXMOPUfJDFckXWI22m
			synchronized (login.getId().toString().intern()) {
				return userService.updateSessionid(login.getId(), sessionid);
			}
		}
		return "用户名或密码错误";
	}

	@ResponseBody
	@RequestMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.removeAttribute("user");
			return "success";
		}
		return "注销失败";
	}

	@ResponseBody
	@RequestMapping("/updateinfo")
	public String updateInfo(User user, String repass, HttpServletRequest req) {
		if (!VerifyUtils.isUsername(user.getUname()))
			return "登录名应为6-18位字母/数字";
		if (!VerifyUtils.isPassword(user.getPassword()))
			return "密码应为6-18位字母/数字";
		if (!user.getPassword().equals(repass))
			return "两次密码不一致";
		if (!VerifyUtils.isEmail(user.getEmail()))
			return "邮箱不合法";
		User usersession = (User) super.getSessionVal(req, "user");
		user.setId(usersession.getId());
		boolean update = userService.update(user);
		if (update) {
			User login = userService.login(user);
			super.setSessionVal(req, "user", login);
			return "更新成功";
		}
		return "更新失败";
	}

	@ResponseBody
	@RequestMapping("/getusers")
	public String getUsers(@RequestParam("page") Integer page, @RequestParam("beginTime") String beginTime,
			@RequestParam("endTime") String endTime) {
		MyPage<User> onePage = userService.getOnePage(page, beginTime, endTime);
		String jsonString = JSON.toJSONString(onePage);
		System.out.println("pp" + onePage);
		return jsonString;
	}

	@ResponseBody
	@RequestMapping("/frozen")
	public String frozen(@RequestParam("id") Integer id, @RequestParam("sessionid") String sessionid) {
		System.out.println("sessionid" + sessionid);
		if ("-1".equals(sessionid)) {
			return userService.unFrozen(id, "0");
		} else {
			User user = userService.selectByPrimarykey(id);
			String sessionid2 = user.getSessionid();
			HttpSession session = myc.getSession(sessionid2);
			System.out.println("session" + sessionid2);
			if (session != null)
				session.removeAttribute("user");
			return userService.unFrozen(id, "-1");
		}
	}

	@RequestMapping("/pay")
	@ResponseBody
	public String pay(Integer pid) {
		// 获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
				AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
				AlipayConfig.sign_type);
		// 设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
//       alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		// 商户订单号，商户网站订单系统中唯一订单号，必填，以随机UUID为例
//        String out_trade_no = new String(UUID.randomUUID().toString().getBytes("ISO-8859-1"),"UTF-8");
//        //付款金额，必填,以100为例，可自行修改
//        String total_amount = new String("100".getBytes("ISO-8859-1"),"UTF-8");
//        //订单名称，必填
//        String subject = new String("这里填你的订单名称".getBytes("ISO-8859-1"),"UTF-8");
//        //商品描述，可空
//        String body = new String("这里填你的订单描述".getBytes("ISO-8859-1"),"UTF-8");

		alipayRequest.setBizContent("{\"out_trade_no\":\"" + UUID.randomUUID().toString() + "\"," + "\"total_amount\":\"" + 1 + "\","
				+ "\"subject\":\"" + "测试233" + "\"," + "\"body\":\"" + "测试" + "\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		String result = null;
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
			System.out.println(result);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("alipay_callback")
	@ResponseBody
	public String alipayCallback(HttpServletRequest request) throws AlipayApiException {
		System.out.println("可以吗");
		Map<String, String> params = new HashMap();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		// 非常重要,验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.
		params.remove("sign_type");
		boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, AlipayConfig.alipay_public_key, "utf-8",
				AlipayConfig.sign_type);
		if (!alipayRSACheckedV2) {
			System.out.println("验证未通过");
			return "失败";
		} else {
			return "成功";
		}
	}
}