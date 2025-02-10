package com.example.multiplegranarymanager.A_TestPackage.Activity;

import static com.kongzue.dialog.v2.DialogSettings.STYLE_KONGZUE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationManager;
import android.app.ProgressDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.Pager.MainPagerAdapter;
import com.example.multiplegranarymanager.Bean.Product.ProductBean;
import com.example.multiplegranarymanager.Bean.Product.ProductInfo;
import com.example.multiplegranarymanager.Body.ProductBody;
import com.example.multiplegranarymanager.Dialog.SelfDialogUplode;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.A_TestPackage.UpdateApp.CheckUpdate;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kongzue.dialog.v2.DialogSettings;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    //导航栏标题
    private final int[] TXT_TABLES = new int[]{R.string.menu_huainan_shili_tuqiao_tem,R.string.menu_huainan_shili_tuqiao_gas,R.string.menu_huainan_shili_tuqiao_air};
    //导航栏图片
    private final int[] IMG_TABLES = new int[]{R.drawable.zzz_function,R.drawable.zzz_history,R.drawable.zzz_setting};
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
    private ArrayList<ProductBody> productBodyList = new ArrayList<>();
    private ArrayList<String> productKeyList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pages);
        tabLayout = findViewById(R.id.tab_layout);
        //Dialog样式
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
        initQuanXian();
        initView();
        initData();
        //更新权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean hasInstallPermission = MainActivity.this.getPackageManager().canRequestPackageInstalls();
            if (!hasInstallPermission) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                intent.setData(Uri.parse("package:" + MainActivity.this.getPackageName()));
                MainActivity.this.startActivity(intent);
            }
        }
    }

    private void initData() {
        if (deviceType.equals("LQBXZJ")){
            productBodyList.clear();
            productKeyList.clear();
            OkHttpUtil.Get1("api/v1/product", Token, new OkHttpUtil.ReqCallBack() {
                @Override
                public void onReqSuccess(Object result) throws JSONException {
                    ProductBean productBean = new ProductBean();
                    try {
                        productBean = gson.fromJson((String) result,ProductBean.class);
                    } catch (JsonSyntaxException e){
                        e.printStackTrace();
                        util.showToast(MainActivity.this,"产品列表信息解析失败");
                        return;
                    }
                    if (productBean.getData().getProductInfo()!=null&&productBean.getData().getProductInfo().size()>0){
                        for (ProductInfo data : productBean.getData().getProductInfo()){
                            if (data.getTypeIdentify().equals("heznlc")){
                                productKeyList.add(data.getProductKey());
                            }
                        }
                    }
                    bundle.putStringArrayList("productKey",productKeyList);
                    initPager();
                    setTabs(tabLayout,getLayoutInflater(),TXT_TABLES,IMG_TABLES);
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    util.showToast(MainActivity.this,errorMsg);
                }
            });
        }
    }

    private void setTabs(TabLayout tabLayout, LayoutInflater layoutInflater, int[] txtTables, int[] imgTables) {
        for (int i=0;i<txtTables.length;i++){
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.item_main_menu,null);
            // 使用自定义视图，目的是为了便于修改，也可使用自带的视图
            tab.setCustomView(view);
            ImageView imgTitle = view.findViewById(R.id.img_tab);
            imgTitle.setImageResource(imgTables[i]);
            TextView textView = view.findViewById(R.id.txt_tab);
            textView.setText(txtTables[i]);
            tabLayout.addTab(tab);
        }
    }

    private void initPager() {
        adapter = new MainPagerAdapter(getSupportFragmentManager(), bundle);
        viewPager.setAdapter(adapter);
        // 关联切换
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 取消平滑切换
                viewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView() {
        Intent intent = getIntent();
        Token = intent.getStringExtra("Token");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        deviceType = intent.getStringExtra("deviceType");
    }

    //自动更新
    private void showUpdateDialog(String description) {
        SelfDialogUplode selfDialogUplode = new SelfDialogUplode(MainActivity.this);
        selfDialogUplode.Message(description);
        selfDialogUplode.setYesOnclickListener("立即更新",()->{
            Message msg = updateHandler.obtainMessage();
            msg.what = 1;
            updateHandler.sendMessage(msg);
            showDownloadDialog();
            selfDialogUplode.dismiss();
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
    private void initQuanXian() {
        //开启更新线程
//        new Thread(new CheckUpdate(getVersionName(),MainActivity.this)).start();
        new Thread(new CheckUpdate(getVersionName(),MainActivity.this)).start();
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
}