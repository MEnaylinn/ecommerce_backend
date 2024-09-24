package com.hostmdy.ecommerce.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

import com.hostmdy.ecommerce.domain.Order;
import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.exception.ProductNotFoundException;
import com.hostmdy.ecommerce.payload.OrderRequest;
import com.hostmdy.ecommerce.service.OrderService;
import com.hostmdy.ecommerce.service.ShoppingCartService;
import com.hostmdy.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
	
	private final OrderService orderService;
	private final UserService userService;
	private final ShoppingCartService shoppingCartService;
	
	@PostMapping("/create")
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest,Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		Order order = new Order();
		order.setOrderNo(UUID.randomUUID().toString());
		User user = userOpt.get();
//		System.out.println(orderRequest.getShippingAddress());
//		System.out.println(orderRequest.getShippingAddress());
////		System.out.println(orderRequest.getPayment());
		
		Order createdOrder = orderService.createOrder(order,orderRequest.getPayment(),orderRequest.getBillingAddress(),orderRequest.getShippingAddress(),user);
		shoppingCartService.emptyCart(user.getCart(),order);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
	}
	
	@PutMapping("/{orderId}/update")
	public ResponseEntity<Order> updateOrder(@RequestBody OrderRequest orderRequest,@PathVariable Long orderId,Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		Optional<Order> orderOpt = orderService.getOrderById(orderId);
		
		if(orderOpt.isEmpty()) {
			throw new ProductNotFoundException("order with id="+orderId+" is not found");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderOpt.get(),orderRequest.getPayment(),orderRequest.getBillingAddress(),orderRequest.getShippingAddress(),userOpt.get()));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Order>> getAllOrders(Principal principal){
		System.out.println("... get all orders");
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders(userOpt.get()));
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<String> deleteOrderById(@PathVariable Long orderId){
		orderService.deleteOrderById(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(orderId.toString());
				
	}
}
