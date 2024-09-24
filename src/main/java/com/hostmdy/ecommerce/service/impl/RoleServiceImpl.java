package com.hostmdy.ecommerce.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.ecommerce.domain.security.Role;
import com.hostmdy.ecommerce.repository.RoleRepository;
import com.hostmdy.ecommerce.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
	
	private final RoleRepository roleRepository;

	@Override
	public Optional<Role> getRoleByName(String name) {
		// TODO Auto-generated method stub
		return roleRepository.findByName(name);
	}
	
	

}
