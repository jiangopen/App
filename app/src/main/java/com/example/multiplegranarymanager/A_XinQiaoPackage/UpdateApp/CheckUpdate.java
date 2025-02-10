package com.example.multiplegranarymanager.A_XinQiaoPackage.UpdateApp;


import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.downloadDialog;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class CheckUpdate implements Runnable {
    //private static  final Logger log = LogSetter.Logset(CheckUpdate.class);
    private String version, VersionName;
    private String description;
    private String appName = "粮情管理系统";
    private String fileName;
    private String equipment, md5;
    private Context context;
    private Boolean quitdownload = false;
    private int id;

    public CheckUpdate(String VersionName, Context context) {
        this.VersionName = VersionName;
        this.context = context;
    }

    public void run() {
        // TODO Auto-generated method stub
        // 创建该线程的Looper对象，用于接收消息
        Looper.prepare();
        // 线程的looper创建的handler表示消息接收者是子线程
        final Looper looper = Looper.myLooper();
        MainActivity.updateHandler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        updateDownload();
                        //looper.quit();
                        break;
                    case 2:
                        quitdownload = true;
                        looper.quit();
                        break;
                    case 3:
                        looper.quit();
                        break;
                    default:
                        break;
                }
            }
        };
        Message msg = MainActivity.mHandler.obtainMessage();
        try {
            //先检测apk
            //URL url = new URL(context.getString(R.string.serverurl));
            URL url = new URL("https://app.ahusmart.com/v2/check?projectName=粮情管理系统&filetype=apk");//https://app.ahusmart.com/v2/check?projectName=轴温检测系统&filetype=apk
            // 联网
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(4000);
            int code = conn.getResponseCode();
            Log.d("1", "更新");
            if (code == 200) {
                InputStream is = conn.getInputStream();
                // 把流转化为string
                String in = StreamTools.readFromStream(is);
                //log.info("联网成功，检测更新！");
                // json解析
                JSONObject json = new JSONObject(in);
                version = (String) json.get("version");//version///{"version":"1.0.0","lastTime":1599118300,"log":"1.修复内存泄漏问题\\n2.优化界面","filename":"hekj.apk"}
                Log.d("zyq", "run: "+version);
                description = (String) json.get("log");//description
                fileName = (String) json.get("filename");

                // 校验是否有新版本
                if (compareVersion(VersionName, version)) {
                    // 没新版本apk，查找zip

                } else {
                    // 有新版本apk，弹出一个升级对话框
                    //给更新内容添加回车换行
                    StringBuffer sb = new StringBuffer();
                    while (description.contains("\\n")) {
                        int index = description.indexOf("\\n");
                        sb.append(description.substring(0, index));
                        sb.append("\n");
                        description = description.substring(index + 2);
                    }
                    sb.append(description);

                    //**********下载新版zip热更新
                    msg.what = 1;
                    msg.obj = sb.toString();
                    //log.info("有新版本");
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //log.error(e);
            msg.what = 3;
//            msg.obj = "连接服务器检查更新失败";
            e.printStackTrace();
        } finally {
            MainActivity.mHandler.sendMessage(msg);
        }
        // 循环从MessageQueue中取消息。
        Looper.loop();
    }
    private boolean compareVersion(String localVersion, String serverVersion) {
        String[] localParts = localVersion.split("\\.");
        String[] serverParts = serverVersion.split("\\.");
        int length = Math.max(localParts.length, serverParts.length);
        for (int i = 0; i < length; i++) {
            int localPart = i < localParts.length? Integer.parseInt(localParts[i]) : 0;
            int serverPart = i < serverParts.length? Integer.parseInt(serverParts[i]) : 0;
            if (localPart < serverPart) {
                return false;
            } else if (localPart > serverPart) {
                return true;
            }
        }
        return true;
    }
    private OkHttpClient client = new OkHttpClient();
    private boolean quitDownload = false;
    public void updateDownload() {
        // 下载APK，并且替换安装
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String url = "http://app.ahusmart.com/v2/download?projectName=" + appName + "&version=" + version + "&filename=" + fileName;
            File file = new File(context.getExternalFilesDir(null), "SmartLock.apk");

            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Message msg = MainActivity.mHandler.obtainMessage();
                    msg.what = 2;
                    msg.obj = "下载失败";
                    MainActivity.mHandler.sendMessage(msg);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        int REQUEST_WRITE_STORAGE = 1;
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
                        return;
                    }

                    try (ResponseBody body = response.body();
                         InputStream inputStream = body.byteStream();
                         FileOutputStream outputStream = new FileOutputStream(file)) {

                        if (body == null) {
                            throw new IOException("Response body is null");
                        }

                        byte[] buffer = new byte[4096];
                        long fileSize = body.contentLength();
                        long totalRead = 0;
                        int read;
                        while ((read = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, read);
                            totalRead += read;

                            int progress = (int) (totalRead * 100 / fileSize);
                            downloadDialog.setProgress(progress);

                            if (quitDownload) {
                                quitDownload = false;
                                return;
                            }
                        }

                        installAPK(file);
                        Message msg = MainActivity.mHandler.obtainMessage();
                        msg.what = 2;
                        msg.obj = "下载成功";
                        MainActivity.mHandler.sendMessage(msg);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Message msg = MainActivity.mHandler.obtainMessage();

                        msg.what = 2;
                        msg.obj = "下载失败";
                        MainActivity.mHandler.sendMessage(msg);
                    }
                }
            });
        } else {
            Message msg = MainActivity.mHandler.obtainMessage();
            msg.what = 2;
            msg.obj = "请插入sdcard卡";
            MainActivity.mHandler.sendMessage(msg);
        }
    }

    /**
     * 安装APK
     *
     * @param t
     */
    private void installAPK(File t) {
        Uri uri = null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 确保文件存在
        if (!t.exists()) {
            Toast.makeText(context, "APK 文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        // 针对 Android 7.0 及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", t);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(t);
        }

        // 设置数据和类型
        intent.setDataAndType(uri, "application/vnd.android.package-archive");

        // 尝试启动活动
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "没有找到安装应用的程序", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "安装过程中出现问题", Toast.LENGTH_SHORT).show();
        }
    }
}
