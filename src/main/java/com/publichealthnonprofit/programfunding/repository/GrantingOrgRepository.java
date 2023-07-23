package com.publichealthnonprofit.programfunding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publichealthnonprofit.programfunding.entity.GrantingOrg;

public interface GrantingOrgRepository extends JpaRepository<GrantingOrg, Long> {
    
}
