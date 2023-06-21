package com.publichealthnonprofit.programfunding.controller.model;

import java.sql.Date;
import java.util.List;

import com.publichealthnonprofit.programfunding.entity.Donation;
import com.publichealthnonprofit.programfunding.entity.Donor;
import com.publichealthnonprofit.programfunding.entity.Program;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DonationData {
    private Long donationId;
    private Double donationAmount;
    private Date donationDate;
    private DonationDonor donor;
    private List<DonationProgram> programs;
    
    DonationData(Donation donation){
        this.donationId = donation.getDonationId();
        this.donationAmount = donation.getDonationAmount();
        this.donationDate = donation.getDonationDate();
        this.donor = new DonationDonor(donation.getDonor());
        this.programs = donation.getPrograms().stream().map(DonationProgram::new).toList();
    }
    
    @Data
    @NoArgsConstructor
    public static class DonationDonor {
        private Long donorId;
        private String donorName;
        private String donorEmail;
        private String donorPhone;
        private String donorAddress;
        private String donorAffiliation;
        
        DonationDonor(Donor donor){
            this.donorId = donor.getDonorId();
            this.donorName = donor.getDonorName();
            this.donorEmail = donor.getDonorEmail();
            this.donorPhone = donor.getDonorPhone();
            this.donorAddress = donor.getDonorAddress();
            this.donorAffiliation = donor.getDonorAffiliation();
        }
        
    }
    
    @Data
    @NoArgsConstructor
    public static class DonationProgram {
        private Long programId;
        private String programName;
        private Double programBudget;
        private Double programBudgetPercentageGrantFunded;
        private Double programBudgetPercentageDonationFunded;
        
        DonationProgram(Program program){
            this.programId = program.getProgramId();
            this.programName = program.getProgramName();
            this.programBudget = program.getProgramBudget();
            this.programBudgetPercentageGrantFunded = program.getProgramBudgetPercentageGrantFunded();
            this.programBudgetPercentageDonationFunded = program.getProgramBudgetPercentageDonationFunded();
        }
    }
}
