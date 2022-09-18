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

@ContextConfiguration(classes = {WeatherApiController.class})
@ExtendWith(SpringExtension.class)
class WeatherApiControllerTest {
	@Autowired
	private WeatherApiController weatherApiController;

	/**
	 * Method under test: {@link WeatherApiController#forecast(String, int, int)}
	 */
	@Test
	void testForecast() throws Exception {
		MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get("/weather/api/v1/forecast")
				.param("gridId", "foo");
		MockHttpServletRequestBuilder paramResult1 = paramResult.param("gridX", String.valueOf(1));
		MockHttpServletRequestBuilder requestBuilder = paramResult1.param("gridY", String.valueOf(1));
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(weatherApiController)
				.build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
	}

	/**
	 * Method under test: {@link WeatherApiController#points(int, int)}
	 */
	@Test
	void testPoints() throws Exception {
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/weather/api/v1/points");
		MockHttpServletRequestBuilder paramResult = getResult.param("latitude", String.valueOf(1));
		MockHttpServletRequestBuilder requestBuilder = paramResult.param("longitude", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(weatherApiController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Method under test: {@link WeatherApiController#points(int, int)}
	 */
	@Test
	void testPoints2() throws Exception {
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/weather/api/v1/points", "Uri Variables");
		MockHttpServletRequestBuilder paramResult = getResult.param("latitude", String.valueOf(1));
		MockHttpServletRequestBuilder requestBuilder = paramResult.param("longitude", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(weatherApiController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}

