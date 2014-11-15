package mapreport.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mapreport.util.Log;

public class DBBase {
	  protected static PreparedStatement pst = null;
	  protected static ResultSet resultSet = null;  
		  
	  static String url = "jdbc:mysql://localhost:3306/new_schema2"; //testdb";
	  static String user = "root";
	  static String password = "hadera";
	  static Connection con;
	  
	  static {
	      try {      	
	      	 Log.info("com.mysql.jdbc.Driver starts");      	 
			 Class.forName("com.mysql.jdbc.Driver");
			 
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.out);
			} 
	  }
}
