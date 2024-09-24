package com.hostmdy.ecommerce.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hostmdy.ecommerce.domain.ShoppingCart;
import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.domain.security.UserRole;
import com.hostmdy.ecommerce.exception.UsernameAlreadyExistsException;
import com.hostmdy.ecommerce.repository.RoleRepository;
import com.hostmdy.ecommerce.repository.UserRepository;
import com.hostmdy.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return (List<User>) userRepository.findAll();
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional
	public User createUser(User user, Set<UserRole> userRoles) {
		// TODO Auto-generated method stub
		Optional<User> existedUserOpt = getUserByUsername(user.getUsername());
		
		if(existedUserOpt.isPresent()) {
			throw new UsernameAlreadyExistsException("User with email="+user.getUsername()+" already exists");
		}
		
		userRoles.forEach((ur) -> roleRepository.save(ur.getRole()));
		user.setUserRoles(userRoles);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		ShoppingCart cart = new ShoppingCart();
		cart.setGrandTotal(new BigDecimal(0.0));
		user.setCart(cart);
		return save(user);
	}

	@Override
	public Optional<User> getUserById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	@Override
	public User updateUserPassword(User user, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		Optional<User> userOpt = getUserById(user.getId());
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("User is not found");

		}
		
		User oldUser = userOpt.get();
		System.out.println(oldUser.getPassword());
		System.out.println(passwordEncoder.encode("apple1234"));
		System.out.println(passwordEncoder.encode(oldPassword));
		
		if(!passwordEncoder.matches(oldPassword, oldUser.getPassword())) {
			throw new UsernameNotFoundException("Password is not correct");
		}
		
		oldUser.setPassword(passwordEncoder.encode(newPassword));
		return save(oldUser);
	}

}
