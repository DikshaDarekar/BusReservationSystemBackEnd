package com.busservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bus")
@Data
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "from_location", length = 255, nullable = false)
    private String fromLocation;

    @Column(name = "to_location", length = 255, nullable = false)
    private String toLocation;

    @Column(name = "departure", nullable = false)
    private LocalDateTime departure;

    @Column(name = "arrival", nullable = false)
    private LocalDateTime arrival;

    @Column(name = "is_ac", nullable = false)
    private boolean isAC;

    @Column(name = "fare", nullable = false)
    private BigDecimal fare;

    @Column(name = "seats",nullable = false)
    private int seats;

    public boolean isIsAC() {
        return isAC;
    }

    public void setIsAC(boolean isAC) {
        this.isAC = isAC;
    }

}
