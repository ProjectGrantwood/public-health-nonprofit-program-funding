package com.publichealthnonprofit.programfunding.controller.endpointMappings;

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
import com.publichealthnonprofit.programfunding.controller.model.GrantingOrgData;
import com.publichealthnonprofit.programfunding.controller.service.GrantingOrgService;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/program_funding")
@Slf4j
public class GrantingOrgController {
  
    @Autowired
    private GrantingOrgService grantingOrgService;
    
    @GetMapping("/grantingOrg")
    @ResponseStatus(code = HttpStatus.OK)
    public List<GrantingOrgData> getAllGrantingOrgs() {
        log.info("Getting all granting organizations...");
        return grantingOrgService.getAllGrantingOrgs();
    }
    
    @PostMapping("/grantingOrg")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GrantingOrgData createGrantingOrg(@RequestBody GrantingOrg grantingOrg) {
        log.info("Creating granting organization...");
        return grantingOrgService.createGrantingOrg(grantingOrg);
    }
    
    @GetMapping("/grantingOrg/{grantingOrgId}")
    @ResponseStatus(code = HttpStatus.OK)
    public GrantingOrgData getGrantingOrgById(@PathVariable Long grantingOrgId) {
        log.info("Getting granting organization by ID {}...", grantingOrgId);
        return grantingOrgService.getGrantingOrgById(grantingOrgId);
    }
    
    @PutMapping("/grantingOrg/{grantingOrgId}")
    @ResponseStatus(code = HttpStatus.OK)
    public GrantingOrgData updateGrantingOrg(@PathVariable Long grantingOrgId, @RequestBody GrantingOrg grantingOrg) {
        log.info("Updating granting organization with ID {}...", grantingOrgId);
        return grantingOrgService.updateGrantingOrg(grantingOrgId, grantingOrg);
    }
    
    @DeleteMapping("/grantingOrg/{grantingOrgId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteGrantingOrg(@PathVariable Long grantingOrgId) {
        log.info("Deleting granting organization with ID {}...", grantingOrgId);
        grantingOrgService.deleteGrantingOrg(grantingOrgId);
    }
    
}

