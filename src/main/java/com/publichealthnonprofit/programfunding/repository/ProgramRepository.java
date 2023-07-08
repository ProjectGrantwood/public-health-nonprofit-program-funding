package com.publichealthnonprofit.programfunding.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.publichealthnonprofit.programfunding.model.Program;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    
    Optional<Boolean> existsByProgramName(String programName);

    @Query("SELECT p FROM Program p JOIN p.donations d WHERE d.donationId = ?1")
    List<Program> findByDonationId(Long donationId);
    
    @Query("SELECT p FROM Program p JOIN p.financialGrants f WHERE f.financialGrantId = ?1")
    List<Program> findByFinancialGrantId(Long financialGrantId);
    
    @Query("SELECT p FROM Program p JOIN p.donations dntn JOIN dntn.donor dnr WHERE dnr.donorId = ?1")
    List<Program> findByDonorId(Long donorId);
    
    @Query("SELECT p FROM Program p JOIN p.financialGrants f JOIN f.grantingOrg go WHERE go.grantingOrgId = ?1")
    List<Program> findByGrantingOrgId(Long grantingOrgId);
    
    @Query("SELECT p.id FROM Program p JOIN p.donations d WHERE d.donationId = :donationId")
    List<Long> findProgramIdsByDonationId(@Param("donationId") Long donationId);
    
    @Query("SELECT p.id FROM Program p JOIN p.financialGrants fg WHERE fg.financialGrantId = :financialGrantId")
    List<Long> findProgramIdsByFinancialGrantId(@Param("financialGrantId") Long financialGrantId);
    
}
