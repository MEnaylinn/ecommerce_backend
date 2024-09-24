package com.hostmdy.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.ecommerce.domain.BillingAddress;
import com.hostmdy.ecommerce.domain.Order;
import com.hostmdy.ecommerce.domain.Payment;
import com.hostmdy.ecommerce.domain.ShippingAddress;
import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.exception.OrderNotFoundException;
import com.hostmdy.ecommerce.repository.OrderRepository;
import com.hostmdy.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;
	
	@Override
	public Order saveOrder(Order order) {
		// TODO Auto-generated method stub
		return orderRepository.save(order);
	}

	@Override
	public Order createOrder(Order order, Payment payment, BillingAddress billingAddress,
			ShippingAddress shippingAddress, User user) {
		// TODO Auto-generated method stub
		order.setUser(user);
		payment.setBillingAddress(billingAddress);
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		
		return saveOrder(order);
	}

	@Override
	public Optional<Order> getOrderById(Long id) {
		// TODO Auto-generated method stub
		return orderRepository.findById(id);
	}

	@Override
	public List<Order> getAllOrders(User user) {
		// TODO Auto-generated method stub
		return orderRepository.findByUser(user);
	}

	@Override
	public void deleteOrderById(Long id) {
		// TODO Auto-generated method stub
		Optional<Order> orderOpt = getOrderById(id);
		if(orderOpt.isEmpty()) {
			throw new OrderNotFoundException("order with id="+id+" is not found");
		}
		
		Order order = orderOpt.get();
		order.setUser(null);
		saveOrder(order);
		
		orderRepository.deleteById(id);
	}

}
