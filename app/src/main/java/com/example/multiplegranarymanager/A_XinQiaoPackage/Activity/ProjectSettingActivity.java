package com.example.multiplegranarymanager.A_XinQiaoPackage.Activity;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ProjectSetting.GranarySettingList;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ProjectSetting.ProjectSettingBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.DeviceList;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.UpDateProject;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProjectSettingActivity extends AppCompatActivity implements View.OnClickListener{
    String url,commandMapId;
    Gson gson = new Gson();
    private EditText txt_command,txt_hour,txt_minuter,txt_commandMapId,txt_granaryName,txt_nature_wind_cha_tem,txt_nature_wind_out_tem,txt_down_wind_max_tem,txt_down_wind_out_tem,txt_down_wind_out_mou,txt_average_wind_cha_tem,txt_average_wind_ave_tem,txt_average_wind_out_tem;
    private TextView txt_reset,txt_submit,txt_nature_wind_device,txt_down_wind_device,txt_average_wind_device;
    private Switch switch_time,switch_smart_wind,switch_nature_wind,switch_down_wind,switch_average_wind;
    private SuoPingDialog suoPingDialog;
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private ProjectSettingBean.Data granaryData = new ProjectSettingBean.Data();
    private ArrayList<HardwareListData> devciceList = new ArrayList<>();
    private ArrayList<DeviceList> deviceList_Flag = new ArrayList<>();
    private List<String> NatureList = new ArrayList<>();
    private List<String> DownList = new ArrayList<>();
    private List<String> AveList = new ArrayList<>();
    private List<String> resultList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_setting);

        url = getIntent().getStringExtra("url");
        commandMapId = getIntent().getStringExtra("commandMapId");
        CountMultiple body = new CountMultiple();
        body.setUrl(url);
        NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setHeaders(null);
        Body.setUrl("/project-setting/get");
        Body.setBody(body);
        String json = gson.toJson(Body);
        suoPingDialog = new SuoPingDialog(this,"加载中,请稍等......");
        suoPingDialog.show();
        InitProject(json, new OnInitProjectFinishedListener() {
            @Override
            public void OnInitProjectListener(boolean success) {
                CountMultiple body2 = new CountMultiple();
                body2.setUrl(url);
                body2.setCommandMapId(commandMapId);
                NewDownRawBodyTWO Body2 = new NewDownRawBodyTWO();
                Body2.setQuery(null);
                Body2.setMethod("POST");
                Body2.setHeaders(null);
                Body2.setUrl("/command-map/get");
                Body2.setBody(body2);
                String json2 = gson.toJson(Body2);
                InitCommand(json2, new OnInitCommandFinishedListener() {
                    @Override
                    public void OnInitCommandListener(boolean success) {
                        suoPingDialog.dismiss();
                        initView();
                        for (HardwareListData data : devciceList) {
                            DeviceList deviceList = new DeviceList();
                            deviceList.setData(data);
                            deviceList.setFlag(false);
                            deviceList_Flag.add(deviceList);
                        }
                        Collections.sort(deviceList_Flag, new Comparator<DeviceList>() {
                            @Override
                            public int compare(DeviceList o1, DeviceList o2) {
                                return o1.getData().getName().compareTo(o2.getData().getName());
                            }
                        });
                    }
                });
            }
        });

    }
    private void initView() {

        txt_command = findViewById(R.id.txt_command);
        txt_hour = findViewById(R.id.txt_hour);
        txt_minuter = findViewById(R.id.txt_minuter);
        txt_commandMapId = findViewById(R.id.txt_commandMapId);
        txt_granaryName = findViewById(R.id.txt_granaryName);
        txt_nature_wind_cha_tem = findViewById(R.id.txt_nature_wind_cha_tem);
        txt_nature_wind_out_tem = findViewById(R.id.txt_nature_wind_out_tem);
        txt_down_wind_max_tem = findViewById(R.id.txt_down_wind_max_tem);
        txt_down_wind_out_tem = findViewById(R.id.txt_down_wind_out_tem);
        txt_down_wind_out_mou = findViewById(R.id.txt_down_wind_out_mou);
        txt_average_wind_cha_tem = findViewById(R.id.txt_average_wind_cha_tem);
        txt_average_wind_ave_tem = findViewById(R.id.txt_average_wind_ave_tem);
        txt_average_wind_out_tem = findViewById(R.id.txt_average_wind_out_tem);

        txt_reset = findViewById(R.id.txt_reset);
        txt_submit = findViewById(R.id.txt_submit);
        txt_nature_wind_device = findViewById(R.id.txt_nature_wind_device);
        txt_down_wind_device = findViewById(R.id.txt_down_wind_device);
        txt_average_wind_device = findViewById(R.id.txt_average_wind_device);

        switch_time = findViewById(R.id.switch_time);
        switch_smart_wind = findViewById(R.id.switch_smart_wind);
        switch_nature_wind = findViewById(R.id.switch_nature_wind);
        switch_down_wind = findViewById(R.id.switch_down_wind);
        switch_average_wind = findViewById(R.id.switch_average_wind);

        txt_reset.setOnClickListener(this);
        txt_submit.setOnClickListener(this);
        txt_nature_wind_device.setOnClickListener(this);
        txt_down_wind_device.setOnClickListener(this);
        txt_average_wind_device.setOnClickListener(this);

        if (granaryData!=null) {
            //判断指令版本
            txt_command.setText(granaryData.getVersion());
            //判断定时监测的开关
            if (granaryData.getTimingDetectSetting().getEnabled()) {
                switch_time.setChecked(true);
                switch_time.setText("开");
            } else {
                switch_time.setChecked(false);
                switch_time.setText("关");
            }
            //定时时间
            txt_hour.setText(String.valueOf(granaryData.getTimingDetectSetting().getHour()));
            txt_minuter.setText(String.valueOf(granaryData.getTimingDetectSetting().getMinute()));
            //判断仓
            for (GranarySettingList data : granaryData.getGranarySettingList()) {
                if (data.getCommandMapId().equals(commandMapId)) {
                    //显示仓名和映射文件ID
                    txt_commandMapId.setText(data.getCommandMapId());
                    txt_granaryName.setText(data.getGranaryName());
                    //判断智能通风开关
                    if (data.getIntelligentVentilationSetting().getEnabled()) {
                        switch_smart_wind.setText("开");
                        switch_smart_wind.setChecked(true);
                    } else {
                        switch_smart_wind.setText("关");
                        switch_smart_wind.setChecked(false);
                    }
                    //判断自然通风开关
                    if (data.getIntelligentVentilationSetting().getPatternV1Enabled()) {
                        switch_nature_wind.setText("开");
                        switch_nature_wind.setChecked(true);
                    } else {
                        switch_nature_wind.setText("关");
                        switch_nature_wind.setChecked(false);
                    }
                    //判断降温通风开关
                    if (data.getIntelligentVentilationSetting().getPatternV2Enabled()) {
                        switch_down_wind.setText("开");
                        switch_down_wind.setChecked(true);
                    } else {
                        switch_down_wind.setText("关");
                        switch_down_wind.setChecked(false);
                    }
                    //判断均温通风开关
                    if (data.getIntelligentVentilationSetting().getPatternV3Enabled()) {
                        switch_average_wind.setText("开");
                        switch_average_wind.setChecked(true);
                    } else {
                        switch_average_wind.setText("关");
                        switch_average_wind.setChecked(false);
                    }
                    //定义自然通风条件
                    txt_nature_wind_cha_tem.setText(data.getIntelligentVentilationSetting().getPatternV1TempOutMinusTempInnerDown().toString());
                    txt_nature_wind_out_tem.setText(data.getIntelligentVentilationSetting().getPatternV1HumidityOutDown().toString());
                    //定义降温通风条件
                    txt_down_wind_max_tem.setText(data.getIntelligentVentilationSetting().getPatternV2TempMaxUp().toString());
                    txt_down_wind_out_tem.setText(data.getIntelligentVentilationSetting().getPatternV2TempOutDown().toString());
                    txt_down_wind_out_mou.setText(data.getIntelligentVentilationSetting().getPatternV2HumidityOutDown().toString());
                    //定义均温通风条件
                    txt_average_wind_cha_tem.setText(data.getIntelligentVentilationSetting().getPatternV3TempDiffMaxUp().toString());
                    txt_average_wind_ave_tem.setText(data.getIntelligentVentilationSetting().getPatternV3TempAveUp().toString());
                    txt_average_wind_out_tem.setText(data.getIntelligentVentilationSetting().getPatternV3HumidityOutDown().toString());
                }
            }
        }
        //定时检测开关
        switch_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //打开
                    switch_time.setText("开");
                    util.showToast(ProjectSettingActivity.this,"打开定时检测");
                    granaryData.getTimingDetectSetting().setEnabled(true);
                } else {
                    //关闭
                    switch_time.setText("关");
                    util.showToast(ProjectSettingActivity.this,"关闭定时检测");
                    granaryData.getTimingDetectSetting().setEnabled(false);
                }
            }
        });
        //智能通风开关
        switch_smart_wind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //打开
                    switch_smart_wind.setText("开");
                    util.showToast(ProjectSettingActivity.this,"打开定时检测");
                    for (GranarySettingList data : granaryData.getGranarySettingList()) {
                        if (data.getCommandMapId().equals(commandMapId)) {
                            data.getIntelligentVentilationSetting().setEnabled(true);
                        }
                    }
                } else {
                    //关闭
                    switch_smart_wind.setText("关");
                    util.showToast(ProjectSettingActivity.this,"关闭定时检测");
                    for (GranarySettingList data : granaryData.getGranarySettingList()) {
                        if (data.getCommandMapId().equals(commandMapId)) {
                            data.getIntelligentVentilationSetting().setEnabled(false);
                        }
                    }
                }
            }
        });
        //自然通风开关
        switch_nature_wind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //打开
                    switch_nature_wind.setText("开");
                    util.showToast(ProjectSettingActivity.this,"打开定时检测");
                    for (GranarySettingList data : granaryData.getGranarySettingList()) {
                        if (data.getCommandMapId().equals(commandMapId)) {
                            data.getIntelligentVentilationSetting().setPatternV1Enabled(true);
                        }
                    }
                } else {
                    //关闭
                    switch_nature_wind.setText("关");
                    util.showToast(ProjectSettingActivity.this,"关闭定时检测");
                    for (GranarySettingList data : granaryData.getGranarySettingList()) {
                        if (data.getCommandMapId().equals(commandMapId)) {
                            data.getIntelligentVentilationSetting().setPatternV1Enabled(false);
                        }
                    }
                }
            }
        });
        //降温通风开关
        switch_down_wind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //打开
                    switch_down_wind.setText("开");
                    util.showToast(ProjectSettingActivity.this,"打开定时检测");
                    for (GranarySettingList data : granaryData.getGranarySettingList()) {
                        if (data.getCommandMapId().equals(commandMapId)) {
                            data.getIntelligentVentilationSetting().setPatternV2Enabled(true);
                        }
                    }
                } else {
                    //关闭
                    switch_down_wind.setText("关");
                    util.showToast(ProjectSettingActivity.this,"关闭定时检测");
                    for (GranarySettingList data : granaryData.getGranarySettingList()) {
                        if (data.getCommandMapId().equals(commandMapId)) {
                            data.getIntelligentVentilationSetting().setPatternV2Enabled(false);
                        }
                    }
                }
            }
        });
        //均温通风开关
        switch_average_wind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //打开
                    switch_average_wind.setText("开");
                    util.showToast(ProjectSettingActivity.this,"打开定时检测");
                    for (GranarySettingList data : granaryData.getGranarySettingList()) {
                        if (data.getCommandMapId().equals(commandMapId)) {
                            data.getIntelligentVentilationSetting().setPatternV3Enabled(true);
                        }
                    }
                } else {
                    //关闭
                    switch_average_wind.setText("关");
                    util.showToast(ProjectSettingActivity.this,"关闭定时检测");
                    for (GranarySettingList data : granaryData.getGranarySettingList()) {
                        if (data.getCommandMapId().equals(commandMapId)) {
                            data.getIntelligentVentilationSetting().setPatternV3Enabled(false);
                        }
                    }
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.txt_reset:
                recreate();
                break;
            case R.id.txt_submit:
                granaryData.getTimingDetectSetting().setHour(Integer.parseInt(txt_hour.getText().toString()));
                granaryData.getTimingDetectSetting().setMinute(Integer.parseInt(txt_minuter.getText().toString()));
                for (GranarySettingList data : granaryData.getGranarySettingList()) {
                    if (data.getCommandMapId().equals(commandMapId)) {

                        data.setCommandMapId(txt_commandMapId.getText().toString());
                        data.setGranaryName(txt_granaryName.getText().toString());

                        data.getIntelligentVentilationSetting().setPatternV1HardwareIdList(NatureList);
                        data.getIntelligentVentilationSetting().setPatternV1HumidityOutDown(Double.valueOf(txt_nature_wind_out_tem.getText().toString()));
                        data.getIntelligentVentilationSetting().setPatternV1TempOutMinusTempInnerDown(Double.valueOf(txt_nature_wind_cha_tem.getText().toString()));

                        data.getIntelligentVentilationSetting().setPatternV2HardwareIdList(DownList);
                        data.getIntelligentVentilationSetting().setPatternV2HumidityOutDown(Double.valueOf(txt_down_wind_out_mou.getText().toString()));
                        data.getIntelligentVentilationSetting().setPatternV2TempMaxUp(Double.valueOf(txt_down_wind_max_tem.getText().toString()));
                        data.getIntelligentVentilationSetting().setPatternV2TempOutDown(Double.valueOf(txt_down_wind_out_tem.getText().toString()));

                        data.getIntelligentVentilationSetting().setPatternV3HardwareIdList(AveList);
                        data.getIntelligentVentilationSetting().setPatternV3HumidityOutDown(Double.valueOf(txt_average_wind_out_tem.getText().toString()));
                        data.getIntelligentVentilationSetting().setPatternV3TempAveUp(Double.valueOf(txt_average_wind_ave_tem.getText().toString()));
                        data.getIntelligentVentilationSetting().setPatternV3TempDiffMaxUp(Double.valueOf(txt_average_wind_cha_tem.getText().toString()));
                    }
                }
                UpDateProject upDate = new UpDateProject();
                upDate.setVersion(granaryData.getVersion());
                upDate.setManagerUrl(granaryData.getManagerUrl());
                upDate.setTimingDetectSetting(granaryData.getTimingDetectSetting());
                upDate.setGranarySettingList(granaryData.getGranarySettingList());
                upDate.setSystemFuncSetting(granaryData.getSystemFuncSetting());
                NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
                Body.setQuery(null);
                Body.setMethod("PUT");
                Body.setHeaders(null);
                Body.setUrl("/project-setting");
                Body.setBody(upDate);
                String json = gson.toJson(Body);
                initUpdate(json, new OnInitUpdateFinishedListener() {
                    @Override
                    public void OninitUpdateListener(boolean success) {
                        if (success) {
                            recreate();
                        }
                    }
                });
                Log.d("zyq", "onClick:txt_submit "+deviceList_Flag.size());
                break;
            case R.id.txt_nature_wind_device:
                if (NatureList!=null) {
                    for (DeviceList data : deviceList_Flag) {
                        for (String data2 : NatureList) {
                            if (data.getData().getType().equals(data2)){
                                data.setFlag(true);
                            }
                        }
                    }
                }
                List<String> result1 = showListDialog();
                NatureList.clear();
                NatureList = result1;
                Log.d("zyq", "onClick:txt_nature_wind_device "+deviceList_Flag.size());
                break;
            case R.id.txt_down_wind_device:
                if (DownList!=null) {
                    for (DeviceList data : deviceList_Flag) {
                        for (String data2 : DownList) {
                            if (data.getData().getType().equals(data2)){
                                data.setFlag(true);
                            }
                        }
                    }
                }
                List<String> result2 = showListDialog();
                DownList.clear();
                DownList = result2;
                Log.d("zyq", "onClick:txt_down_wind_device "+deviceList_Flag.size());
                break;
            case R.id.txt_average_wind_device:
                if (AveList!=null) {
                    for (DeviceList data : deviceList_Flag) {
                        for (String data2 : AveList) {
                            if (data.getData().getType().equals(data2)){
                                data.setFlag(true);
                            }
                        }
                    }
                }
                List<String> result3 = showListDialog();
                AveList.clear();
                AveList = result3;
                Log.d("zyq", "onClick:txt_average_wind_device "+deviceList_Flag.size());
                break;
            default:
                break;
        }
    }
    public interface OnInitUpdateFinishedListener {
        void OninitUpdateListener(boolean success);
    }
    public void initUpdate(String jsondata, final OnInitUpdateFinishedListener listener){
        suoPingDialog = new SuoPingDialog(this,"加载中,请稍等......");
        suoPingDialog.show();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                ProjectSettingBean bean = new ProjectSettingBean();
                try {
                    bean = gson.fromJson((String) result,ProjectSettingBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    listener.OninitUpdateListener(false);
                    util.showToast(ProjectSettingActivity.this,"数据解析出错");
                    e.printStackTrace();
                    return;
                }
                if (bean.getCode()==200) {
                    util.showToast(ProjectSettingActivity.this,"修改成功");
                    listener.OninitUpdateListener(true);
                } else {
                    util.showToast(ProjectSettingActivity.this,"修改失败");
                    listener.OninitUpdateListener(false);
                }
                suoPingDialog.dismiss();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OninitUpdateListener(false);
                util.showToast(ProjectSettingActivity.this,errorMsg);
            }
        });
    }
    private List<String> showListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.list_item_layout,null);
        ListView listView = dialogView.findViewById(R.id.list_view);
        //创建自定义适配器(也可以使用ArrayAdapter简单设置文本)
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        builder.setView(dialogView);
        builder.setTitle("请勾选相应的设备硬件");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resultList.clear();
                for (DeviceList data : deviceList_Flag) {
                    if (data.getFlag()){
                        resultList.add(data.getData().getId());
                    }
                }
                Collections.sort(resultList, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                });
                for (String data : resultList) {
                    Log.d("zyq", "onClick: "+data);
                }
            }
        });
        builder.setNegativeButton("取消",null);
        AlertDialog dialog = builder.create();
        dialog.show();
        return resultList;
    }
    public interface OnInitProjectFinishedListener {
        void OnInitProjectListener(boolean success);
    }
    public void InitProject(String jsondata, final OnInitProjectFinishedListener listener) {
        Log.d("jht", "InitProject: "+jsondata);
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                ProjectSettingBean bean = new ProjectSettingBean();
                try {
                    bean = gson.fromJson((String) result, ProjectSettingBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    listener.OnInitProjectListener(false);
                    util.showToast(ProjectSettingActivity.this, "数据解析出错");
                    e.printStackTrace();
                    return;
                }
                if (bean.getData() != null) {
                    if (bean.getData().getGranarySettingList() != null && bean.getData().getGranarySettingList().size() > 0) {
                        granaryData = bean.getData();
                        for (GranarySettingList data : bean.getData().getGranarySettingList()) {
                            if (data.getCommandMapId().equals(commandMapId)) {
                                NatureList = data.getIntelligentVentilationSetting().getPatternV1HardwareIdList();
                                DownList = data.getIntelligentVentilationSetting().getPatternV2HardwareIdList();
                                AveList = data.getIntelligentVentilationSetting().getPatternV3HardwareIdList();
                            }
                        }
                        listener.OnInitProjectListener(true);
                    } else {
                        listener.OnInitProjectListener(false);
                    }
                } else {
                    listener.OnInitProjectListener(false);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OnInitProjectListener(false);
                util.showToast(ProjectSettingActivity.this, errorMsg);
            }
        });
    }
    public interface OnInitCommandFinishedListener {
        void OnInitCommandListener(boolean success);
    }
    public void InitCommand(String jsondata, final OnInitCommandFinishedListener listener) {
        devciceList.clear();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                CommandMapBean bean = new CommandMapBean();
                try {
                    bean = gson.fromJson((String) result,CommandMapBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    listener.OnInitCommandListener(false);
                    util.showToast(ProjectSettingActivity.this,"数据解析出错");
                    e.printStackTrace();
                    return;
                }
                if (bean.getData()!=null && bean.getData().getData()!=null) {
                    if (bean.getData().getData().getHardwareList()!=null && bean.getData().getData().getHardwareList().size()>0) {
                        for (HardwareListData data : bean.getData().getData().getHardwareList()) {
                            if (data.getType().equals("01") || data.getType().equals("02")) {
                                devciceList.add(data);
                            }
                        }
                        listener.OnInitCommandListener(true);
                    } else {
                        listener.OnInitCommandListener(false);
                    }
                } else {
                    listener.OnInitCommandListener(false);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OnInitCommandListener(false);
                util.showToast(ProjectSettingActivity.this,errorMsg);
            }
        });
    }
    private class MyAdapter extends ArrayAdapter<DeviceList> {
        public MyAdapter() {
            super(ProjectSettingActivity.this,R.layout.list_item,deviceList_Flag);
        }
        @Override
        public View getView(final int position, View convertView, android.view.ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                LayoutInflater inflater = ProjectSettingActivity.this.getLayoutInflater();
                itemView = inflater.inflate(R.layout.list_item,parent,false);
            }
            TextView textView = itemView.findViewById(R.id.txt_name);
            CheckBox checkBox = itemView.findViewById(R.id.checked);
            textView.setText(deviceList_Flag.get(position).getData().getName());
            checkBox.setChecked(deviceList_Flag.get(position).getFlag());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deviceList_Flag.get(position).setFlag(((CheckBox) v).isChecked());
                }
            });
            return itemView;
        }
    }
}