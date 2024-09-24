package com.hostmdy.ecommerce.payload;

import com.hostmdy.ecommerce.domain.BillingAddress;
import com.hostmdy.ecommerce.domain.Payment;
import com.hostmdy.ecommerce.domain.ShippingAddress;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class OrderRequest {
	
	private ShippingAddress shippingAddress;
	private Payment payment;
	private BillingAddress billingAddress;

}
