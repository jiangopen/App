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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.LightAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind.WindOrLightStatusList;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind.WindStatusBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.WindTestInTimeBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.EnergyBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindAddressBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindStatusBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.SharedData.SharedData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
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

public class LightActivity extends AppCompatActivity implements View.OnClickListener{
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    private SuoPingDialog suoPingDialog;
    private HeaderViewAdapter headerViewAdapter;
    private WonderUtil mWonderUtil = new WonderUtil();
    int selectWhich = 0;
    private ImageView txt_select;
    private TextView txt_name,txt_all_open,txt_all_close;
    private RecyclerView mListView;
    private TextView mFootView;
    private String url;
    private String Name;
    private String FLAG;
    private String JsonData;
    private String commandMapId;
    private GranaryListBean.Data selectProduct;
    private ArrayList<GranaryListBean.Data> granarylist = new ArrayList<>();
    private ArrayList<WindStatusBody> LightList = new ArrayList<>();
    private LightAdapter adapter_Light;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        txt_select = findViewById(R.id.txt_select);
        txt_name = findViewById(R.id.txt_name);
        txt_all_close = findViewById(R.id.txt_all_close);
        txt_all_open = findViewById(R.id.txt_all_open);
        mListView = findViewById(R.id.member);
        txt_select.setOnClickListener(this);
        txt_all_open.setOnClickListener(this);
        txt_all_close.setOnClickListener(this);

        bundle = getIntent().getBundleExtra("bundle");
        Name = bundle.getString("仓号");
        JsonData = bundle.getString("照明请求体");
        granarylist = bundle.getParcelableArrayList("粮仓");
        commandMapId = bundle.getString("commandMapId");
        url = bundle.getString("url");
        String name = bundle.getString("granaryName");
        txt_name.setText(name);
        suoPingDialog = new SuoPingDialog(this,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        initLightData(JsonData, new OnInitLightDataFinishedListener() {
            @Override
            public void OnInitLightDataListener(String address, ArrayList<WindAddressBody> lightBody) {
                if (address!=null && lightBody!=null) {
                    NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                    newDownRawBody.setUrl("/measure");
                    newDownRawBody.setMethod("POST");
                    newDownRawBody.setQuery(null);
                    newDownRawBody.setHeaders(null);
                    CountMultiple countMultiple = new CountMultiple();
                    countMultiple.setCommandMapId(commandMapId);
                    countMultiple.setUrl(url);
                    countMultiple.setMeasureType("-1");
                    String addresslist = "";
                    for (WindAddressBody data : lightBody) {
                        addresslist += data.getInspurAddress();
                    }
                    String inspurAddressList =
                              "AA55"
                            + address
                            + "F0FFFF"
                            + intToHex(lightBody.size(),4)
                            + addresslist
                            + "FFFF0D0A";
                    countMultiple.setCommandContent(inspurAddressList);
                    newDownRawBody.setBody(countMultiple);
                    String jsondata = gson.toJson(newDownRawBody);
                    initNewData(jsondata, address, new OnInitNewDataFinishedListener() {
                        @Override
                        public void OnInitNewDataListener(boolean success) {
                            suoPingDialog.dismiss();
                            refreshView(LightActivity.this,LightList);
                        }
                    });
                } else {
                    suoPingDialog.dismiss();
                }
            }
        });
    }

    private void refreshView(LightActivity context, ArrayList<WindStatusBody> list) {
        Log.d("jht", "refreshView: "+list.size());
        View vfoot = LayoutInflater.from(context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        int[] layoutIds = new int[]{R.layout.card_light_adapter_data};
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        mListView.setLayoutManager(layoutManager);
        adapter_Light = new LightAdapter(list,layoutIds,context);
        headerViewAdapter = new HeaderViewAdapter(adapter_Light);
        headerViewAdapter.addFooterView(vfoot);
        mListView.setAdapter(headerViewAdapter);
        mFootView.setText("共"+adapter_Light.getFreshDates().size()+"盏");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_select:
                ArrayList<GranaryListBean.Data> product = new ArrayList<>();
                for (GranaryListBean.Data data : granarylist) {
                    product.add(data);
                }
                showAlertDialog(product);
                break;
            case R.id.txt_all_close:
                initAllTestInTime("01", new OnInitAllTestInTimeFinishedListener() {
                    @Override
                    public void OnInitAllTestInTimeListener(boolean success) {
                        suoPingDialog.dismiss();
                        adapter_Light.notifyDataSetChanged();
                    }
                });
                break;
            case R.id.txt_all_open:
                initAllTestInTime("02", new OnInitAllTestInTimeFinishedListener() {
                    @Override
                    public void OnInitAllTestInTimeListener(boolean success) {
                        suoPingDialog.dismiss();
                        adapter_Light.notifyDataSetChanged();
                    }
                });
                break;
            default:
                break;
        }
    }

    public interface OnInitAllTestInTimeFinishedListener{
        void OnInitAllTestInTimeListener(boolean success);
    }
    public void initAllTestInTime(String flag, final OnInitAllTestInTimeFinishedListener listener){
        String address = "";
        int i=0;
        for (WindStatusBody data : LightList) {
            if (data.getHardwareData().getType().equals("01")) {
                i++;
                address += data.getHardwareData().getInspurAddress() + flag;
            }
        }
        String body =
                  "AA55"
                + FLAG
                + "09FFFF"
                + intToHex(i*2, 4)
                + address
                + "FFFF0D0A";
        NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
        newDownRawBody.setUrl("/measure");
        newDownRawBody.setMethod("POST");
        newDownRawBody.setQuery(null);
        newDownRawBody.setHeaders(null);
        CountMultiple countMultiple = new CountMultiple();
        countMultiple.setUrl(url);
        countMultiple.setMeasureType("-1");
        countMultiple.setCommandMapId(commandMapId);
        countMultiple.setCommandContent(body);
        newDownRawBody.setBody(countMultiple);
        String jsonData = gson.toJson(newDownRawBody);
        suoPingDialog = new SuoPingDialog(LightActivity.this,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsonData, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindTestInTimeBean LightTestInTimeBean = new WindTestInTimeBean();
                try{
                    LightTestInTimeBean = gson.fromJson((String) result,WindTestInTimeBean.class);
                } catch (JsonSyntaxException e){
                    listener.OnInitAllTestInTimeListener(false);
                    e.printStackTrace();
                    util.showToast(LightActivity.this,"通风一键解析有问题");
                    return;
                }
                Log.d("jht", "onReqSuccess: "+LightTestInTimeBean.getMsg());
                if (LightTestInTimeBean.getData()!=null){
                    if (flag.equals("01")){
                        if (LightTestInTimeBean.getData().getLightOpenStatus()!=null&&LightTestInTimeBean.getData().getLightOpenStatus().size()>0){
                            for (int i=0;i<LightTestInTimeBean.getData().getLightOpenStatus().size();i++){
                                if (LightTestInTimeBean.getData().getLightOpenStatus().get(i).equals("00")){
                                    LightList.get(i).setHardwareStatus("02");
                                }
                            }
                        }
                    } else if(flag.equals("02")){
                        if (LightTestInTimeBean.getData().getLightCloseStatus()!=null&&LightTestInTimeBean.getData().getLightCloseStatus().size()>0){
                            for (int i=0;i<LightTestInTimeBean.getData().getLightCloseStatus().size();i++){
                                if (LightTestInTimeBean.getData().getLightCloseStatus().get(i).equals("00")){
                                    LightList.get(i).setHardwareStatus("01");
                                }
                            }
                        }
                    }
                }
                listener.OnInitAllTestInTimeListener(true);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitAllTestInTimeListener(false);
                util.showToast(LightActivity.this,errorMsg);
            }
        });
    }
    private void showAlertDialog(ArrayList<GranaryListBean.Data> product) {
        Log.d("jht", "showAlertDialog: "+product.size());
        Collections.sort(product, (o1, o2) -> o1.getGranaryName().compareTo(o2.getGranaryName()));
        List<String> NAME = new ArrayList<>();
        for (int i=0;i<product.size();i++){
            NAME.add(product.get(i).getGranaryName());
            if (product.get(i).getGranaryName().equals(txt_name.toString())){
                selectWhich=i;
            }
        }
//        Log.d("jht", "showAlertDialog: "+selectWhich);
        ArrayAdapter adapter_this = new ArrayAdapter<String>(this,R.layout.simple_list,NAME);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(adapter_this, selectWhich, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectProduct = product.get(which);
                String cangku = product.get(which).getGranaryName();
                selectWhich = which;
                Name = cangku;
                txt_name.setText(cangku);
                CountMultiple multiple = new CountMultiple();
                for (GranaryListBean.Data data : granarylist) {
                    if (data.getGranaryName().equals(Name)) {
                        multiple.setUrl(data.getUrl());
                        multiple.setCommandMapId(data.getCommandMapId());
                        commandMapId = data.getCommandMapId();
                        url = data.getUrl();
                    }
                }
                NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
                Body.setBody(multiple);
                Body.setHeaders(null);
                Body.setQuery(null);
                Body.setMethod("POST");
                Body.setUrl("/command-map/get");
                String jsonData = gson.toJson(Body);
                suoPingDialog = new SuoPingDialog(LightActivity.this,"正在检测，请稍等......");
                suoPingDialog.setCancelable(true);
                suoPingDialog.show();
                initLightData(jsonData, new OnInitLightDataFinishedListener() {
                    @Override
                    public void OnInitLightDataListener(String address, ArrayList<WindAddressBody> lightBody) {
                        if (address!=null && lightBody!=null) {
                            NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                            newDownRawBody.setUrl("/measure");
                            newDownRawBody.setMethod("POST");
                            newDownRawBody.setQuery(null);
                            newDownRawBody.setHeaders(null);
                            CountMultiple countMultiple = new CountMultiple();
                            countMultiple.setCommandMapId(commandMapId);
                            countMultiple.setUrl(url);
                            countMultiple.setMeasureType("-1");
                            String addresslist = "";
                            for (WindAddressBody data : lightBody) {
                                addresslist += data.getInspurAddress();
                            }
                            String inspurAddressList =
                                    "AA55"
                                            + address
                                            + "F0FFFF"
                                            + intToHex(lightBody.size(),4)
                                            + addresslist
                                            + "FFFF0D0A";
                            countMultiple.setCommandContent(inspurAddressList);
                            Log.d("wan", "OnInitLightDataListener: "+inspurAddressList);
                            newDownRawBody.setBody(countMultiple);
                            String jsondata = gson.toJson(newDownRawBody);
                            Log.d("wan", "OnInitLightDataListener: "+jsondata);
                            initNewData(jsondata, address, new OnInitNewDataFinishedListener() {
                                @Override
                                public void OnInitNewDataListener(boolean success) {
                                    suoPingDialog.dismiss();
                                    refreshView(LightActivity.this,LightList);
                                }
                            });
                        } else {
                            suoPingDialog.dismiss();
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

    public interface OnInitNewDataFinishedListener {
        void OnInitNewDataListener(boolean success);
    }
    private void initNewData(String jsonData, String address, final OnInitNewDataFinishedListener listener){
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsonData, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindStatusBean lightBean = new WindStatusBean();
                try {
                    lightBean = gson.fromJson((String) result,WindStatusBean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    util.showToast(LightActivity.this,lightBean.getMsg());
                    return;
                }
                LightList.clear();
                if (lightBean.getData()!=null && lightBean.getData().getLightStatusList()!=null && lightBean.getData().getLightStatusList().size()>0) {
                    int i=1;
                    for (WindOrLightStatusList data : lightBean.getData().getLightStatusList()) {
                        WindStatusBody LightStatusBody = new WindStatusBody(
                                i,
                                "01",
                                commandMapId,
                                url,
                                data.getHardwareStatus(),
                                address,
                                data.getHardwareInfo(),
                                data.getHardwareInfo().getId()
                        );
                        LightList.add(LightStatusBody);
                        i++;
                    }
                }
                for (WindStatusBody data : LightList) {
                    Log.d("woaini", "onReqSuccess: " +txt_name.getText() + "\n" + data.getHardwareData().getType() + "\n" + data.getHardwareData().getName() + "\n" + data.getHardwareData().getInspurAddress() + "\n" + "status" + data.getHardwareStatus());
                }
                listener.OnInitNewDataListener(true);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitNewDataListener(false);
                util.showToast(LightActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitLightDataFinishedListener {
        void OnInitLightDataListener(String address,ArrayList<WindAddressBody> energyBody);
    }

    private void initLightData(String jsonData, final OnInitLightDataFinishedListener listener) {
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsonData, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                CommandMapBean commandMapBean = new CommandMapBean();
                String address = "";
                ArrayList<WindAddressBody> lightbody = new ArrayList<>();
                try {
                    commandMapBean = gson.fromJson((String) result,CommandMapBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitLightDataListener(null,null);
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util.showToast(LightActivity.this,commandMapBean.getMsg());
                    return;
                }
                if (commandMapBean.getData().getData()!=null) {
                    if (commandMapBean.getData().getData().getCommandList()!=null && commandMapBean.getData().getData().getCommandList().size()>0) {
                        for (CommandListData data : commandMapBean.getData().getData().getCommandList()) {
                            if (data.getInspurControlType().equals("F0")) {
                                address = data.getInspurExtensionAddress();
                            }
                            if (data.getInspurControlType().equals("09")) {
                                FLAG = data.getInspurExtensionAddress();
                            }
                        }
                    }
                    if (commandMapBean.getData().getData().getHardwareList()!=null&&commandMapBean.getData().getData().getHardwareList().size()>0) {
                        for (HardwareListData data : commandMapBean.getData().getData().getHardwareList()) {
                            if (data.getType().equals("03")) {
                                WindAddressBody windAddressBody = new WindAddressBody(
                                        data.getInspurAddress()
                                );
                                lightbody.add(windAddressBody);
                            }
                        }
                    }
                }
                listener.OnInitLightDataListener(address,lightbody);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitLightDataListener(null,null);
                suoPingDialog.dismiss();
                util.showToast(LightActivity.this,errorMsg);
            }
        });
    }
}