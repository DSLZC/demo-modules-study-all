package com.dslcode.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dongsilin on 2016/11/25.
 * 等值判断工具
 */
public class EqualsUtil {
    private static final Logger log = LoggerFactory.getLogger(EqualsUtil.class);

    /**
     * 判断传入的字符串是否全部相等
     * @param strs
     * @return
     */
    public static boolean equalsAllIgnoreCase(String... strs){
        try {
            if(NullUtil.isNull(strs) || strs.length < 2) throw new Exception("至少需要2个对象......");
            String one = strs[0];
            for(int i=1; i<strs.length; i++) {
                if (!one.equalsIgnoreCase(strs[i])) return false;
            }
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

}
