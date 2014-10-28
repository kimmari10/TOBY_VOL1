package springbook.service;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

//클래스 필터가 포함된 포인트 컷
public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut{
	public void setMappedClassName(String mappedClassName) {
		this.setClassFilter(new SimpleClassFilter(mappedClassName));
	}

	static class SimpleClassFilter implements ClassFilter {
		String mappedName;
		
		private SimpleClassFilter(String mappedName) {
			this.mappedName = mappedName;
		}
		
		public boolean matches(Class<?> clazz) {
			//simpleMatch : 와일드카드(*)가 들어간 문자열 비교를 지원하는 스프링의 유틸리티 메소드
			return PatternMatchUtils.simpleMatch(mappedName, clazz.getSimpleName());
		}
	}
}
