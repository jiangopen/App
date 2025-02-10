package com.example.multiplegranarymanager.A_XinQiaoPackage.Activity;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.Pager.WuWeiWindPagerAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ProjectSetting.GranarySettingList;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ProjectSetting.ProjectSettingBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind.WindStatusBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind.WindOrLightStatusList;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindAddressBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindStatusBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.SharedData.SharedData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class WindActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView granaryName;
    Gson gson = new Gson();
    private SuoPingDialog suoPingDialog;
    private String commandMapId;
    private Util1 util = new Util1();
    private PagerAdapter adapter;
//    private final String[] TAB_TITLES_HUAI_NAN = new String[]{"密闭阀","风机"};
    private final String[] TAB_TITLES_WU_WEI = new String[]{"仓窗","通风口","风机"};
    private Bundle bundleData = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind);
        viewPager = findViewById(R.id.view_pages);
        tabLayout = findViewById(R.id.tab_layout);
        granaryName = findViewById(R.id.txt_granary_name);
        SharedData.WindStatusList.clear();
        SharedData.WindStatusList_FengJi.clear();
        SharedData.WindStatusList_CangChuang.clear();
        SharedData.WindStatusList_TongFengKou.clear();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle!=null){
            String data = bundle.getString("通风控制请求体");
            commandMapId = bundle.getString("commandMapId");
            String url = bundle.getString("url");
            String name = bundle.getString("granaryName");
            granaryName.setText(name);
            initWindData(data, new OnInitWindDataFinishedListener() {
                @Override
                public void OnInitWindDataListener(String inspurExtensionAddress, String inspurExtensionAddress2, List<WindAddressBody> WindBodyList) {
                    initProjectSettingData(url, new OnInProjectSettingDataFinishedListener() {
                        @Override
                        public void OnInitProjectSettingListener(boolean enabled) {
                            NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                            newDownRawBody.setUrl("/measure");
                            newDownRawBody.setMethod("POST");
                            newDownRawBody.setQuery(null);
                            newDownRawBody.setHeaders(null);
                            CountMultiple countMultiple = new CountMultiple();
                            countMultiple.setCommandMapId(commandMapId);
                            countMultiple.setUrl(url);
                            String inspurAddressList = "";
                            for (WindAddressBody data : WindBodyList){
                                inspurAddressList += data.getInspurAddress();
                            }
                            String body =
                                      "AA55"
                                    + inspurExtensionAddress
                                    + "F0FFFF"
                                    + intToHex(WindBodyList.size(),4)
                                    + inspurAddressList
                                    + "FFFF0D0A";
                            Log.d("jht", "OnInitWindDataListener: "+body);
                            countMultiple.setCommandContent(body);
                            newDownRawBody.setBody(countMultiple);
                            String jsonData = gson.toJson(newDownRawBody);
                            bundleData.putString("commandMapId", commandMapId);
                            bundleData.putString("url",url);
                            bundleData.putBoolean("wind",enabled);
                            bundleData.putString("jsonData",jsonData);
                            bundleData.putString("Fa_address",inspurExtensionAddress2);
                            suoPingDialog = new SuoPingDialog(WindActivity.this,"加载中,请稍等......");
                            suoPingDialog.show();
                            if (deviceType.equals("cloud_request_trans_xinqiao")){
                                initAllData(commandMapId, url, jsonData, inspurExtensionAddress2, new OnInitAllDataFinishedListener() {
                                    @Override
                                    public void OnInitAllDataListener(boolean success) {
                                        initMiBiFaData(new OnInitMiBiFaDataFinishedListener() {
                                            @Override
                                            public void OnInitMiBiFaDataListener(boolean success) {
                                                suoPingDialog.dismiss();
                                                WuWeiinitWindPages();
                                                setWindTabs(tabLayout,getLayoutInflater(),TAB_TITLES_WU_WEI);
                                            }
                                        });
//                                        if (success) {
//
//                                        } else {
//
//                                        }
                                    }
                                });
                            }
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
    private void WuWeiinitWindPages() {
        adapter = new WuWeiWindPagerAdapter(getSupportFragmentManager(),bundleData);
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
    public interface OnInitWindDataFinishedListener{
        void OnInitWindDataListener(String inspurExtensionAddress, String inspurExtensionAddress2, List<WindAddressBody> WindBodyList);
    }
    public void initWindData(String json,final OnInitWindDataFinishedListener listener){
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", json, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                CommandMapBean commandMapBean = new CommandMapBean();
                String address = "";
                String address2 = "";
                ArrayList<WindAddressBody> windAllBodies = new ArrayList<>();
                try {
                    commandMapBean = gson.fromJson((String) result,CommandMapBean.class);
                } catch (JsonSyntaxException e){
                    e.printStackTrace();
                    util.showToast(WindActivity.this,commandMapBean.getMsg());
                }
                if (commandMapBean.getData().getData()!=null){
                    if (commandMapBean.getData().getData().getCommandList()!=null&&commandMapBean.getData().getData().getCommandList().size()>0){
                        for (CommandListData data : commandMapBean.getData().getData().getCommandList()){
                            if (data.getInspurControlType().equals("F0")){
                                address = data.getInspurExtensionAddress();
                            }
                            if (data.getInspurControlType().equals("09")){
                                address2 = data.getInspurExtensionAddress();
                            }
                        }
                    }
                    if (commandMapBean.getData().getData().getHardwareList()!=null&&commandMapBean.getData().getData().getHardwareList().size()>0){
                        for (HardwareListData data : commandMapBean.getData().getData().getHardwareList()){
                            if (data.getType().equals("02")){
                                WindAddressBody windAllBody = new WindAddressBody(
                                        data.getInspurAddress()
                                );
                                windAllBodies.add(windAllBody);
                            } else if (data.getType().equals("01")){
                                WindAddressBody windAllBody = new WindAddressBody(
                                        data.getInspurAddress()
                                );
                                windAllBodies.add(windAllBody);
                            }
                        }
                    }
                }
                listener.OnInitWindDataListener(address,address2,windAllBodies);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                util.showToast(WindActivity.this,errorMsg);
            }
        });
    }
    public interface OnInProjectSettingDataFinishedListener{
        void OnInitProjectSettingListener(boolean enabled);
    }
    public void initProjectSettingData(String url,final OnInProjectSettingDataFinishedListener listener){
        final Boolean[] enabled = {false};
        NewDownRawBody newDownRawBody = new NewDownRawBody();
        newDownRawBody.setUrl("/project-setting/get");
        newDownRawBody.setMethod("POST");
        newDownRawBody.setQuery(null);
        newDownRawBody.setHeaders(null);
        CountMultiple countMultiple = new CountMultiple();
        countMultiple.setUrl(url);
        newDownRawBody.setBody(countMultiple);
        String jsonData = gson.toJson(newDownRawBody);
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsonData, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                ProjectSettingBean projectSettingBean = new ProjectSettingBean();
                try {
                    projectSettingBean = gson.fromJson((String) result,ProjectSettingBean.class);
                } catch (JsonSyntaxException e){
                    e.printStackTrace();
                    util.showToast(WindActivity.this,projectSettingBean.getMsg());
                }
                if (projectSettingBean.getData()!=null&&projectSettingBean.getData().getGranarySettingList()!=null){
                    if (projectSettingBean.getData().getGranarySettingList().size()>0){
                        for (GranarySettingList data : projectSettingBean.getData().getGranarySettingList()){
                            if (data.getCommandMapId().equals(commandMapId)){
                                enabled[0] = data.getIntelligentVentilationSetting().getEnabled();
                            }
                        }
                    }
                }
                listener.OnInitProjectSettingListener(enabled[0]);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitProjectSettingListener(false);
                util.showToast(WindActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitMiBiFaDataFinishedListener{
        void OnInitMiBiFaDataListener(boolean success);
    }
    public void initMiBiFaData(final OnInitMiBiFaDataFinishedListener listener){
        int i=1,j=1;
        for (WindStatusBody data : SharedData.WindStatusList){
            if (data.getHardwareData().getName().contains("仓窗")) {
                data.setTag(i);
                SharedData.WindStatusList_CangChuang.add(data);
                i++;
            } else if (data.getHardwareData().getName().contains("通风口")) {
                data.setTag(j);
                SharedData.WindStatusList_TongFengKou.add(data);
                j++;
            }
        }
        if (listener!=null){
            listener.OnInitMiBiFaDataListener(true);
        } else {
            listener.OnInitMiBiFaDataListener(false);
        }
    }
    public interface OnInitAllDataFinishedListener{
        void OnInitAllDataListener(boolean success);
    }
    public void initAllData(String COMMANDMAPID, String URL, String data, String address, final OnInitAllDataFinishedListener listener){
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", data, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindStatusBean windStatusBean = new WindStatusBean();
                try {
                    windStatusBean = gson.fromJson((String) result,WindStatusBean.class);
                } catch (JsonSyntaxException e){
                    e.printStackTrace();
                    util.showToast(WindActivity.this,windStatusBean.getMsg());
                    return;
                }
                if (windStatusBean.getData()!=null&&windStatusBean.getData().getWindStatusList()!=null&&windStatusBean.getData().getWindStatusList().size()>0){
                    int i=1;
                    int j=1;
                    for (WindOrLightStatusList data : windStatusBean.getData().getWindStatusList()){
                        if (data.getHardwareInfo().getType().equals("01")){
                            WindStatusBody windStatusBody = new WindStatusBody(
                                    i,
                                    "01",
                                    COMMANDMAPID,
                                    URL,
                                    data.getHardwareStatus(),
                                    address,
                                    data.getHardwareInfo(),
                                    data.getHardwareInfo().getId()
                            );
                            SharedData.WindStatusList.add(windStatusBody);
                            i++;
                        }
                        if (data.getHardwareInfo().getType().equals("02")){
                            WindStatusBody windStatusBody = new WindStatusBody(
                                    j,
                                    "02",
                                    COMMANDMAPID,
                                    URL,
                                    data.getHardwareStatus(),
                                    address,
                                    data.getHardwareInfo(),
                                    data.getHardwareInfo().getFanMapWindowId()
                            );
                            SharedData.WindStatusList_FengJi.add(windStatusBody);
                            j++;
                        }
                    }
                }
                listener.OnInitAllDataListener(true);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitAllDataListener(false);
                util.showToast(WindActivity.this,errorMsg);
            }
        });

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