package com.hostmdy.ecommerce.controller;


import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostmdy.ecommerce.domain.CartItem;
import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.exception.CartItemNotFoundException;
import com.hostmdy.ecommerce.service.CartItemService;
import com.hostmdy.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/cart-item")
public class CartItemController {
	private final CartItemService cartItemService;
	private final UserService userService;
	
	@PostMapping("/add/{productId}/{username}")
	public ResponseEntity<?> createCartItem(@PathVariable Long productId,@PathVariable String username){
//		String username = principal.getName();
		System.out.println("...principal.getname is :"+username);

		Optional<User> userOpt = userService.getUserByUsername(username);
		System.out.println("principal.getname is :"+userOpt.get());
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.createCartItem(productId,userOpt.get().getCart()));
	}
	
	@DeleteMapping("/{cartItemId}/delete")
	public ResponseEntity<String> deleteCartItem(@PathVariable Long cartItemId){
		cartItemService.deleteCartItemById(cartItemId);
		
		return ResponseEntity.status(HttpStatus.OK).body(cartItemId.toString());
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CartItem>> getAllCartItems(Principal principal){
		String username = principal.getName();
		System.out.println("principal.getname is all :"+username);
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
//		List<CartItem> allCartItems = cartItemService.getAllCartItemsByCart(userOpt.get().getCart());
//		
//		List<CartItem> pendingCartItems =allCartItems.stream().filter(cartItem -> cartItem.getOrder()== null).collect(Collectors.toList());
//		
//		if(pendingCartItems.isEmpty()) {
//			throw new UsernameNotFoundException("No pendingCartItems");
//		}
		
			return ResponseEntity.status(HttpStatus.OK).body(cartItemService.getAllCartItemsByCart(userOpt.get().getCart()));
	}
	
	@GetMapping("/allOrderedItem/{username}")
	public ResponseEntity<List<CartItem>> getAllOrderedItem(@PathVariable String username, Principal principal){
//		String username = principal.getName();
		System.out.println("principal.getname is all :"+username);
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		System.out.println("...order item is"+cartItemService.getAllCartItemsByUser(userOpt.get()));
			return ResponseEntity.status(HttpStatus.OK).body(cartItemService.getAllCartItemsByUser(userOpt.get()));
	}
	
	@PatchMapping("/update/{cartItemId}/{quantity}")
	public ResponseEntity<?> updateCartItem(@PathVariable Long cartItemId,@PathVariable Integer quantity){
		Optional<CartItem> cartItemOpt = cartItemService.getCartItemById(cartItemId);
		
		if(cartItemOpt.isEmpty()) {
			throw new CartItemNotFoundException("cartItem with id="+cartItemId+" is not found");
		}
		
		CartItem cartItem = cartItemOpt.get();
		cartItem.setQuantity(quantity);
		cartItem.setSubTotal(cartItem.getProduct().getPrice() * quantity);
		CartItem updatedCartItem = cartItemService.save(cartItem);
		return ResponseEntity.status(HttpStatus.OK).body(updatedCartItem);
	}
}
