package com.techelevator.events.capstone.jdbc;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.events.capstone.Reservation;
import com.techelevator.events.capstone.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO{

	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public long createReservation(int reservationId, int spaceId, long numOfAttendees, LocalDate startDate, LocalDate endDate, String reservedFor) {
		String insertSql = "INSERT INTO reservation (reservation_id, space_id, number_of_attendees, "
				+ "		start_date, end_date, reserved_for) VALUES (?, ?, ?, ?, ?, ?) returning reservation_id";
		
		SqlRowSet rows = jdbcTemplate.queryForRowSet(insertSql, reservationId, spaceId, numOfAttendees, startDate, endDate, reservedFor);
		rows.next();
		long confirmationId= rows.getLong("reservation_id");

		return confirmationId;
	}
	 
	// Create a method that checks the existing Reservations per venue. Check that the dates requested "fits" in the schedule. True if falls outside, false if inside. 
	
	public Reservation getReservationById(long id) {
		String selectSql = "SELECT reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for FROM reservation Where reservation_id = ?";
		
		SqlRowSet rows = jdbcTemplate.queryForRowSet(selectSql, id);
		
		Reservation reservation = null;
		if (rows.next()) {
			reservation = mapRowToReservation(rows);
		}
		return reservation;
	}
	private Reservation mapRowToReservation(SqlRowSet rows) {
		Reservation reservation = new Reservation();
		
		reservation.setReservationId(rows.getLong("reservation_id"));
		reservation.setSpaceId(10);
		reservation.setNumOfAttendees(145);
		reservation.setStartDate(LocalDate.of(2019, 11, 15));
		reservation.setEndDate(LocalDate.of(2019, 11, 20));
		reservation.setReservedFor("reserved_for");

		return reservation;
	}
	
//	@Override
//	public void save(Reservation newReservation) {
//		// TODO Auto-generated method stub
//	}

}
