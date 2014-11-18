package springbook.user.sqlservice;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;

import springbook.user.dao.UserDao;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

public class OxmSqlService implements SqlService{
	private final OxmSqlReader oxmSqlReader = new OxmSqlReader();
	private final BaseSqlService baseSqlService = new BaseSqlService();
	private SqlRegistry sqlRegistry = new HashMapSqlRegistry();
	
	public void setSqlmap(Resource sqlmap) {
		this.oxmSqlReader.setSqlmap(sqlmap);
	}
	
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}
	
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.oxmSqlReader.setUnmarshaller(unmarshaller);
	}
	public void setSqlmapFile(String sqlmapFile) {
		this.oxmSqlReader.setSqlmapFile(sqlmapFile);
	}
	
	
	@PostConstruct
	public void loadSql() {
		this.baseSqlService.setSqlReader(this.oxmSqlReader);
		this.baseSqlService.setSqlRegistry(this.sqlRegistry);
		this.baseSqlService.loadSql();
	}
	
	
	public String getSql(String key) throws SqlRetrievalFailureException {
		return this.baseSqlService.getSql(key);
	}

	
	
	private class OxmSqlReader implements SqlReader {
		private Resource sqlmap = new ClassPathResource("sqlmap.xml", UserDao.class);
		private Unmarshaller unmarshaller;
		private final static String DEFAULT_SQLMAP_FILE = "sqlmap.xml";
		private String sqlmapFile = DEFAULT_SQLMAP_FILE;
		
		
		public void setSqlmap(Resource sqlmap) {
			this.sqlmap = sqlmap;
		}
		public void setUnmarshaller(Unmarshaller unmarshaller) {
			this.unmarshaller = unmarshaller;
		}
		public void setSqlmapFile(String sqlmapFile) {
			this.sqlmapFile = sqlmapFile;
		}
		
		public void read(SqlRegistry sqlRegistry) {
			try {
				Source source = new StreamSource(sqlmap.getInputStream());
				Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(source);

				for(SqlType sql : sqlmap.getSql()) {
					sqlRegistry.registerSql(sql.getKey(), sql.getValue());
				}
				
			} catch (IOException e) {
				throw new IllegalArgumentException(this.sqlmap.getFilename() +"을 가져올 수 없습니다", e);
			}
		}
		
		
		
	}

}
