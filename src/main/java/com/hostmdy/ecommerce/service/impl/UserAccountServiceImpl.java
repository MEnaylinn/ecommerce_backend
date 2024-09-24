package com.hostmdy.ecommerce.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.repository.UserRepository;
import com.hostmdy.ecommerce.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService{
	
	private final UserRepository userRepository;
	
	private User getUser(Long userId) {
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with id="+userId+" is not found");
		}
		
		return userOpt.get();
	}

	@Override
	public void expiredAccount(Long userId) {
		// TODO Auto-generated method stub
		User user = getUser(userId);
		user.setAccountNonExpired(false);
		userRepository.save(user);
	}

	@Override
	public void lockedAccount(Long userId) {
		// TODO Auto-generated method stub
		User user = getUser(userId);
		user.setAccountNonLocked(false);
		userRepository.save(user);
	}

	@Override
	public void expiredCredentials(Long userId) {
		// TODO Auto-generated method stub
		User user = getUser(userId);
		user.setCrendentialsNonExpired(false);
		userRepository.save(user);
	}

	@Override
	public void disabled(Long userId) {
		// TODO Auto-generated method stub
		User user = getUser(userId);
		user.setEnabled(false);
		userRepository.save(user);
	}

}
