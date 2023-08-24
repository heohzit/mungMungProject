package kr.or.iei.board.model.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.or.iei.board.model.dao.BoardDao;
import kr.or.iei.board.model.vo.Board;
import kr.or.iei.board.model.vo.BoardComment;
import kr.or.iei.board.model.vo.BoardListData;
import kr.or.iei.board.model.vo.BoardViewData;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeListData;

@Service
public class BoardService {
    @Autowired
    private BoardDao boardDao;
    
    //커뮤니티 전체 게시글 수
    public int totalCount() {
    	int totalCount = boardDao.totalCount();
    	return 0;
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
	
	//커뮤니티 사진보기
	public List selectPhotoList(int start, int end) {
		List boardList = boardDao.selectPhotoList(start,end);
		return boardList;
	}
	
	//커뮤니티 상세보기
	public BoardViewData selectOneNotice(int boardNo) {
		//공지사항 조회수
		int count = boardDao.updateReadCount(boardNo);
		if(count>0) {
			//커뮤니티 상세보기
			Board b = boardDao.selectOneNotice(boardNo);
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
		int result = boardDao.deleteNotice(boardNo);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}

	//커뮤니티 해당번호 가져오기
	public Board getBoard(int boardNo) {
		Board b = boardDao.selectOneNotice(boardNo);
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
	
	//커뮤니티 검색 리스트 화면
	public BoardListData searchBoardList(int reqPage, String searchType, String searchName) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end-numPerPage+1;
		List boardList;
	    if ("title".equals(searchType)) {
	    	boardList = boardDao.searchBoardTitle(start, end, searchName);
	    } else if ("writer".equals(searchType)) {
	    	boardList = boardDao.searchBoardWriter(start, end, searchName);
	    } else if ("content".equals(searchType)) {
	    	boardList = boardDao.searchBoardContent(start, end, searchName);
	    } else {
	    	boardList = boardDao.selectBoardList(start, end);
	    }
		
		
		//커뮤니티 총 게시글
		int totalCount = boardDao.selectNoticeTotalNum();
		int totalPage = totalCount%numPerPage == 0 ? totalCount/numPerPage : totalCount/numPerPage+1;
		
		int pageNaviSize =5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		String pageNavi ="<ul>";
		pageNavi ="<ul>";
		if(pageNo !=1) {
			pageNavi +="<li>";
			pageNavi +="<a href='/notice/list?reqPage="+(pageNo-1)+"'>";
			pageNavi +="<span class='material-icons-outlined'>navigate_before</span>";
			pageNavi +="</a>";
			pageNavi +="</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			if(pageNo == reqPage) {
				pageNavi +="<li>";
				pageNavi +="<a href='/notice/list?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi +="<li>";
				pageNavi +="<a href='/board/searchList?reqPage="+(pageNo)+"'>";
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
				pageNavi +="<a href='/board/searchList?reqPage="+(pageNo)+"'>";
				pageNavi +="<span class='material-icons-outlined'>navigate_next</span>";
				pageNavi +="</a>";
				pageNavi +="</li>";
			}
			pageNavi +="</ul>";
			BoardListData bld = new BoardListData(boardList, pageNavi);
			return bld;
		}
	}
