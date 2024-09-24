package com.hostmdy.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.domain.UserShippingAddress;

public interface UserShippingAddressService {
	
	UserShippingAddress save(UserShippingAddress userShippingAddress);
	UserShippingAddress createUserShippingAddress(UserShippingAddress userShippingAddress,User user);
	Optional<UserShippingAddress> getUserShippingAddressById(Long id);
	List<UserShippingAddress> getUserShippingAddressByUser(User user);
	void deleteUserShippingAddressById(Long id);
}
