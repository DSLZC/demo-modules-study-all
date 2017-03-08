package com.dslcode.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * 判断非null Util
 * @author DSL 
 * @date 2016-07-13
 */
public class NullUtil {

	/**
	 * 判断对象是否为空或String是否为empty
	 * @param param
	 * @return
	 */
	public static boolean isNull(Object param) {
		if(null == param) return true;
		if (param instanceof String) {	// 字符串
			return ((String) param).length() == 0;
		} else if (param instanceof Collection) {	// 集合
			Collection obj = (Collection) param;
			return obj.size() == 0;
		} else if (param instanceof Map) {	// Map
			Map obj = (Map) param;
			return obj.isEmpty();
		}
		return false;
	}

	/**
	 * 判断提供的对象是否全部为空或String全部是否为empty
	 * @param params
	 * @return
	 */
	public static boolean isNullAll(Object... params){
		for(Object param : params){
			if(!isNull(param)) return false;
		}
		return true;
	}

	/**
	 * 判断对象是否不为空或String是否不为empty
	 * @param param
	 * @return
	 */
	public static boolean isNotNull(Object param) {
		return !isNull(param);
	}

	/**
	 * 判断提供的对象是否全部都不为空或String全部都不为empty
	 * @param params
	 * @return
	 */
	public static boolean isNotNullAll(Object... params){
		for(Object param : params){
			if(isNull(param)) return false;
		}
		return true;
	}
}
