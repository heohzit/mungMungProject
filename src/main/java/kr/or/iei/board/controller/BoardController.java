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
import org.springframework.web.multipart.MultipartFile;

import kr.or.iei.FileUtil;
import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.board.model.vo.Board;

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
    public String boardList(Model model) {
        int totalCount = boardService.totalCount();
        model.addAttribute("totalCount", totalCount);
        return "board/boardList";
    }
    
    //커뮤니티 작성 폼
    @GetMapping(value="writeFrm")
    public String writeFrm() {
        return "board/boardWriteFrm";
    }
    
    //커뮤니티 작성,썸네일 파일
  	@PostMapping(value="write")
  	public String boardWrite(Board b,MultipartFile imageFile){
  		String savepath = root+"photo/";
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
  			return "board/boardList";			
  		}else {
  			return "notice/noticeWriteFrm";	
  		}
  	}
  	
  	//커뮤니티 썸머노트
  	@ResponseBody
  	@PostMapping (value="editor",produces ="plain/text;charset=utf-8")
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
  	
	//커뮤니티 사진
	@ResponseBody
	@PostMapping(value="more")
	public List more(int start , int end) {
		List boardList = boardService.selectPhotoList(start,end);
		return boardList;
	}
	
	//커뮤니티 보기
	@GetMapping(value = "view")
	public String boardView(int boardNo, Model model) {
		Board b = boardService.selectOneNotice(boardNo);
		if (b != null) {
			model.addAttribute("b",b);
			return "board/boardView";
		} else {
			return "board/boardList";
		}
	}
	
	//커뮤니티 삭제
	
	//커뮤니티 수정
	
	

}
