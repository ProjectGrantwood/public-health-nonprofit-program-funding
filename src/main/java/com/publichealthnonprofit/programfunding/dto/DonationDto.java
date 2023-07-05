package com.publichealthnonprofit.programfunding.dto;

import java.util.Date;
import java.util.List;

import com.publichealthnonprofit.programfunding.model.Donation;
import com.publichealthnonprofit.programfunding.model.Donor;
import com.publichealthnonprofit.programfunding.model.Program;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;
@Hidden
@Data
@NoArgsConstructor
public class DonationDto {
    private Long donationId;
    private Double donationAmount;
    private Date donationDate;
    private DonationDonor donor;
    private List<DonationProgram> programs;
    
    public DonationDto(Donation donation){
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
        private Double programBudget;
        private Double programBudgetPercentageGrantFunded;
        private Double programBudgetPercentageDonationFunded;
        
        public DonationProgram(Program program){
            this.programId = program.getProgramId();
            this.programName = program.getProgramName();
            this.programBudget = program.getProgramBudget();
            this.programBudgetPercentageGrantFunded = program.getProgramBudgetPercentageGrantFunded();
            this.programBudgetPercentageDonationFunded = program.getProgramBudgetPercentageDonationFunded();
        }
    }
}
