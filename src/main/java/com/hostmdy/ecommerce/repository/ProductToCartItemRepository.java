package com.hostmdy.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.ProductToCartItem;

public interface ProductToCartItemRepository extends CrudRepository<ProductToCartItem,Long>{

}
