package main;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import objects.MySqlJDBC;

public class updateProfile extends HttpServlet{

	private final MySqlJDBC mysql = new MySqlJDBC();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException
	{
		resp.getWriter().print("profile Update... Ok  ");
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
			
			if(input.containsKey("oldpassword")){
				if(checkOldPassword(input))
					resp.getWriter().print(mysql.executeUpdate(buildInsertQuery(input)));
				else {
					resp.getWriter().print("incorrect password!");
					return; 
				}
			}
			if(input.containsKey("newfullname")){
				resp.getWriter().print(mysql.executeUpdate(buildInsertQuery(input)));
			}
		} catch (Exception e) {
			resp.getWriter().print(e.toString());
		}

	}
	
	private boolean checkOldPassword(Map<String, String> input) {
		boolean check = false;
		
		try {
			String oldPassword = input.get("oldpassword");
			String actualPassword = mysql.executeStmt("select password from users where username='" + input.get("username") + "';");
			System.out.println(actualPassword);
			if(oldPassword.trim().equals(actualPassword.trim()))
				check = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	
	private String buildInsertQuery(Map<String,String> input) {
		String query = "";
		/* update users set password = 'akiyo123' where username = 'ayoko001'; */
		
		try {
			if(input.containsKey("oldpassword")) {
				query = "";
	
				query += "update users set password = '" + input.get("newpassword") + "' where username = '" 
				+ input.get("username") + "';";
				
				System.out.println(query);
			}
			if(input.containsKey("newfullname")) {
				query = "";
	
				query += "update users set fullname = '" + input.get("newfullname") + "' where username = '" 
						+ input.get("username") + "';";
				
				System.out.println(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return query;
	}
	
}
