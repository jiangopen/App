package com.example.multiplegranarymanager.A_XinQiaoPackage.Activity;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.WindActivity.intToHex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.SmartLockAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.GranarySettingData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.LockStatusBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind.WindStatusBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind.WindStatusBeanTWO;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.SmartLockBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindAddressBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewSmartLockBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.example.multiplegranarymanager.Util.WonderUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmartLockActivity extends AppCompatActivity implements View.OnClickListener{
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    private SuoPingDialog suoPingDialog;
    private HeaderViewAdapter headerViewAdapter;
    private WonderUtil mWonderUtil = new WonderUtil();
    int selectWhich = 0;
    private ImageView txt_select;
    private TextView txt_name,txt_alert_num,txt_lock_num,txt_test,txt_lock_off;
    private RecyclerView mListView;
    private TextView mFootView;
    public static String url;
    private String Name;
    private String FLAG;
    private String JsonData;
    public static String commandMapId;
    private ArrayList<GranaryListBean.Data> granarylist = new ArrayList<>();
    private CommandMapBean.Data HardWareListDataPUT = new CommandMapBean.Data();
    public static String TESTING = "";
    public static String OPEN_DOOR = "";
    public static String CLOSE_DOOR = "";
    private String QITI = "";
    private String ALERT_OPEN = "";
    private String ALERT_CLOSE = "";
    private GranarySettingData GranaryData = new GranarySettingData();
    private ArrayList<SmartLockBody> SmartLockList = new ArrayList<>();
    private SmartLockAdapter adapter_Lock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_lock);
        txt_select = findViewById(R.id.txt_select);
        txt_name = findViewById(R.id.txt_name);
        txt_test = findViewById(R.id.txt_test_num);
        txt_alert_num = findViewById(R.id.txt_alert_num);
        txt_lock_num = findViewById(R.id.txt_lock_num);
        txt_lock_off = findViewById(R.id.txt_lock_off);
        mListView = findViewById(R.id.member);
        txt_select.setOnClickListener(this);
        txt_test.setOnClickListener(this);
        txt_alert_num.setOnClickListener(this);
        txt_lock_num.setOnClickListener(this);
        txt_lock_off.setOnClickListener(this);

        bundle = getIntent().getBundleExtra("bundle");
        Name = bundle.getString("仓号");
        JsonData = bundle.getString("门锁请求体");
        Log.d("jht", "onCreate: "+JsonData);
        granarylist = bundle.getParcelableArrayList("粮仓");
        commandMapId = bundle.getString("commandMapId");
        url = bundle.getString("url");
        String name = bundle.getString("granaryName");
        txt_name.setText(name);
//        Log.d("zyq", "initSmartLock: " + JsonData);
        suoPingDialog = new SuoPingDialog(this,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        initSmartLock(JsonData, new OnInitSmartLockFinishedListener() {
            @Override
            public void OnInitSmartLockListener(String address, ArrayList<HardwareListData> datalist) {
                if (address!=null && datalist!=null) {
                    NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                    newDownRawBody.setUrl("/hc/login");
                    newDownRawBody.setMethod("POST");
//                    newDownRawBody.setUrl("/measure");
                    newDownRawBody.setQuery(null);
                    newDownRawBody.setHeaders(null);
                    NewSmartLockBody params = new NewSmartLockBody();
                    params.setCommandMapId(commandMapId);
                    params.setUrl(url);
                    params.setIp(GranaryData.getEntranceGuardIp());
                    params.setName(GranaryData.getEntranceGuardName());
                    params.setPwd(GranaryData.getEntranceGuardPwd());
                    params.setPort(GranaryData.getEntranceGuardPort());
//                    CountMultiple countMultiple = new CountMultiple();
//                    countMultiple.setCommandMapId(commandMapId);
//                    countMultiple.setMeasureType("-1");
//                    String addresslist = "AA55" + TESTING + "5C" + "FFFF" + "FFFF0D0A";
//                    ArrayList<String> ids = new ArrayList<>();
//                    for (HardwareListData data : datalist) {
//                        ids.add(data.getExtensionNumber());
//                    }
//                    CountMultiple.CommandJson commandJson = new CountMultiple.CommandJson();
//                    commandJson.setIds(ids);
//                    countMultiple.setCommandJson(commandJson);
//                    countMultiple.setCommandContent(addresslist);
//                    countMultiple.setUrl(url);
                    newDownRawBody.setBody(params);
                    String jsondata = gson.toJson(newDownRawBody);

                    //这里之后大概率会出问题，如果一个仓安装了两个智能门锁，这里就只会携带第一个的参数进入后续的操作中
                    initNewData(jsondata, datalist.get(0), new OnInitNewDataFinishedListener() {
                        @Override
                        public void OnInitNewDataListener(boolean success) {
                            NewDownRawBodyTWO newDownRawBody1 = new NewDownRawBodyTWO();
                            newDownRawBody1.setUrl("/hc/getAcsStatus");
                            newDownRawBody1.setMethod("POST");
                            newDownRawBody1.setQuery(null);
                            newDownRawBody1.setHeaders(null);
                            NewSmartLockBody params = new NewSmartLockBody();
                            params.setCommandMapId(commandMapId);
                            params.setUrl(url);
                            params.setIp(GranaryData.getEntranceGuardIp());
                            params.setName(GranaryData.getEntranceGuardName());
                            params.setPwd(GranaryData.getEntranceGuardPwd());
                            params.setPort(GranaryData.getEntranceGuardPort());
                            CountMultiple countMultiple = new CountMultiple();
                            countMultiple.setCommandMapId(commandMapId);
                            countMultiple.setUrl(url);
                            newDownRawBody1.setBody(countMultiple);
                            String jsondata = gson.toJson(newDownRawBody1);

                            initStatus(jsondata,datalist.get(0), new OnInitStatusFinishedListener() {
                                @Override
                                public void OnInitStatusListener(boolean success, SmartLockBody one) {
//                                    Log.d("jht", "OnInitSmartLockListener: "+one.getHardwareListData().getName());
                                    NewDownRawBodyTWO newDownRawBody2 = new NewDownRawBodyTWO();
                                    newDownRawBody2.setUrl("/measure");
                                    newDownRawBody2.setMethod("POST");
                                    newDownRawBody2.setQuery(null);
                                    newDownRawBody2.setHeaders(null);
                                    CountMultiple countMultiple = new CountMultiple();
                                    countMultiple.setCommandMapId(commandMapId);
                                    countMultiple.setMeasureType("-1");
                                    String addresslist = "AA55" + QITI + "5E" + "FFFF" + "FFFF0D0A";
                                    countMultiple.setCommandContent(addresslist);
                                    countMultiple.setUrl(url);
                                    newDownRawBody2.setBody(countMultiple);
                                    String jsondata = gson.toJson(newDownRawBody2);
//                                    Log.d("jht", "onReqSuccess: "+one.getTag());
//
//                                    Log.d("jht", "onReqSuccess: "+one.getUrl());
//                                    Log.d("jht", "onReqSuccess: "+one.getHardwareStatus());
//                                    Log.d("jht", "onReqSuccess: "+one.getCommandMapId());
//                                    Log.d("jht", "onReqSuccess: "+one.getGranarySettingData().getName());
                                    initGas(jsondata,one, new OnInitGasFinishedListener() {
                                        @Override
                                        public void OnInitGasListener(SmartLockBody success) {
                                            Log.d("jht", "OnInitGasListener: "+success);
                                            if (success == null) {
                                                SmartLockList.add(one);
                                            } else {
                                                SmartLockList.add(success);
                                            }
//                                            Log.d("jht", "OnInitSmartLockListener: "+success.getHardwareListData().getName());
//                                            Log.d("jht", "OnInitGasListener: "+SmartLockList.get(0).getPh3List().get(0));
                                            suoPingDialog.dismiss();
                                            refreshView(SmartLockActivity.this,SmartLockList);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
    }
    private void refreshView(SmartLockActivity context, ArrayList<SmartLockBody> smartLockList) {
//        Log.d("jht", "refreshView: " + smartLockList.get(0).getHardwareListData().getName());
        View vfoot = LayoutInflater.from(context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        int[] layoutIds = new int[]{R.layout.adapter_smart_lock_card};
        GridLayoutManager layoutManager = new GridLayoutManager(context,1);
        mListView.setLayoutManager(layoutManager);
        adapter_Lock = new SmartLockAdapter(smartLockList,layoutIds,context);
        headerViewAdapter = new HeaderViewAdapter(adapter_Lock);
        headerViewAdapter.addFooterView(vfoot);
        mListView.setAdapter(headerViewAdapter);
        mFootView.setText("共"+adapter_Lock.getFreshDates().size()+"个");
    }
    public interface OnInitGasFinishedListener{
        void OnInitGasListener(SmartLockBody success);
    }
    public void initGas(String jsondata, SmartLockBody one, final OnInitGasFinishedListener listener){
        Log.d("zyq", "initGas: "+jsondata);
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindStatusBeanTWO smartlock = new WindStatusBeanTWO();
                try {
                    smartlock = gson.fromJson((String) result,WindStatusBeanTWO.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitGasListener(null);
                    e.printStackTrace();
                    util.showToast(SmartLockActivity.this,smartlock.getMsg());
                    return;
                }
                SmartLockBody two = new SmartLockBody();
                if (smartlock.getData()!=null) {
                    two = one;


                    if (smartlock.getData().getN2List()!=null && smartlock.getData().getN2List().size()>0) {
                        two.setN2List(smartlock.getData().getN2List());
                    }
                    if (smartlock.getData().getO2List()!=null && smartlock.getData().getO2List().size()>0) {
                        two.setO2List(smartlock.getData().getO2List());
                    }
                    if (smartlock.getData().getPh3List()!=null && smartlock.getData().getPh3List().size()>0) {
                        two.setPh3List(smartlock.getData().getPh3List());
                    }
                }
                Log.d("jht", "coverts: "+two.getN2List().size());
                listener.OnInitGasListener(two);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitGasListener(null);
                util.showToast(SmartLockActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitStatusFinishedListener{
        void OnInitStatusListener(boolean success, SmartLockBody one);
    }
    public void initStatus(String jsondata, HardwareListData hardwareListData, final OnInitStatusFinishedListener listener){
        SmartLockList.clear();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                LockStatusBean lockStatusBean = new LockStatusBean();
                try{
                    lockStatusBean = gson.fromJson((String) result, LockStatusBean.class);
                } catch (JsonSyntaxException e){
                    listener.OnInitStatusListener(false, null);
                    e.printStackTrace();
                    util.showToast(SmartLockActivity.this,"通风一键解析有问题");
                    return;
                }
                if (lockStatusBean.getData()!=null) {
                    SmartLockBody one = new SmartLockBody();
                    one.setHardwareStatus(lockStatusBean.getData().getLockStatus());
                    one.setGranarySettingData(GranaryData);
                    one.setTag(1);
                    one.setUrl(url);
                    one.setCommandMapId(commandMapId);
                    one.setN2List(null);
                    one.setO2List(null);
                    one.setPh3List(null);
                    one.setHardwareListData(hardwareListData);
                    listener.OnInitStatusListener(true,one);
                } else {
                    listener.OnInitStatusListener(false, null);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitStatusListener(false, null);
                util.showToast(SmartLockActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitNewDataFinishedListener{
        void OnInitNewDataListener(boolean success);
    }
    public void initNewData(String jsondata, HardwareListData list, final OnInitNewDataFinishedListener listener){
        Log.d("zyq", "initSmartLock: " + jsondata);
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindStatusBean smartlock = new WindStatusBean();
                try {
                    smartlock = gson.fromJson((String) result,WindStatusBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitNewDataListener(false);
                    e.printStackTrace();
                    util.showToast(SmartLockActivity.this,smartlock.getMsg());
                    return;
                }
//                int i = 1;
//                if (smartlock.getData()!=null && smartlock.getData().getDataList()!=null && smartlock.getData().getDataList().size()>0) {
//                    for (DoorDataList data : smartlock.getData().getDataList()) {
//                        SmartLockBody sb = new SmartLockBody();
//                        sb.setTag(i);
//                        sb.setDataList(data);
//                        sb.setUrl(url);
//                        sb.setHardwareStatus(data.getDoorStatusList().get(0));
//                        sb.setCommandMapId(commandMapId);
//                        sb.setHardwareListData(list);
//                        SmartLockList.add(sb);
//                        i++;
//                    }
//                }
                if (smartlock.getCode()==200){
                    listener.OnInitNewDataListener(true);
                } else {
                    listener.OnInitNewDataListener(false);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitNewDataListener(false);
                util.showToast(SmartLockActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitSmartLockFinishedListener{
        void OnInitSmartLockListener(String address, ArrayList<HardwareListData> datalist);
    }
    private void initSmartLock(String jsondata, final OnInitSmartLockFinishedListener listener) {
        Log.d("zyq", "initSmartLock1: " + jsondata);
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                CommandMapBean commandMapBean = new CommandMapBean();
                String address = "";
                ArrayList<HardwareListData> SmartLockBody = new ArrayList<>();
                try {
                    commandMapBean = gson.fromJson((String) result,CommandMapBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitSmartLockListener(null,null);
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util.showToast(SmartLockActivity.this,commandMapBean.getMsg());
                    return;
                }
                if (commandMapBean.getData()!=null) {
                    HardWareListDataPUT = commandMapBean.getData();
                }
                if (commandMapBean.getData().getData()!=null) {
                    if (commandMapBean.getData().getData().getGranarySetting()!=null) {
                        GranaryData = commandMapBean.getData().getData().getGranarySetting();
                    }
                    if (commandMapBean.getData().getData().getCommandList()!=null && commandMapBean.getData().getData().getCommandList().size()>0) {
                        for (CommandListData data : commandMapBean.getData().getData().getCommandList()) {
                            if (data.getInspurControlType().equals("5C")) {
                                TESTING = data.getInspurExtensionAddress();
                            }
                            if (data.getInspurControlType().equals("58")) {
                                OPEN_DOOR = data.getInspurExtensionAddress();
                            }
                            if (data.getInspurControlType().equals("5A")) {
                                CLOSE_DOOR = data.getInspurExtensionAddress();
                            }
//                            if (data.getInspurControlType().equals("44")) {
//                                AlertNum = data.getInspurExtensionAddress();
//                            }
//                            if (data.getInspurControlType().equals("48")) {
//                                Time = data.getInspurExtensionAddress();
//                            }
                            if (data.getInspurControlType().equals("5E")) {
                                QITI = data.getInspurExtensionAddress();
                            }
                            if (data.getInspurControlType().equals("60")) {
                                ALERT_OPEN = data.getInspurExtensionAddress();
                            }
                            if (data.getInspurControlType().equals("62")) {
                                ALERT_CLOSE = data.getInspurExtensionAddress();
                            }
                        }
                    }
                    if (commandMapBean.getData().getData().getHardwareList()!=null && commandMapBean.getData().getData().getHardwareList().size()>0) {
                        for (HardwareListData data : commandMapBean.getData().getData().getHardwareList()) {
                            if (data.getType().equals("12")) {
                                SmartLockBody.add(data);
                            }
                        }
                    }
                }
                listener.OnInitSmartLockListener(address,SmartLockBody);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitSmartLockListener(null,null);
                suoPingDialog.dismiss();
                util.showToast(SmartLockActivity.this,errorMsg);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_test_num:
                suoPingDialog = new SuoPingDialog(SmartLockActivity.this,"正在操作，请稍等......");
                suoPingDialog.setCancelable(true);
                suoPingDialog.show();
                NewDownRawBodyTWO newDownRawBody2 = new NewDownRawBodyTWO();
                newDownRawBody2.setUrl("/measure");
                newDownRawBody2.setMethod("POST");
                newDownRawBody2.setQuery(null);
                newDownRawBody2.setHeaders(null);
                CountMultiple countMultiple = new CountMultiple();
                countMultiple.setCommandMapId(commandMapId);
                countMultiple.setMeasureType("-1");
                String addresslist = "AA55" + QITI + "5E" + "FFFF" + "FFFF0D0A";
                countMultiple.setCommandContent(addresslist);
                countMultiple.setUrl(url);
                newDownRawBody2.setBody(countMultiple);
                String jsondata = gson.toJson(newDownRawBody2);
                initGas(jsondata,SmartLockList.get(0), new OnInitGasFinishedListener() {
                    @Override
                    public void OnInitGasListener(SmartLockBody success) {
                        Log.d("jht", "OnInitSmartLockListener: "+success.getHardwareListData().getName());
                        SmartLockBody one = new SmartLockBody();
                        one = SmartLockList.get(0);
                        SmartLockList.clear();
                        if (success == null) {
                            SmartLockList.add(one);
                        } else {
                            SmartLockList.add(success);
                        }
//                                            Log.d("jht", "OnInitGasListener: "+SmartLockList.get(0).getPh3List().get(0));
                        suoPingDialog.dismiss();
                        refreshView(SmartLockActivity.this,SmartLockList);
                    }
                });
                break;
            case R.id.txt_select:
                ArrayList<GranaryListBean.Data> product = new ArrayList<>();
                for (GranaryListBean.Data data : granarylist) {
                    product.add(data);
                }
                showAlertDialog(product);
                break;
            case R.id.txt_alert_num:
                initAlertNumDialog(GranaryData);
                break;
            case R.id.txt_lock_num:
                NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                newDownRawBody.setUrl("/measure");
                newDownRawBody.setMethod("POST");
                newDownRawBody.setQuery(null);
                newDownRawBody.setHeaders(null);
                CountMultiple countMultiple2 = new CountMultiple();
                countMultiple2.setCommandMapId(commandMapId);
                ArrayList<String> ids = new ArrayList<>();
//                for (HardwareListData data : datalist) {
//                    ids.add(data.getExtensionNumber());
//                }
                String id = SmartLockList.get(0).getHardwareListData().getExtensionNumber() + SmartLockList.get(0).getHardwareListData().getChannelNumber();
                ids.add(id);
                CountMultiple.CommandJson commandJson = new CountMultiple.CommandJson();
                commandJson.setIds(ids);
                countMultiple2.setCommandJson(commandJson);
                countMultiple2.setMeasureType("-1");
                String addresslist2 = "AA55" + ALERT_OPEN + "60" + "FFFF" + "FFFF0D0A";
                countMultiple2.setCommandContent(addresslist2);
                newDownRawBody.setBody(countMultiple2);
                String jsondata2 = gson.toJson(newDownRawBody);
                initOnOff(jsondata2, "开", new OnInitOnOffFinishedListener() {
                    @Override
                    public void OnInitOnOffListener(boolean success) {
                        if (success) {
                            util.showToast(SmartLockActivity.this,"开启成功");
                        } else {
                            util.showToast(SmartLockActivity.this,"开启失败");
                        }
                    }
                });
                break;
            case R.id.txt_lock_off:
                NewDownRawBodyTWO newDownRawBody3 = new NewDownRawBodyTWO();
                newDownRawBody3.setUrl("/measure");
                newDownRawBody3.setMethod("POST");
                newDownRawBody3.setQuery(null);
                newDownRawBody3.setHeaders(null);
                CountMultiple countMultiple3 = new CountMultiple();
                countMultiple3.setCommandMapId(commandMapId);
                ArrayList<String> ids2 = new ArrayList<>();
//                for (HardwareListData data : datalist) {
//                    ids.add(data.getExtensionNumber());
//                }
                String id2 = SmartLockList.get(0).getHardwareListData().getExtensionNumber() + SmartLockList.get(0).getHardwareListData().getChannelNumber();
                ids2.add(id2);
                CountMultiple.CommandJson commandJson2 = new CountMultiple.CommandJson();
                commandJson2.setIds(ids2);
                countMultiple3.setCommandJson(commandJson2);
                countMultiple3.setMeasureType("-1");
                String addresslist3 = "AA55" + ALERT_CLOSE + "62" + "FFFF" + "FFFF0D0A";
                countMultiple3.setCommandContent(addresslist3);
                newDownRawBody3.setBody(countMultiple3);
                String jsondata3 = gson.toJson(newDownRawBody3);
                initOnOff(jsondata3, "关", new OnInitOnOffFinishedListener() {
                    @Override
                    public void OnInitOnOffListener(boolean success) {
                        if (success) {
                            util.showToast(SmartLockActivity.this,"关闭成功");
                        } else {
                            util.showToast(SmartLockActivity.this,"关闭失败");
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
    public interface OnInitOnOffFinishedListener{
        void OnInitOnOffListener(boolean success);
    }
    public void initOnOff(String jsondata, String flag, final OnInitOnOffFinishedListener listener){
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindStatusBean codebean = new WindStatusBean();
                try {
                    codebean = gson.fromJson((String) result,WindStatusBean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    listener.OnInitOnOffListener(false);
                    util.showToast(SmartLockActivity.this,codebean.getMsg());
                    return;
                }
                if (flag.equals("开")) {
                    if (codebean.getData()!=null && codebean.getData().getOpenFlagList()!=null && codebean.getData().getOpenFlagList().size()>0) {
                        if (codebean.getData().getOpenFlagList().get(0).equals("00")) {
                            listener.OnInitOnOffListener(true);
                        } else {
                            listener.OnInitOnOffListener(false);
                        }
                    } else {
                        listener.OnInitOnOffListener(false);
                    }
                } else if (flag.equals("关")) {
                    if (codebean.getData()!=null && codebean.getData().getCloseFlagList()!=null && codebean.getData().getCloseFlagList().size()>0) {
                        if (codebean.getData().getCloseFlagList().get(0).equals("00")) {
                            listener.OnInitOnOffListener(true);
                        } else {
                            listener.OnInitOnOffListener(false);
                        }
                    } else {
                        listener.OnInitOnOffListener(false);
                    }
                } else {
                    listener.OnInitOnOffListener(false);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitOnOffListener(false);
                util.showToast(SmartLockActivity.this,errorMsg);
            }
        });
    }
    private void initAlertNumDialog(GranarySettingData Data) {
        //自定义Dialog Title样式
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_custom_title,null);
        final TextView textview = view.findViewById(R.id.modify_title);
        textview.setText("修改气体警戒值");
        final View view1 = layoutInflater.inflate(R.layout.dialog_gas_alert_clear,null);
        final EditText et_o2_up = view1.findViewById(R.id.et_o2_up);
        final EditText et_o2_down = view1.findViewById(R.id.et_o2_down);
        final EditText et_ph3_up = view1.findViewById(R.id.et_ph3_up);
        final EditText et_ph3_down = view1.findViewById(R.id.et_ph3_down);
        Double O2UP,O2DOWN,PH3UP,PH3DOWN;
        try {
            O2UP = Data.getLockWarn().getO2WarnUp();
            O2DOWN = Data.getLockWarn().getO2WarnDown();
            PH3UP = Data.getLockWarn().getPh3WarnUp();
            PH3DOWN = Data.getLockWarn().getPh3WarnDown();
            et_o2_up.setText(O2UP.toString()+"");
            et_o2_down.setText(O2DOWN.toString()+"");
            et_ph3_up.setText(PH3UP.toString()+"");
            et_ph3_down.setText(PH3DOWN.toString()+"");
        } catch (Exception e) {
            e.printStackTrace();
            util.showToast(this,"气体警戒值获取错误");
        }
        new AlertDialog.Builder(this).setCustomTitle(view).setView(view1).setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String NEWO2UP = et_o2_up.getText().toString();
                String NEWO2DOWN = et_o2_down.getText().toString();
                String NEWPH3UP = et_ph3_up.getText().toString();
                String NEWPH3DOWN = et_ph3_down.getText().toString();
                if (NEWO2UP.equals("") || NEWO2DOWN.equals("") || NEWPH3UP.equals("") || NEWPH3DOWN.equals("")) {
                    util.showToast(SmartLockActivity.this,"内容不能为空");
                } else if (NEWO2UP.equals("0") && NEWO2DOWN.equals("0") && NEWPH3UP.equals("0") && NEWPH3DOWN.equals("0")) {
                    util.showToast(SmartLockActivity.this,"内容不能同时为零");
                } else {
                    //下发耗时，所以需要锁屏操作
                    suoPingDialog = new SuoPingDialog(SmartLockActivity.this, "正在配置，请稍等......");
                    suoPingDialog.setCancelable(false);
                    suoPingDialog.show();
                    Data.getLockWarn().setO2WarnUp(Double.valueOf(NEWO2UP));
                    Data.getLockWarn().setO2WarnDown(Double.valueOf(NEWO2DOWN));
                    Data.getLockWarn().setPh3WarnUp(Double.valueOf(NEWPH3UP));
                    Data.getLockWarn().setPh3WarnDown(Double.valueOf(NEWPH3DOWN));
                    HardWareListDataPUT.getData().setGranarySetting(Data);
                    NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                    newDownRawBody.setUrl("/command-map/update");
                    newDownRawBody.setMethod("PUT");
                    newDownRawBody.setQuery(null);
                    newDownRawBody.setHeaders(null);
                    CountMultiple countMultiple = new CountMultiple();
                    countMultiple.setUrl(url);
                    countMultiple.setCommandMap(HardWareListDataPUT);
//                    countMultiple.setData(commandMapId);
//                    countMultiple.setVersion(HardWareListDataPUT.getVersion());
//                    countMultiple.setData(HardWareListDataPUT.getData());
                    newDownRawBody.setBody(countMultiple);
                    String jsondata = gson.toJson(newDownRawBody);
                    initPutAlert(jsondata, new OnInitPutAlertFinishedListener() {
                        @Override
                        public void OnInitPutAlertListener(boolean success) {
                            suoPingDialog.dismiss();
                            if (success) {
                                util.showToast(SmartLockActivity.this,"提交成功");
                                recreate();
                            } else {
                                util.showToast(SmartLockActivity.this,"提交失败");
                            }
                        }
                    });
                }
            }
        }).setNegativeButton("取消",null).show();
    }
    public interface OnInitPutAlertFinishedListener {
        void OnInitPutAlertListener(boolean success);
    }
    private void initPutAlert(String jsondata, final OnInitPutAlertFinishedListener listener){
        Log.d("jht", "initPutAlert: "+jsondata);
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindStatusBean codebean = new WindStatusBean();
                try {
                    codebean = gson.fromJson((String) result,WindStatusBean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    listener.OnInitPutAlertListener(false);
                    util.showToast(SmartLockActivity.this,codebean.getMsg());
                    return;
                }
                if (codebean.getCode()==200) {
                    listener.OnInitPutAlertListener(true);
                } else {
                    listener.OnInitPutAlertListener(false);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitPutAlertListener(false);
                util.showToast(SmartLockActivity.this,errorMsg);
            }
        });
    }
    private void showAlertDialog(ArrayList<GranaryListBean.Data> product) {
        Collections.sort(product, (o1, o2) -> o1.getGranaryName().compareTo(o2.getGranaryName()));
        List<String> NAME = new ArrayList<>();
        for (int i=0;i<product.size();i++){
            NAME.add(product.get(i).getGranaryName());
            if (product.get(i).getGranaryName().equals(txt_name.toString())){
                selectWhich=i;
            }
        }
        ArrayAdapter adapter_this = new ArrayAdapter<String>(this,R.layout.simple_list,NAME);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(adapter_this, selectWhich, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cangku = product.get(which).getGranaryName();
                selectWhich = which;
                Name = cangku;
                txt_name.setText(cangku);
                url = product.get(which).getUrl();
                commandMapId = product.get(which).getCommandMapId();
                suoPingDialog = new SuoPingDialog(SmartLockActivity.this,"正在操作，请稍等......");
                suoPingDialog.setCancelable(true);
                suoPingDialog.show();
                initSmartLock(JsonData, new OnInitSmartLockFinishedListener() {
                    @Override
                    public void OnInitSmartLockListener(String address, ArrayList<HardwareListData> datalist) {
                        if (address!=null && datalist!=null) {
                            NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
//                    newDownRawBody.setUrl("/hc/login");
                            newDownRawBody.setMethod("POST");
                            newDownRawBody.setUrl("/measure");
                            newDownRawBody.setQuery(null);
                            newDownRawBody.setHeaders(null);
                            NewSmartLockBody params = new NewSmartLockBody();
                            params.setCommandMapId(commandMapId);
                            params.setUrl(url);
                            params.setIp(GranaryData.getEntranceGuardIp());
                            params.setName(GranaryData.getEntranceGuardName());
                            params.setPwd(GranaryData.getEntranceGuardPwd());
                            params.setPort(GranaryData.getEntranceGuardPort());
//                    CountMultiple countMultiple = new CountMultiple();
//                    countMultiple.setCommandMapId(commandMapId);
//                    countMultiple.setMeasureType("-1");
//                    String addresslist = "AA55" + TESTING + "5C" + "FFFF" + "FFFF0D0A";
//                    ArrayList<String> ids = new ArrayList<>();
//                    for (HardwareListData data : datalist) {
//                        ids.add(data.getExtensionNumber());
//                    }
//                    CountMultiple.CommandJson commandJson = new CountMultiple.CommandJson();
//                    commandJson.setIds(ids);
//                    countMultiple.setCommandJson(commandJson);
//                    countMultiple.setCommandContent(addresslist);
//                    countMultiple.setUrl(url);
                            newDownRawBody.setBody(params);
                            String jsondata = gson.toJson(newDownRawBody);

                            //这里之后大概率会出问题，如果一个仓安装了两个智能门锁，这里就只会携带第一个的参数进入后续的操作中
                            initNewData(jsondata, datalist.get(0), new OnInitNewDataFinishedListener() {
                                @Override
                                public void OnInitNewDataListener(boolean success) {
                                    NewDownRawBodyTWO newDownRawBody1 = new NewDownRawBodyTWO();
                                    newDownRawBody1.setUrl("/hc/getAcsStatus");
                                    newDownRawBody1.setMethod("POST");
                                    newDownRawBody1.setQuery(null);
                                    newDownRawBody1.setHeaders(null);
                                    NewSmartLockBody params = new NewSmartLockBody();
                                    params.setCommandMapId(commandMapId);
                                    params.setUrl(url);
                                    params.setIp(GranaryData.getEntranceGuardIp());
                                    params.setName(GranaryData.getEntranceGuardName());
                                    params.setPwd(GranaryData.getEntranceGuardPwd());
                                    params.setPort(GranaryData.getEntranceGuardPort());
                                    CountMultiple countMultiple = new CountMultiple();
                                    countMultiple.setCommandMapId(commandMapId);
                                    countMultiple.setUrl(url);
                                    newDownRawBody1.setBody(countMultiple);
                                    String jsondata = gson.toJson(newDownRawBody1);

                                    initStatus(jsondata,datalist.get(0), new OnInitStatusFinishedListener() {
                                        @Override
                                        public void OnInitStatusListener(boolean success, SmartLockBody one) {
//                                    Log.d("jht", "OnInitSmartLockListener: "+one.getHardwareListData().getName());
                                            NewDownRawBodyTWO newDownRawBody2 = new NewDownRawBodyTWO();
                                            newDownRawBody2.setUrl("/measure");
                                            newDownRawBody2.setMethod("POST");
                                            newDownRawBody2.setQuery(null);
                                            newDownRawBody2.setHeaders(null);
                                            CountMultiple countMultiple = new CountMultiple();
                                            countMultiple.setCommandMapId(commandMapId);
                                            countMultiple.setMeasureType("-1");
                                            String addresslist = "AA55" + QITI + "5E" + "FFFF" + "FFFF0D0A";
                                            countMultiple.setCommandContent(addresslist);
                                            countMultiple.setUrl(url);
                                            newDownRawBody2.setBody(countMultiple);
                                            String jsondata = gson.toJson(newDownRawBody2);
                                            initGas(jsondata,one, new OnInitGasFinishedListener() {
                                                @Override
                                                public void OnInitGasListener(SmartLockBody success) {
                                                    Log.d("jht", "OnInitSmartLockListener: "+success.getHardwareListData().getName());
                                                    if (success == null) {
                                                        SmartLockList.add(one);
                                                    } else {
                                                        SmartLockList.add(success);
                                                    }
//                                            Log.d("jht", "OnInitGasListener: "+SmartLockList.get(0).getPh3List().get(0));
                                                    suoPingDialog.dismiss();
                                                    refreshView(SmartLockActivity.this,SmartLockList);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
                dialog.dismiss();
            }
        });
        AlertDialog dialogone = builder.create();
        dialogone.show();
        dialogone.setCanceledOnTouchOutside(true);
    }
}