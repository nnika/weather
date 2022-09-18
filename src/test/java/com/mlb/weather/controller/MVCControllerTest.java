package com.mlb.weather.controller;

import com.mlb.weather.model.*;
import com.mlb.weather.model.mlb.*;
import com.mlb.weather.service.StatsAPIService;
import com.mlb.weather.service.WeatherAPIService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MVCController.class})
@ExtendWith(SpringExtension.class)
class MVCControllerTest {
	@Autowired
	private MVCController mVCController;

	@MockBean
	private StatsAPIService statsAPIService;

	@MockBean
	private WeatherAPIService weatherAPIService;


	/**
	 * Method under test: {@link MVCController#addForecastAttribute(Model, Points)}
	 */
	@Test
	void testAddForecastAttribute2() {
		ForecastProps forecastProps = new ForecastProps();
		forecastProps.setPeriods(new ArrayList<>());

		Period period = new Period();
		period.setDetailedForecast("Detailed Forecast");
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		period.setEndTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		period.setNumber(10);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		period.setStartTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		period.setTemperature(10.0d);
		period.setTemperatureUnit("Temperature Unit");

		ArrayList<Period> periodList = new ArrayList<>();
		periodList.add(period);

		ForecastProps forecastProps1 = new ForecastProps();
		forecastProps1.setPeriods(periodList);
		Forecast forecast = mock(Forecast.class);
		when(forecast.getProperties()).thenReturn(forecastProps1);
		doNothing().when(forecast).setId((String) any());
		doNothing().when(forecast).setProperties((ForecastProps) any());
		forecast.setId("42");
		forecast.setProperties(forecastProps);
		when(weatherAPIService.getForecast((String) any(), anyInt(), anyInt())).thenReturn(forecast);
		ConcurrentModel model = new ConcurrentModel();

		PointsProps pointsProps = new PointsProps();
		pointsProps.setApplicableLocation("Applicable Location");
		pointsProps.setCounty("3");
		pointsProps.setCountyZone("3");
		pointsProps.setCwa("Cwa");
		pointsProps.setFireWeatherZone("Fire Weather Zone");
		pointsProps.setFireWeatherZoneUrl("https://example.org/example");
		pointsProps.setForecast("Forecast");
		pointsProps.setForecastGridData("Forecast Grid Data");
		pointsProps.setForecastHourly("https://example.org/example");
		pointsProps.setForecastOffice("Forecast Office");
		pointsProps.setForecastZone("Forecast Zone");
		pointsProps.setGridId("42");
		pointsProps.setGridX(1);
		pointsProps.setGridXDistance("Grid XDistance");
		pointsProps.setGridY(1);
		pointsProps.setGridYDistance("Grid YDistance");
		pointsProps.setRadarStation("Radar Station");
		pointsProps.setRadarStationUrl("https://example.org/example");
		pointsProps.setTimeZone("UTC");
		pointsProps.setZone("Zone");

		Points points = new Points();
		points.setId("42");
		points.setProperties(pointsProps);
		mVCController.addForecastAttribute(model, points);
		verify(weatherAPIService).getForecast((String) any(), anyInt(), anyInt());
		verify(forecast).getProperties();
		verify(forecast).setId((String) any());
		verify(forecast).setProperties((ForecastProps) any());
	}

	/**
	 * Method under test: {@link MVCController#findSchedule(Schedule, Model)}
	 */
	@Test
	void testFindSchedule() throws Exception {
		GameDateList gameDateList = new GameDateList();
		gameDateList.setGameDates(new ArrayList<>());
		gameDateList.setTotalGames(1);
		when(statsAPIService.getSchedule((String) any(), (String) any(), (String) any())).thenReturn(gameDateList);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/findschedule");
		MockMvcBuilders.standaloneSetup(mVCController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().size(1))
				.andExpect(MockMvcResultMatchers.model().attributeExists("schedule"))
				.andExpect(MockMvcResultMatchers.view().name("schedule"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("schedule"));
	}

	/**
	 * Method under test: {@link MVCController#findSchedule(Schedule, Model)}
	 */
	@Test
	void testFindSchedule2() throws Exception {
		GameDate gameDate = new GameDate();
		gameDate.setDate("2020-03-01");
		gameDate.setGames(new ArrayList<>());

		ArrayList<GameDate> gameDateList = new ArrayList<>();
		gameDateList.add(gameDate);

		GameDateList gameDateList1 = new GameDateList();
		gameDateList1.setGameDates(gameDateList);
		gameDateList1.setTotalGames(1);
		when(statsAPIService.getSchedule((String) any(), (String) any(), (String) any())).thenReturn(gameDateList1);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/findschedule");
		MockMvcBuilders.standaloneSetup(mVCController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().size(1))
				.andExpect(MockMvcResultMatchers.model().attributeExists("schedule"))
				.andExpect(MockMvcResultMatchers.view().name("schedule"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("schedule"));
	}

	/**
	 * Method under test: {@link MVCController#findVenue(Venue, Model)}
	 */
	@Test
	void testFindVenue() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/findvenue");
		MockMvcBuilders.standaloneSetup(mVCController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().size(2))
				.andExpect(MockMvcResultMatchers.model().attributeExists("error", "venue"))
				.andExpect(MockMvcResultMatchers.view().name("venue"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("venue"));
	}

	/**
	 * Method under test: {@link MVCController#findVenue(Venue, Model)}
	 */
	@Test
	void testFindVenue2() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/findvenue", "Uri Variables");
		MockMvcBuilders.standaloneSetup(mVCController)
				.build()
				.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().size(2))
				.andExpect(MockMvcResultMatchers.model().attributeExists("error", "venue"))
				.andExpect(MockMvcResultMatchers.view().name("venue"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("venue"));
	}

	/**
	 * Method under test: {@link MVCController#traverseGameDateList(Schedule, GameDateList)}
	 */
	@Test
	void testTraverseGameDateList() {
		Schedule schedule = new Schedule();
		schedule.setEndDate("2020-03-01");
		schedule.setStartDate("2020-03-01");
		schedule.setTeamIds("Team Ids");

		GameDateList gameDateList = new GameDateList();
		gameDateList.setGameDates(new ArrayList<>());
		gameDateList.setTotalGames(1);
		mVCController.traverseGameDateList(schedule, gameDateList);
		assertEquals("2020-03-01", schedule.getEndDate());
		assertEquals("Team Ids", schedule.getTeamIds());
		assertEquals("2020-03-01", schedule.getStartDate());
		assertTrue(gameDateList.getGameDates().isEmpty());
		assertEquals(1, gameDateList.getTotalGames());
		assertFalse(mVCController.currentForecastEnabled);
	}

	/**
	 * Method under test: {@link MVCController#traverseGameDateList(Schedule, GameDateList)}
	 */
	@Test
	void testTraverseGameDateList2() {
		Schedule schedule = mock(Schedule.class);
		doNothing().when(schedule).setEndDate((String) any());
		doNothing().when(schedule).setStartDate((String) any());
		doNothing().when(schedule).setTeamIds((String) any());
		schedule.setEndDate("2020-03-01");
		schedule.setStartDate("2020-03-01");
		schedule.setTeamIds("Team Ids");

		GameDateList gameDateList = new GameDateList();
		gameDateList.setGameDates(new ArrayList<>());
		gameDateList.setTotalGames(1);
		mVCController.traverseGameDateList(schedule, gameDateList);
		verify(schedule).setEndDate((String) any());
		verify(schedule).setStartDate((String) any());
		verify(schedule).setTeamIds((String) any());
		assertTrue(gameDateList.getGameDates().isEmpty());
		assertEquals(1, gameDateList.getTotalGames());
		assertFalse(mVCController.currentForecastEnabled);
	}

	/**
	 * Method under test: {@link MVCController#traverseGameDateList(Schedule, GameDateList)}
	 */
	@Test
	void testTraverseGameDateList3() {
		Schedule schedule = mock(Schedule.class);
		doNothing().when(schedule).setEndDate((String) any());
		doNothing().when(schedule).setStartDate((String) any());
		doNothing().when(schedule).setTeamIds((String) any());
		schedule.setEndDate("2020-03-01");
		schedule.setStartDate("2020-03-01");
		schedule.setTeamIds("Team Ids");

		GameDate gameDate = new GameDate();
		gameDate.setDate("2020-03-01");
		gameDate.setGames(new ArrayList<>());

		ArrayList<GameDate> gameDateList = new ArrayList<>();
		gameDateList.add(gameDate);

		GameDateList gameDateList1 = new GameDateList();
		gameDateList1.setGameDates(gameDateList);
		gameDateList1.setTotalGames(1);
		mVCController.traverseGameDateList(schedule, gameDateList1);
		verify(schedule).setEndDate((String) any());
		verify(schedule).setStartDate((String) any());
		verify(schedule).setTeamIds((String) any());
		assertEquals(1, gameDateList1.getGameDates().size());
		assertEquals(1, gameDateList1.getTotalGames());
		assertFalse(mVCController.currentForecastEnabled);
	}

	/**
	 * Method under test: {@link MVCController#traverseGameDateList(Schedule, GameDateList)}
	 */
	@Test
	void testTraverseGameDateList4() {
		ForecastProps forecastProps = new ForecastProps();
		forecastProps.setPeriods(new ArrayList<>());

		Forecast forecast = new Forecast();
		forecast.setId("42");
		forecast.setProperties(forecastProps);

		Coordinates coordinates = new Coordinates();
		coordinates.setLatitude(1);
		coordinates.setLongitude(1);

		Location location = new Location();
		location.setCity("Oxford");
		location.setDefaultCoordinates(coordinates);
		location.setState("MD");

		Venue venue = new Venue();
		venue.setForecast(forecast);
		venue.setForecastDescription("Forecast Description");
		venue.setFormId("42");
		venue.setId(1);
		venue.setLocation(location);
		venue.setName("Name");
		when(statsAPIService.getVenue(anyInt())).thenReturn(venue);

		PointsProps pointsProps = new PointsProps();
		pointsProps.setApplicableLocation("Applicable Location");
		pointsProps.setCounty("3");
		pointsProps.setCountyZone("3");
		pointsProps.setCwa("Cwa");
		pointsProps.setFireWeatherZone("Fire Weather Zone");
		pointsProps.setFireWeatherZoneUrl("https://example.org/example");
		pointsProps.setForecast("Forecast");
		pointsProps.setForecastGridData("Forecast Grid Data");
		pointsProps.setForecastHourly("https://example.org/example");
		pointsProps.setForecastOffice("Forecast Office");
		pointsProps.setForecastZone("Forecast Zone");
		pointsProps.setGridId("42");
		pointsProps.setGridX(1);
		pointsProps.setGridXDistance("Grid XDistance");
		pointsProps.setGridY(1);
		pointsProps.setGridYDistance("Grid YDistance");
		pointsProps.setRadarStation("Radar Station");
		pointsProps.setRadarStationUrl("https://example.org/example");
		pointsProps.setTimeZone("UTC");
		pointsProps.setZone("Zone");

		Points points = new Points();
		points.setId("42");
		points.setProperties(pointsProps);

		ForecastProps forecastProps1 = new ForecastProps();
		forecastProps1.setPeriods(new ArrayList<>());

		Forecast forecast1 = new Forecast();
		forecast1.setId("42");
		forecast1.setProperties(forecastProps1);
		when(weatherAPIService.getForecast((String) any(), anyInt(), anyInt())).thenReturn(forecast1);
		when(weatherAPIService.getPoints(anyInt(), anyInt())).thenReturn(points);
		Schedule schedule = mock(Schedule.class);
		when(schedule.getStartDate()).thenReturn("2020-03-01");
		doNothing().when(schedule).setEndDate((String) any());
		doNothing().when(schedule).setStartDate((String) any());
		doNothing().when(schedule).setTeamIds((String) any());
		schedule.setEndDate("2020-03-01");
		schedule.setStartDate("2020-03-01");
		schedule.setTeamIds("Team Ids");

		Team team = new Team();
		team.setId("42");
		team.setName("Name");

		Away away = new Away();
		away.setTeam(team);

		Team team1 = new Team();
		team1.setId("42");
		team1.setName("Name");

		Home home = new Home();
		home.setTeam(team1);

		Teams teams = new Teams();
		teams.setAway(away);
		teams.setHome(home);

		ForecastProps forecastProps2 = new ForecastProps();
		forecastProps2.setPeriods(new ArrayList<>());

		Forecast forecast2 = new Forecast();
		forecast2.setId("42");
		forecast2.setProperties(forecastProps2);

		Coordinates coordinates1 = new Coordinates();
		coordinates1.setLatitude(1);
		coordinates1.setLongitude(1);

		Location location1 = new Location();
		location1.setCity("Oxford");
		location1.setDefaultCoordinates(coordinates1);
		location1.setState("MD");

		Venue venue1 = new Venue();
		venue1.setForecast(forecast2);
		venue1.setForecastDescription("Forecast Description");
		venue1.setFormId("42");
		venue1.setId(1);
		venue1.setLocation(location1);
		venue1.setName("Name");

		Game game = new Game();
		game.setTeams(teams);
		game.setVenue(venue1);

		ArrayList<Game> gameList = new ArrayList<>();
		gameList.add(game);

		GameDate gameDate = new GameDate();
		gameDate.setDate("2020-03-01");
		gameDate.setGames(gameList);

		ArrayList<GameDate> gameDateList = new ArrayList<>();
		gameDateList.add(gameDate);

		GameDateList gameDateList1 = new GameDateList();
		gameDateList1.setGameDates(gameDateList);
		gameDateList1.setTotalGames(1);
		mVCController.traverseGameDateList(schedule, gameDateList1);
		verify(statsAPIService).getVenue(anyInt());
		verify(weatherAPIService).getForecast((String) any(), anyInt(), anyInt());
		verify(weatherAPIService).getPoints(anyInt(), anyInt());
		verify(schedule).getStartDate();
		verify(schedule).setEndDate((String) any());
		verify(schedule).setStartDate((String) any());
		verify(schedule).setTeamIds((String) any());
		Venue venue2 = gameDateList1.getGameDates().get(0).getGames().get(0).getVenue();
		assertSame(venue, venue2);
		assertEquals("Not available. Date given beyond forecast periods.", venue2.getForecastDescription());
		assertEquals(forecast2, venue2.getForecast());
	}

	/**
	 * Method under test: {@link MVCController#setAllElements(Schedule, Venue, PointsProps)}
	 */
	@Test
	void testSetAllElements() {
		ForecastProps forecastProps = new ForecastProps();
		forecastProps.setPeriods(new ArrayList<>());

		Forecast forecast = new Forecast();
		forecast.setId("42");
		forecast.setProperties(forecastProps);
		when(weatherAPIService.getForecast((String) any(), anyInt(), anyInt())).thenReturn(forecast);

		Schedule schedule = new Schedule();
		schedule.setEndDate("2020-03-01");
		schedule.setStartDate("2020-03-01");
		schedule.setTeamIds("Team Ids");

		ForecastProps forecastProps1 = new ForecastProps();
		forecastProps1.setPeriods(new ArrayList<>());

		Forecast forecast1 = new Forecast();
		forecast1.setId("42");
		forecast1.setProperties(forecastProps1);

		Coordinates coordinates = new Coordinates();
		coordinates.setLatitude(1);
		coordinates.setLongitude(1);

		Location location = new Location();
		location.setCity("Oxford");
		location.setDefaultCoordinates(coordinates);
		location.setState("MD");

		Venue venue = new Venue();
		venue.setForecast(forecast1);
		venue.setForecastDescription("Forecast Description");
		venue.setFormId("42");
		venue.setId(1);
		venue.setLocation(location);
		venue.setName("Name");

		PointsProps pointsProps = new PointsProps();
		pointsProps.setApplicableLocation("Applicable Location");
		pointsProps.setCounty("3");
		pointsProps.setCountyZone("3");
		pointsProps.setCwa("Cwa");
		pointsProps.setFireWeatherZone("Fire Weather Zone");
		pointsProps.setFireWeatherZoneUrl("https://example.org/example");
		pointsProps.setForecast("Forecast");
		pointsProps.setForecastGridData("Forecast Grid Data");
		pointsProps.setForecastHourly("https://example.org/example");
		pointsProps.setForecastOffice("Forecast Office");
		pointsProps.setForecastZone("Forecast Zone");
		pointsProps.setGridId("42");
		pointsProps.setGridX(1);
		pointsProps.setGridXDistance("Grid XDistance");
		pointsProps.setGridY(1);
		pointsProps.setGridYDistance("Grid YDistance");
		pointsProps.setRadarStation("Radar Station");
		pointsProps.setRadarStationUrl("https://example.org/example");
		pointsProps.setTimeZone("UTC");
		pointsProps.setZone("Zone");
		mVCController.setAllElements(schedule, venue, pointsProps);
		verify(weatherAPIService).getForecast((String) any(), anyInt(), anyInt());
		assertEquals(forecast1, venue.getForecast());
		assertEquals("Not available. Date given beyond forecast periods.", venue.getForecastDescription());
	}

	/**
	 * Method under test: {@link MVCController#setAllElements(Schedule, Venue, PointsProps)}
	 */
	@Test
	void testSetAllElements2() {
		ForecastProps forecastProps = new ForecastProps();
		forecastProps.setPeriods(new ArrayList<>());

		Period period = new Period();
		period.setDetailedForecast("Detailed Forecast");
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		period.setEndTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		period.setNumber(10);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		period.setStartTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		period.setTemperature(10.0d);
		period.setTemperatureUnit("Temperature Unit");

		ArrayList<Period> periodList = new ArrayList<>();
		periodList.add(period);

		ForecastProps forecastProps1 = new ForecastProps();
		forecastProps1.setPeriods(periodList);
		Forecast forecast = mock(Forecast.class);
		when(forecast.getProperties()).thenReturn(forecastProps1);
		doNothing().when(forecast).setId((String) any());
		doNothing().when(forecast).setProperties((ForecastProps) any());
		forecast.setId("42");
		forecast.setProperties(forecastProps);
		when(weatherAPIService.getForecast((String) any(), anyInt(), anyInt())).thenReturn(forecast);

		Schedule schedule = new Schedule();
		schedule.setEndDate("2020-03-01");
		schedule.setStartDate("2020-03-01");
		schedule.setTeamIds("Team Ids");

		ForecastProps forecastProps2 = new ForecastProps();
		forecastProps2.setPeriods(new ArrayList<>());

		Forecast forecast1 = new Forecast();
		forecast1.setId("42");
		forecast1.setProperties(forecastProps2);

		Coordinates coordinates = new Coordinates();
		coordinates.setLatitude(1);
		coordinates.setLongitude(1);

		Location location = new Location();
		location.setCity("Oxford");
		location.setDefaultCoordinates(coordinates);
		location.setState("MD");

		Venue venue = new Venue();
		venue.setForecast(forecast1);
		venue.setForecastDescription("Forecast Description");
		venue.setFormId("42");
		venue.setId(1);
		venue.setLocation(location);
		venue.setName("Name");

		PointsProps pointsProps = new PointsProps();
		pointsProps.setApplicableLocation("Applicable Location");
		pointsProps.setCounty("3");
		pointsProps.setCountyZone("3");
		pointsProps.setCwa("Cwa");
		pointsProps.setFireWeatherZone("Fire Weather Zone");
		pointsProps.setFireWeatherZoneUrl("https://example.org/example");
		pointsProps.setForecast("Forecast");
		pointsProps.setForecastGridData("Forecast Grid Data");
		pointsProps.setForecastHourly("https://example.org/example");
		pointsProps.setForecastOffice("Forecast Office");
		pointsProps.setForecastZone("Forecast Zone");
		pointsProps.setGridId("42");
		pointsProps.setGridX(1);
		pointsProps.setGridXDistance("Grid XDistance");
		pointsProps.setGridY(1);
		pointsProps.setGridYDistance("Grid YDistance");
		pointsProps.setRadarStation("Radar Station");
		pointsProps.setRadarStationUrl("https://example.org/example");
		pointsProps.setTimeZone("UTC");
		pointsProps.setZone("Zone");
		mVCController.setAllElements(schedule, venue, pointsProps);
		verify(weatherAPIService).getForecast((String) any(), anyInt(), anyInt());
		verify(forecast).getProperties();
		verify(forecast).setId((String) any());
		verify(forecast).setProperties((ForecastProps) any());
		assertEquals("Not available. Date given beyond forecast periods.", venue.getForecastDescription());
	}



	/**
	 * Method under test: {@link MVCController#traversePoints(Venue, ForecastProps, LocalDateTime)}
	 */
	@Test
	void testTraversePoints() {
		ForecastProps forecastProps = new ForecastProps();
		forecastProps.setPeriods(new ArrayList<>());

		Forecast forecast = new Forecast();
		forecast.setId("42");
		forecast.setProperties(forecastProps);

		Coordinates coordinates = new Coordinates();
		coordinates.setLatitude(1);
		coordinates.setLongitude(1);

		Location location = new Location();
		location.setCity("Oxford");
		location.setDefaultCoordinates(coordinates);
		location.setState("MD");

		Venue venue = new Venue();
		venue.setForecast(forecast);
		venue.setForecastDescription("Forecast Description");
		venue.setFormId("42");
		venue.setId(1);
		venue.setLocation(location);
		venue.setName("Name");

		ForecastProps forecastProps1 = new ForecastProps();
		forecastProps1.setPeriods(new ArrayList<>());
		MVCController.traversePoints(venue, forecastProps1, LocalDateTime.of(1, 1, 1, 1, 1));
		Forecast forecast1 = venue.getForecast();
		assertSame(forecast, forecast1);
		assertEquals("Name", venue.getName());
		assertEquals("Forecast Description", venue.getForecastDescription());
		assertEquals(1, venue.getId());
		assertSame(location, venue.getLocation());
		assertEquals("42", venue.getFormId());
		List<Period> expectedPeriods = forecast1.getProperties().getPeriods();
		assertEquals(expectedPeriods, forecastProps1.getPeriods());
	}

	/**
	 * Method under test: {@link MVCController#traversePoints(Venue, ForecastProps, LocalDateTime)}
	 */
	@Test
	void testTraversePoints2() {
		ForecastProps forecastProps = new ForecastProps();
		ArrayList<Period> periodList = new ArrayList<>();
		forecastProps.setPeriods(periodList);

		Forecast forecast = new Forecast();
		forecast.setId("42");
		forecast.setProperties(forecastProps);

		Coordinates coordinates = new Coordinates();
		coordinates.setLatitude(1);
		coordinates.setLongitude(1);

		Location location = new Location();
		location.setCity("Oxford");
		location.setDefaultCoordinates(coordinates);
		location.setState("MD");
		Venue venue = mock(Venue.class);
		doNothing().when(venue).setForecast((Forecast) any());
		doNothing().when(venue).setForecastDescription((String) any());
		doNothing().when(venue).setFormId((String) any());
		doNothing().when(venue).setId(anyInt());
		doNothing().when(venue).setLocation((Location) any());
		doNothing().when(venue).setName((String) any());
		venue.setForecast(forecast);
		venue.setForecastDescription("Forecast Description");
		venue.setFormId("42");
		venue.setId(1);
		venue.setLocation(location);
		venue.setName("Name");

		ForecastProps forecastProps1 = new ForecastProps();
		forecastProps1.setPeriods(new ArrayList<>());
		MVCController.traversePoints(venue, forecastProps1, LocalDateTime.of(1, 1, 1, 1, 1));
		verify(venue).setForecast((Forecast) any());
		verify(venue).setForecastDescription((String) any());
		verify(venue).setFormId((String) any());
		verify(venue).setId(anyInt());
		verify(venue).setLocation((Location) any());
		verify(venue).setName((String) any());
		assertEquals(periodList, forecastProps1.getPeriods());
	}

	/**
	 * Method under test: {@link MVCController#traversePoints(Venue, ForecastProps, LocalDateTime)}
	 */
	@Test
	void testTraversePoints3() {
		ForecastProps forecastProps = new ForecastProps();
		forecastProps.setPeriods(new ArrayList<>());

		Forecast forecast = new Forecast();
		forecast.setId("42");
		forecast.setProperties(forecastProps);

		Coordinates coordinates = new Coordinates();
		coordinates.setLatitude(1);
		coordinates.setLongitude(1);

		Location location = new Location();
		location.setCity("Oxford");
		location.setDefaultCoordinates(coordinates);
		location.setState("MD");
		Venue venue = mock(Venue.class);
		doNothing().when(venue).setForecast((Forecast) any());
		doNothing().when(venue).setForecastDescription((String) any());
		doNothing().when(venue).setFormId((String) any());
		doNothing().when(venue).setId(anyInt());
		doNothing().when(venue).setLocation((Location) any());
		doNothing().when(venue).setName((String) any());
		venue.setForecast(forecast);
		venue.setForecastDescription("Forecast Description");
		venue.setFormId("42");
		venue.setId(1);
		venue.setLocation(location);
		venue.setName("Name");

		Period period = new Period();
		period.setDetailedForecast("Detailed Forecast");
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		period.setEndTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		period.setNumber(10);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		period.setStartTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		period.setTemperature(10.0d);
		period.setTemperatureUnit("Temperature Unit");

		ArrayList<Period> periodList = new ArrayList<>();
		periodList.add(period);

		ForecastProps forecastProps1 = new ForecastProps();
		forecastProps1.setPeriods(periodList);
		MVCController.traversePoints(venue, forecastProps1, LocalDateTime.of(1, 1, 1, 1, 1));
		verify(venue).setForecast((Forecast) any());
		verify(venue).setForecastDescription((String) any());
		verify(venue).setFormId((String) any());
		verify(venue).setId(anyInt());
		verify(venue).setLocation((Location) any());
		verify(venue).setName((String) any());
		assertEquals(1, forecastProps1.getPeriods().size());
	}
}

