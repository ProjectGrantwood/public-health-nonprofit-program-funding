package com.publichealthnonprofit.programfunding.controller.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publichealthnonprofit.programfunding.controller.model.FinancialGrantData;
import com.publichealthnonprofit.programfunding.controller.model.FinancialGrantData.FinancialGrantGrantingOrg;
import com.publichealthnonprofit.programfunding.controller.model.FinancialGrantData.FinancialGrantProgram;
import com.publichealthnonprofit.programfunding.dao.FinancialGrantDao;
import com.publichealthnonprofit.programfunding.dao.GrantingOrgDao;
import com.publichealthnonprofit.programfunding.dao.ProgramDao;
import com.publichealthnonprofit.programfunding.entity.FinancialGrant;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg;
import com.publichealthnonprofit.programfunding.entity.Program;

@Service
public class FinancialGrantService {
    
    @Autowired
    private FinancialGrantDao financialGrantDao;
    
    @Autowired
    private GrantingOrgDao grantingOrgDao;
    
    @Autowired
    private ProgramDao programDao;

    @Transactional(readOnly = true)
    public List<FinancialGrantData> getAllFinancialGrantsByGrantingOrgId(Long grantingOrgIdValue) {
        List<FinancialGrantData> allFinancialGrants = getAllFinancialGrants();
        return allFinancialGrants.stream().filter(financialGrant -> financialGrant.getGrantingOrg().getGrantingOrgId().equals(grantingOrgIdValue)).toList();
    }

    @Transactional(readOnly = true)
    public List<FinancialGrantData> getAllFinancialGrantsByProgramId(Long programIdValue) {
        List<FinancialGrantData> allFinancialGrants = getAllFinancialGrants();
        return allFinancialGrants.stream().filter(financialGrant -> financialGrant.getPrograms().stream().anyMatch(program -> program.getProgramId().equals(programIdValue))).toList();
    }
    
    @Transactional(readOnly = true)
    public List<FinancialGrantData> getAllFinancialGrants() {
        return financialGrantDao.findAll().stream().map(FinancialGrantData::new).toList();
    }

    @Transactional(readOnly = false)
    public FinancialGrantData createFinancialGrant(FinancialGrant financialGrant, Long grantingOrgIdValue) {
        GrantingOrg grantingOrg = findGrantingOrgById(grantingOrgIdValue);
        financialGrant.setGrantingOrg(grantingOrg);
        return saveFinancialGrantFromFinancialGrantData(new FinancialGrantData(financialGrant));
    }

    @Transactional(readOnly = false)
    public FinancialGrantData createFinancialGrant(FinancialGrant financialGrant, Long grantingOrgIdValue, Long programIdValue) {
        GrantingOrg grantingOrg = findGrantingOrgById(grantingOrgIdValue);
        financialGrant.setGrantingOrg(grantingOrg);
        return saveFinancialGrantFromFinancialGrantData(new FinancialGrantData(financialGrant), programIdValue);
    }
    
    private FinancialGrantData saveFinancialGrantFromFinancialGrantData(FinancialGrantData financialGrantData) {
        Long financialGrantId = financialGrantData.getFinancialGrantId();
        FinancialGrant financialGrant = findOrCreateFinancialGrant(financialGrantId);
        setFieldsInFinancialGrant(financialGrant, financialGrantData);
        return new FinancialGrantData(financialGrantDao.save(financialGrant));
    }
    
    @Transactional(readOnly = false)
    public FinancialGrantData saveFinancialGrantFromFinancialGrantData(FinancialGrantData financialGrantData, Long programIdValue) {
        Long financialGrantId = financialGrantData.getFinancialGrantId();
        FinancialGrant financialGrant = findOrCreateFinancialGrant(financialGrantId);
        setFieldsInFinancialGrant(financialGrant, financialGrantData, programIdValue);
        return new FinancialGrantData(financialGrantDao.save(financialGrant));
    }
    
    private void setFieldsInFinancialGrant(FinancialGrant financialGrant, FinancialGrantData financialGrantData) {
        financialGrant.setGrantingOrg(findGrantingOrgById(financialGrantData.getGrantingOrg().getGrantingOrgId()));
        Set<Program> programs = new HashSet<>();
        List<FinancialGrantProgram> financialGrantPrograms = financialGrantData.getPrograms();
        for (FinancialGrantProgram financialGrantProgram : financialGrantPrograms) {
            programs.add(findProgramById(financialGrantProgram.getProgramId()));
        }
        financialGrant.setPrograms(programs);
        financialGrant.setFinancialGrantName(financialGrantData.getFinancialGrantName());
        financialGrant.setFinancialGrantAmount(financialGrantData.getFinancialGrantAmount());
        financialGrant.setFinancialGrantStartDate(financialGrantData.getFinancialGrantStartDate());
        financialGrant.setFinancialGrantEndDate(financialGrantData.getFinancialGrantEndDate());
    }
    
        private void setFieldsInFinancialGrant(FinancialGrant financialGrant, FinancialGrantData financialGrantData, Long programIdValue) {
        financialGrant.setGrantingOrg(findGrantingOrgById(financialGrantData.getGrantingOrg().getGrantingOrgId()));
        Set<Program> programs = new HashSet<>();
        List<FinancialGrantProgram> financialGrantPrograms = financialGrantData.getPrograms();
        for (FinancialGrantProgram financialGrantProgram : financialGrantPrograms) {
            programs.add(findProgramById(financialGrantProgram.getProgramId()));
        }
        Program program = findProgramById(programIdValue);
        programs.add(program);
        financialGrant.setPrograms(programs);
        financialGrant.setFinancialGrantName(financialGrantData.getFinancialGrantName());
        financialGrant.setFinancialGrantAmount(financialGrantData.getFinancialGrantAmount());
        financialGrant.setFinancialGrantStartDate(financialGrantData.getFinancialGrantStartDate());
        financialGrant.setFinancialGrantEndDate(financialGrantData.getFinancialGrantEndDate());
        Set<FinancialGrant> programFinancialGrants = program.getFinancialGrants();
        programFinancialGrants.add(financialGrant);
        program.setFinancialGrants(programFinancialGrants);
    }
    
    
    @Transactional(readOnly = false)
    public FinancialGrantData updateFinancialGrant(Long financialGrantId, FinancialGrant financialGrant) {
        FinancialGrant financialGrantToUpdate = findFinancialGrantById(financialGrantId);
        setUpdatedFieldsInFinancialGrant(financialGrantToUpdate, new FinancialGrantData(financialGrant));
        return new FinancialGrantData(financialGrantDao.save(financialGrantToUpdate));
    }

    @Transactional(readOnly = true)
    public FinancialGrantData getFinancialGrantById(Long financialGrantId) {
        return new FinancialGrantData(findFinancialGrantById(financialGrantId));
    }
    
    private FinancialGrant findOrCreateFinancialGrant(Long financialGrantId){
        FinancialGrant financialGrant;
        if (Objects.isNull(financialGrantId)) {
            financialGrant = new FinancialGrant();
        } else {
            financialGrant = findFinancialGrantById(financialGrantId);
        }
        return financialGrant;
    }
    
    @Transactional(readOnly = true)
    public FinancialGrant findFinancialGrantById(Long financialGrantId) {
        return financialGrantDao.findById(financialGrantId).orElseThrow(() -> new NoSuchElementException("FinancialGrant not found for id " + financialGrantId));
    }
    
    private void setUpdatedFieldsInFinancialGrant(FinancialGrant financialGrant, FinancialGrantData financialGrantData) {
        FinancialGrantGrantingOrg updatedGrantingOrg = financialGrantData.getGrantingOrg();
        String updatedFinancialGrantName = financialGrantData.getFinancialGrantName();
        Double updatedFinancialGrantAmount = financialGrantData.getFinancialGrantAmount();
        Date updatedFinancialGrantStartDate = financialGrantData.getFinancialGrantStartDate();
        Date updatedFinancialGrantEndDate = financialGrantData.getFinancialGrantEndDate();
        if (Objects.nonNull(updatedGrantingOrg)) {
            financialGrant.setGrantingOrg(findGrantingOrgById(updatedGrantingOrg.getGrantingOrgId()));
        }
        if (Objects.nonNull(updatedFinancialGrantName)) {
            financialGrant.setFinancialGrantName(updatedFinancialGrantName);
        }
        if (Objects.nonNull(updatedFinancialGrantAmount)) {
            financialGrant.setFinancialGrantAmount(updatedFinancialGrantAmount);
        }
        if (Objects.nonNull(updatedFinancialGrantStartDate)) {
            financialGrant.setFinancialGrantStartDate(updatedFinancialGrantStartDate);
        }
        if (Objects.nonNull(updatedFinancialGrantEndDate)) {
            financialGrant.setFinancialGrantEndDate(updatedFinancialGrantEndDate);
        }
    }
    
    @Transactional(readOnly = true)
    public GrantingOrg findGrantingOrgById(Long grantingOrgId) {
        return grantingOrgDao.findById(grantingOrgId).orElseThrow(() -> new NoSuchElementException("GrantingOrg not found for id " + grantingOrgId));
    }
    
    @Transactional(readOnly = true)
    public Program findProgramById(Long programId){
        return programDao.findById(programId).orElseThrow(() -> new NoSuchElementException("Program not found for id " + programId));
    }

    @Transactional(readOnly = false)
    public void deleteFinancialGrant(Long financialGrantId) {
        if (financialGrantDao.existsById(financialGrantId)) {
            financialGrantDao.deleteById(financialGrantId);
        } else {
            throw new NoSuchElementException("Grant not found for id " + financialGrantId);
        }
    }
    
}
