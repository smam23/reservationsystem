package com.techelevator;

import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.events.capstone.ReservationDAO;
import com.techelevator.events.capstone.SpaceDAO;
import com.techelevator.events.capstone.VenueDAO;
import com.techelevator.events.capstone.jdbc.JDBCReservationDAO;
import com.techelevator.events.capstone.jdbc.JDBCSpaceDAO;
import com.techelevator.events.capstone.jdbc.JDBCVenueDAO;
import com.techelevator.events.view.Menu;

public class ExcelsiorCLI {
	// Main Menu Options
	private static final String MAIN_MENU_VENUE_OPTION = "List Venues";
	private static final String MAIN_MENU_QUIT_OPTION = "Quit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_VENUE_OPTION, MAIN_MENU_QUIT_OPTION };

	// View Venues Options
	private static final String RETURN_TO_PREVIOUS_SCREEN_OPTION = "Return to Previous Screen";

	// Venue Details Options
	private static final String VENUE_VIEW_SPACES_OPTION = "View Spaces";
	private static final String VENUE_SEARCH_RESERVATION_OPTION = "Search for Reservation";

	// List Venue Spaces Options
	private static final String SPACE_RESERVE_OPTION = "Reserve a Space";

	private Menu menu;
	private VenueDAO venueDAO;
	private SpaceDAO spaceDAO;
	private ReservationDAO reservationDAO;

	public static void main(String[] args) throws Exception {
		Menu menu = new Menu();
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior-venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		ExcelsiorCLI application = new ExcelsiorCLI(dataSource, menu);
		application.run();
	}

	public ExcelsiorCLI(DataSource datasource, Menu menu) {
		venueDAO = new JDBCVenueDAO(datasource);
		spaceDAO = new JDBCSpaceDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
		this.menu = menu;
	}

//	public String inputFromUser() {
//		Scanner scanner = new Scanner(System.in);
//		String in = scanner.nextLine();
//		return in;
//	}

	public void run() throws Exception {
		
		while (true) {
			System.out.println("Main Menu");
			System.out.println("What would you like to do?");
			String mainMenuChoice = (String) menu.getSelectionFromUser(MAIN_MENU_OPTIONS);
			if (mainMenuChoice.equals(MAIN_MENU_VENUE_OPTION)) {
				System.out.println(venueDAO.getVenueList());
				break;
			} else if (mainMenuChoice.equals(MAIN_MENU_QUIT_OPTION)) {
				System.out.println("Thank you for nothing.");
				break;
			}
		}
	}
}
