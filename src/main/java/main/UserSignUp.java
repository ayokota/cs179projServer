package main;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.MySqlJDBC;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserSignUp extends HttpServlet {
	private final MySqlJDBC mysql = new MySqlJDBC();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException
	{
		resp.getWriter().print("user signup... Ok  ");
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
			
			//System.out.println(new Gson().toJson(input));
			
			resp.getWriter().print(mysql.executeUpdate(buildInsertQuery(input)));
		} catch (Exception e) {
			resp.getWriter().print(e.toString());
			e.printStackTrace();
		}
	}
	
	private String buildInsertQuery(Map<String,String> input) {
		String query = "";
		
		query += "insert into users values (\"" 
				+ input.get("username") + "\", \"" 
				+ input.get("password") + "\", \""
				+ input.get("fullname") + "\", \""
				+ input.get("sex") + "\", \"\");"
				;
		System.out.println(query);
		return query;
	}
}
