package com.example.miniProject.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_logs")
public class PaymentLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(length = 1000)
    private String communicationDetails;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public String getCommunicationDetails() { return communicationDetails; }
    public void setCommunicationDetails(String communicationDetails) { this.communicationDetails = communicationDetails; }
}
