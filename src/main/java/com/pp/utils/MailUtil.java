package com.pp.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
 
/**
 * 邮件工具类
 */
public class MailUtil {
    //邮箱验证码
    public static boolean sendEmail(String emailaddress,String code,String content){
        // 不要使用SimpleEmail,会出现乱码问题
        HtmlEmail email = new HtmlEmail();
        try {
            // 这里是SMTP发送服务器的名字：，普通qq号只能是smtp.qq.com ；
            email.setHostName("smtp.qq.com");
            //设置需要鉴权端口
            email.setSmtpPort(465);
            //开启 SSL 加密
            email.setSSLOnConnect(true);
           
            // 字符编码集的设置
            email.setCharset("utf-8");
            // 收件人的邮箱
            email.addTo(emailaddress);
            // 发送人的邮箱
            email.setFrom("1350295310@qq.com");
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和得到的授权码
            email.setAuthentication("1350295310@qq.com", "nmgduerpzgybgdja");
            email.setSubject("蜗牛拍拍");
            StringBuilder builder = new StringBuilder();
            //定义要输出日期字符串的格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
             //在内容后加入邮件发送的时间
             builder.append("\n时间：" + sdf.format(new Date()));
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            email.setMsg(content+code+builder);
            // 发送
            email.send();
 
            System.out.println ( "邮件发送成功!" );
            return true;
        } catch (EmailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println ( "邮件发送失败!" );
            return false;
        }
    }
    /**
	 * 产生4位随机字符串 
	 */
	public static String getCheckCode() {
		String base = "0123456789ABCDEFGabcdefg";
		int size = base.length();
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=1;i<=4;i++){
			//产生0到size-1的随机值
			int index = r.nextInt(size);
			//在base字符串中获取下标为index的字符
			char c = base.charAt(index);
			//将c放入到StringBuffer中去
			sb.append(c);
		}
		return sb.toString();
	}
    public static void main(String[] args) {
		MailUtil.sendEmail("1350295310@qq.com", "1234","");
	}
}
