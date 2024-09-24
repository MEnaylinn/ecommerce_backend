package com.hostmdy.ecommerce.service;

import java.util.List;
import java.util.Optional;
import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.domain.UserBillingAddress;
import com.hostmdy.ecommerce.domain.UserPayment;

public interface UserPaymentService {
	
	UserPayment save(UserPayment userPayment);
	
	UserPayment createUserPayment(UserPayment userPayment,UserBillingAddress userBillingAddress,User user);

	List<UserPayment> getAllUserPayments(User user);
	
	Optional<UserPayment> getUserPaymentById(Long id);
	
	void deleteUserPaymentById(Long id);
}
