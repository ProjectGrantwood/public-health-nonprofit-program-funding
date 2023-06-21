package com.publichealthnonprofit.programfunding.controller.model;

import java.sql.Date;

import com.publichealthnonprofit.programfunding.entity.FinancialGrant;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg.GrantingOrgType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FinancialGrantData {
    
    private Long FinancialGrantId;
    private String FinancialGrantName;
    private Double FinancialGrantAmount;
    private Date FinancialGrantStartDate;
    private Date FinancialGrantEndDate;
    private FinancialGrantGrantingOrg grantingOrg;
    
    FinancialGrantData(FinancialGrant financialGrant){
        this.FinancialGrantId = financialGrant.getFinancialGrantId();
        this.FinancialGrantName = financialGrant.getFinancialGrantName();
        this.FinancialGrantAmount = financialGrant.getFinancialGrantAmount();
        this.FinancialGrantStartDate = financialGrant.getFinancialGrantStartDate();
        this.FinancialGrantEndDate = financialGrant.getFinancialGrantEndDate();
        this.grantingOrg = new FinancialGrantGrantingOrg(financialGrant.getGrantingOrg());
    }
    
    @Data
    @NoArgsConstructor
    public static class FinancialGrantGrantingOrg {
        private Long grantingOrgId;
        private String grantingOrgName;
        private String grantingOrgContactName;
        private String grantingOrgContactEmail;
        private String grantingOrgContactPhone;
        private GrantingOrgType grantingOrgType;
        
        FinancialGrantGrantingOrg(GrantingOrg grantingOrg){
            this.grantingOrgId = grantingOrg.getGrantingOrgId();
            this.grantingOrgName = grantingOrg.getGrantingOrgName();
            this.grantingOrgContactName = grantingOrg.getGrantingOrgContactName();
            this.grantingOrgContactEmail = grantingOrg.getGrantingOrgContactEmail();
            this.grantingOrgContactPhone = grantingOrg.getGrantingOrgContactPhone();
            this.grantingOrgType = grantingOrg.getGrantingOrgType();
        }
        
    }
    
}
