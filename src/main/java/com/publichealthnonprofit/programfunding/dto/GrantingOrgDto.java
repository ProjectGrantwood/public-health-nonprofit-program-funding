package com.publichealthnonprofit.programfunding.dto;

import java.util.Date;
import java.util.List;

import com.publichealthnonprofit.programfunding.entity.FinancialGrant;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg.GrantingOrgType;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@NoArgsConstructor
public class GrantingOrgDto {
    private Long grantingOrgId;
    private String grantingOrgName;
    private String grantingOrgContactName;
    private String grantingOrgContactEmail;
    private String grantingOrgContactPhone;
    private GrantingOrgType grantingOrgType;
    private List<GrantingOrgFinancialGrant> financialGrants;
    
    public GrantingOrgDto(GrantingOrg grantingOrg){
        this.grantingOrgId = grantingOrg.getGrantingOrgId();
        this.grantingOrgName = grantingOrg.getGrantingOrgName();
        this.grantingOrgContactName = grantingOrg.getGrantingOrgContactName();
        this.grantingOrgContactEmail = grantingOrg.getGrantingOrgContactEmail();
        this.grantingOrgContactPhone = grantingOrg.getGrantingOrgContactPhone();
        this.grantingOrgType = grantingOrg.getGrantingOrgType();
        this.financialGrants = grantingOrg.getFinancialGrants().stream().map(GrantingOrgFinancialGrant::new).toList();
    }
    
    @Hidden
    @Data
    @NoArgsConstructor
    public static class GrantingOrgFinancialGrant {
        private Long financialGrantId;
        private String financialGrantName;
        private Double financialGrantAmount;
        private Date financialGrantStartDate;
        private Date financialGrantEndDate;
        
        public GrantingOrgFinancialGrant(FinancialGrant financialGrant){
            this.financialGrantId = financialGrant.getFinancialGrantId();
            this.financialGrantName = financialGrant.getFinancialGrantName();
            this.financialGrantAmount = financialGrant.getFinancialGrantAmount();
            this.financialGrantStartDate = financialGrant.getFinancialGrantStartDate();
            this.financialGrantEndDate = financialGrant.getFinancialGrantEndDate();
        }
    }
}
