package com.hostmdy.ecommerce.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter 
public class UsernameExceptionResponse {
	private final String username;
}
