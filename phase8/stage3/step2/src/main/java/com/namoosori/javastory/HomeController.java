package com.namoosori.javastory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
	//
	@GetMapping(value = "/")
	public String home(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {

		try {
			if (session.getAttribute("memberEmail") != null) {
				req.getRequestDispatcher("club/clublists").forward(req, resp);
			}
		} catch (ServletException | IOException e) {
			//
			e.printStackTrace();
		}

		return "index";
	}
	
	@PostMapping(value = "/")
	public String recallHome(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {

		try {
			if (session.getAttribute("memberEmail") != null) {
				req.getRequestDispatcher("club/clublists").forward(req, resp);
			}
		} catch (ServletException | IOException e) {
			//
			e.printStackTrace();
		}

		return "index";
	}

}
