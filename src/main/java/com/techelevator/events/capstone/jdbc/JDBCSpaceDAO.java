package com.techelevator.events.capstone.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.events.capstone.Space;
import com.techelevator.events.capstone.SpaceDAO;

public class JDBCSpaceDAO implements SpaceDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSpaceDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Space> getAllSpacesForSelectedVenue(int selectedVenue) {
		List<Space> spaces = new ArrayList<Space>();

		String sql = "select name from space where venue_id = ?";

		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, selectedVenue);

		while (rows.next()) {
			Space space = mapRowToSpace(rows);
			spaces.add(space);
		}
		return spaces;
	}

	private Space mapRowToSpace(SqlRowSet rows) {
		Space space = new Space();
		space.setName("name");
		return space;
	}

	@Override
	public List<Space> getAvailableSelectedVenueSpaces(int selectedVenue, long numOfAttendees, LocalDate userStartDate,
			LocalDate userEndDate) {
		Space spaces = new Space();
		int userStartMonth = userStartDate.getMonthValue();
		int userEndMonth = userEndDate.getMonthValue();
		List<Space> availableSpaces = new ArrayList<Space>();

		String sql = "select space.id as space_number, name, daily_rate, max_occupancy, is_accessible from space "
				+ "join reservation on space.id = space_id where venue_id = ? and max_occupancy > ? "
				+ "and (open_from <= ? or open_from is null) and (open_to >= ? or open_from is null) "
				+ "and not (? >= reservation.start_date and end_date >= ?) group by space_number limit 5";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, selectedVenue, numOfAttendees, userStartMonth, userEndMonth,
				userStartDate, userEndDate);
		if (availableSpaces.size() == 0) {
			System.out.println("No available spaces for the chosen date, would you like to try a new search?"); //Needs to be in Menu
		}

		if (rows.next()) {
			spaces = mapRowToSpace(rows);
			availableSpaces.add(spaces);
		}
		return availableSpaces;
	}

	@Override
	public void save(Space space) {
		String insertSql = "INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (Default, ?, ?, ?, null, null, ?, ?) returning id";

		SqlRowSet rows = jdbcTemplate.queryForRowSet(insertSql, space.getId(), space.getVenueId(), space.getName(),
				space.isAccessible(), space.getDailyRate(), space.getMaxOccupancy());
		rows.next();
		long spaceId = rows.getLong("id");

		space.setId(spaceId);
	}

}
