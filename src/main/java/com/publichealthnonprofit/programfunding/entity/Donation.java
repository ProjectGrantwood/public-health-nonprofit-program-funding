package com.publichealthnonprofit.programfunding.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Donation {
    
    // Primary Key
    
    @Id
    @GeneratedValue
    private Long donationId;
    
  // Data
    
    private Date donationDate;
    private Double donationAmount;
    
    
    // Foreign Key
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;
    
  
    // For program_donation Join Table
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "donations", cascade = CascadeType.PERSIST)
    private Set<Program> programs = new HashSet<>();
    

}
