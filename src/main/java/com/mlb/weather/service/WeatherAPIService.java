package com.mlb.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlb.weather.model.Forecast;
import com.mlb.weather.model.Points;
import com.mlb.weather.utilities.Utility;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WeatherAPIService {
	private static final Logger logger = LoggerFactory.getLogger(WeatherAPIService.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();
	@Value("${microservice.host}")
	public String microserviceHost;
	@Value("${microservice.port}")
	public String microservicePort;

	public Points getPoints(int latitude, int longitude) {
		try {
			ResponseEntity<String> responseEntity;
			String path = microserviceHost + ":" + microservicePort + "/weather/api/v1/points?" + "latitude=" + latitude + "&" + "longitude=" + longitude;

			responseEntity = Utility.getResponseEntity(Utility.client.execute(new HttpGet(path)));
			return (Utility.isTheResponseSuccessful(Utility.getResponseEntity(Utility.client.execute(new HttpGet(path)))) ? objectMapper.readValue(responseEntity.getBody(), Points.class) : null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public Forecast getForecast(String gridId, int x, int y) {

		try {
			ResponseEntity<String> responseEntity;
			String path = microserviceHost + ":" + microservicePort + "/weather/api/v1/forecast?" + "gridId=" + gridId + "&" + "x=" + x + "&" +"y=" + y;
			responseEntity = Utility.getResponseEntity(Utility.client.execute( new HttpGet(path)));

			return (Utility.isTheResponseSuccessful(Utility.getResponseEntity(Utility.client.execute(new HttpGet(path)))) ? objectMapper.readValue(responseEntity.getBody(), Forecast.class) : null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return null;
	}
}
