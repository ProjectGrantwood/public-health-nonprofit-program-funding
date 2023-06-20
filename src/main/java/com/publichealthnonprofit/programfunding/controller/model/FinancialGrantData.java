package com.publichealthnonprofit.programfunding.controller.model;

import java.sql.Date;

import com.publichealthnonprofit.programfunding.entity.GrantingOrg;

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
    private CollectionlessGrantingOrg grantingOrg;
    
    @Data
    @NoArgsConstructor
    public static class CollectionlessGrantingOrg {
        private Long grantingOrgId;
        private String grantingOrgName;
        private String grantingOrgContactName;
        private String grantingOrgContactEmail;
        private String grantingOrgContactPhone;
        private String grantingOrgType;
        
        CollectionlessGrantingOrg(GrantingOrg grantingOrg){
            this.grantingOrgId = grantingOrg.getGrantingOrgId();
            this.grantingOrgName = grantingOrg.getGrantingOrgName();
            this.grantingOrgContactName = grantingOrg.getGrantingOrgContactName();
            this.grantingOrgContactEmail = grantingOrg.getGrantingOrgContactEmail();
            this.grantingOrgContactPhone = grantingOrg.getGrantingOrgContactPhone();
            this.grantingOrgType = grantingOrg.getGrantingOrgType();
        }
        
    }
    
}
