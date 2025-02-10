package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.Pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Fragment.WuWeiWind.CangChuangFragment;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Fragment.WuWeiWind.TongFengKouFragment;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Fragment.WuWeiWind.WuWeiFengJiFragment;


public class WuWeiWindPagerAdapter extends FragmentPagerAdapter  {
    Bundle bundle = new Bundle();
    public WuWeiWindPagerAdapter(@NonNull FragmentManager fm, Bundle bundle1) {
        super(fm);
        bundle = bundle1;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new CangChuangFragment();
                break;
            case 1:
                fragment = new TongFengKouFragment();
                break;
            case 2:
                fragment = new WuWeiFengJiFragment();
            default:
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
