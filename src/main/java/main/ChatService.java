package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ChatService extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException
	{
		resp.getWriter().print("Chat Servicee... Ok  ");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException
	{
		try {
			String parametersJson = new Gson().toJson(req.getParameterMap());
	
			
			resp.getWriter().print(parametersJson);
		} catch (Exception e) {
			resp.getWriter().print(e.toString());
		}
	}
}
