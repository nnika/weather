package com.mlb.weather.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {StatsAPIController.class})
@ExtendWith(SpringExtension.class)
class StatsAPIControllerTest {
	@Autowired
	private StatsAPIController statsAPIController;

	/**
	 * Method under test: {@link StatsAPIController#schedule(String, String, String, String, String)}
	 */
	@Test
	void testSchedule() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/api/v1/schedule")
				.param("endDate", "foo")
				.param("scheduleTypes", "foo")
				.param("sportIds", "foo")
				.param("startDate", "foo")
				.param("teamIds", "foo");
		MockMvcBuilders.standaloneSetup(statsAPIController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Method under test: {@link StatsAPIController#schedule(String, String, String, String, String)}
	 */
	@Test
	void testSchedule2() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/stats/api/v1/schedule", "Uri Variables")
				.param("endDate", "foo")
				.param("scheduleTypes", "foo")
				.param("sportIds", "foo")
				.param("startDate", "foo")
				.param("teamIds", "foo");
		MockMvcBuilders.standaloneSetup(statsAPIController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Method under test: {@link StatsAPIController#venue(int)}
	 */
	@Test
	void testVenue() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/api/v1/venues/{id}",
				"Uri Variables", "Uri Variables");
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(statsAPIController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
	}
}

