package com.example.multiplegranarymanager.A_TestPackage.Adapter.Pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.multiplegranarymanager.A_TestPackage.Fragment.Main.HistoryFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Main.MultipleFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Main.SettingFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    Bundle bundle = new Bundle();
    public MainPagerAdapter(@NonNull FragmentManager fm, Bundle bundle1) {
        super(fm);
        bundle = bundle1;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MultipleFragment();
                break;
            case 1:
                fragment = new HistoryFragment();
                break;
            case 2:
                fragment = new SettingFragment();
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
