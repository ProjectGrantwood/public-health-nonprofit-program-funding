package com.publichealthnonprofit.programfunding.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

import com.publichealthnonprofit.programfunding.entity.joinedEntities.ProgramDonation;
import com.publichealthnonprofit.programfunding.entity.joinedEntities.ProgramFinancialGrant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Program {
    
    // Primary Key
    
    @Id
    @GeneratedValue
    private Long programId;
    
    // Foriegn Key
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "program", cascade = CascadeType.PERSIST)
    private Set<ProgramFinancialGrant> financialGrants = new HashSet<>();
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private Set<ProgramDonation> donations = new HashSet<>();
    
    // Data
    
    private String programName;
    private Double programBudget;
    private Double programBudgetPercentageGrantFunded;
    private Double programBudgetPercentageDonationFunded;
    
    
    
}
