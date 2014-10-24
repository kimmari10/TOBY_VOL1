package springbook.learningtest.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
	Object target;
	
	//다이나믹 프록시로부터 전달받은 요청을 다시 타깃 오브젝트에 위임해야 하기 때문에 타깃 오브젝트를 주입받음
	public UppercaseHandler(Hello target) {
		this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object ret = method.invoke(target, args);
		//호출한 메소드의 리턴 타입이 String인 경우만 대문자 변경 기능을 적용
		//method이름이 say로 시작하는 조건 추가
		if(ret instanceof String && method.getName().startsWith("say")) {
			return ((String)ret).toUpperCase();
		}
		else {
			return ret;
		}
	}

}
