package kr.or.iei.notice.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class NoticeSearchRowMapper implements RowMapper<Notice> {

	@Override
	public Notice mapRow(ResultSet rs, int rowNum) throws SQLException {
		Notice n = new Notice();
		n.setNoticeNo(rs.getInt("notice_no"));
		n.setNoticeWriter(rs.getInt("notice_writer"));
		n.setNoticeTitle(rs.getString("notice_title"));
		n.setNoticeContent(rs.getString("notice_content"));
		n.setNoticeWriteDate(rs.getString("notice_write_date"));
		n.setNoticeReadCount(rs.getInt("notice_read_count"));
		return n;
	}

}
