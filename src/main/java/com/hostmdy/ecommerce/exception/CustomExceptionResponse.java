package com.hostmdy.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionResponse {
	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<UsernameExceptionResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex){
		UsernameExceptionResponse response = new UsernameExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<NotFoundExceptionResponse> handleRoleNotFoundException(RoleNotFoundException ex){
		NotFoundExceptionResponse response = new NotFoundExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<NotFoundExceptionResponse> handleProductNotFoundException(ProductNotFoundException ex){
		NotFoundExceptionResponse response = new NotFoundExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(CartItemNotFoundException.class)
	public ResponseEntity<NotFoundExceptionResponse> handleCartItemNotFoundException(CartItemNotFoundException ex){
		NotFoundExceptionResponse response = new NotFoundExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(ShoppingCartNotFoundException.class)
	public ResponseEntity<NotFoundExceptionResponse> handleShoppingCartNotFoundException(ShoppingCartNotFoundException ex){
		NotFoundExceptionResponse response = new NotFoundExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(UserShippingAddressNotFoundException.class)
	public ResponseEntity<NotFoundExceptionResponse> handleUserShippingAddressNotFoundException(UserShippingAddressNotFoundException ex){
		NotFoundExceptionResponse response = new NotFoundExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(UserPaymentNotFoundException.class)
	public ResponseEntity<NotFoundExceptionResponse> handleUserPaymentNotFoundException(UserPaymentNotFoundException ex){
		NotFoundExceptionResponse response = new NotFoundExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(UserBillingAddressNotFoundException.class)
	public ResponseEntity<NotFoundExceptionResponse> handleUserBillingAddressNotFoundException(UserBillingAddressNotFoundException ex){
		NotFoundExceptionResponse response = new NotFoundExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<UsernameExceptionResponse> handleUsernameNotFoundException(UsernameNotFoundException ex){
		UsernameExceptionResponse response = new UsernameExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(ClaimsNullException.class)
	public ResponseEntity<ClaimsNullExceptionResponse> handleClaimsNullException(ClaimsNullException ex){
		ClaimsNullExceptionResponse response = new ClaimsNullExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(ReviewNotFoundException.class)
	public ResponseEntity<NotFoundExceptionResponse> handleReviewNotFoundException(ReviewNotFoundException ex){
		NotFoundExceptionResponse response = new NotFoundExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(UserNotMatchException.class)
	public ResponseEntity<UserNotMatchExceptionResponse> handleUserNotMatchException(UserNotMatchException ex){
		UserNotMatchExceptionResponse response = new UserNotMatchExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(AlreadyReviewedException.class)
	public ResponseEntity<AlreadyReviewedExceptionResponse> handleUserNotMatchException(AlreadyReviewedException ex){
		AlreadyReviewedExceptionResponse response = new AlreadyReviewedExceptionResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}
