package kr.or.iei;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	@Autowired
	private JavaMailSender sender;

	public boolean sendMail(String mailTitle, String receiver, String mailContent) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		boolean result = false;
		try {
			helper.setSentDate(new Date());
			helper.setFrom(new InternetAddress("lar@naver.com","강원도 멍멍 탐험대"));
			helper.setTo(receiver);
			helper.setSubject(mailTitle);
			helper.setText(mailContent,true);
			//메일 전송해주는 코드
			sender.send(message);
			result = true;
		}catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public String authMail(String email) {
		System.out.println(email);
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		StringBuffer sb = new StringBuffer();
		
		Random r = new Random();
		
		for(int i=0;i<8;i++) {
			
			int flag = r.nextInt(3);
			if(flag == 0) {
				int num = r.nextInt(10);
				sb.append(num);
			}else if(flag == 1) {
				char ch = (char)(r.nextInt(26)+65);
				sb.append(ch);
			}else if(flag == 2) {
				char ch = (char)(r.nextInt(26)+97);
				sb.append(ch);
			}
		}
		
		try {
			helper.setSentDate(new Date());
			helper.setFrom(new InternetAddress("lar@naver.com","강원도 멍멍 탐험대"));
			helper.setTo(email);
			helper.setSubject("인증 메일입니다.");
			helper.setText("<h1>안녕하세요</h1>"+"<h3>인증번호는 [<span style='color:red;'>"+sb.toString()+"</span>]입니다.</h3>",true);
			sender.send(message);
		} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
}
