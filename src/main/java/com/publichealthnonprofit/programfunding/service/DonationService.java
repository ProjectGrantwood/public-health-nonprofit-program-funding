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

import com.publichealthnonprofit.programfunding.controller.model.DonationData;
import com.publichealthnonprofit.programfunding.controller.model.DonationData.DonationDonor;
import com.publichealthnonprofit.programfunding.controller.model.DonationData.DonationProgram;
import com.publichealthnonprofit.programfunding.dao.DonationDao;
import com.publichealthnonprofit.programfunding.dao.DonorDao;
import com.publichealthnonprofit.programfunding.dao.ProgramDao;
import com.publichealthnonprofit.programfunding.entity.Donation;
import com.publichealthnonprofit.programfunding.entity.Donor;
import com.publichealthnonprofit.programfunding.entity.Program;

@Service
public class DonationService {
    
    @Autowired
    private DonationDao donationDao;
    
    @Autowired
    private DonorDao donorDao;
    
    @Autowired
    private ProgramDao programDao;

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
    public DonationData createDonation(Donation donation, Long donorIdValue) {
        Donor donor = findDonorById(donorIdValue);
        donation.setDonor(donor);
        return saveDonationFromDonationData(new DonationData(donation));
    }
    
    @Transactional(readOnly = false)
    public DonationData createDonation(Donation donation, Long donorIdValue, Long programIdValue) {
        Donor donor = findDonorById(donorIdValue);
        donation.setDonor(donor);
        return saveDonationFromDonationData(new DonationData(donation), programIdValue);
    }
    
    private DonationData saveDonationFromDonationData(DonationData donationData) {
        Long donationId = donationData.getDonationId();
        Donation donation = findOrCreateDonation(donationId);
        setFieldsInDonation(donation, donationData);
        return new DonationData(donationDao.save(donation));
    }
    
    @Transactional(readOnly = false)
    public DonationData saveDonationFromDonationData(DonationData donationData, Long programIdValue) {
        Long donationId = donationData.getDonationId();
        Donation donation = findOrCreateDonation(donationId);
        setFieldsInDonation(donation, donationData, programIdValue);
        return new DonationData(donationDao.save(donation));
    }

    private void setFieldsInDonation(Donation donation, DonationData donationData) {
        donation.setDonor(findDonorById(donationData.getDonor().getDonorId()));
        Set<Program> programs = new HashSet<>();
        List<DonationProgram> donationPrograms = donationData.getPrograms();
        for (DonationProgram donationProgram : donationPrograms) {
            programs.add(findProgramById(donationProgram.getProgramId()));
        }
        donation.setPrograms(programs);
        donation.setDonationAmount(donationData.getDonationAmount());
        donation.setDonationDate(donationData.getDonationDate());
    }
    
    private void setFieldsInDonation(Donation donation, DonationData donationData, Long programIdValue) {
        donation.setDonor(findDonorById(donationData.getDonor().getDonorId()));
        Set<Program> programs = new HashSet<>();
        List<DonationProgram> donationPrograms = donationData.getPrograms();
        for (DonationProgram donationProgram : donationPrograms) {
            programs.add(findProgramById(donationProgram.getProgramId()));
        }
        Program program = findProgramById(programIdValue);
        programs.add(program);
        donation.setPrograms(programs);
        donation.setDonationAmount(donationData.getDonationAmount());
        donation.setDonationDate(donationData.getDonationDate());
        Set<Donation> programDonations = program.getDonations();
        programDonations.add(donation);
        program.setDonations(programDonations);
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
    
    private Donation findOrCreateDonation(Long donationId) {
        Donation donation;
        if (Objects.isNull(donationId)) {
            donation = new Donation();
        } else {
            donation = findDonationById(donationId);
        }
        return donation;
    }
    
    @Transactional(readOnly = true)
    public Donation findDonationById(Long donationId) {
        return donationDao.findById(donationId).orElseThrow(() -> new NoSuchElementException("Donation not found for id " + donationId));
    }
    
    private void setUpdatedFieldsInDonation(Donation donation, DonationData donationData) {
        DonationDonor updatedDonor = donationData.getDonor();
        Date updatedDonationDate = donationData.getDonationDate();
        Double updatedDonationAmount = donationData.getDonationAmount();
        if (Objects.nonNull(updatedDonor)) {
            donation.setDonor(findDonorById(updatedDonor.getDonorId()));
        }
        if (Objects.nonNull(updatedDonationDate)) {
            donation.setDonationDate(updatedDonationDate);
        }
        if (Objects.nonNull(updatedDonationAmount)) {
            donation.setDonationAmount(updatedDonationAmount);
        }
    }
    
    @Transactional(readOnly = true)
    public Program findProgramById(Long programId){
        return programDao.findById(programId).orElseThrow(() -> new NoSuchElementException("Program not found for id " + programId));
    }
    
    @Transactional(readOnly = true)
    public Donor findDonorById(Long donorId){
        return donorDao.findById(donorId).orElseThrow(() -> new NoSuchElementException("Donor not found for id " + donorId));
    }
    
    @Transactional(readOnly = false)
    public void deleteDonation(Long donationId) {
        if (donationDao.existsById(donationId)) {
            donationDao.deleteById(donationId);
        } else {
            throw new NoSuchElementException("Donation not found for id " + donationId);
        }
    }
    
}
