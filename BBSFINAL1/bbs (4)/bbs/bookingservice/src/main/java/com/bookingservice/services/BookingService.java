package com.bookingservice.services;

import com.bookingservice.dto.BookingDetailsDTO;
import com.bookingservice.entities.BookingDetails;
import com.bookingservice.exceptions.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {

    long save(BookingDetails bookingDetails) throws CustomException;
    BookingDetailsDTO findById(long id);
    List<BookingDetailsDTO> findAllByUserId(long userId);
    Page<BookingDetailsDTO> findAll(Pageable pageable);
    void delete(long id);
}
