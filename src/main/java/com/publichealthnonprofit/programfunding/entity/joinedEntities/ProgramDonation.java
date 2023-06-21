package com.publichealthnonprofit.programfunding.entity.joinedEntities;

import com.publichealthnonprofit.programfunding.entity.Donation;
import com.publichealthnonprofit.programfunding.entity.Program;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ProgramDonation {
    
    @Id
    @GeneratedValue
    private Long programDonationId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "donation_id", nullable = false)
    private Donation donation;
    
    private Double alottedToProgramPercentage;
    private Double alottedToProgramAmount;
    
}
