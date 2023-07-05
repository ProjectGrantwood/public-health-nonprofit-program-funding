package com.publichealthnonprofit.programfunding.dto;

import java.util.Date;
import java.util.List;

import com.publichealthnonprofit.programfunding.dto.DonationDto.DonationDonor;
import com.publichealthnonprofit.programfunding.model.Donation;
import com.publichealthnonprofit.programfunding.model.FinancialGrant;
import com.publichealthnonprofit.programfunding.model.Program;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@NoArgsConstructor
public class ProgramDto {
    
    private Long programId;
    private String programName;
    private Double programBudget;
    private Double programBudgetPercentageGrantFunded;
    private Double programBudgetPercentageDonationFunded;
    private List<ProgramDonation> donations;
    private List<ProgramFinancialGrant> financialGrants;
    
    public ProgramDto(Program program){
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
        private Double donationAmount;
        
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
