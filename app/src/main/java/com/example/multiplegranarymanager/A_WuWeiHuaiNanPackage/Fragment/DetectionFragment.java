package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Fragment;

import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.Token;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.DataActivity;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.NewCardDataAdapter;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.HistoryCountMultipleBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.TestInTimeBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.CardAdapterBody;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.NewDownRawMultipleBody;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.TemGranaryBody;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.CustomEditText;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.SideBar;
import com.example.multiplegranarymanager.Util.Util1;
import com.example.multiplegranarymanager.Util.WonderUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DetectionFragment extends Fragment implements View.OnClickListener, TextWatcher, SideBar.OnTouchingLetterChangedListener{
    CustomEditText Search_Input;
    ImageView MoreList,Img_More;
    TextView mFootView;
    TextView Text_Tem,Text_Hum,Text_Gas,Text_KongTiao,Text_TongFeng,Text_NengHao,Text_TianQi,Text_ZhaoMing,Text_Text,Text_Select,Text_Clean,Text_Test;
    TextView selectedTextView = null;
    SmartRefreshLayout refreshLayout;
    LinearLayout WuShebei,infos,layout_more;
    RecyclerView mListView;
    Button btn1,btn2,btn3;
    Bundle bundle = new Bundle();
    //显示或收起功能列表判断
    private boolean Img_More_Flag = true;
    private int positione = -1;
    private Util1 util = new Util1();
    private String deviceType;
    private View ve;
    private TemGranaryBody iteme;
    //    private CardDataAdapter adapter_Card;
    private NewCardDataAdapter adapter_Card;
    private HeaderViewAdapter headerViewAdapter;
    private ArrayList<CardAdapterBody> CardDataAdapterList = new ArrayList<>();
    private ArrayList<GranaryListBean.Data> granarylist = new ArrayList<>();
    private ArrayList<CardAdapterBody> mSelectitems = new ArrayList<>();
    private SuoPingDialog suoPingDialog;
    private WonderUtil mWonderUtil = new WonderUtil();
    Gson gson = new Gson();
    String HistoryCountMultiple = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detection_2, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        if (bundle.getParcelableArrayList("粮仓")!=null){
            CardDataAdapterList.clear();
            granarylist = bundle.getParcelableArrayList("粮仓");
            deviceType = bundle.getString("账号");
            Log.d("jht", "granarylist: "+granarylist.size());
            Log.d("jht", "deviceType: "+deviceType);
            NewDownRawMultipleBody historyCountMultipleBody = new NewDownRawMultipleBody();
            historyCountMultipleBody.setUrl("/history-data/count/multiple");
            historyCountMultipleBody.setMethod("POST");
            historyCountMultipleBody.setHeaders("");
            historyCountMultipleBody.setQuery("");
            List<CountMultiple> body = new ArrayList<>();
            for (int i=0;i<granarylist.size();i++){
                CountMultiple multiple = new CountMultiple();
                multiple.setCount(1);
                multiple.setDataType("04");
                if (granarylist.get(i)!=null){
                    multiple.setCommandMapId(granarylist.get(i).getCommandMapId());
                    multiple.setUrl(granarylist.get(i).getUrl());
                    CardAdapterBody cardAdapterBody = new CardAdapterBody(
                            granarylist.get(i).getCommandMapId(),
                            granarylist.get(i).getGranaryKey(),
                            granarylist.get(i).getGranaryName(),
                            granarylist.get(i).getUrl(),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            false
                    );
                    CardDataAdapterList.add(cardAdapterBody);
                }
                body.add(multiple);
            }
            NewDownRawMultipleBody.list list = new NewDownRawMultipleBody.list();
            list.setList(body);
            historyCountMultipleBody.setBody(list);
            HistoryCountMultiple = gson.toJson(historyCountMultipleBody);
            initData(HistoryCountMultiple, new OnInitDataFinishedListener() {
                @Override
                public void OnInitDataListener(boolean success) {
                    if (success){
                        refreshView(getContext(),CardDataAdapterList);
                    }
                }
            });
        } else {
            WuShebei.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        }
        //加载组件
        initView(view);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData(HistoryCountMultiple, new OnInitDataFinishedListener() {
                    @Override
                    public void OnInitDataListener(boolean success) {
                        if (success){
                            refreshView(getContext(),CardDataAdapterList);
                        }
                    }
                });
                refreshLayout.finishRefresh(1500/*,false*/);//传入false表示刷新失败
                util.showToast(getContext(),"刷新成功");
            }
        });
        //设置Header为贝塞尔雷达样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()).setEnableHorizontalDrag(true));
        return view;
    }
    private void refreshView(Context context, ArrayList<CardAdapterBody> List) {
        View vfoot = LayoutInflater.from(context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        mListView.setLayoutManager(layoutManager);
        adapter_Card = new NewCardDataAdapter(List,context);
        headerViewAdapter = new HeaderViewAdapter(adapter_Card);
        headerViewAdapter.addFooterView(vfoot);
        mListView.setAdapter(headerViewAdapter);
        Log.d("jht", "adapter_Card.flag: "+adapter_Card.flag);
//        adapter_Card.setmOnClickSelectListener(mOnSelectListener);
        mFootView.setText("共"+adapter_Card.getFreshDates().size()+"台设备");
    }
    public interface OnInitDataFinishedListener {
        void OnInitDataListener(boolean success);
    }
    public void initData(String jsonData,final OnInitDataFinishedListener listener){
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=30", jsonData, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                Gson gson = new Gson();
                HistoryCountMultipleBean historyCountMultipleBean = new HistoryCountMultipleBean();
                Log.d("jht", "result: "+result);
                try {
                    historyCountMultipleBean = gson.fromJson((String) result,HistoryCountMultipleBean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    util.showToast(getContext(),"历史数据获取失败！");
                    return;
                }
                if (historyCountMultipleBean.getData()!=null&&historyCountMultipleBean.getData().getList().size()>0){
                    for (HistoryCountMultipleBean.HistoryCountMultiple history : historyCountMultipleBean.getData().getList()){
                        for (CardAdapterBody card : CardDataAdapterList){
                            if (history.getCommandMapId().equals(card.getCommandMapId())){
                                if (history.getData().size()>0&&history.getData().get(0)!=null){
                                    card.setDate(history.getData().get(0).getDate());
                                    card.setHumidityInner(history.getData().get(0).getDataContent().getHumidityInner());
                                    card.setHumidityOut(history.getData().get(0).getDataContent().getHumidityOut());
                                    card.setTempInner(history.getData().get(0).getDataContent().getTempInner());
                                    card.setTempOut(history.getData().get(0).getDataContent().getTempOut());
                                    card.setTempList(history.getData().get(0).getDataContent().getTempList());
                                } else {
                                    card.setDate(null);
                                    card.setHumidityInner(null);
                                    card.setHumidityOut(null);
                                    card.setTempInner(null);
                                    card.setTempOut(null);
                                    card.setTempList(null);
                                }
                            }
                        }
                    }
                    //按名字排序
                    Collections.sort(CardDataAdapterList, new Comparator<CardAdapterBody>() {
                        @Override
                        public int compare(CardAdapterBody o1, CardAdapterBody o2) {
                            return mWonderUtil.compareTo(o1.getGranaryName(), o2.getGranaryName());
                        }
                    });
                }
                if (listener!=null){
                    listener.OnInitDataListener(true);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitDataListener(false);
                util.showToast(getContext(),errorMsg);
            }
        });
    }
    private void initView(View view) {
        //搜索框
        Search_Input = view.findViewById(R.id.school_friend_member_search_input);
        //刷新界面
        refreshLayout = view.findViewById(R.id.layout_refresh);
        //无配置界面
        WuShebei = view .findViewById(R.id.shebei);
        //RecyclerView组件
        mListView = view.findViewById(R.id.member);
        //侧边栏弹出或收回键
        Img_More = view.findViewById(R.id.img_more);
        //侧边栏视图
        layout_more = view.findViewById(R.id.layout_more);
        //侧边栏功能键
        Text_Tem = view.findViewById(R.id.txt_tem);
//        Text_Hum = view.findViewById(R.id.txt_hum);
        Text_Gas = view.findViewById(R.id.txt_gas);
        Text_KongTiao = view.findViewById(R.id.txt_kongtiao);
        Text_TongFeng = view.findViewById(R.id.txt_tongfeng);
        Text_NengHao = view.findViewById(R.id.txt_nenghao);
        Text_TianQi = view.findViewById(R.id.txt_tianqi);
        Text_ZhaoMing = view.findViewById(R.id.txt_zhaoming);
        //功能显示组件
        Text_Text = view.findViewById(R.id.txt_text);
        //全选全清功能键
        Text_Select = view.findViewById(R.id.txt_all_select);
        Text_Clean = view.findViewById(R.id.txt_all_clean);
        //检测功能键
        Text_Test = view.findViewById(R.id.txt_now_test);
        //设置首选为温湿度检测
        Text_Tem.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
        selectedTextView = Text_Tem;
        //对这些组件设置监听事件
        Text_Tem.setOnClickListener(this);
//        Text_Hum.setOnClickListener(this);
        Text_Gas.setOnClickListener(this);
        Text_KongTiao.setOnClickListener(this);
        Text_TongFeng.setOnClickListener(this);
        Text_NengHao.setOnClickListener(this);
        Text_TianQi.setOnClickListener(this);
        Text_ZhaoMing.setOnClickListener(this);
        Text_Select.setOnClickListener(this);
        Text_Clean.setOnClickListener(this);
        Text_Test.setOnClickListener(this);
        Img_More.setOnClickListener(this);
        //刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshView(getContext(),CardDataAdapterList);
                refreshLayout.finishRefresh(1500);
                util.showToast(getContext(),"刷新成功");
            }
        });
        //设置Header为贝塞尔雷达样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()).setEnableHorizontalDrag(true));
        //根据不同账号判断存在哪些功能
        Log.d("jht", "deviceType: "+deviceType);
        if (deviceType.equals("cloud_request_trans_huainan")){
//            Text_Hum.setVisibility(View.GONE);
            Text_Gas.setVisibility(View.GONE);
            Text_NengHao.setVisibility(View.GONE);
            Text_TianQi.setVisibility(View.GONE);
            Text_ZhaoMing.setVisibility(View.GONE);
        } else if (deviceType.equals("cloud_request_trans_wuwei_shili")||deviceType.equals("cloud_request_trans_wuwei_tuqiao")){

        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        ArrayList<CardAdapterBody> lockbean = new ArrayList<>(CardDataAdapterList);
        if (adapter_Card != null){
            adapter_Card.setFreshDates(lockbean);
            positione = -1;
            infos.setVisibility(View.GONE);
        }
    }
    @Override
    public void afterTextChanged(Editable s) {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_tem:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_Tem.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_Tem;
                Text_Text.setText("温湿度检测");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);

                break;
//            case R.id.txt_hum:
//                if (selectedTextView != null){
//                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
//                }
//                Text_Hum.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
//                selectedTextView = Text_Hum;
//                Text_Text.setText("温湿水检测");
//                refreshLayout.setVisibility(View.VISIBLE);
//                WuShebei.setVisibility(View.GONE);
//                break;
            case R.id.txt_gas:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_Gas.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_Gas;
                Text_Text.setText("气体虫害检测");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_kongtiao:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_KongTiao.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_KongTiao;
                Text_Text.setText("空调控制");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_tongfeng:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_TongFeng.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_TongFeng;
                Text_Text.setText("通风控制");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_nenghao:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_NengHao.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_NengHao;
                Text_Text.setText("能耗检测");
                refreshLayout.setVisibility(View.GONE);
                WuShebei.setVisibility(View.VISIBLE);
                break;
            case R.id.txt_tianqi:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_TianQi.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_TianQi;
                Text_Text.setText("天气检测");
                refreshLayout.setVisibility(View.GONE);
                WuShebei.setVisibility(View.VISIBLE);
                break;
            case R.id.txt_zhaoming:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_ZhaoMing.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_ZhaoMing;
                Text_Text.setText("照明控制");
                refreshLayout.setVisibility(View.GONE);
                WuShebei.setVisibility(View.VISIBLE);
                break;
            case R.id.txt_all_select:
                Text_Clean.setVisibility(View.VISIBLE);
                Text_Select.setVisibility(View.GONE);
                for (CardAdapterBody data : CardDataAdapterList){
                    if (!data.getSelected()){
                        data.setSelected(true);
                    }
                }
                adapter_Card.notifyDataSetChanged();
                break;
            case R.id.txt_all_clean:
                Text_Clean.setVisibility(View.GONE);
                Text_Select.setVisibility(View.VISIBLE);
                for (CardAdapterBody data : CardDataAdapterList){
                    if (data.getSelected()){
                        data.setSelected(false);
                    }
                }
                adapter_Card.notifyDataSetChanged();
                break;
            case R.id.txt_now_test:
                String CaoZuo = Text_Text.getText().toString();
                Log.d("jht", "CaoZuo: "+CaoZuo);
                List<CountMultiple> body = new ArrayList<>();
                TextInTime(CaoZuo,body);
                break;
            case R.id.img_more:
                if (Img_More_Flag){
                    layout_more.setVisibility(View.VISIBLE);
                    Img_More.setImageResource(R.drawable.arrow_left_in_main);
                    Img_More_Flag = false;
                    adapter_Card.notifyDataSetChanged();
                } else {
                    layout_more.setVisibility(View.GONE);
                    Img_More.setImageResource(R.drawable.arrow_right_in_main);
                    Img_More_Flag = true;
                    adapter_Card.notifyDataSetChanged();
                }
            default:
                break;
        }
    }
    @Override
    public void onTouchingLetterChanged(String s) {
        int position = 0;
        if (adapter_Card != null){
            position = adapter_Card.getPositionForSection(s.charAt(0));
        }
        if (position != -1){
            mListView.scrollToPosition(position);
        } else if (s.contains("#")) {
            mListView.scrollToPosition(0);
        }
    }
    //各类操作执行的方法
    private void TextInTime(String CaoZuo, List<CountMultiple> body) {
        if (CaoZuo.equals("温湿度检测")){
            for (CardAdapterBody data : CardDataAdapterList){
                if (data.getSelected()){
                    CountMultiple multiple = new CountMultiple();
                    multiple.setUrl(data.getUrl());
                    multiple.setCommandMapId(data.getCommandMapId());
                    String commandContent
                            = "AA55"//起始符
                            + "FF"  //分机地址(弃用)
                            + "03"  //控制符
                            + "FFFF"//备用
                            + "0000"//数据长度
                            + "FFFF"//CRC16
                            + "0D0A"//结束符
                            ;
                    multiple.setCommandContent(commandContent);
                    body.add(multiple);
                }
            }
            if (body.size()==1){
                NewDownRawMultipleBody.list list = new NewDownRawMultipleBody.list();
                list.setList(body);
                //温湿水检测目前用的方法和温湿度检测是一样的，后续如果单出温湿水检测就在这修改
                DetectionTestTempInTime(list, body.get(0));
            } else if (body.size()>1) {
                util.showToast(getContext(),"请只选择一个仓");
            } else {
                util.showToast(getContext(),"请至少选择一个仓");
            }
        } else if (CaoZuo.equals("温湿水检测")) {
            for (CardAdapterBody data : CardDataAdapterList){
                if (data.getSelected()){
                    CountMultiple multiple = new CountMultiple();
                    multiple.setUrl(data.getUrl());
                    multiple.setCommandMapId(data.getCommandMapId());
                    String commandContent
                            = "AA55"//起始符
                            + "FF"  //分机地址(弃用)
                            + "F2"  //控制符
                            + "FFFF"//备用
                            + "0000"//数据长度
                            + "FFFF"//CRC16
                            + "0D0A"//结束符
                            ;
                    multiple.setCommandContent(commandContent);
                    body.add(multiple);
                }
            }
            if (body.size()==1){
                NewDownRawMultipleBody.list list = new NewDownRawMultipleBody.list();
                list.setList(body);
                //温湿水检测目前用的方法和温湿度检测是一样的，后续如果单出温湿水检测就在这修改
                DetectionTestTempInTime(list, body.get(0));
            } else if (body.size()>1) {
                util.showToast(getContext(),"请只选择一个仓");
            } else {
                util.showToast(getContext(),"请至少选择一个仓");
            }
        } else if (CaoZuo.equals("空调控制")) {
            CountMultiple multiple = new CountMultiple();
            String Name = "";
            for (CardAdapterBody data : CardDataAdapterList){
                if (data.getSelected()){
                    multiple.setUrl(data.getUrl());
                    multiple.setCommandMapId(data.getCommandMapId());
                    body.add(multiple);
                    Name = data.getGranaryName();
                }
            }
            if (body.size()==1){
                DetectionTestAirConditionInTime(multiple,Name);
            } else if (body.size()<0) {
                util.showToast(getContext(),"请至少选择一个仓");
            } else if (body.size()>1){
                util.showToast(getContext(),"请只选择一个仓");
            }
        } else if (CaoZuo.equals("通风控制")) {
            CountMultiple multiple = new CountMultiple();
            String Name = "";
            for (CardAdapterBody data : CardDataAdapterList){
                if (data.getSelected()){
                    multiple.setUrl(data.getUrl());
                    multiple.setCommandMapId(data.getCommandMapId());
                    body.add(multiple);
                    Name = data.getGranaryName();
                }
            }
            if (body.size()==1){
                DetectionTestWindInTime(multiple,Name);
            } else if (body.size()<0) {
                util.showToast(getContext(),"请至少选择一个仓");
            } else if (body.size()>1){
                util.showToast(getContext(),"请只选择一个仓");
            }
        } else if (CaoZuo.equals("气体虫害检测")) {
            CountMultiple multiple = new CountMultiple();
            for (CardAdapterBody data : CardDataAdapterList){
                if (data.getSelected()){
                    multiple.setUrl(data.getUrl());
                    multiple.setCommandMapId(data.getCommandMapId());
                    body.add(multiple);
                }
            }
            if (body.size()==1){
                DetectionTestGasInsectInTime(multiple);
            } else if (body.size()<0) {
                util.showToast(getContext(),"请至少选择一个仓");
            } else if (body.size()>1){
                util.showToast(getContext(),"请只选择一个仓");
            }
        } else if (CaoZuo.equals("能耗检测")) {
            for (CardAdapterBody data : CardDataAdapterList){
                if (data.getSelected()){
                    CountMultiple multiple = new CountMultiple();
                    multiple.setUrl(data.getUrl());
                    multiple.setCommandMapId(data.getCommandMapId());
                    String commandContent = "";
                    multiple.setCommandContent(commandContent);
                    body.add(multiple);
                }
            }
            if (body.size()>0){
                NewDownRawMultipleBody.list list = new NewDownRawMultipleBody.list();
                list.setList(body);
                Log.d("jht", "onClick: 正在执行能耗检测");
                util.showToast(getContext(),"正在执行能耗检测");
            } else {
                util.showToast(getContext(),"请至少选择一个仓");
            }
        } else if (CaoZuo.equals("气象站检测")) {
            for (CardAdapterBody data : CardDataAdapterList){
                if (data.getSelected()){
                    CountMultiple multiple = new CountMultiple();
                    multiple.setUrl(data.getUrl());
                    multiple.setCommandMapId(data.getCommandMapId());
                    String commandContent = "";
                    multiple.setCommandContent(commandContent);
                    body.add(multiple);
                }
            }
            if (body.size()>0){
                NewDownRawMultipleBody.list list = new NewDownRawMultipleBody.list();
                list.setList(body);
                Log.d("jht", "onClick: 正在执行气象站检测");
                util.showToast(getContext(),"正在执行气象站检测");
            } else {
                util.showToast(getContext(),"请至少选择一个仓");
            }
        } else if (CaoZuo.equals("照明控制")) {
            Log.d("jht", "onClick: 照明控制");
            util.showToast(getContext(),"照明控制");
        }
    }
    private void DetectionTestWindInTime(CountMultiple multiple, String name) {
        Gson gson = new Gson();
        NewDownRawBody Body = new NewDownRawBody();
        Body.setBody(multiple);
        Body.setHeaders("");
        Body.setQuery("");
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("通风控制请求体",jsonData);
        bundle.putString("commandMapId",multiple.getCommandMapId());
        bundle.putString("url",multiple.getUrl());
        bundle.putString("granaryName",name);
        Intent intent = new Intent(getContext(), com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.WindActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
    private void DetectionTestAirConditionInTime(CountMultiple multiple, String name) {
        Gson gson = new Gson();
        NewDownRawBody Body = new NewDownRawBody();
        Body.setBody(multiple);
        Body.setHeaders("");
        Body.setQuery("");
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("空调控制请求体",jsonData);
        bundle.putString("commandMapId",multiple.getCommandMapId());
        bundle.putString("url",multiple.getUrl());
        bundle.putString("granaryName",name);
        Intent intent = new Intent(getContext(), com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.AirConditionActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
    private void DetectionTestGasInsectInTime(CountMultiple list) {
        Gson gson = new Gson();
        NewDownRawBody Body = new NewDownRawBody();
        Body.setBody(list);
        Body.setHeaders("");
        Body.setQuery("");
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("气体虫害检测请求体",jsonData);
        bundle.putString("commandMapId",list.getCommandMapId());
        bundle.putString("url",list.getUrl());
        Intent intent = new Intent(getContext(), com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.GasInsectActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
    private void DetectionTestTempInTime(NewDownRawMultipleBody.list list, CountMultiple countMultiple) {
        suoPingDialog = new SuoPingDialog(getContext(),"正在检测，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        Gson gson = new Gson();
        NewDownRawMultipleBody Body = new NewDownRawMultipleBody();
        Body.setBody(list);
        Body.setHeaders("");
        Body.setQuery("");
        Body.setMethod("POST");
        Body.setUrl("/measure/multiple");
        String jsonData = gson.toJson(Body);
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=30", jsonData, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                TestInTimeBean test = new TestInTimeBean();
                try {
                    test = gson.fromJson((String) result,TestInTimeBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util.showToast(getContext(),"数据解析出错，请联系工作人员。");
                    return;
                }
                if (test.getData()!=null && test.getData().getList()!=null && test.getData().getList().size()>0) {
                    for (TestInTimeBean.TestInTimeCount NewData : test.getData().getList()) {
                        for (CardAdapterBody OldData : CardDataAdapterList) {
                            if (NewData.getData().getData()!=null && NewData.getCommandMapId().equals(OldData.getCommandMapId())) {
                                OldData.setHumidityInner(NewData.getData().getData().getHumidityInner());
                                OldData.setHumidityOut(NewData.getData().getData().getHumidityOut());
                                OldData.setTempInner(NewData.getData().getData().getTempInner());
                                OldData.setTempOut(NewData.getData().getData().getTempOut());
                                OldData.setTempList(NewData.getData().getData().getTempList());
                                OldData.setSelected(false);
                            } else {
                                suoPingDialog.dismiss();
                                util.showToast(getContext(),NewData.getErrMsg());
                            }
                        }
                    }
                    suoPingDialog.dismiss();
                    adapter_Card.notifyDataSetChanged();
                    util.showToast(getContext(),"检测完成");
                    Bundle bundle2 = new Bundle();
                    bundle2.putParcelable("数据",test.getData().getList().get(0).getData().getData());
//                    Log.d("zyq", "woaini onReqSuccess: "+test.getData().getList().get(0).getData().getData().getTempList().size());
                    Intent intent = new Intent(getContext(), DataActivity.class);
                    intent.putExtra("bundle2",bundle2);
                    startActivity(intent);
                } else {
                    suoPingDialog.dismiss();
                    util.showToast(getContext(),"检测完成！！！");
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                util.showToast(getContext(),errorMsg);
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
