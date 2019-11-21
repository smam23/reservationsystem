package com.techelevator.events.capstone.jdbc;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.events.capstone.Space;
import com.techelevator.events.capstone.SpaceDAO;

public class JDBCSpaceDAOIntegrationTest {
	private static SingleConnectionDataSource dataSource;
	private SpaceDAO dao;
	private JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior-venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Before
	public void setup() {
		dao = new JDBCSpaceDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	protected DataSource getDataSource() {
		return dataSource;
	}

	@Test
	public void get_all_spaces_for_selected_venue() {
		// Arrange
		int selectedVenue = 10;
		Space theSpace = getSpace();
		String sql = "select COUNT(*) AS numberOfSpaces from space where venue_id = ? ";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, selectedVenue);
		rows.next();
		int spaceCount = rows.getInt("numberOfSpaces");

		String insertSql = "INSERT INTO space (id, venue_id, name, is_accessible, daily_rate, max_occupancy) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertSql, theSpace.getId(), theSpace.getVenueId(), theSpace.getName(),
				theSpace.isAccessible(), theSpace.getDailyRate(), theSpace.getMaxOccupancy());

		Space spaceOne = getSpace();
		Space spaceTwo = getSpace();
		spaceTwo.setName("SpaceTwo");

		// Act
		List<Space> returnedSpace = dao.getAllSpacesForSelectedVenue(selectedVenue);
		// Assert
		Assert.assertNotNull(returnedSpace);
		Assert.assertEquals(spaceCount + 2, returnedSpace.size() + 1);
	}

	@Test
	public void get_available_spaces_if_open() {
		// Arrange
		Space theSpace = getAvailableSpace();
		int userVenueChoice = 3;
		long numOfAttendees = 100;
		LocalDate userStartDate = LocalDate.of(2019, 6, 10);
		LocalDate userEndDate = LocalDate.of(2019, 6, 14);
		int userStartMonth = userStartDate.getMonthValue();
		int userEndMonth = userEndDate.getMonthValue();
		String sql = "select name from space where venue_id = ? AND (open_from is null or open_from <= ? and open_to >= ?) ";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userVenueChoice, userStartMonth, userEndMonth);
		rows.next();

		String insertSql = "INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertSql, theSpace.getId(), theSpace.getVenueId(), theSpace.getName(),
				theSpace.isAccessible(), theSpace.getOpenMonth(), theSpace.getCloseMonth(), theSpace.getDailyRate(),
				theSpace.getMaxOccupancy());

		// Act
		List<Space> returnedOpenSpaces = dao.getAvailableSelectedVenueSpaces(userVenueChoice, numOfAttendees,
				userStartDate, userEndDate);
		
		// Assert
		Assert.assertNotNull(returnedOpenSpaces);
		Assert.assertEquals("Glass Castle", theSpace.getName());
		// if true that it doesn't overlap add to the reservation
		// if it does overlap return false
	}

	@Test
	public void no_available_spaces_returns_null() {
		// Arrange
		Space theSpace = getAvailableSpace();
		int userVenueChoice = 3;
		long numOfAttendees = 100;
		LocalDate userStartDate = LocalDate.of(2019, 11, 10);
		LocalDate userEndDate = LocalDate.of(2019, 11, 14);
		int userStartMonth = userStartDate.getMonthValue();
		int userEndMonth = userEndDate.getMonthValue();

		String sql = "select name from space where venue_id = ? AND (open_from is null or open_from <= ? and open_to >= ?) ";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userVenueChoice, userStartMonth, userEndMonth);
		rows.next();

		String insertSql = "INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertSql, theSpace.getId(), theSpace.getVenueId(), theSpace.getName(),
				theSpace.isAccessible(), theSpace.getOpenMonth(), theSpace.getCloseMonth(), theSpace.getDailyRate(),
				theSpace.getMaxOccupancy());

		// Act
		List<Space> returnedOpenSpaces = dao.getAvailableSelectedVenueSpaces(userVenueChoice, numOfAttendees,
				userStartDate, userEndDate);
		// Assert
		Assert.assertEquals(0, returnedOpenSpaces.size());
		// Assert.assertEquals("Glass Castle", theSpace.getName());
	}

	private Space getSpace() {
		Space theSpace = new Space();
		theSpace.setId(80);
		theSpace.setVenueId(10);
		theSpace.setName("Hollah");
		theSpace.setAccessible(true);
		theSpace.setDailyRate(1000);
		theSpace.setMaxOccupancy(100);

		return theSpace;
	}

	private Space getAvailableSpace() {
		Space theSpace = new Space();
		theSpace.setId(81);
		theSpace.setVenueId(3);
		theSpace.setName("Glass Castle");
		theSpace.setAccessible(false);
		theSpace.setOpenMonth(3);
		theSpace.setCloseMonth(10);
		theSpace.setDailyRate(1000);
		theSpace.setMaxOccupancy(100);

		return theSpace;
	}
}
