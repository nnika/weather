package com.mlb.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Period {
	private int number;
	private double temperature;
	private String temperatureUnit;
	private String detailedForecast;
	private Date startTime;
	private Date endTime;
}
