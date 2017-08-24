package com.dslcode.core.date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * date 工具类
 */
public class DateUtil {
    public static final String TIMEZONE = "GMT+8";
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String yyMMddHHmmssSSS = "yyMMddHHmmssSSS";
    public static final String MMddHHmmssSSS = "MMddHHmmssSSS";
    public static final String yyyyMMddHHmmss0 = "yyyyMMddHHmmss";
    public static final String yyyyMMdd0 = "yyyyMMdd";
    public static final String yyyyMM = "yyyyMM";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static final String yyyyMMdd = "yyyy-MM-dd";


    /**
     * 现在时间
     * @return
     */
    public static Date now(){
        return new DateTime().toDate();
    }

    /**
     * 毫秒数转时间str
     * @param format 格式
     * @return
     */
    public static String mils2TimeStr(long mils, String format){
        return new DateTime(mils).toString(DateTimeFormat.forPattern(format));
    }

    /**
     * 现在时间str
     * @param format 格式
     * @return
     */
    public static String nowStr(String format){
        return new DateTime().toString(DateTimeFormat.forPattern(format));
    }

    /**
     * 转换时间字符串为DateTime
     * @param dateStr 时间字符串
     * @param format 格式
     * @return
     */
    public static DateTime parseToDateTime(String dateStr, String format){
        return DateTime.parse(dateStr, DateTimeFormat.forPattern(format));
    }

    /**
     * 转换时间字符串为Date
     * @param dateStr 时间字符串
     * @param format 格式
     * @return
     */
    public static Date parseToDate(String dateStr, String format){
        return parseToDateTime(dateStr, format).toDate();
    }

    /**
     * 现在的秒数
     */
    public static long nowSeconds() {
        return new DateTime().getMillis() / 1000;
    }

    /**
     * 指定时间的毫秒数
     */
    public static long dateStrMillis(String dateStr, String format) {
        return parseToDateTime(dateStr, format).getMillis();
    }

    /**
     * 指定时间的秒数
     */
    public static long dateStrSeconds(String dateStr, String format) {
        return dateStrMillis(dateStr, format) / 1000;
    }

    /**
     * 日期格式化
     * @param date 日期
     * @param format 格式
     * @return
     */
    public static String parse(Date date, String format) {
        return mils2TimeStr(date.getTime(), format);
    }

    /**
     * 人性化DateFormat
     * 30分钟以内--刚刚；30分钟至1个小时--30分钟；1个小时到2个小时--1小时；2个小时以上且是今天--今天；其余--日期。
     * @param date
     * @return
     */
    public static String parseDateToHumanize(Date date) {
        if (null == date) return "";

        long dateSec = date.getTime() / 1000;
        long nowSec = System.currentTimeMillis() / 1000;
        // 判断是否是今天
        if (dateSec/ 86400 == nowSec / 86400){
            long n = dateSec % 86400;
            if (n <= 10800) return "刚刚";
            else if (n <= 21600) return "30分钟";
            else if (n <= 43200) return "1小时";
            else return "今天";
        }
        return DateUtil.parse(date, DateUtil.yyyyMMdd);
    }
}
