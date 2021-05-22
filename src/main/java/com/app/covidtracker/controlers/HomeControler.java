package com.app.covidtracker.controlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.covidtracker.model.LocationStats;
import com.app.covidtracker.services.CovidDataService;

@Controller
public class HomeControler {
	
	@Autowired
	CovidDataService covidDataService;
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<LocationStats> allStats = covidDataService.getAllStats();
		
		int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int newReportedCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPreviousDayCases()).sum();
		
		model.addAttribute("newReportedCases", newReportedCases);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("locationStats", allStats);
		return "home";
	}

}
