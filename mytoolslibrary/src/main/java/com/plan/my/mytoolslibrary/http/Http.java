package com.plan.my.mytoolslibrary.http;

import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class Http extends HttpUtils {
	/** 服务端返回json字符串returnCode **/
	public static final String RETURNCODE_OK = "0";
	public static final String RETURNCODE_OK_0 = "0";
	/** 登录失效的情况 **/
	public static final String RETURNCODE_TIME_OUT = "100";
	/** 连接超时 */
	public static final int conTime = 15 * 1000;
	/** 通讯超时 */
	public static final int soTime = 15 * 1000;
	// public static Dialog dialog;
	public static HttpUtils http;

	/**
	 * http post请求
	 * 
	 * @param url
	 * @param params
	 * @param rc
	 */
	public static void post_Gbk(String url, RequestParams params,
								RequestCallBack<String> rc) {
		Log.e("url", url);
		/*if (params != null && params.getQueryStringParams() != null) {
			String paramStr = "";
			for (NameValuePair nv : params.getQueryStringParams()) {
				paramStr += nv.getName() + "=" + nv.getValue();
			}
			Log.e("params", paramStr);
		}*/
		if (params != null ) {
//			params.getHeaders()
//			String paramStr = "";
//			for (NameValuePair nv : params.getQueryStringParams()) {
//				paramStr += nv.getName() + "=" + nv.getValue();
//			}
			Log.e("params", params.getCharset());
		}
		http = new HttpUtils();
		http.configTimeout(conTime);
		http.configSoTimeout(soTime);
		// 设置返回文本的编码， 默认编码UTF-8
		http.configResponseTextCharset("GBK");
		http.send(HttpMethod.POST, url, params, rc);
	}

	public static void post_Utf(String url, RequestParams params,
								RequestCallBack<String> rc) {
		Log.e("url", url);
		http = new HttpUtils();
		http.configTimeout(conTime);
		http.configSoTimeout(soTime);
		// 设置返回文本的编码， 默认编码UTF-8
//		 http.configResponseTextCharset("GBK");
		http.send(HttpMethod.POST, url, params, rc);
	}
}
