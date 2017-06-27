package com.plan.my.mytoolslibrary.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QNCaller
{
    public static final String TIMEOUT_MSG = "系统繁忙，请稍后再试";
    public static final String NO_NETWORK_MSG = "没有可用的网络，请检查网络";
    /** 接口调用成功 */
    public static final int SUCCESS = 1;
    /** 没有网络连接 */
    public static final int NO_NETWORK = 2;
    /** 网络连接超时 */
    public static final int TIMEOUT = 4;
    /** 接口调用失败 */
    public static final int FAILURE = 5;
    private static final String TAG = "Caller";
    private Context mContext = null;
    private boolean retryToken = false;
    public static final String FAIL_ACCESSTOKEN_CODE = "-201";
    public static final String LOGIN_CODE = "-25";
    public static final String NULL_ACCESSTOKEN_CODE = "-214";
    public QNCaller(Context _context)
    {
        mContext = _context;
    }
    /**

     * 自动登录

     */
//    public synchronized String autoLogin(String url)
    {
//        return QNHttpCaller.init(mContext, "login", url, null).doPost();
    }
    /**

     * 调用接口

     * 

     * @param url

     *            接口url

     * @param paramMap

     *            接口参数

     * @return

     */
    public static HttpResponseInfo doHttpRequestByUTF8(String url, Map<String, String> paramMap, Context context,
                                                       String httpType, int connectionTimeout, int soTimeout)
    {                
        if (TextUtils.isEmpty(url))
        {
            // 接口url为空
            return new HttpResponseInfo(FAILURE);
        }
        // TODO 网络连接
        // if (!CommonUtil.checkNetworkConnected(context)) {
        // // 没有网络连接
        // LogUtil.e("Caller 没有网络连接");
        // LogUtil.d("没有网络连接>>绑定老板号码");
        // return new HttpResponseInfo(NO_NETWORK, NO_NETWORK_MSG);
        // }
        Log.e(TAG ,"Caller 接口url:" + url);
        Log.e(TAG ,"Caller 接口参数:" + (paramMap == null ? "" : paramMap.toString()));
        try
        {
            DefaultHttpClient clientLoginField = new DefaultHttpClient();
            setConnectionTimeOut(clientLoginField, connectionTimeout, soTimeout);
            // 接口传�?参数
            List<NameValuePair> nvps = makePostParam(paramMap);
            HttpPost request = new HttpPost(url);
            request.setEntity(new UrlEncodedFormEntity(nvps, httpType));
            HttpResponse response = clientLoginField.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                // 接口调用成功
                String responseBodyString = EntityUtils.toString(response.getEntity());
                Log.e(TAG ,"Caller 接口调用成功，返回字符串:" + responseBodyString);
                request = null;
                response = null;
                nvps = null;
                clientLoginField = null;
                return new HttpResponseInfo(SUCCESS, "", responseBodyString);
            }else{
            	Log.e(TAG,"Caller 接口调用失败." + response.getStatusLine().getStatusCode());
                request = null;
                response = null;
                nvps = null;
                clientLoginField = null;
                return new HttpResponseInfo(FAILURE);
            }
        }
        catch (ConnectTimeoutException e)
        {
            e.printStackTrace();
            Log.e(TAG ,"Caller 接口调用ConnectTimeoutException" + e);
            return new HttpResponseInfo(TIMEOUT, TIMEOUT_MSG);
        }
        catch (SocketTimeoutException e)
        {
            e.printStackTrace();
            Log.e(TAG ,"Caller 接口调用SocketTimeoutException" + e);
            return new HttpResponseInfo(TIMEOUT, TIMEOUT_MSG);
        }
        catch (SocketException e)
        {
            e.printStackTrace();
            Log.e(TAG ,"Caller 接口调用SocketException" + e);
            return new HttpResponseInfo(TIMEOUT, TIMEOUT_MSG);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e(TAG ,"Caller 接口调用exception" + e);
            return new HttpResponseInfo(FAILURE);
        }
    }
    
    
    /**

     * 设置网络连接超时

     * 

     * @param clientLoginField

     */
    protected static void setConnectionTimeOut(DefaultHttpClient clientLoginField)
    {
        setConnectionTimeOut(clientLoginField, 10000, 10000);
    }
    /**

     * 设置网络连接超时

     * 

     * @param clientLoginField

     */
    protected static void setConnectionTimeOut(DefaultHttpClient clientLoginField, int connectionTimeout, int soTimeout)
    {
        HttpParams httpParams = clientLoginField.getParams();
        // 设置网络超时参数
        HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, soTimeout);
    }
    /**

     * @author: wudl

     * @Title: makePostParam

     * @Description: 构�?接口参数

     * @param paramMap

     *            接口参数

     * @return 接口参数

     * @date: 2015-06-24 下午04:01:59

     */
    private static List<NameValuePair> makePostParam(Map<String, String> paramMap)
    {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (paramMap != null && !paramMap.isEmpty())
        {
            NameValuePair nvp = null;
            Set<String> paramKeys = paramMap.keySet();
            for (String key : paramKeys)
            {
                nvp = new BasicNameValuePair(key, paramMap.get(key));
                nvps.add(nvp);
            }
        }
        return nvps;
    }
    public static HttpResponseInfo doHttpRequestByUTF(String url, Map<String, String> paramMap, Context context)
    {
        return doHttpRequestByUTF8(url, paramMap, context, HTTP.UTF_8, 10000, 20000);
    }
    public static HttpResponseInfo doHttpRequestByGBK(String url, Map<String, String> paramMap, Context context)
    {
        return doHttpRequestByUTF8(url, paramMap, context, "GBK", 10000, 20000);
    }
    
    /**同步短信专用**/
//    public static SyncResult doHttpRequestByGBKForSms(String url, Map<String, String> paramMap, Context context)
//    {
//        return doHttpRequestByUTF8ForSms(url, paramMap, context, "GBK", 10000, 20000);
//    }
}
