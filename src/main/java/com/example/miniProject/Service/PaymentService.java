package com.example.miniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.miniProject.Entity.Order;
import com.example.miniProject.Entity.Payment;
import com.example.miniProject.Entity.PaymentLog;
import com.example.miniProject.repository.PaymentLogRepository;
import com.example.miniProject.repository.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private PaymentLogRepository  logRepo;
	
	@Transactional
	public Payment processPayment(Order order, int amount) {
		
		Payment payment = new Payment();
		payment.setOrder(order);
		payment.setAmount(amount);
		payment.setStatus("PAID");
		payment  = paymentRepo.save(payment);
		
		PaymentLog log = new PaymentLog();
		log.setPayment(payment);
		log.setCommunicationDetails("Payment Successful for amount " + amount);
		logRepo.save(log);
		return payment;
	}
	
}
