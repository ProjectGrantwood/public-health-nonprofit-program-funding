package com.publichealthnonprofit.programfunding.controller.endpoints;

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

import com.publichealthnonprofit.programfunding.controller.model.FinancialGrantData;
import com.publichealthnonprofit.programfunding.controller.service.FinancialGrantService;
import com.publichealthnonprofit.programfunding.entity.FinancialGrant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Grant")
@RestController
@RequestMapping("/program_funding")
@Slf4j
public class FinancialGrantController {
    
    @Autowired
    private FinancialGrantService financialGrantService;
    
    // GET /financial_grant
    
    @Operation(
        summary = "Get all financial grants",
        description = "Get all financial grants, optionally filtered by granting organization ID or program ID."
    )
    @GetMapping("/financial_grant")
    @ResponseStatus(code = HttpStatus.OK)
    public List<FinancialGrantData> getAllFinancialGrants(
        @Parameter(description = "Search all grants by Granting Organization") @RequestParam(required = false) Optional<Long> grantingOrgId, 
        @Parameter(description = "Search all grants associated with a specific program") @RequestParam(required = false) Optional<Long> programId) {
        if (grantingOrgId.isPresent() && programId.isPresent()) {
            log.warn("Granting organization ID and Program ID are both present. Only one is allowed at a time.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Granting organization ID and Program ID are both present. Only one is allowed at a time.");
        } else if (grantingOrgId.isPresent()) {
            Long grantingOrgIdValue = grantingOrgId.get();
            log.info("Getting all financial grants for granting organization ID {}...", grantingOrgIdValue);
            return financialGrantService.getAllFinancialGrantsByGrantingOrgId(grantingOrgIdValue);
        } else if (programId.isPresent()) {
            Long programIdValue = programId.get();
            log.info("Getting all financial grants for program ID {}...", programIdValue);
            return financialGrantService.getAllFinancialGrantsByProgramId(programIdValue);
        }
        log.info("Getting all financial grants...");
        return financialGrantService.getAllFinancialGrants();
    }
    
    //POST /financial_grant
    
    @Operation(
        summary = "Create a financial grant",
        description = "Create a financial grant, optionally associated with a specific program."
    )
    @PostMapping("/financial_grant")
    @ResponseStatus(code = HttpStatus.CREATED)
    public FinancialGrantData createFinancialGrant(
        @Parameter(description = "The organization through which the grant originates", required = true) @RequestParam(required = true) Optional<Long> grantingOrgId, 
        @Parameter(description = "The program with which the grant is associated") @RequestParam(required = false) Optional<Long> programId, @RequestBody FinancialGrant financialGrant) {
        if (grantingOrgId.isEmpty()){
            log.warn("Granting organization ID is required.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Granting organization ID is required.");
        }
        if (programId.isPresent()) {
            Long programIdValue = programId.get();
            log.info("Creating grant associated with program ID {}...", programIdValue);
            return financialGrantService.createFinancialGrant(financialGrant, grantingOrgId.get(), programIdValue);
        }
        log.info("Creating financial grant...");
        return financialGrantService.createFinancialGrant(financialGrant, grantingOrgId.get());
    }
    
    //GET /financial_grant/{financialGrantId}
    
    @Operation(
        summary = "Search for a specific grant",
        description = "Search for a specific grant by numerical ID."
    )
    @GetMapping("/financial_grant/{financialGrantId}")
    @ResponseStatus(code = HttpStatus.OK)
    public FinancialGrantData getFinancialGrantById(@PathVariable Long financialGrantId) {
        log.info("Getting financial grant with ID {}...", financialGrantId);
        return financialGrantService.getFinancialGrantById(financialGrantId);
    }
    
    //PUT /financial_grant/{financialGrantId}
    
    @Operation(
        summary = "Update a specific grant",
        description = "Update a specific grant by numerical ID."
    )
    @PutMapping("/financial_grant/{financialGrantId}")
    @ResponseStatus(code = HttpStatus.OK)
    public FinancialGrantData updateFinancialGrant(@PathVariable Long financialGrantId, @RequestBody FinancialGrant financialGrant) {
        log.info("Updating financial grant with ID {}...", financialGrantId);
        return financialGrantService.updateFinancialGrant(financialGrantId, financialGrant);
    }
    
    //DELETE /financial_grant/{financialGrantId}
    
    @Operation(
        summary = "Delete a specific grant",
        description = "Delete a specific grant by numerical ID."
    )
    @DeleteMapping("/financial_grant/{financialGrantId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteFinancialGrant(@PathVariable Long financialGrantId) {
        log.info("Deleting financial grant with ID {}...", financialGrantId);
        financialGrantService.deleteFinancialGrant(financialGrantId);
    }
    
}
