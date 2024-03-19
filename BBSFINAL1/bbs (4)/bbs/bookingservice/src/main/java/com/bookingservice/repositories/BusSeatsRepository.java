package com.bookingservice.repositories;

import com.bookingservice.entities.BusSeats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusSeatsRepository extends JpaRepository<BusSeats,Long> {
    Optional<BusSeats> findByBusId(long busId);
}
