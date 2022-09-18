package com.mlb.weather.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StatsAPIService.class})
@ExtendWith(SpringExtension.class)
class StatsAPIServiceTest {
	@Autowired
	private StatsAPIService statsAPIService;

	/**
	 * Method under test: {@link StatsAPIService#getVenue(int)}
	 */
	@Test
	void testGetVenue() {
		assertNull(statsAPIService.getVenue(1));
	}

	/**
	 * Method under test: {@link StatsAPIService#getSchedule(String, String, String, String, String)}
	 */
	@Test
	void testGetSchedule() {
		assertNull(statsAPIService.getSchedule("Schedule Types", "Sport Ids", "Team Ids", "2020-03-01", "2020-03-01"));
	}
}

