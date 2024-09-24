package com.hostmdy.ecommerce.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostmdy.ecommerce.service.MapValidationErrorService;
import com.hostmdy.ecommerce.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
@CrossOrigin(origins = "http://localhost:3000")
public class UserAccountController {
	private final UserAccountService userAccountService;
	
	@PatchMapping("/expire/{userId}")
	public ResponseEntity<String> expireAccount(@PathVariable Long userId){
		userAccountService.expiredAccount(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body("user with account id="+userId+" is expired");
	}
	
	@PatchMapping("/lock/{userId}")
	public ResponseEntity<String> lockAccount(@PathVariable Long userId){
		userAccountService.expiredAccount(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body("user with account id="+userId+" is locked");
	}
	
	@PatchMapping("/credentials/{userId}")
	public ResponseEntity<String> expireCredentials(@PathVariable Long userId){
		userAccountService.expiredAccount(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body("user with account id="+userId+" is expired credentials");
	}
	
	@PatchMapping("/disable/{userId}")
	public ResponseEntity<String> disableAccount(@PathVariable Long userId){
		userAccountService.expiredAccount(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body("user with account id="+userId+" is disabled");
	
	}
	
}
