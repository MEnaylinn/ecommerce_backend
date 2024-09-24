package com.hostmdy.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.Product;
import com.hostmdy.ecommerce.domain.Review;
import java.util.List;


public interface ReviewRepository extends CrudRepository<Review,Long>{
	
	List<Review> findByProduct(Product product);
	
}
