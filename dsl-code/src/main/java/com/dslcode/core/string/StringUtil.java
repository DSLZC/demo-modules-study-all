package com.dslcode.core.string;

import com.dslcode.core.util.NullUtil;

import java.util.Collection;

/**
 * StringUtil
 * @author 董思林
 * @date 2016年11月08日 15:55:30
 */
public class StringUtil {

	/**
	 * 判断是否为空或empty
	 * @param s
	 * @return
	 */
	public static boolean isNull(String s) {
		return null == s || s.length() == 0;
	}

	/**
	 * 判断是否全部为空或empty
	 * @param ss
	 * @return
	 */
	public static boolean isNullAll(String... ss){
		for(String s : ss) if(!isNull(s)) return false;
		return true;
	}

	/**
	 * 判断是否不为空或empty
	 * @param s
	 * @return
	 */
	public static boolean isNotNull(String s) {
		return !isNull(s);
	}

	/**
	 * 判断是否全部不为空或empty
	 * @param ss
	 * @return
	 */
	public static boolean isNotNullAll(String... ss){
		for(String s : ss) if(isNull(s)) return false;
		return true;
	}

	/**
	 * 将第一个字母转为大写
	 * @param str
	 * @return
	 */
	public static String toUpperFirstCase(String str) {
		if(NullUtil.isNull(str)) return "";
		char[] cbuf = str.toCharArray();
		cbuf[0] = Character.toUpperCase(cbuf[0]);
		return String.valueOf(cbuf);
	}
	
	
	/**
	* 模拟js join
	* @param c 
	* @param split
	* @return 
	*/
	public static String join(Collection c, String split){
		if(NullUtil.isNull(c)) return "";
		StringBuffer sb = new StringBuffer();
		c.forEach(o -> sb.append(o).append(split));
		return sb.deleteCharAt(sb.length() - split.length()).toString();
	}
	
	/**
	* 模拟js join
	* @param objects 
	* @param split
	* @return 
	*/
	public static String join(Object[] objects, String split){
		if(NullUtil.isNull(objects)) return "";
		StringBuffer sb = new StringBuffer();
		for (Object o : objects) sb.append(o).append(split);
		return sb.deleteCharAt(sb.length() - split.length()).toString();
	}

	/**
	* 字符串拼接连续添加
	* @param sb 已存在的StringBuffer对象，可以为null
	* @param objects 拼接对象
	* @return 拼接过后的字符串
	*/
	public static String append2String(StringBuffer sb, Object...objects){
		return append(sb, objects).toString();
	}

	/**
	 * 字符串拼接连续添加
	 * @param objects 拼接对象
	 * @return 拼接过后的字符串
	 */
	public static String append2String(Object...objects){
		return append(objects).toString();
	}

	/**
	 * 字符串拼接连续添加
	 * @param sb 已存在的StringBuffer对象，可以为null
	 * @param objects 拼接对象
	 * @return 拼接过后的StringBuffer对象
	 */
	public static StringBuffer append(StringBuffer sb, Object...objects) {
		if(null == sb) sb = new StringBuffer();
		for (Object o : objects) sb = sb.append(o);
		return sb;
	}

	/**
	 * 字符串拼接连续添加
	 * @param objects 拼接对象
	 * @return 拼接过后的StringBuffer对象
	 */
	public static StringBuffer append(Object...objects) {
		return append(null, objects);
	}

	/**
	 * 将用户名除第一个中文字符的其他字符替换为*
	 * @param origin
	 * @return
	 */
	public static String replaceUserName(String origin) {
		if(isNull(origin)) return "";
		return origin.replaceAll("([\\u4e00-\\u9fa5_a-zA-Z0-9]{1})[\\u4e00-\\u9fa5_a-zA-Z0-9]*", "$1**");
	}

	/**
	 * 将手机号码的中间四位替换为*
	 * 13112324567 -->131****4567
	 * @param origin
	 * @return
	 */
	public static String replacePhone(String origin) {
		if(isNull(origin)) return "";
		if (origin.length() < 11) return origin;
		return origin.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}

}
