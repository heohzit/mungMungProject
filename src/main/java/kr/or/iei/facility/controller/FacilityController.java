package kr.or.iei.facility.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/facility")
public class FacilityController {
	@GetMapping(value = "/tourDetail")
	public String tourDetail() {
		return "facility/tourDetail";
	}
	
	@GetMapping(value="/tourList")
	public String tourList() {
		return "facility/tourList";
	}
	
	@GetMapping(value="/insertFrm")
	public String tourInsert() {
		return "facility/insertFrm";
	}
}
