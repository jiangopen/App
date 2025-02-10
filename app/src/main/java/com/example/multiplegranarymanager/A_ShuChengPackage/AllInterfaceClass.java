package com.example.multiplegranarymanager.A_ShuChengPackage;

import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.deviceType;

import android.content.Context;
import android.util.Log;

import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.NewDownRaw.CommandType03;
import com.example.multiplegranarymanager.A_ShuChengPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class AllInterfaceClass<JHT> {
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private Class<JHT> jhtClass;

    public AllInterfaceClass(Class<JHT> jhtClass) {
        this.jhtClass = jhtClass;
    }
    public void PostOne(NewDownRawBodyTWO newDownRawBodyTWO, String msg, PostCallBack<JHT> callback){
        Log.d("jht", "PostOne: "+newDownRawBodyTWO.getUrl());
        String data = gson.toJson(newDownRawBodyTWO);
        Log.d("jht", "PostOne: "+data);
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", data, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                JHT zyq = null;
                try {
                    // 通过反射获取JHT对应的Class对象
//                    Class<JHT> jhtClass = (Class<JHT>) getGenericClass(AllInterfaceClass.this.getClass());
                    // 创建TypeToken来表示JHT的具体类型
                    zyq = gson.fromJson((String) result, jhtClass);
                    if (callback != null) {
                        callback.onSuccess(zyq);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    // 反序列化失败，调用回调接口的onFailure方法，传递错误信息
                    if (callback!= null) {
                        callback.onFailure("错误"+msg);
                    }
                    return;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                // 网络请求失败，调用回调接口的onFailure方法，传递网络请求失败的错误信息
                if (callback!= null) {
                    callback.onFailure("网络请求失败: " + errorMsg);
                }
            }
        });
    }
    public void PostTwo(NewDownRawBodyTWO newDownRawBodyTWO, String msg, PostCallBack<JHT> callback){
        String data = gson.toJson(newDownRawBodyTWO);
        Log.d("jht", "PostTwo: " + data);
        OkHttpUtilTWO.PostNewDownRawTWO("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", data, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                JHT zyq = null;
                try {
                    // 通过反射获取JHT对应的Class对象
//                    Class<JHT> jhtClass = (Class<JHT>) getGenericClass(AllInterfaceClass.this.getClass());
                    // 创建TypeToken来表示JHT的具体类型
                    zyq = gson.fromJson((String) result, jhtClass);
                    if (callback != null) {
                        callback.onSuccess(zyq);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    // 反序列化失败，调用回调接口的onFailure方法，传递错误信息
                    if (callback!= null) {
                        callback.onFailure("错误"+msg);
//                        Log.d("jht", "onReqSuccess: "+"错误"+msg);
                    }
                    return;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                // 网络请求失败，调用回调接口的onFailure方法，传递网络请求失败的错误信息
                if (callback!= null) {
                    callback.onFailure("网络请求失败: " + errorMsg);
                }
            }
        });
    }
    public void PostThr(NewDownRawBodyTWO newDownRawBodyTWO, String msg, PostCallBack<JHT> callback){
        String data = new GsonBuilder().disableHtmlEscaping().create().toJson(newDownRawBodyTWO);
        Log.d("jht", "PostOne: "+data);
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", data, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                JHT zyq = null;
                Log.d("zyq", "onReqSuccess: "+result);
                try {
                    // 通过反射获取JHT对应的Class对象
//                    Class<JHT> jhtClass = (Class<JHT>) getGenericClass(AllInterfaceClass.this.getClass());
                    // 创建TypeToken来表示JHT的具体类型
                    zyq = gson.fromJson((String) result, jhtClass);
                    if (callback != null) {
                        callback.onSuccess(zyq);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    // 反序列化失败，调用回调接口的onFailure方法，传递错误信息
                    if (callback!= null) {
                        callback.onFailure("错误"+msg);
                    }
                    return;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                // 网络请求失败，调用回调接口的onFailure方法，传递网络请求失败的错误信息
                if (callback!= null) {
                    callback.onFailure("网络请求失败: " + errorMsg);
                }
            }
        });
    }
    public String PostFou(NewDownRawBodyTWO newDownRawBodyTWO, String msg){
        String data = new GsonBuilder().disableHtmlEscaping().create().toJson(newDownRawBodyTWO);
        Log.d("jht", "PostOne: "+data);
        final String[] res = new String[1];
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", data, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                res[0] = (String) result;
            }

            @Override
            public void onReqFailed(String errorMsg) {
                res[0] = null;
            }
        });
        return res[0];
    }
    // 辅助方法，用于获取泛型的实际类型Class对象（这里假设简单的直接继承泛型类情况）
//    private static Type getGenericClass(Class<?> clazz) {
//        Type genericSuperclass = clazz.getGenericSuperclass();
//        if (genericSuperclass instanceof ParameterizedType) {
//            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
//            return parameterizedType.getActualTypeArguments()[0];
//        }
//        return Object.class;
//    }

    public interface PostCallBack<JHT> {
        void onSuccess(JHT zyq);
        void onFailure(String zyq);
    }
    public interface PostCallbackTwo<JHT> {
        CommandType03 onSuccess(JHT zyq);
    }
}
