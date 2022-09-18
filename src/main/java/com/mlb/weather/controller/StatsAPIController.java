package com.mlb.weather.controller;

import com.mlb.weather.utilities.Utility;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsAPIController {
	private static Logger logger = LoggerFactory.getLogger(StatsAPIController.class);
	@Value("${mlb.stats.host}")
	public String statsApiHost;
	@Value("${weather.api.host}")
	public String weatherApiHost;

	@GetMapping("/api/v1/venues/{id}")
	public ResponseEntity<String> venue(@PathVariable(value = "id") int id) {
		ResponseEntity<String> responseEntity = null;
		try {
			String path = statsApiHost + "/api/v1/venues/" + id + "?hydrate=location";


			responseEntity = Utility.getResponseEntity(Utility.client.execute(new HttpGet(path)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (responseEntity != null && Utility.isTheResponseSuccessful(responseEntity)) {
			logger.info("Call to /venues was successful");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=UTF-8");
			return new ResponseEntity<>(responseEntity.getBody(), headers, responseEntity.getStatusCode());
		}

		return responseEntity;
	}

	@GetMapping("/api/v1/schedule")
	public ResponseEntity<String> schedule(@RequestParam String scheduleTypes, @RequestParam String sportIds,
			@RequestParam String teamIds, @RequestParam String startDate, @RequestParam String endDate) {
		ResponseEntity<String> responseEntity = null;
		try {
			String path = statsApiHost +//
					"/api/v1/schedule?" +//
					"scheduleTypes=" + scheduleTypes + "&" + //
					"sportIds=" + sportIds + "&" +//
					"teamIds=" + teamIds + "&" +//
					"startDate=" + startDate + "&" +//
					"endDate=" + endDate;

			responseEntity = Utility.getResponseEntity(Utility.client.execute(new HttpGet(path)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (responseEntity != null && Utility.isTheResponseSuccessful(responseEntity)) {
			logger.info("Call to /schedule was successful");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=UTF-8");
			return new ResponseEntity<String>(responseEntity.getBody(), headers, responseEntity.getStatusCode());
		}

		return responseEntity;
	}

}
