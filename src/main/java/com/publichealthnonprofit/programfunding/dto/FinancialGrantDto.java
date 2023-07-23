package com.publichealthnonprofit.programfunding.dto;

import java.util.Date;
import java.util.List;

import com.publichealthnonprofit.programfunding.entity.FinancialGrant;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg;
import com.publichealthnonprofit.programfunding.entity.GrantingOrg.GrantingOrgType;
import com.publichealthnonprofit.programfunding.entity.Program;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@NoArgsConstructor
public class FinancialGrantDto {
    
    private Long financialGrantId;
    private String financialGrantName;
    private Double financialGrantAmount;
    private Date financialGrantStartDate;
    private Date financialGrantEndDate;
    private FinancialGrantGrantingOrg grantingOrg;
    private List<FinancialGrantProgram> programs;
    
    public FinancialGrantDto(FinancialGrant financialGrant){
        this.financialGrantId = financialGrant.getFinancialGrantId();
        this.financialGrantName = financialGrant.getFinancialGrantName();
        this.financialGrantAmount = financialGrant.getFinancialGrantAmount();
        this.financialGrantStartDate = financialGrant.getFinancialGrantStartDate();
        this.financialGrantEndDate = financialGrant.getFinancialGrantEndDate();
        this.grantingOrg = new FinancialGrantGrantingOrg(financialGrant.getGrantingOrg());
        this.programs = financialGrant.getPrograms().stream().map(FinancialGrantProgram::new).toList();
    }
    
    @Hidden
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
        private Double programBudget;
        private Double programBudgetPercentageGrantFunded;
        private Double programBudgetPercentageDonationFunded;
        
        public FinancialGrantProgram(Program program){
            this.programId = program.getProgramId();
            this.programName = program.getProgramName();
            this.programBudget = program.getProgramBudget();
            this.programBudgetPercentageGrantFunded = program.getProgramBudgetPercentageGrantFunded();
            this.programBudgetPercentageDonationFunded = program.getProgramBudgetPercentageDonationFunded();
            
        }
    } 
    
}
