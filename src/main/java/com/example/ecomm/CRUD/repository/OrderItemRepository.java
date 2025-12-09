package com.example.ecomm.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecomm.CRUD.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}