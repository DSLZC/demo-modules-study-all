package com.dslcode.core.property;

import com.dslcode.core.error.ErrorParser;
import com.dslcode.core.reflect.ReflectUtil_MDL;
import com.dslcode.core.string.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.ReflectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;


/**
 * 对象属性Util
 * @author 董思林
 * @date 2016年11月08日 15:55:30
 */
public class PropertyUtil<T> {

	private final static Log log = LogFactory.getLog(PropertyUtil.class);
	public static Class sourceAnnoClass = PropertySource.class;
	public static Class propAnnoClass = PropertyMapping.class;

	/**
	 * 复制对象属性
	 * @param source
	 * @param target
	 * @param validType 验证source类型是否与target中@PropertySource类型一致
	 */
	public static void copyProperties(Object source, Object target, boolean validType) {
		try {
			copy(source, target, validType);
		}catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 复制集合对象属性到voList
	 * @param sources
	 * @param targetClass
	 * @param validType 验证source类型是否与target中@PropertySource类型一致
	 * @return List<T>
	 */
	public static<T> List<T> copyProperties(Collection sources, Class<T> targetClass, boolean validType) {
		List<T> vos = new ArrayList<T>();
		if(null == sources) return vos;
		try {
			for (Object source : sources) {
				T vo = targetClass.newInstance();
				vos.add(vo);
				copy(source, vo, validType);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return vos;
	}

	/**
	 * 复制对象属性
	 * @param source
	 * @param target
	 * @param validType 验证source类型是否与target中@PropertySource类型一致
	 * @param target
	 */
	private static void copy(Object source, Object target, boolean validType) throws Exception {
		if(null == source || null == target) return;
		//copy普通属性
		BeanUtils.copyProperties(source, target);
		//判断类是否匹配
		PropertySource propertySource = (PropertySource) target.getClass().getDeclaredAnnotation(sourceAnnoClass);
		Class targetClass = target.getClass();
		if(null != propertySource){//有配置PropertySource注解但是value类型不匹配source则返回
			if(validType){//验证拷贝类型
				Class<?> sourceClass = propertySource.value();
				if(sourceClass != source.getClass() &&
						sourceClass != source.getClass().getSuperclass()//考虑代理对象
					) return;
			}
			// copy带注解的特殊属性
			PropertyDescriptor[] descriptors = ReflectUtils.getBeanProperties(targetClass);
			for (PropertyDescriptor descriptor : descriptors) {
				String name = descriptor.getName();
				Field field = targetClass.getDeclaredField(name);
				String mapping = getPropMapping(field);
				if(null != mapping){	// copy带注解的特殊属性
					Object value = getPropMappingValue(source, mapping);
					PropertyMapping propAnno = getPropMappingAnno(field);
					if(null != propAnno && propAnno.thisClass()!=Object.class){//指定使用vo类型转换属性
						Class targetValueClass = propAnno.thisClass();//VOclass
						if(value instanceof Collection){//集合类型的vo属性copy
							Collection sourceValueList = (Collection) value;
							Collection targetValueList = new ArrayList();
							if(value instanceof List) targetValueList = new ArrayList();
							else if(value instanceof Set) targetValueList = new HashSet();
							//生成集合VO对象
							for (Object sourceValue : sourceValueList) {
								Object targetValue = targetValueClass.newInstance();
								targetValueList.add(targetValue);
								copy(sourceValue, targetValue, validType);
							}
							value = targetValueList;
						}else{//单个的vo属性copy
							Object targetValue = null;
							if(null != value){//值不为空
								targetValue = targetValueClass.newInstance();
								copy(value, targetValue, validType);
							}
							value = targetValue;
						}
					}
					// 通过setXXX方法写入target
					descriptor.getWriteMethod().invoke(target, value);
				}
			}
		}
	}

	/**
	 * 通过@PropertySource生成目标对象并填入数据
	 * @param source
	 * @return Object
	 */
	public static Object generateTarget(Object source) {
		if(null == source) return null;
		Class sourceClass = source.getClass();
		//判断类是否匹配
		PropertySource propertySource = (PropertySource) source.getClass().getDeclaredAnnotation(sourceAnnoClass);
		if(propertySource == null)log.error(sourceClass.getName()+"没有注解@PropertySource");
		Class targetClass = propertySource.value();
		Object target = null;
		try {
			target = targetClass.newInstance();
			//copy普通属性
			BeanUtils.copyProperties(source, target);
			//copy特殊属性
			PropertyDescriptor[] descriptors = ReflectUtils.getBeanProperties(sourceClass);
			for (PropertyDescriptor descriptor : descriptors) {
				String name = descriptor.getName();
//				System.out.println("--------------"+name+"--------------");
				try {
					Field field = sourceClass.getDeclaredField(name);
					String mapping = getPropMapping(field);
//					System.out.print(mapping);
					if(null != mapping){
						Object value = descriptor.getReadMethod().invoke(source);
						PropertyMapping propAnno = getPropMappingAnno( field);
						Class targetValueClass = propAnno.thisClass();//VOclass
						if(null != propAnno && propAnno.thisClass()!=Object.class){//指定使用vo类型转换属性
							if(value instanceof Collection){//集合类型的vo属性copy
								log.error(field.getType().getName());
								PropertySource voPropertyTarget = (PropertySource) targetValueClass.getDeclaredAnnotation(sourceAnnoClass);
								if(null == voPropertyTarget)log.error(targetValueClass.getName()+"没有注解@PropertySource");
								targetValueClass = voPropertyTarget.value();//vo集合对应的Class
								Collection sourceValueList = sourceValueList = (Collection) value;
								Collection targetValueList = new ArrayList();
								if(value instanceof List){
									targetValueList = new ArrayList();
								}else if(value instanceof Set){
									targetValueList = new HashSet();
								}
								//生成VOTarget集合对象
								for (Object sourceValue : sourceValueList) {
									log.error(targetValueClass.getName());
									targetValueList.add(generateTarget(sourceValue));
								}
								value = targetValueList;
							}else{//单个的vo属性copy
								value = generateTarget(value);
							}
						}
						setPropMappingValue(target, mapping, value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			log.error(StringUtil.append(targetClass.getClass().getName(),ErrorParser.getInfo(e)),e);
		}
		return target;
	}

	/**
	 * 读取字段注解@PropertyMapping
	 * @param field
	 * @return String
	 */
	public static PropertyMapping getPropMappingAnno(Field field) {
		return  (PropertyMapping) field.getDeclaredAnnotation(propAnnoClass);
	}

	/**
	 * 读取字段注解@PropertyMapping("user.baseUser.id")中的映射属性user.baseUser.id
	 * @param field
	 * @return String
	 */
	public static String getPropMapping(Field field) {
		PropertyMapping propAnno =  getPropMappingAnno(field);
		if(null == propAnno) return null;
		else{
			String mapping = propAnno.value();
			return "".equals(mapping)? null : mapping;
		}
	}

	/**
	 * 获取源对象的指定多层属性值(如user.baseUser.id)
	 * @param source
	 * @param mapping
	 * @return Object
	 */
	public static Object getPropMappingValue(Object source, String mapping) throws Exception {
		if(null == source) return null;
		Object value = null;
		String[] names = mapping.split("\\.", 2);
		PropertyDescriptor propDescriptor = ReflectUtil_MDL.getFieldDescriptor(source.getClass(), names[0]);
		value = propDescriptor.getReadMethod().invoke(source);
		//继续获取下一层的属性
		if(names.length == 2) value = getPropMappingValue(value, names[1]);
		return value;
	}

	/**
	 * 设置目标对象的指定多层属性值(如user.baseUser.id)
	 * @param target
	 * @param mapping
	 * @param value
	 */
	public static void setPropMappingValue(Object target, String mapping, Object value) {
		if(null == target || null == value)return ;
		String[] names = mapping.split("\\.", 2);
		try {
			PropertyDescriptor propDescriptor = ReflectUtil_MDL.getFieldDescriptor(target.getClass(), names[0]);
			try {
				if(names.length == 2){//继续设置下一层的属性
					Object fieldValue = propDescriptor.getReadMethod().invoke(target);
					if(null == fieldValue){//中间属性为空,需要连接起来
						fieldValue = propDescriptor.getPropertyType().newInstance();
						propDescriptor.getWriteMethod().invoke(target,fieldValue);
					}
					setPropMappingValue(fieldValue, names[1], value);
				}else{
					propDescriptor.getWriteMethod().invoke(target,value);
				}
			} catch (Exception e) {
				log.error(StringUtil.append(target.getClass().getName(),":",propDescriptor.getName(),ErrorParser.getInfo(e)),e);
			}
		} catch (Exception e) {
			log.error(StringUtil.append(target.getClass().getName(),ErrorParser.getInfo(e)),e);
		}
	}


	private static final Mapper dozerMapper = new DozerBeanMapper();

	/**
	 * Dozer 复制属性，性能非常好
	 * @param source
	 * @param targetClass
	 * @return
	 */
	public static<T> T dozerMapper(Object source, Class<T> targetClass){
		return dozerMapper.map(source, targetClass);
	}

	/**
	 * Dozer 复制List属性，性能非常好
	 * @param sources
	 * @param targetClass
	 * @return
	 */
	public static<T> Collection<T> dozerListMapper(List sources, Class<T> targetClass){
		List<T> results = new ArrayList<T>(sources.size());
		sources.forEach(s -> results.add(dozerMapper(s, targetClass)));
		return results;
	}

}
