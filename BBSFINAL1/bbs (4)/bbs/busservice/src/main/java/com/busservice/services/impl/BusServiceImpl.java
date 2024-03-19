package com.busservice.services.impl;

import com.busservice.entities.Bus;
import com.busservice.exceptions.CustomException;
import com.busservice.repositories.BusRepositories;
import com.busservice.services.BusService;
import com.busservice.utils.StringUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepositories busRepositories;

    @Override
    public void save(Bus bus) throws CustomException {
        CustomException customException=this.validate(bus);
        if(!customException.getExceptions().isEmpty()) {
            throw customException;
        }
        log.info(bus.isIsAC()+"");
        this.busRepositories.save(bus);
    }

    @Override
    public Page<Bus> findAll(Pageable pageable) {
        Page<Bus> page = this.busRepositories.findAll(pageable);
        return page;
    }

    @Override
    public Bus findById(long id) {
        Optional<Bus> busOptional = this.busRepositories.findById(id);
        if(busOptional.isPresent()) {
            return busOptional.get();
        }
        return null;
    }

    @Override
    public void delete(long id) {
        Bus bus=this.findById(id);
        if(bus!=null) {
            this.busRepositories.delete(bus);
        }
    }

    private CustomException validate(Bus bus) {
        CustomException customException=new CustomException();
        if(StringUtility.isNullEmptyOrBlank(bus.getName())) {
            customException.addException("name","Name is required");
        }
        if(StringUtility.isNullEmptyOrBlank(bus.getFromLocation())) {
            customException.addException("fromLocation","From location is required");
        }
        if(StringUtility.isNullEmptyOrBlank(bus.getToLocation())) {
            customException.addException("toLocation","To location is required");
        }
        boolean isErr=false;
        if(bus.getDeparture()==null) {
            customException.addException("departure","Departure datetime is required");
            isErr=true;
        }
        if(bus.getArrival()==null) {
            customException.addException("arrival","Arrival datetime is required");
            isErr=true;
        }
        if(!isErr && bus.getDeparture().isAfter(bus.getArrival())) {
            customException.addException("departure","Departure time is before arrival time");
        }
        if(bus.getFare()==null) {
            customException.addException("fare","Fare is required");
        }else if(bus.getFare().compareTo(BigDecimal.ZERO)==0 || bus.getFare().compareTo(BigDecimal.ZERO)<0) {
            customException.addException("fare","Fare must be greater than 0");
        }
        return customException;
    }
}
