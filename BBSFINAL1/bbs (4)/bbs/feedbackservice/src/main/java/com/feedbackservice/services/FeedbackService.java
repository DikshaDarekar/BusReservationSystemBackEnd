package com.feedbackservice.services;

import com.feedbackservice.dto.FeedbackDTO;
import com.feedbackservice.entities.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {

    void save(Feedback feedback);
    Page<FeedbackDTO> findAll(Pageable pageable);
}
