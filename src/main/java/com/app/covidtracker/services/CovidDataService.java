package com.app.covidtracker.services;


import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.app.covidtracker.model.LocationStats;



@Service
public class CovidDataService {

	private static String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private List<LocationStats> allStats = new ArrayList<>();
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}
	
	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchVirusData() throws Exception {
		
		List<LocationStats> updatedStats = new ArrayList<>();
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(DATA_URL)).build();
		
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		StringReader reader = new StringReader(response.body());
		
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
		for (CSVRecord record : records) {
			LocationStats stats = new LocationStats();
			
			stats.setState(record.get("Province/State"));
			stats.setCountry(record.get("Country/Region"));
			int lastestCases = Integer.parseInt(record.get(record.size() - 1));
			int previousDayCases = Integer.parseInt(record.get(record.size() - 2));
			
			stats.setLatestTotalCases(lastestCases);
			stats.setDiffFromPreviousDayCases(lastestCases - previousDayCases);
			
			updatedStats.add(stats);
		}
		this.allStats = updatedStats;
	}

}
