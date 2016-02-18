package objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlJDBC {
	
	private Connection conn = null;
	private Statement stmt = null;
    private ResultSet rs = null;
    
    private String mysqlUrl = "jdbc:mysql://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:3306/hangout";
    private String username = "username";
    private String password = "password";
    
    public MySqlJDBC() {
    	try {
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    		conn = DriverManager.getConnection(mysqlUrl, username, password);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public String executeStmt(String query) {
    	String result = "";
    	try {
    		stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = rs.getString(i);
                    result +=  columnValue ;
                }
                System.out.println("");
            }
            
            rs.close();
            rs = null;
            stmt.close();
            stmt=null;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return result;
    }
    
    public int executeUpdate(String query) {
    	int result = 0;
    	try {
    		stmt = conn.createStatement();
            result = stmt.executeUpdate(query);
            //return 1 means update happens correctly
            //return 0 means update happens incorrectly

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return result;
    }
	
    public static void main(String[] args) {
        try {
             Class.forName("com.mysql.jdbc.Driver").newInstance();
             Connection conn = null;
            try {
                conn =
                DriverManager.getConnection("jdbc:mysql://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:3306/hangout"
           		   , "username", "password");
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("show tables;");

                    while (rs.next())
                    { //I am processing result set now
                    	System.out.println(rs.getString(1) );
                    }
                }
                catch (SQLException ex){
                    // handle any errors
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }
                finally {
                    // it is a good idea to release
                    // resources in a finally{} block
                    // in reverse-order of their creation
                    // if they are no-longer needed

                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException sqlEx) { } // ignore

                        rs = null;
                    }

                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException sqlEx) { } // ignore

                        stmt = null;
                    }
                }
                conn.close();
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } 
        }catch (Exception ex) {
            // handle the error
        }
        
    }
}
