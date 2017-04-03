package com.dslcode.core.string;

import com.dslcode.core.reflect.ReflectUtil;
import com.dslcode.core.util.NullUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dongsilin on 2016/11/25.
 * XML工具
 */
@Slf4j
public class XMLUtil {

    /**
     * 获取xml的根元素
     * @param xml
     * @return
     * @throws Exception
     */
    public static Element getRootElement(String xml) throws Exception {
        xml = xml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if(NullUtil.isNull(xml)) return null;
        InputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        Document doc = new SAXReader().read(in);
        in.close(); //关闭流
        return doc.getRootElement();
    }

    /**
     * xml解析成map
     * @param xml
     * @return
     * @throws IOException
     */
    public static Map xmlParse2Map(String xml) throws Exception {
        if(NullUtil.isNull(xml)) return new HashMap();
        Element root = getRootElement(xml);
        if(null == root) return null;
        return xmlChild2Map(root.elements());
    }

    /**
     * 解析xml子节点成map
     * @param elements
     * @return
     */
    private static Map<String, Object> xmlChild2Map(List<Element> elements){
        Map<String, Object> result = new HashMap<String, Object>();
        elements.forEach(element ->{
            String name = element.getName();
            Object value;
            List<Element> children = element.elements();
            if(children.isEmpty()) value = element.getTextTrim();
            else value = xmlChild2Map(children);
            result.put(name, value);
        });
        return result;
    }

    public static<T> T xmlParse2Instance(String xml, Class<T> c) {
        try {
            Element root = getRootElement(xml);
            if(null == root) return null;
            T t = c.newInstance();
            Class<?> fieldType;
            Object fieldValue;
            for(Element element : (List<Element>) root.elements()){
                String elementName = element.getName();
                fieldType = c.getDeclaredField(elementName).getType();
                Method setMethod = c.getMethod("set" + StringUtil.toUpperFirstCase(elementName), fieldType);
                if(element.elements().isEmpty()) fieldValue = ReflectUtil.getValue(fieldType, element.getTextTrim());
                else fieldValue = element.asXML(); //包含子元素则保留 xml content
                setMethod.invoke(t, fieldValue);
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
