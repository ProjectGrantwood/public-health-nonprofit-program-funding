package com.publichealthnonprofit.programfunding.controller.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publichealthnonprofit.programfunding.controller.model.DonorData;
import com.publichealthnonprofit.programfunding.dao.DonorDao;
import com.publichealthnonprofit.programfunding.entity.Donor;

@Service
public class DonorService {
    
    @Autowired
    private DonorDao donorDao;

    @Transactional(readOnly = true)
    public List<DonorData> getAllDonors() {
        return donorDao.findAll().stream().map(DonorData::new).toList();
    }
    
    @Transactional(readOnly = true)
    public DonorData getDonorById(Long donorId) {
        return new DonorData(findDonorById(donorId));
    }

    @Transactional(readOnly = false)
    public DonorData createDonor(Donor donor) {
        return saveDonorFromDonorData(new DonorData(donor));
    }

    @Transactional(readOnly = false)
    public DonorData updateDonor(Long donorId, Donor donor) {
        Donor donorToUpdate = findDonorById(donorId);
        setUpdatedFieldsInDonor(donorToUpdate, new DonorData(donor));
        return new DonorData(donorDao.save(donorToUpdate));
    }

    @Transactional(readOnly = false)
    public void deleteDonor(Long donorId) {
        Donor donor = findDonorById(donorId);
        if (donor.getDonations().isEmpty()) {
            donorDao.delete(donor);
        } else {
            throw new IllegalArgumentException("Donor with id " + donorId + " cannot be deleted because they have donations associated with them.");
        }
    }
    
    @Transactional(readOnly = false)
    private DonorData saveDonorFromDonorData(DonorData donorData) {
        Long donorId = donorData.getDonorId();
        Donor donor = findOrCreateDonor(donorId);
        setFieldsInDonor(donor, donorData);
        return new DonorData(donorDao.save(donor));
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
        return donorDao.findById(donorId).orElseThrow(() -> new NoSuchElementException("Donor not found for id: " + donorId));
    }
    
    //Method for setting the fields in a Donor entity based on a DonorData object
    private void setFieldsInDonor(Donor donor, DonorData donorData) {
        donor.setDonorName(donorData.getDonorName());
        donor.setDonorEmail(donorData.getDonorEmail());
        donor.setDonorPhone(donorData.getDonorPhone());
        donor.setDonorAddress(donorData.getDonorAddress());
        donor.setDonorAffiliation(donorData.getDonorAffiliation());
    }
    
    // Method for updating fields in a Donor entity only if those fields were present in the requestBody
    private void setUpdatedFieldsInDonor(Donor donor, DonorData donorData) {
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
