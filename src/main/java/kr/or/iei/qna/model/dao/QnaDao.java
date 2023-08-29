package kr.or.iei.qna.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.qna.model.vo.Qna;
import kr.or.iei.qna.model.vo.QnaRowMapper;
@Repository
public class QnaDao {
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	QnaRowMapper qnaRowMapper;
	
	public int insertQna(Qna q) {
		// TODO Auto-generated method stub
		String query = "insert into QNA values (qna_seq.nextval,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'),default, null)";
		Object[] params = {q.getQnaCase(),q.getQnaWriter(), q.getQnaTitle(), q.getQnaContent()};
		int result = jdbc.update(query, params);
		return result;
	}

	public List selectQnaList(int start, int end) {
		// TODO Auto-generated method stub
		String query = "select * from (select rownum as rnum , n.* from (select * from qna join member on (member.member_no = qna.qna_writer) order by 1 desc)n)where rnum between ? and ?";
		List qnaList = jdbc.query(query, qnaRowMapper, start, end);
		return qnaList;
	}

	public int selectQnaTotalNum() {
		String query = "select count(*) from qna";
		int totalNum = jdbc.queryForObject(query, Integer.class);
		return totalNum;
	}

	public Qna selectOneQna(int qnaNo) {
		// TODO Auto-generated method stub
		String query = "select * from qna join member on (member.member_no = qna.qna_writer) where qna_no = ?";
		List list = jdbc.query(query, qnaRowMapper,qnaNo);
		return (Qna)list.get(0);
	}

	public int deleteQna(int qnaNo) {
		String query = "delete from qna where qna_no = ?";
		Object[] params = {qnaNo};
		int result = jdbc.update(query,params);
		return result;
	}

}
