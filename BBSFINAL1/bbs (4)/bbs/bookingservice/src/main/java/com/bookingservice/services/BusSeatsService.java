package com.bookingservice.services;

import com.bookingservice.entities.BusSeats;

public interface BusSeatsService {
    BusSeats findByBusId(long busId);
    void save(BusSeats busSeats);
}
