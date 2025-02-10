package com.example.multiplegranarymanager.A_ShuChengPackage.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_ShuChengPackage.Adapter.DataPagerAdapter;
import com.example.multiplegranarymanager.A_ShuChengPackage.Fragment.GasFragment;
import com.example.multiplegranarymanager.A_ShuChengPackage.Fragment.HumFragment;
import com.example.multiplegranarymanager.A_ShuChengPackage.Fragment.TemFragment;
import com.example.multiplegranarymanager.R;
import com.google.android.material.tabs.TabLayout;

public class DataTestActivity extends AppCompatActivity {
    private final String[] TAB_DATA = new String[]{"温湿度记录","温湿水记录","气体记录"};
    ViewPager viewPager;
    TabLayout tabLayout;
    private PagerAdapter adapter;
    private Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_test);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        initData();
    }

    private void initData() {
        String Fragment = getIntent().getStringExtra("fragment");
        String fragment = getIntent().getStringExtra("TAG");
        String Flag = getIntent().getStringExtra("Flag");
        String FLAG_TAG = getIntent().getStringExtra("TAG_FLAG");
        initPager();
        setTabs(tabLayout,getLayoutInflater(),TAB_DATA,fragment);
        bundle.putString("Flag",Flag);
        if (Fragment.equals("1")){
            replaceFragment(new TemFragment(),bundle);
        } else if (Fragment.equals("2")) {
            replaceFragment(new HumFragment(),bundle);
        } else if (Fragment.equals("3")) {
            replaceFragment(new GasFragment(),bundle);
        }
    }

    private void replaceFragment(Fragment fragment, Bundle args) {
        fragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.view_pager, fragment)
                .commit();
    }

    private void setTabs(TabLayout tabLayout, LayoutInflater layoutInflater, String[] tabData, String fragment) {
        for (int i=0;i<tabData.length;i++){
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.item_main_menu,null);
            tab.setCustomView(view);
            ImageView imageView = view.findViewById(R.id.img_tab);
            imageView.setVisibility(View.GONE);
            TextView tvTitle = view.findViewById(R.id.txt_tab);
            tvTitle.setText(tabData[i]);
            tabLayout.addTab(tab);
            if (fragment.equals(String.valueOf(i + 1))) {
                tab.select(); // 选中当前fragment对应的标签
            }
        }
    }

    private void initPager(){
        adapter = new DataPagerAdapter(getSupportFragmentManager(),bundle);
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