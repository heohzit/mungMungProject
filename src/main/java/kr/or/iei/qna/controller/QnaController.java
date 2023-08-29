package kr.or.iei.qna.controller;

import java.io.File;
import java.io.IOException;

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
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.qna.model.service.QnaService;
import kr.or.iei.qna.model.vo.Qna;
import kr.or.iei.qna.model.vo.QnaListData;

@Controller
@RequestMapping(value = "/qna")
public class QnaController {
	@Autowired
	QnaService qnaService;
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtil fileUtil;
	
		@GetMapping(value="/list")
		public String noticeList (int reqPage , Model model) {
			QnaListData qld = qnaService.selectQnaList(reqPage);
			model.addAttribute("qnaList",qld.getQnaList());
			model.addAttribute("pageNavi",qld.getPageNavi());
			return "qna/qnaList";
		}
		@GetMapping(value="writeFrm")
		public String noticeWriteFrm(){	
			return "qna/qnaWriteFrm";	
		}	
		
		@PostMapping(value="write")
		public String noticeWrite(Qna q,Model model){	
			int result = qnaService.insertQna(q);
			if(result>0) {
				model.addAttribute("title", "Q&A 작성 성공");
				model.addAttribute("msg", "Q&A가 작성 되었습니다.");
				model.addAttribute("icon", "success");			
			}else {
				model.addAttribute("title", "Q&A 작성 실패");
				model.addAttribute("msg", "내용을 확인해주세요.");
				model.addAttribute("icon", "error");
			}
			model.addAttribute("loc", "/qna/list?reqPage=1");
			return "common/msg";
		}
		
		@GetMapping(value = "view")
		public String qnaView(int qnaNo, Model model) {
			Qna q = qnaService.selectOneQna(qnaNo);
			if (q != null) {
				model.addAttribute("q",q);
				return "qna/qnaView";
			} else {
				model.addAttribute("title", "조회 실패");
				model.addAttribute("msg", "이미 삭제된 게시물 입니다.");
				model.addAttribute("icon", "info");
				model.addAttribute("loc", "/qna/list?reqPage=1");
				return "common/msg";
			}
		}
		@ResponseBody
		@PostMapping (value="editor",produces ="plain/text;charset=utf-8")
		public String editorUpload(MultipartFile file) {
			String savepath = root+"qna/";
			String filepath = fileUtil.getFilepath(savepath, file.getOriginalFilename());
			File image = new File(savepath+filepath);
				try {
					file.transferTo(image);
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return "/qna/"+filepath;
		}
		
		//공지사항 삭제
		@GetMapping(value="delete")
		public String deleteQna(int qnaNo , Model model) {
			int result = qnaService.deleteQna(qnaNo);
			if (result != 0) {
				model.addAttribute("title", "삭제완료");
				model.addAttribute("msg", "게시글이 삭제되었습니다.");
				model.addAttribute("icon", "success");
				model.addAttribute("loc", "/qna/list?reqPage=1");
			} else {
				model.addAttribute("title", "삭제실패");
				model.addAttribute("msg", "관리자에게 문의하세요");
				model.addAttribute("icon", "error");
				model.addAttribute("loc", "/qna/view?qnaNo="+qnaNo);		
			}
			return "common/msg";
		}
		@GetMapping(value="updateFrm")
		public String updateFrm(int qnaNo, Model model) {
			Qna q = qnaService.getNotice(qnaNo);
			model.addAttribute("q", q);
			return "qna/qnaUpdateFrm";
		}	
		
		@PostMapping(value="update")
		public String update(Qna q,Model model) {
			int result = qnaService.updateQna(q);
			if(result>0) {
				model.addAttribute("title", "수정완료");
				model.addAttribute("msg", "게시글이 수정되었습니다");
				model.addAttribute("icon", "success");					
			}else {
				model.addAttribute("title", "수정실패");
				model.addAttribute("msg", "관리자에게 문의하세요");
				model.addAttribute("icon", "error");	
			}
			model.addAttribute("loc","/qna/view?qnaNo="+q.getQnaNo());
			return "common/msg";
		}
	
}
