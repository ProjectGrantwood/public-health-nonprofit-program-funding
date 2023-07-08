package com.publichealthnonprofit.programfunding.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/* 

Note: This class was initially just named "Grant", 
but I encountered major errors when Hibernate tried
to generate the table for this class. It eventually dawned
on me that this is because "Grant" is a reserved word in SQL.

*/

@Entity
@Data
public class FinancialGrant {
    
    // Primary Key
    
    @Id
    @GeneratedValue
    private Long financialGrantId;
    
    // Data
    
    private String financialGrantName;
    private Double financialGrantAmount;
    private Date financialGrantStartDate;
    private Date financialGrantEndDate;
    
    // Foreign Keys
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "granting_org_id", nullable = false)
    private GrantingOrg grantingOrg;
    

    
    // For program_grant Join Table
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "financialGrants", cascade = CascadeType.PERSIST)
    private Set<Program> programs = new HashSet<>();
    
    
}
