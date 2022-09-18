package com.mlb.weather.model.mlb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
	private  String city;
	private  String state;
	private  Coordinates defaultCoordinates;
}
