package com.hostmdy.ecommerce.service;

import java.util.Optional;

import com.hostmdy.ecommerce.domain.security.Role;

public interface RoleService {
	Optional<Role> getRoleByName(String name);
}
