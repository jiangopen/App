package com.example.multiplegranarymanager.A_ShuChengPackage.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.multiplegranarymanager.A_ShuChengPackage.Fragment.GasFragment;
import com.example.multiplegranarymanager.A_ShuChengPackage.Fragment.HumFragment;
import com.example.multiplegranarymanager.A_ShuChengPackage.Fragment.TemFragment;

public class DataPagerAdapter extends FragmentPagerAdapter {
    private Bundle bundle = new Bundle();
    public DataPagerAdapter(@NonNull FragmentManager fm, Bundle bundle1) {
        super(fm);
        this.bundle = bundle1;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TemFragment();
                break;
            case 1:
                fragment = new HumFragment();
                break;
            case 2:
                fragment = new GasFragment();
                break;
            default:
                break;
        };
        fragment.setArguments(bundle);
        return fragment;
    }
}
