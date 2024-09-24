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
import com.hostmdy.ecommerce.domain.UserShippingAddress;
import com.hostmdy.ecommerce.exception.ProductNotFoundException;
import com.hostmdy.ecommerce.service.UserService;
import com.hostmdy.ecommerce.service.UserShippingAddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/shipping")
public class UserShippingAddressController {
	
	private final UserShippingAddressService userShippingAddressService;
	private final UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createUserShippingAddress(@RequestBody UserShippingAddress userShippingAddress,Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		UserShippingAddress createdUserShippingAddress = userShippingAddressService.createUserShippingAddress(userShippingAddress,userOpt.get());
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUserShippingAddress);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateUserShippingAddress(@RequestBody UserShippingAddress userShippingAddress,Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		userShippingAddress.setUser(userOpt.get());
		return ResponseEntity.status(HttpStatus.OK).body(userShippingAddressService.save(userShippingAddress));
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getShippingAddressesByUser(Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		List<UserShippingAddress> userShippingAddresses = userShippingAddressService.getUserShippingAddressByUser(userOpt.get());
		return ResponseEntity.status(HttpStatus.OK).body(userShippingAddresses);
	}
	
	@GetMapping("/{shippingId}")
	public ResponseEntity<?> getShippingAddressById(@PathVariable Long shippingId){
		Optional<UserShippingAddress> shippingAddressOpt = userShippingAddressService.getUserShippingAddressById(shippingId);
		
		if(shippingAddressOpt.isEmpty()) {
			throw new ProductNotFoundException("userShippingAddress with id="+shippingId+" is not found");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(shippingAddressOpt.get());
	}
	
	@DeleteMapping("/{userShippingAddressId}/delete")
	public ResponseEntity<String> deleteUserShippingAddress(@PathVariable Long userShippingAddressId){
		userShippingAddressService.deleteUserShippingAddressById(userShippingAddressId);
		
		return ResponseEntity.status(HttpStatus.OK).body(userShippingAddressId.toString());
		
	}

}
