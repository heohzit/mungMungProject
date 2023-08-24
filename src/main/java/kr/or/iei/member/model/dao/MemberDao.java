package kr.or.iei.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.member.model.vo.Member;
import kr.or.iei.member.model.vo.MemberRowMapper;

@Repository
public class MemberDao {

	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MemberRowMapper memberRowMapper;
	
	public Member selectOneMember(String signId, String signPw) {
		String query = "select * from member where member_id = ? and member_pw = ?";
		List list = jdbc.query(query, memberRowMapper, signId, signPw);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}

	public int insertMember(Member member) {
		String query = "insert into member values(member_seq.nextval,?,?,?,?,2,to_char(sysdate,'yyyy-mm-dd'),?)";
		Object [] params = {member.getMemberId(),member.getMemberPw(),member.getMemberPhone(),member.getMemberEmail(),member.getMemberName()};
		int result = jdbc.update(query,params);
		return result;

	}

	public Member selectOneMember(String checkId) {
		String query = "select * from member where member_id=?";
		List list = jdbc.query(query, memberRowMapper,checkId);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}

	public int updateMember(Member member) {
		//query,Object = 바꿀 내용 순서대로 작성
		String query = "update member set member_pw=?, member_phone=?, member_name=? where member_id=?";
		Object [] params = {member.getMemberPw(),member.getMemberPhone(),member.getMemberName(),member.getMemberId()};
		int result = jdbc.update(query,params);
		return result;
	}

	public int deleteMember(int memberNo) {
		String query = "delete from member where member_no=?";
		Object[] params = {memberNo};
		int result = jdbc.update(query,params);
		return result;
	}
}
