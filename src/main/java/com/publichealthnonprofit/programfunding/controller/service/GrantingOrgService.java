package com.publichealthnonprofit.programfunding.controller.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publichealthnonprofit.programfunding.controller.model.GrantingOrgData;
import com.publichealthnonprofit.programfunding.dao.GrantingOrgDao;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg.GrantingOrgType;

@Service
public class GrantingOrgService {
    
    @SuppressWarnings("unused")
    @Autowired
    private GrantingOrgDao grantingOrgDao;

    public GrantingOrgData getGrantingOrgById(Long grantingOrgId) {
        return new GrantingOrgData(findGrantingOrgById(grantingOrgId));
    }

    @Transactional(readOnly = true)
    public List<GrantingOrgData> getAllGrantingOrgs() {
        return grantingOrgDao.findAll().stream().map(GrantingOrgData::new).toList();
    }

    
    public GrantingOrgData createGrantingOrg(GrantingOrg grantingOrg) {
        return saveGrantingOrgFromGrantingOrgData(new GrantingOrgData(grantingOrg));
    }
    
    @Transactional(readOnly = false)
    public GrantingOrgData updateGrantingOrg(Long grantingOrgId, GrantingOrg grantingOrg) {
        GrantingOrg grantingOrgToUpdate = findGrantingOrgById(grantingOrgId);
        updateFieldsInGrantingOrg(grantingOrgToUpdate, new GrantingOrgData(grantingOrg));
        return new GrantingOrgData(grantingOrgDao.save(grantingOrgToUpdate));
    }
    
    @Transactional(readOnly = false)
    public GrantingOrgData saveGrantingOrgFromGrantingOrgData(GrantingOrgData grantingOrgData) {
        GrantingOrg grantingOrg = findOrCreateGrantingOrg(grantingOrgData.getGrantingOrgId());
        setFieldsInGrantingOrg(grantingOrg, grantingOrgData);
        return new GrantingOrgData(grantingOrgDao.save(grantingOrg));
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
        return grantingOrgDao.findById(grantingOrgId).orElseThrow(() -> new NoSuchElementException("GrantingOrg not found with id: " + grantingOrgId));
    }
    
    private void setFieldsInGrantingOrg(GrantingOrg grantingOrg, GrantingOrgData grantingOrgData) {
        grantingOrg.setGrantingOrgName(grantingOrgData.getGrantingOrgName());
        grantingOrg.setGrantingOrgContactName(grantingOrgData.getGrantingOrgContactName());
        grantingOrg.setGrantingOrgContactEmail(grantingOrgData.getGrantingOrgContactEmail());
        grantingOrg.setGrantingOrgContactPhone(grantingOrgData.getGrantingOrgContactPhone());
        grantingOrg.setGrantingOrgType(grantingOrgData.getGrantingOrgType());
    }
    
    private void updateFieldsInGrantingOrg(GrantingOrg grantingOrg, GrantingOrgData grantingOrgData) {
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
