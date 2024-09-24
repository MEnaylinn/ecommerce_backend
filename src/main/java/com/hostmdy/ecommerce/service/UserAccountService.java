package com.hostmdy.ecommerce.service;

public interface UserAccountService {
	void expiredAccount(Long userId);
	
	void lockedAccount(Long userId);
	
	void expiredCredentials(Long userId);
	
	void disabled(Long userId);
}
