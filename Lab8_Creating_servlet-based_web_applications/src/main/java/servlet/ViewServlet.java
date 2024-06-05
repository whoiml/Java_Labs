package servlet;

import entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ViewServlet extends ChatServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		boolean b=false;
		for (ChatUser aUser: activeUsers.values()) {
			if(aUser.getName().equals((String)request.getSession().getAttribute("name"))){
				b=true;
			}
		}
		if(!b)
			response.sendRedirect(response.encodeRedirectURL("/labb8_war_exploded/login.do"));
		PrintWriter pw = response.getWriter();
		pw.println("<!DOCTYPE html>\n<html>" +
					"<head>" +
						"<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>" +
						"<title>Чат: Сообщения</title>" +
					"</head>");
		pw.println( "<frameset rows=\"90,10\">" +
						"<frameset cols=\"80,20\">"+
							"<frame name=\"frameMessages\" src=\"/labb8_war_exploded/messages.do\">"+
							"<frame name=\"frameUsers\" src=\"/labb8_war_exploded/users.do\" noresize>" +
						"</frameset>"+
						"<frame name=\"frameMessage\" src=\"/labb8_war_exploded/WebContent/compose_message.html\" noresize>"+
					"</frameset>");
		pw.println("</html>");
	}
}
