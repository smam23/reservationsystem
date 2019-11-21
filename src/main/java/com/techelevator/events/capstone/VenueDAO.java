package com.techelevator.events.capstone;

import java.util.List;



public interface VenueDAO {
	
	public List<Venue> getVenueList();
	
	public Venue getSelectedVenue(String name);

}
