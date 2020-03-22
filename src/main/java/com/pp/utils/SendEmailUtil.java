package com.pp.utils;

 
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.util.MailSSLSocketFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
 
public class SendEmailUtil {
    
    //邮件服务器主机名
    // QQ邮箱的 SMTP 服务器地址为: smtp.qq.com
    private static String myEmailSMTPHost = "smtp.163.com";
    
    //发件人邮箱
    private static String myEmailAccount = "yachol@163.com";
    
    //发件人邮箱密码（授权码）gycdkidqepvdffed
    //在开启SMTP服务时会获取到一个授权码，把授权码填在这里qfchtjzebkynifhe
    private static String myEmailPassword = "yachol9088";
    //邮件主题
    private static  String emailTitle ="蜗牛拍拍注册验证码";
    // 邮件内容
    private static String emailContent="";
    /**
     * 邮件单发（自由编辑短信，并发送，适用于私信）
     *
     * @param toEmailAddress 收件箱地址
     * @param emailTitle 邮件主题
     * @param emailContent 邮件内容
     * @throws Exception
     */
    public static boolean sendEmail(String toEmailAddress,String code ){
       try {
		
	     
        Properties props = new Properties();
         
        // 开启debug调试
        props.setProperty("mail.debug", "true");
                 
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
         
        // 端口号
        props.put("mail.smtp.port", 465);
         
        // 设置邮件服务器主机名
        props.setProperty("mail.smtp.host", myEmailSMTPHost);
         
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
         
        /**SSL认证，注意腾讯邮箱是基于SSL加密的，所以需要开启才可以使用**/
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
         
        //设置是否使用ssl安全连接（一般都使用）
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
         
        //创建会话
        Session session = Session.getInstance(props);
         
        //获取邮件对象
        //发送的消息，基于观察者模式进行设计的
        Message msg = new MimeMessage(session);
         
        //设置邮件标题
        msg.setSubject(emailTitle);
         
        //设置邮件内容
        //使用StringBuilder，因为StringBuilder加载速度会比String快，而且线程安全性也不错
        StringBuilder builder = new StringBuilder();
         
        //写入内容
        builder.append("\n" + emailContent+code);
         
        //写入我的官网
       // builder.append("\n官网：" + "https://www.hbuecx.club");
         
        //定义要输出日期字符串的格式
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         
        //在内容后加入邮件发送的时间
        builder.append("\n时间：" + sdf.format(new Date()));
         
        //设置显示的发件时间
       msg.setSentDate(new Date());
         
        //设置邮件内容
        msg.setText(builder.toString());
         
        //设置发件人邮箱
        // InternetAddress 的三个参数分别为: 发件人邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
        msg.setFrom(new InternetAddress(myEmailAccount,"蜗牛拍拍", "UTF-8"));
         
        //得到邮差对象
        Transport transport = session.getTransport();
         
        //连接自己的邮箱账户
        //密码不是自己QQ邮箱的密码，而是在开启SMTP服务时所获取到的授权码
        //connect(host, user, password)
        transport.connect( myEmailSMTPHost, myEmailAccount, myEmailPassword);
        // 构建简单邮件对象，见名知意
        // *** 关键 ***
        msg.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse("yachol@163.com"));
       // MimeMessageHelper helper = new MimeMessageHelper(msg,true,"utf-8");

        //发送邮件
        transport.sendMessage(msg, new Address[] { new InternetAddress(toEmailAddress) });
         
        //将该邮件保存到本地
      // OutputStream out = new FileOutputStream("MyEmail.eml");
//        msg.writeTo(out);
//        out.flush();
//        out.close();
        //transport.close();
        return true;
       } catch (Exception e) {
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
		try {
			sendEmail("1350295310@qq.com","1234");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
