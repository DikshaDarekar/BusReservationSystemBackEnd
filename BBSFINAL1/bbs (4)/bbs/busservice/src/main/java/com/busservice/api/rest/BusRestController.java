package com.busservice.api.rest;


import com.busservice.entities.Bus;
import com.busservice.exceptions.CustomException;
import com.busservice.services.BusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/busservice")
@Slf4j
public class BusRestController {

    @Autowired
    private BusService busService;

    @PostMapping("/bus/save")
    public ResponseEntity<?> save(@RequestBody Bus bus) {
        log.info("Rest request to save bus");
        try {
            this.busService.save(bus);
            Map<String,Object> responseMap=new HashMap<>();
            responseMap.put("message","Bus scheduled successfully");
            return ResponseEntity.ok().body(responseMap);
        } catch (CustomException customException) {
            if (customException.getExceptions() != null && !customException.getExceptions().isEmpty())
                return ResponseEntity.badRequest().body(customException.getExceptions());
            else return ResponseEntity.badRequest().body(customException.getException());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/bus/findall")
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            Page<Bus> page = this.busService.findAll(pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/bus/findbyid/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        try {
            Bus bus = this.busService.findById(id);
            return ResponseEntity.ok().body(bus);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @DeleteMapping("/bus/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            this.busService.delete(id);
            Map<String,Object> responseMap=new HashMap<>();
            responseMap.put("message","Bus cancelled successfully");
            return ResponseEntity.ok().body(responseMap);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

}
