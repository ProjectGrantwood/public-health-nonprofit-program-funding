package com.publichealthnonprofit.programfunding.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publichealthnonprofit.programfunding.dto.DonorDto;
import com.publichealthnonprofit.programfunding.entity.Donor;
import com.publichealthnonprofit.programfunding.repository.DonorRepository;

@Service
public class DonorService {
    
    @Autowired
    private DonorRepository donorRepository;

    @Transactional(readOnly = true)
    public List<DonorDto> getAllDonors() {
        return donorRepository.findAll().stream().map(DonorDto::new).toList();
    }
    
    @Transactional(readOnly = true)
    public DonorDto getDonorById(Long donorId) {
        return new DonorDto(findDonorById(donorId));
    }

    @Transactional(readOnly = false)
    public DonorDto createDonor(Donor donor) {
        return saveDonorFromDonorData(new DonorDto(donor));
    }

    @Transactional(readOnly = false)
    public DonorDto updateDonor(Long donorId, Donor donor) {
        Donor donorToUpdate = findDonorById(donorId);
        setUpdatedFieldsInDonor(donorToUpdate, new DonorDto(donor));
        return new DonorDto(donorRepository.save(donorToUpdate));
    }

    @Transactional(readOnly = false)
    public void deleteDonor(Long donorId) {
        Donor donor = findDonorById(donorId);
        if (donor.getDonations().isEmpty()) {
            donorRepository.delete(donor);
        } else {
            throw new IllegalArgumentException("Donor with id " + donorId + " cannot be deleted because they have donations associated with them.");
        }
    }
    
    @Transactional(readOnly = false)
    public DonorDto saveDonorFromDonorData(DonorDto donorData) {
        Long donorId = donorData.getDonorId();
        Donor donor = findOrCreateDonor(donorId);
        setFieldsInDonor(donor, donorData);
        return new DonorDto(donorRepository.save(donor));
    }
    
    // Method for finding or creating a donor entity
    private Donor findOrCreateDonor(Long donorId){
        Donor donor;
        if (Objects.isNull(donorId)) {
            donor = new Donor();
        } else {
            donor = findDonorById(donorId);
        }
        return donor;
    }
    
    // Method for getting an actual donor entity
    public Donor findDonorById(Long donorId){
        return donorRepository.findById(donorId).orElseThrow(() -> new NoSuchElementException("Donor not found for id: " + donorId));
    }
    
    //Method for setting the fields in a Donor entity based on a DonorData object
    private void setFieldsInDonor(Donor donor, DonorDto donorData) {
        donor.setDonorName(donorData.getDonorName());
        donor.setDonorEmail(donorData.getDonorEmail());
        donor.setDonorPhone(donorData.getDonorPhone());
        donor.setDonorAddress(donorData.getDonorAddress());
        donor.setDonorAffiliation(donorData.getDonorAffiliation());
    }
    
    // Method for updating fields in a Donor entity only if those fields were present in the requestBody
    private void setUpdatedFieldsInDonor(Donor donor, DonorDto donorData) {
        String updatedDonorName = donorData.getDonorName();
        String updatedDonorEmail = donorData.getDonorEmail();
        String updatedDonorPhone = donorData.getDonorPhone();
        String updatedDonorAddress = donorData.getDonorAddress();
        String updatedDonorAffiliation = donorData.getDonorAffiliation();
        if (Objects.nonNull(updatedDonorName)) {
            donor.setDonorName(updatedDonorName);
        }
        if (Objects.nonNull(updatedDonorEmail)) {
            donor.setDonorEmail(updatedDonorEmail);
        }
        if (Objects.nonNull(updatedDonorPhone)) {
            donor.setDonorPhone(updatedDonorPhone);
        }
        if (Objects.nonNull(updatedDonorAddress)) {
            donor.setDonorAddress(updatedDonorAddress);
        }
        if (Objects.nonNull(updatedDonorAffiliation)) {
            donor.setDonorAffiliation(updatedDonorAffiliation);
        }
    }
    
}
