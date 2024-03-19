package com.paymentservice.services;

import com.paymentservice.entities.PaymentDetails;

public interface PaymentService {

    void save(PaymentDetails paymentDetails);
    PaymentDetails findByBookingDetailsId(long bookingDetailsId);
}
