package com.example.storescontrol.Url;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class Request {
  //  public  static String BASEURL="http://123456789.vaiwan.com";
    public  static String BASEURL;
   //public  static String BASEURL="http://192.168.200.20:8881";
   // public  static String BASEURL="http://192.168.1.85:8881";
    public static  String URL_WKF ="http://wkf.vaiwan.com";
    public static  String URL_AR="http://ar_wms.vaiwan.com";
 //   public static  String URL_LD="http://47.103.60.28:3000";
    public static  String URL_LD="http://192.168.0.101:3000";

    public  static String URL;
    public static Call<ResponseBody> getRequestbody(String obj) {

         OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(120, TimeUnit.SECONDS).
                readTimeout(120, TimeUnit.SECONDS).
                writeTimeout(120, TimeUnit.SECONDS).build();

        Retrofit retrofit=new Retrofit.Builder().client(client).baseUrl(URL).build();

        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        iUrl login = retrofit.create(iUrl.class);
        retrofit2.Call<ResponseBody> data = login.getMessage(body);
        return  data;
    }
}
