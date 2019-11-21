package com.techelevator.events.capstone;

import java.time.LocalDate;
import java.util.List;

public interface SpaceDAO {

	public List<Space> getAllSpacesForSelectedVenue(int selectedVenue);
	
	public List<Space> getAvailableSelectedVenueSpaces(int selectedVenue, long numOfAttendees, LocalDate userStartDate,LocalDate userEndDate);
	
	public void save(Space space);

}
