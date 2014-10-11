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
		
		User user2 = new User();
		user2 = dao.get(user1.getId());
		
		if(!user1.getName().equals(user2.getName())) {
			System.out.println("테스트 실패 (name)");
		}
		else if (!user1.getPassword().equals(user2.getPassword())) {
			System.out.println("테스트 실패 (password)");
		}
		else {
			System.out.println("조회 테스트 성공");
		}
		
	}

}
