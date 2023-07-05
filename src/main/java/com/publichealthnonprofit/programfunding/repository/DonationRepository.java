package com.publichealthnonprofit.programfunding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publichealthnonprofit.programfunding.model.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    
}
