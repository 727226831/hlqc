package com.example.storescontrol.url;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface iUrl {

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/Handler.ashx")
    Call<ResponseBody> getMessage(@Body RequestBody info);   // 请求体RequestBody 类型

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/login")
    Call<ResponseBody> login(@Body RequestBody info);   // 请求体RequestBody 类型

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/tasklist")
    Call<ResponseBody> tasklist(@Body RequestBody info);   // 请求体RequestBody 类型
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头

    @POST("/taskApproval")
    Call<ResponseBody> taskApproval(@Body RequestBody info);   // 请求体RequestBody 类型
    @POST("/updateMoq")
    Call<ResponseBody> updateMoq(@Body RequestBody info);

}
