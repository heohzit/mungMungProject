package kr.or.iei.board.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardCommentRowMapper implements RowMapper<BoardComment> {

	@Override
	public BoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardComment bc = new BoardComment();
		bc.setBoardCommentNo(rs.getInt("board_comment_no"));
		bc.setBoardCommentWriter(rs.getInt("board_comment_writer"));
		bc.setBoardCommentContent(rs.getString("board_comment_content"));
		bc.setBoardCommentWriteDate(rs.getString("board_comment_write_date"));
		bc.setBoardNo(rs.getInt("board_no"));
		bc.setBoardCommentRefNo(rs.getInt("board_comment_ref_no"));
		return bc;
	}

}
