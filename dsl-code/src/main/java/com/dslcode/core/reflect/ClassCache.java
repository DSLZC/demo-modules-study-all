/**    
 * @Title: ReflectCache.java
 * @Package com.linkincode.core.reflect
 * @Description: TODO
 * @author 母德亮
 * @date 2016年3月15日 下午6:03:25
 * @version V1.0
 * @修改人: 
 * @修改备注: 
 * @修改时间: 
 */
package com.dslcode.core.reflect;

import org.springframework.cglib.core.ReflectUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: ReflectCache
 * @Description: TODO
 * @author 母德亮
 * @date 2016年3月15日 下午6:03:25
 * @修改人:
 * @修改备注:
 * @修改时间:
 * 
 */
public class ClassCache {
	private static Map<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();
	private static Map<Class<?>, Map<String, PropertyDescriptor>> classPropMap = new LinkedHashMap<Class<?>, Map<String, PropertyDescriptor>>();

	/**
	 * @Description: 获取类型
	 * @param className
	 * @return Class<?>
	 */
	public static Class<?> get(String className) {
		Class<?> c = classMap.get(className);
		if (null == c) {
			try {
				c = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			classMap.put(className, c);
		}
		return c;
	}

	/**
	 * @Description: 缓存类型
	 * @param obj
	 * @return void
	 */
	public static void add(Object obj) {
		if (null == obj)
			return;
		add(obj.getClass());
	}
	/**
	 * @Description: 缓存类型
	 * @param c
	 * @return void
	 */
	public static void add(Class c) {
		if (null == c) return;
		if (classPropMap.containsKey(c))
			return;
		classMap.put(c.getName(), c);
		// 添加PropertyDescriptor
		Map<String, PropertyDescriptor> propMap = new HashMap<String, PropertyDescriptor>();
		classPropMap.put(c, propMap);
		PropertyDescriptor[] descriptors = ReflectUtils.getBeanProperties(c);
		for (PropertyDescriptor descriptor : descriptors) {
			propMap.put(descriptor.getName(), descriptor);
		}
	}

	/**
	 * @Description: 获取对象属性的描述
	 * @param c
	 * @param fieldName
	 * @return Object
	 */
	public static PropertyDescriptor getFieldDescriptor(Class c, String fieldName) {
		if (!classPropMap.containsKey(c)) {
			add(ReflectUtils.newInstance(c));
		}
		PropertyDescriptor propDescriptor = classPropMap.get(c).get(fieldName);
		return propDescriptor;
	}
}
