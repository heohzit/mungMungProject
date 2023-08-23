package kr.or.iei.notice.controller;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.iei.FileUtil;
import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeListData;


@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtil fileUtil;
	
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
	public String noticeWrite(Notice n){	
		int result = noticeService.insertNotice(n);
		if(result>0) {
			return "notice/noticeWriteFrm";				
		}else {
			return "notice/noticeWriteFrm";	
		}
	}
	
	//공지사항작성 summernote
	@ResponseBody
	@PostMapping (value="editor",produces ="plain/text;charset=utf-8")
	public String editorUpload(MultipartFile file) {
		String savepath = root+"notice/";
		String filepath = fileUtil.getFilepath(savepath, file.getOriginalFilename());
		File image = new File(savepath+filepath);
			try {
				file.transferTo(image);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "/editor/"+filepath;
	}
	//공지사항 보기
	@GetMapping(value = "view")
	public String noticeView(int noticeNo, Model model) {
		Notice n = noticeService.selectOneNotice(noticeNo);
		if (n != null) {
			model.addAttribute("n",n);
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
			return "notice/noticeList";
		} else {
			return "notice/noticeList";
		}
	}
		
	//공지사항 수정
	@GetMapping(value="updateFrm")
	public String updateFrm(int noticeNo , Model model) {
		Notice n = noticeService.getNotice(noticeNo);
		model.addAttribute("n", n);
		return "notice/updateFrm";
	}
	
	//공지사항 수정
	@PostMapping(value="update")
	public String update(Notice n) {
		int result = noticeService.updateNotice(n);
		if(result>0) {
			return "notice/noticeWriteFrm";				
		}else {
			return "notice/noticeWriteFrm";	
		}
	}
	

}
