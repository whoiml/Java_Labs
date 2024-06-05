package servlet;

import entity.ChatMessage;
import entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected HashMap<String, ChatUser> activeUsers;
	protected ArrayList<ChatMessage> messages;
	@SuppressWarnings("unchecked")
	public void init() throws ServletException {
		super.init();
		activeUsers = (HashMap<String, ChatUser>)getServletContext().getAttribute("activeUsers");
		messages = (ArrayList<ChatMessage>)getServletContext().getAttribute("messages");
		if (activeUsers==null) {
			activeUsers = new HashMap<String, ChatUser>();
			getServletContext().setAttribute("activeUsers",	activeUsers);
		}
		if (messages==null) {
			messages = new ArrayList<ChatMessage>(100);
			getServletContext().setAttribute("messages", messages);
		}
	}

	public boolean checklogint(HttpServletRequest request, HttpServletResponse response) throws IOException{
		boolean b=false;
		for (ChatUser aUser: activeUsers.values()) {
			if(aUser.getName().equals((String)request.getSession().getAttribute("name"))){
				b=true;
			}
		}
		if(!b)
			response.sendRedirect(response.encodeRedirectURL("/labb8_war_exploded/login.do"));
		return b;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(resp.encodeRedirectURL("/labb8_war_exploded/view.do"));
	}
}
