package com.dslcode.demo.shiro.config;

import java.util.LinkedHashMap;

public class AuthorizationFilterChainBuilder {

	/**
	 配置哪些页面需要受保护（按顺序查找）.
	 以及访问这些页面需要的权限.权限在DefaultFilter 枚举里面获取
	 1). anon 可以被匿名（任何人）访问
	 2). authc 必须认证(即登录)后才可能访问的页面.
	 3). logout 登出.
	 4). roles 角色过滤器
	 * @return
	 */
	public LinkedHashMap<String, String> buildFilterChainMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		
		map.put("/page/login.jsp", "anon");
		map.put("/user/login", "anon");
		map.put("/user/logout", "logout");
		map.put("/page/user.jsp", "authc,roles[user]");
		map.put("/page/admin.jsp", "authc,roles[admin]");

		map.put("/page/list", "user");// 记住我能访问
		map.put("/**", "authc");
		
		return map;
	}
	
}