package com.publichealthnonprofit.programfunding.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;
    
    // Data
    
    private String programName;
    private Double programBudget;
    private Double programBudgetPercentageGrantFunded;
    private Double programBudgetPercentageDonationFunded;
    
    // Foriegn Key
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
        name = "program_financial_grant", 
        joinColumns = @JoinColumn(name = "program_id"),
        inverseJoinColumns = @JoinColumn(name = "financial_grant_id"))
    private Set<FinancialGrant> financialGrants = new HashSet<>();
    
    
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
        name = "program_donation",
        joinColumns = @JoinColumn(name = "program_id"),
        inverseJoinColumns = @JoinColumn(name = "donation_id"))
    private Set<Donation> donations = new HashSet<>();
    
    
    
}
