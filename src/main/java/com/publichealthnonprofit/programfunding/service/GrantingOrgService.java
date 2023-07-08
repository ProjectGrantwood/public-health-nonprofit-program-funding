package com.publichealthnonprofit.programfunding.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publichealthnonprofit.programfunding.dto.GrantingOrgDto;
import com.publichealthnonprofit.programfunding.model.GrantingOrg;
import com.publichealthnonprofit.programfunding.model.GrantingOrgType;
import com.publichealthnonprofit.programfunding.repository.GrantingOrgRepository;

@Service
public class GrantingOrgService {
    
    @Autowired
    private GrantingOrgRepository grantingOrgRepository;

    @Transactional(readOnly = true)
    public GrantingOrgDto getGrantingOrgById(Long grantingOrgId) {
        return new GrantingOrgDto(findGrantingOrgById(grantingOrgId));
    }

    @Transactional(readOnly = true)
    public List<GrantingOrgDto> getAllGrantingOrgs() {
        return grantingOrgRepository.findAll().stream().map(GrantingOrgDto::new).toList();
    }

    @Transactional(readOnly = false)
    public GrantingOrgDto createGrantingOrg(GrantingOrg grantingOrg) {
        return saveGrantingOrgFromGrantingOrgData(new GrantingOrgDto(grantingOrg));
    }
    
    @Transactional(readOnly = false)
    public GrantingOrgDto updateGrantingOrg(Long grantingOrgId, GrantingOrg grantingOrg) {
        GrantingOrg grantingOrgToUpdate = findGrantingOrgById(grantingOrgId);
        updateFieldsInGrantingOrg(grantingOrgToUpdate, new GrantingOrgDto(grantingOrg));
        return new GrantingOrgDto(grantingOrgRepository.save(grantingOrgToUpdate));
    }
    
    @Transactional(readOnly = false)
    public GrantingOrgDto saveGrantingOrgFromGrantingOrgData(GrantingOrgDto grantingOrgData) {
        GrantingOrg grantingOrg = findOrCreateGrantingOrg(grantingOrgData.getGrantingOrgId());
        setFieldsInGrantingOrg(grantingOrg, grantingOrgData);
        return new GrantingOrgDto(grantingOrgRepository.save(grantingOrg));
    }
    
    @Transactional(readOnly = false)
    public void deleteGrantingOrg(Long grantingOrgId) {
        GrantingOrg grantingOrg = findGrantingOrgById(grantingOrgId);
        if (grantingOrg.getFinancialGrants().isEmpty()) {
            grantingOrgRepository.delete(grantingOrg);
        } else {
            throw new IllegalArgumentException("GrantingOrg with id " + grantingOrgId + " cannot be deleted because it has at least one grant associated with it.");
        }
    }
    
    private GrantingOrg findOrCreateGrantingOrg(Long grantingOrgId) {
        GrantingOrg grantingOrg;
        if (Objects.isNull(grantingOrgId)){
            grantingOrg = new GrantingOrg();
        } else {
            grantingOrg = findGrantingOrgById(grantingOrgId);
        }
        return grantingOrg;
    }
    
    @Transactional(readOnly = true)
    public GrantingOrg findGrantingOrgById(Long grantingOrgId) {
        return grantingOrgRepository.findById(grantingOrgId).orElseThrow(() -> new NoSuchElementException("GrantingOrg not found with id: " + grantingOrgId));
    }
    
    private void setFieldsInGrantingOrg(GrantingOrg grantingOrg, GrantingOrgDto grantingOrgData) {
        grantingOrg.setGrantingOrgName(grantingOrgData.getGrantingOrgName());
        grantingOrg.setGrantingOrgContactName(grantingOrgData.getGrantingOrgContactName());
        grantingOrg.setGrantingOrgContactEmail(grantingOrgData.getGrantingOrgContactEmail());
        grantingOrg.setGrantingOrgContactPhone(grantingOrgData.getGrantingOrgContactPhone());
        grantingOrg.setGrantingOrgType(grantingOrgData.getGrantingOrgType());
    }
    
    private void updateFieldsInGrantingOrg(GrantingOrg grantingOrg, GrantingOrgDto grantingOrgData) {
        String updatedGrantingOrgName = grantingOrgData.getGrantingOrgName();
        String updatedGrantingOrgContactName = grantingOrgData.getGrantingOrgContactName();
        String updatedGrantingOrgContactEmail = grantingOrgData.getGrantingOrgContactEmail();
        String updatedGrantingOrgContactPhone = grantingOrgData.getGrantingOrgContactPhone();
        GrantingOrgType updatedGrantingOrgType = grantingOrgData.getGrantingOrgType();
        if (Objects.nonNull(updatedGrantingOrgName)){
            grantingOrg.setGrantingOrgName(updatedGrantingOrgName);
        }
        if (Objects.nonNull(updatedGrantingOrgContactName)){
            grantingOrg.setGrantingOrgContactName(updatedGrantingOrgContactName);
        }
        if (Objects.nonNull(updatedGrantingOrgContactEmail)){
            grantingOrg.setGrantingOrgContactEmail(updatedGrantingOrgContactEmail);
        }
        if (Objects.nonNull(updatedGrantingOrgContactPhone)){
            grantingOrg.setGrantingOrgContactPhone(updatedGrantingOrgContactPhone);
        }
        if (Objects.nonNull(updatedGrantingOrgType)){
            grantingOrg.setGrantingOrgType(updatedGrantingOrgType);
        }
    }
    
}
