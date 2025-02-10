package com.example.multiplegranarymanager.A_TestPackage.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.Pager.GasControlPagerAdapter;
import com.example.multiplegranarymanager.Body.QiTiao.QiTiaoBody;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

public class GasControlActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    Gson gson = new Gson();
    private Util1 util = new Util1();
    private PagerAdapter adapter;
    private final String[] TAB_TITLES = new String[]{"氮气气调","气密性检测"};
    private Bundle bundleData = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_control);
        viewPager = findViewById(R.id.view_pages);
        tabLayout = findViewById(R.id.tab_layout);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle==null){
            String productName = getIntent().getStringExtra("productName");
            String moudleName = getIntent().getStringExtra("moudleName");
            String granaryId = getIntent().getStringExtra("granaryId");
            String granaryFen = getIntent().getStringExtra("granaryFen");
            String nickName = getIntent().getStringExtra("nickName");
            String productKey = getIntent().getStringExtra("productKey");
            String deviceKey = getIntent().getStringExtra("deviceKey");
            String date = getIntent().getStringExtra("date");
            String N2_list_00 = getIntent().getStringExtra("N2_list_00");
            String pressure_list_01 = getIntent().getStringExtra("pressure_list_01");
            String QiTiaoStatus_list_02 = getIntent().getStringExtra("QiTiaoStatus_list_02");
            String FaMengStatus_list_03 = getIntent().getStringExtra("FaMengStatus_list_03");
            String FengJiStatus_list_04 = getIntent().getStringExtra("FengJiStatus_list_04");
            String N2QiJiStatus_list_05 = getIntent().getStringExtra("N2QiJiStatus_list_05");
            String ChunDu_list_06 = getIntent().getStringExtra("ChunDu_list_06");
            String LiuLiang_list_07 = getIntent().getStringExtra("LiuLiang_list_07");
            String PressureNum_list_08 = getIntent().getStringExtra("PressureNum_list_08");
            String Temperature_list_09 = getIntent().getStringExtra("Temperature_list_09");
            String LiuLiangNum_list_10 = getIntent().getStringExtra("LiuLiangNum_list_10");
            QiTiaoBody data = new QiTiaoBody(
                    productName,
                    moudleName,
                    granaryId,
                    granaryFen,
                    nickName,
                    productKey,
                    deviceKey,
                    date,
                    N2_list_00,
                    pressure_list_01,
                    QiTiaoStatus_list_02,
                    FaMengStatus_list_03,
                    FengJiStatus_list_04,
                    N2QiJiStatus_list_05,
                    ChunDu_list_06,
                    LiuLiang_list_07,
                    PressureNum_list_08,
                    Temperature_list_09,
                    LiuLiangNum_list_10
            );
            bundleData.putParcelable("params",data);
            initN2QiTiao();
            setN2QiTiaoTabs(tabLayout,getLayoutInflater(),TAB_TITLES);
        }
    }

    private void setN2QiTiaoTabs(TabLayout tabLayout, LayoutInflater layoutInflater, String[] tabTitles) {
        for (String name : tabTitles){
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

    private void initN2QiTiao() {
        adapter = new GasControlPagerAdapter(getSupportFragmentManager(),bundleData);
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
}