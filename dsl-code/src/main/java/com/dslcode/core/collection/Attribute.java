package com.dslcode.core.collection;

/**
 * Created by dongsilin on 2016/11/22.
 * 获取对象属性接口
 * 函数式接口：只包含一个抽象方法
 */

public interface Attribute<T, U> {
    /**
     * 抽象获取，让实现类实现
     * @param t 源对象
     * @return 对象属性
     */
    U get(T t);
}
