package com.techelevator.events.capstone.jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.events.capstone.Venue;
import com.techelevator.events.capstone.VenueDAO;

public class JDBCVenueDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private VenueDAO dao;
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
		dao = new JDBCVenueDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	protected DataSource getDataSource() {
		return dataSource;
	}

	@Test
	public void get_venue_list() {
		// Arrange
		String sql = "select COUNT(*) AS numberOfVenues from venue";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
		rows.next();
		int venueCount = rows.getInt("numberOfVenues");
		
		// Act
		List<Venue> returnedVenue = dao.getVenueList();
		// Assert
		Assert.assertNotNull(returnedVenue);
		Assert.assertEquals(venueCount, returnedVenue.size());
	}
	
	@Test
	public void get_selected_venue_name() {
		// Arrange
		Venue theVenue = getVenue();
		String insertSql =  "INSERT INTO venue (id, name, city_id, description) VALUES (?, ?, ?, ?)";
		// SqlRowSet rows = jdbcTemplate.queryForRowSet(insertSql, theVenue.getName(), theVenue.getCity(), theVenue.getState(), theVenue.getDescription(), theVenue.getCategory());
		jdbcTemplate.update(insertSql, theVenue.getId(), theVenue.getName(), 
				theVenue.getCityId(), theVenue.getDescription());
		
		//Act
		Venue returnedVenue = dao.getSelectedVenue(theVenue.getName());
		//Assert
		Assert.assertNotNull(returnedVenue);
		assertVenuesAreEqual(theVenue, returnedVenue);
	}
	
	private Venue getVenue() {
		Venue theVenue = new Venue();
		theVenue.setId(16);
		theVenue.setName("testName");
		theVenue.setCityId(2);
		theVenue.setDescription("Sinay");
		return theVenue;
	}
	
	private void assertVenuesAreEqual(Venue expected, Venue actual) {
		// assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		// assertEquals(expected.getCityId(), actual.getCityId());
		// assertEquals(expected.getDescription(), actual.getDescription());		
	}
}
