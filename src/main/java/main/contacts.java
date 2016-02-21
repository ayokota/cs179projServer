package main;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.MySqlJDBC;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class contacts extends HttpServlet{

	private final MySqlJDBC mysql = new MySqlJDBC();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException
	{
		resp.getWriter().print("contacts... Ok  ");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException
	{
		ServletInputStream in = req.getInputStream();
		String theString = IOUtils.toString(in, "UTF-8");

		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Map<String, String> input = new Gson().fromJson(theString, Map.class);
		
		//send friend request, decline or approve
		if(input.containsKey("user2")) {
			
		} else {		//just to check friend list
			//resp.getWriter().print(mysql.executeStmt(pullFriendList(input)));
			String response = mysql.executeStmt(pullFriendList(input));
			Map<String, String> friendList = formatResponse(response, input.get("user1"));
			//System.out.println(new Gson().toJson(friendList));
			resp.getWriter().print(new Gson().toJson(friendList));
		}


	}
	
	private Map<String,String> formatResponse(String response, String user) {
		Map<String,String> result = new HashMap<String,String> ();
		
		StringTokenizer st = new StringTokenizer(response);
		
		while (st.hasMoreElements()) {
			//System.out.println(st.nextElement());
			//if(st.nextElement().equals(user));
			String user1 = st.nextElement().toString();
			String user2 = st.nextElement().toString();
			String status = st.nextElement().toString();
			
			String friend = "";
			if(user1.equals(user)) {
				friend = user2;
			} else if (user2.equals(user)) {
				friend = user1;
			}
			
			if(!status.equals("declined")) {
				result.put(friend, status);
			}
			
		}
		
		return result;
	}
	
	private String pullFriendList(Map<String,String> input) {
		String query = "";
		
		query += "select * from contacts where user1='" + input.get("user1") + "'"
				+ " or user2='" + input.get("user1") + "';";
		
		return query;
	}
	
	private String updateFriendList(Map<String,String> input) {
		String query = "";
		/* update users set password = 'akiyo123' where username = 'ayoko001'; */
		
		
		return query;
	}
	
}