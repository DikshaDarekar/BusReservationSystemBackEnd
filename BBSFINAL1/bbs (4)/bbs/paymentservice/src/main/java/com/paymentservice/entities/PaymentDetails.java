package com.paymentservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_details")
@Data
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "payment_datetime", nullable = false)
    private LocalDateTime paymentDatetime=LocalDateTime.now();

    @Column(name = "booking_details_id", nullable = false)
    private long bookingDetailsId;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;


    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
}
