package com.bookingservice.repositories;

import com.bookingservice.entities.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {
    List<BookingDetails> findAllByBookedBy(long bookedBy);
}
