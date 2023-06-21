package com.publichealthnonprofit.programfunding.entity.joinedEntities;

import com.publichealthnonprofit.programfunding.entity.FinancialGrant;
import com.publichealthnonprofit.programfunding.entity.Program;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ProgramFinancialGrant {
    
    @Id
    @GeneratedValue
    private Long programFinancialGrantId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "financial_grant_id", nullable = false)
    private FinancialGrant financialGrant;
    
    private Double alottedToProgramPercentage;
    private Double alottedToProgramAmount;
    
}
