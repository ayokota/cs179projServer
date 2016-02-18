package main;

import objects.MySqlJDBC;

public class mainApp {
	public static void main(String []args) {
		MySqlJDBC mysql = new MySqlJDBC();
		System.out.println(mysql.executeStmt("select * from users"));
	}
}
