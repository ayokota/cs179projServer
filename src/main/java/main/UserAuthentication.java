package main;

import java.io.FileInputStream;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserAuthentication extends HttpServlet{
	
	
	
	private final MySqlJDBC mysql = new MySqlJDBC();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException
	{
		resp.getWriter().print("user authentication... Ok  ");
	}
	
	
//	
//	public String convertStreamToString(InputStream is) { 
//		StringWriter writer = new StringWriter();
//		IOUtils.copy(is, writer, encoding);
//		String theString = writer.toString();
//	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException
	{
//		ConfigurationSource source = new ConfigurationSource(new FileInputStream("/Users/ayoko001/Desktop/EclipseProjects/main_server/log4j.configurationFile")) ;
//		XmlConfiguration xmlConfig = new XmlConfiguration(source);
		
		try {
			ServletInputStream in = req.getInputStream();
			String theString = IOUtils.toString(in, "UTF-8");
			
			System.out.println(theString);
			
			/*--------------------------------*/
	
			Type type = new TypeToken<Map<String, String>>(){}.getType();
			Map<String, String> input = new Gson().fromJson(theString, Map.class);
	
	//		String username = req.getParameter("username");
	//		String password = req.getParameter("password");
			String username = input.get("username");
			String password = input.get("password");
			
			
			String query = "select users.password from users where username = '" + username + "';";
			String actualPassword = mysql.executeStmt(query).trim();
			System.out.println(username);
			System.out.println(password);
			if(actualPassword.equals(password)) {
				/* update users set password = 'akiyo123' where username = 'ayoko001'; */
				//mysql.executeStmt("update users set login='1' where username = '" + username.trim() + "';");
				
				//here we have to make sure the token is correct 
				if(input.containsKey("gcmtoken")) {
					if(!isCorrectToken(input)) {
						/* update users set password = 'akiyo123' where username = 'ayoko001'; */
						System.out.println("updating token");
						String updateTokenQuery = "update users set gcmid ='" + input.get("gcmtoken") + "' where username ='" + username + "';";
						System.out.println(updateTokenQuery);
						int status = mysql.executeUpdate(updateTokenQuery);
						System.out.println("update token status: "  + status);
					}
				}
				resp.getWriter().print(username);
			} else {
				resp.getWriter().print(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().print(e.toString());
		}
	}
	
	private boolean isCorrectToken (Map<String, String> input) {
		boolean correct = false;
		
		try {
			String username = input.get("username").trim();
			String token = input.get("gcmtoken").trim();
			
			String query = "select users.gcmid from users where username ='" + username + "';";
			System.out.println(query);
			String dbtoken = mysql.executeStmt(query).trim();

			if(dbtoken.equals(token))
				correct = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return correct;
	}
}