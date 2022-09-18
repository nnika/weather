package com.mlb.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlb.weather.model.mlb.GameDateList;
import com.mlb.weather.model.mlb.Venue;
import com.mlb.weather.model.mlb.VenueResponse;
import com.mlb.weather.utilities.Utility;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatsAPIService {
	private static final Logger logger = LoggerFactory.getLogger(StatsAPIService.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();
	@Value("${microservice.host}")
	public String microserviceHost;
	@Value("${microservice.port}")
	public String microservicePort;

	public Venue getVenue(int id) {
		try {
			ResponseEntity<String> responseEntity = null;
			try {
				CloseableHttpResponse response = Utility.client.execute(new HttpGet(microserviceHost + ":" + microservicePort + "/stats/api/v1/venues/" + id + "?hydrate=location"));
				responseEntity = Utility.getResponseEntity(response);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if (responseEntity != null) {
				return Utility.isTheResponseSuccessful(responseEntity) ? objectMapper.readValue(responseEntity.getBody(), VenueResponse.class).getVenues().get(0) : null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	public GameDateList getSchedule(String teamIds, String startDate, String endDate) {
		return getSchedule("games", "1", teamIds, startDate, endDate);
	}

	public GameDateList getSchedule(String scheduleTypes, String sportIds, String teamIds, String startDate, String endDate) {
		try {
			ResponseEntity<String> responseEntity = null;
			try {
				String path = microserviceHost + ":" + microservicePort + "/stats/api/v1/schedule?" + "scheduleTypes=" +
						scheduleTypes + "&" +  "sportIds=" + sportIds + "&" + "teamIds=" + teamIds + "&" + "startDate=" + startDate + "&" + "endDate=" + endDate;

				responseEntity = Utility.getResponseEntity(Utility.client.execute(new HttpGet(path)));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			if (responseEntity != null) {
				return Utility.isTheResponseSuccessful(responseEntity) ? objectMapper.readValue(responseEntity.getBody(), GameDateList.class) : null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}
}
