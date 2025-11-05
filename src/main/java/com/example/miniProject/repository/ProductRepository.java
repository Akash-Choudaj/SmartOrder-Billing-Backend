package com.example.miniProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.miniProject.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
