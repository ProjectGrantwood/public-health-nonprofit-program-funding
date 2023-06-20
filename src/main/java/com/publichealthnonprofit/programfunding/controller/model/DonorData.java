package com.publichealthnonprofit.programfunding.controller.model;

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
    
    
}
