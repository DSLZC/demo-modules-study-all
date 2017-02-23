package com.dslcode.core.validate;

import com.dslcode.core.util.NullUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;

/**
 * Created by dongsilin on 2016/12/14.
 * 格式验证工具类
 * Bean Validation 中内置的 constraint
 *      @Null   被注释的元素必须为 null
 *      @NotNull    被注释的元素必须不为 null
 *      @AssertTrue     被注释的元素必须为 true
 *      @AssertFalse    被注释的元素必须为 false
 *      @Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 *      @Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 *      @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 *      @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 *      @Size(max=, min=)   被注释的元素的大小必须在指定的范围内
 *      @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内
 *      @Past   被注释的元素必须是一个过去的日期
 *      @Future     被注释的元素必须是一个将来的日期
 *      @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式
 * Hibernate Validator 附加的 constraint
 *      @NotBlank(message =)   验证字符串非null，且长度必须大于0
 *      @Email  被注释的元素必须是电子邮箱地址
 *      @Length(min=,max=)  被注释的字符串的大小必须在指定的范围内
 *      @NotEmpty   被注释的字符串的必须非空
 *      @Range(min=,max=,message=)  被注释的元素必须在合适的范围内
 */
@Slf4j
public class ValidationUtil<T> {
    private static final Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();

    /** 4-20长度的所有字符 正则 */
    public static final String ALL_4_20_REG = "^.{4,20}$";
    /** 1-50长度的英文字符 正则 */
    public static final String ENGLISH_1_50_REG = "^[A-Za-z]{1,50}$";
    /** 数字和26个英文字母组成4-20的字符串 正则 */
    public static final String NUM_ENGLISH_4_20_REG = "^\\w{4,20}$";
    /** 中文、英文、数字包括下划线，不含其它特殊字符 正则 */
    public static final String NOT_SPECIAL_REG = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$";
    /** 邮箱 正则 */
    public static final String EMAIL_REG = "^\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}$";
    /** 中文2-4 正则 */
    public static final String CHINESE_2_4_REG = "^[\\u4e00-\\u9fa5]{2,4}$";
    /** 网址 正则 */
    public static final String URL_REG = "^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+$";
    /** 手机号码 正则 */
    public static final String MOBILE_REG = "^(13|15|18)[0-9]{9}$";
    /** 浮点数 正则 */
    public static final String FLOAT_NUM_REG = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$";
    /** 非零开头的最多带两位小数的数字 正则 */
    public static final String FLOAT_NUM_2_POINT_REG = "^([1-9][0-9]*)+(.[0-9]{1,2})?$";
    /** 金额 正则 */
    public static final String MONEY_REG = "(^[1-9]\\d*(\\.\\d{1,2})?$)|(^0\\.0[1-9]$)|(^0\\.[1-9]([0-9])?$)";
    /** 大于0的整数 正则 */
    public static final String GT0_NUM_REG = "^[1-9]\\d*$";
    /** 至少11位的数字 正则 */
    public static final String AT_LEAST_11_NUM_REG = "：^\\d{11,}$$";
    /** QQ号码 正则 */
    public static final String QQ_REG = "^[1-9]([0-9]{5,11})$";
    /** 身份证号码 正则 */
    public static final String IDCARD_REG = "^\\d{17}[\\d|x|X]|\\d{15}$";
    /** 时间 正则 */
    public static final String DATE_TIME_REG = "^\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}$";
    /** 用户名 正则 */
    public static final String USER_NAME_REG = "^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{2,10}$";
    /** 密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线) 正则 */
    public static final String PASSWORD_REG = "^[a-zA-Z]\\w{5,17}$";


    /**
     * 正则表达式验证
     * @param str 目标字符串
     * @param required 是否需要验证
     * @param reg 正则表达式
     * @return
     */
    public static boolean isMatch(String str, String reg, boolean required) {
        if(NullUtil.isNull(reg)) return false;
        if(required && NullUtil.isNull(str)) return false;
        //必须验证
        if(required || NullUtil.isNotNull(str)){
            return str.matches(reg);
        }
        return false;
    }

    /**
     * 验证
     * @param t
     * @param <T>
     * @throws Exception
     */
    public static<T> void validate(T t) throws Exception {
        for (ConstraintViolation<T> constraintViolation : validator.validate(t)) {
            throw new Exception(constraintViolation.getPropertyPath().toString() + "：" + constraintViolation.getMessage());
        }
    }

    /**
     * 验证集合
     * @param cs
     * @param <T>
     * @throws Exception
     */
    public static<T> void validateCollection(Collection<T> cs) throws Exception {
        for (T t : cs) validate(t);
    }

    /**
     * 获取第一个验证失败的消息
     * @param result
     * @return
     */
    public static String getErrorMsg(BindingResult result){
        FieldError error = result.getFieldErrors().get(0);
        return error.getField() + "：" + error.getDefaultMessage();
    }
}
