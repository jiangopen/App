package com.example.multiplegranarymanager.A_XinQiaoPackage.Activity;

import static com.kongzue.dialog.v2.DialogSettings.STYLE_KONGZUE;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.Pager.MainPagerAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.UpdateApp.CheckUpdate;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SelfDialogUplode;
import com.example.multiplegranarymanager.LoginActivity;
import com.example.multiplegranarymanager.R;

import com.example.multiplegranarymanager.Util.Util1;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.SelectDialog;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    //导航栏标题
    private final int[] TXT_TABLES = new int[]{R.string.menu_huainan_shili_tuqiao_tem,R.string.menu_huainan_shili_tuqiao_gas};
    //导航栏图片
    private final int[] IMG_TABLES = new int[]{R.drawable.zzz_function,R.drawable.zzz_history};
    public static String Token;
    public static String username;
    public static String password;
    public static String deviceType;
    private static final int NOTIFICATION_FLAG = 3;
    private PagerAdapter adapter;
    private NotificationManager manager;
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    public static Handler mHandler,updateHandler;
    public static ProgressDialog downloadDialog;
    private ArrayList<GranaryListBean.Data> GranaryList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        viewPager = findViewById(R.id.view_pages);
        tabLayout = findViewById(R.id.tab_layout);
        DialogSettings.style = STYLE_KONGZUE;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 1:
                        showUpdateDialog((String) msg.obj);
                        break;
                    case 2:
                        downloadDialog.dismiss();
                        util.showToast(MainActivity.this, (String) msg.obj);
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }
        };
        //app更新，需要重新获取服务器地址以及跟新地址
        initQuanXian();
        initView();
        initData();
        setTabs(tabLayout,getLayoutInflater(),TXT_TABLES,IMG_TABLES);
//        //更新权限
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            boolean hasInstallPermission = MainActivity.this.getPackageManager().canRequestPackageInstalls();
//            if (!hasInstallPermission) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
//                intent.setData(Uri.parse("package:" + MainActivity.this.getPackageName()));
//                MainActivity.this.startActivity(intent);
//            }
//        }
    }
    private static final int REQUEST_PERMISSION_CODE = 100;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予权限，进行更新操作
                Message msg = updateHandler.obtainMessage();
                msg.what = 1;
                updateHandler.sendMessage(msg);
                showDownloadDialog();
            } else {
                // 用户拒绝权限，进行相应处理，如提示用户
                Toast.makeText(this, "需要存储权限才能更新", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showUpdateDialog(String description) {
        SelfDialogUplode selfDialogUplode = new SelfDialogUplode(MainActivity.this);
        selfDialogUplode.Message(description);
        selfDialogUplode.setYesOnclickListener("立即更新",()->{
            // 请求权限，这里以存储权限为例
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_CODE);
            } else {
                // 已经有权限，直接执行更新操作
                Message msg = updateHandler.obtainMessage();
                msg.what = 1;
                updateHandler.sendMessage(msg);
                showDownloadDialog();
                selfDialogUplode.dismiss();
            }
//            Message msg = updateHandler.obtainMessage();
//            msg.what = 1;
//            updateHandler.sendMessage(msg);
//            showDownloadDialog();
//            selfDialogUplode.dismiss();
        });
        selfDialogUplode.setNoOnclickListener("稍后再说",()->{
            //关闭dialog
            Message msg = Message.obtain();
            msg.what = 3;
            updateHandler.sendMessage(msg);
            selfDialogUplode.dismiss();
        });
        selfDialogUplode.show();
    }
    private void showDownloadDialog() {
        downloadDialog = new ProgressDialog(MainActivity.this);
        downloadDialog.setIcon(R.drawable.zzz_download);
        downloadDialog.setTitle("下载进度");
        downloadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        downloadDialog.setMax(100);
        downloadDialog.setButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Message msg = Message.obtain();
                msg.what = 2;
                updateHandler.sendMessage(msg);
                dialog.dismiss();
            }
        });
        downloadDialog.show();
    }
    private void initData() {
        Gson gson = new Gson();
        NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
        newDownRawBody.setUrl("/granary-list");
        newDownRawBody.setMethod("GET");
        newDownRawBody.setHeaders(null);
        newDownRawBody.setQuery(null);
        newDownRawBody.setBody(null);
        String jsonData = gson.toJson(newDownRawBody);
        Log.d("zyq", "initData: "+jsonData);
        OkHttpUtilTWO.PostNewDownRawTWO("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=5", jsonData, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                GranaryListBean granaryListBean = null;
                try {
                    granaryListBean = gson.fromJson((String) result,GranaryListBean.class);
                } catch (JsonSyntaxException e) {
                    //初始化页卡
                    initPages();
                    e.printStackTrace();
                    util.showToast(MainActivity.this,"产品解析出错1");
                    return;
                }
                if (granaryListBean.getData().size()>0){
                    GranaryList.clear();
                    for (int i=0;i<granaryListBean.getData().size();i++){
                        GranaryList.add(granaryListBean.getData().get(i));
                    }
                    bundle.putParcelableArrayList("粮仓",GranaryList);
                    bundle.putString("账号",deviceType);
                    //初始化页卡
                    initPages();

                } else {
                    util.showToast(MainActivity.this,"当前无产品");
                    //初始化页卡
                    initPages();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                Log.d("zyq", "onReqFailed: "+errorMsg);
                //获取产品失败
                util.showToast(MainActivity.this,errorMsg);
                // 在这里添加跳转到登录界面的代码，例如：
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                // 关闭当前Activity或者执行其他必要的操作
                finish();
            }
        });
    }
    private void initPages() {
        adapter = new MainPagerAdapter(getSupportFragmentManager(),bundle);
        viewPager.setAdapter(adapter);
        //关联切换
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //取消平滑切换
                viewPager.setCurrentItem(tab.getPosition(),false);
//                titleText.setText(TXT_TITLES[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setTabs(TabLayout tabLayout, LayoutInflater layoutInflater, int[] txtTables, int[] imgTables) {
        for (int i=0;i<imgTables.length;i++){
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.item_main_menu,null);
            //使用自定义视图，目的是为了便于修改，也是用自带的视图
            tab.setCustomView(view);
            ImageView imageView = view.findViewById(R.id.img_tab);
            TextView textView = view.findViewById(R.id.txt_tab);
            imageView.setImageResource(imgTables[i]);
            textView.setText(txtTables[i]);
            tabLayout.addTab(tab);
        }
    }
    private void initView() {
        Intent intent = getIntent();
        deviceType = intent.getStringExtra("deviceType");
    }
    private void initQuanXian() {
        //开启更新线程
        new Thread(new CheckUpdate(getVersionName(), MainActivity.this)).start();
    }
    //得到应用程序的版本名
    private String getVersionName() {
        //原来管理手机的apk
        PackageManager pm = getPackageManager();
        //获得指定APK的功能清单文件
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(),0);
            String versionName = info.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
    //返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            dialog();
        }
        return false;
    }
    //判断程序是否在前台运行（当前运行的程序）
    private boolean isRunForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null){
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses){
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                return true;//程序运行在前台
            }
        }
        return false;
    }
    private void dialog() {
        View customView = LayoutInflater.from(this).inflate(R.layout.layout_back,null);
        TextView textView = customView.findViewById(R.id.tv_title_password);
        textView.setText("你确定要退出登录吗？");
        textView.setTextSize(20f);
        ImageView imageView = customView.findViewById(R.id.img_ico);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.back));
        imageView.setPadding(0,0,0,10);
        SelectDialog.show(MainActivity.this, null, null, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //将提醒框提醒取消
                if (manager != null) {
                    manager.cancel(NOTIFICATION_FLAG);
                }
                dialog.dismiss();
                finish();
            }
        }, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCustomView(customView).setCanCancel(true);
    }

    protected void onDestroy(){
        super.onDestroy();
        //将消息框提醒取消
        if (manager != null){
            manager.cancel(NOTIFICATION_FLAG);
        }
//        //取消所有定时
//        if (timer != null){
//            timer.cancel();
//            timer = null;
//        }
    }
    /**
     * 判断Activity是否Destroy
     *
     * @param mActivity
     * @return true:已销毁
     */
    public static boolean isDestroy(Activity mActivity) {
        if (mActivity == null ||
                mActivity.isFinishing() ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }
}
