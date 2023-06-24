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

import com.publichealthnonprofit.programfunding.controller.model.DonationData;
import com.publichealthnonprofit.programfunding.controller.model.DonationData.DonationDonor;
import com.publichealthnonprofit.programfunding.controller.model.DonationData.DonationProgram;
import com.publichealthnonprofit.programfunding.dao.DonationDao;
import com.publichealthnonprofit.programfunding.entity.Donation;
import com.publichealthnonprofit.programfunding.entity.Program;

@Service
public class DonationService {
    
    @Autowired
    private DonationDao donationDao;
    
    @Autowired
    private DonorService donorService;
    
    @Autowired
    private ProgramService programService;

    @Transactional(readOnly = true)
    public List<DonationData> getAllDonationsByDonorId(Long donorIdValue) {
        List<DonationData> allDonations = getAllDonations();
        return allDonations.stream().filter(donation -> donation.getDonor().getDonorId().equals(donorIdValue)).toList();
    }

    @Transactional(readOnly = true)
    public List<DonationData> getAllDonationsByProgramId(Long programIdValue) {
        List<DonationData> allDonations = getAllDonations();
        return allDonations.stream().filter(donation -> donation.getPrograms().stream().anyMatch(program -> program.getProgramId().equals(programIdValue))).toList();
        
    }

    @Transactional(readOnly = true)
    public List<DonationData> getAllDonations() {
        return donationDao.findAll().stream().map(DonationData::new).toList();
    }

    @Transactional(readOnly = false)
    public DonationData createDonation(Donation donation) {
        return saveDonationFromDonationData(new DonationData(donation));
    }
    
    @Transactional(readOnly = false)
    public DonationData createDonation(Donation donation, Long programIdValue) {
        DonationData donationData = new DonationData(donation);
        donationData.setPrograms(List.of(new DonationProgram(programService.findProgramById(programIdValue))));
        return saveDonationFromDonationData(donationData);
    }

    @Transactional(readOnly = false)
    public DonationData updateDonation(Long donationId, Donation donation) {
        Donation donationToUpdate = findDonationById(donationId);
        setUpdatedFieldsInDonation(donationToUpdate, new DonationData(donation));
        return new DonationData(donationDao.save(donationToUpdate));
        
    }

    @Transactional(readOnly = true)
    public DonationData getDonationById(Long donationId) {
        return new DonationData(findDonationById(donationId));
    }
    
    @Transactional(readOnly = false)
    private DonationData saveDonationFromDonationData(DonationData donationData) {
        Long donationId = donationData.getDonationId();
        Donation donation = findOrCreateDonation(donationId);
        setFieldsInDonation(donation, donationData);
        return new DonationData(donationDao.save(donation));
    }
    
    private Donation findOrCreateDonation(Long donationId) {
        Donation donation;
        if (Objects.isNull(donationId)) {
            donation = new Donation();
        } else {
            donation = findDonationById(donationId);
        }
        return donation;
    }
    
    private Donation findDonationById(Long donationId) {
        return donationDao.findById(donationId).orElseThrow(() -> new NoSuchElementException("Donation not found for id " + donationId));
    }
    
    private void setFieldsInDonation(Donation donation, DonationData donationData) {
        donation.setDonor(donorService.findDonorById(donationData.getDonor().getDonorId()));
        Set<Program> programs = new HashSet<>();
        List<DonationProgram> donationPrograms = donationData.getPrograms();
        for (DonationProgram donationProgram : donationPrograms) {
            programs.add(programService.findProgramById(donationProgram.getProgramId()));
        }
        donation.setPrograms(programs);
        donation.setDonationAmount(donationData.getDonationAmount());
        donation.setDonationDate(donationData.getDonationDate());
    }
    
    private void setUpdatedFieldsInDonation(Donation donation, DonationData donationData) {
        DonationDonor updatedDonor = donationData.getDonor();
        Date updatedDonationDate = donationData.getDonationDate();
        Float updatedDonationAmount = donationData.getDonationAmount();
        List<DonationProgram> donationPrograms = donationData.getPrograms();
        if (Objects.nonNull(updatedDonor)) {
            donation.setDonor(donorService.findDonorById(updatedDonor.getDonorId()));
        }
        if (Objects.nonNull(updatedDonationDate)) {
            donation.setDonationDate(updatedDonationDate);
        }
        if (Objects.nonNull(updatedDonationAmount)) {
            donation.setDonationAmount(updatedDonationAmount);
        }
        if (Objects.nonNull(donationPrograms)) {
            Set<Program> programs = new HashSet<>();
            for (DonationProgram donationProgram : donationPrograms) {
                Program program = programService.findProgramById(donationProgram.getProgramId());
                if (!donation.getPrograms().contains(program)) {
                    programs.add(program);
                }
            }
            donation.setPrograms(programs);
        }
        
    }
    
}
