package com.dslcode.web.request.create;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 模拟产生Java URI请求
 * @author 董思林
 */
public class JavaURIRequest {
	/** 编码 */
	private static final String CHARSET = "utf-8";
	
	 /**
     * 向指定URL发送GET方法的请求
     * @param type 请求类型  get 或 post
     * @param url 发送请求的URL
     * @param params 请求参数
     * @return InputStream 所代表远程资源响应结果的输入流
     */
    public static InputStream invoke(String type, String url, Map<String, String> params) {
    	//参数不为空
    	if(!params.isEmpty()){
    		StringBuilder sb = new StringBuilder(url).append("?");
    		for(String key : params.keySet()){
    			sb.append(key).append("=").append(params.get(key)).append("&");
    		}
    		//去除最后一个&
    		url = sb.substring(0, sb.length()-1);
    	}
        try {
        	URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "keep-alive");
            //connection.setRequestProperty("Accept-Charset", CHARSET);
            if(type.equalsIgnoreCase("post")) {
            	// 发送POST请求必须设置如下两行
            	connection.setDoOutput(true);
            	connection.setDoInput(true);
            }
            // 建立实际的连接
            connection.connect();
            return connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }

}
