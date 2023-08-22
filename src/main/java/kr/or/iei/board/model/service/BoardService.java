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

    public int totalCount() {
    	int totalCount = boardDao.totalCount();
    	return 0;
    }
    
	public int insertBoard(Board b) {
		int result = boardDao.insertBoard(b);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}

}