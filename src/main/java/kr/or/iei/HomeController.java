package kr.or.iei;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.board.model.vo.BoardListData;
import kr.or.iei.facility.model.service.FacilityService;
import kr.or.iei.facility.model.vo.FacilityListData;
import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.NoticeListData;
import kr.or.iei.product.model.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	private ProductService productService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private FacilityService facilityService;
	@Autowired
	private BoardService boardService;
	
	@GetMapping(value="/")
	public String main(Model model) { 
//	공지사항 최신 가져오기
		List noticeList = noticeService.selectNoticeList();
		model.addAttribute("noticeList", noticeList);
		
//	패키지최신 가져오기
		List productList = productService.selectProductList();
		model.addAttribute("productList", productList);
// 숙소 가져오기
		List hotelList = facilityService.selectHotelList();
		model.addAttribute("hotelList", hotelList);
// 관광지 가져오기 
		List tourList = facilityService.selectTourList();
		model.addAttribute("tourList", tourList);
// 식음료 가져오기 
		List cafeList = facilityService.selectCafeList();
		model.addAttribute("cafeList", cafeList);
// 커뮤니티 가져오기 
		List boardList = boardService.selectBoardList();
		model.addAttribute("boardList", boardList);
		return "index";
	}
	
	@GetMapping(value="/mung_info")
	public String mungInfo() {
		return "common/mung_info";
	}
	@GetMapping(value="/ref")
	public String ref() {
		return "ref";
	}
}
