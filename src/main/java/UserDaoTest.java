import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;


public class UserDaoTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = 
				new GenericXmlApplicationContext("springbook/user/dao/applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user1 = new User();
		
		user1.setId("id");
		user1.setName("name");
		user1.setPassword("pass");
		
		dao.add(user1);
		User user2 = dao.get(user1.getId());

		System.out.println(user2.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		
	}

}
