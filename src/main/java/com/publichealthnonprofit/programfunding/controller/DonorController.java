package com.publichealthnonprofit.programfunding.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.publichealthnonprofit.programfunding.dto.DonorDto;
import com.publichealthnonprofit.programfunding.model.Donor;
import com.publichealthnonprofit.programfunding.service.DonorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Donor")
@RestController
@RequestMapping("/program_funding")
@Slf4j
public class DonorController {

    @Autowired
    private DonorService donorService;

    // GET /donor

    @Operation(summary = "Get all donors", description = "Retrieve a list of all donors in the database.")
    @GetMapping("/donor")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DonorDto> getAllDonors() {
        log.info("Getting all donors...");
        return donorService.getAllDonors();
    }

    // Post /donor

    @Operation(summary = "Create a new donor", description = "Add a new donor to the database.")
    @PostMapping("/donor")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DonorDto createDonor(@RequestBody Donor donor) {
        log.info("Creating donor...");
        return donorService.createDonor(donor);
    }

    // GET /donor/{donorId}

    @Operation(summary = "Get a specific donor", description = "Retrieve a donor from the database by their numeric ID.")
    @GetMapping("/donor/{donorId}")
    @ResponseStatus(code = HttpStatus.OK)
    public DonorDto getDonorById(@PathVariable Long donorId) {
        log.info("Getting donor by ID {}...", donorId);
        return donorService.getDonorById(donorId);
    }

    // PUT /donor/{donorId}

    @Operation(summary = "Update a specific donor", description = "Update a donor in the database by their numeric ID.")
    @PutMapping("/donor/{donorId}")
    @ResponseStatus(code = HttpStatus.OK)
    public DonorDto updateDonor(@PathVariable Long donorId, @RequestBody Donor donor) {
        log.info("Updating donor with ID {}...", donorId);
        return donorService.updateDonor(donorId, donor);
    }

    // DELETE /donor/{donorId}

    @Operation(summary = "Delete a specific donor", description = "Delete a donor from the database specified by their numeric ID.")
    @DeleteMapping("/donor/{donorId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteDonor(@PathVariable Long donorId) {
        log.info("Deleting donor with ID {}...", donorId);
        donorService.deleteDonor(donorId);
    }

}
