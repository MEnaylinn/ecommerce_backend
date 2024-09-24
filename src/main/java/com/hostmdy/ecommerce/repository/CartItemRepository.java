package com.hostmdy.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.CartItem;
import com.hostmdy.ecommerce.domain.ShoppingCart;
import com.hostmdy.ecommerce.domain.User;

public interface CartItemRepository extends CrudRepository<CartItem,Long>{	
	List<CartItem> findByCart(ShoppingCart cart);
	
	List<CartItem> findByUser(User user);
	
}
