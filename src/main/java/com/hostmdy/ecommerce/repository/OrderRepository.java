package com.hostmdy.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.Order;
import com.hostmdy.ecommerce.domain.User;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long>{
	
	List<Order> findByUser(User user);
}
