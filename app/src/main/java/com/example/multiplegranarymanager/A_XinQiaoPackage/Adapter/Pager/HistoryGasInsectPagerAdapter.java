package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.Pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.History.HistoryGasFragment;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.History.HistoryInsectFragment;

public class HistoryGasInsectPagerAdapter extends FragmentPagerAdapter {
    Bundle bundle = new Bundle();
    public HistoryGasInsectPagerAdapter(@NonNull FragmentManager fm, Bundle bundle1) {
        super(fm);
        bundle = bundle1;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HistoryGasFragment();
                break;
            case 1:
                fragment = new HistoryInsectFragment();
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
