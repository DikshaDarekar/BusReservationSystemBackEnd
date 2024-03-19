package com.feedbackservice.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class FeedbackDTO implements Serializable {
    private long id;
    private int rating;
    private String remarks;
    private Map<String,Object> userDetails;
}
