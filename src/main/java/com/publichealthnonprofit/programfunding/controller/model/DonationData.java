package com.publichealthnonprofit.programfunding.controller.model;

import java.util.Date;
import java.util.List;
import com.publichealthnonprofit.programfunding.entity.Donation;
import com.publichealthnonprofit.programfunding.entity.Donor;
import com.publichealthnonprofit.programfunding.entity.Program;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;
@Hidden
@Data
@NoArgsConstructor
public class DonationData {
    private Long donationId;
    private Float donationAmount;
    private Date donationDate;
    private DonationDonor donor;
    private List<DonationProgram> programs;
    
    public DonationData(Donation donation){
        this.donationId = donation.getDonationId();
        this.donationAmount = donation.getDonationAmount();
        this.donationDate = donation.getDonationDate();
        this.donor = new DonationDonor(donation.getDonor());
        this.programs = donation.getPrograms().stream().map(DonationProgram::new).toList();
    }
    
    @Hidden
    @Data
    @NoArgsConstructor
    public static class DonationDonor {
        private Long donorId;
        private String donorName;
        private String donorEmail;
        private String donorPhone;
        private String donorAddress;
        private String donorAffiliation;
        
        public DonationDonor(Donor donor){
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
        private Float programBudget;
        private Float programBudgetPercentageGrantFunded;
        private Float programBudgetPercentageDonationFunded;
        
        public DonationProgram(Program program){
            this.programId = program.getProgramId();
            this.programName = program.getProgramName();
            this.programBudget = program.getProgramBudget();
            this.programBudgetPercentageGrantFunded = program.getProgramBudgetPercentageGrantFunded();
            this.programBudgetPercentageDonationFunded = program.getProgramBudgetPercentageDonationFunded();
        }
    }
}
