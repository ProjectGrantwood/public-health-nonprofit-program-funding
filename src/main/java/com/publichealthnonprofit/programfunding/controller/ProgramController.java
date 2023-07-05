package com.publichealthnonprofit.programfunding.controller;

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

import com.publichealthnonprofit.programfunding.dto.ProgramDto;
import com.publichealthnonprofit.programfunding.model.Program;
import com.publichealthnonprofit.programfunding.service.ProgramService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Program")
@RestController
@RequestMapping("/program_funding")
@Slf4j
public class ProgramController {

    @Autowired
    private ProgramService programService;

    // GET /program

    @Operation(summary = "Get all programs", description = "Get all programs. Optionally, filter by donor, granting organization, donation, or financial grant.")
    @GetMapping("/program")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ProgramDto> getAllPrograms(
            @Parameter(description = "Search all programs to which a specific donor has made at least one donation") @RequestParam(required = false) Optional<Long> donorId,
            @Parameter(description = "Search all programs to which at least one grant from a specific organization has been applied") @RequestParam(required = false) Optional<Long> grantingOrgId,
            @Parameter(description = "Search all programs associated with a specific donation") @RequestParam(required = false) Optional<Long> donationId,
            @Parameter(description = "Search all programs associated with a specific grant") @RequestParam(required = false) Optional<Long> financialGrantId) {
        /*
         * We need to check if more than one of the query parameters is present. If so,
         * we need to throw an error.
         * We don't care which ones are present, just that the total number of them is
         * less than or equal to 1.
         * The DRYest way to do this is to put them in an array, loop through it
         * counting the numbe of true values,
         * and throw an error directly in the loop body if the count is ever greater
         * than 1.
         */
        boolean[] queryParamPresent = {
                donorId.isPresent(),
                grantingOrgId.isPresent(),
                donationId.isPresent(),
                financialGrantId.isPresent()
        };
        int trueCount = 0;
        for (boolean queryParam : queryParamPresent) {
            if (queryParam) {
                trueCount++;
                if (trueCount > 1) {
                    log.warn("More than one query parameter is present. Only one is allowed at a time.");
                    throw new IllegalArgumentException(
                            "More than one query parameter is present. Only one is allowed at a time.");
                }
            }
        }
        /*
         * Since we already have the presence of each query parameter stored in an
         * array,
         * we'll just reference the array instead of calling isPresent() again.
         */
        if (queryParamPresent[0]) {
            Long donorIdValue = donorId.get();
            log.info("Getting all programs for donor ID {}...", donorIdValue);
            return programService.getAllProgramsByDonorId(donorIdValue);
        } else if (queryParamPresent[1]) {
            Long grantingOrgIdValue = grantingOrgId.get();
            log.info("Getting all programs for granting org ID {}...", grantingOrgIdValue);
            return programService.getAllProgramsByGrantingOrgId(grantingOrgIdValue);
        } else if (queryParamPresent[2]) {
            Long donationIdValue = donationId.get();
            log.info("Getting all programs for donation ID {}...", donationIdValue);
            return programService.getAllProgramsByDonationId(donationIdValue);
        } else if (queryParamPresent[3]) {
            Long financialGrantIdValue = financialGrantId.get();
            log.info("Getting all programs for financial grant ID {}...", financialGrantIdValue);
            return programService.getAllProgramsByFinancialGrantId(financialGrantIdValue);
        }
        // Finally, if no query params are present, we just get all programs.
        log.info("Getting all programs...");
        return programService.getAllProgramsAsProgramData();
    }

    // POST /program

    @Operation(summary = "Create a new program", description = "Create a new program.")
    @PostMapping("/program")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProgramDto createProgram(@RequestBody Program program) {
        log.info("Creating program...");
        return programService.createProgram(program);
    }

    // GET /program/{programId}

    @Operation(summary = "Get a specific program", description = "Get a program by its associated ID.")
    @GetMapping("/program/{programId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ProgramDto getProgramById(@PathVariable Long programId) {
        log.info("Getting program with ID {}...", programId);
        return programService.getProgramById(programId);
    }

    // PUT /program/{programId}

    @Operation(summary = "Update a specific program", description = "Update a program by its associated ID. Optionally, associate a financial grant or donation with the program.")
    @PutMapping("/program/{programId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ProgramDto updateProgram(
            @PathVariable Long programId,
            @RequestBody(required = false) Program program,
            @Parameter(description = "Associate a grant specified by numeric ID with the program") @RequestParam(required = false) Optional<Long> financialGrantId,
            @Parameter(description = "Assocaite a donation specified by numeric ID with the program") @RequestParam(required = false) Optional<Long> donationId) {
        log.info("Updating program with ID {}...", programId);
        boolean financialGrantIdPresent = financialGrantId.isPresent();
        boolean donationIdPresent = donationId.isPresent();
        if (financialGrantIdPresent && donationIdPresent) {
            log.warn("Both financial grant ID and donation ID are present. Only one is allowed at a time.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Both financial grant ID and donation ID are present. Only one is allowed at a time.");
        } else if (financialGrantIdPresent) {
            Long financialGrantIdValue = financialGrantId.get();
            log.info("Adding financial grant with ID {} to program with ID {}...", financialGrantIdValue, programId);
            return programService.addFinancialGrant(programId, financialGrantIdValue);
        } else if (donationIdPresent) {
            Long donationIdValue = donationId.get();
            log.info("Adding donation with ID {} to program with ID {}...", donationIdValue, programId);
            return programService.addDonation(programId, donationIdValue);
        }
        return programService.updateProgram(programId, program);
    }

    // DELETE /program/{programId}

    @Hidden
    @DeleteMapping("/program/{programId}")
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteProgram(@PathVariable Long programId) {
        log.warn("Deleting a program is not a supported operation.");
        throw new UnsupportedOperationException("Deleting a program is not a supported operation.");
    }

    // DELETE /program

    @Hidden
    @DeleteMapping("/program")
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteAllPrograms() {
        log.warn("Deleting all programs is not a supported operation.");
        throw new UnsupportedOperationException("Deleting all programs is not a supported operation.");
    }
}
