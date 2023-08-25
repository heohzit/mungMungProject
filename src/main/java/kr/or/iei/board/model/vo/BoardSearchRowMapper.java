package kr.or.iei.board.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardSearchRowMapper implements RowMapper<Board> {

	@Override
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board b = new Board();
		b.setBoardNo(rs.getInt("board_no"));
		b.setBoardWriter(rs.getInt("board_writer"));
		b.setBoardTitle(rs.getString("board_title"));
		b.setBoardContent(rs.getString("board_content"));
		b.setBoardWriteDate(rs.getString("board_write_date"));
		b.setBoardReadCount(rs.getInt("board_read_count"));
		b.setBoardFilepath(rs.getString("board_filepath"));
		return b;
	}

}
