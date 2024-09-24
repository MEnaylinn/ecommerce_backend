package com.hostmdy.ecommerce.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> userOpt = userRepository.findByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username = "+username+" is not found");
		}
		
		return userOpt.get();
	}
	
	public UserDetails loadUserById(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with id = "+id+" is not found");
		}
		
		return userOpt.get();
	}

}
