package com.publichealthnonprofit.programfunding.controller.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    
    @Transactional(readOnly = false)
    public ProgramData createProgram(Program program) {
            /* We need to perform several checks: 
            - Is the program name unique?
            - Are the percentageBudget fields represented as decimals (e.g. 0.5 instead of 50)?
            - Do the percentageBudget fields add up to 1.0?
          */
        String programName = program.getProgramName();
        Optional<Boolean> programNameOptional = programDao.existsByProgramName(programName);
        boolean programNameExists = programNameOptional.isPresent() && programNameOptional.get();
        Float programBudgetPercentageGrantFunded = program.getProgramBudgetPercentageGrantFunded();
        Float programBudgetPercentageDonationFunded = program.getProgramBudgetPercentageDonationFunded();
        Float programBudgetPercentageTotal = programBudgetPercentageGrantFunded + programBudgetPercentageDonationFunded;
        if (programNameExists) {
            throw new DuplicateKeyException("Program name already exists");
        } else if (programBudgetPercentageGrantFunded > 1.0 || programBudgetPercentageGrantFunded < 0.0) {
            throw new IllegalArgumentException("Grant-funded percentage must be between 0.0 and 1.0");
        } else if (programBudgetPercentageDonationFunded > 1.0 || programBudgetPercentageDonationFunded < 0.0) {
            throw new IllegalArgumentException("Donation-funded percentage must be between 0.0 and 1.0");
        } else if (programBudgetPercentageTotal != 1.0) {
            throw new IllegalArgumentException("Grant-funded and donation-funded percentages must add up to 1.0");
        }
        return saveProgramFromProgramData(new ProgramData(program));
    }
    
    @Transactional(readOnly = true)
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
        if (program.getFinancialGrants().contains(financialGrant)) {
            throw new IllegalArgumentException("Financial grant is already associated with program");
        }
        program.getFinancialGrants().add(financialGrant);
        return new ProgramData(programDao.save(program));
        
    }

    @Transactional(readOnly = false)
    public ProgramData addDonation(Long programId, Long donationIdValue) {
        Program program = findProgramById(programId);
        Donation donation = findDonationById(donationIdValue);
        if (program.getDonations().contains(donation)) {
            throw new IllegalArgumentException("Donation is already associated with program");
        }
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
