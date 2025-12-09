package com.example.ecomm.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecomm.CRUD.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}