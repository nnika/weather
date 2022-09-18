package com.mlb.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule {
	private String teamIds;
	private String startDate;
	private String endDate;
}
