package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
	public Connection makeNewConnection() throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection c = DriverManager.getConnection("jdbc:oracle:thin@localhost:1521:oracle");
		
		return c;
	}
	
}
