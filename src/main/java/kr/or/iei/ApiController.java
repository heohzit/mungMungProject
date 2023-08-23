package kr.or.iei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api")
public class ApiController {
	
	@Autowired
	private EmailSender emailSender;
	
	@ResponseBody
	@PostMapping(value="/auth")
	public String authMail(String email) {
		String authCode = emailSender.authMail(email);
		return authCode;
	}
}

