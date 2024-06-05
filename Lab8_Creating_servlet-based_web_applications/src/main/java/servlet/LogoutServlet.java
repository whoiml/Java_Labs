package servlet;

import entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends ChatServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = (String) request.getSession().getAttribute("name");
		if (name!=null) {
			ChatUser aUser = activeUsers.get(name);
			if (aUser!=null && aUser.getSessionId().equals((String)request.getSession().getId())) {
				synchronized (activeUsers) {
					activeUsers.remove(name);
				}
				request.getSession().setAttribute("name", null);
				response.addCookie(new Cookie("sessionId", null));
				response.sendRedirect(response.encodeRedirectURL("/labb8_war_exploded/"));
			} else {
				response.addCookie(new Cookie("sessionId", null));
				response.sendRedirect(response.encodeRedirectURL("/labb8_war_exploded/login.do"));
			}
		} else {
			response.addCookie(new Cookie("sessionId", null));
			response.sendRedirect(response.encodeRedirectURL("/labb8_war_exploded/login.do"));
		}
	}
}
