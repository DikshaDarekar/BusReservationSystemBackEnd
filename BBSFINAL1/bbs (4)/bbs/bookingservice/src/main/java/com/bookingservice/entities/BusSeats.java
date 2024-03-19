package com.bookingservice.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "bus_seats")
@Data
public class BusSeats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "bus_id",nullable = false)
    private long busId;

    @Column(name = "total_seats_booked",nullable = false)
    private int totalSeatsBooked;
}
