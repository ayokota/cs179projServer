package objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class MySqlJDBC {
	
	//private static final Logger logger = LogManager.getLogger(MySqlJDBC.class);
	
	private Statement stmt = null;
    private ResultSet rs = null;
    
    private String mysqlUrl = "jdbc:mysql://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:3306/hangout";
    private String username = "username";
    private String password = "password";
    private Connection conn ;

    
    public MySqlJDBC() {
    	try {
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    	} catch (Exception e) {
    		//logger.error("exception occur while initializing JDBC.", e);
    	}
    }
    
    public String executeStmt(String query) {
    	
    	String result = "";
    	try {
    		conn = DriverManager.getConnection(mysqlUrl, username, password);
    		stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = rs.getString(i);
                    result +=  columnValue + " ";
                }
                System.out.println("");
            }
            
            rs.close();
            rs = null;
            stmt.close();
            stmt=null;
            conn.close();
            conn=null;
    	} catch (Exception e) {
    		///logger.error("exception occur while executing statement.", e);
    	}
    	return result;
    }
    
    public List<String> executeStmtAsList(String query) {
    	List<String> resultList = new LinkedList<String> ();
    	
    	try {
    		conn = DriverManager.getConnection(mysqlUrl, username, password);
    		stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
            	String result = "";
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = rs.getString(i);
                    result +=  columnValue + " ";
                }
                resultList.add(result);
            }
            
            rs.close();
            rs = null;
            stmt.close();
            stmt=null;
            conn.close();
            conn=null;
    	} catch (Exception e) {
    		///logger.error("exception occur while executing statement.", e);
    	}
    	return resultList;
    }
    
    public int executeUpdate(String query) {
    	int result = 0;
    	try {
    		conn = DriverManager.getConnection(mysqlUrl, username, password);
    		stmt = conn.createStatement();
            result = stmt.executeUpdate(query);
            //return 1 means update happens correctly
            //return 0 means update happens incorrectly

            stmt.close();
            stmt=null;
            conn.close();
            conn=null;
    	} catch (Exception e) {
    		//logger.error("exception occur while updating statement", e);
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
