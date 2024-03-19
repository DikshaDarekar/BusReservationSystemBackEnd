package com.searchservice.api.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/searchservice")
@Slf4j
public class SearchRestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/booking/findAll")
    public ResponseEntity<?> findAllBooking(@RequestParam("page")int page,@RequestParam("size") int size) {
        try {
            return restTemplate.getForEntity("http://localhost:8083/api/bookingservice/book/findall?page="+page+"&size="+size,Object.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/bus/findAll")
    public ResponseEntity<?> findAllBuses(@RequestParam("page")int page,@RequestParam("size") int size) {
        try {
            return restTemplate.getForEntity("http://localhost:8082/api/busservice/bus/findall?page="+page+"&size="+size,Object.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

}
