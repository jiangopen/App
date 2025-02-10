package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.Pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.DetectionFragment;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.HistoryFragment;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.ModifyFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    Bundle bundle = new Bundle();
    public MainPagerAdapter(@NonNull FragmentManager fm,Bundle bundle1) {
        super(fm);
        bundle = bundle1;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new DetectionFragment();
                break;
            case 1:
                fragment = new HistoryFragment();
                break;
//            case 2:
//                fragment = new ModifyFragment();
//                break;
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
