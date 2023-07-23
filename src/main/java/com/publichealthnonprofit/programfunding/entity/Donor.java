package com.publichealthnonprofit.programfunding.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Donor {
    
    // Primary Key
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donorId;
    
    // Data
    
    private String donorName;
    private String donorEmail;
    private String donorPhone;
    private String donorAddress;
    private String donorAffiliation;
    
    // For referencing donors in the donation table
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Donation> donations = new HashSet<>();
    
}
