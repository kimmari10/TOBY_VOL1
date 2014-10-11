package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;

import springbook.user.domain.User;


public class UserDao {
	
	private static UserDao INSTANCE;
	private ConnectionMaker connectionMaker;
	private Connection c;
	private User user;
	
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
	
	public UserDao() {
		connectionMaker = new DConnectionMaker();
	}
	
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker());
		return userDao;
	}
	
	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
	
/*	public static synchronized UserDao getInstance() {
		if (INSTANCE == null) INSTANCE = new UserDao(???);
		return INSTANCE;
	}*/
	
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException {
		this.c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"INSERT INTO USERS(ID, NAME, PASSWORD) VALUES(?,?,?)");

		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		this.c.close();
		
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		this.c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"SELECT * FROM USERS WHERE ID = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		
		this.user = new User();
		this.user.setId(rs.getString("id"));
		this.user.setName(rs.getString("name"));
		this.user.setPassword(rs.getString("password"));
		
		rs.close();
		ps.close();
		this.c.close();
		
		return this.user;
	}
	
}
