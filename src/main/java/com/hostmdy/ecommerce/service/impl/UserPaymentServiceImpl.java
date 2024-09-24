package com.hostmdy.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.domain.UserBillingAddress;
import com.hostmdy.ecommerce.domain.UserPayment;
import com.hostmdy.ecommerce.exception.ProductNotFoundException;
import com.hostmdy.ecommerce.repository.UserPaymentRepository;
import com.hostmdy.ecommerce.service.UserPaymentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPaymentServiceImpl implements UserPaymentService{
	private final UserPaymentRepository userPaymentRepository;
	
	@Override
	public UserPayment save(UserPayment userPayment) {
		// TODO Auto-generated method stub
		return userPaymentRepository.save(userPayment);
	}

	@Override
	public UserPayment createUserPayment(UserPayment userPayment, UserBillingAddress userBillingAddress, User user) {
		// TODO Auto-generated method stub
		userPayment.setUserBillingAddress(userBillingAddress);
		userPayment.setUser(user);
		return save(userPayment);
	}

	@Override
	public List<UserPayment> getAllUserPayments(User user) {
		// TODO Auto-generated method stub
		return userPaymentRepository.findByUser(user);
	}

	@Override
	public Optional<UserPayment> getUserPaymentById(Long id) {
		// TODO Auto-generated method stub
		return userPaymentRepository.findById(id);
	}

	@Override
	@Transactional
	public void deleteUserPaymentById(Long id) {
		// TODO Auto-generated method stub
		Optional<UserPayment> userPaymentOpt = getUserPaymentById(id);
		if(userPaymentOpt.isEmpty()) {
			throw new ProductNotFoundException("userPayment with id="+id+" is not found");
		}
		UserPayment userPayment = userPaymentOpt.get();
		userPayment.setUser(null);
		save(userPayment);
		userPaymentRepository.deleteById(id);
	}

}
