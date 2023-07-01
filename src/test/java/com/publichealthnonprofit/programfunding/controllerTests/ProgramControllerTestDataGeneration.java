package com.publichealthnonprofit.programfunding.controllerTests;

import java.util.ArrayList;
import java.util.List;

import com.publichealthnonprofit.programfunding.controller.model.ProgramData;

public class ProgramControllerTestDataGeneration {
    private static String BASE_URI = "/program_funding";
    protected static String PROGRAM_URI = BASE_URI + "/program";
    
    private ProgramData generateTestProgramData(String programName, Double programBudget, Double programBudgetPercentageDonationFunded, Double programBudgetPercentageGrantFunded) {
        ProgramData testProgramData = new ProgramData();
        testProgramData.setProgramName(programName);
        testProgramData.setProgramBudget(programBudget);
        testProgramData.setProgramBudgetPercentageDonationFunded(programBudget);
        testProgramData.setProgramBudgetPercentageGrantFunded(programBudgetPercentageGrantFunded);
        return testProgramData;
    }
    
    protected List<ProgramData> generateTestProgramDataList() {
        List<ProgramData> testProgramDataList = new ArrayList<>();
        testProgramDataList.add(generateTestProgramData("testProgramName", 1000.00, 0.5, 0.5));
        testProgramDataList.add(generateTestProgramData("testProgramName2", 2000.00, 0.4, 0.6));
        testProgramDataList.add(generateTestProgramData("testProgramName3", 3000.00, 0.3, 0.7));
        testProgramDataList.add(generateTestProgramData("testProgramName4", 4000.00, 0.2, 0.8));
        testProgramDataList.add(generateTestProgramData("testProgramName5", 5000.00, 0.1, 0.9));
        testProgramDataList.add(generateTestProgramData("testProgramName6", 6000.00, 0.0, 1.0));
        return testProgramDataList;
    }
    
    private List<String> fieldNamesForJsonPaths = List.of(
        "programId",
        "programName",
        "programBudget",
        "programBudgetPercentageGrantFunded",
        "programBudgetPercentageDonationFunded",
        "donations",
        "financialGrants"
    );
    
    protected List<String> generateJsonPathsForTestProgram(int index){
        List<String> jsonPathsForTestProgram = new ArrayList<>();
        for (String fieldName : fieldNamesForJsonPaths) {
            jsonPathsForTestProgram.add("$[" + index + "]." + fieldName);
        }
        return jsonPathsForTestProgram;
    }
}
