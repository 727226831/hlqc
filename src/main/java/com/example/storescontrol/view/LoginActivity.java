package com.example.storescontrol.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storescontrol.BuildConfig;
import com.example.storescontrol.R;
import com.example.storescontrol.Url.Request;
import com.example.storescontrol.bean.LoginBean;
import com.example.storescontrol.Url.iUrl;
import com.example.storescontrol.databinding.ActivityLoginBinding;
import com.example.storescontrol.service.DemoIntentService;
import com.example.storescontrol.service.DemoPushService;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Random;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {
    TextView titleTv;
    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activityLoginBinding=DataBindingUtil.setContentView(this,R.layout.activity_login);

        titleTv=activityLoginBinding.getRoot().findViewById(R.id.tv_title);
        titleTv.setText(getResources().getText(R.string.app_name));
        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);

        activityLoginBinding.cbRemember.setChecked(sharedPreferences.getBoolean("isChecked",true));
        activityLoginBinding.etUsername.setText(sharedPreferences.getString("user",""));

        activityLoginBinding.tvVersion.setText("版本号"+BuildConfig.VERSION_NAME);

        if(activityLoginBinding.cbRemember.isChecked()){
            activityLoginBinding.etPassword.setText(sharedPreferences.getString("password",""));
        }
          activityLoginBinding.cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
                  SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                  editor.putBoolean("isChecked",activityLoginBinding.cbRemember.isChecked());
                  if (isChecked==false){
                      editor.putString("password", "");
                  }
                  editor.commit();

              }
          });

         activityLoginBinding.cbType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 if(b){
                     type=2;
                 }
             }
         });

            activityLoginBinding.bSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this,PortActivity.class));
                }
            });

        activityLoginBinding.bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                    login();


            }
        });

        sharedPreferences= getSharedPreferences("sp", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("port","").equals("")){
//            Request.BASEURL=Request.URL_WKF;
             Request.BASEURL=Request.URL_LD;
//             Request.BASEURL=Request.URL_AR;
            sharedPreferences.edit().putString("port",Request.BASEURL).commit();
        }
        Request.URL=sharedPreferences.getString("port","");
        if(Request.URL.equals(Request.URL_LD)){
            activityLoginBinding.cbType.setVisibility(View.VISIBLE);
        }


    }



    private void login() {



        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("methodname","UserLogin");
            jsonObject.put("usercode",activityLoginBinding.etUsername.getText().toString());
            jsonObject.put("userpassword",activityLoginBinding.etPassword.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("json object",obj);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(Request.URL).build();
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        iUrl login = retrofit.create(iUrl.class);
        retrofit2.Call<ResponseBody> data;
        if(Request.URL.equals(Request.URL_LD)) {
           data= login.login(body);
        }else {
            data= login.getMessage(body);
        }
        Log.i("url--->", data.request().url().toString());

        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    dialog.dismiss();
                    switch (response.code()){
                        case 200:
                            String result=response.body().string();

                            LoginBean resultBean=new Gson().fromJson(result,LoginBean.class);
                            if(resultBean.getResultcode().equals("200")) {
                                SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                                editor.putString("usercode",resultBean.getUsercode());
                                editor.putString("user",activityLoginBinding.etUsername.getText().toString());
                                editor.putString("password",activityLoginBinding.etPassword.getText().toString()).commit();
                                editor.putBoolean("isChecked",activityLoginBinding.cbRemember.isChecked());
                                editor.putString("userinfo",new Gson().toJson(resultBean));
                                editor.commit();

                                PushManager.getInstance().bindAlias(LoginActivity.this,resultBean.getAcccode(),"alias");
                                if(!resultBean.getVersionNumber().equals(BuildConfig.VERSION_NAME)){
                                    downloadByWeb(LoginActivity.this,Request.URL+"/upgrade/MMS_"+resultBean.getVersionNumber()+".apk");
                                }else {
                                    acccode=resultBean.getAcccode();
                                    usercode=resultBean.getUsercode();


                                    startActivity(new Intent(LoginActivity.this,IndexActivity.class));
                                    LoginActivity.this.finish();
                                }

                            }else {
                                Toast.makeText(LoginActivity.this,resultBean.getResultMessage(),Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 500:
                            Toast.makeText(LoginActivity.this,"服务器内部错误。",Toast.LENGTH_LONG).show();
                            break;
                        case 404:
                            Toast.makeText(LoginActivity.this,"所请求的页面不存在或已被删除！",Toast.LENGTH_LONG).show();
                            break;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });
    }

    private static void downloadByWeb(Context context, String apkPath) {
        Uri uri = Uri.parse(apkPath);
        //String android.intent.action.VIEW 比较通用，会根据用户的数据类型打开相应的Activity。如:浏览器,电话,播放器,地图
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }





}
