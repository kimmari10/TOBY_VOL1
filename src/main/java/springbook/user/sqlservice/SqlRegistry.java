package springbook.user.sqlservice;

public interface SqlRegistry {
	void registerSql(String key, String value);
	
	String findSql(String key) throws SqlNotFoundException;
	

}
