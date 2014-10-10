import java.sql.SQLException;

import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;


public class UserDaoTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao = new DaoFactory().userDao();
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
