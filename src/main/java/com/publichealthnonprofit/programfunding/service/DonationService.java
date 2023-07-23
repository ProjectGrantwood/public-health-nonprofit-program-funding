package com.publichealthnonprofit.programfunding.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publichealthnonprofit.programfunding.dto.DonationDto;
import com.publichealthnonprofit.programfunding.dto.DonationDto.DonationDonor;
import com.publichealthnonprofit.programfunding.dto.DonationDto.DonationProgram;
import com.publichealthnonprofit.programfunding.entity.Donation;
import com.publichealthnonprofit.programfunding.entity.Donor;
import com.publichealthnonprofit.programfunding.entity.Program;
import com.publichealthnonprofit.programfunding.repository.DonationRepository;
import com.publichealthnonprofit.programfunding.repository.DonorRepository;
import com.publichealthnonprofit.programfunding.repository.ProgramRepository;

@Service
public class DonationService {
    
    @Autowired
    private DonationRepository donationRepository;
    
    @Autowired
    private DonorRepository donorRepository;
    
    @Autowired
    private ProgramRepository programRepository;

    @Transactional(readOnly = true)
    public List<DonationDto> getAllDonationsByDonorId(Long donorIdValue) {
        List<DonationDto> allDonations = getAllDonations();
        return allDonations.stream().filter(donation -> donation.getDonor().getDonorId().equals(donorIdValue)).toList();
    }

    @Transactional(readOnly = true)
    public List<DonationDto> getAllDonationsByProgramId(Long programIdValue) {
        List<DonationDto> allDonations = getAllDonations();
        return allDonations.stream().filter(donation -> donation.getPrograms().stream().anyMatch(program -> program.getProgramId().equals(programIdValue))).toList();
    }

    @Transactional(readOnly = true)
    public List<DonationDto> getAllDonations() {
        return donationRepository.findAll().stream().map(DonationDto::new).toList();
    }

    @Transactional(readOnly = false)
    public DonationDto createDonation(Donation donation, Long donorIdValue) {
        Donor donor = findDonorById(donorIdValue);
        donation.setDonor(donor);
        return saveDonationFromDonationData(new DonationDto(donation));
    }
    
    @Transactional(readOnly = false)
    public DonationDto createDonation(Donation donation, Long donorIdValue, Long programIdValue) {
        Donor donor = findDonorById(donorIdValue);
        donation.setDonor(donor);
        return saveDonationFromDonationData(new DonationDto(donation), programIdValue);
    }
    
    private DonationDto saveDonationFromDonationData(DonationDto donationData) {
        Long donationId = donationData.getDonationId();
        Donation donation = findOrCreateDonation(donationId);
        setFieldsInDonation(donation, donationData);
        return new DonationDto(donationRepository.save(donation));
    }
    
    @Transactional(readOnly = false)
    public DonationDto saveDonationFromDonationData(DonationDto donationData, Long programIdValue) {
        Long donationId = donationData.getDonationId();
        Donation donation = findOrCreateDonation(donationId);
        setFieldsInDonation(donation, donationData, programIdValue);
        return new DonationDto(donationRepository.save(donation));
    }

    private void setFieldsInDonation(Donation donation, DonationDto donationData) {
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
    
    private void setFieldsInDonation(Donation donation, DonationDto donationData, Long programIdValue) {
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
    public DonationDto updateDonation(Long donationId, Donation donation) {
        Donation donationToUpdate = findDonationById(donationId);
        setUpdatedFieldsInDonation(donationToUpdate, new DonationDto(donation));
        return new DonationDto(donationRepository.save(donationToUpdate));
        
    }

    @Transactional(readOnly = true)
    public DonationDto getDonationById(Long donationId) {
        return new DonationDto(findDonationById(donationId));
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
        return donationRepository.findById(donationId).orElseThrow(() -> new NoSuchElementException("Donation not found for id " + donationId));
    }
    
    private void setUpdatedFieldsInDonation(Donation donation, DonationDto donationData) {
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
        return programRepository.findById(programId).orElseThrow(() -> new NoSuchElementException("Program not found for id " + programId));
    }
    
    @Transactional(readOnly = true)
    public Donor findDonorById(Long donorId){
        return donorRepository.findById(donorId).orElseThrow(() -> new NoSuchElementException("Donor not found for id " + donorId));
    }
    
    @Transactional(readOnly = false)
    public void deleteDonation(Long donationId) {
        if (donationRepository.existsById(donationId)) {
            
            // remove donation from all associated programs
            Donation donation = findDonationById(donationId);
            
            List<Program> programs = programRepository.findByDonationId(donationId);
            
            // This is slow and inefficient.  It would be better to have a set of queries that deletes the association
            // I would attempt this as described here: https://thorben-janssen.com/avoid-cascadetype-delete-many-assocations/
            for (Program program : programs) {
                program.getDonations().remove(donation);
                programRepository.save(program);
            }
            
            donationRepository.deleteById(donationId);
        } else {
            throw new NoSuchElementException("Donation not found for id " + donationId);
        }
    }
    
}
