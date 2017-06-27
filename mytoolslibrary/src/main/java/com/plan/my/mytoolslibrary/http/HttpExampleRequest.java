package com.plan.my.mytoolslibrary.http;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Http请求接口的例子
 * 这里不实现
 * 只是做样板
 */
public class HttpExampleRequest {
/**
	public static Dialog dialog;


	public static void get_Share_Url_Json(final Context context,
												   final RequestParams params,
												   final StringValueListener stringValueListener) {
//		dialog = UITools.createLoadingDialog(context, "正在发送验证码，请稍候...");

		RequestCallBack<String> rc = new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				UITools.Toast(UITools.fromError(context, arg1), context);
				Log.e("error", arg1 + "_");
//				dialog.cancel();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Log.e("``````````````````", arg0.result);
//				dialog.cancel();
				Gson gson = new Gson();
				try {
					** 解析json *
					LoginInfo loginInfo = gson.fromJson(arg0.result, LoginInfo.class);
					** 状态==00，销毁当前activity *
					if (Http.RETURNCODE_OK.equalsIgnoreCase(loginInfo
							.getCode())) {
						if(stringValueListener != null){
							stringValueListener.getValue(true,loginInfo.getVCode());
						}
					}else if (Http.RETURNCODE_TIME_OUT.equalsIgnoreCase(loginInfo
							.getCode())) {
						//uid失效的时候要重新登录
						// 登录失效的情况！！！
						changeTimeOut(context, URLs.IP + URLs.GET_SHARE_URL_JSON, params, this);
					} else {
						UITools.Toast(loginInfo.getMsg(), context);
						if(stringValueListener != null){
							stringValueListener.getValue(false,"");
						}
					}

				} catch (Exception e) {
					if(stringValueListener != null){
						stringValueListener.getValue(false,"");
					}
				}
			}

			@Override
			public void onStart() {
//				dialog.show();
			}

		};

		Http.post_Utf(URLs.IP + URLs.GET_SHARE_URL_JSON, params, rc);

	}

*/
}
