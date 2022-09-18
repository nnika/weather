package com.mlb.weather.controller;

import com.mlb.weather.utilities.Utility;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherApiController {
	private static final Logger logger = LoggerFactory.getLogger(WeatherApiController.class);
	@Value("${mlb.stats.host}")
	public String mlbStatsHost;
	@Value("${weather.api.host}")
	public String weatherApiHost;

	@GetMapping("/api/v1/points")
	public ResponseEntity<String> points(@RequestParam(value = "latitude") int latitude, @RequestParam(value = "longitude") int longitude) {
		ResponseEntity<String> responseEntity = null;
		try {

			final HttpGet request = new HttpGet(weatherApiHost + "/points/" + latitude + "," + longitude);
			request.addHeader("User-Agent", "mlb-weather/1.0.0");
			request.addHeader("Accept", "*/*"); 
			CloseableHttpResponse response = Utility.client.execute(request);

			responseEntity = Utility.getResponseEntity(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (responseEntity != null && Utility.isTheResponseSuccessful(responseEntity)) {
			logger.info("Call to /points was successful");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=UTF-8");
			return new ResponseEntity<>(responseEntity.getBody(), headers, responseEntity.getStatusCode());
		}

		return responseEntity;
	}

	@GetMapping("/api/v1/forecast")
	public ResponseEntity<String> forecast(@RequestParam String gridId, @RequestParam(value = "x") int gridX, @RequestParam(value = "y") int gridY) {
		ResponseEntity<String> responseEntity = null;
		try {  
			final HttpGet request = new HttpGet(weatherApiHost + "/gridpoints/" + gridId + "/" + gridX + "," + gridY + "/forecast");
			request.addHeader("User-Agent", "mlb-weather/1.0.0");
			request.addHeader("Accept", "*/*");
			CloseableHttpResponse response = Utility.client.execute(request);

			responseEntity = Utility.getResponseEntity(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (responseEntity != null && Utility.isTheResponseSuccessful(responseEntity)) {
			logger.info("Call to /forecast was successful");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=UTF-8");
			return new ResponseEntity<String>(responseEntity.getBody(), headers, responseEntity.getStatusCode());
		}

		return responseEntity;
	}

}
