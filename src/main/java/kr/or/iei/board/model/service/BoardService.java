package kr.or.iei.board.model.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.or.iei.board.model.dao.BoardDao;
import kr.or.iei.board.model.vo.Board;
import kr.or.iei.board.model.vo.BoardComment;
import kr.or.iei.board.model.vo.BoardViewData;
import kr.or.iei.notice.model.vo.Notice;

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

}