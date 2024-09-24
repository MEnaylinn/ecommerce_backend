package com.hostmdy.ecommerce.utility;

import java.io.File;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.hostmdy.ecommerce.domain.Order;

import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailConstructor {
	
	private final Environment env;
	private final TemplateEngine templateEngine;
	private final ClasspathFileLoader classpathFileLoader;
	
	public SimpleMailMessage constructSimpleMail(String to,String subject,String text) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom(env.getProperty("support.mail"));
		email.setTo(to);
		email.setSubject(subject);
		email.setText(text);
		return email;
	}
	
	public MimeMessagePreparator constructTemplateMail(String to,String subject,Order order) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			Context context = new Context();
			context.setVariable("order", order);
			
			String text = templateEngine.process("order-confirm", context);
			
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			
			messageHelper.setFrom(env.getProperty("support.mail"));
			messageHelper.setTo(new InternetAddress(to));
			messageHelper.setSubject(subject);
			messageHelper.setText(text,true);
		};
		
		return messagePreparator;
	}
	
	public MimeMessagePreparator constructAttachmentMail(String to,String subject,String filePath,String text) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
			messageHelper.setFrom(env.getProperty("support.mail"));
			messageHelper.setTo(new InternetAddress(to));
			messageHelper.setSubject(subject);
			messageHelper.setText(text);
			messageHelper.addAttachment("Attachment",new File(classpathFileLoader.getClasspathFileRelativePath(filePath)));
		};
		
		return messagePreparator;
	}
	
}
