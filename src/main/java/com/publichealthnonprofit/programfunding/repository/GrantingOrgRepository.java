package com.publichealthnonprofit.programfunding.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publichealthnonprofit.programfunding.entity.GrantingOrg;

public interface GrantingOrgDao extends JpaRepository<GrantingOrg, Long> {
    
}
