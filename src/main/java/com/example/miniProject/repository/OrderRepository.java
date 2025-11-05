package com.example.miniProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.miniProject.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
