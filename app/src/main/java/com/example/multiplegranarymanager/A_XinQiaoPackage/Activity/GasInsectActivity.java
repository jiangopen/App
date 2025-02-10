package com.example.multiplegranarymanager.A_XinQiaoPackage.Activity;

import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.Pager.GasInsectPagerAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.GasInsectBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

public class GasInsectActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    Gson gson = new Gson();
    private Util1 util = new Util1();
    private PagerAdapter adapter;
    private final String[] TAB_TITLES = new String[]{"气体检测","虫害检测"};
    private Bundle bundleData = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_insect);
        viewPager = findViewById(R.id.view_pages);
        tabLayout = findViewById(R.id.tab_layout);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle!=null){
            String data = bundle.getString("气体虫害检测请求体");
            String commandMapId = bundle.getString("commandMapId");
            String url = bundle.getString("url");
            initGasInsectData(data, new OnInitGasInsectDataListener() {
                @Override
                public void OnInitGasInsectListener(GasInsectBody success) {
                    bundleData.putString("commandMapId", commandMapId);
                    bundleData.putString("url",url);
                    bundleData.putString("deviceType",deviceType);
                    bundleData.putString("地址参数",success.getInspurExtensionAddress());
                    bundleData.putParcelableArrayList("选通器列表",success.getInspurChannelNumberMap());
                    initGasInsectPages();
                    setGasInsectTabs(tabLayout,getLayoutInflater(),TAB_TITLES);
                }
            });
        }
    }
    private void setGasInsectTabs(TabLayout tabLayout, LayoutInflater layoutInflater, String[] tabTitles) {
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
    private void initGasInsectPages() {
        adapter = new GasInsectPagerAdapter(getSupportFragmentManager(),bundleData);
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
    public interface OnInitGasInsectDataListener{
        void OnInitGasInsectListener(GasInsectBody success);
    }
    public void initGasInsectData(String json,final OnInitGasInsectDataListener listener){
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", json, new OkHttpUtilTWO.ReqCallBack(){
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                CommandMapBean commandMapBean = new CommandMapBean();
                GasInsectBody gasInsectBody = new GasInsectBody();
                try {
                    commandMapBean = gson.fromJson((String) result,CommandMapBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitGasInsectListener(null);
                    e.printStackTrace();
                    util.showToast(GasInsectActivity.this,"数据解析出错");
                }
                if (commandMapBean.getData().getData()!=null){
                    if (commandMapBean.getData().getData().getCommandList()!=null&&commandMapBean.getData().getData().getCommandList().size()>0){
                        for (CommandListData data : commandMapBean.getData().getData().getCommandList()){
                            if (data.getInspurControlType().equals("0D")){
                                gasInsectBody.setInspurExtensionAddress(data.getInspurExtensionAddress());
                            }
                        }
                        if (commandMapBean.getData().getData().getGasInsectInfo().getInspurChannelNumberMap()!=null&&commandMapBean.getData().getData().getGasInsectInfo().getInspurChannelNumberMap().size()>0){
                            gasInsectBody.setInspurChannelNumberMap(commandMapBean.getData().getData().getGasInsectInfo().getInspurChannelNumberMap());
                        }
                    }
                }
                if (listener!=null){
                    listener.OnInitGasInsectListener(gasInsectBody);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitGasInsectListener(null);
                util.showToast(GasInsectActivity.this,errorMsg);
            }
        });
    }
}