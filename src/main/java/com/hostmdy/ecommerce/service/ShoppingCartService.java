package com.hostmdy.ecommerce.service;

import com.hostmdy.ecommerce.domain.Order;
import com.hostmdy.ecommerce.domain.ShoppingCart;

public interface ShoppingCartService {
	
	void emptyCart(ShoppingCart cart,Order order);
	
	ShoppingCart updateCart(ShoppingCart cart);

}
