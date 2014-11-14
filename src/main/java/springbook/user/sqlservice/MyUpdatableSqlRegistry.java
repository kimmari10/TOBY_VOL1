package springbook.user.sqlservice;

import java.util.Map;

import springbook.issuetracker.sqlservice.SqlUpdateFailureException;
import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;

public class MyUpdatableSqlRegistry implements UpdatableSqlRegistry{

	@Override
	public void registerSql(String key, String sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String findSql(String key) throws SqlNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSql(String key, String sql)
			throws SqlUpdateFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSql(Map<String, String> sqlmap)
			throws SqlUpdateFailureException {
		// TODO Auto-generated method stub
		
	}

}
