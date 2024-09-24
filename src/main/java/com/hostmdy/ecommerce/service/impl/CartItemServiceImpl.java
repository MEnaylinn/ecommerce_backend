package com.hostmdy.ecommerce.service.impl;

import java.io.Console;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hostmdy.ecommerce.domain.CartItem;
import com.hostmdy.ecommerce.domain.Product;
import com.hostmdy.ecommerce.domain.ProductToCartItem;
import com.hostmdy.ecommerce.domain.ShoppingCart;
import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.exception.CartItemNotFoundException;
import com.hostmdy.ecommerce.exception.ProductNotFoundException;
import com.hostmdy.ecommerce.repository.CartItemRepository;
import com.hostmdy.ecommerce.repository.ProductRepository;
import com.hostmdy.ecommerce.repository.ProductToCartItemRepository;
import com.hostmdy.ecommerce.repository.UserRepository;
import com.hostmdy.ecommerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemServiceImpl implements CartItemService{
	private final CartItemRepository cartItemRepository;
	private final ProductToCartItemRepository productToCartItemRepository;
	private final ProductRepository productRepository;
	

	@Override
	public CartItem save(CartItem cartItem) {
		// TODO Auto-generated method stub
		return cartItemRepository.save(cartItem);
	}

	@Override
	@Transactional
	public CartItem createCartItem(Long productId,ShoppingCart cart) {
		// TODO Auto-generated method stub
		Optional<Product> productOpt = productRepository.findById(productId);
		
		if(productOpt.isEmpty()) {
			throw new ProductNotFoundException("product with id="+productId+" is not found");
		}
		
		Product product = productOpt.get();
		
		List<CartItem> cartItems = getAllCartItemsByCart(cart);
		
		CartItem existedCartItem = null;
		for(final CartItem cartItem : cartItems) {
			if(cartItem.getProduct().getId() == product.getId()) {
				existedCartItem = cartItem;
				break;
			}
		}
		
		if(existedCartItem != null) {
			existedCartItem.setQuantity(existedCartItem.getQuantity()+1);
			existedCartItem.setSubTotal(existedCartItem.getQuantity() * existedCartItem.getProduct().getPrice());
			save(existedCartItem);
		}else {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setQuantity(1);
			cartItem.setSubTotal(product.getPrice());
			cartItem.setCart(cart);
			existedCartItem = save(cartItem);
			ProductToCartItem productToCartItem = new ProductToCartItem(product, existedCartItem);
			System.out.println("product to cart item is :"+productToCartItem);
			productToCartItemRepository.save(productToCartItem);
		}
		return existedCartItem;
	}

	@Override
	public Optional<CartItem> getCartItemById(Long cartItemId) {
		// TODO Auto-generated method stub
		return cartItemRepository.findById(cartItemId);
	}

	@Override
	@Transactional
	public void deleteCartItemById(Long cartItemId) {
		Optional<CartItem> cartItemOpt = getCartItemById(cartItemId);
		
		if(cartItemOpt.isEmpty()) {
			throw new CartItemNotFoundException("cartItem with id="+cartItemId+" is not found");
		}
		
		CartItem cartItem = cartItemOpt.get();
		cartItem.setCart(null);
		cartItem.setProduct(null);
		save(cartItem);
		
		cartItemRepository.deleteById(cartItemId);
	}

	@Override
	public List<CartItem> getAllCartItemsByCart(ShoppingCart cart) {
		// TODO Auto-generated method stub
		return cartItemRepository.findByCart(cart);
	}

	@Override
	public List<CartItem> getAllCartItemsByUser(User user) {
		// TODO Auto-generated method stub
		return cartItemRepository.findByUser(user);
	}
	
	

}
