package com.publichealthnonprofit.programfunding.controller.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.publichealthnonprofit.programfunding.controller.model.ProgramData;
import com.publichealthnonprofit.programfunding.dao.ProgramDao;
import com.publichealthnonprofit.programfunding.entity.Program;

@Service
public class ProgramService {
    
    @SuppressWarnings("unused")
    @Autowired
    private ProgramDao programDao;

    public List<ProgramData> getAllPrograms() {
        return null;
    }

    public List<ProgramData> getAllProgramsByFinancialGrantId(Long financialGrantIdValue) {
        return null;
    }


    public List<ProgramData> getAllProgramsByDonationId(Long donationIdValue) {
        return null;
    }

    public List<ProgramData> getAllProgramsByGrantingOrgId(Long grantingOrgIdValue) {
        return null;
    }

    public List<ProgramData> getAllProgramsByDonorId(Long donorIdValue) {
        return null;
    }
    
    public ProgramData createProgram(ProgramData program) {
        return null;
    }
    
    public ProgramData getProgramById(Long programId) {
        return null;
    }
    
    public ProgramData updateProgram(Long programId, ProgramData program) {
        return null;
    }
    
    public Program findProgramById(Long programId) {
        return programDao.findById(programId).orElseThrow(() -> new NoSuchElementException("Program not found with id " + programId));
    }
    
}
