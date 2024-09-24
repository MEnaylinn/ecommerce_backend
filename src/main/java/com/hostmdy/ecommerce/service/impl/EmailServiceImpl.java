package com.hostmdy.ecommerce.service.impl;

import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.hostmdy.ecommerce.domain.Order;
import com.hostmdy.ecommerce.exception.OrderNotFoundException;
import com.hostmdy.ecommerce.payload.AttachmentEmailRequest;
import com.hostmdy.ecommerce.payload.SimpleEmailRequest;
import com.hostmdy.ecommerce.repository.OrderRepository;
import com.hostmdy.ecommerce.service.EmailService;
import com.hostmdy.ecommerce.utility.MailConstructor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
	
	private final JavaMailSender sender;
	private final MailConstructor mailConstructor;
	private final OrderRepository orderRepository;
	private final Environment env;

	@Override
	public void sendEmail(SimpleEmailRequest email) {
		// TODO Auto-generated method stub
		SimpleMailMessage mail = mailConstructor.constructSimpleMail(email.getTo(),email.getSubject(),email.getText());
		sender.send(mail);
	}

	@Override
	public void sendOrderConfirmEmail(Long orderId) {
		// TODO Auto-generated method stub
		Optional<Order> orderOpt = orderRepository.findById(orderId);
		
		if(orderOpt.isEmpty()) {
			throw new OrderNotFoundException("order with id="+orderId+" is not found");
		}
		Order order = orderOpt.get();
		
		MimeMessagePreparator mail = mailConstructor.constructTemplateMail(order.getUser().getUsername(),env.getProperty("order_email_subject"),order);
		sender.send(mail);
	}

	@Override
	public void sendAttachmentEmail(AttachmentEmailRequest email) {
		// TODO Auto-generated method stub
		MimeMessagePreparator mail = mailConstructor.constructAttachmentMail(email.getTo(),email.getSubject(),email.getFilePath(),email.getText());
		sender.send(mail);
	}

}
