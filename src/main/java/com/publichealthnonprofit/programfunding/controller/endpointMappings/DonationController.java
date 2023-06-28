package com.publichealthnonprofit.programfunding.controller.endpointmappings;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.publichealthnonprofit.programfunding.controller.model.DonationData;
import com.publichealthnonprofit.programfunding.controller.service.DonationService;
import com.publichealthnonprofit.programfunding.entity.Donation;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/program_funding")
@Slf4j
public class DonationController {
    
    @Autowired
    private DonationService donationService;
    
    @GetMapping("/donation")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DonationData> getAllDonations(@RequestParam(required = false) Optional<Long> donorId, @RequestParam(required = false) Optional<Long> programId) {
        if (donorId.isPresent() && programId.isPresent()) {
            log.warn("Donor ID and Program ID are both present. Only one is allowed at a time.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Donor ID and Program ID are both present. Only one is allowed at a time.");
        } else if (donorId.isPresent()) {
            Long donorIdValue = donorId.get();
            log.info("Getting all donations for donor ID {}...", donorIdValue);
            return donationService.getAllDonationsByDonorId(donorIdValue);
        } else if (programId.isPresent()) {
            Long programIdValue = programId.get();
            log.info("Getting all donations for program ID {}...", programIdValue);
            return donationService.getAllDonationsByProgramId(programIdValue);
        }
        log.info("Getting all donations...");
        return donationService.getAllDonations();
    }
    
    @PostMapping("/donation")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DonationData createDonation(@RequestParam(required = true) Optional<Long> donorId, @RequestParam(required = false) Optional<Long> programId, @RequestBody Donation donation) {
        if (donorId.isEmpty()){
            log.warn("Donor ID is required.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Donor ID is required.");
        }
        log.info("Creating donation...");
        return programId.isPresent() ? donationService.createDonation(donation, donorId.get(), programId.get()) : donationService.createDonation(donation, donorId.get());
    }
    
    @GetMapping("/donation/{donationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public DonationData getDonationById(@PathVariable Long donationId) {
        log.info("Getting donation by ID {}...", donationId);
        return donationService.getDonationById(donationId);
    }
    
    @PutMapping("/donation/{donationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public DonationData updateDonation(@PathVariable Long donationId, @RequestBody Donation donation) {
        log.info("Updating donation with ID {}...", donationId);
        return donationService.updateDonation(donationId, donation);
    }
    
}
