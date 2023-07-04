package com.publichealthnonprofit.programfunding.controllerTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

@WebMvcTest(ProgramController.class)
class ProgramControllerMockMvcTest extends ProgramControllerTestDataGeneration {
    
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
    
}