package com.hostmdy.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.User;

public interface UserRepository extends CrudRepository<User,Long>{

	Optional<User> findByUsername(String username);
	
}
