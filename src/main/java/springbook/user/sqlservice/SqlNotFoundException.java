package springbook.user.sqlservice;

public class SqlNotFoundException extends RuntimeException {
	public SqlNotFoundException(String message) {
		super(message);
	}
	public SqlNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	public SqlNotFoundException(Throwable cause) {
		super(cause);
	}

}