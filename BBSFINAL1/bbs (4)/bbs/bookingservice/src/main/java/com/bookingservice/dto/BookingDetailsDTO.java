package com.bookingservice.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class BookingDetailsDTO implements Serializable {
    private long id;
    private int seatsToBook;
    private Map<String,Object> bookedBy;
    private Map<String,Object> busDetails;
    private BigDecimal totalAmount;
    private LocalDateTime bookedAt;
}
