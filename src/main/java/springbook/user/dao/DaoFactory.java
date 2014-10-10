package springbook.user.dao;

public class DaoFactory {
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}
	
	/*public AccountDao accountDao() {
		return new AccountDao(connectionMaker());
	}
	
	public MessageDao messageDao() {
		return new MessageDao(connectionMaker());
	}*/
	// AccountDao와 MessageDao를 실제로 구현하지 않았으므로 주석처리 함
	
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
	
	
	
}
