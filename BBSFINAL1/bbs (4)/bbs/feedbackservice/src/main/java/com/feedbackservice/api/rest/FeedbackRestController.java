package com.feedbackservice.api.rest;


import com.feedbackservice.dto.FeedbackDTO;
import com.feedbackservice.entities.Feedback;
import com.feedbackservice.services.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/feedbackservice")
@Slf4j
public class FeedbackRestController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/feedback/save")
    public ResponseEntity<?> save(@RequestBody Feedback feedback) {
        log.info("Rest request to save bus");
        try {
            this.feedbackService.save(feedback);
            Map<String,Object> responseMap=new HashMap<>();
            responseMap.put("message","Feedback saved successfully");
            return ResponseEntity.ok().body(responseMap);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/feedback/findAll")
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            Page<FeedbackDTO> page = this.feedbackService.findAll(pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }


}
