package com.publichealthnonprofit.programfunding.controller.model;

import java.util.List;

import com.publichealthnonprofit.programfunding.entity.Program;
import com.publichealthnonprofit.programfunding.entity.joinedEntities.ProgramDonation;
import com.publichealthnonprofit.programfunding.entity.joinedEntities.ProgramFinancialGrant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProgramData {
    
    private Long programId;
    private String programName;
    private Double programBudget;
    private Double programBudgetPercentageGrantFunded;
    private Double programBudgetPercentageDonationFunded;
    private List<ProgramDonationData> donations;
    private List<ProgramFinancialGrantData> financialGrants;
    
    ProgramData(Program program){
        this.programId = program.getProgramId();
        this.programName = program.getProgramName();
        this.programBudget = program.getProgramBudget();
        this.programBudgetPercentageGrantFunded = program.getProgramBudgetPercentageGrantFunded();
        this.programBudgetPercentageDonationFunded = program.getProgramBudgetPercentageDonationFunded();
        this.donations = program.getDonations().stream().map(ProgramDonationData::new).toList();
        this.financialGrants = program.getFinancialGrants().stream().map(ProgramFinancialGrantData::new).toList();
    }
    
    @Data
    @NoArgsConstructor
    public static class ProgramDonationData {
        private Long programDonationId;
        private Double alottedToProgramPercentage;
        private Double alottedToProgramAmount;
        
        ProgramDonationData(ProgramDonation programDonation){
            this.programDonationId = programDonation.getProgramDonationId();
            this.alottedToProgramPercentage = programDonation.getAlottedToProgramPercentage();
            this.alottedToProgramAmount = programDonation.getAlottedToProgramAmount();
        }
        
    }
    
    @Data
    @NoArgsConstructor
    public static class ProgramFinancialGrantData {
        private Long programFinancialGrantId;
        private Double alottedToProgramPercentage;
        private Double alottedToProgramAmount;
        
        ProgramFinancialGrantData(ProgramFinancialGrant programFinancialGrant){
            this.programFinancialGrantId = programFinancialGrant.getProgramFinancialGrantId();
            this.alottedToProgramPercentage = programFinancialGrant.getAlottedToProgramPercentage();
            this.alottedToProgramAmount = programFinancialGrant.getAlottedToProgramAmount();
        }
        
    }
    
}
