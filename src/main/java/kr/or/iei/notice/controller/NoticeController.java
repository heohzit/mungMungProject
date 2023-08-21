package kr.or.iei.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeListData;


@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	//공지사항 리스트
	@GetMapping(value="list")
	public String noticeList (int reqPage , Model model) {
		NoticeListData nld = noticeService.selectNoticeList(reqPage);
		model.addAttribute("noticeList", nld.getNoticeList());
		model.addAttribute("pageNavi", nld.getPageNavi());
		return "notice/noticeList";
	}
		
	//공지사항 작성이동
	@GetMapping(value="writeFrm")
	public String noticeWriteFrm(){	
		return "notice/noticeWriteFrm";	
	}
	
	//공지사항 작성
	@PostMapping(value="write")
	public String noticeWrite(Notice n,Model model){	
		int result = noticeService.insertNotice(n);
		return "/notice/list";	
	}
	
	//공지사항 보기
	@GetMapping(value = "view")
	public String noticeView(int noticeNo, Model model) {
		Notice n = noticeService.selectOneNotice(noticeNo);
		if (n != null) {
			return "notice/noticeView";
		} else {
			return "notice/noticeList";
		}
	}
	
	//공지사항 삭제
	@GetMapping(value="delete")
	public String deleteNotice(int noticeNo) {
		int result = noticeService.deleteNotice(noticeNo);
		if (result != 0) {
			return "notice/noticeView";
		} else {
			return "notice/noticeList";
		}
	}
		
		//공지사항 수정
}
