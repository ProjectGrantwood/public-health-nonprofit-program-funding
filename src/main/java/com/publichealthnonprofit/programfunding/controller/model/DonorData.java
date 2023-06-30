package com.publichealthnonprofit.programfunding.controller.model;

import java.util.Date;
import java.util.List;

import com.publichealthnonprofit.programfunding.controller.model.DonationData.DonationProgram;
import com.publichealthnonprofit.programfunding.entity.Donation;
import com.publichealthnonprofit.programfunding.entity.Donor;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@NoArgsConstructor
public class DonorData {
    
    // Primary Key
    
    private Long donorId;
    
    // Data
    
    private String donorName;
    private String donorEmail;
    private String donorPhone;
    private String donorAddress;
    private String donorAffiliation;
    private List<DonorDonation> donations;
    
    public DonorData(Donor donor) {
        this.donorId = donor.getDonorId();
        this.donorName = donor.getDonorName();
        this.donorEmail = donor.getDonorEmail();
        this.donorPhone = donor.getDonorPhone();
        this.donorAddress = donor.getDonorAddress();
        this.donorAffiliation = donor.getDonorAffiliation();
        this.donations = donor.getDonations().stream().map(DonorDonation::new).toList();
    }
    
    @Hidden
    @Data
    @NoArgsConstructor
    public static class DonorDonation {
        private Long donationId;
        private Float donationAmount;
        private Date donationDate;
        private List<DonationProgram> programs;
        
        public DonorDonation(Donation donation) {
            this.donationId = donation.getDonationId();
            this.donationAmount = donation.getDonationAmount();
            this.donationDate = donation.getDonationDate();
            this.programs = donation.getPrograms().stream().map(DonationProgram::new).toList();
        }
        
    }
    
}
