package cn.xtan.wechaterweima.http;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.plan.my.mytoolslibrary.view.UITools;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import cn.xtan.wechaterweima.bean.RecodeInfo;
import cn.xtan.wechaterweima.utils.ImageUtils;

/**
 * Created by wudl on 2017/6/27 13:46
 * <p>
 * 邮箱 770616344@qq.com
 */

public class UploadTAsyncTask extends
        AsyncTask<String, Void, Integer> {

    /**
     * 图片上传成功之后的内容返回
     *
     * @author wudl
     *
     *         2015年10月26日上午9:25:11
     */
    public interface UploadTImagesListener {
        /**
         * code=-1上传图片异常 code =1正常
         *
         * @param code
         * @param bitmap
         */
        public void onUploadTImagesListener(int code, Bitmap bitmap);
    }

    private UploadTImagesListener listener_New;
    private String path = "";
//    private GetUploadSomeImagesListener listener;
    private Context activity;
    private Bitmap bitmap = null;
    private Dialog dialog = null;
    private WeakReference<Activity> weak;
    Map<String, String> param;
    private ImageView imageView = null;

    public UploadTAsyncTask(Context context,
                            Map<String, String> paramh, ImageView imageView,
                            UploadTImagesListener listener) {
        this.listener_New = listener;
        this.activity = context;
        this.param = paramh;
        this.imageView = imageView;
        dialog = UITools.createLoadingDialog(activity, "正在加载，请稍候...");
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // dialog.show();
    }

    RecodeInfo info;
    /** 返回的是图片内容 **/
    String vCode = "";

    @Override
    protected Integer doInBackground(String... params) {
        try {

            info = uploadFile(this.activity, param);
            /** 这个方法没什么效果，和上面的方法差不多 **/
        } catch (Exception e) {// FileNotFoundException
            return 0;
            // } catch (IOException e) {//IOException
            // return -1;
        }
        return 1;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (dialog != null) {
            dialog.cancel();
        }
        if (result == 0) {
            listener_New.onUploadTImagesListener(-1, null);
        } else if (result == 1) {// 正常
            if (info != null) {
                if (info.getCode() == 0) {
                    listener_New.onUploadTImagesListener(1,
                            info.getBitmap());
                } else {
                    listener_New.onUploadTImagesListener(-1, null);
                }

            } else {
                listener_New.onUploadTImagesListener(1, null);
            }

        } else {
            listener_New.onUploadTImagesListener(-1, null);
        }
    }

    public  RecodeInfo uploadFileNew(Context context,
                                     Map<String, String> param) {
        RecodeInfo item = new RecodeInfo();
        String uid = param.get("uid");
        if (uid == null || "".equals(uid)) {
            return item;
        }
//        String path = UriUtils.getImagePathFromUri(context, uri);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        item = uploadFile(context,  param);
        return item;
    }


    // Create an anonymous class to trust all certificates.
    // This is bad style, you should create a separate class.
    private X509TrustManager xtm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            // System.out.println("cert: " + chain[0].toString() + ", authType:
            // "
            // + authType);
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };
    // Create an class to trust all hosts
    private HostnameVerifier hnv = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            // System.out.println("hostname: " + hostname);
            return true;
        }
    };

    // 上传图片int pictureOrder
    public  RecodeInfo uploadFile(Context context,
                                  Map<String, String> mapParam) {

        RecodeInfo info = new RecodeInfo();
        info.setCode(-1);
        info.setvCode("");

        String end = "";//
        String twoHyphens = "";
        String boundary = "";
        DataOutputStream ds = null;
        try {


            /**
             // 创建SSLContext对象，并使用我们指定的信任管理器初始化
             TrustManager[] tm = { new MyX509TrustManager() };
             SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
             sslContext.init(null, tm, new java.security.SecureRandom());
             // 从上述SSLContext对象中得到SSLSocketFactory对象
             SSLSocketFactory ssf = sslContext.getSocketFactory();
             ***/
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");//TLS
                X509TrustManager[] xtmArray = new X509TrustManager[] {xtm};
                sslContext.init(null, xtmArray, new java.security.SecureRandom());
            } catch (GeneralSecurityException e) {
                // Print out some error message and deal with this exception
                e.printStackTrace();
            }
            String access_token = mapParam.get("access_token");
            String aa = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + access_token;
            Log.e("www","&&&&&&&&&&&&");
            Log.e("",aa);
            Log.e("www","&&&&&&&&&&&&");
            URL url = new URL(aa);// URLs.UPDATE_PHOTO_JSON
            // URLs.IP +
            // "UpdateImageJson"UpdatePersonalImagesJson
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            //            con.setSSLSocketFactory(ssf);

            // Set the default SocketFactory and HostnameVerifier
            // for javax.net.ssl.HttpsURLConnection
            if (sslContext != null) {
                con.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            }
            con.setDefaultHostnameVerifier(hnv);

			/* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
			/* 设置传送的method=POST */
            con.setRequestMethod("POST");
			/* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "utf-8");//GBK
            con.setRequestProperty("Content-Type",
                    "application/json");// boundary 边界线
			/* 设置DataOutputStream */
            ds = new DataOutputStream(con.getOutputStream());

//            FileInputStream fStream = new FileInputStream(uploadFile);

            StringBuffer sb = null;
            String params = "";
            sb = new StringBuffer();

            // {"path":"pages/schoolLibrary/schoolLibrary?query=1"}
            sb = null;
            sb = new StringBuffer();
            String value = param.get("path");//{"path": "pages/index?query=1", "width": 430}
            sb.append(twoHyphens).append(boundary).append(end);//Content-Disposition:form-data;
            String width = param.get("width");
            if(TextUtils.isEmpty(width)){
                //Map里面没有传宽度的时候   就不传宽度的参数
                sb.append("{\"path\":\"")
                        .append(value).append("\"};")
                        .append(end);
            }else{
                //Map里面传了宽度的时候   就传宽度的参数
                sb.append("{\"path\":\"")
                        .append(value).append("\",\"width\":").append(width).append("};")
                        .append(end);
            }


//            sb.append(value).append(end);
            params = sb.toString();
            // Log.i(TAG, key + "=" + params + "##");
            ds.writeBytes(params);



//            ds.writeBytes(twoHyphens + boundary + end);
//            ds.writeBytes("Content-Disposition: form-data; "
//                    + "filename=_\"" + value
//                    + "\"" + end);//name="imageDate";
//            ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			/* 设置每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
			/* 从文件读取数据至缓冲区 */
//            while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
//                ds.write(buffer, 0, length);
//            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
//            fStream.close();
            ds.flush();
			/* 取得Response内容 */
            int code = con.getResponseCode();
            Log.d("www", "取得Response内容" + code);
            String value12 = "";
            if (code == 200) {
                InputStream is = con.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));// GBK
                Bitmap bitmap11 = BitmapFactory.decodeStream(is);

//                imageView.setImageBitmap(bitmap);


                String filename = "" + ImageUtils.getImageName();
                File f = new File(ImageUtils.IMAGE_PATH + filename);
                if (f.exists()) {
                    f.delete();
                }

                ImageUtils.saveBitmap2(bitmap11, filename);//小程序二维码

                info.setCode(0);
                info.setBitmap(bitmap11);

                int ch;
                StringBuffer b = new StringBuffer();
                while ((ch = reader.read()) != -1) {
                    b.append((char) ch);
                }
                value12 = b.toString();
            }
            Log.e("上传微官网头图", "图片上传结束后返回值：" + value12);
            try {

            } catch (Exception e) {
                e.printStackTrace();
                info.setCode(-1);
                info.setvCode("");
            }
            ds.close();
            // con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            info.setCode(-1);
            info.setvCode("");
        }
        return info;
    }

}
