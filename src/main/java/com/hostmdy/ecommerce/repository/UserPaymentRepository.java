package com.hostmdy.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.domain.UserPayment;
import java.util.List;


public interface UserPaymentRepository extends CrudRepository<UserPayment,Long>{
	List<UserPayment> findByUser(User user);
}
