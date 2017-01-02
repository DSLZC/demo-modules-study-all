package com.dslcode.core.map;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装自己适用的Map
 * @author 董思林
 * @param <T> value数据类型
 */
public class DMap<T> {
	private Map<String, T> data;
	
	public DMap(){
		this.data = new HashMap<String, T>();
	}
	
	/**
	 * 创建Map并放入一条数据
	 * @param key
	 * @param value
	 */
	public DMap(String key, T value){
		this.data = new HashMap<String, T>();
		this.data.put(key, value);
	}
	
	/**
	 * 放置数据，返回this以便再次put数据
	 * @param key
	 * @param value
	 * @return
	 */
	public DMap<T> put(String key, T value){
		this.data.put(key, value);
		return this;
	}

	/**
	 * 放置数据，返回Map数据，不能再次put
	 * @param key
	 * @param value
	 * @return
	 */
	public Map<String, T> putGet(String key, T value){
		this.data.put(key, value);
		return this.data;
	}
	
	/**
	 * 返回Map数据
	 */
	public Map<String, T> getMap(){
		return this.data;
	}
}
