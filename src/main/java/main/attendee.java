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

public class attendee extends HttpServlet{
	
	private final MySqlJDBC mysql = new MySqlJDBC();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException
	{
		resp.getWriter().print("attendee... Ok  ");
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
			
			/*------------- debug session -------------*/
//			String host = input.get("host");
//			String topic = input.get("topic");
//			String keywords = input.get("keyword");
//			String time = input.get("time");
//			String location = input.get("location");
//			
//			System.out.println(host + "\t" + topic + "\t" + keywords + "\t" + time + "\t" + location + "\n");
			/*------------- debug session -------------*/

			
			if (input.containsKey("attendee")) {
				resp.getWriter().print(mysql.executeUpdate(addAttendeeQuery(input)));
			} else if (input.size()==1 && input.containsKey("id")) {
				resp.getWriter().print(mysql.executeStmt(getAttendeeQuery(input)).trim());
			}
			
		} catch (Exception e) {
			resp.getWriter().print(e.toString());
		}
	}
	
	private String addAttendeeQuery(Map<String, String> input) {
		String query = "";
		
		try {
			String attendeeList = mysql.executeStmt(getAttendeeQuery(input));
			attendeeList += input.get("attendee");
			query = "update events set attendees='" + attendeeList
					+ "' where id ='" + input.get("id") + "';";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return query;
	}
	
	private String getAttendeeQuery(Map<String, String> input) {
		String query = "";
		
		try {
			query = "select attendees from events where id='" + input.get("id") + "';";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}
}
	
