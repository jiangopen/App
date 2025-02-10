package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Fragment;

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

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.InsectDataAdapter;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.CommandMap.InspurChannelNumberMap;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.GasInsectBean.GasInsectTestInTimeBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.CardInsectAdapterBody;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.CountMultiple;
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
import java.util.List;

public class InsectFragment extends Fragment {
    CustomEditText Search_Input;
    TextView Txt_All_Test;
    TextView mFootView;
    FrameLayout layout_main;
    LinearLayout layout_no_shebei;
    RecyclerView mListView;
    private Gson gson = new Gson();
    private String deviceType = "06";
    private InsectDataAdapter adapter_Insect;
    private HeaderViewAdapter headerViewAdapter;
    private ArrayList<CardInsectAdapterBody> InsectCardList = new ArrayList<>();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function_insect, container, false);
        //加载组件
        initView(view);
        Bundle bundle = getArguments();
        assert bundle != null;
        if (bundle.getParcelableArrayList("选通器列表")!=null&&bundle.getString("地址参数")!=null){
            InsectCardList.clear();
            String commandMapId = bundle.getString("commandMapId");
            String url = bundle.getString("url");
            String inspurExtensionAddress = bundle.getString("地址参数");
            ArrayList<InspurChannelNumberMap> datas = bundle.getParcelableArrayList("选通器列表");
            for (InspurChannelNumberMap data : datas){
                CardInsectAdapterBody cardInsectAdapterBody = new CardInsectAdapterBody(
                        hex2int(data.getInspurChannelNumber()),
                        inspurExtensionAddress,
                        data.getInspurChannelNumber(),
                        deviceType,
                        url,
                        commandMapId,
                        -1
                );
                InsectCardList.add(cardInsectAdapterBody);
            }
            refresh(getContext(),InsectCardList);
        } else {
            layout_main.setVisibility(View.GONE);
            layout_no_shebei.setVisibility(View.VISIBLE);
        }
        return view;
    }
    private void refresh(Context context, ArrayList<CardInsectAdapterBody> insectCardList) {
        View vfoot = LayoutInflater.from(context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        int[] layoutIds = new int[]{R.layout.card_insect_adapter_data};
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        mListView.setLayoutManager(layoutManager);
        adapter_Insect = new InsectDataAdapter(insectCardList,layoutIds,getContext());
        headerViewAdapter = new HeaderViewAdapter(adapter_Insect);
        headerViewAdapter.addFooterView(vfoot);
        mListView.setAdapter(headerViewAdapter);
        mFootView.setText("共"+adapter_Insect.getFreshDates().size()+"个选通器");
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
                for (int i=0;i<InsectCardList.size();i++){
                    if (i%3==0){
                        int measureGroupCountNum = 3;
                        int measureGroupCount = (int) Math.floor(measureGroupCountNum / 8) + (measureGroupCountNum % 8 == 0 ? 0 : 1);
                        String measureGroup = "";
                        for (int j=0;j<measureGroupCount;j++){
                            measureGroup+="FF";
                        }
                        NewDownRawBody newDownRawBody = new NewDownRawBody();
                        newDownRawBody.setUrl("/measure");
                        newDownRawBody.setMethod("POST");
                        newDownRawBody.setQuery("");
                        newDownRawBody.setHeaders("");
                        CountMultiple countMultiple = new CountMultiple();
                        countMultiple.setCommandMapId(InsectCardList.get(i).getCommandMapId());
                        countMultiple.setUrl(InsectCardList.get(i).getUrl());
                        String body =
                                  "AA55"
                                + InsectCardList.get(i).getInspurExtensionAddress()
                                + "0DFFFF"
                                + intToHex(5+measureGroupCount,4)
                                + InsectCardList.get(i).getInspurChannelNumber()
                                + intToHex(measureGroupCountNum,4)
                                + measureGroup
                                + InsectCardList.get(i).getDetectType()
                                + "FFFF"
                                + "0D0A";
                        countMultiple.setCommandContent(body);
                        newDownRawBody.setBody(countMultiple);
                        String jsonData = gson.toJson(newDownRawBody);
                        jsonDataList.add(jsonData);
                    }
                }
                initInsectTestData(jsonDataList, new OnInitTestInsectDataFinishedListener() {
                    @Override
                    public void OnInitTestInsectListener(boolean success) {
                        adapter_Insect.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    public interface OnInitTestInsectDataFinishedListener{
        void OnInitTestInsectListener(boolean success);
    }
    public void initInsectTestData(List<String> json, final OnInitTestInsectDataFinishedListener listener){
        suoPingDialog = new SuoPingDialog(getContext(),"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        for (String data : json){
            OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + MainActivity.deviceType + "&bodyType=json&timeout=30", data, Token, new OkHttpUtil.ReqCallBack() {
                @Override
                public void onReqSuccess(Object result) throws JSONException {
                    //这里检测接收到的数据类型并不知道，很大概率会出错
                    GasInsectTestInTimeBean insectTestInTimeBean = new GasInsectTestInTimeBean();
                    try {
                        insectTestInTimeBean = gson.fromJson((String) result, GasInsectTestInTimeBean.class);
                        Log.d("zyq", "onReqSuccess: "+insectTestInTimeBean.getMsg()+"\n"+insectTestInTimeBean.getData().getCommandMapId());
                    } catch (JsonSyntaxException e) {
                        Log.d("zyq", "onReqSuccess: "+insectTestInTimeBean.getMsg()+"\n"+insectTestInTimeBean.getData().getCommandMapId());
                        suoPingDialog.dismiss();
                        e.printStackTrace();
                        util.showToast(getContext(),insectTestInTimeBean.getMsg());
                    }
                    if (insectTestInTimeBean.getData()!=null&&insectTestInTimeBean.getData().getDataContent()!=null&&insectTestInTimeBean.getData().getDataContent().getGasInsectList()!=null){
                        if (insectTestInTimeBean.getData().getDataContent().getGasInsectList().size()>0){
                            for (CardInsectAdapterBody data : InsectCardList){
                                if (data.getCommandMapId().equals(insectTestInTimeBean.getData().getCommandMapId())){
                                    data.setInsect(insectTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getInsect());
                                }
                                suoPingDialog.dismiss();
                                util.showToast(getContext(),"检测成功！");
                            }
                        }
                    }
                    if (listener!=null){
                        listener.OnInitTestInsectListener(true);
                    }
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    listener.OnInitTestInsectListener(false);
                    suoPingDialog.dismiss();
                    util.showToast(getContext(),errorMsg);
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