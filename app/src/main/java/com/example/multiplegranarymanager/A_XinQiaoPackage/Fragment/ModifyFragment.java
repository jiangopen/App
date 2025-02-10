package com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.CommandSettingActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.ProjectSettingActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.SettingDataAdapter;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

public class ModifyFragment extends Fragment implements SettingDataAdapter.OnItemClickListener,SettingDataAdapter.OnItemClickListener2{
    RecyclerView mListView;
    SmartRefreshLayout refreshLayout;
    TextView mFootView;
    Gson gson = new Gson();
    private SuoPingDialog suoPingDialog;
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private SettingDataAdapter adapter_Setting;
    private HeaderViewAdapter headerViewAdapter;
    private ArrayList<GranaryListBean.Data> granarylist = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_setting, container, false);
        //RecyclerView组件
        mListView = view.findViewById(R.id.member);
        //刷新界面
        refreshLayout = view.findViewById(R.id.layout_refresh);
        bundle = getArguments();
        assert bundle != null;
        if (bundle.getParcelableArrayList("粮仓")!=null){
            granarylist = bundle.getParcelableArrayList("粮仓");
            refreshView(getContext(),granarylist);
        }
        return view;
    }

    private void refreshView(Context context, ArrayList<GranaryListBean.Data> List) {
        View vfoot = LayoutInflater.from(context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        GridLayoutManager layoutManager = new GridLayoutManager(context,1);
        int[] layoutIds = new int[]{R.layout.card_view_adapter_setting};
        mListView.setLayoutManager(layoutManager);
        adapter_Setting = new SettingDataAdapter(List,layoutIds,context);
        adapter_Setting.setOnItemClickListener(this);
        adapter_Setting.setOnItemClickListener2(this);
        headerViewAdapter = new HeaderViewAdapter(adapter_Setting);
        headerViewAdapter.addFooterView(vfoot);
        mListView.setAdapter(headerViewAdapter);
        Log.d("jht", "adapter_Card.flag: "+adapter_Setting.flag);
//        adapter_Card.setmOnClickSelectListener(mOnSelectListener);
        mFootView.setText("共"+adapter_Setting.getFreshDates().size()+"台设备");
    }
    @Override
    public void OnItemClick(String commandMapId, String url) {
        Intent intent = new Intent(getContext(), ProjectSettingActivity.class);
        intent.putExtra("commandMapId",commandMapId);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void OnItemClick2(String commandMapId, String url) {
        Intent intent = new Intent(getContext(), CommandSettingActivity.class);
        intent.putExtra("commandMapId",commandMapId);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
