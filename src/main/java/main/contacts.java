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
		try {
			ServletInputStream in = req.getInputStream();
			String theString = IOUtils.toString(in, "UTF-8");
	
			Type type = new TypeToken<Map<String, String>>(){}.getType();
			Map<String, String> input = new Gson().fromJson(theString, Map.class);
			
			//send friend request, decline or approve
			if(input.containsKey("user2")) {
				resp.getWriter().print(mysql.executeUpdate(updateFriendList(input)));
			} else if(input.containsKey("check") ){
				System.out.println("check users");
				resp.getWriter().print(mysql.executeStmt(checkUserQuery(input.get("check"))));
			} else {		//just to check friend list
				//resp.getWriter().print(mysql.executeStmt(pullFriendList(input)));
				String response = mysql.executeStmt(pullFriendList(input));
				Map<String, String> friendList = formatResponse(response, input.get("user1"));
				//System.out.println(new Gson().toJson(friendList));
				resp.getWriter().print(new Gson().toJson(friendList));
			}
		} catch (Exception e) {
			resp.getWriter().print(e.toString());
		}


	}
	
	private Map<String,String> formatResponse(String response, String user) {
		Map<String,String> result = new HashMap<String,String> ();
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private String pullFriendList(Map<String,String> input) {
		String query = "";
		try {
			query += "select * from contacts where (user1='" + input.get("user1") + "' and status='accepted') "
					+ " or user2='" + input.get("user1") + "';";
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return query;
	}
	
	private String updateFriendList(Map<String,String> input) {
		String query = "";
		/* update users set password = 'akiyo123' where username = 'ayoko001'; */
		
//		query += "update users set password = '" + input.get("newpassword") + "' where username = '" 
//				+ input.get("username") + "';";
		try {
			if(input.get("status").equals("requested")) {
				query = "insert into contacts values (\""
						+ input.get("user1") + "\", \"" 
						+ input.get("user2") + "\", \""
						+ input.get("status") + "\");";
			} else if(input.get("status").equals("accepted")) {
				query += "update contacts set status = '" + input.get("status") + "' where " 
				+ "(user1 = '" + input.get("user1").trim() + "' and user2 = '" + input.get("user2").trim() + "') or "
				+ "(user1 = '" + input.get("user2").trim() + "' and user2 = '" + input.get("user1").trim() + "'); " ;
			} else {
				query += "delete from contacts where "
						+ "(user1 = '" + input.get("user1").trim() + "' and user2 = '" + input.get("user2").trim() + "') or "
						+ "(user1 = '" + input.get("user2").trim() + "' and user2 = '" + input.get("user1").trim() + "'); " ;
			}
			
			System.out.println(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}
	
	private String checkUserQuery(String user) {
		String query = "";
		
		try {
			query = "select count(*) as total from users where username='" + user + "';";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}
}