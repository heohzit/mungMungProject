package kr.or.iei.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.board.model.vo.Board;
import kr.or.iei.board.model.vo.BoardComment;
import kr.or.iei.board.model.vo.BoardListData;
import kr.or.iei.board.model.vo.BoardViewData;
import kr.or.iei.member.model.vo.Member;

@Controller
@RequestMapping(value="/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Value("${file.root}")
	private String root;
	@Autowired
	private FileUtil fileUtil;
	
	//커뮤니티 리스트
	@GetMapping(value="/list")
	public String boardList (int reqPage , Model model,String searchType , String searchName) {
		BoardListData bld;
		if(searchType != null || searchName != null) {
			bld = boardService.selectBoardList(reqPage,searchType,searchName);
		}else {
			bld = boardService.selectBoardList(reqPage);
		}
		model.addAttribute("boardList", bld.getBoardList());
		model.addAttribute("pageNavi", bld.getPageNavi());
		return "board/boardList";
	}
    
    //커뮤니티 검색 리스트 화면
//  	@GetMapping(value="/searchList")
//  	public String boardList (int reqPage , Model model,String searchType , String searchName) {
//  		BoardListData bld = boardService.searchBoardList(reqPage,searchType,searchName);
//  		model.addAttribute("boardList", bld.getBoardList());
//  		model.addAttribute("pageNavi", bld.getPageNavi());
//  		return "board/boardSearchList";
//  	}
    
    //커뮤니티 작성 폼
    @GetMapping(value="/writeFrm")
    public String writeFrm() {
        return "board/boardWriteFrm";
    }
    
    //커뮤니티 작성,썸네일 파일
  	@PostMapping(value="/write")
  	public String boardWrite(Board b,MultipartFile imageFile){
  		String savepath = root+"boardmain/";
		String filepath = fileUtil.getFilepath(savepath, imageFile.getOriginalFilename());
		b.setBoardFilepath(filepath);
		File upFile = new File(savepath+filepath);
		try {
			imageFile.transferTo(upFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		int result = boardService.insertBoard(b);
  		if(result>0) {
  			return "redirect:/board/list";		
  		}else {
  			return "board/boardWriteFrm";	
  		}
  	}
  	
  	//커뮤니티 썸머노트
  	@ResponseBody
  	@PostMapping (value="/editor",produces ="plain/text;charset=utf-8")
  	public String editorUpload(MultipartFile file) {
  		String savepath = root+"board/";
  		String filepath = fileUtil.getFilepath(savepath, file.getOriginalFilename());
  		File image = new File(savepath+filepath);
  			try {
  				file.transferTo(image);
  			} catch (IllegalStateException | IOException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  		return "/board/"+filepath;
  	}
  	
//	//커뮤니티 더보기버튼 사진
//	@ResponseBody
//	@PostMapping(value="/more")
//	public List more(int start , int end) {
//		List boardList = boardService.selectBoardList(start,end);
//		return boardList;
//	}
	
	//커뮤니티 상세보기
	@GetMapping(value = "/view")
	public String boardView(int boardNo, Model model,@SessionAttribute(required=false) Member m) {
		BoardViewData bvd = boardService.selectOneBoard(boardNo);
		if (bvd != null) {
			model.addAttribute("b",bvd.getB());
			model.addAttribute("commentList",bvd.getCommentList());
			model.addAttribute("reCommentList",bvd.getReCommentList());
			return "board/boardView";
		} else {
			return "board/boardList";
		}
	}
	
	//커뮤니티 삭제
	@GetMapping(value="/delete")
	public String deleteBoard(int boardNo) {
		int result = boardService.deleteBoard(boardNo);
		if (result != 0) {
			return "board/boardList";
		} else {
			return "board/boardList";
		}
	}
	
	//커뮤니티 수정 폼
	@GetMapping(value="/updateFrm")
	public String updateFrm(int boardNo , Model model) {
		//커뮤니티 해당번호 가져오기
		Board b = boardService.getBoard(boardNo);
		model.addAttribute("b", b);
		return "board/boardUpdateFrm";
	}
	
	//커뮤니티 수정
	@PostMapping(value="/update")
	public String update(Board b) {
		int result = boardService.updateBoard(b);
		if(result>0) {
			return "board/boardUpdateFrm";	
		}else {
			return "board/boardUpdateFrm";
		}
	}
	
	//커뮤니티 댓글쓰기
	@PostMapping(value="/insertComment")
	public String insertComment(BoardComment bc, Model model) {
		int result = boardService.insertComment(bc);
		if(result>0) {
			return "board/boardList";
		}else {
			return "board/boardList";
		}
	}
	//커뮤니티 댓글 수정
	@PostMapping (value="updateCommnet")
	public String updateCommnet(BoardComment bc , Model model) {
		int result = boardService.updateCommnet(bc);
		if(result>0) {
			model.addAttribute("title", "수정완료");
			model.addAttribute("msg", "댓글이 수정되었습니다.");
			model.addAttribute("icon", "success");
		}else {
			model.addAttribute("title", "수정실패");
			model.addAttribute("msg", "댓글 수정에 실패했습니다.");
			model.addAttribute("icon", "error");
		}
		model.addAttribute("loc","/board/view?boardNo="+bc.getBoardNo());
		return "common/msg";
	}
	
		//커뮤니티 댓글 삭제
		@GetMapping (value="deleteComment")
		public String deleteComment(int boardCommentNo, int boardNo,Model model) {
			int result = boardService.deleteComment(boardCommentNo);
			if(result>0) {
				model.addAttribute("title", "삭제완료");
				model.addAttribute("msg", "댓글이 삭제되었습니다.");
				model.addAttribute("icon", "success");
			}else {
				model.addAttribute("title", "삭제실패");
				model.addAttribute("msg", "댓글 삭제가 실패했습니다.");
				model.addAttribute("icon", "error");
			}
			model.addAttribute("loc","/board/view?boardNo="+boardNo);
			return "common/msg";	
		}
}
