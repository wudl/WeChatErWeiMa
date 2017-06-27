package cn.xtan.wechaterweima;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

import cn.xtan.wechaterweima.http.HttpRequest;
import cn.xtan.wechaterweima.http.UploadTAsyncTask;
import cn.xtan.wechaterweima.listener.BooleanStringListener;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }






    Button login1_btn;
    EditText name1_edt,name2_edt,path_edt,width_edt;
    private void initView(){
        name1_edt = (EditText)this.findViewById(R.id.name1_edt);
        name2_edt = (EditText)this.findViewById(R.id.name2_edt);
        path_edt = (EditText)this.findViewById(R.id.path_edt);
        width_edt = (EditText)this.findViewById(R.id.width_edt);
        login1_btn = (Button)this.findViewById(R.id.login1_btn);

        name1_edt.setText("wx8dfe1988165dc7b4");
        name2_edt.setText("9dd8cd57cb9cf826140097169b0fff63");
        path_edt.setText("pages/schoolLibrary/schoolLibrary?query=1");
        width_edt.setText("");
        login1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putHttp(name1_edt.getEditableText().toString().trim(),name2_edt.getEditableText().toString().trim(),path_edt.getEditableText().toString().trim());
            }
        });

        login_img = (ImageView)this.findViewById(R.id.login_img);
    }
    ImageView login_img;



    private void putHttp(String appid,String secret,final String path){
        if(TextUtils.isEmpty(appid)){
            Toast.makeText(MainActivity.this,"appid不能为空！",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(secret)){
            Toast.makeText(MainActivity.this,"secret不能为空！",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(path)){
            Toast.makeText(MainActivity.this,"path不能为空！",Toast.LENGTH_LONG).show();
            return;
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("grant_type","client_credential");
        params.addQueryStringParameter("grant_type","client_credential");
        params.addBodyParameter("appid",appid);
        params.addQueryStringParameter("appid",appid);
        params.addBodyParameter("secret","" + secret);
        params.addQueryStringParameter("secret","" + secret);
        HttpRequest.requestGetToken(this, params, true, new BooleanStringListener() {
            @Override
            public void onCallBack(boolean isok,String access_token) {
                if(isok){
                    try{
//                        erWeiMa(SettingTestActivity.this,access_token);
//                        requestGetErWeiMa(access_token);
                        upload(access_token,path);
                    }catch (Exception e){
                        Log.e("www","======access_token===Exception====");
                    }

//                    ToastUtil.Toast("谢谢您的反馈");
                }
            }
        });
    }


    UploadTAsyncTask taaaask;
    private void upload(String access_token,String path){
        //有图片，要先调用图片接口ImageView imageView
        Map<String, String> paramh = new HashMap<String, String>();
        paramh.put("access_token", access_token);
        paramh.put("path", path);
        String width = width_edt.getText().toString().trim();
        if(!TextUtils.isEmpty(width)){
            paramh.put("width", width);
        }

        taaaask = new UploadTAsyncTask(MainActivity.this, paramh,  login_img, new UploadTAsyncTask.UploadTImagesListener() {

            @Override
            public void onUploadTImagesListener(int code, Bitmap bitmap) {
                if(code == 1 && bitmap != null){
//					imageLoader.clearDiskCache();
//					imageLoader.clearMemoryCache();
                    Toast.makeText(MainActivity.this,"生成成功,已存储在本地",Toast.LENGTH_LONG).show();
                    login_img.setImageBitmap(bitmap);
                }else{
                    Toast.makeText(MainActivity.this,"生成失败",Toast.LENGTH_LONG).show();
                }

            }
        });
        taaaask.execute("");
    }


}
