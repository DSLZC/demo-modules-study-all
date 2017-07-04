package com.dslcode.core.enum_;

/**
 * Created by dongsilin on 2017/7/3.
 */
public enum  CompareEnum {
    /** 小于 */
    lt("小于", "不能大于"),

    /** 小于等于 */
    lte("小于等于", "大于"),

    /** 等于 */
    eq("等于", "不能等于"),

    /** 不等于 */
    ne("不等于", "等于"),

    /** 大于 */
    gt("大于", "不能小于"),

    /** 大于等于 */
    gte("大于等于", "小于");


    public String desc;
    public String reverseDesc;


    CompareEnum(String desc, String reverseDesc) {
        this.desc = desc;
        this.reverseDesc = reverseDesc;
    }
}
