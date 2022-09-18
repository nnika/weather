package com.mlb.weather.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WeatherAPIService.class})
@ExtendWith(SpringExtension.class)
class WeatherAPIServiceTest {
	@Autowired
	private WeatherAPIService weatherAPIService;

	/**
	 * Method under test: {@link WeatherAPIService#getPoints(int, int)}
	 */
	@Test
	void testGetPoints() {
		assertNull(weatherAPIService.getPoints(1, 1));
	}

	/**
	 * Method under test: {@link WeatherAPIService#getForecast(String, int, int)}
	 */
	@Test
	void testGetForecast() {
		assertNull(weatherAPIService.getForecast("42", 2, 3));
	}
}

