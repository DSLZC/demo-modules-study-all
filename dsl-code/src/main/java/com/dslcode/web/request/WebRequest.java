package com.dslcode.web.request;

import javax.servlet.http.HttpServletRequest;

public class WebRequest {

	/**
	 * 判断是否为Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		String header = request.getHeader("X-Requested-With");
	    return (null != header && "XMLHttpRequest".equals(header));
	}
}
