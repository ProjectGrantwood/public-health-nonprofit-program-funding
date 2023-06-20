package com.publichealthnonprofit.programfunding.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publichealthnonprofit.programfunding.entity.Donation;

public interface DonationDao extends JpaRepository<Donation, Long> {
    
}
