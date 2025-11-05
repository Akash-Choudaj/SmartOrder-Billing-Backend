package com.example.miniProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.miniProject.Entity.PaymentLog;

public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {

}
