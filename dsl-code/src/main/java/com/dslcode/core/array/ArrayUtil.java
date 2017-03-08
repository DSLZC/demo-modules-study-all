package com.dslcode.core.array;

import com.dslcode.core.collection.ListUtil;
import com.dslcode.core.string.StringUtil;
import com.dslcode.core.util.NullUtil;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/21.
 * 数组操作util
 * @author 董思林
 * @date 2016-11-21 10:20:25
 */
public class ArrayUtil<T> {

    /**
     * List转换为Array
     * @param list 源List
     * @param arrayClass
     * @param <T>
     * @return
     */
    public static<T> T[] toArray(List<T> list, Class<T> arrayClass){
        if(NullUtil.isNotNullAll(list, arrayClass)){
            T[] ts = (T[]) Array.newInstance(arrayClass, list.size());
            for(int i=0;i<list.size();i++) ts[i] = list.get(i);
            return ts;
        }
        return null;
    }

    /**
     * 移除null元素
     * @param oos 源array
     * @param arrayClass
     * @param <T>
     * @return
     */
    public static<T> T[] removeNull(Class<T> arrayClass, T... oos){
        List<T> lists = Arrays.asList(oos);
        lists = ListUtil.removeNull(lists);
        return toArray(lists, arrayClass);
    }

    /**
     * 模拟js join
     * @param objects
     * @param split
     * @return
     */
    public static String join(Object[] objects, String split){
        return StringUtil.join(objects, split);
    }

    /**
     * 判断是否为空
     * @param param
     * @return
     */
    public static<T> boolean isNull(T... param) {
        return null == param || param.length == 0;
    }

    /**
     * 判断是否不为空
     * @param param
     * @return
     */
    public static<T> boolean isNotNull(T... param) {
        return !isNull(param);
    }

}
