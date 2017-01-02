package com.dslcode.core.property;

import java.lang.annotation.*;

/**
 * PropertyMapping 注解
 * 指定该属性值从@PropertySource的类中的value属性拷贝过来,并可通过PropertyUtil.copyProperties方法进行属性拷贝，不指定则直接拷贝名称相同的属性
 * @author 董思林
 * @date 2016年11月08日 15:55:30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropertyMapping {
	String value() default "";
	Class thisClass() default Object.class;
}
