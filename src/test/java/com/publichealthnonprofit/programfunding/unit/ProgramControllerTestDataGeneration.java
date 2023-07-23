package com.publichealthnonprofit.programfunding.unit;

import java.util.ArrayList;
import java.util.List;

import com.publichealthnonprofit.programfunding.dto.ProgramDto;
import com.publichealthnonprofit.programfunding.entity.Program;

public class ProgramControllerTestDataGeneration {
    
    private static String BASE_URI = "/program_funding";
    protected static String PROGRAM_URI = BASE_URI + "/program";
    protected List<String> multipleQueryParams = List.of(
        "?grantingOrgId=1&donorId=1",
        "?financialGrantId=1&donationId=1",
        "?grantingOrgId=1&financialGrantId=1",
        "?donorId=1&donationId=1",
        "?grantingOrgId=1&donationId=1",
        "?financialGrantId=1&donorId=1",
        "?grantingOrgId=1&donorId=1&financialGrantId=1"
    );
    
    private ProgramDto generateTestProgramData(String programName, Double programBudget, Double programBudgetPercentageDonationFunded, Double programBudgetPercentageGrantFunded) {
        ProgramDto testProgramData = new ProgramDto();
        testProgramData.setProgramName(programName);
        testProgramData.setProgramBudget(programBudget);
        testProgramData.setProgramBudgetPercentageDonationFunded(programBudgetPercentageDonationFunded);
        testProgramData.setProgramBudgetPercentageGrantFunded(programBudgetPercentageGrantFunded);
        return testProgramData;
    }
    
    private Program generateTestProgram(String programName, Double programBudget, Double programBudgetPercentageDonationFunded, Double programBudgetPercentageGrantFunded) {
        Program testProgram = new Program();
        testProgram.setProgramName(programName);
        testProgram.setProgramBudget(programBudget);
        testProgram.setProgramBudgetPercentageDonationFunded(programBudgetPercentageDonationFunded);
        testProgram.setProgramBudgetPercentageGrantFunded(programBudgetPercentageGrantFunded);
        return testProgram;
    }
    
    protected List<Program> generateTestProgramList() {
        List<Program> testProgramList = new ArrayList<>();
        testProgramList.add(generateTestProgram("testProgramName", 1000.00, 0.5, 0.5));
        testProgramList.add(generateTestProgram("testProgramName2", 2000.00, 0.4, 0.6));
        testProgramList.add(generateTestProgram("testProgramName3", 3000.00, 0.3, 0.7));
        testProgramList.add(generateTestProgram("testProgramName4", 4000.00, 0.2, 0.8));
        testProgramList.add(generateTestProgram("testProgramName5", 5000.00, 0.1, 0.9));
        testProgramList.add(generateTestProgram("testProgramName6", 6000.00, 0.0, 1.0));
        return testProgramList;
    }
    
    protected List<ProgramDto> generateTestProgramDataList() {
        List<ProgramDto> testProgramDataList = new ArrayList<>();
        testProgramDataList.add(generateTestProgramData("testProgramName", 1000.00, 0.5, 0.5));
        testProgramDataList.add(generateTestProgramData("testProgramName2", 2000.00, 0.4, 0.6));
        testProgramDataList.add(generateTestProgramData("testProgramName3", 3000.00, 0.3, 0.7));
        testProgramDataList.add(generateTestProgramData("testProgramName4", 4000.00, 0.2, 0.8));
        testProgramDataList.add(generateTestProgramData("testProgramName5", 5000.00, 0.1, 0.9));
        testProgramDataList.add(generateTestProgramData("testProgramName6", 6000.00, 0.0, 1.0));
        return testProgramDataList;
    }
}
