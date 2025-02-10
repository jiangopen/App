package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity;

import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.deviceType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.Pager.HistoryGasInsectPagerAdapter;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap.InspurChannelNumberMap;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.GasInsectBean.GasInsectHistoryDataBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.HistoryCountMultipleBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HistoryGasInsertDataActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView titletext;
    Gson gson = new Gson();
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Bundle bundle1 = new Bundle();

    public SuoPingDialog suoPingDialog;
    private HistoryGasInsectPagerAdapter adapter;
    private final String[] TXT_TITLES = new String[]{"气体","虫害"};
    private String NAME,DEVICETYPE,TOKEN,JSONDATA;
    private ArrayList<GranaryListBean.Data> granarylist = new ArrayList<>();
    private GranaryListBean.Data Granary;
    private ArrayList<InspurChannelNumberMap> inspurChannelNumber = new ArrayList<>();
    private ArrayList<HistoryCountMultipleBean.DataContent> DataContent = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_gas_insert_data);
        viewPager = findViewById(R.id.view_pages);
        tabLayout = findViewById(R.id.tab_layout);
        titletext = findViewById(R.id.title_text);
        bundle = getIntent().getBundleExtra("bundle1");
        NAME = getIntent().getStringExtra("仓号");
        DEVICETYPE = getIntent().getStringExtra("账号");
        TOKEN = getIntent().getStringExtra("token");
        JSONDATA = getIntent().getStringExtra("温度历史数据请求体");
        granarylist = bundle.getParcelableArrayList("粮仓");
        for (GranaryListBean.Data data : granarylist) {
            if (data.getGranaryName().equals(NAME)) {
                Granary = data;
            }
        }
        suoPingDialog = new SuoPingDialog(this,"加载中,请稍等......");
        suoPingDialog.show();
        initGasInsectData(JSONDATA, new OnInitGasInsectDataFinishedListener() {
            @Override
            public void OnInitGasInsectListener(List<HistoryCountMultipleBean.DataContent> DataContent) {
                if (DataContent!=null){
                    CountMultiple multiple = new CountMultiple();
                    multiple.setUrl(Granary.getUrl());
                    multiple.setCommandMapId(Granary.getCommandMapId());
                    NewDownRawBody Body = new NewDownRawBody();
                    Body.setBody(multiple);
                    Body.setMethod("POST");
                    Body.setQuery("");
                    Body.setHeaders("");
                    Body.setUrl("/command-map/get");
                    String jsondata = gson.toJson(Body);
                    initGetCommand(jsondata, new OnInitGetCommandFinishedListener() {
                        @Override
                        public void OnInitGetCommandListener(ArrayList<InspurChannelNumberMap> ChannelNumberMap) {
                            suoPingDialog.dismiss();
                            ArrayList<HistoryCountMultipleBean.DataContent> list = new ArrayList<>(DataContent);
                            bundle1.putParcelableArrayList("DataContent", list);
                            bundle1.putParcelableArrayList("ChannelNumberMap", ChannelNumberMap);
                            bundle1.putParcelable("Granary",Granary);
                            initHistoryGasInsectPages();
                            setHistoryGasInsectTabs(tabLayout,getLayoutInflater(),TXT_TITLES);
                        }
                    });
                } else {
                    suoPingDialog.dismiss();
                    util.showToast(HistoryGasInsertDataActivity.this,"无数据");
                }
            }
        });
        titletext.setText(NAME);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在 Activity 销毁时检查并关闭 Dialog
        if (suoPingDialog!= null && suoPingDialog.isShowing()) {
            suoPingDialog.dismiss();
        }
    }
    private void setHistoryGasInsectTabs(TabLayout tabLayout, LayoutInflater layoutInflater, String[] tabTitles) {
        for (String name : tabTitles){
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.item_main_menu,null);
            //使用自定义视图，目的是为了便于修改，也是用自带的视图
            tab.setCustomView(view);
            TextView titleTxt = view.findViewById(R.id.txt_tab);
            ImageView imageView = view.findViewById(R.id.img_tab);
            imageView.setVisibility(View.GONE);
            titleTxt.setText(name);
            tabLayout.addTab(tab);
        }
    }
    private void initHistoryGasInsectPages() {
        adapter = new HistoryGasInsectPagerAdapter(getSupportFragmentManager(),bundle1);
        viewPager.setAdapter(adapter);
        //关联切换
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //取消平滑切换
                viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public interface OnInitGasInsectDataFinishedListener {
        void OnInitGasInsectListener(List<HistoryCountMultipleBean.DataContent> DataContent);
    }
    public void initGasInsectData(String jsondata, final OnInitGasInsectDataFinishedListener listener){
        OkHttpUtil.PostNewDownRaw("api/v1/newDownRaw?deviceType=" + DEVICETYPE + "&bodyType=json&timeout=30", jsondata, TOKEN, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                GasInsectHistoryDataBean gasInsectHistoryDataBean;
                try {
                    gasInsectHistoryDataBean = gson.fromJson((String) result,GasInsectHistoryDataBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitGasInsectListener(null);
                    e.printStackTrace();
                    util.showToast(HistoryGasInsertDataActivity.this,"数据解析出错");
                    return;
                }
                if (gasInsectHistoryDataBean.getData()!=null && gasInsectHistoryDataBean.getData().size()>0){
                    for (GasInsectHistoryDataBean.Data data : gasInsectHistoryDataBean.getData()){
                        DataContent.add(data.getDataContent());
                    }
                    listener.OnInitGasInsectListener(DataContent);
                } else {
                    listener.OnInitGasInsectListener(null);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitGasInsectListener(null);
                util.showToast(HistoryGasInsertDataActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitGetCommandFinishedListener {
        void OnInitGetCommandListener(ArrayList<InspurChannelNumberMap> ChannelNumberMap);
    }
    public void initGetCommand(String jsonData, final OnInitGetCommandFinishedListener listener){
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + DEVICETYPE + "&bodyType=json&timeout=30", jsonData, TOKEN, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                inspurChannelNumber.clear();
                CommandMapBean commandMapBean = new CommandMapBean();
                try {
                    commandMapBean = gson.fromJson((String) result,CommandMapBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitGetCommandListener(null);
                    e.printStackTrace();
                    util.showToast(HistoryGasInsertDataActivity.this,"数据解析出错");
                    return;
                }
                if (commandMapBean.getData()!=null &&
                        commandMapBean.getData().getData()!=null &&
                        commandMapBean.getData().getData().getGasInsectInfo()!=null &&
                        commandMapBean.getData().getData().getGasInsectInfo().getInspurChannelNumberMap()!=null){
                    for (InspurChannelNumberMap data : commandMapBean.getData().getData().getGasInsectInfo().getInspurChannelNumberMap()) {
                        inspurChannelNumber.add(data);
                    }
                }
                listener.OnInitGetCommandListener(inspurChannelNumber);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitGetCommandListener(null);
                util.showToast(HistoryGasInsertDataActivity.this,errorMsg);
            }
        });
    }
}