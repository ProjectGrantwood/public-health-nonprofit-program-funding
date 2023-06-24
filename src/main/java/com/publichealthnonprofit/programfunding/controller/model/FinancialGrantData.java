package com.publichealthnonprofit.programfunding.controller.model;

import java.sql.Date;
import java.util.List;
import com.publichealthnonprofit.programfunding.entity.FinancialGrant;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg.GrantingOrgType;
import com.publichealthnonprofit.programfunding.entity.Program;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FinancialGrantData {
    
    private Long financialGrantId;
    private String financialGrantName;
    private Float financialGrantAmount;
    private Date financialGrantStartDate;
    private Date financialGrantEndDate;
    private FinancialGrantGrantingOrg grantingOrg;
    private List<FinancialGrantProgram> programs;
    
    public FinancialGrantData(FinancialGrant financialGrant){
        this.financialGrantId = financialGrant.getFinancialGrantId();
        this.financialGrantName = financialGrant.getFinancialGrantName();
        this.financialGrantAmount = financialGrant.getFinancialGrantAmount();
        this.financialGrantStartDate = financialGrant.getFinancialGrantStartDate();
        this.financialGrantEndDate = financialGrant.getFinancialGrantEndDate();
        this.grantingOrg = new FinancialGrantGrantingOrg(financialGrant.getGrantingOrg());
        this.programs = financialGrant.getPrograms().stream().map(FinancialGrantProgram::new).toList();
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
        
        public FinancialGrantGrantingOrg(GrantingOrg grantingOrg){
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
    public static class FinancialGrantProgram {
        private Long programId;
        private String programName;
        private Float programBudget;
        private Float programBudgetPercentageGrantFunded;
        private Float programBudgetPercentageDonationFunded;
        
        public FinancialGrantProgram(Program program){
            this.programId = program.getProgramId();
            this.programName = program.getProgramName();
            this.programBudget = program.getProgramBudget();
            this.programBudgetPercentageGrantFunded = program.getProgramBudgetPercentageGrantFunded();
            this.programBudgetPercentageDonationFunded = program.getProgramBudgetPercentageDonationFunded();
            
        }
    } 
    
}
