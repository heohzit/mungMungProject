package kr.or.iei.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.member.model.dao.MemberDao;
import kr.or.iei.member.model.vo.Member;
import kr.or.iei.member.model.vo.MemberListData;

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
	public Member selectOneMemberId(String memberId) {
		Member m = memberDao.selectOneMemberId(memberId);
		return m;
	}
	public Member selectOneMemberPw(String memberPw) {
		Member m = memberDao.selectOneMemberPw(memberPw);
		return m;
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
	public MemberListData selectAllMember(int reqPage) {
		// 1. 한 페이지당 게시물 수 설정
		int numPerPage = 15;
		// 게시물을 DB에서 조회 후 가져옴
		int endNum = reqPage * numPerPage;
		int startNum = endNum - numPerPage + 1;
		List memberList = memberDao.selectAllMember(startNum, endNum);
		
		// 2. 페이지 내비게이션 생성
		// 총 게시물 수 DB에서 조회해서 가져옴
		int totalList = memberDao.selectAllMembertTotalCount();
		// 총 페이지 수 계산
		int totalPage = totalList / numPerPage;
		// 총 게시물 수를 한 페이지당 게시물 수로 나눈 나머지 값이 0이 아니면 총 페이지 수에 1을 더함
		if (totalList % numPerPage != 0) {
			totalPage += 1;
		}
		// 페이지 내비게이션 사이즈 설정
		int pageNaviSize = 5;
		// 페이지 내비게이션 시작번호 설정
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

		// 페이지 내비게이션 html 제작
		String pageNavi = "<ul class='pagination'>";
		// 페이지 내비게이션 번호가 1이 아닌 경우에만 이전버튼 활성화
		if (pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-btn' href='/member/admin?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>arrow_back_ios_new</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 내비게이션 숫자버튼 제작
		// 요청페이지와 페이지 내비게이션 번호가 같은 경우만 css활성화
		// 페이지 내비게이션 번호가 총 페이지 수보다 크면 반복문 종료
		for (int i = 0; i < pageNaviSize; i++) {
			if (pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-btn select-page' href='/member/admin?reqPage=" + pageNo + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			} else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-btn' href='/member/admin?reqPage=" + pageNo + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if (pageNo > totalPage) {
				break;
			}
		}

		// 페이지 내비게이션 번호가 총 페이지 수보다 크지 않은 경우만 다음버튼 활성화
		if (pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-btn' href='/member/admin?reqPage=" + pageNo + "'>";
			pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}

		pageNavi += "</ul>";
		
		MemberListData mld = new MemberListData(memberList, pageNavi);
		
		return mld;
	}
	public Member selectMemberByNameAndEmail(String memberName, String memberEmail) {
		Member m = memberDao.selectMemberByNameAndEmail(memberName, memberEmail);
		return m;
	}
	public Member selectMemberByIdAndEmail(String memberId, String memberEmail) {
		Member m = memberDao.selectMemberByIdAndEmail(memberId, memberEmail);
		return m;
	}
	
}
