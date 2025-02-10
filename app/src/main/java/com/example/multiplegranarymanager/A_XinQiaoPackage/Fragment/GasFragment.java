package com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment;

import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.Token;


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
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.GasDataAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.InspurChannelNumberMap;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.GasInsectBean.GasInsectTestInTimeBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CardGasAdapterBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.CustomEditText;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GasFragment extends Fragment {
    CustomEditText Search_Input;
    TextView Txt_All_Test;
    TextView mFootView;
    FrameLayout layout_main;
    LinearLayout layout_no_shebei;
    RecyclerView mListView;
    private Gson gson = new Gson();
    private String DEVICETYPE = "05";
    private String devicetype = "cloud_request_trans_xinqiao";
    private GasDataAdapter adapter_Gas;
    private HeaderViewAdapter headerViewAdapter;
    private ArrayList<CardGasAdapterBody> GasCardList = new ArrayList<>();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function_gas, container, false);
        //加载组件
        initView(view);
        Bundle bundle = getArguments();
        assert bundle != null;
        if (bundle.getParcelableArrayList("选通器列表")!=null&&bundle.getString("地址参数")!=null){
            GasCardList.clear();
            String commandMapId = bundle.getString("commandMapId");
            String url = bundle.getString("url");
            String inspurExtensionAddress = bundle.getString("地址参数");
            ArrayList<InspurChannelNumberMap> datas = bundle.getParcelableArrayList("选通器列表");
            for (InspurChannelNumberMap data : datas) {
                int i = hex2int(data.getInspurChannelNumber());
                CardGasAdapterBody cardGasAdapterBody = new CardGasAdapterBody(
                        i,
                        inspurExtensionAddress,
                        data.getInspurChannelNumber(),
                        DEVICETYPE,
                        url,
                        commandMapId,
                        null,
                        null,
                        null,
                        null
                );
                GasCardList.add(cardGasAdapterBody);
            }
            refresh(getContext(),GasCardList);
        } else {
            layout_main.setVisibility(View.GONE);
            layout_no_shebei.setVisibility(View.VISIBLE);
        }
        return view;
    }
    private void refresh(Context context, ArrayList<CardGasAdapterBody> gasCardList) {
        View vfoot = LayoutInflater.from(context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        int[] layoutIds = new int[]{R.layout.card_gas_adapter_data};
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        mListView.setLayoutManager(layoutManager);
        adapter_Gas = new GasDataAdapter(gasCardList,layoutIds,getContext());
        headerViewAdapter = new HeaderViewAdapter(adapter_Gas);
        headerViewAdapter.addFooterView(vfoot);
        mListView.setAdapter(headerViewAdapter);
        mFootView.setText("共"+adapter_Gas.getFreshDates().size()+"个选通器");
    }
    private void initView(View view) {
        Search_Input = view.findViewById(R.id.edit_search);
        Txt_All_Test = view.findViewById(R.id.txt_all_test);
        layout_main = view.findViewById(R.id.layout_frame_main);
        layout_no_shebei = view.findViewById(R.id.shebei);
        mListView = view.findViewById(R.id.member);
        //一键检测功能
        Txt_All_Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> jsonDataList = new ArrayList<>();
                for (int i=0;i<GasCardList.size();i++){
                    if (i%3==0){
                        int measureGroupCountNum = 3;
                        int measureGroupCount = (int) Math.floor(measureGroupCountNum / 8) + (measureGroupCountNum % 8 == 0 ? 0 : 1);
                        String measureGroup = "";
                        for (int j=0;j<measureGroupCount;j++){
                            measureGroup+="FF";
                        }
                        NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                        newDownRawBody.setUrl("/measure");
                        newDownRawBody.setMethod("POST");
                        newDownRawBody.setQuery(null);
                        newDownRawBody.setHeaders(null);
                        CountMultiple countMultiple = new CountMultiple();
                        countMultiple.setCommandMapId(GasCardList.get(i).getCommandMapId());
                        countMultiple.setUrl(GasCardList.get(i).getUrl());
                        String body =
                                  "AA55"
                                + GasCardList.get(i).getInspurExtensionAddress()
                                + "0DFFFF"
                                + intToHex(5+measureGroupCount,4)
                                + GasCardList.get(i).getInspurChannelNumber()
                                + intToHex(measureGroupCountNum,4)
                                + measureGroup
//                                + GasCardList.get(i).getDetectType()
                                + "07"
                                + "FFFF"
                                + "0D0A";
                        countMultiple.setCommandContent(body);
                        newDownRawBody.setBody(countMultiple);
                        String jsonData = gson.toJson(newDownRawBody);
                        jsonDataList.add(jsonData);
                    }
                }
                initGasTestData(jsonDataList, new OnInitTestGasDataFinishedListener() {
                    @Override
                    public void OnInitTestGasListener(boolean success) {
                        adapter_Gas.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    public interface OnInitTestGasDataFinishedListener{
        void OnInitTestGasListener(boolean success);
    }
    public void initGasTestData(List<String> json,final OnInitTestGasDataFinishedListener listener){
        suoPingDialog = new SuoPingDialog(getContext(),"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        for (String data : json){
            Log.d("jht", "initGasTestData: " + data);
            OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + devicetype + "&timeOut=30", data, new OkHttpUtilTWO.ReqCallBack() {
                @Override
                public void onReqSuccess(Object result) throws JSONException {
                    //这里检测接收到的数据类型并不知道，很大概率会出错
                    GasInsectTestInTimeBean gasTestInTimeBean = new GasInsectTestInTimeBean();
                    try {
                        gasTestInTimeBean = gson.fromJson((String) result, GasInsectTestInTimeBean.class);
                        Log.d("zyq", "onReqSuccess: " + gasTestInTimeBean.getMsg() + "\n" + gasTestInTimeBean.getData().getCommandMapId());
                    } catch (JsonSyntaxException e) {
                        Log.d("zyq", "onReqSuccess: " + gasTestInTimeBean.getMsg() + "\n" + gasTestInTimeBean.getData().getCommandMapId());
                        suoPingDialog.dismiss();
                        e.printStackTrace();
                        util.showToast(getContext(), gasTestInTimeBean.getMsg());
                    }
                    if (gasTestInTimeBean.getData() != null && gasTestInTimeBean.getData().getDataContent() != null && gasTestInTimeBean.getData().getDataContent().getGasInsectList() != null) {
                        if (gasTestInTimeBean.getData().getDataContent().getGasInsectList().size() > 0) {
                            for (CardGasAdapterBody data : GasCardList) {
                                if (data.getCommandMapId().equals(gasTestInTimeBean.getData().getCommandMapId())) {
                                    data.setCO2(gasTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getCo2());
                                    data.setO2(gasTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getO2());
                                    data.setN2(99 - gasTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getCo2() - gasTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getO2());
                                    data.setPH3(gasTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getPh3());
                                }
                                suoPingDialog.dismiss();
                                util.showToast(getContext(), "检测成功！");
                            }
                        }
                    }
                    if (listener != null) {
                        listener.OnInitTestGasListener(true);
                    }
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    listener.OnInitTestGasListener(false);
                    suoPingDialog.dismiss();
                    util.showToast(getContext(), errorMsg);
                }
            });

        }
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