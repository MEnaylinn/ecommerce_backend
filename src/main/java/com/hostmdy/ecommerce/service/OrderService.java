package com.hostmdy.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.ecommerce.domain.BillingAddress;
import com.hostmdy.ecommerce.domain.Order;
import com.hostmdy.ecommerce.domain.Payment;
import com.hostmdy.ecommerce.domain.ShippingAddress;
import com.hostmdy.ecommerce.domain.User;

public interface OrderService {
	
	Order saveOrder(Order order);
	
	Order createOrder(Order order,Payment payment,BillingAddress billingAddress,ShippingAddress shippingAddress,User user);
	
	Optional<Order> getOrderById(Long id);
	
	List<Order> getAllOrders(User user);
	
	void deleteOrderById(Long id);

}
