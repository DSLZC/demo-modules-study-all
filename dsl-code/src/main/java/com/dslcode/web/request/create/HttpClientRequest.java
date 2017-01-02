package com.dslcode.web.request.create;

/**
 * Apache HttpClient 发起http请求
 * @author 董思林
 */
public class HttpClientRequest {
//	/** 编码 */
//	private static final String CHARSET = "UTF-8";
//	/** 设置请求超时时间 */
//	private static final int CONNECTION_TIMEOUT = 10 * 1000;
//	/** 设置等待数据超时时间 */
//	private static final int SO_TIMEOUT = 5 * 1000;
//
//	/** httpClient */
//	private HttpClient client = new HttpClient();
//
//	/** 执行请求的get/post方法 */
//	private HttpMethod HttpMethod;
//
//	public HttpClientRequest() {
//		// 设置代理服务器地址和端口
//	    //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
//		HttpConnectionManagerParams params = client.getHttpConnectionManager().getParams();
//		params.setConnectionTimeout(CONNECTION_TIMEOUT);//设置请求超时时间
//		params.setSoTimeout(SO_TIMEOUT);//设置等待数据超时时间
//	}
//
//	/**
//	 * 发起get请求，必须手动调用 releaseConnection() 方法来释放连接
//	 * @param url 请求地址
//	 * @param params 请求参数
//	 * @param responseType 返回参数类型  0字符串或json	1流
//	 * @return 根据返回参数类型确定返回字符串或json或流
//	 * @throws Exception
//	 */
//	public Object doGet(String url, Map<String, String> params, int responseType) {
//		if(null != getHttpMethod(url, params, 0)) return this.getResponse(client, this.HttpMethod, responseType);
//		return null;
//	}
//
//	/**
//	 * 发起post请求，必须手动调用 releaseConnection() 方法来释放连接
//	 * @param url 请求地址
//	 * @param params 请求参数
//	 * @param responseType 返回参数类型  0字符串或json	1流
//	 * @return 根据返回参数类型确定返回字符串或json或流
//	 */
//	public Object doPost(String url, Map<String, String> params, int responseType) {
//		if(null != getHttpMethod(url, params, 1)) return this.getResponse(client, this.HttpMethod, responseType);
//		return null;
//	}
//
//	/**
//	 * 发起上传文件请求，必须手动调用 releaseConnection() 方法来释放连接
//	 * @param url 请求地址
//	 * @param params 请求参数
//	 * @param fileMap 上传文件 Map（服务器端接收文件的名称，文件List）
//	 * @param responseType 返回参数类型  0字符串或json	1流
//	 * @return 根据返回参数类型确定返回字符串或json或流
//	 */
//    public Object uploadFile(String url, Map<String, String> params, Map<String, List<File>> fileMap, int responseType) {
//    	PostMethod postMethod = (PostMethod) getHttpMethod(url, params, 1);
//    	try {
//	    	//添加文件到请求中
//			if(!BooleanUtil.isNull(fileMap)){
//				// FilePart：用来上传文件的类, file即要上传的文件
//				Part[] fileParts = new Part[1];
//				int fileCount = 0;
//				for(String key : fileMap.keySet()) {
//					List<File> files = fileMap.get(key);
//					fileParts = Arrays.copyOf(fileParts, fileCount + files.size());
//					for(File f : files) fileParts[fileCount++] = new FilePart(key, f);
//				}
//				// 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
//				MultipartRequestEntity mre = new MultipartRequestEntity(fileParts, postMethod.getParams());
//				postMethod.setRequestEntity(mre);
//				client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// 由于要上传的文件可能比较大 , 因此在此设置最大的连接超时时间
//			}
//    	} catch (Exception e) {
//    		e.printStackTrace();
//    	}
//    	if(null != postMethod) return this.getResponse(client, postMethod, responseType);
//		return null;
//    }
//
//    /**
//     * 发起请求数据为text流的post请求，必须手动调用 releaseConnection() 方法来释放连接
//     * @param url 请求地址
//	 * @param params 请求参数
//	 * @param is text流
//     * @return
//     * @throws Exception
//     */
//	public Object sendTextStream(String url, Map<String, String> params, InputStream is, int responseType) {
//    	PostMethod postMethod = (PostMethod) getHttpMethod(url, params, 1);
//        // 设置请求的内容直接从输入流中读取
//    	postMethod.setRequestBody(is);
//        // 指定请求内容的类型
//    	postMethod.setRequestHeader( "Content-type" , "text/xml; charset=UTF-8" );
//    	if(null != postMethod) return this.getResponse(client, postMethod, responseType);
//		return null;
//    }
//
//    /**
//     * 模拟单点登录，必须手动调用 releaseConnection() 方法来释放连接
//     * @param url 请求地址
//	 * @param params 请求参数
//     * @return Map(res=登录服务器返回数据， JSESSIONID=响应的cookie中包含JSESSIONID，再次请求时需回传，isRedirect=页面是否重定向)，response.setHeader("Cookie", cookies.toString())
//     */
//    public Map<String, String> casLogin(String url, Map<String, String> params) {
//    	PostMethod postMethod = (PostMethod) getHttpMethod(url, params, 1);
//    	try {
//			//执行方法请求,返回服务器响应状态
//			int responseCode = client.executeMethod(postMethod);
//			Map<String, String> returnData = new HashMap<String, String>();
//			//请求成功
//			if (responseCode == HttpStatus.SC_OK) {
//				returnData.put("res", postMethod.getResponseBodyAsString());
//				for(Cookie c : client.getState().getCookies()){
//					if("JSESSIONID".equals(c.getName())) returnData.put("JSESSIONID", c.getValue());
//				}
//				returnData.put("isRedirect", isRedirect(responseCode)+"");
//			}
//			//释放连接
//			postMethod.releaseConnection();
//			return returnData;
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//		return null;
//    }
//
//    /**
//     * 根据url获取响应html内容，必须手动调用 releaseConnection() 方法来释放连接
//     * @param url
//     * @param params
//     * @return
//     */
//    public String getUrlContent(String url, Map<String, String> params) {
//		PostMethod postMethod = (PostMethod) getHttpMethod(url, params, 1);
//		//释放连接会自动close InputStream
//		InputStream is = (InputStream) this.getResponse(client, postMethod, 1);
//		return streamTOString(is, "UTF-8");
//    }
//
//
//	/**
//	 * 获取请求连接对象
//	 * @param url 请求地址
//	 * @param params 请求参数
//	 * @param requestType 请求类型 0-get  1-post
//	 * @return
//	 */
//	private HttpMethod getHttpMethod(String url, Map<String, String> params, int requestType){
//		if(requestType == 0){
//			//请求参数
//		    if(!BooleanUtil.isNull(params)){
//	    		StringBuilder sb = new StringBuilder(url).append("?");
//	    		for(String key : params.keySet()){
//	    			sb.append(key).append("=").append(params.get(key)).append("&");
//	    		}
//	    		//去除最后一个&
//	    		url = sb.substring(0, sb.length()-1);
//	    	}
//		    //使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
//		    GetMethod getMethod = new GetMethod(url);
//		    //设置编码
//		    //getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);
//		    this.HttpMethod = getMethod;
//		    return getMethod;
//		}else if(requestType == 1){
//			//使用 post 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
//			PostMethod postMethod = new PostMethod(url);
//			//设置编码
//			//postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);
//			//请求参数
//			if(!BooleanUtil.isNull(params)){
//				NameValuePair[] nameValues = new NameValuePair[params.size()];
//				int len = 0;
//				for(String key : params.keySet()){
//					nameValues[len++] = new NameValuePair(key, params.get(key));
//				}
//				postMethod.setRequestBody(nameValues);
//			}
//			this.HttpMethod = postMethod;
//			return postMethod;
//		}
//		return null;
//	}
//
//	/**
//	 * 释放HttpMethod连接
//	 * @param method
//	 */
//	public void releaseConnection(){
//		if (null != this.HttpMethod) this.HttpMethod.releaseConnection();
//	}
//
//	/**
//	 * 获取响应返回结果
//	 * @param client
//	 * @param method get或post HttpMethod
//	 * @param responseType 返回参数类型  0字符串或json	1流
//	 * @return
//	 */
//	private Object getResponse(HttpClient client, HttpMethod method, int responseType){
//		try {
//			//执行方法请求,返回服务器响应状态
//			int responseCode = client.executeMethod(method);
//			//重定向
//			if(this.isRedirect(responseCode)) {
//				return this.redirect(method);
//			}
//			else if(responseCode == HttpStatus.SC_OK) {//请求成功
//				Object res = null;
//				if(responseType == 0) res = method.getResponseBodyAsString();
//				else if(responseType == 1) res = method.getResponseBodyAsStream();
//				return res;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 是否重定向请求
//	 * @param responseCode 响应状态
//	 */
//	private boolean isRedirect(int responseCode) {
//		return responseCode == HttpStatus.SC_MOVED_TEMPORARILY || responseCode == HttpStatus.SC_MOVED_PERMANENTLY || responseCode == HttpStatus.SC_SEE_OTHER || responseCode == HttpStatus.SC_TEMPORARY_REDIRECT;
//	}
//
//	/**
//	 * 重定向请求
//	 * @param method 本次发起的请求
//	 * @return 重定向响应
//	 */
//	private Object redirect(HttpMethod method) {
//		// 读取新的 URL 地址
//		Header header = method.getResponseHeader("location");
//		if (null != header) {
//			String newUri = method.getHostConfiguration().getHostURL();
//			if (!BooleanUtil.isNull(newUri)) newUri += header.getValue();
//			return new HttpClientRequest().doGet(newUri, null, 0);
//		}
//		return null;
//	}
//
//    /**
//     * 转换text输入流为字符串
//     * @param txtIs
//     * @param code
//     * @return
//     */
//	private static String streamTOString(InputStream txtIs, String code) {
//		if(null == txtIs) return null;
//		try {
//			BufferedReader br = new BufferedReader(new InputStreamReader(txtIs, code));
//			String tempbf;
//			StringBuffer html = new StringBuffer(100);
//			while ((tempbf = br.readLine()) != null) {
//				html.append(tempbf).append("\n");
//			}
//			return html.toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//    }
//

}
