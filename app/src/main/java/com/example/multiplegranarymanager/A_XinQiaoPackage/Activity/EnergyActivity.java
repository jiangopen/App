package com.example.multiplegranarymanager.A_XinQiaoPackage.Activity;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;

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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.EnergyCardDataAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Energy.EnergyBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Energy.EnergyConsumptionList;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.History.HistoryBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.EnergyBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.TimeMultipe;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindAddressBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.SelfDialog;
import com.example.multiplegranarymanager.Util.Util1;
import com.example.multiplegranarymanager.Util.WonderUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EnergyActivity extends AppCompatActivity implements View.OnClickListener{
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    private String Name;
    TextView mFootView;
    private String JsonData;
    private TextView TXT_name,TXT_test;
    RecyclerView mListView;
    private ImageView IMG_select;
    private TextView TXT_all_energy;
    private SelfDialog selfDialog;
    int selectWhich = 0;
    private SuoPingDialog suoPingDialog;
    GranaryListBean.Data selectProduct;
    private String commandMapId;
    private ArrayList<EnergyBody> EnergyBodies = new ArrayList<>();
    private ArrayList<GranaryListBean.Data> granarylist = new ArrayList<>();
    private WonderUtil mWonderUtil = new WonderUtil();
    private EnergyCardDataAdapter adapter_energy;
    private HeaderViewAdapter headerViewAdapter;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);
        TXT_name = findViewById(R.id.name);
        TXT_test = findViewById(R.id.test);
        IMG_select = findViewById(R.id.select);
        TXT_all_energy = findViewById(R.id.txt_all_energy);
        mListView = findViewById(R.id.member);
        TXT_test.setOnClickListener(this);
        IMG_select.setOnClickListener(this);

        bundle = getIntent().getBundleExtra("bundle");
        Name = bundle.getString("仓号");
        JsonData = bundle.getString("能耗请求体");
        Log.d("jht", "onCreate: "+JsonData);
        granarylist = bundle.getParcelableArrayList("粮仓");
        commandMapId = bundle.getString("commandMapId");
        url = bundle.getString("url");
        String name = bundle.getString("granaryName");
        TXT_name.setText(name);
        suoPingDialog = new SuoPingDialog(this,"正在检测，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        initEnergyData(JsonData, new OnInitEnergyDataFinishedListener() {
            @Override
            public void OnInitEnergyDataListener(String address,ArrayList<WindAddressBody> energyBody) {
                if (address!=null&&energyBody!=null){
                    NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                    newDownRawBody.setUrl("/measure");
                    newDownRawBody.setMethod("POST");
                    newDownRawBody.setQuery(null);
                    newDownRawBody.setHeaders(null);
                    CountMultiple countMultiple = new CountMultiple();
                    countMultiple.setCommandMapId(commandMapId);
                    countMultiple.setUrl(url);
//                countMultiple.setMeasureType("-1");
                    String inspurAddressList =
                            "AA55"
                                    + address
                                    + "12FFFF0000FFFF0D0A";
                    countMultiple.setCommandContent(inspurAddressList);
                    newDownRawBody.setBody(countMultiple);
                    String jsondata = gson.toJson(newDownRawBody);
                    initNewData(jsondata, new OnInitNewDataFinishedListener() {
                        @Override
                        public void OnInitNewDataListener(boolean success) {
                            if (success){
                                suoPingDialog.dismiss();
                                float all = 0;
                                for (EnergyBody data : EnergyBodies) {
                                    if (data.getActiveElectricEnergy()!=null) {
                                        all += data.getActiveElectricEnergy();
                                    }
                                }
                                TXT_all_energy.setText(all+"KWH");
                                refreshView(EnergyActivity.this,EnergyBodies);
                            }
                        }
                    });
                } else {
                    suoPingDialog.dismiss();
                }
            }
        });
    }

    private void refreshView(EnergyActivity Context, ArrayList<EnergyBody> list) {
        Log.d("zyq", "refreshView: "+list.size());
        View vfoot = LayoutInflater.from(Context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        GridLayoutManager layoutManager = new GridLayoutManager(Context,2);
        mListView.setLayoutManager(layoutManager);
        adapter_energy = new EnergyCardDataAdapter(list,Context);
        headerViewAdapter = new HeaderViewAdapter(adapter_energy);
        mListView.setAdapter(headerViewAdapter);
        mFootView.setText("共"+adapter_energy.getFreshDates().size()+"台设备");
    }

    public interface OnInitNewDataFinishedListener {
        void OnInitNewDataListener(boolean success);
    }
    private void initNewData(String jsondata, final OnInitNewDataFinishedListener listener) {
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                EnergyBean energyBean = new EnergyBean();
                try {
                    energyBean = gson.fromJson((String) result,EnergyBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    listener.OnInitNewDataListener(false);
                    util.showToast(EnergyActivity.this,"数据解析出错");
                    e.printStackTrace();
                    return;
                }
                EnergyBodies.clear();
                if (energyBean.getData()!=null && energyBean.getData().getEnergyConsumptionList()!=null && energyBean.getData().getEnergyConsumptionList().size()>0) {
                    for (EnergyConsumptionList data : energyBean.getData().getEnergyConsumptionList()) {
                        EnergyBody datas = new EnergyBody(
                                TXT_name.getText().toString(),
                                data.getHardwareInfo().getName(),
                                data.getaI(),
                                data.getaU(),
                                data.getActiveElectricEnergy(),
                                data.getbI(),
                                data.getbU(),
                                data.getcI(),
                                data.getcU(),
                                data.getFrequency(),
                                data.getPowerFactor()
                        );
                        EnergyBodies.add(datas);
                    }
                    //按照名字排序
                    Collections.sort(EnergyBodies, new Comparator<EnergyBody>() {
                        @Override
                        public int compare(EnergyBody o1, EnergyBody o2) {
                            return mWonderUtil.compareTo(o1.getName(), o2.getName());
                        }
                    });
                }
                if (listener!=null){
                    listener.OnInitNewDataListener(true);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OnInitNewDataListener(false);
                util.showToast(EnergyActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitEnergyDataFinishedListener {
        void OnInitEnergyDataListener(String address,ArrayList<WindAddressBody> energyBody);
    }
    private void initEnergyData(String jsonData, final OnInitEnergyDataFinishedListener listener) {
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsonData, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                CommandMapBean commandMapBean = new CommandMapBean();
                String address = "";
                ArrayList<WindAddressBody> energybody = new ArrayList<>();
                try {
                    commandMapBean = gson.fromJson((String) result,CommandMapBean.class);
                } catch (JsonSyntaxException e){
                    listener.OnInitEnergyDataListener(null,null);
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util.showToast(EnergyActivity.this,commandMapBean.getMsg());
                    return;
                }
                if (commandMapBean.getData().getData()!=null) {
                    if (commandMapBean.getData().getData().getCommandList()!=null && commandMapBean.getData().getData().getCommandList().size()>0) {
                        for (CommandListData data : commandMapBean.getData().getData().getCommandList()){
                            if (data.getInspurControlType().equals("12")) {
                                address = data.getInspurExtensionAddress();
                            }
                        }
                    }
                    if (commandMapBean.getData().getData().getHardwareList()!=null&&commandMapBean.getData().getData().getHardwareList().size()>0){
                        for (HardwareListData data : commandMapBean.getData().getData().getHardwareList()){
                            if (data.getType().equals("05")){
                                WindAddressBody windAllBody = new WindAddressBody(
                                        data.getInspurAddress()
                                );
                                energybody.add(windAllBody);
                            }
                        }
                    }
                }
                listener.OnInitEnergyDataListener(address,energybody);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitEnergyDataListener(null,null);
                suoPingDialog.dismiss();
                util.showToast(EnergyActivity.this,errorMsg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select:
                ArrayList<GranaryListBean.Data> product = new ArrayList<>();
                for (GranaryListBean.Data data : granarylist) {
                    product.add(data);
                }
                showAlertDialog(product);
                break;
            case R.id.test:
                suoPingDialog = new SuoPingDialog(this,"正在检测，请稍等......");
                suoPingDialog.setCancelable(true);
                suoPingDialog.show();
                CountMultiple multiple = new CountMultiple();
                for (GranaryListBean.Data data : granarylist) {
                    if (data.getGranaryName().equals(TXT_name.getText())){
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
                initEnergyData(jsonData, new OnInitEnergyDataFinishedListener() {
                    @Override
                    public void OnInitEnergyDataListener(String address,ArrayList<WindAddressBody> energyBody) {
                        if (address!=null&&energyBody!=null){
                            NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                            newDownRawBody.setUrl("/measure");
                            newDownRawBody.setMethod("POST");
                            newDownRawBody.setQuery(null);
                            newDownRawBody.setHeaders(null);
                            CountMultiple countMultiple = new CountMultiple();
                            countMultiple.setCommandMapId(commandMapId);
                            countMultiple.setUrl(url);
//                countMultiple.setMeasureType("-1");
                            String inspurAddressList =
                                    "AA55"
                                            + address
                                            + "12FFFF0000FFFF0D0A";
                            countMultiple.setCommandContent(inspurAddressList);
                            newDownRawBody.setBody(countMultiple);
                            String jsondata = gson.toJson(newDownRawBody);
                            initNewData(jsondata, new OnInitNewDataFinishedListener() {
                                @Override
                                public void OnInitNewDataListener(boolean success) {
                                    if (success){
                                        suoPingDialog.dismiss();
                                        float all = 0;
                                        for (EnergyBody data : EnergyBodies) {
                                            if (data.getActiveElectricEnergy()!=null) {
                                                all += data.getActiveElectricEnergy();
                                            }
                                        }
                                        TXT_all_energy.setText(all+"KWH");
                                        refreshView(EnergyActivity.this,EnergyBodies);
                                    }

                                }
                            });
                        } else {
                            suoPingDialog.dismiss();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
    private void showAlertDialog(ArrayList<GranaryListBean.Data> product) {
        Collections.sort(product, (o1, o2) -> o1.getGranaryName().compareTo(o2.getGranaryName()));
        List<String> NAME = new ArrayList<>();
        for (int i=0;i<product.size();i++){
            NAME.add(product.get(i).getGranaryName());
            if (product.get(i).getGranaryName().equals(TXT_name.toString())){
                selectWhich=i;
            }
        }
        Log.d("jht", "showAlertDialog: "+selectWhich);
        ArrayAdapter adapter_this = new ArrayAdapter<String>(this,R.layout.simple_list,NAME);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(adapter_this, selectWhich, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectProduct = product.get(which);
                String cangku = product.get(which).getGranaryName();
                selectWhich = which;
                Name = cangku;
                TXT_name.setText(cangku);
                CountMultiple multiple = new CountMultiple();
                for (GranaryListBean.Data data : granarylist) {
                    if (data.getGranaryName().equals(cangku)){
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
                suoPingDialog = new SuoPingDialog(EnergyActivity.this,"正在检测，请稍等......");
                suoPingDialog.setCancelable(true);
                suoPingDialog.show();
                initEnergyData(jsonData, new OnInitEnergyDataFinishedListener() {
                    @Override
                    public void OnInitEnergyDataListener(String address,ArrayList<WindAddressBody> energyBody) {
                        if (address!=null&&energyBody!=null){
                            NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                            newDownRawBody.setUrl("/measure");
                            newDownRawBody.setMethod("POST");
                            newDownRawBody.setQuery(null);
                            newDownRawBody.setHeaders(null);
                            CountMultiple countMultiple = new CountMultiple();
                            countMultiple.setCommandMapId(commandMapId);
                            countMultiple.setUrl(url);
//                countMultiple.setMeasureType("-1");
                            String inspurAddressList =
                                    "AA55"
                                            + address
                                            + "12FFFF0000FFFF0D0A";
                            countMultiple.setCommandContent(inspurAddressList);
                            newDownRawBody.setBody(countMultiple);
                            String jsondata = gson.toJson(newDownRawBody);
                            initNewData(jsondata, new OnInitNewDataFinishedListener() {
                                @Override
                                public void OnInitNewDataListener(boolean success) {
                                    if (success){
                                        suoPingDialog.dismiss();
                                        float all = 0;
                                        for (EnergyBody data : EnergyBodies) {
                                            if (data.getActiveElectricEnergy()!=null) {
                                                all += data.getActiveElectricEnergy();
                                            }
                                        }
                                        TXT_all_energy.setText(all+"KWH");
                                        refreshView(EnergyActivity.this,EnergyBodies);
                                    }

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
}