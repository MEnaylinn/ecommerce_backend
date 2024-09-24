package com.hostmdy.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.domain.UserShippingAddress;
import com.hostmdy.ecommerce.exception.ProductNotFoundException;
import com.hostmdy.ecommerce.repository.UserShippingAddressRepository;
import com.hostmdy.ecommerce.service.UserShippingAddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserShippingAddressServiceImpl implements UserShippingAddressService{
	
	private final UserShippingAddressRepository userShippingAddressRepository;

	@Override
	public UserShippingAddress save(UserShippingAddress userShippingAddress) {
		// TODO Auto-generated method stub
		return userShippingAddressRepository.save(userShippingAddress);
	}

	@Override
	public UserShippingAddress createUserShippingAddress(UserShippingAddress userShippingAddress, User user) {
		// TODO Auto-generated method stub
		user.getUserShippingAddresses().add(userShippingAddress);
		userShippingAddress.setUser(user);
		return userShippingAddressRepository.save(userShippingAddress);
	}

	@Override
	public Optional<UserShippingAddress> getUserShippingAddressById(Long id) {
		// TODO Auto-generated method stub
		return userShippingAddressRepository.findById(id);
	}

	@Override
	public List<UserShippingAddress> getUserShippingAddressByUser(User user) {
		// TODO Auto-generated method stub
		return userShippingAddressRepository.findByUser(user);
	}

	@Override
	@Transactional
	public void deleteUserShippingAddressById(Long id) {
		// TODO Auto-generated method stub
		Optional<UserShippingAddress> userShippingAddressOpt = getUserShippingAddressById(id);
		
		if(userShippingAddressOpt.isEmpty()) {
			throw new ProductNotFoundException("userShippingAddress with id="+id+" is not found");
		}
		
		UserShippingAddress userShippingAddress = userShippingAddressOpt.get();
		userShippingAddress.setUser(null);
		save(userShippingAddress);
		
		userShippingAddressRepository.deleteById(id);
	}

}
