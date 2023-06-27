package com.publichealthnonprofit.programfunding;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.publichealthnonprofit.programfunding.controller.endpointMappings.ProgramController;

@WebMvcTest(ProgramController.class)

class ProgramFundingApplicationTests {

	@Test
	void contextLoads() {
		assertEquals(1 == 1, true);
	}

}
