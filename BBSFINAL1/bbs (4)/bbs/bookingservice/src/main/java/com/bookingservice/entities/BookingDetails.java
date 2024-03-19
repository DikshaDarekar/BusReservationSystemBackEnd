package com.bookingservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "booking_details")
@Data
public class BookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "seats_to_book",nullable = false)
    private int seatsToBook;

    @Column(name = "booked_by",nullable = false)
    private long bookedBy;

    @Column(name = "bus_id",nullable = false)
    private long busId;

    @Column(name = "total_amount",nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "booked_at",nullable = false)
    private LocalDateTime bookedAt=LocalDateTime.now();

}
