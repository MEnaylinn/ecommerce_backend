package com.hostmdy.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.ecommerce.domain.CartItem;
import com.hostmdy.ecommerce.domain.ShoppingCart;
import com.hostmdy.ecommerce.domain.User;

public interface CartItemService {
	CartItem save(CartItem cartItem);
	
	CartItem createCartItem(Long productId,ShoppingCart cart);
	
	Optional<CartItem> getCartItemById(Long cartItemId);
	
	void deleteCartItemById(Long cartItemId);
	
	List<CartItem> getAllCartItemsByCart(ShoppingCart cart);
	
	List<CartItem> getAllCartItemsByUser(User user);
	

}
