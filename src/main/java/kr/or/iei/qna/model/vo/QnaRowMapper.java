package kr.or.iei.qna.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class QnaRowMapper implements RowMapper<Qna>{

	@Override
	public Qna mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Qna q = new Qna();
		q.setQnaAnswerCase(rs.getInt("qna_answer_case"));
		q.setQnaCase(rs.getInt("qna_case"));
		q.setQnaContent(rs.getString("qna_content"));
		q.setQnaNo(rs.getInt("qna_no"));
		q.setQnaTitle(rs.getString("qna_title"));
		q.setQnaWriteDate(rs.getString("qna_write_date"));
		q.setQnaWriter(rs.getInt("qna_writer"));
		q.setQnaAnswer(rs.getNString("qna_answer"));
		
		q.setMemberId(rs.getString("member_id"));
		return q;
	}

}
