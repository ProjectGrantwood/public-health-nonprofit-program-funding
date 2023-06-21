package com.publichealthnonprofit.programfunding.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.publichealthnonprofit.programfunding.entity.joinedEntities.ProgramDonation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    
    // Foreign Key
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;
    
    // Data
    
    private Date donationDate;
    private Double donationAmount;
    
    // For program_donation Join Table
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL)
    private Set<ProgramDonation> programs = new HashSet<>();
}
