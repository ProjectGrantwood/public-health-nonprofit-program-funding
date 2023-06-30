package com.publichealthnonprofit.programfunding.controller.model;

import java.util.Date;
import java.util.List;

import com.publichealthnonprofit.programfunding.controller.model.DonationData.DonationDonor;
import com.publichealthnonprofit.programfunding.entity.Donation;
import com.publichealthnonprofit.programfunding.entity.FinancialGrant;
import com.publichealthnonprofit.programfunding.entity.Program;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@NoArgsConstructor
public class ProgramData {
    
    private Long programId;
    private String programName;
    private Float programBudget;
    private Float programBudgetPercentageGrantFunded;
    private Float programBudgetPercentageDonationFunded;
    private List<ProgramDonation> donations;
    private List<ProgramFinancialGrant> financialGrants;
    
    public ProgramData(Program program){
        this.programId = program.getProgramId();
        this.programName = program.getProgramName();
        this.programBudget = program.getProgramBudget();
        this.programBudgetPercentageGrantFunded = program.getProgramBudgetPercentageGrantFunded();
        this.programBudgetPercentageDonationFunded = program.getProgramBudgetPercentageDonationFunded();
        this.donations = program.getDonations().stream().map(ProgramDonation::new).toList();
        this.financialGrants = program.getFinancialGrants().stream().map(ProgramFinancialGrant::new).toList();
    }
    
    @Hidden
    @Data
    @NoArgsConstructor
    public static class ProgramDonation {
        
        private Long donationId;
        private DonationDonor donor;
        private Date donationDate;
        private Float donationAmount;
        
        public ProgramDonation(Donation donation) {
            this.donationId = donation.getDonationId();
            this.donor = new DonationDonor(donation.getDonor());
            this.donationDate = donation.getDonationDate();
            this.donationAmount = donation.getDonationAmount();
        }
        
    }
    
    @Hidden
    @Data
    @NoArgsConstructor
    public static class ProgramFinancialGrant {
        private Long financialGrantId;
        private String financialGrantName;
        private Float financialGrantAmount;
        private Date financialGrantStartDate;
        private Date financialGrantEndDate;
        
        public ProgramFinancialGrant(FinancialGrant financialGrant){
            this.financialGrantId = financialGrant.getFinancialGrantId();
            this.financialGrantName = financialGrant.getFinancialGrantName();
            this.financialGrantAmount = financialGrant.getFinancialGrantAmount();
            this.financialGrantStartDate = financialGrant.getFinancialGrantStartDate();
            this.financialGrantEndDate = financialGrant.getFinancialGrantEndDate();
        }
        
    }
    
}
