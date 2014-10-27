package springbook.learningtest.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionHandler implements InvocationHandler{
	private Object target;
	private PlatformTransactionManager transactionManager;
	private String pattern;
	
	public void setTarget(Object target) {
		this.target = target;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		//트랜잭션 적용 대상 메소드를 선별해서 트랜잭션 경계설정 기능 부여
		if(method.getName().startsWith(pattern)) {
			return invokeInTransaction(method, args);
		}
		else {
			return method.invoke(target, args);
		}
	}

	private Object invokeInTransaction(Method method, Object[] args)
		throws Throwable {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			//트랜잭션을 시작하고 타깃 오브젝트의 메소드를 호출, 예외가 발생하지 않으면 커밋
			Object ret = method.invoke(target, args);
			this.transactionManager.commit(status);
			return ret;
		} catch (InvocationTargetException e) {
			this.transactionManager.rollback(status);
			throw e.getTargetException();
		}
	}
	
		
}
