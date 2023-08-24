package kr.or.iei.facility.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		
	@GetMapping(value="/insertFrm")
	public String tourInsert() {
		return "facility/insertFrm";
	}
	
	@GetMapping(value="/tourList")
	public String tourList(Model model, int reqPage) {
		FacilityListData fld = facilityService.selectTourList(reqPage);
		model.addAttribute("facilityList", fld.getFacilityList());
		model.addAttribute("pageNavi", fld.getPageNavi());
		return "facility/tourList";
	}
	@GetMapping(value="/searchTourList")
	public String searchTourList(Model model, int reqPage, String searchName) {
		FacilityListData fld = facilityService.selectSearchTourList(reqPage, searchName);
		model.addAttribute("facilityList", fld.getFacilityList());
		model.addAttribute("pageNavi", fld.getPageNavi());
		return "facility/tourList";
	}	
	@GetMapping(value="/hotelList")
	public String hotelList(Model model, int reqPage) {
		FacilityListData fld = facilityService.selectHotelList(reqPage);
		model.addAttribute("facilityList", fld.getFacilityList());
		model.addAttribute("pageNavi", fld.getPageNavi());
		return "facility/hotelList";
	}	
	@GetMapping(value="/searchHotelList")
	public String searchHotelList(Model model, int reqPage, String searchName) {
		FacilityListData fld = facilityService.selectSearchHotelList(reqPage, searchName);
		model.addAttribute("facilityList", fld.getFacilityList());
		model.addAttribute("pageNavi", fld.getPageNavi());
		return "facility/hotelList";
	}
	@GetMapping(value="/cafeList")
	public String cafeList(Model model, int reqPage) {
		FacilityListData fld = facilityService.selectCafeList(reqPage);
		model.addAttribute("facilityList", fld.getFacilityList());
		model.addAttribute("pageNavi", fld.getPageNavi());
		return "facility/cafeList";
	}	
	@GetMapping(value="/searchCafeList")
	public String searchCafeList(Model model, int reqPage, String searchName) {
		FacilityListData fld = facilityService.selectSearchCafeList(reqPage, searchName);
		model.addAttribute("facilityList", fld.getFacilityList());
		model.addAttribute("pageNavi", fld.getPageNavi());
		return "facility/cafeList";
	}
	@GetMapping(value="/activityList")
	public String activityList(Model model, int reqPage) {
		FacilityListData fld = facilityService.selectActivityList(reqPage);
		model.addAttribute("facilityList", fld.getFacilityList());
		model.addAttribute("pageNavi", fld.getPageNavi());
		return "facility/activityList";
	}	
	@GetMapping(value="/searchActivityList")
	public String searchActivityList(Model model, int reqPage, String searchName) {
		FacilityListData fld = facilityService.selectSearchActivityList(reqPage, searchName);
		model.addAttribute("facilityList", fld.getFacilityList());
		model.addAttribute("pageNavi", fld.getPageNavi());
		return "facility/activityList";
	}
	

	@PostMapping(value="/insertFacility")
	public String insertFacility(Facility f, Model model) {
		int result = facilityService.insertFacility(f);
		if(result > 0) {
			model.addAttribute("title", "시설 등록 완료");
			model.addAttribute("msg", "시설 등록이 완료되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/");
		}else {
			model.addAttribute("title", "시설 등록 실패");
			model.addAttribute("msg", "시스템 관리자에게 문의하세요.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/facility/insertFrm");
		}
		return "common/msg";
	}
}
