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

public class tracking extends HttpServlet{
	
	private final MySqlJDBC mysql = new MySqlJDBC();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException
	{
		resp.getWriter().print("Events... Ok  ");
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
			
			if(!input.containsKey("location")) {
				resp.getWriter().print(mysql.executeStmt(pullRecordQuery(input)));
			} else if(recordExists(input)) {
				resp.getWriter().print(mysql.executeUpdate(updateRecordQuery(input)));
	
			} else {
				resp.getWriter().print(mysql.executeUpdate(insertRecordQuery(input)));

			}

			
		} catch (Exception e) {
			resp.getWriter().print(e.toString());
		}
	}
	
	private boolean recordExists (Map<String, String> input) {
		boolean exists = false;
		
		try {
			String value = mysql.executeStmt(checkRecordQuery(input)).trim();
			System.out.println("value = " + value);
			if (!value.equals("0")) {
				exists = true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(exists);

		return exists;
	}
	
	private String checkRecordQuery(Map<String, String> input) {
		String query = "";
		
		try {
			query = "select count(*) from tracking where id='" + input.get("id") 
					+ "' and attendee='" + input.get("attendee") + "';";
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(query);

		return query;
	}
	
	private String updateRecordQuery(Map<String, String> input) {
		String query = "";
		
		/* update users set password = 'akiyo123' where username = 'ayoko001'; */
		
		try {
			query = "update tracking set location='" + input.get("location") + "' "
					+ "where id='" + input.get("id") + "' and attendee='" + input.get("attendee") + "';";
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(query);

		return query;
	}
	
	private String insertRecordQuery(Map<String, String> input) {
		String query = "";
		
		try {
			query = "insert into tracking values ('" + input.get("id") 
					+ "', '" + input.get("attendee") 
					+ "', '" + input.get("location") + "');";
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(query);

		return query;
	}
	
	private String pullRecordQuery(Map<String, String> input) {
		String query = "";
		
		try {
			query = "select location from tracking where id='" + input.get("id") 
					+ "' and attendee='" + input.get("attendee") + "';";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}
}
