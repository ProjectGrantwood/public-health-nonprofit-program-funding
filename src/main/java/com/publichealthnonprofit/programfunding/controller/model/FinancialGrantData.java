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
    
    private Long financialGrantId;
    private String financialGrantName;
    private Double financialGrantAmount;
    private Date financialGrantStartDate;
    private Date financialGrantEndDate;
    private FinancialGrantGrantingOrg grantingOrg;
    
    FinancialGrantData(FinancialGrant financialGrant){
        this.financialGrantId = financialGrant.getFinancialGrantId();
        this.financialGrantName = financialGrant.getFinancialGrantName();
        this.financialGrantAmount = financialGrant.getFinancialGrantAmount();
        this.financialGrantStartDate = financialGrant.getFinancialGrantStartDate();
        this.financialGrantEndDate = financialGrant.getFinancialGrantEndDate();
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
