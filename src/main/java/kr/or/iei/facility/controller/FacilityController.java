package kr.or.iei.facility.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.iei.facility.model.service.FacilityService;
import kr.or.iei.facility.model.vo.Facility;
import kr.or.iei.facility.model.vo.FacilityListData;

@Controller
@RequestMapping(value = "/facility")
public class FacilityController {
	@Autowired
	public FacilityService facilityService;
	
	@GetMapping(value = "/tourDetail")
	public String tourDetail(int facilityNo, Model model) {
		Facility facility = facilityService.selectOneTour(facilityNo);
		model.addAttribute("f",facility);
		return "facility/tourDetail";
	}
	
	@GetMapping(value="/tourList")
	public String tourList(Model model, int reqPage) {
		FacilityListData fld = facilityService.selectTourList(reqPage);
		model.addAttribute("facilityList", fld.getFacilityList());
		model.addAttribute("pageNavi", fld.getPageNavi());
		return "facility/tourList";
	}
	
	@GetMapping(value="/insertFrm")
	public String tourInsert() {
		return "facility/insertFrm";
	}
	
}
