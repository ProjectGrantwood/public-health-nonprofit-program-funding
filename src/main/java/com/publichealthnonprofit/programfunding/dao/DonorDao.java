package com.publichealthnonprofit.programfunding.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publichealthnonprofit.programfunding.entity.Donor;

public interface DonorDao extends JpaRepository<Donor, Long> {
    
}
