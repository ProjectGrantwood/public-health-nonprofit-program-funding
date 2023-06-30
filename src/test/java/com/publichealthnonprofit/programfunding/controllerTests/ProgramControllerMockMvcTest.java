package com.publichealthnonprofit.programfunding.controllerTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.publichealthnonprofit.programfunding.ProgramFundingApplication;
import com.publichealthnonprofit.programfunding.controller.endpointmappings.ProgramController;
import com.publichealthnonprofit.programfunding.controller.model.ProgramData;
import com.publichealthnonprofit.programfunding.controller.service.ProgramService;
import com.publichealthnonprofit.programfunding.entity.Program;

@WebMvcTest(ProgramController.class)
class ProgramControllerMockMvcTest {
    
    private String BASE_URI = "/program_funding";
    private String PROGRAM_URI = BASE_URI + "/program";
    // private String DONATION_URI = BASE_URI + "/donation";
    // private String FINANCIAL_GRANT_URI = BASE_URI + "/financial_grant";
    // private String GRANTING_ORG_URI = BASE_URI + "/granting_org";
    // private String DONOR_URI = BASE_URI + "/donor";
    
    private String testProgramContentString = "{\"programName\":\"testProgramName\", \"programBudget\":1000.00, \"programBudgetPercentageDonationFunded\":0.5, \"programBudgetPercentageGrantFunded\":0.5}";
    private List<String> jsonPaths = list.of(
        "$.programName",
        "$.programBudget",
        "$.programBudgetPercentageDonationFunded",
        "$.programBudgetPercentageGrantFunded"
    );
    
    private ProgramData generateTestProgramData() {
        ProgramData testProgramData = new ProgramData();
        testProgramData.setProgramName("testProgramName");
        testProgramData.setProgramBudget(1000.00f);
        testProgramData.setProgramBudgetPercentageDonationFunded(0.5f);
        testProgramData.setProgramBudgetPercentageGrantFunded(0.5f);
        return testProgramData;
    }
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProgramService programService;
    
    @Test
    void testGetAllPrograms() throws Exception {
        when(programService.getAllPrograms()).thenReturn(List.of(generateTestProgramData()));
        mockMvc.perform(get(PROGRAM_URI))
            .andExpect(status().isOk())
            .andExpect(content().));
    }
    
    @Test
    void testCreateProgram() throws Exception {
        mockMvc.perform(post(PROGRAM_URI)
        .contentType("application/json")
        .content(testProgramContentString))
        .andExpect(status().isCreated());
    }
    
    
    
}
