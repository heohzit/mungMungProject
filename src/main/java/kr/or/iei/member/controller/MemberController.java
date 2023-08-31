package kr.or.iei.member.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.iei.EmailSender;
import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.board.model.vo.BoardListData;
import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.FavoriteListData;
import kr.or.iei.member.model.vo.Member;
import kr.or.iei.member.model.vo.MemberListData;
import kr.or.iei.member.model.vo.ReservationListData;
import kr.or.iei.qna.model.vo.QnaListData;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private EmailSender emailSender;
	@Autowired
	private BoardService boardService;
	
	@PostMapping(value="/signin")
	public String signIn(String signId, String signPw, Model model, HttpSession session) {
		Member m = memberService.selectOneMember(signId,signPw);
		if(m != null) {
			session.setAttribute("m", m);
			model.addAttribute("title", "로그인 완료");
			model.addAttribute("msg", "로그인이 완료되었습니다.");		
			model.addAttribute("loc", "/");
		}else {
			model.addAttribute("title", "로그인 실패");
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요.");		
			model.addAttribute("loc", "/");
		}
		return "common/msg";
	
	}
	@GetMapping(value="/login")
	public String login() {
		return "member/login";
	}

	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
		}
	
	@PostMapping(value="/signup")
		public String signup(Member member, Model model){
		int result = memberService.insertMember(member);
		if(result>0) {
			model.addAttribute("title", "가입 완료");
			model.addAttribute("msg", "회원가입이 완료되었습니다.");;
			model.addAttribute("loc", "/");
		}else {
			model.addAttribute("title", "가입 실패");
			model.addAttribute("msg", "입력하신 정보를 다시 확인해주세요.");
			model.addAttribute("loc", "/");
		}
		return "common/msg";
	}
	@GetMapping(value="/signup")
	public String signup() {
		return "member/signup";
	}
	@ResponseBody
	@GetMapping(value="/ajaxCheckId")
	public String ajaxCheckId(String memberId) {
		Member m = memberService.selectOneMemberId(memberId);
		if(m == null) {
			return "0";
		}else {
			return "1";
		}
	}
	
	@ResponseBody
	@GetMapping(value="/ajaxCheckPw")
	public String ajaxCheckPw(String memberPw) {
		Member m = memberService.selectOneMemberPw(memberPw);
		if(m == null) {
			return "0";
		}else {
			return "1";
		}
	}
	
	@GetMapping(value="/mypage")
	public String mypage() {
		return "member/mypage";
	}
	@GetMapping(value="/myFavorite")
	public String myFavorite(int memberNo, int reqPage, Model model) {
		FavoriteListData fld = memberService.selectFavoriteList(memberNo, reqPage);
		model.addAttribute("favoriteList", fld.getFavoriteList());
		model.addAttribute("pageNavi", fld.getPageNavi());
		return "member/myFavorite";
	}
	@GetMapping(value="/myReservation")
	public String myReservation(int memberNo, int reqPage, Model model) {
		ReservationListData rld = memberService.selectOneMpp(memberNo, reqPage);
		model.addAttribute("mppList", rld.getFavoriteList());
		model.addAttribute("pageNavi", rld.getPageNavi());
		return "member/myReservation";
	}
	@GetMapping(value="/myBoard")
	public String myBoard(Model model,int reqPage,int memberNo) {
		BoardListData bld = boardService.selectMyBoard(memberNo,reqPage);
		model.addAttribute("boardList", bld.getBoardList());
		model.addAttribute("pageNavi", bld.getPageNavi());
		return "member/myBoard";
	}
	@GetMapping(value="/myQna")
	public String myQna(int reqPage, Model model, int memberNo) {
		QnaListData qld = memberService.myQna(reqPage, memberNo);
		model.addAttribute("qnaList", qld.getQnaList());
		model.addAttribute("pageNavi", qld.getPageNavi());
		return "member/myQna";
	}

	@PostMapping(value="/update")
	public String update(Member member, Model model, @SessionAttribute(required = false) Member m) {
		int result = memberService.updateMember(member);
		if(result>0) {
			m.setMemberPw(member.getMemberPw());
			m.setMemberPhone(member.getMemberPhone());
			m.setMemberName(member.getMemberName());
			
			model.addAttribute("title", "정보수정 완료");
			model.addAttribute("msg", "회원정보 수정이 완료되었습니다.");
			model.addAttribute("loc", "/member/mypage");	
		}else {
			model.addAttribute("title", "정보수정 실패");
			model.addAttribute("msg", "회원정보를 다시 입력해주세요.");
			model.addAttribute("loc", "/");
		}
		return "common/msg";
	}
	@GetMapping(value="/delete")
	public String delete(Model model, @SessionAttribute(required = false)Member m) {
		 int result = memberService.deleteMember(m.getMemberNo());
		 if(result>0) {
			model.addAttribute("title", "회원탈퇴 완료");
			model.addAttribute("msg", "회원탈퇴가 완료되었습니다.");
			model.addAttribute("loc", "/");
			return "common/msg";
		}else {
			model.addAttribute("title", "회원탈퇴 실패");
			model.addAttribute("msg", "마이페이지로 이동합니다.");
			model.addAttribute("loc", "/member/mypage");	
			return "common/msg";
		}
	}
	
	@GetMapping(value="/admin")
	public String admin(Model model, int reqPage) {
		MemberListData mld = memberService.selectAllMember(reqPage);
		model.addAttribute("memberList", mld.getMemberList());
		model.addAttribute("pageNavi", mld.getPageNavi());
		return "/member/admin";
	}
	
	@ResponseBody
	@GetMapping(value="/searchId")
	public String ajaxSearchId(String memberName, String memberEmail) {
		Member m = memberService.selectMemberByNameAndEmail(memberName, memberEmail);
		if(m != null) {
			return m.getMemberId();
		}else {
			return null;
		}
	}
	@ResponseBody
	@GetMapping(value="/searchPw")
	public String ajaxSearchPw(String memberId, String memberEmail) {
		Member m = memberService.selectMemberByIdAndEmail(memberId, memberEmail);
		if(m != null) {
			emailSender.pwMail(m.getMemberPw(), memberEmail);
			return "1";
		}else {
			return "0";
		}
	}
	
	@GetMapping(value = "/cancelManage")
	public String cancelManage(Model model) {
		List list = memberService.selectAllMpp();
		model.addAttribute("cancelList", list);
		return "/member/cancelManage";
	}
	
	@GetMapping(value = "/loginMsg")
	public String loginMsg(Model model) {
		model.addAttribute("title", "로그인 필요");
		model.addAttribute("msg", "로그인이 필요합니다.");
		model.addAttribute("loc", "/");
		return "common/msg";
	}
	
	@GetMapping(value = "/adminMsg")
	public String adminMsg(Model model) {
		model.addAttribute("title", "관리자 페이지");
		model.addAttribute("msg", "관리자 권한이 필요합니다.");
		model.addAttribute("loc", "/");
		return "common/msg";
	}
}
