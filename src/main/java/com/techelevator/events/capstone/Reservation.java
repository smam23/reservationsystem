package com.techelevator.events.capstone;

import java.time.LocalDate;

public class Reservation {
	private long reservationId;
	private long spaceId;
	private long numOfAttendees;
	private LocalDate startDate;
	private LocalDate endDate;
	private String reservedFor;
	private String spaceName;
	private long dailyRate;
	private long maxOccupancy;
	private boolean isAccessible;
	
	
	public long getReservationId() {
		return reservationId;
	}
	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}
	public long getSpaceId() {
		return spaceId;
	}
	public void setSpaceId(long spaceId) {
		this.spaceId = spaceId;
	}
	public long getNumOfAttendees() {
		return numOfAttendees;
	}
	public void setNumOfAttendees(long numOfAttendees) {
		this.numOfAttendees = numOfAttendees;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getReservedFor() {
		return reservedFor;
	}
	public void setReservedFor(String reservedFor) {
		this.reservedFor = reservedFor;
	}
	public String getSpaceName() {
		return spaceName;
	}
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	public long getDailyRate() {
		return dailyRate;
	}
	public void setDailyRate(long dailyRate) {
		this.dailyRate = dailyRate;
	}
	public long getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(long maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessible() {
		return isAccessible;
	}
	public void setAccessible(boolean isAccessible) {
		this.isAccessible = isAccessible;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (dailyRate ^ (dailyRate >>> 32));
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + (isAccessible ? 1231 : 1237);
		result = prime * result + (int) (maxOccupancy ^ (maxOccupancy >>> 32));
		result = prime * result + (int) (numOfAttendees ^ (numOfAttendees >>> 32));
		result = prime * result + (int) (reservationId ^ (reservationId >>> 32));
		result = prime * result + ((reservedFor == null) ? 0 : reservedFor.hashCode());
		result = prime * result + (int) (spaceId ^ (spaceId >>> 32));
		result = prime * result + ((spaceName == null) ? 0 : spaceName.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (dailyRate != other.dailyRate)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (isAccessible != other.isAccessible)
			return false;
		if (maxOccupancy != other.maxOccupancy)
			return false;
		if (numOfAttendees != other.numOfAttendees)
			return false;
		if (reservationId != other.reservationId)
			return false;
		if (reservedFor == null) {
			if (other.reservedFor != null)
				return false;
		} else if (!reservedFor.equals(other.reservedFor))
			return false;
		if (spaceId != other.spaceId)
			return false;
		if (spaceName == null) {
			if (other.spaceName != null)
				return false;
		} else if (!spaceName.equals(other.spaceName))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
	
	public String toString() {
		return this.spaceName;
	}
	
	
}
