package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.User;


public class UserDao {
	
	private static UserDao INSTANCE;
	private ConnectionMaker connectionMaker;
	private Connection c;
	private User user;
	private DataSource dataSource;
	
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
	
	public UserDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException {
		StatementStrategy st = new AddStatement(user);
		jdbcContextWithStatementStrategy(st);
		
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		this.c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"SELECT * FROM USERS WHERE ID = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			
			this.user = new User();
			this.user.setId(rs.getString("id"));
			this.user.setName(rs.getString("name"));
			this.user.setPassword(rs.getString("password"));
			System.out.println("조회 성공");
		}			
		
		rs.close();
		ps.close();
		this.c.close();
		
		if(user == null) throw new EmptyResultDataAccessException(1);
		
		return this.user;
	}
	
	public void deleteAll() throws SQLException {
		StatementStrategy st = new DeleteAllStatement();
		jdbcContextWithStatementStrategy(st);
		
	}

	public int getCount() throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			c = dataSource.getConnection();

			ps = c.prepareStatement("select count(*) from users");

			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e){
			throw e;
		}finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e){
				}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if(c != null) {
				try{
					c.close();
				}catch (SQLException e) {
				}	
			}
		}
	}
	
	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = dataSource.getConnection();
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null) { try {ps.close();} catch (SQLException e) {}}
			if(c != null) { try {c.close();} catch (SQLException e) {}}
		}
	}
	
}
