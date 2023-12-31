package kr.or.iei.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.member.model.vo.Member;
import kr.or.iei.member.model.vo.MemberFacilityFavoriteRowMapper;
import kr.or.iei.member.model.vo.MemberProductPayRowMapper;
import kr.or.iei.member.model.vo.MemberRowMapper;
import kr.or.iei.qna.model.vo.QnaRowMapper;

@Repository
public class MemberDao {

	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MemberRowMapper memberRowMapper;
	@Autowired
	private MemberProductPayRowMapper memberProductPayRowMapper;
	@Autowired
	private MemberFacilityFavoriteRowMapper memberFacilityFavoriteRowMapper;
	@Autowired
	private QnaRowMapper qnaRowMapper;
	
	public Member selectOneMember(String signId, String signPw) {
		String query = "select * from member where member_id = ? and member_pw = ?";
		List list = jdbc.query(query, memberRowMapper, signId, signPw);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}

	public int insertMember(Member member) {
		System.out.println(member);
		String query = "insert into member values(member_seq.nextval,?,?,?,?,2,to_char(sysdate,'yyyy-mm-dd'),?)";
		Object [] params = {member.getMemberId(),member.getMemberPw(),member.getMemberPhone(),member.getMemberEmail(),member.getMemberName()};
			return jdbc.update(query,params);
		} 
	
	
	public Member selectOneMemberId(String memberId) {
		String query = "select * from member where member_id=?";
		List list = jdbc.query(query, memberRowMapper,memberId);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}
	
	public Member selectOneMemberPw(String memberPw) {
		String query = "select * from member where member_pw=?";
		List list = jdbc.query(query, memberRowMapper,memberPw);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}
	
	public int updateMember(Member member) {
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

	public List selectAllMember(int startNum, int endNum) {
		String query = "select * from (select rownum as rnum, n.* from (select * from member order by 1) n) where rnum between ? and ?";
		List list = jdbc.query(query, memberRowMapper, startNum, endNum);
		return list;
	}

	public int selectAllMembertTotalCount() {
		String query = "select count(*) from member";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public Member selectMemberByNameAndEmail(String memberName, String memberEmail) {
		String query = "select * from member where member_name=? and member_email=?";
		List list = jdbc.query(query, memberRowMapper,memberName, memberEmail);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}

	public Member selectMemberByIdAndEmail(String memberId, String memberEmail) {
		String query = "select * from member where member_id=? and member_email=?";
		List list = jdbc.query(query, memberRowMapper,memberId, memberEmail);
		if(list.isEmpty()) {
			return null;
		}
		return (Member)list.get(0);
	}

	public List selectOneMpp(int memberNo, int startNum, int endNum) {
		String query = "select * from (select rownum as rnum, n.* from (select * from member join pay on (member_no = pay_member_no) join product on (pay_product_no = product_no) where member_no = ? order by pay_no desc) n) where rnum between ? and ?";
		List list = jdbc.query(query, memberProductPayRowMapper, memberNo, startNum, endNum);
		return list;
	}


	public List selectAllMpp() {
		String query = "select * from member join pay on (member_no = pay_member_no) join product on (pay_product_no = product_no) where pay_status = 2";
		List list = jdbc.query(query, memberProductPayRowMapper);
		return list;

	}

	public List selectFavoriteList(int memberNo, int startNum, int endNum) {
		String query = "select * from (select rownum as rnum, n.* from (select * from member join favorite on (member_no = favorite_member_no) join facility on (favorite_facility_no = facility_no) where member_no = ?) n) where rnum between ? and ?";
		List list = jdbc.query(query, memberFacilityFavoriteRowMapper, memberNo, startNum, endNum);
		return list;
	}

	public List myQna(int start, int end, int memberNo) {
		// TODO Auto-generated method stub
		String query = "select * from (select rownum as rnum , n.* from (select * from qna join member on (member.member_no = qna.qna_writer) order by 1 desc)n)where (rnum between ? and ?) and qna_writer = ?";
		List qnaList = jdbc.query(query, qnaRowMapper, start, end, memberNo);
		return qnaList;
	}

	public int selectQnaTotalNum(int memberNo) {
		String query = "select count(*) from qna where qna_writer = ?";
		int totalNum = jdbc.queryForObject(query, Integer.class, memberNo);
		return totalNum;
	}

	public int selectOneMppTotalCount(int memberNo) {
		String query = "select count(*) from pay where pay_member_no = ?";
		int totalCount = jdbc.queryForObject(query, Integer.class, memberNo);
		return totalCount;
	}
}	


