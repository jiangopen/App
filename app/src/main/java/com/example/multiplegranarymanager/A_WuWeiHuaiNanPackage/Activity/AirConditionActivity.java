package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity;



import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.deviceType;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.AirCondition.HuaiNanAirConditionDataAdapter;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.AirCondition.WuWeiAirConditionDataAdapter;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.AirCondition.AirConditionBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.AirCondition.AirConditionerStatsList;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap.HardwareListData;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.CardAirConditionAdapterBody;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.CustomEditText;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;

import java.util.ArrayList;

public class AirConditionActivity extends AppCompatActivity {
    SmartRefreshLayout refreshLayout;
    CustomEditText Search_Input;
    TextView Txt_Granary_Name;
    TextView mFootView;
    FrameLayout layout_main;
    LinearLayout layout_no_shebei;
    RecyclerView mListView;
    private Gson gson = new Gson();
    private String commandMapId;
    private String url;
    private String json_data;
    private Bundle bundle = new Bundle();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    private HeaderViewAdapter headerViewAdapter;
    private WuWeiAirConditionDataAdapter adapter_WuWei_AirCondition;
    private HuaiNanAirConditionDataAdapter adapter_HuaiNan_AirCondition;
    private ArrayList<CardAirConditionAdapterBody> AirCardList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_condition);
        initView();
        bundle = getIntent().getBundleExtra("bundle");
        if (bundle!=null){
            AirCardList.clear();
            layout_no_shebei.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
            json_data = bundle.getString("空调控制请求体");
            commandMapId = bundle.getString("commandMapId");
            url = bundle.getString("url");
            String name = bundle.getString("granaryName");
            Txt_Granary_Name.setText(name);
            initAirConditionData(json_data, new OnInitAirConditionDataListener() {
                @Override
                public void OnInitAirConditionListener(boolean success) {
                    if (success) {
                        NewDownRawBody newDownRawBody = new NewDownRawBody();
                        newDownRawBody.setUrl("/measure");
                        newDownRawBody.setMethod("POST");
                        newDownRawBody.setQuery("");
                        newDownRawBody.setHeaders("");
                        CountMultiple countMultiple = new CountMultiple();
                        countMultiple.setCommandMapId(commandMapId);
                        countMultiple.setUrl(url);
                        String addressList = "";
                        for (CardAirConditionAdapterBody data : AirCardList){
                            addressList+=data.getInspurAddress();
                        }
                        String body =
                                  "AA55"
                                + "FF"
                                + "0B"
                                + "FFFF"
                                + intToHex(AirCardList.size(),4)
                                + addressList
                                + "FFFF"
                                + "0D0A";
                        countMultiple.setCommandContent(body);
                        newDownRawBody.setBody(countMultiple);
                        String jsonData = gson.toJson(newDownRawBody);
                        initAirConditionTestInTime(jsonData, new OnInitAirConditionTestInTimeListener() {
                            @Override
                            public void OnInitAirConditionTestListener(boolean success) {
                                refreshView(AirCardList);
                            }
                        });
                    }
                }
            });
        } else {
            layout_no_shebei.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                AirCardList.clear();
                initAirConditionData(json_data, new OnInitAirConditionDataListener() {
                    @Override
                    public void OnInitAirConditionListener(boolean success) {
                        if (success) {
                            NewDownRawBody newDownRawBody = new NewDownRawBody();
                            newDownRawBody.setUrl("/measure");
                            newDownRawBody.setMethod("POST");
                            newDownRawBody.setQuery("");
                            newDownRawBody.setHeaders("");
                            CountMultiple countMultiple = new CountMultiple();
                            countMultiple.setCommandMapId(commandMapId);
                            countMultiple.setUrl(url);
                            String addressList = "";
                            for (CardAirConditionAdapterBody data : AirCardList){
                                addressList+=data.getInspurAddress();
                            }
                            String body =
                                      "AA55"
                                    + "FF"
                                    + "0B"
                                    + "FFFF"
                                    + intToHex(AirCardList.size(),4)
                                    + addressList
                                    + "FFFF"
                                    + "0D0A";
                            countMultiple.setCommandContent(body);
                            newDownRawBody.setBody(countMultiple);
                            String jsonData = gson.toJson(newDownRawBody);
                            initAirConditionTestInTime(jsonData, new OnInitAirConditionTestInTimeListener() {
                                @Override
                                public void OnInitAirConditionTestListener(boolean success) {
                                    if (deviceType.equals("cloud_request_trans_wuwei_shili")||deviceType.equals("cloud_request_trans_wuwei_tuqiao")){
                                        adapter_WuWei_AirCondition.notifyDataSetChanged();
                                    } else if (deviceType.equals("cloud_request_trans_huainan")){
                                        adapter_HuaiNan_AirCondition.notifyDataSetChanged();
                                    }
                                    refreshView(AirCardList);
                                }
                            });
                        }
                    }
                });
                refreshLayout.finishRefresh(1500);
                util.showToast(AirConditionActivity.this,"刷新成功");
            }
        });
    }
    private void refreshView(ArrayList<CardAirConditionAdapterBody> List) {
        View fv = LayoutInflater.from(this).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = fv.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        if (deviceType.equals("cloud_request_trans_wuwei_shili")||deviceType.equals("cloud_request_trans_wuwei_tuqiao")){
            int[] layoutIds = new int[]{R.layout.card_aircondition_adapter_data_wuwei};
            GridLayoutManager layoutManager = new GridLayoutManager(this,1);
            mListView.setLayoutManager(layoutManager);
            adapter_WuWei_AirCondition = new WuWeiAirConditionDataAdapter(List,layoutIds,this);
            headerViewAdapter = new HeaderViewAdapter(adapter_WuWei_AirCondition);
            headerViewAdapter.addFooterView(fv);
            mListView.setAdapter(headerViewAdapter);
            mFootView.setText("共"+adapter_WuWei_AirCondition.getFreshDatas().size()+"台设备");
        } else if (deviceType.equals("cloud_request_trans_huainan")){
            int[] layoutIds = new int[]{R.layout.card_aircondition_adapter_data_huainan};
            GridLayoutManager layoutManager = new GridLayoutManager(this,1);
            mListView.setLayoutManager(layoutManager);
            adapter_HuaiNan_AirCondition = new HuaiNanAirConditionDataAdapter(List,layoutIds,this);
            headerViewAdapter = new HeaderViewAdapter(adapter_HuaiNan_AirCondition);
            headerViewAdapter.addFooterView(fv);
            mListView.setAdapter(headerViewAdapter);
            mFootView.setText("共"+adapter_HuaiNan_AirCondition.getFreshDatas().size()+"台设备");
        }
    }
    private void initView() {
        refreshLayout = findViewById(R.id.layout_refresh);
        Search_Input = findViewById(R.id.edit_search);
        Txt_Granary_Name = findViewById(R.id.txt_granary_name);
        layout_main = findViewById(R.id.layout_frame_main);
        layout_no_shebei = findViewById(R.id.shebei);
        mListView = findViewById(R.id.member);
    }
    public interface OnInitAirConditionTestInTimeListener{
        void OnInitAirConditionTestListener(boolean success);
    }
    public void initAirConditionTestInTime(String json, final OnInitAirConditionTestInTimeListener listener){
        suoPingDialog = new SuoPingDialog(this,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=30", json, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                AirConditionBean airConditionBean = new AirConditionBean();
                try {
                    airConditionBean = gson.fromJson((String) result,AirConditionBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util.showToast(AirConditionActivity.this, airConditionBean.getMsg());
                }
                if (airConditionBean.getData()!=null) {
                    if (airConditionBean.getData().getAirConditionerStatsList()!=null&&airConditionBean.getData().getAirConditionerStatsList().size()>0){
                        for (AirConditionerStatsList data : airConditionBean.getData().getAirConditionerStatsList()){
                            for (CardAirConditionAdapterBody item : AirCardList){
                                if (data.getInspurAddress().equals(item.getInspurAddress())){
                                    item.setStatus(data.getStatus());
                                    item.setTemp(data.getTemp().toString());
                                    item.setHumidity(data.getHumidity().toString());
                                }
                            }
                        }
                    }
                }
                suoPingDialog.dismiss();
                if (listener!=null){
                    listener.OnInitAirConditionTestListener(true);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OnInitAirConditionTestListener(false);
                util.showToast(AirConditionActivity.this, errorMsg);
            }
        });
    }
    public interface OnInitAirConditionDataListener{
        void OnInitAirConditionListener(boolean success);
    }
    public void initAirConditionData(String json, final OnInitAirConditionDataListener listener){
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=30", json, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                CommandMapBean commandMapBean = new CommandMapBean();
                try {
                    commandMapBean = gson.fromJson((String) result,CommandMapBean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    util.showToast(AirConditionActivity.this, commandMapBean.getMsg());
                }
                if (commandMapBean.getData().getData()!=null){
                    if (commandMapBean.getData().getData().getHardwareList()!=null&&commandMapBean.getData().getData().getHardwareList().size()>0){
                        int i=1;
                        for (HardwareListData data : commandMapBean.getData().getData().getHardwareList()){
                            if (data.getType()!=null&&data.getType().equals("04")){
                                CardAirConditionAdapterBody airConditionBody = new CardAirConditionAdapterBody(
                                        i,
                                        commandMapId,
                                        url,
                                        data.getExtensionNumber(),
                                        data.getId(),
                                        data.getInspurAddress(),
                                        data.getName(),
                                        data.getType(),
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null
                                );
                                i++;
                                AirCardList.add(airConditionBody);
                            }
                        }
                    }
                }
                if (listener!=null){
                    listener.OnInitAirConditionListener(true);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitAirConditionListener(false);
                util.showToast(AirConditionActivity.this,errorMsg);
            }
        });
    }
    public int hex2int(String v){
        int value = 0;
        try {
            value = Integer.parseInt(v,16);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return value;
    }
    public static String intToHex(int value, int count) {
        String res = Integer.toHexString(value).toUpperCase();
        int diff = count - res.length();
        if (diff > 0) {
            for (int i = 0; i < diff; i++) {
                res = "0" + res;
            }
        }
        return res;
    }
}