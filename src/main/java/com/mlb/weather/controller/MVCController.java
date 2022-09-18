package com.mlb.weather.controller;

import com.mlb.weather.model.*;
import com.mlb.weather.model.mlb.*;
import com.mlb.weather.service.StatsAPIService;
import com.mlb.weather.service.WeatherAPIService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class MVCController {
	private static final Logger logger = LoggerFactory.getLogger(MVCController.class);
	public static final String EST = "US/Eastern";
	final
	StatsAPIService statsService;
	final
	WeatherAPIService weatherService;
	@Value("#{new Boolean('${current.forecast.enabled}')}")
	Boolean currentForecastEnabled;

	public MVCController(StatsAPIService statsService, WeatherAPIService weatherService) {
		this.statsService = statsService;
		this.weatherService = weatherService;
	}

	@GetMapping("/")
	public String initialize(Venue venue, Schedule schedule) {
		venue.setFormId("680");
		schedule.setTeamIds("121");
		schedule.setStartDate("2022-04-07");
		return "home";
	}

	@PostMapping(value = "/findvenue")
	public String findVenue(Venue form, Model model) {
		if (StringUtils.isNumeric(form.getFormId())) {
			int id = Integer.parseInt(form.getFormId());
			Venue venue = statsService.getVenue(id);
			if (venue != null) {
				setVenueAttributes(model, venue);
			}   else {
				model.addAttribute("error", "Venue not found");
			}
		} else {
			model.addAttribute("error", "Invalid venue id");
		}

		return "venue";
	}

	void setVenueAttributes(Model model, Venue venue) {
		model.addAttribute("venue", venue);
		Coordinates c = venue.getLocation().getDefaultCoordinates();
		Points points = weatherService.getPoints(c.getLatitude(), c.getLongitude());
		addForecastAttribute(model, points);
	}

	void addForecastAttribute(Model model, Points points) {
		if (points != null) {
			PointsProps p = points.getProperties();
			Forecast f = weatherService.getForecast(p.getGridId(), p.getGridX(), p.getGridY());
			if (f != null) {
				model.addAttribute("forecast", f.getProperties().getPeriods().get(0));
			}
		}
	}

	@PostMapping(value = "/findschedule")
	public String findSchedule(Schedule schedule, Model model) {
		GameDateList gameDateList = null;
		try {
			schedule.setEndDate(schedule.getStartDate());
			gameDateList = statsService.getSchedule(schedule.getTeamIds(), schedule.getStartDate(), schedule.getEndDate());
		} catch (Exception e) {
			logger.warn(e.getMessage());
			model.addAttribute("invalidDate", schedule.getStartDate());
		}

		if (gameDateList != null) {
			traverseGameDateList(schedule, gameDateList);
			model.addAttribute("schedule", gameDateList);
		} else {
			model.addAttribute("schedule", null);
		}
		return "schedule";
	}

	 void traverseGameDateList(Schedule form, GameDateList schedule) {
		for (GameDate gameDate : schedule.getGameDates()) {
			for (Game game : gameDate.getGames()) {
				Venue venue = statsService.getVenue(game.getVenue().getId());
				Coordinates c = venue.getLocation().getDefaultCoordinates();
				Points points = weatherService.getPoints(c.getLatitude(), c.getLongitude());
				PointsProps p = points.getProperties();
				setAllElements(form, venue, p);
				game.setVenue(venue);

			}
		}
	}

	void setAllElements(Schedule form, Venue venue, PointsProps p) {
		if (p != null) {
			Forecast f = weatherService.getForecast(p.getGridId(), p.getGridX(), p.getGridY());
			if (f != null) {
				venue.setForecast(f);
				ForecastProps props = f.getProperties();
				if (currentForecastEnabled) {
					venue.setForecastDescription(props.getPeriods().get(0).getDetailedForecast());
				} else {
					LocalDateTime isoLocalDateTime = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(form.getStartDate())).atStartOfDay();
					venue.setForecastDescription("Not available. Date given beyond forecast periods.");
					traversePoints(venue, props, isoLocalDateTime);
				}
			} else {
				venue.setForecastDescription("Not available. Date given beyond forecast periods.");
			}
		}
	}

	static void traversePoints(Venue venue, ForecastProps props, LocalDateTime isoLocalDateTime) {
		for (Period period : props.getPeriods()) {
			Date periodStartTime = period.getStartTime();
			LocalDateTime pStartLocalDateTime = LocalDateTime.ofInstant(periodStartTime.toInstant(), ZoneId.of(EST));
			Date periodEndDateTime = period.getStartTime();
			LocalDateTime pEndLocalDateTime = LocalDateTime.ofInstant(periodEndDateTime.toInstant(), ZoneId.of(EST));

			if (isoLocalDateTime.isAfter(pStartLocalDateTime) && isoLocalDateTime.isBefore(pEndLocalDateTime)) {
				venue.setForecastDescription(period.getDetailedForecast());
				break;
			}
		}
	}
}
