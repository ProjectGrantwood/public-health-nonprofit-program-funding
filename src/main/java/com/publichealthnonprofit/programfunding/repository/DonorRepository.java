package com.publichealthnonprofit.programfunding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publichealthnonprofit.programfunding.model.Donor;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    
}
