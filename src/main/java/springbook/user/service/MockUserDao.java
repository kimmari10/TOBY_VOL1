package springbook.user.service;

import java.util.ArrayList;
import java.util.List;

import springbook.user.dao.UserDao;
import springbook.user.domain.User;

public class MockUserDao implements UserDao{
	private List<User> users;
	private List<User> updated = new ArrayList();
	
	public MockUserDao(List<User> users) {
		this.users = users;
	}

	public List<User> getUpdated() {
		return this.updated;
	}

	public List<User> getAll() {
		return this.users;
	}
	public void update(User user) {
		updated.add(user);
	}
	
	
	// return null, 빈채로 둬도 상관없지만 실수로 실행되는 것을 대비해 예외를 던지게 한다.
	public void add(User user) {throw new UnsupportedOperationException();}
	public void deleteAll() {throw new UnsupportedOperationException();}
	public User get(String id) {throw new UnsupportedOperationException();}
	public int getCount() {throw new UnsupportedOperationException();}
}
