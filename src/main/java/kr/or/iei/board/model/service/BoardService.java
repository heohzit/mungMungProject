package kr.or.iei.board.model.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.or.iei.board.model.dao.BoardDao;
import kr.or.iei.board.model.vo.Board;
import kr.or.iei.board.model.vo.BoardComment;
import kr.or.iei.board.model.vo.BoardListData;
import kr.or.iei.board.model.vo.BoardViewData;

@Service
public class BoardService {
    @Autowired
    private BoardDao boardDao;
    
  //공지사항 리스트 검색없을때
  	public BoardListData selectBoardList(int reqPage) {
  		int numPerPage = 8;
  		int end = reqPage * numPerPage;
  		int start = end-numPerPage+1;
  		List boardList = boardDao.selectBoardList(start, end);
  		//공지사항 총 게시글
  		int totalCount = boardDao.selectBoardTotalNum();
  		int totalPage = totalCount%numPerPage == 0 ? totalCount/numPerPage : totalCount/numPerPage+1;
  		
  		int pageNaviSize =5;
  		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
  		
  		String pageNavi ="<ul class='pagination'>";
  		if(pageNo !=1) {
  			pageNavi +="<li>";
  			pageNavi +="<a class='page-btn' href='/board/list?reqPage="+(pageNo-1)+"'>";
  			pageNavi +="<span class='material-icons'>arrow_back_ios_new</span>";
  			pageNavi +="</a>";
  			pageNavi +="</li>";
  		}
  		for(int i=0;i<pageNaviSize;i++) {
  			if(pageNo == reqPage) {
  				pageNavi +="<li>";
  				pageNavi +="<a class='page-btn select-page' href='/board/list?reqPage="+(pageNo)+"'>";
  				pageNavi += pageNo;
  				pageNavi += "</a>";
  				pageNavi += "</li>";
  			}else {
  				pageNavi +="<li>";
  				pageNavi +="<a class='page-btn' href='/board/list?reqPage="+(pageNo)+"'>";
  				pageNavi += pageNo;
  				pageNavi += "</a>";
  				pageNavi += "</li>";
  			}
  			pageNo++;
  			if(pageNo>totalPage) {
  				break;
  			}
  		}		
  			if(pageNo <= totalPage) {
  				pageNavi +="<li>";
  				pageNavi +="<a class='page-btn' href='/board/list?reqPage="+(pageNo)+"'>";
  				pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
  				pageNavi +="</a>";
  				pageNavi +="</li>";
  			}
  			pageNavi +="</ul>";
  			BoardListData bld = new BoardListData(boardList, pageNavi);
  			return bld;

  		}

  	//공지사항 리스트 검색있을때
	public BoardListData searchBoardList(int reqPage, String searchType, String searchName) {
  		int numPerPage = 5;
  		int end = reqPage * numPerPage;
  		int start = end-numPerPage+1;
  		
  		List boardList = null;
  	    if ("title".equals(searchType)) {
  	    	boardList = boardDao.searchBoardTitle(start, end, searchName);
  	    } else if ("writer".equals(searchType)) {
  	    	boardList = boardDao.searchBoardWriter(start, end, searchName);
  	    } else if ("content".equals(searchType)) {
  	    	boardList = boardDao.searchBoardContent(start, end, searchName);
  	    }
  		
  	    int totalCount=0;
	    if ("title".equals(searchType)) {
	    	totalCount = boardDao.searchBoardTitleTotalNum(searchName);
	    } else if ("writer".equals(searchType)) {
	    	totalCount = boardDao.searchBoardWriterTotalNum(searchName);
	    } else if ("content".equals(searchType)) {
	    	totalCount = boardDao.searchBoardContentTotalNum(searchName);
	    } 
  		
  		int totalPage = totalCount%numPerPage == 0 ? totalCount/numPerPage : totalCount/numPerPage+1;
  		
  		int pageNaviSize =5;
  		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
  		String pageNavi ="<ul class='pagination'>";
  		if(pageNo !=1) {
  			pageNavi +="<li>";
  			pageNavi +="<a class='page-btn' href='/board/searchBoardList?reqPage="+(pageNo-1)+"&searchType="+searchType+"&searchName="+searchName+"'>";;
  			pageNavi +="<span class='material-icons'>arrow_back_ios_new</span>";
  			pageNavi +="</a>";
  			pageNavi +="</li>";
  		}
  		for(int i=0;i<pageNaviSize;i++) {
  			if(pageNo == reqPage) {
  				pageNavi +="<li>";
  				pageNavi +="<a class='page-btn select-page' href='/board/searchBoardList?reqPage="+(pageNo)+"&searchType="+searchType+"&searchName="+searchName+"'>";
  				pageNavi += pageNo;
  				pageNavi += "</a>";
  				pageNavi += "</li>";
  			}else {
  				pageNavi +="<li>";
  				pageNavi +="<a class='page-btn' href='/board/searchBoardList?reqPage="+pageNo+"&searchType="+searchType+"&searchName="+searchName+"'>";
  				pageNavi += pageNo;
  				pageNavi += "</a>";
  				pageNavi += "</li>";
  			}
  			pageNo++;
  			if(pageNo>totalPage) {
  				break;
  			}
  		}		
  			if(pageNo <= totalPage) {
  				pageNavi +="<li>";
  				pageNavi +="<a class='page-btn' href='/board/searchBoardList?reqPage="+pageNo+"&searchType="+searchType+"&searchName="+searchName+"'>";
  				pageNavi +="<span class='material-icons'>arrow_forward_ios</span>";
  				pageNavi +="</a>";
  				pageNavi +="</li>";
  			}
  			pageNavi +="</ul>";
  			BoardListData bld = new BoardListData(boardList, pageNavi);
  			return bld;
  		}
    
    //커뮤니티 작성
	public int insertBoard(Board b) {
		int result = boardDao.insertBoard(b);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}
	
	//커뮤니티 상세보기
	public BoardViewData selectOneBoard(int boardNo) {
		//공지사항 조회수
		int count = boardDao.updateReadCount(boardNo);
		if(count>0) {
			//커뮤니티 상세보기
			Board b = boardDao.selectOneBoard(boardNo);
			//커뮤니티 댓글 조회
			List commentList = boardDao.selectCommentList(boardNo);
			//커뮤니티 대댓글 조회
			List reCommentList = boardDao.selectReCommentList(boardNo);
			BoardViewData bvd = new BoardViewData(b,commentList,reCommentList);
			return bvd;
		}else {
			return null;
		}
	}
	
	//커뮤니티 삭제
	public int deleteBoard(int boardNo) {
		int result = boardDao.deleteBoard(boardNo);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}

	//커뮤니티 해당번호 가져오기
	public Board getBoard(int boardNo) {
		Board b = boardDao.selectOneBoard(boardNo);
		return b;
	}
	
	//커뮤니티 수정
	public int updateBoard(Board b) {
		int result = boardDao.updateBoard(b);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}
	
	//커뮤니티 댓글쓰기
	public int insertComment(BoardComment bc) {
		int result = boardDao.insertComment(bc);
		return result;
	}
	
	//커뮤니티 댓글 수정
	public int updateCommnet(BoardComment bc) {
		int result = boardDao.updateComment(bc);
		return result;
	}
	
	//커뮤니티 댓글 삭제
	public int deleteComment(int boardCommentNo) {
		int result = boardDao.deleteComment(boardCommentNo);
		return result;
	}


}
