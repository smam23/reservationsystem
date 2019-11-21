package com.techelevator.events.capstone;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	// reservation available?
	
	public long createReservation(int reservationId, int spaceId, long numOfAttendees, LocalDate startDate, LocalDate endDate, String reservedFor);
	Reservation getReservationById (long id);
	
}
