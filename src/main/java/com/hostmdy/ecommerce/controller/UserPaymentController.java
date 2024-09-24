package com.hostmdy.ecommerce.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.domain.UserPayment;
import com.hostmdy.ecommerce.exception.ProductNotFoundException;
import com.hostmdy.ecommerce.payload.UserPaymentRequest;
import com.hostmdy.ecommerce.service.UserPaymentService;
import com.hostmdy.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class UserPaymentController {
	private final UserPaymentService userPaymentService;
	private final UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createUserPayment(@RequestBody UserPaymentRequest userPaymentRequest,Principal principal){
		System.out.println("payment created");
		System.out.println(userPaymentRequest);
		System.out.println(userPaymentRequest.getUserBillingAddress());
		System.out.println(userPaymentRequest.getUserPayment());
		System.out.println(userPaymentRequest.getUserPayment().getCardNo());
		System.out.println(userPaymentRequest.getUserPayment().getCvv());
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		UserPayment createdUserPayment = userPaymentService.createUserPayment(userPaymentRequest.getUserPayment(),userPaymentRequest.getUserBillingAddress(),userOpt.get());
		System.out.println(createdUserPayment);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUserPayment);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateUserShippingAddress(@RequestBody UserPaymentRequest userPaymentRequest,Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		UserPayment userPayment = userPaymentRequest.getUserPayment();
		userPayment.setUser(userOpt.get());
		userPayment.setUserBillingAddress(userPaymentRequest.getUserBillingAddress());
		
		return ResponseEntity.status(HttpStatus.OK).body(userPaymentService.save(userPayment));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<UserPayment>> getShippingAddressesByUser(Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		System.out.println("....all payment is ");
		System.out.println(userOpt);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		List<UserPayment> userPayments = userPaymentService.getAllUserPayments(userOpt.get());
		System.out.println(userPayments);
		return ResponseEntity.status(HttpStatus.OK).body(userPayments);
	}
	
	@GetMapping("/{paymentId}")
	public ResponseEntity<?> getShippingAddressById(@PathVariable Long paymentId){
		Optional<UserPayment> userPaymentOpt = userPaymentService.getUserPaymentById(paymentId);
		
		if(userPaymentOpt.isEmpty()) {
			throw new ProductNotFoundException("userPayment with id="+paymentId+" is not found");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(userPaymentOpt.get());
	}
	
	@DeleteMapping("/{paymentId}/delete")
	public ResponseEntity<String> deleteUserShippingAddress(@PathVariable Long paymentId){
		userPaymentService.deleteUserPaymentById(paymentId);
		
		return ResponseEntity.status(HttpStatus.OK).body(paymentId.toString());
		
	}
}
