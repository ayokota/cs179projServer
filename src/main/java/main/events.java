package main;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

public class events extends HttpServlet{
	
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
			
			if (input.containsKey("search")) {
				resp.getWriter().print(new Gson().toJson(putIntoKeyValPair(mysql.executeStmtAsList(pullAllEvents(input)))));
			}
			else if(input.containsKey("delete")){
				resp.getWriter().print(mysql.executeUpdate(getDeleteEventQuery(input)));
			}
			else if(input.size()==1 && input.containsKey("host")) {
				resp.getWriter().print(getMapJson(mysql.executeStmt(pullEventQuery(input))));
			}
			else if(input.size()==1 && input.containsKey("attendee")) {
				//resp.getWriter().print(new Gson().toJson(mysql.executeStmtAsList(pullAllEventAsAttendeeQuery(input))));
				List<Map<String, String>> response = new LinkedList<Map<String, String>> ();
				List<String> mysqlResponseList = mysql.executeStmtAsList(pullAllEventAsAttendeeQuery(input));
				for(String s : mysqlResponseList) {
					response.add(getMap(s));
				}
				resp.getWriter().print(new Gson().toJson(response));

			}
			else if(input.containsKey("id")) {
				resp.getWriter().print(mysql.executeUpdate(editEvent(input)));
			} else {
				String eventID = generateID(input);
				int status = mysql.executeUpdate(insertEvent(input, eventID));
				if(status == 1) {
					resp.getWriter().print(eventID);
	
				} else {
					resp.getWriter().print(0);
	
				}
			}
		} catch (Exception e) {
			resp.getWriter().print(e.toString());
		}
	}
	
	private Map<String, String> putIntoKeyValPair(List<String> response) {
		Map<String,String> result = new HashMap<String, String>();
		try {
			for (String s : response) {
				StringTokenizer st = new StringTokenizer(s);

				String id =  st.nextToken();
				String topic = "";
		    	 while(st.hasMoreTokens()) {
		    		 topic += st.nextToken().toString() + " ";
		    	 }
		    	 result.put(topic.trim(), id);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String pullAllEvents(Map<String,String> input) {
		String query = "";
		
		try {
			query = "select id, topic from events";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return query;
	}
	
	private String pullAllEventAsAttendeeQuery(Map<String, String> input) {
		String sql = "";
		
		try {
			sql = "select * from events where attendees like '%" + input.get("attendee") + "%';";
			System.out.println(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return sql;
	}
	
	private String getDeleteEventQuery(Map<String,String> input) {
		String sql = "";
		
		try {
			sql = "delete from events where id='" + input.get("delete") + "';";
			System.out.println(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return sql;
	}
	
	private String generateID (Map<String,String> input) {
		return input.get("host")+input.get("topic");
	}
	
	private String insertEvent(Map<String,String> input, String eventID) {
		String sql = "";
		
		sql = "insert into events values ('" + eventID 
				+ "', '" + input.get("host")
				+ "', '" + input.get("keyword")
				+ "', '" + input.get("topic")
				+ "', '" + input.get("time")
				+ "', '" + input.get("location")
				+ "', '' );";  
		System.out.println(sql);
		
		return sql;
	}
	
	private String editEvent(Map<String,String> input) {
		String sql = "";
		
		
		/* update users set password = 'akiyo123' where username = 'ayoko001'; */

		sql = "update events set keyword ='" + input.get("keyword")
				+ "' , topic = '" + input.get("topic") 
				+ "' , time = '" + input.get("time")
				+ "' , location = '" + input.get("location") + "' "
				+ " where id='" + input.get("id") + "';";
		System.out.println(sql);
		
		return sql;
	}
	
	private String pullEventQuery(Map<String,String> input) {
		String sql = "";
		
		
		/* update users set password = 'akiyo123' where username = 'ayoko001'; */

		sql = "select * from events where host='" + input.get("host") + "';";
		System.out.println(sql);
		
		return sql;
	}
	
	private String getMapJson(String result) {
		String json = "";
		Map<String, String> params = new HashMap<String, String>();
		try {
			//System.out.println("injson" + result);
		     StringTokenizer st = new StringTokenizer(result);
		     //while (st.hasMoreTokens()) {
		     //    System.out.println(st.nextToken());
		     //}
		     //| id            | host     | keyword | topic | time                | location | attendees
		     params.put("id", st.nextToken().toString());
		     params.put("host", st.nextToken().toString());
		     params.put("keyword", st.nextToken().toString());
		     params.put("topic", st.nextToken().toString());
		     params.put("time", st.nextToken().toString() + " " + st.nextToken().toString());
		     params.put("location", st.nextToken().toString() +" "  +st.nextToken().toString());
		     if(st.hasMoreTokens()) {
		    	 //params.put("attendees", st.nextToken().toString());
		    	 String attendeeList = "";
		    	 while(st.hasMoreTokens()) {
		    		 attendeeList += st.nextToken().toString() + " ";
		    	 }
		    	 params.put("attendees", attendeeList.trim());

		     }
		     else
		    	 params.put("attendees", "");
		     json = new Gson().toJson(params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	

	private Map<String, String> getMap(String result) {
		Map<String, String> params = new HashMap<String, String>();
		try {
			//System.out.println("injson" + result);
		     StringTokenizer st = new StringTokenizer(result);
		     //while (st.hasMoreTokens()) {
		     //    System.out.println(st.nextToken());
		     //}
		     //| id            | host     | keyword | topic | time                | location | attendees
		     params.put("id", st.nextToken().toString());
		     params.put("host", st.nextToken().toString());
		     params.put("keyword", st.nextToken().toString());
		     params.put("topic", st.nextToken().toString());
		     params.put("time", st.nextToken().toString() + " " + st.nextToken().toString());
		     params.put("location", st.nextToken().toString() + " " + st.nextToken().toString());
		     if(st.hasMoreTokens()) {
		    	 //params.put("attendees", st.nextToken().toString());
		    	 String attendeeList = "";
		    	 while(st.hasMoreTokens()) {
		    		 attendeeList += st.nextToken().toString() + " ";
		    	 }
		    	 params.put("attendees", attendeeList.trim());

		     }
		     else
		    	 params.put("attendees", "");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return params;
	}
}