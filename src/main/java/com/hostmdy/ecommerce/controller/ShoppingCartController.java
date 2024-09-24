package com.hostmdy.ecommerce.controller;

import java.security.Principal;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hostmdy.ecommerce.domain.ShoppingCart;
import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.service.ShoppingCartService;
import com.hostmdy.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class ShoppingCartController {

	private final ShoppingCartService shoppingCartService;
	private final UserService userService;
		
	@DeleteMapping("/empty")
	public ResponseEntity<String> emptyShoppingCart(Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		shoppingCartService.emptyCart(userOpt.get().getCart(),null);
		
		return ResponseEntity.status(HttpStatus.OK).body("shopping cart is now empty");
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ShoppingCart> updateShoppingCart(Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		ShoppingCart cart = shoppingCartService.updateCart(userOpt.get().getCart());
		
		return ResponseEntity.status(HttpStatus.OK).body(cart);
	}
		
}
