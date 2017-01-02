package com.dslcode.core.property;

import java.lang.annotation.*;

/**
 * PropertySource注解
 * 指定该类的属性从value类拷贝过来,并可通过PropertyUtil.generateTarget方法生成一个新的value类实例，实例中的属性由@PropertyMapping指定,如果不指定则直接拷贝名称相同的属性
 * @author 董思林
 * @date 2016年11月08日 15:55:30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropertySource {
	Class value();
}
