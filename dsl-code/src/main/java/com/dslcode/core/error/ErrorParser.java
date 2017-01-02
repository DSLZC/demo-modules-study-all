package com.dslcode.core.error;

/**
 * ErrorParser
 * @author 董思林
 * @date 2016年11月08日 15:55:30
 */
public class ErrorParser {
	
	public static String getInfo(Throwable e) {
		String type = e.getClass().getName();
		switch (e.getClass().getName()) {
		case "java.lang.NoSuchFieldException":type = "没有属性:";break;
		case "java.lang.SecurityException":type = "不安全:";break;
		case "java.lang.IllegalAccessException":type = "非法访问:";break;
		case "java.lang.IllegalArgumentException":type = "非法参数:";break;
		case "java.lang.InvocationTargetException":type = "方法执行:";break;
		case "java.lang.InstantiationException":type = "没有默认构造非法:";break;
		case "java.lang.ClassNotFoundException":type = "找不到类:";break;
		default:
			break;
		}
		String msg = e.getMessage();
		return type+msg;
	}

}
