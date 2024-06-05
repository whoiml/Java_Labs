package servlet;

import entity.ChatMessage;
import entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

public class NewMessageServlet extends ChatServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String pname=null;
		String message = (String)request.getParameter("message");
		if (message!=null && !"".equals(message)) {
			String privatem=(String)request.getSession().getAttribute("privatem");
			if(privatem != null && !"toall".equals(privatem))
				pname=privatem;
			ChatUser author = activeUsers.get((String)
					request.getSession().getAttribute("name"));
			synchronized (messages) {
				messages.add(new ChatMessage(message, author,
						Calendar.getInstance().getTimeInMillis(),pname));
			}
		}
		response.sendRedirect("/labb8_war_exploded/WebContent/compose_message.html");
	}
}
