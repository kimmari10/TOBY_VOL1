package springbook.user.dao;

import java.sql.Connection;

public interface ConnectionMaker {
	public Connection makeConnection();
}
