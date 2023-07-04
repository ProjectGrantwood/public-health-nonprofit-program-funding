package com.publichealthnonprofit.programfunding.controller.endpointmappings;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Donation")
@RestController
@RequestMapping("/program_funding")
@Slf4j
public class DonationController {

    @Autowired
    private DonationService donationService;

    // GET /donation

    @Operation(summary = "Get all donations", description = "Get all donations, or get all donations for a specific donor or program.")
    @GetMapping("/donation")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DonationData> getAllDonations(@RequestParam(required = false) Optional<Long> donorId,
            @RequestParam(required = false) Optional<Long> programId) {
        if (donorId.isPresent() && programId.isPresent()) {
            log.warn("Donor ID and Program ID are both present. Only one is allowed at a time.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Donor ID and Program ID are both present. Only one is allowed at a time.");
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

    // POST /donation
    @Operation(summary = "Create a donation", description = "Create a donation, optionally specifying the program with which it is associated.")
    @PostMapping("/donation")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DonationData createDonation(
            @Parameter(description = "The donor associated with this donation, specified by numeric ID.") @RequestParam(required = true) Optional<Long> donorId,
            @Parameter(description = "The program with which to optionally associate this donation, specified by numeric ID.") @RequestParam(required = false) Optional<Long> programId,
            @RequestBody Donation donation) {
        if (donorId.isEmpty()) {
            log.warn("Donor ID is required.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Donor ID is required.");
        }
        log.info("Creating donation...");
        return programId.isPresent() ? donationService.createDonation(donation, donorId.get(), programId.get())
                : donationService.createDonation(donation, donorId.get());
    }

    // GET /donation/{donationId}

    @Operation(summary = "Get a specific donation", description = "Search for a donation specified by its numeric ID.")
    @GetMapping("/donation/{donationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public DonationData getDonationById(@PathVariable Long donationId) {
        log.info("Getting donation by ID {}...", donationId);
        return donationService.getDonationById(donationId);
    }

    // PUT /donation/{donationId}

    @Operation(summary = "Update a specific donation", description = "Update a donation specified by its numeric ID.")
    @PutMapping("/donation/{donationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public DonationData updateDonation(@PathVariable Long donationId, @RequestBody Donation donation) {
        log.info("Updating donation with ID {}...", donationId);
        return donationService.updateDonation(donationId, donation);
    }
    
    //DELETE /donation/{donationId}
    
    @Operation(summary = "Delete a specific donation", description = "Delete a donation specified by its numeric ID.")
    @DeleteMapping("/donation/{donationId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteDonation(@PathVariable Long donationId) {
        log.info("Deleting donation with ID {}...", donationId);
        donationService.deleteDonation(donationId);
    }

}
