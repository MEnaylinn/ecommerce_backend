package com.hostmdy.ecommerce.service;

import com.hostmdy.ecommerce.payload.AttachmentEmailRequest;
import com.hostmdy.ecommerce.payload.SimpleEmailRequest;

public interface EmailService {
	
	void sendEmail(SimpleEmailRequest email);
	
	void sendOrderConfirmEmail(Long orderId);
	
	void sendAttachmentEmail(AttachmentEmailRequest email);

}
