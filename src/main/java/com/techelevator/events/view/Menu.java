package com.techelevator.events.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

	private Scanner in = new Scanner(System.in);

	String selectedOption;

	public String getSelectionFromUser(String[] options) {

		while (selectedOption == null) {

			displayUserOptions(options);

			try {
				int userChoice = in.nextInt();
				if (userChoice - 1 >= options.length || userChoice < 1) {
					displayUserMessage("Please select a valid option!");
				} else {
					selectedOption = options[userChoice - 1];
				}
			} catch (InputMismatchException e) {
				System.out.println("Please select a valid option");
			} finally {
				in.nextLine();
			}
		}
		return selectedOption;
	}

	public void displayUserMessage(String message) {
		System.out.println(message);
		System.out.flush();
	}

	public void displayUserOptions(String[] options) {
		for (int i = 0; i < options.length; i++) {
			System.out.println((i + 1) + ")" + options[i]);
		}
		System.out.print(">>>>>");
		System.out.flush();
	}

}
