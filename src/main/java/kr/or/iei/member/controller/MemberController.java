package kr.or.iei.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@PostMapping(value="/signin")
	public String signIn(String signId, String signPw, Model model, HttpSession session) {
		Member m = memberService.selectOneMember(signId,signPw);
		if(m != null) {
			session.setAttribute("m", m);
			model.addAttribute("title", "로그인 완료");
			model.addAttribute("msg", "로그인이 완료되었습니다.");		
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/");
		}else {
			model.addAttribute("title", "로그인 실패");
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요.");		
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/");
		}
		return "common/msg";
	
	}
	@GetMapping(value="/login")
	public String login() {
		return "member/login";
	}

	@PostMapping(value="/signup")
		public String signup(Member member, Model model){
		int result = memberService.insertMember(member);
		if(result>0) {
			model.addAttribute("title", "가입 완료");
			model.addAttribute("msg", "회원가입이 완료되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/");
		}else {
			model.addAttribute("title", "가입 실패");
			model.addAttribute("msg", "입력하신 정보를 다시 확인해주세요.");
			model.addAttribute("icon", "error");
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
		Member m = memberService.selectOneMember(memberId);
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
}
