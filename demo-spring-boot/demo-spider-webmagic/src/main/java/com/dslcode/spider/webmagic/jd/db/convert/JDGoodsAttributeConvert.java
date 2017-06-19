package com.dslcode.spider.webmagic.jd.db.convert;

import com.dslcode.core.json.JsonUtil;

import javax.persistence.AttributeConverter;
import java.util.Map;

/**
 * Created by dongsilin on 2017/6/7.
 */
public class JDGoodsAttributeConvert implements AttributeConverter<Map, String> {

    @Override
    public String convertToDatabaseColumn(Map meta) {
        return null == meta? null : JsonUtil.toJson(meta);
    }

    @Override
    public Map convertToEntityAttribute(String dbData) {
        return null == dbData ? null : JsonUtil.readJson(dbData, Map.class);
    }

}
