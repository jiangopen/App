package com.example.multiplegranarymanager.A_ShuChengPackage.Activity;

import static com.kongzue.dialog.v2.DialogSettings.STYLE_KONGZUE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multiplegranarymanager.A_ShuChengPackage.Adapter.MainPagerAdapter;
import com.example.multiplegranarymanager.A_ShuChengPackage.AllInterfaceClass;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product.DeviceInfo;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product.Product;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product.ProductInfo;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.ProductDetail.Data;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.ProductDetail.ProductDetail;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.UserAlertInfos.UserAlertInfos;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Body;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.DeviceInfos.DeviceInfos;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Headers;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Query;
import com.example.multiplegranarymanager.A_ShuChengPackage.SharedData.ShareProductDetail;
import com.example.multiplegranarymanager.A_ShuChengPackage.SharedData.SharedDeviceInfos;
import com.example.multiplegranarymanager.A_ShuChengPackage.UpdateApp.CheckUpdate;
import com.example.multiplegranarymanager.Bean.Login.LoginBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SelfDialogUplode;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.SelectDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    Button alarmBtn;
    TextView titleText;
    public static Badge badge;//提示图标
    public static int totalNumber = 0;
    public static Timer timer = new Timer();
    private TimerTask timerTask;
    private NotificationManager manager;
    public static SuoPingDialog suoPingDialog;
    private final long dingShi = 60;
    private int mIn = 0;
    private final int[] TXT_TABLES = new int[]{R.string.menu_huainan_shili_tuqiao_tem,R.string.menu_huainan_shili_tuqiao_gas};
    private final int[] IMG_TABLES = new int[]{R.drawable.zzz_function,R.drawable.zzz_history};
    private final String[] TXT_TITLES = new String[]{"功能界面","历史界面"};
    public static String Token;
    public static String pwd;
    public static String username;
    public static String deviceType;
    private static final int NOTIFICATION_FLAG = 2;
    private PagerAdapter adapter;
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    public static Handler mHandler,updateHandler;
    public static ProgressDialog downloadDialog;
    private ArrayList<String> productKey = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        ActionBar actionBar = getSupportActionBar();
        ShareProductDetail.ProductDetialList.clear();
        SharedDeviceInfos.DeviceInfosList.clear();
        alarmBtn = findViewById(R.id.back_btn);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        titleText = findViewById(R.id.title_text);
        if (actionBar != null){
            actionBar.hide();
        }
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
        initData(new InitDataFinishedListener() {
            @Override
            public void InitDataListener(List<String> result) {
                if (result != null && result.size() > 0) {
                    initDataTwo(result, new InitDataTwoFinishedListener() {
                        @Override
                        public void InitDataTwoListener(boolean success) {
                            suoPingDialog.dismiss();
                            if (success) {

                                //初始化页卡
                                initPager();
                                setTabs(tabLayout,getLayoutInflater(),TXT_TABLES,IMG_TABLES);
                                Log.d("zyq", "InitDataTwoListener: "+ShareProductDetail.ProductDetialList.size());
                            }
                        }
                    });
                } else {
                    suoPingDialog.dismiss();
                }
            }
        });
        //若此前有定时则重置
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                mIn = 0;
                AllInterfaceClass<UserAlertInfos> one = new AllInterfaceClass<>(UserAlertInfos.class);
                Body body = new Body();
                body.setPageIndex(1);
                body.setAsc(0);
                body.setPageSize(20);
                body.setUnRead(1);
                Headers headers = new Headers();
                headers.setTokenOffline(Token);
                NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
                newDownRawBodyTWO.setQuery(null);
                newDownRawBodyTWO.setMethod("POST");
                newDownRawBodyTWO.setHeaders(headers);
                newDownRawBodyTWO.setUrl("/userAlertInfos");
                newDownRawBodyTWO.setBody(body);
                one.PostOne(newDownRawBodyTWO, "MainActivity/timerTask", new AllInterfaceClass.PostCallBack<UserAlertInfos>() {
                    @Override
                    public void onSuccess(UserAlertInfos zyq) {
                        Log.d("jht", "UserAlertInfos: " + zyq.getData().getTotal());
                        mIn = mIn + zyq.getData().getTotal();
                        totalNumber = mIn;
                        badge.setBadgeNumber(totalNumber);
                        //若在后台运行，且在定时时间内有最新报警信息就信息框提醒
                        if (!isRunForeground()){
                            if (zyq.getData().getAlertInfo().get(0).getTime() > System.currentTimeMillis()/1000 - dingShi){
                                Notification(mIn);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String zyq) {
                        totalNumber = mIn;
                        badge.setBadgeNumber(totalNumber);
                        util.showToast(MainActivity.this,zyq);
                    }
                });
                //格式化时间
                SimpleDateFormat sdf = new SimpleDateFormat();
                //a为am/pm的标记
                sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
                //获取当前时间
                Date date = new Date();
                Log.e("alarm_receiver", "周期定时运行时间"+sdf.format(date));
            }
        };
        timer.schedule(timerTask,2*1000,dingShi*1000);
        alarmBtn.setOnClickListener(view -> {
            if (productKey.size()==0){
                util.showToast(MainActivity.this,"未找到产品");
                return;
            }
            Intent intent = new Intent(MainActivity.this,EvnelpeActivity.class);
//            intent.putExtra("keyandtype", (Serializable) KeyAndType);
            intent.putStringArrayListExtra("productKey",productKey);
            startActivity(intent);
        });
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
    //消息提示框
    private void Notification(int mIn) {
        Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.zzz_jinggao);
        Intent intent1 = new Intent(MainActivity.this,EvnelpeActivity.class);
//        intent1.putExtra("keyandtype", (Serializable) KeyAndType);
        intent1.putStringArrayListExtra("productKey",productKey);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notify = new Notification.Builder(MainActivity.this)
                .setSmallIcon(R.drawable.zzz_jinggao)//设置状态栏中的小图片，尺寸一般建议在24X24
                .setLargeIcon(bitmap)//这里也可以设置大图标
                .setTicker("报警")//设置显示的提示文字
                .setContentTitle("警报")//设置显示的标题
                .setContentText("您有新的警报信息，请及时处理！")//消息的详细内容
                .setContentIntent(pendingIntent)//关联PendingIntent
                .setNumber(mIn)//在TextView的右方显示的数字，可以在外部定义一个变量，点击累加setNumber(count),这时显示和
                .getNotification();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        manager = (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_FLAG,notify);
        bitmap.recycle();//回收bitmap
    }
    public interface InitDataFinishedListener {
        void InitDataListener(List<String> result);
    }
    private void initData(final InitDataFinishedListener listener) {
        suoPingDialog = new SuoPingDialog(this,"正在加载中，请稍等......");
        suoPingDialog.show();
        initToken(new OnInitTokenFinishedListener() {
            @Override
            public void OnInitTokenListener(boolean success) {
                if (success) {
                    AllInterfaceClass<Product> one = new AllInterfaceClass<>(Product.class);
                    Headers headers = new Headers();
                    headers.setTokenOffline(Token);
                    NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
                    newDownRawBodyTWO.setHeaders(headers);
                    newDownRawBodyTWO.setBody(null);
                    newDownRawBodyTWO.setUrl("/product");
                    newDownRawBodyTWO.setQuery(null);
                    newDownRawBodyTWO.setMethod("GET");
                    one.PostOne(newDownRawBodyTWO, "MainActiviyt/initData", new AllInterfaceClass.PostCallBack<Product>() {
                        @Override
                        public void onSuccess(Product zyq) {
                            Log.d("jht", "onSuccess: " + zyq.getData().getProductInfo().size());
                            for (ProductInfo data : zyq.getData().getProductInfo()) {
                                if (data.getTypeIdentify().equals("heznlc_zhuji_bingxing")) {
                                    productKey.add(data.getProductKey());
                                }
                            }
                            listener.InitDataListener(productKey);
                        }

                        @Override
                        public void onFailure(String zyq) {
                            suoPingDialog.dismiss();
                            listener.InitDataListener(null);
                            util.showToast(MainActivity.this,zyq);
                            Log.d("jht", "onFailure: " + zyq);
                            //初始化页卡
                            initPager();
                            setTabs(tabLayout,getLayoutInflater(),TXT_TABLES,IMG_TABLES);
                        }
                    });
                }
            }
        });
    }
    public interface InitDataTwoFinishedListener {
        void InitDataTwoListener(boolean success);
    }
    private void initDataTwo(List<String> ListKey, final InitDataTwoFinishedListener listener) {
        for (int i=0;i<ListKey.size();i++) {
            AllInterfaceClass<ProductDetail> one = new AllInterfaceClass<>(ProductDetail.class);
            Headers headers = new Headers();
            headers.setTokenOffline(Token);
            Query query = new Query();
            query.setProductKey(ListKey.get(i));
            NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
            newDownRawBodyTWO.setHeaders(headers);
            newDownRawBodyTWO.setBody(null);
            newDownRawBodyTWO.setQuery(query);
            newDownRawBodyTWO.setUrl("/productDetail");
            newDownRawBodyTWO.setMethod("GET");
            int finalI = i;
            one.PostOne(newDownRawBodyTWO, "MainActivity/initDataTwo", new AllInterfaceClass.PostCallBack<ProductDetail>() {
                @Override
                public void onSuccess(ProductDetail zyq) {
                    ShareProductDetail.ProductDetialList.add(zyq.getData());
                    DeviceInfos device = new DeviceInfos();
                    Data wind = new Data();
                    device.setSelected(false);
                    device.setProductKey(zyq.getData().getProductKey());
                    Log.d("jhtzyq", "onSuccess: "+zyq.getData().getExtraInfo().get("liangshizhonglei").getValue());
                    device.setGrainType((String) zyq.getData().getExtraInfo().get("liangshizhonglei").getValue());
                    for (DeviceInfo info : zyq.getData().getDeviceInfo()) {
                        if (info.getDeviceType().equals("honen_zj_bx")) {
                            device.setTemDeviceKey(info.getDeviceKey());
                            String data = String.valueOf(info.getExtraInfo().get("granaryId").getValue());
                            data = data.replaceAll("\\.0$", "");
                            //舒城的账号里面每个设备都是有温度的，而且其他的设备这三个参数都是一样的，所以这里我获取一遍
                            device.setGranaryName(String.valueOf(info.getExtraInfo().get("granaryName").getValue()));
                            device.setGranaryId(data);
                            device.setModuleName(String.valueOf(info.getExtraInfo().get("moduleName").getValue()));
                        } else if (info.getDeviceType().equals("honen_zj_bx_air_dust")) {
                            device.setGasDeviceKey(info.getDeviceKey());
                        } else if (info.getDeviceType().equals("honen_zj_bx_moisture")) {
                            device.setHumDeviceKey(info.getDeviceKey());
                        } else if (info.getDeviceType().equals("honen_zj_bx_smart_wind")) {
                            device.setWindDeviceKey(info.getDeviceKey());
                            device.setWindDeviceData(info);
                        }
                    }
                    SharedDeviceInfos.DeviceInfosList.add(device);
                    if (ShareProductDetail.ProductDetialList.size() == ListKey.size()) {
                        listener.InitDataTwoListener(true);
                    }
                }

                @Override
                public void onFailure(String zyq) {
                    util.showToast(MainActivity.this,zyq);
                    ShareProductDetail.ProductDetialList.add(null);
                    if (ShareProductDetail.ProductDetialList.size() == ListKey.size()) {
                        listener.InitDataTwoListener(true);
                    }
                }
            });
        }
    }
    private void setTabs(TabLayout tabLayout, LayoutInflater layoutInflater, int[] txtTables, int[] imgTables) {
        for (int i=0; i<imgTables.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.item_main_menu,null);
            //使用自定义视图，目的是为了便于修改，也可使用自带的视图
            tab.setCustomView(view);
            ImageView imgTitle = view.findViewById(R.id.img_tab);
            TextView tvTitle = view.findViewById(R.id.txt_tab);
            imgTitle.setImageResource(imgTables[i]);
            tvTitle.setText(txtTables[i]);
            tabLayout.addTab(tab);
        }
    }

    private void initPager() {
        bundle.putStringArrayList("productKey",productKey);
        adapter = new MainPagerAdapter(getSupportFragmentManager(),bundle);
        viewPager.setAdapter(adapter);
        //关联切换
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //取消平滑切换
                viewPager.setCurrentItem(tab.getPosition(),false);
                titleText.setText(TXT_TITLES[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public interface OnInitTokenFinishedListener {
        void OnInitTokenListener(boolean success);
    }
    public void initToken(final OnInitTokenFinishedListener listener){
        AllInterfaceClass<LoginBean> one = new AllInterfaceClass<>(LoginBean.class);
        Body body = new Body();
        body.setUsername(username);
        body.setPassword(pwd);
        NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
        newDownRawBodyTWO.setHeaders(null);
        newDownRawBodyTWO.setBody(body);
        newDownRawBodyTWO.setQuery(null);
        newDownRawBodyTWO.setUrl("/session");
        newDownRawBodyTWO.setMethod("POST");
        one.PostOne(newDownRawBodyTWO, "MainActivity/initView", new AllInterfaceClass.PostCallBack<LoginBean>() {
            @Override
            public void onSuccess(LoginBean zyq) {
                Token = zyq.getData().getToken();
                listener.OnInitTokenListener(true);
            }

            @Override
            public void onFailure(String zyq) {
                Token = null;
                listener.OnInitTokenListener(false);
                util.showToast(MainActivity.this,zyq);
            }
        });
    }
    private void initView() {
        Intent intent = getIntent();
        pwd = intent.getStringExtra("password");
        username = intent.getStringExtra("username");
        deviceType = intent.getStringExtra("deviceType");
        //信封红标提醒初始化
        badge = new QBadgeView(MainActivity.this).bindTarget(findViewById(R.id.point));
        badge.setBadgeNumber(0);
        badge.setExactMode(false);//false显示99+，true显示具体数值
        badge.setBadgeGravity(Gravity.END|Gravity.TOP);
        badge.setBadgeTextSize(12,true);
        badge.setBadgePadding(6,true);
        badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                if (dragState == STATE_SUCCEED){

                }
            }
        });
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
    //返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            dialog();
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
    private static final int REQUEST_PERMISSION_CODE = 100;
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
    protected void onDestroy(){
        super.onDestroy();
    }
}