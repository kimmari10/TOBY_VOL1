package springbook.user.sqlservice.updatable;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import springbook.issuetracker.sqlservice.SqlUpdateFailureException;
import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest{
	UpdatableSqlRegistry sqlRegistry;
	
	@Override
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		return new ConcurrentHashMapSqlRegistry();
	}
	
	@Test
	public void transacionUpdate() {
		checkFind("SQL1", "SQL2", "SQL3");
		
		Map<String, String> sqlmap = new HashMap<String, String>();
		sqlmap.put("KEY1", "Modified1");
		sqlmap.put("KEY9999!@#$", "Modified9999");
		// 존재하지않는 키 설정으로 테스트실패
		
		try {
			sqlRegistry.updateSql(sqlmap);
//			fail();
		} catch (SqlUpdateFailureException e) {}
		checkFind("SQL1", "SQL2", "SQL3");
	}

}
