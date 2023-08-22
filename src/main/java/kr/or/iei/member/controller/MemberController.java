package kr.or.iei.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}