package com.publichealthnonprofit.programfunding.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publichealthnonprofit.programfunding.dto.ProgramDto;
import com.publichealthnonprofit.programfunding.model.Donation;
import com.publichealthnonprofit.programfunding.model.FinancialGrant;
import com.publichealthnonprofit.programfunding.model.Program;
import com.publichealthnonprofit.programfunding.repository.DonationRepository;
import com.publichealthnonprofit.programfunding.repository.FinancialGrantRepository;
import com.publichealthnonprofit.programfunding.repository.ProgramRepository;

@Service
public class ProgramService {
    
    @Autowired
    private ProgramRepository programRepository;
    
    @Autowired
    private FinancialGrantRepository financialGrantDao;
    
    @Autowired
    private DonationRepository donationDao;

    @Transactional(readOnly = true)
    public List<ProgramDto> getAllProgramsAsProgramData() {
        return programRepository.findAll().stream().map(ProgramDto::new).toList();
    }
    
    @Transactional(readOnly = true)
    public List<Program> getAllPrograms() {
        return programRepository.findAll().stream().toList();
    }

    @Transactional(readOnly = true)
    public List<ProgramDto> getAllProgramsByFinancialGrantId(Long financialGrantIdValue) {
        return programRepository.findByFinancialGrantId(financialGrantIdValue).stream().map(ProgramDto::new).toList();
    }


    @Transactional(readOnly = true)
    public List<ProgramDto> getAllProgramsByDonationId(Long donationIdValue) {
        return programRepository.findByDonationId(donationIdValue).stream().map(ProgramDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<ProgramDto> getAllProgramsByGrantingOrgId(Long grantingOrgIdValue) {
        return programRepository.findByGrantingOrgId(grantingOrgIdValue).stream().map(ProgramDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<ProgramDto> getAllProgramsByDonorId(Long donorIdValue) {
        return programRepository.findByDonorId(donorIdValue).stream().map(ProgramDto::new).toList();
    }
    
    @Transactional(readOnly = false)
    public ProgramDto createProgram(Program program) {
            /* We need to perform several checks: 
            - Is the program name unique?
            - Are the percentageBudget fields represented as decimals (e.g. 0.5 instead of 50)?
            - Do the percentageBudget fields add up to 1.0?
          */
        String programName = program.getProgramName();
        Optional<Boolean> programNameOptional = programRepository.existsByProgramName(programName);
        boolean programNameExists = programNameOptional.isPresent() && programNameOptional.get();
        Double programBudgetPercentageGrantFunded = program.getProgramBudgetPercentageGrantFunded();
        Double programBudgetPercentageDonationFunded = program.getProgramBudgetPercentageDonationFunded();
        Double programBudgetPercentageTotal = programBudgetPercentageGrantFunded + programBudgetPercentageDonationFunded;
        if (programNameExists) {
            throw new DuplicateKeyException("Program name already exists");
        } else if (programBudgetPercentageGrantFunded > 1.0 || programBudgetPercentageGrantFunded < 0.0) {
            throw new IllegalArgumentException("Grant-funded percentage must be between 0.0 and 1.0");
        } else if (programBudgetPercentageDonationFunded > 1.0 || programBudgetPercentageDonationFunded < 0.0) {
            throw new IllegalArgumentException("Donation-funded percentage must be between 0.0 and 1.0");
        } else if (programBudgetPercentageTotal != 1.0) {
            throw new IllegalArgumentException("Grant-funded and donation-funded percentages must add up to 1.0");
        }
        return saveProgramFromProgramData(new ProgramDto(program));
    }
    
    @Transactional(readOnly = true)
    public ProgramDto getProgramById(Long programId) {
        return new ProgramDto(findProgramById(programId));
    }
    
    @Transactional(readOnly = false)
    public ProgramDto updateProgram(Long programId, Program program) {
        Program programToUpdate = findProgramById(programId);
        updateFieldsInProgram(programToUpdate, new ProgramDto(program));
        return new ProgramDto(programRepository.save(programToUpdate));
        
    }
    
    @Transactional(readOnly = true)
    public Program findProgramById(Long programId) {
        return programRepository.findById(programId).orElseThrow(() -> new NoSuchElementException("Program not found with id " + programId));
    }
    
    @Transactional(readOnly = false)
    public ProgramDto saveProgramFromProgramData(ProgramDto programData) {
        Program program = findOrCreateProgram(programData.getProgramId());
        setFieldsInProgram(program, programData);
        return new ProgramDto(programRepository.save(program));
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
    
    private void setFieldsInProgram(Program program, ProgramDto programData) {
        program.setProgramName(programData.getProgramName());
        program.setProgramBudget(programData.getProgramBudget());
        program.setProgramBudgetPercentageDonationFunded(programData.getProgramBudgetPercentageDonationFunded());
        program.setProgramBudgetPercentageGrantFunded(programData.getProgramBudgetPercentageGrantFunded());
    }
    
    private void updateFieldsInProgram(Program program, ProgramDto programData) {
        String updatedProgramName = programData.getProgramName();
        Double updatedProgramBudget = programData.getProgramBudget();
        Double updatedProgramBudgetPercentageDonationFunded = programData.getProgramBudgetPercentageDonationFunded();
        Double updatedProgramBudgetPercentageGrantFunded = programData.getProgramBudgetPercentageGrantFunded();
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
    public ProgramDto addFinancialGrant(Long programId, Long financialGrantIdValue) {
        Program program = findProgramById(programId);
        FinancialGrant financialGrant = findFinancialGrantById(financialGrantIdValue);
        if (program.getFinancialGrants().contains(financialGrant)) {
            throw new IllegalArgumentException("Financial grant is already associated with program");
        }
        program.getFinancialGrants().add(financialGrant);
        return new ProgramDto(programRepository.save(program));
        
    }

    @Transactional(readOnly = false)
    public ProgramDto addDonation(Long programId, Long donationIdValue) {
        Program program = findProgramById(programId);
        Donation donation = findDonationById(donationIdValue);
        if (program.getDonations().contains(donation)) {
            throw new IllegalArgumentException("Donation is already associated with program");
        }
        program.getDonations().add(donation);
        return new ProgramDto(programRepository.save(program));
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
