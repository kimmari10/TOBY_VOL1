package springbook.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	UserDao userDao;
	
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECCOMEND_FOR_GOLD = 30;
	
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if(canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
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
		
		
		sendUpgradeEMail(user);
	}
	
	public void add(User user) {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
	
	private void sendUpgradeEMail(User user) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail()); //보낼 메일 계정
		mailMessage.setFrom("kimmari10@naver.com"); //보내는사람
		mailMessage.setSubject("Subject");
		mailMessage.setText("your level is" + user.getLevel().name());
		
		this.mailSender.send(mailMessage);
	}

	public User get(String id) {
		return userDao.get(id);
	}

	public List<User> getAll() {
		return userDao.getAll();
	}

	public void deleteAll() {
		userDao.deleteAll();
	}

	public void update(User user) {
		userDao.update(user);
	}
}