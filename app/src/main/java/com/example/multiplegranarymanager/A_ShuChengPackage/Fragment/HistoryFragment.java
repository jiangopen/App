package com.example.multiplegranarymanager.A_ShuChengPackage.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_ShuChengPackage.Activity.HistoryActivity;
import com.example.multiplegranarymanager.A_ShuChengPackage.Adapter.HistoryInfoAdapter;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.DeviceInfos.DeviceInfos;
import com.example.multiplegranarymanager.A_ShuChengPackage.SharedData.SharedDeviceInfos;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.CustomEditText;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements View.OnClickListener{
    CustomEditText Search_Input;
    ImageView MoreList,Img_More;
    TextView mFootView;
    TextView Text_Tem,Text_Gas,Text_Hum,Text_Text,Text_Test;
    TextView selectedTextView = null;
    SmartRefreshLayout refreshLayout;
    LinearLayout WuShebei,infos,layout_more;
    RecyclerView mListView;
    Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    //显示或收起功能列表判断
    private boolean Img_More_Flag = true;
    private int positione = -1;
    private Util1 util = new Util1();
    private HistoryInfoAdapter adapter_Card;
    private HeaderViewAdapter headerViewAdapter;
    private View ve;
    private DeviceInfos iteme;
    private HistoryInfoAdapter.OnItemClickListener MyItemClickListener = new HistoryInfoAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, DeviceInfos item, int position) {
            if (positione == position){
                ve = null;
                iteme = null;
                positione = -1;
            } else {
                ve = v;
                iteme = item;
                positione = position;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_2, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        //加载组件
        initView(view);
        refreshView();
        return view;
    }

    private void refreshView() {
        View fv = LayoutInflater.from(getContext()).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = fv.findViewById(R.id.tv_foot);
        mFootView.setText("正在加载数据......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        mListView.setLayoutManager(layoutManager);
        int[] layoutIds = new int[]{R.layout.adapter_card_history_2};
        adapter_Card = new HistoryInfoAdapter(SharedDeviceInfos.DeviceInfosList,layoutIds,getContext());
        headerViewAdapter = new HeaderViewAdapter(adapter_Card);
        headerViewAdapter.addFooterView(fv);
        mListView.setAdapter(headerViewAdapter);
        adapter_Card.setmOnItemClickListener(MyItemClickListener);
        mFootView.setText("共"+adapter_Card.getFreshDates().size()+"台设备");
    }

    private void initView(View view) {
        //搜索框
        Search_Input = view.findViewById(R.id.school_friend_member_search_input);
        //刷新界面
        refreshLayout = view.findViewById(R.id.layout_refresh);
        //无配置界面
        WuShebei = view .findViewById(R.id.shebei);
        //RecyclerView组件
        mListView = view.findViewById(R.id.member);
        //侧边栏弹出或收回键
        Img_More = view.findViewById(R.id.img_more);
        //侧边栏视图
        layout_more = view.findViewById(R.id.layout_more);
        //侧边栏功能键
        Text_Tem = view.findViewById(R.id.txt_tem);
        Text_Gas = view.findViewById(R.id.txt_gas);
        Text_Hum = view.findViewById(R.id.txt_hum);


        //功能显示组件
        Text_Text = view.findViewById(R.id.txt_text);
        //检测功能键
        Text_Test = view.findViewById(R.id.txt_now_test);
        //设置首选为温湿度检测
        Text_Tem.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
        selectedTextView = Text_Tem;
        //对这些组件设置监听事件
        Text_Tem.setOnClickListener(this);
        Text_Gas.setOnClickListener(this);
        Text_Hum.setOnClickListener(this);
        Text_Test.setOnClickListener(this);
        Img_More.setOnClickListener(this);
        //刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshView();
                refreshLayout.finishRefresh(1500);
                util.showToast(getContext(),"刷新成功");
            }
        });
        //设置Header为贝塞尔雷达样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()).setEnableHorizontalDrag(true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_tem:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_Tem.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_Tem;
                Text_Text.setText("温湿度");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_hum:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_Hum.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_Hum;
                Text_Text.setText("温湿水");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_gas:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_Gas.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_Gas;
                Text_Text.setText("气体");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_now_test:
                String CaoZuo = Text_Text.getText().toString();
                Log.d("jht", "CaoZuo: "+CaoZuo);
                List<CountMultiple> body = new ArrayList<>();
                TextInTime(CaoZuo,body);
                break;
            case R.id.img_more:
                if (Img_More_Flag){
                    layout_more.setVisibility(View.VISIBLE);
                    Img_More.setImageResource(R.drawable.arrow_left_in_main);
                    Img_More_Flag = false;
                    if (adapter_Card!=null) {
                        adapter_Card.notifyDataSetChanged();
                    }
                } else {
                    layout_more.setVisibility(View.GONE);
                    Img_More.setImageResource(R.drawable.arrow_right_in_main);
                    Img_More_Flag = true;
                    if (adapter_Card!=null) {
                        adapter_Card.notifyDataSetChanged();
                    }
                }
            default:
                break;
        }
    }

    private void TextInTime(String caoZuo, List<CountMultiple> body) {
        if (caoZuo.equals("温湿度")) {
            if (iteme!=null){
                HistoryInfoTest(iteme.getGranaryName(),"1");
            }
        } else if (caoZuo.equals("温湿水")) {
            if (iteme!=null){
                HistoryInfoTest(iteme.getGranaryName(),"2");
            }
        } else if (caoZuo.equals("气体")) {
            if (iteme!=null){
                HistoryInfoTest(iteme.getGranaryName(),"3");
            }
        }
    }

    private void HistoryInfoTest(String granaryName, String number) {
        Intent intent = new Intent(getContext(), HistoryActivity.class);
        String flag;
        switch (number) {
            case "1":
                flag = "温湿度";
                break;
            case "2":
                flag = "温湿水";
                break;
            case "3":
                flag = "气体";
                break;
            default:
                return;
        }
        intent.putExtra("granaryName",granaryName);
        intent.putExtra("flag",flag);
        startActivity(intent);
    }
}