package com.publichealthnonprofit.programfunding.controller.model;

import java.sql.Date;
import java.util.List;

import com.publichealthnonprofit.programfunding.controller.model.DonationData.DonationDonor;
import com.publichealthnonprofit.programfunding.entity.Donation;
import com.publichealthnonprofit.programfunding.entity.FinancialGrant;
import com.publichealthnonprofit.programfunding.entity.Program;
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
    
    @Data
    @NoArgsConstructor
    public static class ProgramDonation {
        
        private Long donationId;
        private DonationDonor donor;
        private Date donationDate;
        private Double donationAmount;
        
        public ProgramDonation(Donation donation) {
            this.donationId = donation.getDonationId();
            this.donor = new DonationDonor(donation.getDonor());
            this.donationDate = donation.getDonationDate();
            this.donationAmount = donation.getDonationAmount();
        }
        
    }
    
    @Data
    @NoArgsConstructor
    public static class ProgramFinancialGrant {
        private Long financialGrantId;
        private String financialGrantName;
        private Double financialGrantAmount;
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
