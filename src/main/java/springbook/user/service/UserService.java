package springbook.user.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserService {
	private DataSource dataSource;
	private PlatformTransactionManager transactionManager;
	
	UserDao userDao;
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECCOMEND_FOR_GOLD = 30;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void upgradeLevels() throws Exception {
		TransactionStatus status = 
				this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		//트랜잭션 시작
		
		try {
			List<User> users = userDao.getAll();
			for(User user : users) {
				if(canUpgradeLevel(user)) {
					upgradeLevel(user);
				}
			}
			this.transactionManager.commit(status);
			
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		} 
		
	}
	
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch(currentLevel) {
			case BASIC : return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
			case SILVER : return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
			case GOLD : return false;
			default : throw new IllegalArgumentException("Unknown Level : "+ currentLevel);
		}
	}
	
	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
	}
	
	public void add(User user) {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
}