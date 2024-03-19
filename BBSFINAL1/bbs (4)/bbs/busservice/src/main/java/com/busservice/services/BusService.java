package com.busservice.services;

import com.busservice.entities.Bus;
import com.busservice.exceptions.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BusService {
    void save(Bus bus) throws CustomException;
    Page<Bus> findAll(Pageable pageable);
    void delete(long id);
    Bus findById(long id);
}
