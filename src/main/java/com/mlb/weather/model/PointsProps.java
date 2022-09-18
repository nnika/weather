package com.mlb.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsProps {
	private String gridId;
	private int gridX;
	private int gridY;
	private String forecast;
	private String forecastOffice;
	private String gridXDistance;
	private String gridYDistance;
	private String applicableLocation;
	private String zone;
	private String county;
	private String cwa;
	private String fireWeatherZone;
	private String timeZone;
	private String radarStation;
	private String forecastHourly;
	private String forecastGridData;
	private String forecastZone;
	private String countyZone;
	private String fireWeatherZoneUrl;
	private String radarStationUrl;
}
