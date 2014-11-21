package springbook.user.dao;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springbook.user.service.DummyMailSender;
import springbook.user.service.UserService;
import springbook.user.service.UserServiceTest.TestUserService;
import springbook.user.sqlservice.SqlMapConfig;
import springbook.user.sqlservice.UserSqlMapConfig;

import com.mysql.jdbc.Driver;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="springbook.user")
@EnableSqlService
@PropertySource("/database.properties")
public class AppContext implements SqlMapConfig{
	
	@Value("${db.driverClass}") Class<? extends Driver> driverClass;
	@Value("${db.url}") String url;
	@Value("${db.username}") String username;
	@Value("${db.password}") String password;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource();

		ds.setDriverClass(this.driverClass); 
		ds.setUrl(this.url);
		ds.setUsername(this.username);
		ds.setPassword(this.password);
		
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}
	
	@Configuration
	@Profile("test")
	public static class TestAppContext {
		
		@Bean
		public UserService testUserService() {
			return new TestUserService();
		}
		
		@Bean
		public MailSender mailSender() {
			return new DummyMailSender();
		}
		
		
	}
	
	@Configuration
	@Profile("production")
	public static class ProductionAppContext {
		//mail은 testUserService에서 발송되기 때문에 빈정보 추가
		@Bean
		public UserService testUserService() {
			return new TestUserService();
		}
		
	   @Bean
	   public MailSender mailSender() {
	      Properties prop = new Properties();
	      JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	      mailSender.setHost("smtp.naver.com");
	      mailSender.setPort(25);
	      mailSender.setProtocol("smtp");
	      //계정정보 입력(naver)
	      mailSender.setUsername("userName");
	      mailSender.setPassword("password");
	      
	      prop.put("mail.smtp.starttls.enable", "true");
	      prop.put("mail.smtp.auth", "true");
	      prop.put("mail.smtps.ssl.checkserveridentity", "true");
	      prop.put("mail.smtps.ssl.truest", "*");
	      mailSender.setJavaMailProperties(prop);
	      
	      return mailSender;
	   }
	}

	public Resource getSqlMapResource() {
		return new ClassPathResource("sqlmap.xml", UserDao.class);
	}

	
}
