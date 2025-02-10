package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.QiTiao.DieFaFragment;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.QiTiao.HuanLiuFragment;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.WuWeiWind.CangChuangFragment;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.WuWeiWind.TongFengKouFragment;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.WuWeiWind.WuWeiFengJiFragment;

public class QiTiaoPagerAdapter extends FragmentPagerAdapter {
    Bundle bundle = new Bundle();
    public QiTiaoPagerAdapter(@NonNull FragmentManager fm, Bundle bundle1) {
        super(fm);
        bundle = bundle1;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new DieFaFragment();
                break;
            case 1:
                fragment = new HuanLiuFragment();
                break;
            default:
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
