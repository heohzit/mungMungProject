package kr.or.iei.facility.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.or.iei.FileUtil;
import kr.or.iei.facility.model.service.FacilityService;
import kr.or.iei.facility.model.vo.Facility;
import kr.or.iei.facility.model.vo.FacilityFile;
import kr.or.iei.facility.model.vo.FacilityListData;
import kr.or.iei.member.model.vo.Member;

@Controller
@RequestMapping(value = "/facility")
public class FacilityController {
	@Autowired
	private FacilityService facilityService;
	@Autowired
	private FileUtil fileUtil;
	@Value("${file.root}")
	private String root;
	
	@GetMapping(value = "/tourDetail")
	public String tourDetail(int facilityNo, Model model, @SessionAttribute(required= false) Member m) {	
		int memberNo = (m==null? 0 : m.getMemberNo());
		Facility facility = facilityService.selectOneTour(facilityNo, memberNo);
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
	public String insertFacility(Facility f, Model model, MultipartFile[] facilityFile) {
		// 첨부파일을 저장할 ArrayList 선언
		ArrayList<FacilityFile> fileList = null;
		if(!facilityFile[0].isEmpty()) {
			// 첨부파일이 존재하는 경우만 fileList 객체 생성
			fileList = new ArrayList<FacilityFile>();
			// 첨부파일을 저장할 폴더 주소 지정
			String savepath = root + "facility/";
			
			for(MultipartFile file : facilityFile) {
				// 작성자가 업로드한 파일명
				String filename = file.getOriginalFilename();
				// 파일주소 생성
				String filepath = fileUtil.getFilepath(savepath, filename);
				// 지정한 폴더에 파일 업로드
				File uploadFile = new File(savepath + filepath);				
				try {
					file.transferTo(uploadFile);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
				// fileList에 첨부파일 저장
				FacilityFile ff = new FacilityFile();
				ff.setFacilityFilepath(filepath);
				fileList.add(ff);
			}		
		}
		
		// Facility와 fileList를 서비스로 보냄
		int result = facilityService.insertFacility(f, fileList);
		
		if((fileList == null && result == 1) || (fileList != null && result == (fileList.size() + 1))) {
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
	
	@ResponseBody
	@PostMapping(value = "addFavorite")
	public void addFavorite(int memberNo, int facilityNo) {
		facilityService.addFavorite(memberNo, facilityNo);
	}
	
	@ResponseBody
	@PostMapping(value = "removeFavorite")
	public void removeFavorite(int memberNo, int facilityNo) {
		facilityService.removeFavorite(memberNo, facilityNo);
	}
	
}
