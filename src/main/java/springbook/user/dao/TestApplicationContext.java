package springbook.user.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
@ImportResource("/mysql.xml")
public class TestApplicationContext {
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(com.mysql.jdbc.Driver.class);
		ds.setUrl("jdbc:mysql://localhost/jsjs");
		ds.setUsername("js");
		ds.setPassword("kim");
		
		return ds;
	}

}
