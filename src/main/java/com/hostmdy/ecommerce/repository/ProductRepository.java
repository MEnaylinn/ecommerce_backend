package com.hostmdy.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.Product;

public interface ProductRepository extends CrudRepository<Product,Long>{
	
}
