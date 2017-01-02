package com.dslcode.core.reflect;

import java.math.BigDecimal;

/**
 * Created by dongsilin on 2016/12/9.
 * 反射工具类
 */
public class ReflectUtil {

    /**
     * 根据Class类型将String转化为对应的值
     * @param c
     * @param value
     * @return
     * @throws Exception
     */
    public static Object getValue(Class c, String value) throws Exception {
        String cName = c.getSimpleName();
        if("String".equals(cName)) return value;
        else if("int".equals(cName) || "Integer".equals(cName)) return Integer.parseInt(value);
        else if("Long".equalsIgnoreCase(cName)) return Long.parseLong(value);
        else if("Double".equalsIgnoreCase(cName)) return Double.parseDouble(value);
        else if("Float".equalsIgnoreCase(cName)) return Float.parseFloat(value);
        else if("BigDecimal".equals(cName)) return new BigDecimal(value);
        else throw new Exception("类型"+ cName + "暂不能解析...");
    }
}
