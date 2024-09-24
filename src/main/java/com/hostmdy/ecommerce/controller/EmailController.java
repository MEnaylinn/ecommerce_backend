package com.hostmdy.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hostmdy.ecommerce.payload.AttachmentEmailRequest;
import com.hostmdy.ecommerce.payload.SimpleEmailRequest;
import com.hostmdy.ecommerce.service.EmailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:3000")
public class EmailController {
	private final EmailService emailService;
	
	@PostMapping("/contact")
	public ResponseEntity<String> sendEmail(@RequestBody SimpleEmailRequest emailRequest){
		emailService.sendEmail(emailRequest);
		
		return ResponseEntity.ok("Email Sent");
	}
	
	@PostMapping("/order/{orderId}")
	public ResponseEntity<String> sendOrderConfirmEmail(@PathVariable Long orderId){
		emailService.sendOrderConfirmEmail(orderId);
		
		return ResponseEntity.ok("Email Sent");
	}
	
	@PostMapping("/attachment")
	public ResponseEntity<String> sendAttachmentEmail(@RequestBody AttachmentEmailRequest emailRequest){
		emailService.sendAttachmentEmail(emailRequest);
		
		return ResponseEntity.ok("Email Sent");
	}
	
	

}
