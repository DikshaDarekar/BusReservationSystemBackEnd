package com.paymentservice.repositories;

import com.paymentservice.entities.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails,Long> {

    Optional<PaymentDetails> findByBookingDetailsId(long bookingDetailsId);
}
