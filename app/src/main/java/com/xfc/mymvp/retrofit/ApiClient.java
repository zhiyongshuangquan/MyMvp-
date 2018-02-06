package com.xfc.mymvp.retrofit;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xfc.mymvp.BuildConfig;
import com.xfc.mymvp.config.Config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xfc
 * github:https://github.com/zhiyongshuangquan/
 * 个人博客：http://blog.csdn.net/qq_35115643
 */
public class ApiClient {
    public static final long DEFAULT_TIMEOUT = 15;
    private static ApiClient apiClient;
    private final static Object syncLock = new Object();

    private ApiClient() {
    }

    public static ApiClient getInstance() {
        if (apiClient == null) {
            synchronized (syncLock) {
                if (apiClient == null) {
                    apiClient = new ApiClient();
                }
            }
        }
        return apiClient;
    }

    /**
     * 提交表单数据
     */
    public ApiStores setForm() {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e("OKHTTP", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient okHttpClient = builder
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiStores apiService = retrofit.create(ApiStores.class);
        return apiService;
    }

    /**
     * 提交文件设置(多文件上传)
     */
    public static Map<String, RequestBody> setFile(Map<String, Object> map, String filename, File file) {
        Map<String, RequestBody> fileUpload = new HashMap<>();
        MediaType textType = MediaType.parse("text/plain");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            fileUpload.put(entry.getKey().toString(), RequestBody.create(textType, entry.getValue().toString()));
        }
        RequestBody fileio = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        fileUpload.put("file\"; filename=\"" + filename, fileio);
        return fileUpload;
    }

    /**
     * 单个文件上传
     */
    public static MultipartBody.Part setFile(File file) {
        //构建requestbody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //将resquestbody封装为MultipartBody.Part对象
        //string name 为对应上传的字段名
        MultipartBody.Part body = MultipartBody.Part.createFormData("myfiles", file.getName(), requestFile);
        return body;
    }

    /**
     * 提交json设置
     */
    public static RequestBody setBodyjson(String bodyjson) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyjson);
        return body;
    }

}
