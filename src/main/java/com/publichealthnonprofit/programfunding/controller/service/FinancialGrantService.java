package com.publichealthnonprofit.programfunding.controller.service;

import java.sql.Date;
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
import com.publichealthnonprofit.programfunding.entity.FinancialGrant;
import com.publichealthnonprofit.programfunding.entity.Program;

@Service
public class FinancialGrantService {
    
    @SuppressWarnings("unused")
    @Autowired
    private FinancialGrantDao financialGrantDao;
    
    @Autowired
    private GrantingOrgService grantingOrgService;
    
    @Autowired
    private ProgramService programService;

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
    public FinancialGrantData createFinancialGrant(FinancialGrant financialGrant) {
        return saveFinancialGrantFromFinancialGrantData(new FinancialGrantData(financialGrant));
    }

    @Transactional(readOnly = false)
    public FinancialGrantData createFinancialGrant(FinancialGrant financialGrant, Long programIdValue) {
        FinancialGrantData financialGrantData = new FinancialGrantData(financialGrant);
        financialGrantData.setPrograms(List.of(new FinancialGrantProgram(programService.findProgramById(programIdValue))));
        return saveFinancialGrantFromFinancialGrantData(financialGrantData);
    }

    public FinancialGrantData getFinancialGrantById(Long financialGrantId) {
        return new FinancialGrantData(findFinancialGrantById(financialGrantId));
    }

    @Transactional(readOnly = false)
    public FinancialGrantData updateFinancialGrant(Long financialGrantId, FinancialGrant financialGrant) {
        FinancialGrant financialGrantToUpdate = findFinancialGrantById(financialGrantId);
        setUpdatedFieldsInFinancialGrant(financialGrantToUpdate, new FinancialGrantData(financialGrant));
        return new FinancialGrantData(financialGrantDao.save(financialGrantToUpdate));
    }
    
    @Transactional(readOnly = false)
    private FinancialGrantData saveFinancialGrantFromFinancialGrantData(FinancialGrantData financialGrantData) {
        Long financialGrantId = financialGrantData.getFinancialGrantId();
        FinancialGrant financialGrant = findOrCreateFinancialGrant(financialGrantId);
        setFieldsInFinancialGrant(financialGrant, financialGrantData);
        return new FinancialGrantData(financialGrantDao.save(financialGrant));
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
    
    private void setFieldsInFinancialGrant(FinancialGrant financialGrant, FinancialGrantData financialGrantData) {
        financialGrant.setGrantingOrg(grantingOrgService.findGrantingOrgById(financialGrantData.getGrantingOrg().getGrantingOrgId()));
        Set<Program> programs = new HashSet<>();
        List<FinancialGrantProgram> financialGrantPrograms = financialGrantData.getPrograms();
        for (FinancialGrantProgram financialGrantProgram : financialGrantPrograms) {
            programs.add(programService.findProgramById(financialGrantProgram.getProgramId()));
        }
        financialGrant.setPrograms(programs);
        financialGrant.setFinancialGrantName(financialGrantData.getFinancialGrantName());
        financialGrant.setFinancialGrantAmount(financialGrantData.getFinancialGrantAmount());
        financialGrant.setFinancialGrantStartDate(financialGrantData.getFinancialGrantStartDate());
        financialGrant.setFinancialGrantEndDate(financialGrantData.getFinancialGrantEndDate());
    }
    
    private void setUpdatedFieldsInFinancialGrant(FinancialGrant financialGrant, FinancialGrantData financialGrantData) {
        FinancialGrantGrantingOrg updatedGrantingOrg = financialGrantData.getGrantingOrg();
        String updatedFinancialGrantName = financialGrantData.getFinancialGrantName();
        Float updatedFinancialGrantAmount = financialGrantData.getFinancialGrantAmount();
        Date updatedFinancialGrantStartDate = financialGrantData.getFinancialGrantStartDate();
        Date updatedFinancialGrantEndDate = financialGrantData.getFinancialGrantEndDate();
        List<FinancialGrantProgram> updatedFinancialGrantPrograms = financialGrantData.getPrograms();
        if (Objects.nonNull(updatedGrantingOrg)) {
            financialGrant.setGrantingOrg(grantingOrgService.findGrantingOrgById(updatedGrantingOrg.getGrantingOrgId()));
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
        if (Objects.nonNull(updatedFinancialGrantPrograms)) {
            Set<Program> programs = new HashSet<>();
            for (FinancialGrantProgram financialGrantProgram : updatedFinancialGrantPrograms) {
               Program program = programService.findProgramById(financialGrantProgram.getProgramId());
               if (!financialGrant.getPrograms().contains(program)) {
                   programs.add(program);
               }
            }
            financialGrant.setPrograms(programs);
        }
    }
    
}
