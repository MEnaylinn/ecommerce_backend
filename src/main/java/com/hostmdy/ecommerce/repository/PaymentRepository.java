package com.hostmdy.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.ecommerce.domain.Payment;

public interface PaymentRepository extends CrudRepository<Payment,Long>{

}
