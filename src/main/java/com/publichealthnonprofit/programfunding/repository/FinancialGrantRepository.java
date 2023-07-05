package com.publichealthnonprofit.programfunding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publichealthnonprofit.programfunding.model.FinancialGrant;

public interface FinancialGrantRepository extends JpaRepository<FinancialGrant, Long> {
    
}
