package com.example.miniProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.miniProject.Entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
