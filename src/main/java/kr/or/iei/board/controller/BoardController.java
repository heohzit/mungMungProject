package kr.or.iei.board.controller;

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
import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.board.model.vo.Board;
import kr.or.iei.board.model.vo.BoardListData;

@Controller
@RequestMapping(value="/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtil fileUtil;
    
    @GetMapping(value="/list")
    public String boardList(Model model) {
        int totalCount = boardService.totalCount();
        model.addAttribute("totalCount", totalCount);
        return "board/boardList";
    }
    
    @GetMapping(value="writeFrm")
    public String writeFrm() {
        return "board/boardWriteFrm";
    }
  	@PostMapping(value="write")
  	public String boardWrite(Board b){	
  		int result = boardService.insertBoard(b);
  		if(result>0) {
  			return "notice/noticeWriteFrm";				
  		}else {
  			return "notice/noticeWriteFrm";	
  		}
  	}
  	
  	@ResponseBody
  	@PostMapping (value="editor",produces ="plain/text;charset=utf-8")
  	public String editorUpload(MultipartFile file) {
  		String savepath = root+"editor/";
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

}
