package com.techelevator.events.capstone.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.events.capstone.Venue;
import com.techelevator.events.capstone.VenueDAO;

public class JDBCVenueDAO implements VenueDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCVenueDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Venue> getVenueList() {
		List<Venue> venues = new ArrayList<Venue>();

		SqlRowSet rows = jdbcTemplate.queryForRowSet("Select name from venue");

		while (rows.next()) {
			Venue venue = mapRowToVenue(rows);
			venues.add(venue);
		}
		return venues;
	}

	@Override
	public Venue getSelectedVenue(String name) {
		String selectSql = "select venue.id, venue.name, city.name, city.state_abbreviation, venue.description " + 
				"from venue " + 
				"join city on venue.city_id = city.id " + 
				"where venue.name = ? " + 
				"order by venue.name asc ";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(selectSql, name);

		Venue theVenue = null;
		if (rows.next()) {
			theVenue = mapRowToVenue(rows);
			String secondSql = "Select category.name " + 
					"from category " + 
					"join category_venue on category.id = category_venue.category_id " + 
					"where category_venue.venue_id = ? ";
		}
		return theVenue;
	}
	
	private Venue mapRowToVenue(SqlRowSet rows) {
		Venue venue = new Venue();
		venue.setName(rows.getString("name"));
		return venue;
	}

}
