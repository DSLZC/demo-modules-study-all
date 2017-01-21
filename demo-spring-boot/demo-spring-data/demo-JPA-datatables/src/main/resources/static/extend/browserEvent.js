/**
 * Created by dongsilin on 2017/1/8.
 * 浏览器事件处理
 */

 /**
  * 注册浏览器事件，打开，刷新，和关闭事件
  * @param onopen 自定义浏览器打开处理方法
  * @param onfresh 自定义浏览器刷新处理方法
  * @param onclose 自定义浏览器关闭处理方法
  */
var browser_regist_init = function(onopen, onfresh, onclose){
	var browser_refresh_time = 5; //浏览器刷新时间(ms)，灵活配置
	var _beforeUnload_time = 0, 
	_gap_time = 0, 
	is_fireFox = navigator.userAgent.indexOf("Firefox")>-1;//是否是火狐浏览器
	
	// 页面加载时执行
	if(typeof(onopen) == 'function') window.onload = function (){
		onopen();
	};
	
	// 刷新之前执行
	window.onbeforeunload = function (){
		_beforeUnload_time = new Date().getTime();
		if(is_fireFox)//火狐关闭执行
			if(typeof(onclose) == 'function') onclose();
	};
	
	// 新页面即将替换旧页面时onunload事件
	window.onunload = function (){
		_gap_time = new Date().getTime() - _beforeUnload_time;
		if(_gap_time > browser_refresh_time) {
			// 浏览器刷新
			if(typeof(onfresh) == 'function') onfresh();
		} else {
			// 浏览器关闭
			if(typeof(onclose) == 'function') onclose();
		}
	}
};

