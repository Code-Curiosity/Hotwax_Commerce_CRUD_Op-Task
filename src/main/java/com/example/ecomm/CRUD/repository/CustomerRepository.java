package com.example.ecomm.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecomm.CRUD.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
