package com.plan.my.mytoolslibrary.http;

import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.util.Log;

import com.plan.my.mytoolslibrary.toolutils.L;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 主线程里面调用http请求
 * 重新构建框架
 * Created by wudl on 2016/9/13 10:16
 * <p/>
 * 邮箱 770616344@qq.com
 */
public class HttpCaller {
    private static final String TAG = HttpCaller.class.getSimpleName();
    private static final int CONNEC_TTIME = 20000;
    private static final int READ_TIME = 15000;

    public static String getByUtf8(String urlStr,Map<String, String> paramMap) throws Exception {

        JSONObject object = new JSONObject(paramMap);
        HttpURLConnection httpUrlConnection = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {

            System.setProperty("http.keepAlive", "false");
        }

        try {

            //将URL转换为URL类对象
            URL url = new URL(urlStr);
            //打开URL连接
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(CONNEC_TTIME);
            httpUrlConnection.setReadTimeout(READ_TIME);
            httpUrlConnection.setRequestMethod("Get");
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Charset", "UTF-8");
            // Post 请求不能使用缓存
            httpUrlConnection.setUseCaches(false);
            // 设定传送的内容类型是可序列化的java对象
            // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            httpUrlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("parametros", object.toString());

            String query = builder.build().getEncodedQuery();

            httpUrlConnection.setFixedLengthStreamingMode(query.getBytes().length);

            OutputStream os = httpUrlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            //创立连接
            httpUrlConnection.connect();

            int responseCode = httpUrlConnection.getResponseCode();

            L.v(TAG + " reponseCode", String.valueOf(responseCode));

            if(responseCode == HttpURLConnection.HTTP_OK){

                StringBuilder sb = new StringBuilder();
                try{
                    //读取网页内容（字符串流）
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                    String linha;

                    while ((linha = br.readLine())!= null){

                        sb.append(linha);
                    }

                    return sb.toString();
                }catch (Exception e){

                    e.printStackTrace();
                }

            }else{

                if(responseCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT){

                    throw new Exception("Tempo maximo na comunição atingido: "+ httpUrlConnection.getErrorStream());
                }
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
            throw new Exception("Falha de comunicação, verifique sua conexão com a internet");
        }finally {

            httpUrlConnection.disconnect();
        }

        return null;
    }

    public static String postByUtf8(String urlStr,Map<String, String> paramMap) throws Exception {

        JSONObject object = new JSONObject(paramMap);
        HttpURLConnection httpUrlConnection = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {

            System.setProperty("http.keepAlive", "false");
        }

        try {

            //将URL转换为URL类对象
            URL url = new URL(urlStr);
            //打开URL连接
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(CONNEC_TTIME);
            httpUrlConnection.setReadTimeout(READ_TIME);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Charset", "UTF-8");
            // Post 请求不能使用缓存
            httpUrlConnection.setUseCaches(false);
            // 设定传送的内容类型是可序列化的java对象
            // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            httpUrlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("parametros", object.toString());

            String query = builder.build().getEncodedQuery();

            httpUrlConnection.setFixedLengthStreamingMode(query.getBytes().length);

            OutputStream os = httpUrlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            //创立连接
            httpUrlConnection.connect();

            int responseCode = httpUrlConnection.getResponseCode();

            L.v(TAG + " reponseCode", String.valueOf(responseCode));

            if(responseCode == HttpURLConnection.HTTP_OK){

                StringBuilder sb = new StringBuilder();
                try{
                    //读取网页内容（字符串流）
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                    String linha;

                    while ((linha = br.readLine())!= null){

                        sb.append(linha);
                    }

                    return sb.toString();
                }catch (Exception e){

                    e.printStackTrace();
                }

            }else{

                if(responseCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT){

                    throw new Exception("Tempo maximo na comunição atingido: "+ httpUrlConnection.getErrorStream());
                }
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
            throw new Exception("Falha de comunicação, verifique sua conexão com a internet");
        }finally {

            httpUrlConnection.disconnect();
        }

        return null;
    }


}
