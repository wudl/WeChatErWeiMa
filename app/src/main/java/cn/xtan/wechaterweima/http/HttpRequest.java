package cn.xtan.wechaterweima.http;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.plan.my.mytoolslibrary.http.Http;
import com.plan.my.mytoolslibrary.view.UITools;

import cn.xtan.wechaterweima.bean.AccessTokenWeChatBean;
import cn.xtan.wechaterweima.listener.BooleanStringListener;


/**
 * Created by wudl on 2017/6/27 16:49
 * <p/>
 * 邮箱 770616344@qq.com
 */
public class HttpRequest {


    public static Dialog dialog;


    public static void requestGetToken(final Context context,
                                       final RequestParams params,final Boolean isShow,
                                       final BooleanStringListener vCodeListener) {
        dialog = UITools.createNewLoadingDialog(context, "正在获取token...");

        RequestCallBack<String> rc = new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException arg0, String arg1) {
//                ToastUtil.Toast(UITools.fromError(context, arg1), context);
                Log.e("error", arg1 + "_");
                if(isShow){dialog.cancel();}
                if(vCodeListener != null){
                    vCodeListener.onCallBack(false,"");
                }
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                Log.e("``````````````````", arg0.result);
                if(isShow){dialog.cancel();}
                Gson gson = new Gson();
                try {
                    /** 解析json */
//                    {"access_token":"9z5DbWOn8JHgDUccQFtS7TZEntuvxDIWDfNvc8FWPtZsqjKpkZbhMNOE2toAdjRBqdzhncVg4ubBJJIwLoZZS6ZbyeIRWa86QCYv29zvHpNCrL_iCmkQJmnrZMDisI-lZYTeABATSN","expires_in":7200}

                    /** 解析json */
                    AccessTokenWeChatBean loginInfo = gson.fromJson(arg0.result, AccessTokenWeChatBean.class);

                    if(vCodeListener != null){
                        vCodeListener.onCallBack(true,loginInfo.getAccess_token());
                    }

                } catch (Exception e) {
                    if(vCodeListener != null){
                        vCodeListener.onCallBack(false,"");
                    }
//                    ToastUtil.Toast(context.getString(R.string.login_error),
//                            context);
                }
            }

            @Override
            public void onStart() {
                if(isShow){dialog.show();}
            }

        };

        Http.post_Utf("https://api.weixin.qq.com/cgi-bin/token", params, rc);
    }




}
