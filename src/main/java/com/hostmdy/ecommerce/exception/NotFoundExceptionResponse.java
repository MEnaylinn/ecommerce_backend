package com.hostmdy.ecommerce.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
public class NotFoundExceptionResponse {
	private final String message;
}
