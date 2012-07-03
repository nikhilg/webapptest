package com.proquest.interview.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is just a utility class, you should not have to change anything here
 * @author rconklin
 */
public class DatabaseUtil {
	public static void initDB() {
		try {
			Connection cn = getConnection();
			Statement stmt = cn.createStatement();
			stmt.execute("DROP TABLE PHONEBOOK IF EXISTS");
			stmt.execute("CREATE TABLE PHONEBOOK (NAME varchar(255), PHONENUMBER varchar(255), ADDRESS varchar(255))");
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES('Chris Johnson','(321) 231-7876', '452 Freeman Drive, Algonac, MI')");
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES('Dave Williams','(231) 502-1236', '285 Huron St, Port Austin, MI')");
			cn.commit();
			cn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		return DriverManager.getConnection("jdbc:hsqldb:mem", "sa", "");
	}
	
	public static void closeResources(ResultSet resultSet, Statement statement, Connection connection) {
		try {
			if (resultSet != null){
				resultSet.close();				
			}
		} catch (Exception localException) {
		}
		try {
			if (statement != null){
				statement.close();				
			}
		} catch (Exception localException) {
		}
		try {
			if (connection != null){
				connection.close();				
			}
		} catch (Exception localException) {
		}
	}
	
	/**
	 * This is being used in test class to clean the test data after each test method run
	 */
	public static void clearTestDB() {
		try {
			Connection cn = getConnection();
			Statement stmt = cn.createStatement();
			stmt.execute("DROP TABLE PHONEBOOK IF EXISTS");
			cn.commit();
			cn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
