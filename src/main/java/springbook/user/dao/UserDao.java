package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import springbook.user.domain.User;


public class UserDao {
	
	private Connection c;
	private User user;
	private DataSource dataSource;
	private JdbcContext jdbcContext;
	private JdbcTemplate jdbcTemplate;


	public UserDao() {
	}
	
	public UserDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate= new JdbcTemplate(dataSource);

		this.dataSource = dataSource;
	}
	
	public void add(final User user) throws ClassNotFoundException, SQLException {
		this.jdbcTemplate.update("insert into users(id,name,password) values(?,?,?)", 
				user.getId(), user.getName(), user.getPassword());
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
		this.jdbcTemplate.update("delete from users");
		
	}

	public int getCount() throws SQLException {
		return this.jdbcTemplate.queryForInt("select count(*) from users");
	}
	

}
