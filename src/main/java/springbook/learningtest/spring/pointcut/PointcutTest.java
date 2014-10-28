package springbook.learningtest.spring.pointcut;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Target;

import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class PointcutTest {

	@Test
	public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(int minus(int, int))");
		
		//Target.minus()
		assertThat(pointcut.getClassFilter().matches(
				Target.class) && pointcut.getMethodMatcher().matches(
					Target.class.getMethod("minus", int.class, int.class),null), is(true));
		
		//Target.plus()
		assertThat(pointcut.getClassFilter().matches(Target.class) &&
				pointcut.getMethodMatcher().matches(
					Target.class.getMethod("plus", int.class, int.class), null), is(false));
	}
}
