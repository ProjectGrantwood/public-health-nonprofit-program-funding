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

import com.publichealthnonprofit.programfunding.controller.model.FinancialGrantData;
import com.publichealthnonprofit.programfunding.controller.service.FinancialGrantService;
import com.publichealthnonprofit.programfunding.entity.FinancialGrant;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/program_funding")
@Slf4j
public class FinancialGrantController {
    @Autowired
    private FinancialGrantService financialGrantService;
    
    @GetMapping("/financial_grant")
    @ResponseStatus(code = HttpStatus.OK)
    public List<FinancialGrantData> getAllFinancialGrants(@RequestParam(required = false) Optional<Long> grantingOrgId, @RequestParam(required = false) Optional<Long> programId) {
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
    
    @PostMapping("/financial_grant")
    @ResponseStatus(code = HttpStatus.CREATED)
    public FinancialGrantData createFinancialGrant(@RequestParam(required = true) Optional<Long> grantingOrgId, @RequestParam(required = false) Optional<Long> programId, @RequestBody FinancialGrant financialGrant) {
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
    
    @GetMapping("/financial_grant/{financialGrantId}")
    @ResponseStatus(code = HttpStatus.OK)
    public FinancialGrantData getFinancialGrantById(@PathVariable Long financialGrantId) {
        log.info("Getting financial grant with ID {}...", financialGrantId);
        return financialGrantService.getFinancialGrantById(financialGrantId);
    }
    
    @PutMapping("/financial_grant/{financialGrantId}")
    @ResponseStatus(code = HttpStatus.OK)
    public FinancialGrantData updateFinancialGrant(@PathVariable Long financialGrantId, @RequestBody FinancialGrant financialGrant) {
        log.info("Updating financial grant with ID {}...", financialGrantId);
        return financialGrantService.updateFinancialGrant(financialGrantId, financialGrant);
    }
    
    @DeleteMapping("/financial_grant/{financialGrantId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteFinancialGrant(@PathVariable Long financialGrantId) {
        log.info("Deleting financial grant with ID {}...", financialGrantId);
        financialGrantService.deleteFinancialGrant(financialGrantId);
    }
    
}
