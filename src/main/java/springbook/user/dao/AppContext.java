package springbook.user.dao;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springbook.user.service.DummyMailSender;
import springbook.user.service.UserService;
import springbook.user.service.UserServiceTest.TestUserService;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="springbook.user")
@Import({SqlServiceContext.class, AppContext.TestAppContext.class, AppContext.ProductionAppContext.class})
public class AppContext {
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(com.mysql.jdbc.Driver.class);
		ds.setUrl("jdbc:mysql://localhost/jsjs");
		ds.setUsername("js");
		ds.setPassword("kim");
		
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

	
}
