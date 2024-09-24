package com.hostmdy.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.domain.UserShippingAddress;
import java.util.List;

public interface UserShippingAddressRepository extends CrudRepository<UserShippingAddress,Long>{
	List<UserShippingAddress> findByUser(User user);
}
