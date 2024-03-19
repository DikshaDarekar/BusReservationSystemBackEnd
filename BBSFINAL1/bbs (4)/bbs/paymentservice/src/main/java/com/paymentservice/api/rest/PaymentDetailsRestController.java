package com.paymentservice.api.rest;


import com.paymentservice.entities.PaymentDetails;
import com.paymentservice.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/paymentservice")
@Slf4j
public class PaymentDetailsRestController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/save")
    public ResponseEntity<?> save(@RequestBody PaymentDetails paymentDetails) {
        log.info("Rest request to save bus");
        try {
            this.paymentService.save(paymentDetails);
            Map<String,Object> responseMap=new HashMap<>();
            responseMap.put("message","Payment done successfully");
            return ResponseEntity.ok().body(responseMap);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/payment/findbybookingdetailsid")
    public ResponseEntity<?> findById(@RequestParam("bookingDetailsId") long bookingDetailsId) {
        try {
            PaymentDetails paymentDetails = this.paymentService.findByBookingDetailsId(bookingDetailsId);
            return ResponseEntity.ok().body(paymentDetails);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }


}
