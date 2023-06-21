package com.publichealthnonprofit.programfunding.controller.model;

import java.sql.Date;
import java.util.List;
import com.publichealthnonprofit.programfunding.controller.model.DonationData.DonationProgram;
import com.publichealthnonprofit.programfunding.entity.Donation;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    DonorData(Donation donation) {
        this.donorId = donation.getDonor().getDonorId();
        this.donorName = donation.getDonor().getDonorName();
        this.donorEmail = donation.getDonor().getDonorEmail();
        this.donorPhone = donation.getDonor().getDonorPhone();
        this.donorAddress = donation.getDonor().getDonorAddress();
        this.donorAffiliation = donation.getDonor().getDonorAffiliation();
        this.donations = donation.getDonor().getDonations().stream().map(DonorDonation::new).toList();
    }
    
    @Data
    @NoArgsConstructor
    public static class DonorDonation {
        private Long donationId;
        private Double donationAmount;
        private Date donationDate;
        private List<DonationProgram> programs;
        
        DonorDonation(Donation donation) {
            this.donationId = donation.getDonationId();
            this.donationAmount = donation.getDonationAmount();
            this.donationDate = donation.getDonationDate();
            this.programs = donation.getPrograms().stream().map(DonationProgram::new).toList();
        }
        
    }
    
}
