package com.publichealthnonprofit.programfunding.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class GrantingOrg {
    
    public enum GrantingOrgType {
        FEDERAL,
        PRIVATE
    }
    
    // Primary Key
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grantingOrgId;
    
    // Data
    
    private String grantingOrgName;
    private String grantingOrgContactName;
    private String grantingOrgContactEmail;
    private String grantingOrgContactPhone;
    
    @Enumerated(EnumType.STRING)
    private GrantingOrgType grantingOrgType;
    
    // For referencing granting organizations in the grant table
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "grantingOrg", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FinancialGrant> financialGrants = new HashSet<>();
    
    
    
}
