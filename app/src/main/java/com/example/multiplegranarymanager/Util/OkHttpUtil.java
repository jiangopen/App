package com.example.multiplegranarymanager.Util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.nio.charset.StandardCharsets;


import com.example.multiplegranarymanager.Bean.Login.LoginBean;
import com.example.multiplegranarymanager.Bean.Login.LoginBean2;
import com.example.multiplegranarymanager.Bean.Login.LoginBeanTwo;
import com.example.multiplegranarymanager.Bean.Login.LoginNewDownRawBean;
import com.example.multiplegranarymanager.Bean.Login.LoginNewDownRawBean2;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("appli/json; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    //http://49.4.8.167:30071/aplus_api和http://api.ahusmart.com效果一样，但是后者是云端接口，前者是网页
    private static final String BASE_URL = "https://api.ahusmart.com";//请求接口根地址http://api.ahusmart.com//http://49.4.69.205:8080//http://49.4.8.167:30002/aplus_api
    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST_JSON = 1;//post请求参数为json
    private OkHttpClient mOkHttpClient;//okHttpClient 实例
    private Handler okHttpHandler;//全局处理子线程和M主线程通信
    private static final String TAG = OkHttpUtil.class.getSimpleName();
    private static volatile OkHttpUtil mInstance;//单利引用

    /**
     *
     * 初始化RequestManager
     */
    public OkHttpUtil() {
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
    public static OkHttpUtil getInstance() {
        OkHttpUtil inst = mInstance;
        //第一次为了不必要的同步
        if (inst == null) {
            synchronized (OkHttpUtil.class) {
                inst = mInstance;
                //第二次是在OkHttpUtil等于null的时候创建实例
                if (inst == null) {
                    inst = new OkHttpUtil();
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    public static void LoginPost1(String Url, String Data1, final ReqCallBack callBack) {
        getInstance().LoginPost(Url, Data1, callBack);
    }

    private void LoginPost(String Url, String Data1, final ReqCallBack callBack) {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, Data1);
        String requestUrl = String.format("%s/%s", BASE_URL, Url);
        final Request request = new Request.Builder()
                .url(requestUrl).post(body).build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {//异步，操作在子线程
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack("连接失败，请检查网络", callBack);
                Log.e(TAG, e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    String result = response.body().string();
                    if (response.code() == 200) {
                        Log.e(TAG, "LoginPost ----->" + result + "---" + response);
                        Gson gson = new Gson();
                        LoginBean javaBean = gson.fromJson(result, LoginBean.class);
                        if (javaBean.getMsg().equals("ok") || javaBean.getCode() == 200) {
                            String Data = javaBean.getData().getToken();
                            successCallBack(Data, callBack);
                        } else {
                            failedCallBack(javaBean.getMsg(), callBack);
                        }
                        // }
                    } else {
                        Log.e(TAG, "服务器错误");
                        failedCallBack("服务器错误", callBack);
                    }
                } catch (IOException e) {
                    failedCallBack(e.toString(), callBack);
                }
            }
        });
        //return null;
    }

    public static void Post1(String Url, String Data1, String token, final ReqCallBack callBack) {
        getInstance().Post(Url, Data1, token, callBack);
    }

    public void Post2(String Data1,final ReqCallBack callBack){
        String requestUrl = "https://subapi.ahusmart.com/smart-wind/v1/start";
        MediaType MEDIA_TYPE_JSON_OTHER = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON_OTHER, Data1);
//        final Request request = addHeaders(null).url(requestUrl).post(body).build();
        final Request request = new Request.Builder().url(requestUrl).post(body).build();
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
                    if (response.code() == 200) {
                        Log.e(TAG, "POSTresponse ----->" + string);
                        Gson gson = new Gson();
                        LoginBean2 javaBean = gson.fromJson(string, LoginBean2.class);
                        if (javaBean.getMsg().equals("ok") || javaBean.getCode() == 200 || javaBean.getStatus().equals("success")) {
                            successCallBack(string, callBack);
                        } else {
                            failedCallBack(javaBean.getMsg(), callBack);
                        }
                    } else {
                        Log.e(TAG, "Post2服务器错误"+response.code());
                        failedCallBack("服务器错误", callBack);
                    }
                } catch (IOException e) {
                    failedCallBack(e.toString(), callBack);
                }
            }
        });
    }
    public static void Post3(String Data1, final ReqCallBack callBack){
        getInstance().Post2(Data1,callBack);
    }
    private void Post(String Url, String Data1, String token, final ReqCallBack callBack) {
        //byte[] Data = Data1.getBytes();
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, Data1);
        String requestUrl = String.format("%s/%s", BASE_URL, Url);
        final Request request = addHeaders(token).url(requestUrl).post(body).build();
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
                    if (response.code() == 200) {
                        Log.e(TAG, "POSTresponse ----->" + string);
                        Gson gson = new Gson();
                        LoginBean javaBean = gson.fromJson(string, LoginBean.class);
                        if (javaBean.getMsg().equals("ok") || javaBean.getCode() == 200 || javaBean.getStatus().equals("success")) {
                            successCallBack(string, callBack);
                        } else {
                            failedCallBack(javaBean.getMsg(), callBack);
                        }
                    } else {
                        Log.e(TAG, "服务器错误");
                        failedCallBack("服务器错误", callBack);
                    }
                } catch (IOException e) {
                    failedCallBack(e.toString(), callBack);
                }
            }
        });
    }
    public static void PostNewDownRaw(String Url, String Data1, String token, final ReqCallBack callBack){
        getInstance().NewDownRaw(Url, Data1, token, callBack);
    }
    private void NewDownRaw(String Url, String Data1, String token, final ReqCallBack callBack){
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, Data1);
        String requestUrl = String.format("%s/%s", BASE_URL, Url);
        Log.e("zyq", "requestUrl "+requestUrl);
        final Request request = addHeaders(token).url(requestUrl).post(body).build();
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

                    if (response.code() == 200) {
                        Log.e(TAG, "POSTresponse ----->" + string);
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
                        Log.e("zyq", "服务器错误"+callBack);
                        failedCallBack("服务器错误", callBack);
                    }
                } catch (IOException e) {
                    failedCallBack(e.toString(), callBack);
                }
            }
        });
    }
    public static void PostFIRST(String Url, String Data1, String token, final ReqCallBack callBack) {
        getInstance().PostOne(Url, Data1, token, callBack);
    }
    private void PostOne(String Url, String Data1, String token, final ReqCallBack callBack) {
        //byte[] Data = Data1.getBytes();
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, Data1);
        String requestUrl = String.format("%s/%s", BASE_URL, Url);
        final Request request = addHeaders(token).url(requestUrl).post(body).build();
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
                    if (response.code() == 200) {
                        Log.e(TAG, "POSTresponse ----->" + string);
                        Gson gson = new Gson();
                        LoginBeanTwo javaBean = gson.fromJson(string, LoginBeanTwo.class);
                        if (javaBean.getMsg().equals("ok") || javaBean.getCode() == 200) {
                            successCallBack(string, callBack);
                        } else {
                            failedCallBack(javaBean.getMsg(), callBack);
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
    public static void Put(String Url, String Data1, String token, final ReqCallBack callBack) {
        getInstance().Put1(Url, Data1, token, callBack);
    }

    private void Put1(String Url, String Data1, String token, final ReqCallBack callBack) {
        //byte[] Data = Data1.getBytes();
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, Data1);
        String requestUrl = String.format("%s/%s", BASE_URL, Url);
        final Request request = addHeaders(token).url(requestUrl).put(body).build();
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
                    if (response.code() == 200) {
                        Log.e(TAG, "PUTresponse ----->" + string);
                        Gson gson = new Gson();
                        LoginBean javaBean = gson.fromJson(string, LoginBean.class);
                        if (javaBean.getMsg().equals("ok") || javaBean.getCode() == 200) {
                            successCallBack(string, callBack);
                        } else {
                            failedCallBack(javaBean.getMsg(), callBack);
                        }
                    } else {
                        Log.e(TAG, "服务器错误");
                        failedCallBack("服务器错误", callBack);
                    }
                } catch (IOException e) {
                    failedCallBack(e.toString(), callBack);
                }
            }
        });
    }

    public static void Get1(String Url, String token, final ReqCallBack callBack) {
        getInstance().Get(Url, token, callBack);
    }

    private void Get(String Url, String token, final ReqCallBack callBack) {
        Object BASE_URLs = "http://49.4.8.167:30071";
        String requestUrl = String.format("%s/%s", BASE_URL, Url);
        final Request request = addHeaders(token).url(requestUrl).build();
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
                    if (response.code() == 200) {
                        Log.e(TAG, "GETresponse ----->" + string);
                        Gson gson = new Gson();
                        LoginBean javaBean = gson.fromJson(string, LoginBean.class);
                        if (javaBean.getMsg().equals("ok") || javaBean.getCode() == 200) {
                            successCallBack(string, callBack);
                        } else {
                            failedCallBack(javaBean.getMsg(), callBack);
                        }

                    } else {
                        Log.e(TAG, "GET服务器错误");
                        failedCallBack("GET服务器错误", callBack);
                    }
                } catch (IOException e) {
                    failedCallBack(e.toString(), callBack);
                }
            }
        });
    }

    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders(String token) {
        Request.Builder builder = new Request.Builder().addHeader("token",token);
        return builder;
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
}
