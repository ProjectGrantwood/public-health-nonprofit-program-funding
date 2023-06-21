package com.publichealthnonprofit.programfunding.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
    
    // Foreign Keys
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "granting_org_id", nullable = false)
    private GrantingOrg grantingOrg;
    
    // Data
    
    private String financialGrantName;
    private Double financialGrantAmount;
    private Date financialGrantStartDate;
    private Date financialGrantEndDate;
    
    // For program_grant Join Table
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "program_financial_grant",
        joinColumns = @JoinColumn(name = "financial_grant_id"),
        inverseJoinColumns = @JoinColumn(name = "program_id")
    )
    private Set<Program> programs = new HashSet<>();
    
    
}
