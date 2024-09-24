package com.hostmdy.ecommerce.payload;

import com.hostmdy.ecommerce.domain.UserBillingAddress;
import com.hostmdy.ecommerce.domain.UserPayment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserPaymentRequest {
	private UserPayment userPayment;
	private UserBillingAddress userBillingAddress;
}
