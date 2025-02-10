package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity;

import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.deviceType;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap.CommandListData;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap.CommandMapData;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap.HardwareListData;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap.InspurChannelNumberMap;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.ProjectSetting.ProjectSettingBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.UpDateCommand;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CommandSettingActivity extends AppCompatActivity implements View.OnClickListener{
    String url,commandMapId;
    Gson gson = new Gson();
    private SuoPingDialog suoPingDialog;
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private EditText et_moduleName, et_granaryId, et_granaryName,  et_tem_zu,et_tem_lan,et_tem_ceng, et_mou_zu,et_mou_lan,et_mou_ceng, et_langchao_address, et_strobeExtensionNumber,et_insectExtensionNumber,et_insectPumpExtensionNumber, et_gasExtensionNumber,et_gasChannelNumber,et_gasPumpExtensionNumber;
    private Spinner sp_mou_type,sp_tem_type,sp_grainType;
    private TextView txt_submit,txt_reset, txt_add_device,txt_add_langchao_address, txt_insectPumpChannelNumberlist,txt_gasPumpChannelNumberlist;
    private ListView list_device,list_langchao_address;
    private CommandMapData CommandMapData = new CommandMapData();
    private CommandMapData CommandMapData_FLAG = new CommandMapData();
    private List<HardwareListData> HardwareList = new ArrayList<>();
    private ArrayList<InspurChannelNumberMap> InspurChannelMap = new ArrayList<>();
    private CommandMapBean.Data BODY = new CommandMapBean.Data();
    private String[] GRANARYLIST = new String[] {"平房仓","筒仓"};
    private String[] GRAINTYPE = new String[] {"小麦","稻谷","大米","玉米","黍子","大豆"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_setting);
        initView();
        url = getIntent().getStringExtra("url");
        commandMapId = getIntent().getStringExtra("commandMapId");
        CountMultiple body = new CountMultiple();
        body.setUrl(url);
        body.setCommandMapId(commandMapId);
        NewDownRawBody Body = new NewDownRawBody();
        Body.setQuery("");
        Body.setMethod("POST");
        Body.setHeaders("");
        Body.setUrl("/command-map/get");
        Body.setBody(body);
        String json = gson.toJson(Body);
        suoPingDialog = new SuoPingDialog(this,"加载中,请稍等......");
        suoPingDialog.show();
        InitCommand(json, new ProjectSettingActivity.OnInitCommandFinishedListener() {
            @Override
            public void OnInitCommandListener(boolean success) {
                suoPingDialog.dismiss();
                CommandMapData_FLAG = CommandMapData;
                initData(CommandMapData_FLAG);
            }
        });
    }

    private void initData(CommandMapData dataone) {
        ArrayAdapter<String> granaryAdapter = new ArrayAdapter<>(this,R.layout.list_spinner,GRANARYLIST);
        granaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_mou_type.setAdapter(granaryAdapter);
        sp_tem_type.setAdapter(granaryAdapter);

        ArrayAdapter<String> grainAdapter = new ArrayAdapter<>(this,R.layout.list_spinner,GRAINTYPE);
        grainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_grainType.setAdapter(grainAdapter);

        et_moduleName.setText(dataone.getModuleName().toString());
        et_granaryId.setText(dataone.getGranaryId().toString());
        int a = 0;
        et_granaryName.setText(dataone.getGranarySetting().getName().toString());
        if (dataone.getGranarySetting().getGrainType()!=null) {
            for (int i=0;i<GRAINTYPE.length;i++){
                if (GRAINTYPE[i].equals(dataone.getGranarySetting().getGrainType())){
                    a = i;
                }
            }
            sp_grainType.setSelection(a);
            sp_grainType.invalidate();
        }
        String type = "";
        if (dataone.getGranarySetting().getType()!=null) {
            if (dataone.getGranarySetting().getType().equals("PFC")){
                type = "平房仓";
                for (int i=0;i<GRANARYLIST.length;i++){
                    if (GRANARYLIST[i].equals(type)){
                        a = i;
                    }
                }
                sp_tem_type.setSelection(a);
                sp_mou_type.setSelection(a);
                sp_tem_type.invalidate();
                sp_mou_type.invalidate();
            } else if (dataone.getGranarySetting().getType().equals("TC")) {
                type = "筒仓";
                for (int i=0;i<GRANARYLIST.length;i++){
                    if (GRANARYLIST[i].equals(type)){
                        a = i;
                    }
                }
                sp_tem_type.setSelection(a);
                sp_mou_type.setSelection(a);
                sp_tem_type.invalidate();
                sp_mou_type.invalidate();
            }
        }
        if (dataone.getGranarySetting().getZu()!=null) {
            et_tem_zu.setText(dataone.getGranarySetting().getZu().toString());
        }
        if (dataone.getGranarySetting().getLan()!=null) {
            et_tem_lan.setText(dataone.getGranarySetting().getLan().toString());
        }
        if (dataone.getGranarySetting().getCeng()!=null) {
            et_tem_ceng.setText(dataone.getGranarySetting().getCeng().toString());
        }
        if (dataone.getGranarySetting().getMoistureZu()!=null) {
            et_mou_ceng.setText(dataone.getGranarySetting().getMoistureZu().toString());
        }
        if (dataone.getGranarySetting().getMoistureLan()!=null) {
            et_mou_ceng.setText(dataone.getGranarySetting().getMoistureLan().toString());
        }
        if (dataone.getGranarySetting().getMoistureCeng()!=null) {
            et_mou_ceng.setText(dataone.getGranarySetting().getMoistureCeng().toString());
        }
        if (dataone.getGasInsectInfo()!=null){
            if (dataone.getGasInsectInfo().getInspurAddress()!=null) {
                et_langchao_address.setText(dataone.getGasInsectInfo().getInspurAddress().toString());
            }
            if (dataone.getGasInsectInfo().getStrobeExtensionNumber()!=null) {
                et_strobeExtensionNumber.setText(dataone.getGasInsectInfo().getStrobeExtensionNumber().toString());
            }
            if (dataone.getGasInsectInfo().getInsectExtensionNumber()!=null) {
                et_insectExtensionNumber.setText(dataone.getGasInsectInfo().getInsectExtensionNumber().toString());
            }
            if (dataone.getGasInsectInfo().getInsectPumpExtensionNumber()!=null) {
                et_insectPumpExtensionNumber.setText(dataone.getGasInsectInfo().getInsectPumpExtensionNumber().toString());
            }
            if (dataone.getGasInsectInfo().getGasExtensionNumber()!=null) {
                et_gasExtensionNumber.setText(dataone.getGasInsectInfo().getGasExtensionNumber().toString());
            }
            if (dataone.getGasInsectInfo().getGasChannelNumber()!=null) {
                et_gasChannelNumber.setText(dataone.getGasInsectInfo().getGasChannelNumber().toString());
            }
            if (dataone.getGasInsectInfo().getGasPumpExtensionNumber()!=null) {
                et_gasPumpExtensionNumber.setText(dataone.getGasInsectInfo().getGasPumpExtensionNumber().toString());
            }
        }

        DeviceAdapter deviceAdapter = new DeviceAdapter(this,HardwareList);
        list_device.setAdapter(deviceAdapter);

        AddressAdapter addressAdapter = new AddressAdapter(this,InspurChannelMap);
        list_langchao_address.setAdapter(addressAdapter);
    }
    public void InitCommand(String jsondata, final ProjectSettingActivity.OnInitCommandFinishedListener listener) {
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=30", jsondata, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                CommandMapBean bean = new CommandMapBean();
                try {
                    bean = gson.fromJson((String) result,CommandMapBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    listener.OnInitCommandListener(false);
                    util.showToast(CommandSettingActivity.this,"数据解析出错");
                    e.printStackTrace();
                    return;
                }
                if (bean.getData()!=null && bean.getData().getData()!=null) {
                    BODY = bean.getData();
                    CommandMapData = bean.getData().getData();
                    if (bean.getData().getData().getHardwareList()!=null) {
                        HardwareList = bean.getData().getData().getHardwareList();
                    }
                    if (bean.getData().getData().getGasInsectInfo().getInspurChannelNumberMap()!=null){
                        for (int i=1;i<=bean.getData().getData().getGasInsectInfo().getInspurChannelNumberMap().size();i++) {
                            bean.getData().getData().getGasInsectInfo().getInspurChannelNumberMap().get(i-1).setId(i);
                        }
                        InspurChannelMap = bean.getData().getData().getGasInsectInfo().getInspurChannelNumberMap();
                    }
                    listener.OnInitCommandListener(true);
                } else {
                    listener.OnInitCommandListener(false);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OnInitCommandListener(false);
                util.showToast(CommandSettingActivity.this,errorMsg);
            }
        });
    }
    private void initView() {

        et_moduleName = findViewById(R.id.et_moduleName);
        et_granaryId = findViewById(R.id.et_granaryid);
        et_granaryName = findViewById(R.id.et_granaryName);
        et_tem_zu = findViewById(R.id.et_tem_zu);
        et_tem_lan = findViewById(R.id.et_tem_lan);
        et_tem_ceng = findViewById(R.id.et_tem_ceng);
        et_mou_zu = findViewById(R.id.et_mou_zu);
        et_mou_lan = findViewById(R.id.et_mou_lan);
        et_mou_ceng = findViewById(R.id.et_mou_ceng);
        et_langchao_address = findViewById(R.id.et_langchao_address);
        et_strobeExtensionNumber = findViewById(R.id.et_strobeExtensionNumber);
        et_insectExtensionNumber = findViewById(R.id.et_insectExtensionNumber);
        et_insectPumpExtensionNumber = findViewById(R.id.et_insectPumpExtensionNumber);
        et_gasExtensionNumber = findViewById(R.id.et_gasExtensionNumber);
        et_gasChannelNumber = findViewById(R.id.et_gasChannelNumber);
        et_gasPumpExtensionNumber = findViewById(R.id.et_gasPumpExtensionNumber);

        sp_grainType = findViewById(R.id.sp_grainType);
        sp_tem_type = findViewById(R.id.sp_tem_type);
        sp_mou_type = findViewById(R.id.sp_mou_type);

        txt_submit = findViewById(R.id.txt_submit);
        txt_reset = findViewById(R.id.txt_reset);
        txt_add_device = findViewById(R.id.txt_add_device);
        txt_add_langchao_address = findViewById(R.id.txt_add_langchao_address);
        txt_insectPumpChannelNumberlist = findViewById(R.id.txt_insectPumpChannelNumberList);
        txt_gasPumpChannelNumberlist = findViewById(R.id.txt_gasPumpChannelNumberList);

        list_device = findViewById(R.id.list_device);
        list_langchao_address = findViewById(R.id.list_langchao_address);

        txt_submit.setOnClickListener(this);
        txt_reset.setOnClickListener(this);
        txt_add_device.setOnClickListener(this);
        txt_add_langchao_address.setOnClickListener(this);
        txt_insectPumpChannelNumberlist.setOnClickListener(this);
        txt_gasPumpChannelNumberlist.setOnClickListener(this);

        et_strobeExtensionNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) {
                        s.delete(i, i + 1);
                        util.showToast(CommandSettingActivity.this,"只能输入十六进制数字");
                    }
                }
            }
        });
        et_insectExtensionNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) {
                        s.delete(i, i + 1);
                        util.showToast(CommandSettingActivity.this,"只能输入十六进制数字");
                    }
                }
            }
        });
        et_insectPumpExtensionNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) {
                        s.delete(i, i + 1);
                        util.showToast(CommandSettingActivity.this,"只能输入十六进制数字");
                    }
                }
            }
        });
        et_gasExtensionNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) {
                        s.delete(i, i + 1);
                        util.showToast(CommandSettingActivity.this,"只能输入十六进制数字");
                    }
                }
            }
        });
        et_gasChannelNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) {
                        s.delete(i, i + 1);
                        util.showToast(CommandSettingActivity.this,"只能输入十六进制数字");
                    }
                }
            }
        });
        et_gasPumpExtensionNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) {
                        s.delete(i, i + 1);
                        util.showToast(CommandSettingActivity.this,"只能输入十六进制数字");
                    }
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.txt_submit:

                CommandMapData.getGranarySetting().setType(sp_grainType.getSelectedItem().toString());

                if (sp_tem_type.getSelectedItem().toString().equals("平房仓")){
                    CommandMapData.getGranarySetting().setType("PFC");
                } else if (sp_tem_type.getSelectedItem().toString().equals("筒仓")) {
                    CommandMapData.getGranarySetting().setType("TC");
                }

                CommandMapData.getGranarySetting().setZu(Integer.valueOf(String.valueOf(et_tem_zu.getText())));
                CommandMapData.getGranarySetting().setLan(Integer.valueOf(String.valueOf(et_tem_lan.getText())));
                CommandMapData.getGranarySetting().setCeng(Integer.valueOf(String.valueOf(et_tem_ceng.getText())));
                CommandMapData.getGranarySetting().setMoistureZu(Integer.valueOf(String.valueOf(et_mou_zu.getText())));
                CommandMapData.getGranarySetting().setMoistureLan(Integer.valueOf(String.valueOf(et_mou_lan.getText())));
                CommandMapData.getGranarySetting().setMoistureCeng(Integer.valueOf(String.valueOf(et_mou_ceng.getText())));

                CommandMapData.getGasInsectInfo().setInspurAddress(et_langchao_address.getText().toString());
                CommandMapData.getGasInsectInfo().setStrobeExtensionNumber(et_strobeExtensionNumber.getText().toString());
                CommandMapData.getGasInsectInfo().setInsectExtensionNumber(et_insectExtensionNumber.getText().toString());
                CommandMapData.getGasInsectInfo().setInsectPumpExtensionNumber(et_insectPumpExtensionNumber.getText().toString());
                CommandMapData.getGasInsectInfo().setGasExtensionNumber(et_gasExtensionNumber.getText().toString());
                CommandMapData.getGasInsectInfo().setGasChannelNumber(et_gasChannelNumber.getText().toString());
                CommandMapData.getGasInsectInfo().setGasPumpExtensionNumber(et_gasPumpExtensionNumber.getText().toString());

                CommandMapData.setHardwareList(HardwareList);
                CommandMapData.getGasInsectInfo().setInspurChannelNumberMap(InspurChannelMap);

                BODY.setData(CommandMapData);

                UpDateCommand upDate = new UpDateCommand();
                upDate.setCommandMap(BODY);
                upDate.setUrl(url);
                NewDownRawBody Body = new NewDownRawBody();
                Body.setQuery("");
                Body.setMethod("PUT");
                Body.setHeaders("");
                Body.setUrl("/command-map/update");
                Body.setBody(upDate);
                String json = gson.toJson(Body);
                initUpdate(json, new ProjectSettingActivity.OnInitUpdateFinishedListener() {
                    @Override
                    public void OninitUpdateListener(boolean success) {
                        if (success) {
                            recreate();
                        }
                    }
                });
                break;
            case R.id.txt_reset:
                recreate();
                break;
            case R.id.txt_add_device:
                util.showToast(this,"该功能未开发");
                break;
            case R.id.txt_add_langchao_address:
                util.showToast(this,"该功能未开发");
                break;
            case R.id.txt_insectPumpChannelNumberList:
                util.showToast(this,"该功能未开发");
                break;
            case R.id.txt_gasPumpChannelNumberList:
                util.showToast(this,"该功能未开发");
                break;
            default:
                break;
        }
    }

    private void initUpdate(String json, ProjectSettingActivity.OnInitUpdateFinishedListener listener) {
        suoPingDialog = new SuoPingDialog(this,"加载中,请稍等......");
        suoPingDialog.show();
        OkHttpUtil.Put("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=30", json, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                ProjectSettingBean bean = new ProjectSettingBean();
                try {
                    bean = gson.fromJson((String) result,ProjectSettingBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    listener.OninitUpdateListener(false);
                    util.showToast(CommandSettingActivity.this,"数据解析出错");
                    e.printStackTrace();
                    return;
                }
                if (bean.getCode()==200) {
                    util.showToast(CommandSettingActivity.this,"修改成功");
                    listener.OninitUpdateListener(true);
                } else {
                    util.showToast(CommandSettingActivity.this,"修改失败");
                    listener.OninitUpdateListener(false);
                }
                suoPingDialog.dismiss();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OninitUpdateListener(false);
                util.showToast(CommandSettingActivity.this,errorMsg);
            }
        });
    }

    public class DeviceAdapter extends BaseAdapter {
        private List<HardwareListData> hardwareList;
        private Context context;
        private LinearLayout device_01, device_02, device_03, device_04;
        private TextView
                txt_01_id,txt_01_type,
                txt_02_id,txt_02_type,
                txt_03_id,txt_03_type,
                txt_04_id,txt_04_type;
        private EditText
                et_01_name,et_01_langchao_address,et_01_fengji_address,et_01_channelNumber,et_01_channelNumber_two,et_01_open,et_01_close,
                et_02_name,et_02_langchao_address,et_02_fengji_address,et_02_channelNumber,et_02_channelNumber_two,et_02_id,
                et_03_name,et_03_langchao_address,et_03_fengji_address,et_03_channelNumber,
                et_04_name,et_04_langchao_address,et_04_fengji_address;

        public DeviceAdapter(Context mcontext, List<HardwareListData> mhardwareList) {
            this.hardwareList = mhardwareList;
            this.context = mcontext;
        }

        @Override
        public int getCount() {
            return hardwareList.size();
        }

        @Override
        public Object getItem(int position) {
            return hardwareList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_view_device_list_two,parent,false);

            device_01 = convertView.findViewById(R.id.device_01);
            device_02 = convertView.findViewById(R.id.device_02);
            device_03 = convertView.findViewById(R.id.device_03);
            device_04 = convertView.findViewById(R.id.device_04);

            txt_01_id = convertView.findViewById(R.id.txt_01_id);
            txt_01_type = convertView.findViewById(R.id.txt_01_type);
            et_01_name = convertView.findViewById(R.id.et_01_name);
            et_01_langchao_address = convertView.findViewById(R.id.et_01_langchao_address);
            et_01_fengji_address = convertView.findViewById(R.id.et_01_fengji_address);
            et_01_channelNumber = convertView.findViewById(R.id.et_01_channelNumber);
            et_01_channelNumber_two = convertView.findViewById(R.id.et_01_channelNumber_two);
            et_01_open = convertView.findViewById(R.id.et_01_open);
            et_01_close = convertView.findViewById(R.id.et_01_close);

            txt_02_id = convertView.findViewById(R.id.txt_02_id);
            txt_02_type = convertView.findViewById(R.id.txt_02_type);
            et_02_name = convertView.findViewById(R.id.et_02_name);
            et_02_langchao_address = convertView.findViewById(R.id.et_02_langchao_address);
            et_02_fengji_address = convertView.findViewById(R.id.et_02_fengji_address);
            et_02_channelNumber = convertView.findViewById(R.id.et_02_channelNumber);
            et_02_channelNumber_two = convertView.findViewById(R.id.et_02_channelNumber_two);
            et_02_id = convertView.findViewById(R.id.et_02_id);

            txt_03_id = convertView.findViewById(R.id.txt_03_id);
            txt_03_type = convertView.findViewById(R.id.txt_03_type);
            et_03_name = convertView.findViewById(R.id.et_03_name);
            et_03_langchao_address = convertView.findViewById(R.id.et_03_langchao_address);
            et_03_fengji_address = convertView.findViewById(R.id.et_03_fengji_address);
            et_03_channelNumber = convertView.findViewById(R.id.et_03_channelNumber);

            txt_04_id = convertView.findViewById(R.id.txt_04_id);
            txt_04_type = convertView.findViewById(R.id.txt_04_type);
            et_04_name = convertView.findViewById(R.id.et_04_name);
            et_04_langchao_address = convertView.findViewById(R.id.et_04_langchao_address);
            et_04_fengji_address = convertView.findViewById(R.id.et_04_fengji_address);

            HardwareListData data = hardwareList.get(position);
            initVisibility(data.getType(),data);

            initOnChange(position);

            return convertView;
        }

        private void initOnChange(int position) {
            et_01_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setName(s.toString());
                    HardwareList.get(position).setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_01_langchao_address.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setInspurAddress(s.toString());
                    HardwareList.get(position).setInspurAddress(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_01_fengji_address.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setExtensionNumber(s.toString());
                    HardwareList.get(position).setExtensionNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_01_channelNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setChannelNumber(s.toString());
                    HardwareList.get(position).setChannelNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_01_channelNumber_two.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setChannelSecNumber(s.toString());
                    HardwareList.get(position).setChannelSecNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_01_open.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setChannelOpenStatusNumber(s.toString());
                    HardwareList.get(position).setChannelOpenStatusNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_01_close.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setChannelCloseStatusNumber(s.toString());
                    HardwareList.get(position).setChannelCloseStatusNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            et_02_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setName(s.toString());
                    HardwareList.get(position).setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_02_langchao_address.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setInspurAddress(s.toString());
                    HardwareList.get(position).setInspurAddress(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_02_fengji_address.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setExtensionNumber(s.toString());
                    HardwareList.get(position).setExtensionNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_02_channelNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setChannelNumber(s.toString());
                    HardwareList.get(position).setChannelNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_02_channelNumber_two.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setChannelSecNumber(s.toString());
                    HardwareList.get(position).setChannelSecNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_02_id.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setFanMapWindowId(s.toString());
                    HardwareList.get(position).setFanMapWindowId(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            et_03_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setName(s.toString());
                    HardwareList.get(position).setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_03_langchao_address.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setInspurAddress(s.toString());
                    HardwareList.get(position).setInspurAddress(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_03_fengji_address.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setExtensionNumber(s.toString());
                    HardwareList.get(position).setExtensionNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_03_channelNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setChannelNumber(s.toString());
                    HardwareList.get(position).setChannelNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            et_04_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setName(s.toString());
                    HardwareList.get(position).setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_04_langchao_address.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setInspurAddress(s.toString());
                    HardwareList.get(position).setInspurAddress(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_04_fengji_address.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    hardwareList.get(position).setExtensionNumber(s.toString());
                    HardwareList.get(position).setExtensionNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        private void initVisibility(String type, HardwareListData data) {
            device_01.setVisibility(View.GONE);
            device_02.setVisibility(View.GONE);
            device_03.setVisibility(View.GONE);
            device_04.setVisibility(View.GONE);
            if (type.equals("01")) {
                device_01.setVisibility(View.VISIBLE);

                txt_01_id.setText(data.getId());
                txt_01_type.setText("通风门窗");
                et_01_name.setText(data.getName());
                et_01_langchao_address.setText(data.getInspurAddress());
                et_01_fengji_address.setText(data.getExtensionNumber());
                et_01_channelNumber.setText(data.getChannelNumber());
                et_01_channelNumber_two.setText(data.getChannelSecNumber());
                et_01_open.setText(data.getChannelOpenStatusNumber());
                et_01_close.setText(data.getChannelCloseStatusNumber());
            } else if (type.equals("02")) {
                device_02.setVisibility(View.VISIBLE);

                txt_02_id.setText(data.getId());
                txt_02_type.setText("通风风机");
                et_02_name.setText(data.getName());
                et_02_langchao_address.setText(data.getInspurAddress());
                et_02_fengji_address.setText(data.getExtensionNumber());
                et_02_channelNumber.setText(data.getChannelNumber());
                et_02_channelNumber_two.setText(data.getChannelBadStatusNumber());
                if (data.getFanMapWindowId()!=null) {
                    et_02_id.setText(data.getFanMapWindowId());
                }
            } else if (type.equals("03")) {
                device_03.setVisibility(View.VISIBLE);

                txt_03_id.setText(data.getId());
                txt_03_type.setText("照明");
                et_03_name.setText(data.getName());
                et_03_langchao_address.setText(data.getInspurAddress());
                et_03_fengji_address.setText(data.getExtensionNumber());
                et_03_channelNumber.setText(data.getChannelNumber());
            } else if (type.equals("04")) {
                device_04.setVisibility(View.VISIBLE);

                txt_04_id.setText(data.getId());
                txt_04_type.setText("空调");
                et_04_name.setText(data.getName());
                et_04_langchao_address.setText(data.getInspurAddress());
                et_04_fengji_address.setText(data.getExtensionNumber());
            } else if (type.equals("05")) {
                device_04.setVisibility(View.VISIBLE);

                txt_04_id.setText(data.getId());
                txt_04_type.setText("能耗");
                et_04_name.setText(data.getName());
                et_04_langchao_address.setText(data.getInspurAddress());
                et_04_fengji_address.setText(data.getExtensionNumber());
            } else if (type.equals("06")) {
                device_04.setVisibility(View.VISIBLE);

                txt_04_id.setText(data.getId());
                txt_04_type.setText("气象站");
                et_04_name.setText(data.getName());
                et_04_langchao_address.setText(data.getInspurAddress());
                et_04_fengji_address.setText(data.getExtensionNumber());
            } else if (type.equals("07")) {
                device_04.setVisibility(View.VISIBLE);

                txt_04_id.setText(data.getId());
                txt_04_type.setText("粮食数量");
                et_04_name.setText(data.getName());
                et_04_langchao_address.setText(data.getInspurAddress());
                et_04_fengji_address.setText(data.getExtensionNumber());
            } else if (type.equals("08")) {
                device_01.setVisibility(View.VISIBLE);

                txt_01_id.setText(data.getId());
                txt_01_type.setText("气调阀门");
                et_01_name.setText(data.getName());
                et_01_langchao_address.setText(data.getInspurAddress());
                et_01_fengji_address.setText(data.getExtensionNumber());
                et_01_channelNumber.setText(data.getChannelNumber());
                et_01_channelNumber_two.setText(data.getChannelSecNumber());
                et_01_open.setText(data.getChannelOpenStatusNumber());
                et_01_close.setText(data.getChannelCloseStatusNumber());
            } else if (type.equals("09")) {
                device_04.setVisibility(View.VISIBLE);

                txt_04_id.setText(data.getId());
                txt_04_type.setText("气调氮气机");
                et_04_name.setText(data.getName());
                et_04_langchao_address.setText(data.getInspurAddress());
                et_04_fengji_address.setText(data.getExtensionNumber());
            } else if (type.equals("10")) {
                device_04.setVisibility(View.VISIBLE);

                txt_04_id.setText(data.getId());
                txt_04_type.setText("气调风机");
                et_04_name.setText(data.getName());
                et_04_langchao_address.setText(data.getInspurAddress());
                et_04_fengji_address.setText(data.getExtensionNumber());
            } else if (type.equals("11")) {
                device_04.setVisibility(View.VISIBLE);

                txt_04_id.setText(data.getId());
                txt_04_type.setText("气调气密性");
                et_04_name.setText(data.getName());
                et_04_langchao_address.setText(data.getInspurAddress());
                et_04_fengji_address.setText(data.getExtensionNumber());
            } else if (type.equals("12")) {
                device_04.setVisibility(View.VISIBLE);

                txt_04_id.setText(data.getId());
                txt_04_type.setText("气调压力");
                et_04_name.setText(data.getName());
                et_04_langchao_address.setText(data.getInspurAddress());
                et_04_fengji_address.setText(data.getExtensionNumber());
            } else if (type.equals("13")) {
                device_04.setVisibility(View.VISIBLE);

                txt_04_id.setText(data.getId());
                txt_04_type.setText("气调气体");
                et_04_name.setText(data.getName());
                et_04_langchao_address.setText(data.getInspurAddress());
                et_04_fengji_address.setText(data.getExtensionNumber());
            } else if (type.equals("14")) {
                device_04.setVisibility(View.VISIBLE);

                txt_04_id.setText(data.getId());
                txt_04_type.setText("智能门锁");
                et_04_name.setText(data.getName());
                et_04_langchao_address.setText(data.getInspurAddress());
                et_04_fengji_address.setText(data.getExtensionNumber());
            }
        }
    }

    private class AddressAdapter extends BaseAdapter {
        private List<InspurChannelNumberMap> InspurChannel;
        private Context context;
        private TextView txt_id;
        private EditText et_langchao_Number,et_channel_Number;
        public AddressAdapter(Context mcontext, List<InspurChannelNumberMap> mhardwareList) {
            this.InspurChannel = mhardwareList;
            this.context = mcontext;
        }
        @Override
        public int getCount() {
            return InspurChannel.size();
        }

        @Override
        public Object getItem(int position) {
            return InspurChannel.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_view_adapter_address,parent,false);

            txt_id = convertView.findViewById(R.id.txt_id);
            et_langchao_Number = convertView.findViewById(R.id.et_langchao_Number);
            et_channel_Number = convertView.findViewById(R.id.et_channel_Number);

            txt_id.setText(String.valueOf(InspurChannel.get(position).getId()));
            et_langchao_Number.setText(InspurChannel.get(position).getInspurChannelNumber().toString());
            et_channel_Number.setText(InspurChannel.get(position).getStrobeChannelNumber().toString());

            et_langchao_Number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    InspurChannel.get(position).setInspurChannelNumber(s.toString());
                    InspurChannelMap.get(position).setInspurChannelNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            et_channel_Number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    InspurChannel.get(position).setStrobeChannelNumber(s.toString());
                    InspurChannelMap.get(position).setStrobeChannelNumber(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            return convertView;
        }
    }
}