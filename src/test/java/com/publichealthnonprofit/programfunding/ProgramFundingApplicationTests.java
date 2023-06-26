package com.publichealthnonprofit.programfunding;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.publichealthnonprofit.programfunding.controller.endpointMappings.ProgramController;

@WebMvcTest(ProgramController.class)

class ProgramFundingApplicationTests {

	@Test
	void contextLoads() {
	}

}
