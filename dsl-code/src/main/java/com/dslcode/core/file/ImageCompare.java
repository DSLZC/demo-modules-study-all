package com.dslcode.core.file;

import com.dslcode.core.enum_.CompareEnum;
import com.dslcode.core.string.StringUtil;
import lombok.Data;

/**
 * Created by dongsilin on 2017/7/3.
 */
@Data
public class ImageCompare {

    private CompareEnum compare;

    private long value;

    private long limitValue;

    private Type type;

    public ImageCompare() {
    }

    public enum  Type {
        /** 宽 */
        width("宽度"),
        /** 高 */
        height("宽度"),
        /** 文件大小，字节为单位 */
        size("大小");
        public String desc;

        Type(String desc) {
            this.desc = desc;
        }
    }

    public ImageCompare(CompareEnum compare, long value, long limitValue, Type type) {
        this.compare = compare;
        this.value = value;
        this.limitValue = limitValue;
        this.type = type;
    }

    public static ImageCompare build(Type type, long value, long limitValue, CompareEnum compare){
        return new ImageCompare(compare, value, limitValue, type);
    }

    public static ImageCompare build(Type type, long limitValue, CompareEnum compare){
        return new ImageCompare(compare, 0L, limitValue, type);
    }

    public void executeCompare() throws Exception{
        switch (this.getCompare()){
            case lt:
                if (this.value >= this.limitValue) throw new Exception(error());
                break;
            case lte:
                if (this.value > this.limitValue) throw new Exception(error());
                break;
            case eq:
                if (this.value != this.limitValue) throw new Exception(error());
                break;
            case ne:
                if (this.value == this.limitValue) throw new Exception(error());
                break;
            case gt:
                if (this.value <= this.limitValue) throw new Exception(error());
                break;
            case gte:
                if (this.value < this.limitValue) throw new Exception(error());
                break;
        }
    }

    public String error(){
        return StringUtil.append2String("图片", this.type.desc, this.compare.reverseDesc , this.type == Type.size?size2String() : this.limitValue);
    }


    private String size2String(){
        if (this.value > 1024*1024) return this.value/1024/1024 + "M";
        else if (this.value > 1024) return this.value/1024 + "KB";
        return this.value + "b";
    }
}
