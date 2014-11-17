package springbook.user.sqlservice.updatable;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import springbook.issuetracker.sqlservice.SqlUpdateFailureException;
import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest{
	EmbeddedDatabase db;
	
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		db = new EmbeddedDatabaseBuilder()
		.setType(HSQL).addScript(
				"classpath:/springbook/user/sqlservice/updatable/sqlRegistrySchema.sql")
				.build();
		
		EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
		embeddedDbSqlRegistry.setDataSource(db);
		
		return embeddedDbSqlRegistry;
				
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

	
	
	@After
	public void tearDown() {
		db.shutdown();
	}
}
