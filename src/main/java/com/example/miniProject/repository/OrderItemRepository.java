package com.example.miniProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.miniProject.Entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
