package com.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


public class SendMail {

	/**生成纯文本邮件
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage sendTextMail(Session session) throws Exception {

		// 邮件对象，Mime为邮件类型
		MimeMessage message = new MimeMessage(session);
		// 设置发件人邮箱地址,Meiko为设置的发件人名称
		Address address = new InternetAddress("1048683661@qq.com", "Meiko");
		message.setFrom(address);
		// 设置收件人类型，地址
		// message.setRecipient(Message.RecipientType.TO, new
		// InternetAddress("916645573@qq.com"));
		message.setRecipient(Message.RecipientType.CC, new InternetAddress("zhongjichuan@fulan.com.cn "));
		message.setSubject("sendMailTest-title");
		message.setContent("只包含文本的邮件。。。。内容", "text/html;charset=utf-8");

		return message;
	}

	//生成带图片的邮件
	public static MimeMessage sendImgMail(Session session) throws Exception {
		// 邮件对象，Mime为邮件类型
		MimeMessage message = new MimeMessage(session);
		// 设置发件人邮箱地址,Meiko为设置的发件人名称
		Address address = new InternetAddress("1048683661@qq.com", "Meiko");
		message.setFrom(address);
		//收件人
		message.setRecipient(Message.RecipientType.CC, new InternetAddress(
				"zhongjichuan@fulan.com.cn "));
		message.setSubject("带图片的邮件");
		
		//创建图片节点
		
		MimeBodyPart image = new MimeBodyPart();
		SendMail s = new SendMail();
		DataHandler dh = new DataHandler(new FileDataSource("src/main/resources/images/1.jpg"));
		image.setDataHandler(dh);
		image.setContentID("1.jpg");
		
		//创建文本节点
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("这是一封邮件正文带图片<img src='cid:1.jpg'>的邮件","text/html;charset=UTF-8");
		// 描述数据关系,将结合后的节点放入message
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text);
		mm.addBodyPart(image);
		mm.setSubType("related");

		message.setContent(mm);

		return message;
	}
	
	//生成带附件的邮件
	public static MimeMessage sendAttachmentMail(Session session) throws Exception {
		// 邮件对象，Mime为邮件类型
		MimeMessage message = new MimeMessage(session);
		// 设置发件人邮箱地址,Meiko为设置的发件人名称
		Address address = new InternetAddress("1048683661@qq.com", "Meiko");
		message.setFrom(address);
		//收件人
		message.setRecipient(Message.RecipientType.CC, new InternetAddress(
				"zhongjichuan@fulan.com.cn "));
		message.setSubject("带图片的邮件");
		
		//创建图片节点
		MimeBodyPart image = new MimeBodyPart();
		SendMail s = new SendMail();
		DataHandler dh = new DataHandler(new FileDataSource("src/main/resources/images/1.jpg"));
		image.setDataHandler(dh);
		image.setContentID("1.jpg");
		
		//创建文本节点
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("这是一封邮件正文带图片<img src='cid:1.jpg'>的邮件","text/html;charset=UTF-8");
		// 描述数据关系,将结合后的节点放入message
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text);
		mm.addBodyPart(image);
		mm.setSubType("related");//文本图片为关联关系 	
		//复合节点转换成普通节点（含文本，图片）
		MimeBodyPart text_imgBodyPart = new MimeBodyPart();
		text_imgBodyPart.setContent(mm);
		
		//创建附件节点
		MimeBodyPart attachment = new MimeBodyPart();
		FileDataSource fileDataSource = new FileDataSource("src/main/resources/images/2.PNG");
		DataHandler dhAttachment = new DataHandler(fileDataSource);
		attachment.setDataHandler(dhAttachment);
		//给附件设置文件名
		attachment.setFileName(MimeUtility.encodeText(fileDataSource.getName()));
		
		//复合节点
		MimeMultipart mm1 = new MimeMultipart();
		mm1.addBodyPart(text_imgBodyPart);
		mm1.addBodyPart(attachment);
		mm1.setSubType("mixed");
		message.setContent(mm1);
		return message;
	}
	
	
	
	public static void main(String[] args) throws Exception {

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");// 使用smtp协议
		props.setProperty("mail.smtp.host", "smtp.qq.com");// 协议地址
		props.setProperty("mail.smtp.port", "465");// 端口

		// QQ邮箱特别注意
		props.put("mail.smtp.auth", "true");// 授权登录
		props.setProperty("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接
															// ---一般都使用
		props.setProperty("mail.debug", "true");
		// 得到回话对象
		Session session = Session.getInstance(props);
		// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(true);

		//纯文本邮件
		//Message message = sendTextMail(session);
		//文本带图片邮件
		//Message message = sendImgMail(session);
		//带附件邮件
		Message message = sendAttachmentMail(session);
		
		// 邮差对象,1.4需要备注使用“smtp”
		// Transport transport = session.getTransport("smtp");
		Transport transport = session.getTransport();// 此方法中会主动调用getProperty("mail.transport.protocol")
		// 连接自己的邮箱账户
		transport.connect("1048683661@qq.com", "zyelnszguwncbefi");// QQ授权码
		// 发送邮件
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
}
