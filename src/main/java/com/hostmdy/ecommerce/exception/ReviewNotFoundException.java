package com.hostmdy.ecommerce.exception;

public class ReviewNotFoundException extends RuntimeException{

		/**
	 * 
	 */
	private static final long serialVersionUID = 902621273623051149L;

		public ReviewNotFoundException(String message) {
			super(message);
		}
}
