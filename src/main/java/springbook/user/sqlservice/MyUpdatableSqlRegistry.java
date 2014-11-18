package springbook.user.sqlservice;

import java.util.Map;

import springbook.issuetracker.sqlservice.SqlUpdateFailureException;
import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;

public class MyUpdatableSqlRegistry implements UpdatableSqlRegistry{

	public void registerSql(String key, String sql) {
		// TODO Auto-generated method stub
		
	}

	public String findSql(String key) throws SqlNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateSql(String key, String sql)
			throws SqlUpdateFailureException {
		// TODO Auto-generated method stub
		
	}

	public void updateSql(Map<String, String> sqlmap)
			throws SqlUpdateFailureException {
		// TODO Auto-generated method stub
		
	}

}
