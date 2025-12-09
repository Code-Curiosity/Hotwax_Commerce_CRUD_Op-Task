package com.example.ecomm.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecomm.CRUD.entity.OrderHeader;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Integer> {
}