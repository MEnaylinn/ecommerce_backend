package com.hostmdy.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart,Long>{

}
