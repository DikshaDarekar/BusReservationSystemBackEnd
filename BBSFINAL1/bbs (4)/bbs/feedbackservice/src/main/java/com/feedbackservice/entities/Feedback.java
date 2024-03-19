package com.feedbackservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "feedback")
@Data
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "remarks", nullable = false)
    private String remarks;

    @Column(name = "userId", nullable = false)
    private long userId;

}
