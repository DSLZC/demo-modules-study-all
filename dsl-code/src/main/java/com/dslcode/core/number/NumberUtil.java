package com.dslcode.core.number;

import com.dslcode.core.util.NullUtil;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数值格式化util
 * @author 董思林
 * @date 2016年11月3日 16:07:38
 */
public class NumberUtil {
	public static final Map<String, DecimalFormat> formatCache = new ConcurrentHashMap<String, DecimalFormat>();
	/** 默认format 2位小数  */
	public static final String DEFAULT = "#.##";
	/** 取整  */
	public static final String INTEGER = "#";

	/**
	 * 获取format对象
	 * @param format
	 * @return DecimalFormat
	 */
	public static DecimalFormat getFormart(String format) {
		DecimalFormat df = formatCache.get(format);
		if (null == df) {
			df = new DecimalFormat(format);
			formatCache.put(format, df);
		}
		return df;
	}

	/**
	 * 格式化
	 * @param num
	 * @param format
	 * @return DecimalFormat
	 */
	public static String format(Number num, String format) {
    	if(NullUtil.isNull(format)) return defaultFormat(num);
		return getFormart(format).format(num);
	}
	/**
	 * 默认格式化 ***.**
	 * @param num
	 * @return DecimalFormat
	 */
	public static String defaultFormat(Number num) {
		return getFormart(DEFAULT).format(num);
	}

//	public static void main(String[] args) {
//		System.out.println(NumberUtil.format(1));
//		System.out.println(NumberUtil.format(1.2));
//		System.out.println(NumberUtil.format(1.23343));
//		System.out.println(NumberUtil.format(1.23343,"##.###"));
//		System.out.println(NumberUtil.format(1.23343,".00%"));
//		System.out.println("--------------");
//	}
}
