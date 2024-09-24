package com.hostmdy.ecommerce.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
public class AlreadyReviewedExceptionResponse {
	private final String message;
}
