package com.publichealthnonprofit.programfunding.controllerTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.publichealthnonprofit.programfunding.controller.endpointmappings.ProgramController;
import com.publichealthnonprofit.programfunding.controller.model.ProgramData;
import com.publichealthnonprofit.programfunding.controller.service.ProgramService;
import com.publichealthnonprofit.programfunding.entity.Program;

@WebMvcTest(ProgramController.class)
class ProgramControllerMockMvcTest extends ProgramControllerTestDataGeneration {
    
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
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProgramService programService;
    
    @Test
    void getAllPrograms_shouldReturnProgramData() throws Exception {
        List<ProgramData> programDataList = generateTestProgramDataList();
        when(programService.getAllProgramsAsProgramData()).thenReturn(programDataList);
        for (int i = 0; i < programDataList.size(); i++){
            mockMvc.perform(get(PROGRAM_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[" + i + "].programName").value(programDataList.get(i).getProgramName()));
        }
    }
    
    @Test 
    void getAllPrograms_shouldOnlyAcceptOneQueryParameter() throws Exception {
        for (String getRequestWithMultipleQueryParams : multipleQueryParams) {
        when(programService.getAllPrograms()).thenReturn(generateTestProgramList());
        mockMvc.perform(get(PROGRAM_URI + getRequestWithMultipleQueryParams))
            .andExpect(status().isBadRequest());
        }
    }
    
    @Test
        void deleteAllPrograms_shouldReturnMethodNotAllowed() throws Exception {
        mockMvc.perform(delete(PROGRAM_URI))
            .andExpect(status().isMethodNotAllowed());
        }

    private ProgramData generateTestProgramData(String programName, Double programBudget, Double programBudgetPercentageDonationFunded, Double programBudgetPercentageGrantFunded) {
        ProgramData testProgramData = new ProgramData();
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
    
    private List<Program> generateTestProgramList() {
        List<Program> testProgramList = new ArrayList<>();
        testProgramList.add(generateTestProgram("testProgramName", 1000.00, 0.5, 0.5));
        testProgramList.add(generateTestProgram("testProgramName2", 2000.00, 0.4, 0.6));
        testProgramList.add(generateTestProgram("testProgramName3", 3000.00, 0.3, 0.7));
        testProgramList.add(generateTestProgram("testProgramName4", 4000.00, 0.2, 0.8));
        testProgramList.add(generateTestProgram("testProgramName5", 5000.00, 0.1, 0.9));
        testProgramList.add(generateTestProgram("testProgramName6", 6000.00, 0.0, 1.0));
        return testProgramList;
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
    
}