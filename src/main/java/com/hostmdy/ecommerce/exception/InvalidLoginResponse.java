package com.hostmdy.ecommerce.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InvalidLoginResponse {
	
	private String username;
	private String password;
	
	public InvalidLoginResponse() {
		username = "username is invalid";
		password = "password is invalid";
	}
}
