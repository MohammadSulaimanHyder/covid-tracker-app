package com.app.covidtracker.model;


public class LocationStats {
	
	private String state;
	private String country;
	private int latestTotalCases;
	private int diffFromPreviousDayCases;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	public int getDiffFromPreviousDayCases() {
		return diffFromPreviousDayCases;
	}
	public void setDiffFromPreviousDayCases(int diffFromPreviousDayCases) {
		this.diffFromPreviousDayCases = diffFromPreviousDayCases;
	}
	
	@Override
	public String toString() {
		return "{\"LocationStat\":{\"state\":\"" + state + "\", \"country\":\"" + country + "\", \"latestTotalCases\":\"" + latestTotalCases
				+ "\"}}";
	}
}
