/**    
 * @Title: ReflectUtil.java
 * @Package com.linkincode.core.reflect
 * @Description: TODO
 * @author 母德亮
 * @date 2016年3月8日 下午6:29:55
 * @version V1.0
 * @修改人: 
 * @修改备注: 
 * @修改时间: 
 */
package com.dslcode.core.reflect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.util.GenericSignature.ClassSignature;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @ClassName: ReflectUtil
 * @Description: TODO
 * @author 母德亮
 * @date 2016年3月8日 下午6:29:55
 * @修改人: 
 * @修改备注: 
 * @修改时间: 
 * 
 */
public class ReflectUtil_MDL {
	/**
	 * @Description: 获取切面切入点得注解
	 * @param joinPoint
	 * @param c
	 * @return
	 * @throws Exception Annotation
	 */
	public static <T extends Annotation> Annotation getJoinAnnotation(JoinPoint joinPoint,Class<T> c) throws Exception {
		T anno = null;
		Signature signature = joinPoint.getSignature();
		if(signature instanceof MethodSignature){
			Method method = ((MethodSignature) signature).getMethod();
			anno = method.getDeclaredAnnotation(c);
		}else if(signature instanceof ClassSignature){
			anno = joinPoint.getClass().getDeclaredAnnotation(c);
		}
		return anno;
	}
	/**
	 * @Description: 获取切面切入点类
	 * @param joinPoint
	 * @return
	 * @throws Exception Annotation
	 */
	public static Class getJoinClass(JoinPoint joinPoint) throws Exception {
		return joinPoint.getTarget().getClass();
	}
	/**
	 * @Description: 获取切面切入点方法
	 * @param joinPoint
	 * @return
	 * @throws Exception Annotation
	 */
	public static Method getJoinMethod(JoinPoint joinPoint) throws Exception {
		Method method =  null;
		Signature signature = joinPoint.getSignature();
		if(signature instanceof MethodSignature){
			method = ((MethodSignature) signature).getMethod();
		}else if(signature instanceof ClassSignature){
		}
		return method;
	}
	/**
	 * @Description: 获取对象属性的描述
	 * @param c
	 * @param fieldName
	 * @return Object
	 */
	public static PropertyDescriptor getFieldDescriptor(Class c, String fieldName) {
		return ClassCache.getFieldDescriptor(c, fieldName);
	}
}
