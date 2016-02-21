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
		ServletInputStream in = req.getInputStream();
		String theString = IOUtils.toString(in, "UTF-8");
		

		
		/*--------------------------------*/

		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Map<String, String> input = new Gson().fromJson(theString, Map.class);

//		String username = req.getParameter("username");
//		String password = req.getParameter("password");
		String username = input.get("username");
		String password = input.get("password");
		String query = "select users.password from users where username = '" + username + "';";
		String actualPassword = mysql.executeStmt(query);
		System.out.println(username);
		System.out.println(password);
		if(actualPassword.equals(password)) {
			resp.getWriter().print(1);
		} else {
			resp.getWriter().print(0);
		}
	}
}