package com.dslcode.core.collection;

import com.dslcode.core.string.StringUtil;
import com.dslcode.core.util.NullUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/4.
 * List集合工具类
 * @author 董思林
 * @date 2016-11-15 10:20:25
 *
 */
public class ListUtil<T, U> {

    /**
     * 根据参数，获取List
     * @param datas
     * @param <T>
     * @return
     */
    public static<T> List<T> getList(T... datas){
        List<T> list = new ArrayList<T>(5);
        if(NullUtil.isNull(datas)) return list;
        for(T data : datas){
            list.add(data);
        }
        return list;
    }

    /**
     * 数组转List
     * @param ts 数组
     * @param <T>
     * @return
     */
    public static<T> List<T> asList(T[] ts){
        return Arrays.asList(ts);
    }

    /**
     * 移除null元素
     * @param list
     * @param <T>
     * @return
     */
    public static<T> List<T> removeNull(List<T> list){
        List<T> results = new ArrayList<T>(list.size());
        list.forEach(l -> {if(null != l) results.add(l);});
        return results;
    }

    /**
     * 模拟js join
     * @param c
     * @param split
     * @return
     */
    public static String join(Collection c, String split){
        return StringUtil.join(c, split);
    }

    /**
     * 根据传入的集合，获取集合中元素的指定属性。例如：List<User> users; User含有Dept，此时若想要获取其中的dept组成的List，即可调用此方法。
     * 实例：List<Dept> depts = ListUtil.attribute2List(users, (User u) -> u.getDept())；
     * @param collection 源集合
     * @param attribute lambda或者实现ListAttribute的对象
     * @param <U> 源集合元素类型
     * @param <T> 想要得到源集合元素的属性的类型
     * @return
     */
    public static<T, U> List<U> attribute2List(Collection<T> collection, Attribute<T, U> attribute){
        List<U> list = new ArrayList<U>();
        collection.forEach(c -> list.add(attribute.get(c)));
        return list;
    }



}
