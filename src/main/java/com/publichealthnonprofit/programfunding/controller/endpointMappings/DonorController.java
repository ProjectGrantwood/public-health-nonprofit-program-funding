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
import com.publichealthnonprofit.programfunding.controller.model.DonorData;
import com.publichealthnonprofit.programfunding.controller.service.DonorService;
import com.publichealthnonprofit.programfunding.entity.Donor;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/program_funding")
@Slf4j
public class DonorController {
  
    @Autowired
    private DonorService donorService;
    
    @GetMapping("/donor")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DonorData> getAllDonors() {
        log.info("Getting all donors...");
        return donorService.getAllDonors();
    }
    
    @PostMapping("/donor")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DonorData createDonor(@RequestBody Donor donor) {
        log.info("Creating donor...");
        return donorService.createDonor(donor);
    }
    
    @GetMapping("/donor/{donorId}")
    @ResponseStatus(code = HttpStatus.OK)
    public DonorData getDonorById(@PathVariable Long donorId) {
        log.info("Getting donor by ID {}...", donorId);
        return donorService.getDonorById(donorId);
    }
    
    @PutMapping("/donor/{donorId}")
    @ResponseStatus(code = HttpStatus.OK)
    public DonorData updateDonor(@PathVariable Long donorId, @RequestBody Donor donor) {
        log.info("Updating donor with ID {}...", donorId);
        return donorService.updateDonor(donorId, donor);
    }
    
    @DeleteMapping("/donor/{donorId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteDonor(@PathVariable Long donorId) {
        log.info("Deleting donor with ID {}...", donorId);
        donorService.deleteDonor(donorId);
    }
    
}
