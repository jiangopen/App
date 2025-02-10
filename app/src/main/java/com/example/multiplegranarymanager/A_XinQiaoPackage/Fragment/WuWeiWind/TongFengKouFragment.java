package com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.WuWeiWind;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.WindActivity.intToHex;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.Wind.HuaiNan.MiBiFaAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind.WindHistoryDataBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.WindTestInTimeBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindStatusBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.SharedData.SharedData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.CustomEditText;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;

public class TongFengKouFragment extends Fragment {
    CustomEditText Search_Input;
    TextView Txt_All_Open,Txt_All_Close,Txt_Wind_Name;
    TextView mFootView;
    FrameLayout layout_main;
    LinearLayout layout_no_shebei;
    RecyclerView mListView;
    private String Ko_address = "";
    private String commandMapId = "";
    private String url = "";
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    private MiBiFaAdapter adapter_TongFengKou;
    private HeaderViewAdapter headerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mibifa, container, false);
        //加载组件
        initView(view);
        Bundle bundle = getArguments();
        if (bundle!=null) {
            layout_main.setVisibility(View.VISIBLE);
            layout_no_shebei.setVisibility(View.GONE);
            commandMapId = bundle.getString("commandMapId");
            url = bundle.getString("url");
            String data = bundle.getString("jsonData");
            Boolean enabled = bundle.getBoolean("wind");
            Ko_address = bundle.getString("Fa_address");
            suoPingDialog = new SuoPingDialog(getContext(),"加载中,请稍等......");
            suoPingDialog.show();
            initWindPattern(commandMapId, url, enabled, new OnInitWindPatternFininshedListener() {
                @Override
                public void OnInitWindPatternListener(String Pattern) {
                    Txt_Wind_Name.setText(Pattern);
                    suoPingDialog.dismiss();
                    Log.d("zyq", " : "+ SharedData.WindStatusList_TongFengKou.size());
                    refreshView(getContext(),SharedData.WindStatusList_TongFengKou);
                }
            });
        }
        return view;
    }
    private void refreshView(Context context, ArrayList<WindStatusBody> windStatusList) {
        View vfoot = LayoutInflater.from(context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        int[] layoutIds = new int[]{R.layout.card_wind_mibifa_cangchuang_tongfengkou_adapter_data};
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        mListView.setLayoutManager(layoutManager);
        adapter_TongFengKou = new MiBiFaAdapter(windStatusList,layoutIds,context);
        headerViewAdapter = new HeaderViewAdapter(adapter_TongFengKou);
        headerViewAdapter.addFooterView(vfoot);
        mListView.setAdapter(headerViewAdapter);
        mFootView.setText("共"+adapter_TongFengKou.getFreshDates().size()+"台设备");
    }
    private void initView(View view) {
        Search_Input = view.findViewById(R.id.edit_search);
        Txt_All_Open = view.findViewById(R.id.txt_all_open);
        Txt_All_Close = view.findViewById(R.id.txt_all_close);
        Txt_Wind_Name = view.findViewById(R.id.txt_wind_moshi);
        layout_main = view.findViewById(R.id.layout_frame_main);
        layout_no_shebei = view.findViewById(R.id.shebei);
        mListView = view.findViewById(R.id.member);
        Txt_All_Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAllTestInTime("01", new OnInitAllTestInTimeFinishedListener() {
                    @Override
                    public void OnInitAllTestInTimeListener(boolean success) {
                        suoPingDialog.dismiss();
                        adapter_TongFengKou.notifyDataSetChanged();
                    }
                });
            }
        });
        Txt_All_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAllTestInTime("00", new OnInitAllTestInTimeFinishedListener() {
                    @Override
                    public void OnInitAllTestInTimeListener(boolean success) {
                        suoPingDialog.dismiss();
                        adapter_TongFengKou.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    public interface OnInitWindPatternFininshedListener{
        void OnInitWindPatternListener(String Pattern);
    }
    public void initWindPattern(String COMMANDMAPID, String URL, Boolean WIND, final OnInitWindPatternFininshedListener listener){
        if (WIND){
            NewDownRawBody newDownRawBody = new NewDownRawBody();
            newDownRawBody.setUrl("/history-data/count");
            newDownRawBody.setMethod("POST");
            newDownRawBody.setQuery(null);
            newDownRawBody.setHeaders(null);
            CountMultiple countMultiple = new CountMultiple();
            countMultiple.setCommandMapId(COMMANDMAPID);
            countMultiple.setCount(1);
            countMultiple.setUrl(URL);
            countMultiple.setDataType("smartWindRecord");
            newDownRawBody.setBody(countMultiple);
            String jsonData = gson.toJson(newDownRawBody);
            OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsonData, new OkHttpUtilTWO.ReqCallBack() {
                @Override
                public void onReqSuccess(Object result) throws JSONException {
                    String pattern = "";
                    WindHistoryDataBean windHistoryDataBean = new WindHistoryDataBean();
                    try {
                        windHistoryDataBean = gson.fromJson((String) result,WindHistoryDataBean.class);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        util.showToast(getContext(),windHistoryDataBean.getMsg());
                    }
                    if (windHistoryDataBean.getData()!=null&&windHistoryDataBean.getData().size()>0){
                        if (windHistoryDataBean.getData().get(0).getDataContent()!=null){
                            if (windHistoryDataBean.getData().get(0).getDataContent().getPattern()!=null){
                                pattern = windHistoryDataBean.getData().get(0).getDataContent().getPattern();
                            } else {
                                pattern = "无符合模式";
                            }
                        }
                    }
                    listener.OnInitWindPatternListener(pattern);
                }
                @Override
                public void onReqFailed(String errorMsg) {
                    listener.OnInitWindPatternListener("无符合模式");
                    util.showToast(getContext(),errorMsg);
                }
            });
        } else {
            listener.OnInitWindPatternListener("手动通风");
        }
    }
    public interface OnInitAllTestInTimeFinishedListener{
        void OnInitAllTestInTimeListener(boolean success);
    }
    public void initAllTestInTime(String flag, final OnInitAllTestInTimeFinishedListener listener){
        String address = "";
        int i=0;
        int j=0;
        for (WindStatusBody data : SharedData.WindStatusList_TongFengKou){
            if (flag.equals("01") && data.getHardwareStatus().equals("01")){
                i++;
                address += data.getHardwareData().getInspurAddress()+flag;
            } else if (flag.equals("00") && data.getHardwareStatus().equals("02")) {
                i++;
            }
        }
        for (WindStatusBody data2 : SharedData.WindStatusList_FengJi) {
            if (data2.getHardwareData().getName().contains("仓底轴流风机")){
                Log.d("jht", "initAllTestInTime: " + data2.getHardwareData().getName());
                if (data2.getHardwareStatus().equals("01")) {
                    j++;
                }
            }
        }

        Log.d("jht", "initAllTestInTime: i"+i+"     j"+j);
        if (flag.equals("01")) {
            String body =
                    "AA55"
                            + Ko_address
                            + "09FFFF"
                            + intToHex(i*2,4)
                            + address
                            + "FFFF0D0A";
            Log.d("jht", "body: "+body);
            NewDownRawBody newDownRawBody = new NewDownRawBody();
            newDownRawBody.setUrl("/measure");
            newDownRawBody.setMethod("POST");
            newDownRawBody.setQuery(null);
            newDownRawBody.setHeaders(null);
            CountMultiple countMultiple = new CountMultiple();
//        countMultiple.setUrl(url);
            countMultiple.setMeasureType("-1");
            countMultiple.setCommandMapId(commandMapId);
            countMultiple.setCommandContent(body);
            newDownRawBody.setBody(countMultiple);
            String jsonData = gson.toJson(newDownRawBody);
            suoPingDialog = new SuoPingDialog(getContext(),"正在操作，请稍等......");
            suoPingDialog.setCancelable(true);
            suoPingDialog.show();
            OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsonData, new OkHttpUtilTWO.ReqCallBack() {
                @Override
                public void onReqSuccess(Object result) throws JSONException {
                    WindTestInTimeBean windTestInTimeBean = new WindTestInTimeBean();
                    try{
                        windTestInTimeBean = gson.fromJson((String) result,WindTestInTimeBean.class);
                    } catch (JsonSyntaxException e){
                        e.printStackTrace();
                        util.showToast(getContext(),"通风一键解析有问题");
                    }
                    Log.d("jht", "onReqSuccess: "+windTestInTimeBean.getMsg());
                    if (windTestInTimeBean.getData()!=null){
                        if (flag.equals("01")){
                            if (windTestInTimeBean.getData().getWindOpenStatus()!=null&&windTestInTimeBean.getData().getWindOpenStatus().size()>0){
                                for (int i=0;i<windTestInTimeBean.getData().getWindOpenStatus().size();i++){
                                    if (windTestInTimeBean.getData().getWindOpenStatus().get(i).equals("00")){
                                        SharedData.WindStatusList_FengJi.get(i).setHardwareStatus("02");
                                    }
                                }
                            }
                        } else if(flag.equals("00")){
                            if (windTestInTimeBean.getData().getWindCloseStatus()!=null&&windTestInTimeBean.getData().getWindCloseStatus().size()>0){
                                for (int i=0;i<windTestInTimeBean.getData().getWindCloseStatus().size();i++){
                                    if (windTestInTimeBean.getData().getWindCloseStatus().get(i).equals("00")){
                                        SharedData.WindStatusList_FengJi.get(i).setHardwareStatus("01");
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
                    util.showToast(getContext(),errorMsg);
                }
            });
        } else if (flag.equals("00") && j==6) {
            String body =
                    "AA55"
                            + Ko_address
                            + "09FFFF"
                            + intToHex(i*2,4)
                            + address
                            + "FFFF0D0A";
            Log.d("jht", "body: "+body);
            NewDownRawBody newDownRawBody = new NewDownRawBody();
            newDownRawBody.setUrl("/measure");
            newDownRawBody.setMethod("POST");
            newDownRawBody.setQuery(null);
            newDownRawBody.setHeaders(null);
            CountMultiple countMultiple = new CountMultiple();
//        countMultiple.setUrl(url);
            countMultiple.setMeasureType("-1");
            countMultiple.setCommandMapId(commandMapId);
            countMultiple.setCommandContent(body);
            newDownRawBody.setBody(countMultiple);
            String jsonData = gson.toJson(newDownRawBody);
            suoPingDialog = new SuoPingDialog(getContext(),"正在操作，请稍等......");
            suoPingDialog.setCancelable(true);
            suoPingDialog.show();
            OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsonData, new OkHttpUtilTWO.ReqCallBack() {
                @Override
                public void onReqSuccess(Object result) throws JSONException {
                    WindTestInTimeBean windTestInTimeBean = new WindTestInTimeBean();
                    try{
                        windTestInTimeBean = gson.fromJson((String) result,WindTestInTimeBean.class);
                    } catch (JsonSyntaxException e){
                        e.printStackTrace();
                        util.showToast(getContext(),"通风一键解析有问题");
                    }
                    Log.d("jht", "onReqSuccess: "+windTestInTimeBean.getMsg());
                    if (windTestInTimeBean.getData()!=null){
                        if (flag.equals("01")){
                            if (windTestInTimeBean.getData().getWindOpenStatus()!=null&&windTestInTimeBean.getData().getWindOpenStatus().size()>0){
                                for (int i=0;i<windTestInTimeBean.getData().getWindOpenStatus().size();i++){
                                    if (windTestInTimeBean.getData().getWindOpenStatus().get(i).equals("00")){
                                        SharedData.WindStatusList_FengJi.get(i).setHardwareStatus("02");
                                    }
                                }
                            }
                        } else if(flag.equals("00")){
                            if (windTestInTimeBean.getData().getWindCloseStatus()!=null&&windTestInTimeBean.getData().getWindCloseStatus().size()>0){
                                for (int i=0;i<windTestInTimeBean.getData().getWindCloseStatus().size();i++){
                                    if (windTestInTimeBean.getData().getWindCloseStatus().get(i).equals("00")){
                                        SharedData.WindStatusList_FengJi.get(i).setHardwareStatus("01");
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
                    util.showToast(getContext(),errorMsg);
                }
            });
        } else if (flag.equals("00") && j!=6) {
            listener.OnInitAllTestInTimeListener(false);
            util.showToast(getContext(),"请关闭对应的风机后再试！");
        } else {
            listener.OnInitAllTestInTimeListener(false);
            util.showToast(getContext(),"请稍后重试！");
        }
    }
}