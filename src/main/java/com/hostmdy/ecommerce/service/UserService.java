package com.hostmdy.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.domain.security.UserRole;

public interface UserService {

	User save(User user);
	
	User createUser(User user,Set<UserRole> userRoles);
	
	List<User> getAllUsers();
	
	Optional<User> getUserByUsername(String username);
	
	Optional<User> getUserById(Long id);
	
	User updateUserPassword(User user,String oldPassword,String newPassword);
	
}
