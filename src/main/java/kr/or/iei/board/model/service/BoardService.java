package kr.or.iei.board.model.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.or.iei.board.model.dao.BoardDao;
import kr.or.iei.board.model.vo.Board;
import kr.or.iei.board.model.vo.BoardListData;

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
	
	//커뮤니티 보기
	public Board selectOneNotice(int boardNo) {
		//공지사항 조회수
		int count = boardDao.updateReadCount(boardNo);
		if(count>0) {
			Board b = boardDao.selectOneNotice(boardNo);	
			return b;
		}else {
			return null;
		}
	}

}