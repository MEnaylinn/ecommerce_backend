package com.hostmdy.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.ecommerce.domain.Product;

public interface ProductService {
	Product save(Product product);
	
	List<Product> getAllProduct();
	
	Optional<Product> getProductById(Long id);
	
	void deleteProductById(Long id);
}
