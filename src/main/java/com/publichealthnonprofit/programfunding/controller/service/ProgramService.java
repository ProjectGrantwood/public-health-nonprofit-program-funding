package com.publichealthnonprofit.programfunding.controller.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publichealthnonprofit.programfunding.controller.model.ProgramData;
import com.publichealthnonprofit.programfunding.dao.DonationDao;
import com.publichealthnonprofit.programfunding.dao.FinancialGrantDao;
import com.publichealthnonprofit.programfunding.dao.ProgramDao;
import com.publichealthnonprofit.programfunding.entity.Donation;
import com.publichealthnonprofit.programfunding.entity.FinancialGrant;
import com.publichealthnonprofit.programfunding.entity.Program;

@Service
public class ProgramService {
    
    @Autowired
    private ProgramDao programDao;
    
    @Autowired
    private FinancialGrantDao financialGrantDao;
    
    @Autowired
    private DonationDao donationDao;

    @Transactional(readOnly = true)
    public List<ProgramData> getAllPrograms() {
        return programDao.findAll().stream().map(ProgramData::new).toList();
    }

    @Transactional(readOnly = true)
    public List<ProgramData> getAllProgramsByFinancialGrantId(Long financialGrantIdValue) {
        List<Program> allPrograms = programDao.findAll().stream().toList();
        List<Program> filteredByGrant = allPrograms.stream().filter(program -> program.getFinancialGrants().stream().anyMatch(financialGrant -> financialGrant.getFinancialGrantId().equals(financialGrantIdValue))).toList();
        return filteredByGrant.stream().map(ProgramData::new).toList();
    }


    public List<ProgramData> getAllProgramsByDonationId(Long donationIdValue) {
        List<Program> allPrograms = programDao.findAll().stream().toList();
        List<Program> filteredByDonation = allPrograms.stream().filter(program -> program.getDonations().stream().anyMatch(donation -> donation.getDonationId().equals(donationIdValue))).toList();
        return filteredByDonation.stream().map(ProgramData::new).toList();
        
    }

    public List<ProgramData> getAllProgramsByGrantingOrgId(Long grantingOrgIdValue) {
        List<Program> allPrograms = programDao.findAll().stream().toList();
        List<Program> filteredByGrantingOrg = allPrograms.stream().filter(program -> program.getFinancialGrants().stream().anyMatch(financialGrant -> financialGrant.getGrantingOrg().getGrantingOrgId().equals(grantingOrgIdValue))).toList();
        return filteredByGrantingOrg.stream().map(ProgramData::new).toList();
    }

    public List<ProgramData> getAllProgramsByDonorId(Long donorIdValue) {
        List<Program> allPrograms = programDao.findAll().stream().toList();
        List<Program> filteredByDonor = allPrograms.stream().filter(program -> program.getDonations().stream().anyMatch(donation -> donation.getDonor().getDonorId().equals(donorIdValue))).toList();
        return filteredByDonor.stream().map(ProgramData::new).toList();
    }
    
    public ProgramData createProgram(Program program) {
        return saveProgramFromProgramData(new ProgramData(program));
    }
    
    public ProgramData getProgramById(Long programId) {
        return new ProgramData(findProgramById(programId));
    }
    
    @Transactional(readOnly = false)
    public ProgramData updateProgram(Long programId, Program program) {
        Program programToUpdate = findProgramById(programId);
        updateFieldsInProgram(programToUpdate, new ProgramData(program));
        return new ProgramData(programDao.save(programToUpdate));
        
    }
    
    @Transactional(readOnly = true)
    public Program findProgramById(Long programId) {
        return programDao.findById(programId).orElseThrow(() -> new NoSuchElementException("Program not found with id " + programId));
    }
    
    @Transactional(readOnly = false)
    public ProgramData saveProgramFromProgramData(ProgramData programData) {
        Program program = findOrCreateProgram(programData.getProgramId());
        setFieldsInProgram(program, programData);
        return new ProgramData(programDao.save(program));
    }
    
    private Program findOrCreateProgram(Long programId) {
        Program program;
        if (Objects.isNull(programId)){
            program = new Program();
        } else {
            program = findProgramById(programId);
        }
        return program;
    }
    
    private void setFieldsInProgram(Program program, ProgramData programData) {
        program.setProgramName(programData.getProgramName());
        program.setProgramBudget(programData.getProgramBudget());
        program.setProgramBudgetPercentageDonationFunded(programData.getProgramBudgetPercentageDonationFunded());
        program.setProgramBudgetPercentageGrantFunded(programData.getProgramBudgetPercentageGrantFunded());
    }
    
    private void updateFieldsInProgram(Program program, ProgramData programData) {
        String updatedProgramName = programData.getProgramName();
        Float updatedProgramBudget = programData.getProgramBudget();
        Float updatedProgramBudgetPercentageDonationFunded = programData.getProgramBudgetPercentageDonationFunded();
        Float updatedProgramBudgetPercentageGrantFunded = programData.getProgramBudgetPercentageGrantFunded();
        if (Objects.nonNull(updatedProgramName)) {
            program.setProgramName(updatedProgramName);
        }
        if (Objects.nonNull(updatedProgramBudget)) {
            program.setProgramBudget(updatedProgramBudget);
        }
        if (Objects.nonNull(updatedProgramBudgetPercentageDonationFunded)) {
            program.setProgramBudgetPercentageDonationFunded(updatedProgramBudgetPercentageDonationFunded);
        }
        if (Objects.nonNull(updatedProgramBudgetPercentageGrantFunded)) {
            program.setProgramBudgetPercentageGrantFunded(updatedProgramBudgetPercentageGrantFunded);
        }
    }

    @Transactional(readOnly = false)
    public ProgramData addFinancialGrant(Long programId, Long financialGrantIdValue) {
        Program program = findProgramById(programId);
        FinancialGrant financialGrant = findFinancialGrantById(financialGrantIdValue);
        program.getFinancialGrants().add(financialGrant);
        return new ProgramData(programDao.save(program));
        
    }

    @Transactional(readOnly = false)
    public ProgramData addDonation(Long programId, Long donationIdValue) {
        Program program = findProgramById(programId);
        Donation donation = findDonationById(donationIdValue);
        program.getDonations().add(donation);
        return new ProgramData(programDao.save(program));
    }
    
    @Transactional(readOnly = true)
    public Donation findDonationById(Long donationId){
        return donationDao.findById(donationId).orElseThrow(() -> new NoSuchElementException("Donation not found with id " + donationId));
    }
    
    @Transactional(readOnly = true)
    public FinancialGrant findFinancialGrantById(Long financialGrantId){
        return financialGrantDao.findById(financialGrantId).orElseThrow(() -> new NoSuchElementException("Financial Grant not found with id " + financialGrantId));
    }
    
}
