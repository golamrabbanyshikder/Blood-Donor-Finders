package com.bd.blooddonerfinder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class DonationHistory {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Users donor;

    @ManyToOne
    private Users recipient;

    @ManyToOne
    private BloodRequest request;

    private LocalDateTime donationDate;
    private String notes;

    private Boolean verified;

}
