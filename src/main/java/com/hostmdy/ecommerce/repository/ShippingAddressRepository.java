package com.hostmdy.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.ShippingAddress;

public interface ShippingAddressRepository extends CrudRepository<ShippingAddress,Long>{

}
