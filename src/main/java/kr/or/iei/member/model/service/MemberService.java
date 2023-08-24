package kr.or.iei.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.member.model.dao.MemberDao;
import kr.or.iei.member.model.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	public Member selectOneMember(String signId, String signPw) {
		Member m = memberDao.selectOneMember(signId, signPw);
		return m;
	}
	@Transactional
	public int insertMember(Member member) {
		int result = memberDao.insertMember(member);
		return result;

	}
	public Member selectOneMember(String memberId) {
		Member m = memberDao.selectOneMember(memberId);
		return null;
	}
	@Transactional
	public int updateMember(Member member) {
		int result = memberDao.updateMember(member);
		return result;
	}
	@Transactional
	public int deleteMember(int memberNo) {
		int result = memberDao.deleteMember(memberNo);
		return result;
	}

}
