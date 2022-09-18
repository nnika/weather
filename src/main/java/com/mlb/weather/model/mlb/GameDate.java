package com.mlb.weather.model.mlb;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDate {
	private  String date;
	List<Game> games;
}
