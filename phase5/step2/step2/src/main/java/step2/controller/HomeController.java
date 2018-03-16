package step2.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

public class HomeController {
	//
	@RequestMapping("/")
	public String initpage(HttpServletRequest req) {
		//
		return "redirect:/home";
	}
}
