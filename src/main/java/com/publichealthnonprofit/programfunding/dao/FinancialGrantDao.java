package com.publichealthnonprofit.programfunding.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publichealthnonprofit.programfunding.entity.FinancialGrant;

public interface FinancialGrantDao extends JpaRepository<FinancialGrant, Long> {
    
}
