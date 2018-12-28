package com.org.kap.utill;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

class MysqlCon {
	public static void main(String args[]) {

		Connection con = getConnection();
		Statement stmt = null;
		if (con != null) {
			System.out.println("\n Connection Successed....");
		} else {
			System.out.println("\n Connection Failed....");
		}
		try {
			String sql = "select user_id,user_name,user_type from tab_user_master";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("user_id");
				String name = rs.getString("user_name");
				String mob = rs.getString("user_type");

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", Name: " + name);
				System.out.print(", type: " + mob+"\n\n");
			}
			rs.close();

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					con.close();
			} catch (SQLException se) {
			}// do nothing
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
	}

	public static Connection getConnection() {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// For local connection
			 con = (Connection)
			 DriverManager.getConnection("jdbc:mysql://localhost:3306/kap",
			"root","");

			// For AWS connection
			/*con = (Connection) DriverManager
					.getConnection(
							"jdbc:mysql://kapdbinst.cs6omqrtyc39.us-east-1.rds.amazonaws.com:3306/kapdb",
							"kapadmin", "Passw0rd");*/

			System.out.println(" Connection Obj = " + con);

		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}
}
