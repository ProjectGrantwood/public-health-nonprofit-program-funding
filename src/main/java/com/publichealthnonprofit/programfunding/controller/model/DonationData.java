package com.publichealthnonprofit.programfunding.controller.model;

import java.sql.Date;
import java.util.Set;

import com.publichealthnonprofit.programfunding.entity.Donor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DonationData {
    private Long donationId;
    private Double donationAmount;
    private Date donationDate;
    private CollectionlessDonor donor;
    private Set<CollectionlessProgram> programs;
    
    @Data
    @NoArgsConstructor
    public static class CollectionlessDonor {
        private Long donorId;
        private String donorName;
        private String donorEmail;
        private String donorPhone;
        private String donorAddress;
        private String donorAffiliation;
        
        CollectionlessDonor(Donor donor){
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
    public static class CollectionlessProgram {
        private Long programId;
        private String programName;
        private Double programBudget;
        private Double programPercentageGrantFunded;
        private Double programPercentageDonationFunded;
    }
}
