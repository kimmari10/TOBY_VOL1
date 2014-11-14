package springbook.user.sqlservice;

import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;

public class SqlAdminService implements AdminEventListener{
	private UpdatableSqlRegistry updatableSqlRegistry;
	
	public void setUpdatableSqlRegistry(UpdatableSqlRegistry updatableSqlRegistry) {
		this.updatableSqlRegistry = updatableSqlRegistry;
	}
	
	public void updateEventListener(UpdateEvent event) {
		this.updatableSqlRegistry.updateSql(event.get(KEY_ID), event.get(SQL_ID));
	}
	
	
}
