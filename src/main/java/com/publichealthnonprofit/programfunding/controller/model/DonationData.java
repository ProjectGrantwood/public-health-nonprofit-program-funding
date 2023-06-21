package com.publichealthnonprofit.programfunding.controller.model;

import java.sql.Date;
import java.util.List;

import com.publichealthnonprofit.programfunding.entity.Donation;
import com.publichealthnonprofit.programfunding.entity.Donor;
import com.publichealthnonprofit.programfunding.entity.joinedEntities.ProgramDonation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DonationData {
    private Long donationId;
    private Double donationAmount;
    private Date donationDate;
    private DonationDonor donor;
    private List<DonationProgramData> programs;
    
    DonationData(Donation donation){
        this.donationId = donation.getDonationId();
        this.donationAmount = donation.getDonationAmount();
        this.donationDate = donation.getDonationDate();
        this.donor = new DonationDonor(donation.getDonor());
        this.programs = donation.getPrograms().stream().map(DonationProgramData::new).toList();
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
    public static class DonationProgramData {
        private Long programDonationId;
        private Double alottedToProgramPercentage;
        private Double alottedToProgramAmount;
        
        DonationProgramData(ProgramDonation programDonation){
            this.programDonationId = programDonation.getProgramDonationId();
            this.alottedToProgramPercentage = programDonation.getAlottedToProgramPercentage();
            this.alottedToProgramAmount = programDonation.getAlottedToProgramAmount();
            
        }
    }
}
