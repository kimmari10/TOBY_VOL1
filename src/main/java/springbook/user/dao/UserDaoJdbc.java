package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.sqlservice.SqlService;

@Component("userDao")
public class UserDaoJdbc implements UserDao{
	private String sqlAdd;
	private String sqlUpdate;
	private String sqlGet;
	private String sqlGetAll;
	private String sqlDeleteAll;
	private String sqlGetCount;
	private Map<String, String> sqlMap;
	@Autowired
	private SqlService sqlService;

	
	
	public void setSqlService(SqlService sqlService) {
		this.sqlService = sqlService;
	}

	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	public void setSqlAdd(String sqlAdd) {
		this.sqlAdd = sqlAdd;
	}

	public void setSqlUpdate(String sqlUpdate) {
		this.sqlUpdate = sqlUpdate;
	}

	public void setSqlGet(String sqlGet) {
		this.sqlGet = sqlGet;
	}

	public void setSqlGetAll(String sqlGetAll) {
		this.sqlGetAll = sqlGetAll;
	}

	public void setSqlDeleteAll(String sqlDeleteAll) {
		this.sqlDeleteAll = sqlDeleteAll;
	}

	public void setSqlGetCount(String sqlGetCount) {
		this.sqlGetCount = sqlGetCount;
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate= new JdbcTemplate(dataSource);
		
	}
	private JdbcTemplate jdbcTemplate;
	private RowMapper<User> userMapper = 
			new RowMapper<User>() {
				public User mapRow(ResultSet rs, int rowNum)throws SQLException {
					User user = new User();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setLevel(Level.valueOf(rs.getInt("level")));
					user.setLogin(rs.getInt("login"));
					user.setRecommend(rs.getInt("recommend"));
					user.setEmail(rs.getString("email"));
					return user;
				}
			};
	
	public void add(final User user) {
		this.jdbcTemplate.update(this.sqlService.getSql("userAdd"), 
				user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
	}
	
	public User get(String id) {
		return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"),
				new Object[] {id}, this.userMapper);
	}
	
	public List<User> getAll() {
		return this.jdbcTemplate.query(this.sqlService.getSql("userGetAll"), 
				this.userMapper);
	}
	
	
	public void deleteAll(){
		this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
		
	}

	public int getCount(){
		return this.jdbcTemplate.queryForInt(this.sqlService.getSql("userGetCount"));
	}

	public void update(User user) {
		this.jdbcTemplate.update(this.sqlService.getSql("userUpdate"),
				user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(),user.getEmail(), user.getId());
		
		
	}
	

	

}
