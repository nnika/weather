package com.mlb.weather.model.mlb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDateList {
	private  int totalGames;
	@JsonProperty(value = "dates")
	private  List<GameDate> gameDates;
}
