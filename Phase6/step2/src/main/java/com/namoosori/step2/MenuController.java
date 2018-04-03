package com.namoosori.step2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MenuController {
	//

	@RequestMapping(value = "clubMenu")
	public void getClubMenu(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		try {
			if(session.getAttribute("memberEmail") == null) {
				htmlPrint(resp, "Login First!");
				req.getRequestDispatcher("/").forward(req, resp);
			}else {
				req.getRequestDispatcher("club").forward(req, resp);
			}
		} catch (ServletException | IOException e) {
			//
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "memberMenu")
	public void getMemberMenu(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		try {
			if (session.getAttribute("memberEmail") == null) {
				htmlPrint(resp, "Login First!");
				req.getRequestDispatcher("/").forward(req, resp);
			} else {
				req.getRequestDispatcher("memberProfile").forward(req, resp);
			}
		} catch (ServletException | IOException e) {
			//
			e.printStackTrace();
		}
	}

	private void htmlPrint(HttpServletResponse res, String message) throws IOException {
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("alert('" + message + "');");
		out.println("history.back(-1);");
		out.println("</script>");
		out.println("</html>");
	}
}
