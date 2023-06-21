package com.publichealthnonprofit.programfunding.controller.model;

import java.sql.Date;
import java.util.List;
import com.publichealthnonprofit.programfunding.entity.FinancialGrant;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg.GrantingOrgType;
import com.publichealthnonprofit.programfunding.entity.joinedEntities.ProgramFinancialGrant;

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
    private List<FinancialGrantProgramData> programs;
    
    FinancialGrantData(FinancialGrant financialGrant){
        this.financialGrantId = financialGrant.getFinancialGrantId();
        this.financialGrantName = financialGrant.getFinancialGrantName();
        this.financialGrantAmount = financialGrant.getFinancialGrantAmount();
        this.financialGrantStartDate = financialGrant.getFinancialGrantStartDate();
        this.financialGrantEndDate = financialGrant.getFinancialGrantEndDate();
        this.grantingOrg = new FinancialGrantGrantingOrg(financialGrant.getGrantingOrg());
        this.programs = financialGrant.getPrograms().stream().map(FinancialGrantProgramData::new).toList();
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
    
    @Data
    @NoArgsConstructor
    public static class FinancialGrantProgramData {
        private Long programFinancialGrantId;
        private Double alottedToProgramPercentage;
        private Double alottedToProgramAmount;
        
        FinancialGrantProgramData(ProgramFinancialGrant programFinancialGrant){
            this.programFinancialGrantId = programFinancialGrant.getProgramFinancialGrantId();
            this.alottedToProgramPercentage = programFinancialGrant.getAlottedToProgramPercentage();
            this.alottedToProgramAmount = programFinancialGrant.getAlottedToProgramAmount();
        }
    } 
    
}
