package cn.hutool.core.annotation;

import cn.hutool.core.util.ReflectUtil;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;

public class ForceAliasedAnnotationAttributeTest {

	@Test
	public void baseInfoTest() {
		// 组合属性
		final Annotation annotation = ClassForTest1.class.getAnnotation(AnnotationForTest.class);
		final Method valueMethod = ReflectUtil.getMethod(AnnotationForTest.class, "value");
		final CacheableAnnotationAttribute valueAttribute = new CacheableAnnotationAttribute(annotation, valueMethod);
		final Method nameMethod = ReflectUtil.getMethod(AnnotationForTest.class, "name");
		final CacheableAnnotationAttribute nameAttribute = new CacheableAnnotationAttribute(annotation, nameMethod);
		final ForceAliasedAnnotationAttribute valueAnnotationAttribute = new ForceAliasedAnnotationAttribute(valueAttribute, nameAttribute);

		// 注解属性
		assertEquals(annotation, valueAnnotationAttribute.getAnnotation());
		assertEquals(annotation.annotationType(), valueAnnotationAttribute.getAnnotationType());

		// 方法属性
		assertEquals(valueMethod.getName(), valueAnnotationAttribute.getAttributeName());
		assertEquals(valueMethod.getReturnType(), valueAnnotationAttribute.getAttributeType());
	}

	@Test
	public void workWhenValueDefaultTest() {
		// 组合属性
		final Annotation annotation = ClassForTest1.class.getAnnotation(AnnotationForTest.class);
		final Method valueMethod = ReflectUtil.getMethod(AnnotationForTest.class, "value");
		final CacheableAnnotationAttribute valueAttribute = new CacheableAnnotationAttribute(annotation, valueMethod);
		final Method nameMethod = ReflectUtil.getMethod(AnnotationForTest.class, "name");
		final CacheableAnnotationAttribute nameAttribute = new CacheableAnnotationAttribute(annotation, nameMethod);
		final AliasedAnnotationAttribute valueAnnotationAttribute = new AliasedAnnotationAttribute(valueAttribute, nameAttribute);

		// 值处理
		assertEquals("name", valueAnnotationAttribute.getValue());
		assertFalse(valueAnnotationAttribute.isValueEquivalentToDefaultValue());
		assertTrue(valueAnnotationAttribute.isWrapped());
	}

	@Test
	public void workWhenValueNonDefaultTest() {
		// 组合属性
		final Annotation annotation = ClassForTest2.class.getAnnotation(AnnotationForTest.class);
		final Method valueMethod = ReflectUtil.getMethod(AnnotationForTest.class, "value");
		final CacheableAnnotationAttribute valueAttribute = new CacheableAnnotationAttribute(annotation, valueMethod);
		final Method nameMethod = ReflectUtil.getMethod(AnnotationForTest.class, "name");
		final CacheableAnnotationAttribute nameAttribute = new CacheableAnnotationAttribute(annotation, nameMethod);
		final ForceAliasedAnnotationAttribute valueAnnotationAttribute = new ForceAliasedAnnotationAttribute(valueAttribute, nameAttribute);

		// 值处理
		assertEquals("", valueAnnotationAttribute.getValue());
		assertTrue(valueAnnotationAttribute.isValueEquivalentToDefaultValue());
		assertTrue(valueAnnotationAttribute.isWrapped());
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD, ElementType.TYPE })
	@interface AnnotationForTest {
		String value() default "";
		String name() default "";
	}

	@AnnotationForTest(name = "name", value = "value")
	static class ClassForTest1 {}

	@AnnotationForTest(value = "value")
	static class ClassForTest2 {}

}
