package com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.QiTiao;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.DieFaDataAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.HuanLiuDataAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindStatusBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.SharedData.QiTiaoSharedData;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HuanLiuFragment extends Fragment {
    private RecyclerView mListView;
    private LinearLayout WuSheBei;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    private Bundle bundle = new Bundle();
    private String commandMapId,url;
    private HuanLiuDataAdapter adapter_HuanLiu;
    private HeaderViewAdapter headerViewAdapter;
    TextView mFootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_huan_liu, container, false);
        mListView = view.findViewById(R.id.member);
        bundle = getArguments();
        assert bundle != null;
        commandMapId = bundle.getString("commandMapId");
        url = bundle.getString("url");
        refreshView(getContext(), QiTiaoSharedData.HuanLiuStatusList);
        return view;
    }

    private void refreshView(Context context, ArrayList<WindStatusBody> faMenStatusList) {
        View vfoot = LayoutInflater.from(context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        int[] layoutIds = new int[]{R.layout.adapter_card_huanliu};
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        mListView.setLayoutManager(layoutManager);
        adapter_HuanLiu = new HuanLiuDataAdapter(faMenStatusList,layoutIds,context);
        headerViewAdapter = new HeaderViewAdapter(adapter_HuanLiu);
        headerViewAdapter.addFooterView(vfoot);
        mListView.setAdapter(headerViewAdapter);
        mFootView.setText("共"+adapter_HuanLiu.getFreshDates().size()+"个");
    }
}