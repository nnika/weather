package com.mlb.weather.model.mlb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {
	private  int latitude;
	private  int longitude;
}
