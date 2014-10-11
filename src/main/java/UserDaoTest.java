import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.domain.User;


public class UserDaoTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		JUnitCore.main("UserDaoTest");
	}
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
		ApplicationContext context = 
				new GenericXmlApplicationContext("springbook/user/dao/applicationContext.xml");

		
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user1 = new User();
		
		user1.setId("gyumee");
		user1.setName("박성철");
		user1.setPassword("springno1");
		
		dao.add(user1);
		
		User user2 = dao.get(user1.getId());
		
		assertThat(user2.getName(), is(user1.getName()));
		assertThat(user2.getPassword(), is(user1.getPassword()));
		
		
	}

}
