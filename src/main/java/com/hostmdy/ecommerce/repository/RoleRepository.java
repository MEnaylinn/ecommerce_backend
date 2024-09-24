package com.hostmdy.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role,Long>{
	
	Optional<Role> findByName(String name);
}
