package com.paymentservice.services.impl;

import com.paymentservice.entities.PaymentDetails;
import com.paymentservice.repositories.PaymentDetailsRepository;
import com.paymentservice.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Override
    public void save(PaymentDetails paymentDetails) {
        this.paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails findByBookingDetailsId(long bookingDetailsId) {
        Optional<PaymentDetails> paymentDetailsOptional = this.paymentDetailsRepository.findByBookingDetailsId(bookingDetailsId);
        if (paymentDetailsOptional.isPresent()) {
            return paymentDetailsOptional.get();
        }
        return null;
    }
}
