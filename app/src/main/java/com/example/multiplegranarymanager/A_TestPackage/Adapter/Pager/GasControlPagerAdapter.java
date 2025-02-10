package com.example.multiplegranarymanager.A_TestPackage.Adapter.Pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.GasControlFunction.GasTightFragment;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.GasControlFunction.N2InputFragment;

public class GasControlPagerAdapter extends FragmentPagerAdapter {
    Bundle bundle = new Bundle();
    public GasControlPagerAdapter(@NonNull FragmentManager fm, Bundle bundle1){
        super(fm);
        bundle = bundle1;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new N2InputFragment();
                break;
            case 1:
                fragment = new GasTightFragment();
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
