package com.feedbackservice.services.impl;


import com.feedbackservice.dto.FeedbackDTO;
import com.feedbackservice.entities.Feedback;
import com.feedbackservice.repositories.FeedbackRepository;
import com.feedbackservice.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public void save(Feedback feedback) {
        this.feedbackRepository.save(feedback);
    }

    @Override
    public Page<FeedbackDTO> findAll(Pageable pageable) {
        Page<Feedback> feedbackPage=this.feedbackRepository.findAll(pageable);
        List<Feedback> feedbackList=feedbackPage.getContent();
        List<FeedbackDTO> feedbackDTOList=new ArrayList<>();
        for(Feedback feedback:feedbackList) {
            FeedbackDTO feedbackDTO=new FeedbackDTO();
            feedbackDTO.setId(feedback.getId());
            feedbackDTO.setRating(feedback.getRating());
            feedbackDTO.setRemarks(feedback.getRemarks());
            Map<String,Object> userDetails=restTemplate.getForObject("http://localhost:8081/api/users/findById/"+feedback.getUserId(),Map.class);
            feedbackDTO.setUserDetails(userDetails);
            feedbackDTOList.add(feedbackDTO);
        }
        return new PageImpl<>(feedbackDTOList,pageable,feedbackDTOList.size());
    }
}
