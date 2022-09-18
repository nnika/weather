package com.mlb.weather.model.mlb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mlb.weather.model.Forecast;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Venue {
	private  String formId;
	private  int id;
	private  String name;
	private  Location location;
	private Forecast forecast;
	private  String forecastDescription;
}
