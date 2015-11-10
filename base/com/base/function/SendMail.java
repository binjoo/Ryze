package com.base.function;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.base.utils.AppConfig;

@SuppressWarnings("unused")
public class SendMail {
	private String toEmail;
	private String title;
	private String content;
	private String sendType = "html";
	private String contentType = "text/html";
	private String code = "base64";

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
		if(this.sendType.equalsIgnoreCase("plain")){
			this.contentType = "text/plain";
		}else{
			this.contentType = "text/html";
		}
	}

	public String getContentType() {
		return contentType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 发送邮件
	 * @throws Exception
	 */
	public void send() throws Exception {
		// 配置javamail
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", AppConfig.getPro("mail.smtp.host"));
		props.put("mail.smtp.port", AppConfig.getPro("mail.smtp.port"));

		// 控制连接和socket timeout 时间
		props.put("mail.smtp.connectiontimeout", 180);
		props.put("mail.smtp.timeout", 600);
		props.setProperty("mail.mime.encodefilename", "true");

		// 认证的用户和密码， 不同于登录站点的用户名和密码，需要登录SendCloud进行发信域名获取
		final String userName = AppConfig.getPro("mail.smtp.username");
		final String password = AppConfig.getPro("mail.smtp.password");

		// 用户验证
		Session mailSession = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		// 设置调试，打印smtp信息
		//mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();
		MimeMessage message = new MimeMessage(mailSession);
		Multipart multipart = new MimeMultipart("alternative");


		// html形式的邮件正文
		BodyPart part = new MimeBodyPart();
		part.setHeader("Content-Type", "text/" + this.contentType + "; charset=UTF-8");
		part.setHeader("Content-Transfer-Encoding", this.code); // 或者使用base64
		if(this.sendType.equalsIgnoreCase("plain")){
			part.setText("欢迎使用SendCloud");
		}else{
			part.setContent(this.content, "text/html; charset=UTF-8");
		}

		multipart.addBodyPart(part);

		message.setContent(multipart);
		message.setFrom(new InternetAddress("noreply@binjoo.net", "冰剑管家", "UTF-8"));
		message.setSubject(this.title, "UTF-8");
		message.addRecipient(RecipientType.TO, new InternetAddress(this.toEmail));

		transport.connect();
		transport.sendMessage(message, message.getRecipients(RecipientType.TO));
		transport.close();
	}
	
	public static void main(String[] args) {
		SendMail sm = new SendMail();
		sm.setTitle("xxxxx");
		sm.setToEmail("icesword28@qq.com");
		try {
			sm.send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
