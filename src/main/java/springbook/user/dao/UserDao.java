package springbook.user.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
		
		return this.jdbcTemplate.queryForObject("select * from users where id = ?",
				new Object[] {id},
				new RowMapper<User>(){
					public User mapRow(ResultSet rs, int rowNum)throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						return user;
					}
				});
	}
	
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id", 
				new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum)throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		});
	}
	
	
	public void deleteAll() throws SQLException {
		this.jdbcTemplate.update("delete from users");
		
	}

	public int getCount() throws SQLException {
		return this.jdbcTemplate.queryForInt("select count(*) from users");
	}
	

}
