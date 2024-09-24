package com.hostmdy.ecommerce.service.impl;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.hostmdy.ecommerce.domain.CartItem;
import com.hostmdy.ecommerce.domain.Order;
import com.hostmdy.ecommerce.domain.ShoppingCart;
import com.hostmdy.ecommerce.repository.ShoppingCartRepository;
import com.hostmdy.ecommerce.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService{
	
	private final ShoppingCartRepository shoppingCartRepository;
	
	@Override
	public void emptyCart(ShoppingCart cart,Order order) {
		Set<CartItem> cartItems = cart.getCartItems();
		
		cartItems.forEach(ct -> {
			ct.setCart(null);
			ct.setOrder(order);
			ct.setUser(order.getUser());
		});
		shoppingCartRepository.save(cart);
	}

	@Override
	public ShoppingCart updateCart(ShoppingCart cart) {
		Set<CartItem> cartItems = cart.getCartItems();
		Double sumSubTotal = cartItems.stream()
				.collect(Collectors.summingDouble(c -> c.getSubTotal()));
		cart.setGrandTotal(new BigDecimal(sumSubTotal));
		return shoppingCartRepository.save(cart);
	}
	
}
