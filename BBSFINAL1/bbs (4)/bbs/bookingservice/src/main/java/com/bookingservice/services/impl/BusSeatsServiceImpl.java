package com.bookingservice.services.impl;

import com.bookingservice.entities.BusSeats;
import com.bookingservice.repositories.BusSeatsRepository;
import com.bookingservice.services.BusSeatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BusSeatsServiceImpl implements BusSeatsService {

    @Autowired
    private BusSeatsRepository busSeatsRepository;

    @Override
    public BusSeats findByBusId(long busId) {
        Optional<BusSeats> busSeatsOptional=this.busSeatsRepository.findByBusId(busId);
        if (busSeatsOptional.isPresent()) {
            return busSeatsOptional.get();
        }
        return null;
    }

    @Override
    public void save(BusSeats busSeats) {
        this.busSeatsRepository.save(busSeats);
    }
}