package kr.or.iei.board.model.dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.board.model.vo.Board;
import kr.or.iei.board.model.vo.BoardRowMapper;
@Repository
public class BoardDao {
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private BoardRowMapper boardRowMapper;
    
    public int totalCount() {
    	String query = "select count(*) from board";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
    }
    
	public int insertBoard(Board b) {
		String query = "insert into board values (board_seq.nextval,?,?,?,to_char(sysdate,'yyyy-mm-dd'),0)";
		Object[] params = {b.getMemberId(),b.getBoardTitle(),b.getBoardContent()};
		int result = jdbc.update(query,params);
		return result;
	}
}
