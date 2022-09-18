package com.mlb.weather.model.mlb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
	private  Teams teams;
	private  Venue venue;

}
