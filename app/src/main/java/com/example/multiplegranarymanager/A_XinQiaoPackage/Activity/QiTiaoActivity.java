package com.example.multiplegranarymanager.A_XinQiaoPackage.Activity;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.QiTiaoPagerAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind.QiTiaoStatuslist;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind.WindStatusBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.QiTiaoFlagBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindAddressBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindStatusBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.SharedData.QiTiaoSharedData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class QiTiaoActivity extends AppCompatActivity{
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView txt_select;
    TextView txt_name,txt_infos;
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Bundle bundleData = new Bundle();
    private Gson gson = new Gson();
    private SuoPingDialog suoPingDialog;
    private String url;
    private String Name;
    private String FLAG;
    private String JsonData;
    private String commandMapId;
    private PagerAdapter adapter;
    private String DieFa,HuanLiu,YaLi,N2;
    private String Fa_On = "30", Fa_Off = "32", FJ_On = "38", FJ_Off = "3A", Ya_Li = "52", C_N2 = "54", Fa_Status = "56";
    private QiTiaoFlagBody CAOZUO_FLAG = new QiTiaoFlagBody();
    private ArrayList<GranaryListBean.Data> granarylist = new ArrayList<>();
    private List<Double> pressurelist = new ArrayList<>();
    private List<Double> n2list = new ArrayList<>();
    private final String[] TAB_TITLES_WU_WEI = new String[]{"蝶阀","风机"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qi_tiao);
        viewPager = findViewById(R.id.view_pages);
        tabLayout = findViewById(R.id.tab_layout);
        txt_name = findViewById(R.id.txt_name);
        txt_infos = findViewById(R.id.txt_infos);
        txt_infos.setSelected(true);
        bundle = getIntent().getBundleExtra("bundle");
        QiTiaoSharedData.HuanLiuStatusList.clear();
        QiTiaoSharedData.FaMenStatusList.clear();
        if (bundle!=null) {
            String data = bundle.getString("气调请求体");
            commandMapId = bundle.getString("commandMapId");
            url = bundle.getString("url");
            String name = bundle.getString("granaryName");
            txt_name.setText(name);
            suoPingDialog = new SuoPingDialog(QiTiaoActivity.this,"正在操作，请稍等......");
            suoPingDialog.setCancelable(true);
            suoPingDialog.show();
            initQiTiaoData(data, new OnInitCommandDataFinishedListener() {
                @Override
                public void OnInitCommandDataListener(ArrayList<WindAddressBody> DF, ArrayList<WindAddressBody> HL, ArrayList<WindAddressBody> YL, ArrayList<WindAddressBody> NN) {
                    //压力数值
                    NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                    newDownRawBody.setUrl("/measure");
                    newDownRawBody.setMethod("POST");
                    newDownRawBody.setQuery(null);
                    newDownRawBody.setHeaders(null);
                    CountMultiple countMultiple = new CountMultiple();
                    countMultiple.setCommandMapId(commandMapId);
                    countMultiple.setUrl(url);
                    CountMultiple.CommandJson commandJson = new CountMultiple.CommandJson();
                    List<String> ids = new ArrayList<>();
                    for (WindAddressBody data : YL) {
                        ids.add(data.getInspurAddress());
                    }
                    commandJson.setIds(ids);
                    countMultiple.setCommandJson(commandJson);
                    countMultiple.setMeasureType("-1");
                    String yl = "AA55" + CAOZUO_FLAG.getYa_Li_InspurExtensionAddress() + Ya_Li + "FFFF" + "FFFF0D0A";
                    countMultiple.setCommandContent(yl);
                    newDownRawBody.setBody(countMultiple);
                    String jsonYL = gson.toJson(newDownRawBody);
                    initPressureData(jsonYL, new OnInitPressureDataFinishedListener() {
                        @Override
                        public void OnInitPressureDataListener(boolean success) {
                            NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                            newDownRawBody.setUrl("/measure");
                            newDownRawBody.setMethod("POST");
                            newDownRawBody.setQuery(null);
                            newDownRawBody.setHeaders(null);
                            CountMultiple countMultiple = new CountMultiple();
                            countMultiple.setCommandMapId(commandMapId);
                            countMultiple.setUrl(url);
                            CountMultiple.CommandJson commandJson = new CountMultiple.CommandJson();
                            List<String> ids = new ArrayList<>();
                            for (WindAddressBody data : NN) {
                                ids.add(data.getInspurAddress());
                            }
                            commandJson.setIds(ids);
                            countMultiple.setCommandJson(commandJson);
                            countMultiple.setMeasureType("-1");
                            String n2 = "AA55" + CAOZUO_FLAG.getC_N2_InspurExtensionAddress() + C_N2 + "FFFF" + "FFFF0D0A";
                            countMultiple.setCommandContent(n2);
                            newDownRawBody.setBody(countMultiple);
                            String jsonN2 = gson.toJson(newDownRawBody);
                            //氮气浓度
                            initN2Data(jsonN2, new OnInitN2DataFinishedListener() {
                                @Override
                                public void OnInitN2DataListener(boolean success) {
                                    DecimalFormat df = new DecimalFormat("#.##");
                                    String titles1 = "";
                                    for (int i=1;i<=n2list.size();i++) {
                                        titles1 += "氮气" + i + ":" + df.format(n2list.get(i-1)) + "%,";
                                        double o2 = 99 - n2list.get(i-1);
                                        titles1 += "氧气" + i + ":" + df.format(o2) + "%,";
                                    }
                                    String titles2 = "";
                                    for (int i=1;i<=pressurelist.size();i++) {
                                        titles2 += "压力" + i + ":" + df.format(pressurelist.get(i-1)) + "Pa,";
                                    }
                                    txt_infos.setText(titles1+","+titles2+"。");
                                    //蝶阀状态
                                    NewDownRawBodyTWO newDownRawBody2 = new NewDownRawBodyTWO();
                                    newDownRawBody2.setUrl("/measure");
                                    newDownRawBody2.setMethod("POST");
                                    newDownRawBody2.setQuery(null);
                                    newDownRawBody2.setHeaders(null);
                                    CountMultiple countMultiple2 = new CountMultiple();
                                    countMultiple2.setCommandMapId(commandMapId);
                                    countMultiple2.setUrl(url);
                                    countMultiple2.setMeasureType("-1");
                                    String fs = "AA55" + CAOZUO_FLAG.getFa_Status_InspurExtensionAddress() + Fa_Status + "FFFF" + "FFFF0D0A";
                                    countMultiple2.setCommandContent(fs);
                                    newDownRawBody2.setBody(countMultiple2);
                                    String jsonFS = gson.toJson(newDownRawBody2);
                                    Log.d("jht", "OnInitN2DataListener: " + jsonFS);
                                    initFaStatus(jsonFS, new OnInitFaStatusFinishedListener() {
                                        @Override
                                        public void OnInitFaStatusListener(boolean success) {
                                            suoPingDialog.dismiss();
                                            bundleData.putString("commandMapId", commandMapId);
                                            bundleData.putString("url",url);
                                            InitWindPages();
                                            setWindTabs(tabLayout,getLayoutInflater(),TAB_TITLES_WU_WEI);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        }
    }
    private void setWindTabs(TabLayout tabLayout, LayoutInflater layoutInflater, String[] tabTitles) {
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
    private void InitWindPages() {
        adapter = new QiTiaoPagerAdapter(getSupportFragmentManager(),bundleData);
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
    public interface OnInitFaStatusFinishedListener{
        void OnInitFaStatusListener(boolean success);
    }
    public void initFaStatus(String jsondata, final OnInitFaStatusFinishedListener listener) {
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindStatusBean windStatusBean = new WindStatusBean();
                try {
                    windStatusBean = gson.fromJson((String) result,WindStatusBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitFaStatusListener(false);
                    e.printStackTrace();
                    util.showToast(QiTiaoActivity.this,windStatusBean.getMsg());
                    return;
                }
                if (windStatusBean.getData()!=null && windStatusBean.getData().getValveStatusList()!=null && windStatusBean.getData().getValveStatusList().size()>0) {
                    for (QiTiaoStatuslist data : windStatusBean.getData().getValveStatusList()) {
                        for (WindStatusBody data2 : QiTiaoSharedData.FaMenStatusList) {
                            if (data.getId().equals(data2.getID())) {
                                data2.setHardwareStatus(data.getStatus());
                            }
                        }
                    }
                }
                listener.OnInitFaStatusListener(true);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitFaStatusListener(false);
                util.showToast(QiTiaoActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitN2DataFinishedListener{
        void OnInitN2DataListener(boolean success);
    }
    public void initN2Data(String jsondata, final OnInitN2DataFinishedListener listener) {
        n2list.clear();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindStatusBean N2StatusBean = new WindStatusBean();
                try {
                    N2StatusBean = gson.fromJson((String) result,WindStatusBean.class);
                } catch (JsonSyntaxException e){
                    suoPingDialog.dismiss();
                    listener.OnInitN2DataListener(false);
                    e.printStackTrace();
                    util.showToast(QiTiaoActivity.this,N2StatusBean.getMsg());
                    return;
                }
                if (N2StatusBean.getData()!=null && N2StatusBean.getData().getN2List()!=null && N2StatusBean.getData().getN2List().size()>0) {
                    n2list = N2StatusBean.getData().getN2List();
                }
                listener.OnInitN2DataListener(true);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OnInitN2DataListener(false);
                util.showToast(QiTiaoActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitPressureDataFinishedListener{
        void OnInitPressureDataListener(boolean success);
    }
    public void initPressureData(String jsondata, final OnInitPressureDataFinishedListener listener) {
        pressurelist.clear();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindStatusBean PressureStatusBean = new WindStatusBean();
                try {
                    PressureStatusBean = gson.fromJson((String) result,WindStatusBean.class);
                } catch (JsonSyntaxException e){
                    suoPingDialog.dismiss();
                    listener.OnInitPressureDataListener(false);
                    e.printStackTrace();
                    util.showToast(QiTiaoActivity.this,PressureStatusBean.getMsg());
                    return;
                }
                if (PressureStatusBean.getData()!=null && PressureStatusBean.getData().getPressureList()!=null && PressureStatusBean.getData().getPressureList().size()>0) {
                    pressurelist = PressureStatusBean.getData().getPressureList();
                }
                listener.OnInitPressureDataListener(true);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OnInitPressureDataListener(false);
                util.showToast(QiTiaoActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitCommandDataFinishedListener{
        void OnInitCommandDataListener(ArrayList<WindAddressBody> DF,ArrayList<WindAddressBody> HL,ArrayList<WindAddressBody> YL, ArrayList<WindAddressBody> NN);
    }
    private void initQiTiaoData(String jsondata, final OnInitCommandDataFinishedListener listener){
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                CommandMapBean commandMapBean = new CommandMapBean();
                ArrayList<WindAddressBody> DFAllBodies = new ArrayList<>();
                ArrayList<WindAddressBody> HLAllBodies = new ArrayList<>();
                ArrayList<WindAddressBody> YLAllBodies = new ArrayList<>();
                ArrayList<WindAddressBody> N2AllBodies = new ArrayList<>();
                try {
                    commandMapBean = gson.fromJson((String) result,CommandMapBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util.showToast(QiTiaoActivity.this,commandMapBean.getMsg());
                    listener.OnInitCommandDataListener(null,null,null,null);
                    return;
                }
                if (commandMapBean.getData().getData() != null) {
                    if (commandMapBean.getData().getData().getCommandList()!=null&&commandMapBean.getData().getData().getCommandList().size()>0) {
                        for (CommandListData data : commandMapBean.getData().getData().getCommandList()){
                            if (data.getInspurControlType().equals("30")){
                                CAOZUO_FLAG.setFa_On_InspurExtensionAddress(data.getInspurExtensionAddress());
                            }
                            if (data.getInspurControlType().equals("32")){
                                CAOZUO_FLAG.setFa_Off_InspurExtensionAddress(data.getInspurExtensionAddress());
                            }
                            if (data.getInspurControlType().equals("38")){
                                CAOZUO_FLAG.setFJ_On_InspurExtensionAddress(data.getInspurExtensionAddress());
                            }
                            if (data.getInspurControlType().equals("3A")){
                                CAOZUO_FLAG.setFJ_Off_InspurExtensionAddress(data.getInspurExtensionAddress());
                            }
                            if (data.getInspurControlType().equals("52")){
                                CAOZUO_FLAG.setYa_Li_InspurExtensionAddress(data.getInspurExtensionAddress());
                            }
                            if (data.getInspurControlType().equals("54")){
                                CAOZUO_FLAG.setC_N2_InspurExtensionAddress(data.getInspurExtensionAddress());
                            }
                            if (data.getInspurControlType().equals(Fa_Status)){
                                CAOZUO_FLAG.setFa_Status_InspurExtensionAddress(data.getInspurExtensionAddress());
                            }
                        }
                    }
                    if (commandMapBean.getData().getData().getHardwareList()!=null && commandMapBean.getData().getData().getHardwareList().size()>0) {
                        int i=0;
                        int j=0;
                        for (HardwareListData data : commandMapBean.getData().getData().getHardwareList()){

                            if (data.getType().equals("08")){
                                i++;
                                WindAddressBody one = new WindAddressBody(
                                        data.getExtensionNumber()
                                );
                                DFAllBodies.add(one);
                                WindStatusBody two = new WindStatusBody(
                                        i,
                                        data.getFatherValveBox(),
                                        commandMapId,
                                        url,
                                        null,
                                        data.getExtensionNumber(),
                                        data,
                                        data.getId()
                                );
                                QiTiaoSharedData.FaMenStatusList.add(
                                        two
                                );

                            } else if (data.getType().equals("10")){
                                j++;
                                WindAddressBody two = new WindAddressBody(
                                        data.getExtensionNumber()
                                );
                                HLAllBodies.add(two);
                                WindStatusBody one = new WindStatusBody(
                                        j,
                                        data.getFatherValveBox(),
                                        commandMapId,
                                        url,
                                        null,
                                        data.getExtensionNumber(),
                                        data,
                                        data.getId()
                                );
                                QiTiaoSharedData.HuanLiuStatusList.add(
                                        one
                                );

                            } else if (data.getType().equals("13")){
                                WindAddressBody three = new WindAddressBody(
                                        data.getExtensionNumber()
                                );
                                YLAllBodies.add(three);
                            } else if (data.getType().equals("14")){
                                WindAddressBody four = new WindAddressBody(
                                        data.getExtensionNumber()
                                );
                                N2AllBodies.add(four);
                            }
                        }
                    }
                }
                listener.OnInitCommandDataListener(DFAllBodies,HLAllBodies,YLAllBodies,N2AllBodies);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OnInitCommandDataListener(null,null,null,null);
                util.showToast(QiTiaoActivity.this,errorMsg);
            }
        });
    }
}