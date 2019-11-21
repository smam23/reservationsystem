package com.techelevator.events.capstone.jdbc;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.events.capstone.Reservation;
import com.techelevator.events.capstone.ReservationDAO;

public class JDBCReservationDAOIntegrationTest {
	private static SingleConnectionDataSource dataSource;
	private ReservationDAO dao;
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
		dao = new JDBCReservationDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	protected DataSource getDataSource() {
		return dataSource;
	}

	@Test
	public void create_reservation() {
		// Arrange
		Reservation reservation = createDummyReservation();
		int reservationId = 8;
		int spaceId = 12;
		LocalDate startDate = LocalDate.of(2019, 11, 15);
		LocalDate endDate = LocalDate.of(2019, 11, 20);
		long numOfAttendees = 145;
		String reservedFor = "Team 9";
		// Act
		long newReservation = dao.createReservation(reservationId, spaceId, numOfAttendees, startDate, endDate, reservedFor);
		// Assert
//		Assert.assertNotEquals(0, newReservation.getReservationId());
//		Reservation returnedReservation = dao.getReservationById(reservation.getReservationId());
		Assert.assertNotEquals(reservation.getReservationId(), newReservation.getReservationId());
	}

	@Test
	public void reserve_before_1st_day_of_reservation_is_true() {
		// Arrange
		// Dummy user input info --> users desired dates for reservation
		LocalDate userStartDate = LocalDate.of(2019, 11, 9);
		int userLengthOfReservation = 2;
		LocalDate userEndDate = userStartDate.plusDays(userLengthOfReservation);
		// Dummy data "already" in table
		Reservation reservation = createDummyReservation();
		Reservation reservation1 = createDummyReservation1();
		String insertSql = "INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertSql, reservation.getReservationId(), reservation.getSpaceId(),
				reservation.getNumOfAttendees(), reservation.getStartDate(), reservation.getEndDate(),
				reservation.getReservedFor());
		// Act
		// Assert
		Assert.assertTrue(userEndDate.isBefore(reservation.getStartDate()));
		Assert.assertTrue(userEndDate.isBefore(reservation1.getStartDate()));
	}

	@Test
	public void reserve_ends_1st_day_reservation_starts_is_true() {
		// Arrange
		// Dummy user input info --> users desired dates for reservation
		LocalDate userStartDate = LocalDate.of(2019, 11, 9);
		int userLengthOfReservation = 3;
		LocalDate userEndDate = userStartDate.plusDays(userLengthOfReservation);
		// Dummy data "already" in table
		Reservation reservation = createDummyReservation();
		Reservation reservation1 = createDummyReservation1();
		String insertSql = "INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertSql, reservation.getReservationId(), reservation.getSpaceId(),
				reservation.getNumOfAttendees(), reservation.getStartDate(), reservation.getEndDate(),
				reservation.getReservedFor());
		// Act
		// Assert
		Assert.assertEquals(userEndDate, reservation.getStartDate());
		Assert.assertTrue(userEndDate.isBefore(reservation1.getStartDate()));
	}

	@Test
	public void starts_before_ends_during_is_false() {
		// Arrange
		// Dummy user input info --> users desired dates for reservation
		LocalDate userStartDate = LocalDate.of(2019, 11, 9);
		int userLengthOfReservation = 4;
		LocalDate userEndDate = userStartDate.plusDays(userLengthOfReservation);
		// Dummy data "already" in table
		Reservation reservation = createDummyReservation();
		Reservation reservation1 = createDummyReservation1();
		String insertSql = "INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertSql, reservation.getReservationId(), reservation.getSpaceId(),
				reservation.getNumOfAttendees(), reservation.getStartDate(), reservation.getEndDate(),
				reservation.getReservedFor());
		// Act
		// Assert
		Assert.assertFalse(userEndDate.isBefore(reservation.getStartDate()));
		Assert.assertTrue(userEndDate.isBefore(reservation1.getStartDate()));
	}

	@Test
	public void starts_on_first_day_ends_during_is_false() {

	}

	@Test
	public void starts_and_ends_during_reservation_is_false() {

	}

	@Test
	public void starts_during_ends_after_reservation_is_false() {

	}

	@Test
	public void starts_on_last_day_ends_after_is_true() {

	}

	@Test
	public void starts_after_ends_after_is_true() {

	}

	private Reservation createDummyReservation() {
		// List<Reservation> existingReservations = new ArrayList<Reservation>();
		String truncateSql = "truncate reservation cascade";
		jdbcTemplate.update(truncateSql);
		Reservation reservation = new Reservation();
		reservation.setReservationId(1);
		reservation.setSpaceId(10);
		reservation.setStartDate(LocalDate.of(2019, 11, 12));
		reservation.setEndDate(LocalDate.of(2019, 11, 15));
		reservation.setNumOfAttendees(150);
		reservation.setReservedFor("Team 9 OSU");
		// existingReservations.add(reservation);

		return reservation;
	}

	private Reservation createDummyReservation1() {
		// List<Reservation> existingReservations = new ArrayList<Reservation>();
		Reservation reservation = new Reservation();
		reservation.setReservationId(2);
		reservation.setSpaceId(10);
		reservation.setStartDate(LocalDate.of(2019, 11, 16));
		reservation.setEndDate(LocalDate.of(2019, 11, 18));
		reservation.setNumOfAttendees(150);
		reservation.setReservedFor("Team 9 OSU");
		// existingReservations.add(reservation);

		return reservation;
	}
}
