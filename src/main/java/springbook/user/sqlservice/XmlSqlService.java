package springbook.user.sqlservice;

import java.util.HashMap;
import java.util.Map;
import springbook.user.sqlservice.jaxb.*;
public class XmlSqlService implements SqlService{
	private Map<String, String> sqlMap = new HashMap<String, String>();

	public XmlSqlService() {
		String contextPath = Sqlmap.class.getPackage().getName();
		
	}
	
	public String getSql(String key) throws SqlRetrievalFailureException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
