package com.example.multiplegranarymanager.A_XinQiaoPackage.Activity;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.Pager.DianZiHuoWeiCardPagerAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.SharedData.CommandGetSharedData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;

public class HuoWeiCardActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    Gson gson = new Gson();
    private String data,commandMapId,url,name;
    private ArrayList<GranaryListBean.Data> granarylist = new ArrayList<>();
    private Util1 util = new Util1();
    private PagerAdapter adapter;
    private SuoPingDialog suoPingDialog;
    private final String[] TAB_TITLES = new String[]{"仓房基本情况档案卡","仓(罐)保管员档案","粮情货位卡"};
    private Bundle bundleData = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huo_wei_card);
        viewPager = findViewById(R.id.view_pages);
        tabLayout = findViewById(R.id.tab_layout);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            data = bundle.getString("电子货位卡请求体");
            commandMapId = bundle.getString("commandMapId");
            url = bundle.getString("url");
            name = bundle.getString("granaryName");
            granarylist = bundle.getParcelableArrayList("粮仓");
            suoPingDialog = new SuoPingDialog(this,"正在操作，请稍等......");
            suoPingDialog.setCancelable(true);
            suoPingDialog.show();
            initHuoWeiData(data, new OnInitHuoWeiFinishedListener() {
                @Override
                public void OnInitHuoWeiListener(boolean success) {
                    suoPingDialog.dismiss();
                    bundleData.putString("commandMapId", commandMapId);
                    bundleData.putString("url",url);
                    initHuoWeiPages();
                    setHuoWeiCardTabs(tabLayout,getLayoutInflater(),TAB_TITLES);
                }
            });
        }
    }

    private void setHuoWeiCardTabs(TabLayout tabLayout, LayoutInflater layoutInflater, String[] tabTitles) {
        for (String name : tabTitles) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.item_main_menu,null);
            //使用自定义视图，目的是为了便于修改，也是用自带的视图
            tab.setCustomView(view);
            TextView titleTxt = view.findViewById(R.id.txt_tab);
            ImageView imageView = view.findViewById(R.id.img_tab);
            imageView.setVisibility(View.GONE);
            titleTxt.setText(name);
            tabLayout.addTab(tab);
        }
    }

    private void initHuoWeiPages() {
        adapter = new DianZiHuoWeiCardPagerAdapter(getSupportFragmentManager(),bundleData);
        viewPager.setAdapter(adapter);
        //关联切换
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //取消平滑切换
                viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public interface OnInitHuoWeiFinishedListener {
        void OnInitHuoWeiListener(boolean success);
    }

    public void initHuoWeiData(String jsondata, final OnInitHuoWeiFinishedListener listener) {
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                CommandMapBean commandMapBean = new CommandMapBean();
                try {
                    commandMapBean = gson.fromJson((String) result,CommandMapBean.class);
                }catch (JsonSyntaxException e) {
                    listener.OnInitHuoWeiListener(false);
                    e.printStackTrace();
                    util.showToast(HuoWeiCardActivity.this,"数据解析出错");
                }
                if (commandMapBean.getCode()==200 && commandMapBean.getData()!=null) {
                    CommandGetSharedData.GRANARY = commandMapBean.getData();
                    listener.OnInitHuoWeiListener(true);
                } else {
                    listener.OnInitHuoWeiListener(false);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitHuoWeiListener(false);
                util.showToast(HuoWeiCardActivity.this,errorMsg);
            }
        });
    }
}