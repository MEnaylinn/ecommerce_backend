package com.hostmdy.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hostmdy.ecommerce.domain.Product;
import com.hostmdy.ecommerce.exception.ProductNotFoundException;
import com.hostmdy.ecommerce.repository.ProductRepository;
import com.hostmdy.ecommerce.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository productRepository;

	@Override
	public Product save(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return (List<Product>) productRepository.findAll();
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id);
	}

	@Override
	@Transactional
	public void deleteProductById(Long id) {
		// TODO Auto-generated method stub
		Optional<Product> productOpt = productRepository.findById(id);
		
		if(productOpt.isEmpty()) {
			throw new ProductNotFoundException("product with id="+id+" is not found");
		}
		Product product = productOpt.get();
		product.setUser(null);
		save(product);
		productRepository.deleteById(id);
	}

}
