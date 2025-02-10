package com.example.multiplegranarymanager.A_ShuChengPackage.Util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.multiplegranarymanager.Bean.Login.LoginBean;
import com.example.multiplegranarymanager.Bean.Login.LoginNewDownRawBean;
import com.example.multiplegranarymanager.Bean.Login.LoginNewDownRawBean2;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtilTWO {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("appli/json; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_JSON_TWO = MediaType.parse("application/json");//mdiatype 这个需要和服务端保持一致
    private static final String BASE_URL_TWO = "https://subapi.ahusmart.com";
    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST_JSON = 1;//post请求参数为json
    private OkHttpClient mOkHttpClient;//okHttpClient 实例
    private Handler okHttpHandler;//全局处理子线程和M主线程通信
    private static final String TAG = com.example.multiplegranarymanager.Util.OkHttpUtil.class.getSimpleName();
    private static volatile OkHttpUtilTWO mInstance;//单利引用
    /**
     *
     * 初始化RequestManager
     */
    public OkHttpUtilTWO() {
        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(15, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        //初始化Handler
        okHttpHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    //双重检查模式(DCL)优点：资源利用率高，缺点：第一次加载慢
    public static OkHttpUtilTWO getInstance() {
        OkHttpUtilTWO inst = mInstance;
        //第一次为了不必要的同步
        if (inst == null) {
            synchronized (com.example.multiplegranarymanager.Util.OkHttpUtil.class) {
                inst = mInstance;
                //第二次是在OkHttpUtil等于null的时候创建实例
                if (inst == null) {
                    inst = new OkHttpUtilTWO();
                    mInstance = inst;
                }
            }
        }
        return inst;
    }
    public static void PostNewDownRaw(String Url, String Data1,final ReqCallBack callBack){
        getInstance().NewDownRaw(Url, Data1, callBack);
    }
    private void NewDownRaw(String Url, String Data1, final ReqCallBack callBack){
        Log.d("jht", "NewDownRaw: 1111    "+Data1);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON_TWO, Data1);
        String requestUrl = String.format("%s/%s", BASE_URL_TWO, Url);
        Log.d("zyq", "NewDownRawTWO: "+requestUrl);
        final Request request = new Request.Builder().url(requestUrl).method("POST",body).addHeader("Content-Type", "application/json").build();
        Log.d("zyq", "NewDownRawTWO: "+request);
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack("访问失败", callBack);
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string = response.body().string();
                    Log.d("zyq", "NewDownRawTWO: "+string);
                    if (response.code() == 200) {
                        Log.e(TAG, "POSTresponse2 ----->" + string);
                        Gson gson = new Gson();
                        try {
                            LoginBean javaBean = gson.fromJson(string, LoginBean.class);
                            if (javaBean.getMsg().equals("ok") || javaBean.getCode() == 200) {
                                successCallBack(string, callBack);
                            } else {
                                failedCallBack(javaBean.getMsg(), callBack);
                            }
                        } catch (JsonSyntaxException e) {
                            try {
                                LoginNewDownRawBean2 javaBean2 = gson.fromJson(string, LoginNewDownRawBean2.class);
                                if (javaBean2.getCode() == 20080) {
                                    successCallBack(string, callBack);
                                } else {
                                    failedCallBack(javaBean2.getMsg(), callBack);
                                }
                            } catch (JsonSyntaxException w){
                                failedCallBack("数据解析异常", callBack);
                            }
                        }
                    } else {
                        Log.e(TAG, "服务器错误"+callBack);
                        failedCallBack("服务器错误", callBack);
                    }
                } catch (IOException e) {
                    failedCallBack(e.toString(), callBack);
                }
            }
        });
    }
    public static void PostNewDownRawTHREE(String Url, String Data1,final ReqCallBack callBack){
        getInstance().NewDownRawTHREE(Url, Data1, callBack);
    }
    private void NewDownRawTHREE(String Url, String Data1, final ReqCallBack callBack){
        Log.d("jht", "NewDownRaw: 1111    "+Data1);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON_TWO, Data1);
        String requestUrl = String.format("%s/%s", BASE_URL_TWO, Url);
        Log.d("zyq", "NewDownRawTWO: "+requestUrl);
        final Request request = new Request.Builder().url(requestUrl).method("POST",body).addHeader("Content-Type", "application/json").build();
        Log.d("zyq", "NewDownRawTWO: "+request);
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack("访问失败", callBack);
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string = response.body().string();
                    Log.d("zyq", "NewDownRawTWO: "+string);
                    if (response.code() == 200) {
                        Log.e(TAG, "POSTresponse2 ----->" + string);
                        Gson gson = new Gson();
                        try {
                            LoginBean javaBean = gson.fromJson(string, LoginBean.class);
                            if (javaBean.getMsg().equals("ok") || javaBean.getCode() == 200) {
                                successCallBack(string, callBack);
                            } else {
                                failedCallBack(javaBean.getMsg(), callBack);
                            }
                        } catch (JsonSyntaxException e) {
                            try {
                                LoginNewDownRawBean2 javaBean2 = gson.fromJson(string, LoginNewDownRawBean2.class);
                                if (javaBean2.getCode() == 20080) {
                                    successCallBack(string, callBack);
                                } else {
                                    failedCallBack(javaBean2.getMsg(), callBack);
                                }
                            } catch (JsonSyntaxException w){
                                failedCallBack("数据解析异常", callBack);
                            }
                        }
                    } else {
                        Log.e(TAG, "服务器错误"+callBack);
                        failedCallBack("服务器错误", callBack);
                    }
                } catch (IOException e) {
                    failedCallBack(e.toString(), callBack);
                }
            }
        });
    }
    public static void PostNewDownRawTWO(String Url, String Data1,final ReqCallBack callBack){
        getInstance().NewDownRawTWO(Url, Data1, callBack);
    }
    private void NewDownRawTWO(String Url, String Data1, final ReqCallBack callBack){
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON_TWO, Data1);
        String requestUrl = String.format("%s/%s", BASE_URL_TWO, Url);
        Log.d("zyq", "NewDownRawTWO: "+requestUrl);
        final Request request = new Request.Builder().url(requestUrl).method("POST",body).addHeader("Content-Type", "application/json").build();
        Log.d("zyq", "NewDownRawTWO: "+request);
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack("访问失败", callBack);
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string = response.body().string();
                    Log.d("zyq", "NewDownRawTWO: "+string);
                    if (response.code() == 200) {
                        Log.e(TAG, "POSTresponse2 ----->" + string);
                        Gson gson = new Gson();
                        try {
                            LoginNewDownRawBean javaBean = gson.fromJson(string, LoginNewDownRawBean.class);
                            if (javaBean.getMsg().equals("ok") || javaBean.getCode() == 200) {
                                successCallBack(string, callBack);
                            } else {
                                failedCallBack(javaBean.getMsg(), callBack);
                            }
                        } catch (JsonSyntaxException e) {
                            try {
                                LoginNewDownRawBean2 javaBean2 = gson.fromJson(string, LoginNewDownRawBean2.class);
                                if (javaBean2.getCode() == 20080) {
                                    successCallBack(string, callBack);
                                } else {
                                    failedCallBack(javaBean2.getMsg(), callBack);
                                }
                            } catch (JsonSyntaxException w){
                                failedCallBack("数据解析异常", callBack);
                            }
                        }
                    } else {
                        Log.e(TAG, "服务器错误"+callBack);
                        failedCallBack("服务器错误", callBack);
                    }
                } catch (IOException e) {
                    failedCallBack(e.toString(), callBack);
                }
            }
        });
    }
    public static void PostNewDownRawFOUR(String Url, String Data1,final ReqCallBackTWO callBack){
        getInstance().NewDownRawFOUR(Url, Data1, callBack);
    }
    private void NewDownRawFOUR(String Url, String Data1, final ReqCallBackTWO callBack){
        Log.d("jht", "NewDownRaw: 1111    "+Data1);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, Data1);
        String requestUrl = String.format("%s/%s", BASE_URL_TWO, Url);
        Log.d("zyq", "NewDownRawTWO: "+requestUrl);
        final Request request = new Request.Builder().url(requestUrl).method("POST",body).addHeader("Content-Type", "application/json").build();
        Log.d("zyq", "NewDownRawTWO: "+request);
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                failedCallBack("访问失败", callBack);
                callBack.onSuccess(null);
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string = response.body().string();
                    Log.d("zyq", "NewDownRawTWO: "+string);
                    if (response.code() == 200) {
                        Log.e(TAG, "POSTresponse2 ----->" + string);
                        Gson gson = new Gson();
                        try {
                            LoginBean javaBean = gson.fromJson(string, LoginBean.class);
                            if (javaBean.getMsg().equals("ok") || javaBean.getCode() == 200) {
//                                successCallBack(string, callBack);
                                callBack.onSuccess(javaBean);
                            } else {
                                callBack.onSuccess(javaBean);
                            }
                        } catch (JsonSyntaxException e) {
                            try {
                                LoginNewDownRawBean2 javaBean2 = gson.fromJson(string, LoginNewDownRawBean2.class);
                                if (javaBean2.getCode() == 20080) {
                                    callBack.onSuccess(javaBean2);
                                } else {
                                    callBack.onSuccess(javaBean2);
                                }
                            } catch (JsonSyntaxException w){
                                callBack.onSuccess(null);
                            }
                        }
                    } else {
                        Log.e(TAG, "服务器错误"+callBack);
                        callBack.onSuccess(null);
                    }
                } catch (IOException e) {
                    callBack.onSuccess(null);
                }
            }
        });
    }
    /**
     * 统一同意处理成功信息
     *
     * @param result
     * @param callBack
     * @param <T>
     */
    private <T> void successCallBack(final T result, final ReqCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onReqSuccess(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    /**
     * 统一处理失败信息
     *
     * @param errorMsg
     * @param callBack
     * @param <T>
     */
    private <T> void failedCallBack(final String errorMsg, final ReqCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onReqFailed(errorMsg);
                }
            }
        });
    }
    public interface ReqCallBack<T> {
        /**
         * 响应成功
         *
         * @return
         */
        void onReqSuccess(T result) throws JSONException;

        /**
         * 响应失败
         */
        void onReqFailed(String errorMsg);
    }
    public interface ReqCallBackTWO<T> {
        T onSuccess(T result);
    }
}
