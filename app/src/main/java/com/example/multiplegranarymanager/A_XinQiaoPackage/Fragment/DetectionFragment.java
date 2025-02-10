package com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.AirConditionActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.DataActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.EnergyActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.GasInsectActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.HuoWeiCardActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.LightActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.QiTiaoActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.ShuLiangActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.SmartLockActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.WindActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.NewCardDataAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.HistoryCountMultipleBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ListBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.TestInTimeThreeBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.TestInTimeTwoBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CardAdapterBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.NewDownRawMultipleBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.NewDownRawMultipleThreeBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.NewDownRawMultipleTwoBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.TemGranaryBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.CustomEditText;

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
    TextView Text_Tem,Text_Gas,Text_KongTiao,Text_TongFeng,Text_NengHao,Text_TianQi,Text_ZhaoMing,Text_Text,Text_Select,Text_Clean,Text_Test,Text_QiTiao,Text_ShuLiang;
    TextView selectedTextView = null;
    SmartRefreshLayout refreshLayout;
    LinearLayout WuShebei,infos,layout_more;
    RecyclerView mListView;
    Button btn1,btn2,btn3;
    Bundle bundle = new Bundle();
    //显示或收起功能列表判断
    private boolean Img_More_Flag = false;
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
        View view = inflater.inflate(R.layout.fragment_detection, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        //加载组件
        initView(view);
        if (bundle.getParcelableArrayList("粮仓")!=null){
            CardDataAdapterList.clear();
            granarylist = bundle.getParcelableArrayList("粮仓");
            deviceType = bundle.getString("账号");
            Log.d("jht", "granarylist: "+granarylist.size());
            Log.d("jht", "deviceType: "+deviceType);
            NewDownRawMultipleBody historyCountMultipleBody = new NewDownRawMultipleBody();
            historyCountMultipleBody.setUrl("/history-data/count/multiple");
            historyCountMultipleBody.setMethod("POST");
            historyCountMultipleBody.setHeaders(null);
            historyCountMultipleBody.setQuery(null);
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
            suoPingDialog = new SuoPingDialog(getContext(),"正在操作，请稍等......");
            suoPingDialog.setCancelable(true);
            suoPingDialog.show();
            initData(HistoryCountMultiple, new OnInitDataFinishedListener() {
                @Override
                public void OnInitDataListener(boolean success) {
//                    if (success){
//                        refreshView(getContext(),CardDataAdapterList);
//                    } else {
//                        Log.d("jht", "Oh, I get it: ");
//                    }
                    suoPingDialog.dismiss();
                    refreshView(getContext(),CardDataAdapterList);
                }
            });
        } else {
            WuShebei.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        }

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                suoPingDialog = new SuoPingDialog(getContext(),"正在操作，请稍等......");
                suoPingDialog.setCancelable(true);
                suoPingDialog.show();
                initData(HistoryCountMultiple, new OnInitDataFinishedListener() {
                    @Override
                    public void OnInitDataListener(boolean success) {
//                        if (success){
//
//                        }
                        suoPingDialog.dismiss();
                        refreshView(getContext(),CardDataAdapterList);
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
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsonData, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                Gson gson = new Gson();
                HistoryCountMultipleBean historyCountMultipleBean = new HistoryCountMultipleBean();
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
                            if (history.getUrl().equals(card.getUrl())){
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
        Text_QiTiao = view.findViewById(R.id.txt_qitiao);
        Text_ShuLiang = view.findViewById(R.id.txt_shuliang);
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
        Text_QiTiao.setOnClickListener(this);
        Text_ShuLiang.setOnClickListener(this);
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
                Text_Test.setText("检测");
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
//                Text_Text.setText("电子货位卡");
//                refreshLayout.setVisibility(View.VISIBLE);
//                WuShebei.setVisibility(View.GONE);
//                break;
            case R.id.txt_gas:
                Text_Test.setText("详情");
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
                Text_Test.setText("详情");
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
                Text_Test.setText("详情");
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
                Text_Test.setText("详情");
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_NengHao.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_NengHao;
                Text_Text.setText("能耗检测");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_tianqi:
                Text_Test.setText("详情");
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_TianQi.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_TianQi;
                Text_Text.setText("进出仓管理");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_zhaoming:
                Text_Test.setText("详情");
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_ZhaoMing.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_ZhaoMing;
                Text_Text.setText("照明控制");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_qitiao:
                Text_Test.setText("详情");
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_QiTiao.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_QiTiao;
                Text_Text.setText("气调控制");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_shuliang:
                Text_Test.setText("详情");
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_ShuLiang.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_ShuLiang;
                Text_Text.setText("数量检测");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
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
                    if (adapter_Card!=null) {
                        adapter_Card.notifyDataSetChanged();
                    }
                } else {
                    layout_more.setVisibility(View.GONE);
                    Img_More.setImageResource(R.drawable.arrow_right_in_main);
                    Img_More_Flag = true;
                    if (adapter_Card!=null) {
                        adapter_Card.notifyDataSetChanged();
                    }
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
                NewDownRawMultipleTwoBody.list list = new NewDownRawMultipleTwoBody.list();
                list.setCommandContent(body.get(0).getCommandContent());
                list.setUrl(body.get(0).getUrl());
                list.setCommandMapId(body.get(0).getCommandMapId());
                //温湿水检测目前用的方法和温湿度检测是一样的，后续如果单出温湿水检测就在这修改
                DetectionTestTempInTime(list, body.get(0));
            } else if (body.size()>1) {
                List<NewDownRawMultipleThreeBody.list> RAM = new ArrayList<>();
                for (CountMultiple data : body) {
                    NewDownRawMultipleThreeBody.list list = new NewDownRawMultipleThreeBody.list();
                    list.setCommandContent(data.getCommandContent());
                    list.setUrl(data.getUrl());
                    list.setCommandMapId(data.getCommandMapId());
                    RAM.add(list);
                }
                if (RAM != null && RAM.size()>0) {
                    DetectionTestTempInTime(RAM);
                }
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
//                NewDownRawMultipleTwoBody.list list = new NewDownRawMultipleTwoBody.list();
//                list.setCommandContent(body.get(0).getCommandContent());
//                list.setUrl(body.get(0).getUrl());
//                list.setCommandMapId(body.get(0).getCommandMapId());
                util.showToast(getContext(),"未添加配置");
                //温湿水检测目前用的方法和温湿度检测是一样的，后续如果单出温湿水检测就在这修改
//                DetectionTestTempInTime(list, body.get(0));
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
                DetectionTestEnergyInTime(multiple,Name);
            } else if (body.size()<0) {
                util.showToast(getContext(),"请至少选择一个仓");
            } else if (body.size()>1){
                util.showToast(getContext(),"请只选择一个仓");
            }
        } else if (CaoZuo.equals("进出仓管理")) {
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
                DetectionTestLockInTime(multiple,Name);
            } else if (body.size()<0) {
                util.showToast(getContext(),"请至少选择一个仓");
            } else if (body.size()>1){
                util.showToast(getContext(),"请只选择一个仓");
            }
        } else if (CaoZuo.equals("照明控制")) {
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
                DetectionTestLightInTime(multiple,Name);
            } else if (body.size()<0) {
                util.showToast(getContext(),"请至少选择一个仓");
            } else if (body.size()>1){
                util.showToast(getContext(),"请只选择一个仓");
            }
        } else if (CaoZuo.equals("气调控制")) {
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
                DetectionTestQiTiaoInTime(multiple,Name);
            } else if (body.size()<0) {
                util.showToast(getContext(),"请至少选择一个仓");
            } else if (body.size()>1){
                util.showToast(getContext(),"请只选择一个仓");
            }
        } else if (CaoZuo.equals("数量检测")) {
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
                DetectionTestShuLiangInTime(multiple,Name);
            } else if (body.size()<0) {
                util.showToast(getContext(),"请至少选择一个仓");
            } else if (body.size()>1){
                util.showToast(getContext(),"请只选择一个仓");
            }
        } else if (CaoZuo.equals("电子货位卡")) {
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
                DetectionTestHuoWeiCardInTime(multiple,Name);
            } else if (body.size()<0) {
                util.showToast(getContext(),"请至少选择一个仓");
            } else if (body.size()>1){
                util.showToast(getContext(),"请只选择一个仓");
            }
        }
    }

    private void DetectionTestTempInTime(List<NewDownRawMultipleThreeBody.list> ram) {
        suoPingDialog = new SuoPingDialog(getContext(),"正在检测，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        Gson gson = new Gson();
        NewDownRawMultipleThreeBody.ListBody rom = new NewDownRawMultipleThreeBody.ListBody();
        rom.setList(ram);
        NewDownRawMultipleThreeBody Body = new NewDownRawMultipleThreeBody();
        Body.setBody(rom);
        Body.setHeaders(null);
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setUrl("/measure/multiple");
        String jsonData = gson.toJson(Body);
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=50", jsonData, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                Log.d("jht", "onReqSuccess: " + result.toString());
                TestInTimeThreeBean test = new TestInTimeThreeBean();
                try {
                    test = gson.fromJson((String) result,TestInTimeThreeBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util.showToast(getContext(),"数据解析出错，请联系工作人员。");
                    return;
                }
                if (test.getData()!=null) {
                    suoPingDialog.dismiss();
                    adapter_Card.notifyDataSetChanged();
                    util.showToast(getContext(),"检测完成");
                    List<HistoryCountMultipleBean.DataContent> rom = new ArrayList<>();
                    for (ListBody data : test.getData().getList()) {
                        rom.add(data.getData().getData());
                        Log.d("jht", "onReqSuccess: "+ data.getData().getData().getGranarySetting().getType());
                    }
                    Log.d("jht", "onReqSuccess: "+ rom.size());
                    Bundle bundle2 = new Bundle();
                    bundle2.putParcelableArrayList("数据", (ArrayList<? extends Parcelable>) rom);
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

    private void DetectionTestHuoWeiCardInTime(CountMultiple multiple, String name) {
        Gson gson = new Gson();
        NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
        Body.setBody(multiple);
        Body.setHeaders(null);
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("电子货位卡请求体",jsonData);
        bundle.putString("commandMapId",multiple.getCommandMapId());
        bundle.putString("url",multiple.getUrl());
        bundle.putString("granaryName",name);
        bundle.putParcelableArrayList("粮仓",granarylist);
        Intent intent = new Intent(getContext(), HuoWeiCardActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    private void DetectionTestShuLiangInTime(CountMultiple multiple, String name) {
        Gson gson = new Gson();
        NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
        Body.setBody(multiple);
        Body.setHeaders(null);
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("数量请求体",jsonData);
        bundle.putString("commandMapId",multiple.getCommandMapId());
        bundle.putString("url",multiple.getUrl());
        bundle.putString("granaryName",name);
        bundle.putParcelableArrayList("粮仓",granarylist);
        Intent intent = new Intent(getContext(), ShuLiangActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    private void DetectionTestLockInTime(CountMultiple multiple, String name) {
        Gson gson = new Gson();
        NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
        Body.setBody(multiple);
        Body.setHeaders(null);
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("门锁请求体",jsonData);
        bundle.putString("commandMapId",multiple.getCommandMapId());
        bundle.putString("url",multiple.getUrl());
        bundle.putString("granaryName",name);
        bundle.putParcelableArrayList("粮仓",granarylist);
        Intent intent = new Intent(getContext(), SmartLockActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    private void DetectionTestLightInTime(CountMultiple multiple, String name) {
        Gson gson = new Gson();
        NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
        Body.setBody(multiple);
        Body.setHeaders(null);
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("照明请求体",jsonData);
        bundle.putString("commandMapId",multiple.getCommandMapId());
        bundle.putString("url",multiple.getUrl());
        bundle.putString("granaryName",name);
        bundle.putParcelableArrayList("粮仓",granarylist);
        Intent intent = new Intent(getContext(), LightActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    private void DetectionTestQiTiaoInTime(CountMultiple multiple, String name) {
        Gson gson = new Gson();
        NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
        Body.setBody(multiple);
        Body.setHeaders(null);
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("气调请求体",jsonData);
        bundle.putString("commandMapId",multiple.getCommandMapId());
        bundle.putString("url",multiple.getUrl());
        bundle.putString("granaryName",name);
        bundle.putParcelableArrayList("粮仓",granarylist);
        Intent intent = new Intent(getContext(), QiTiaoActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    private void DetectionTestEnergyInTime(CountMultiple multiple, String name) {
        Gson gson = new Gson();
        NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
        Body.setBody(multiple);
        Body.setHeaders(null);
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("能耗请求体",jsonData);
        bundle.putString("commandMapId",multiple.getCommandMapId());
        bundle.putString("url",multiple.getUrl());
        bundle.putString("granaryName",name);
        bundle.putParcelableArrayList("粮仓",granarylist);
        Intent intent = new Intent(getContext(), EnergyActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    private void DetectionTestWindInTime(CountMultiple multiple, String name) {
        Gson gson = new Gson();
        NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
        Body.setBody(multiple);
        Body.setHeaders(null);
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("通风控制请求体",jsonData);
        bundle.putString("commandMapId",multiple.getCommandMapId());
        bundle.putString("url",multiple.getUrl());
        bundle.putString("granaryName",name);
        Intent intent = new Intent(getContext(), WindActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
    private void DetectionTestAirConditionInTime(CountMultiple multiple, String name) {
        Gson gson = new Gson();
        NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
        Body.setBody(multiple);
        Body.setHeaders(null);
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("空调控制请求体",jsonData);
        bundle.putString("commandMapId",multiple.getCommandMapId());
        bundle.putString("url",multiple.getUrl());
        bundle.putString("granaryName",name);
        Intent intent = new Intent(getContext(), AirConditionActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
    private void DetectionTestGasInsectInTime(CountMultiple list) {
        Gson gson = new Gson();
        NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
        Body.setBody(list);
        Body.setHeaders(null);
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setUrl("/command-map/get");
        String jsonData = gson.toJson(Body);
        Bundle bundle = new Bundle();
        bundle.putString("气体虫害检测请求体",jsonData);
        bundle.putString("devicetype",deviceType);
        bundle.putString("commandMapId",list.getCommandMapId());
        bundle.putString("url",list.getUrl());
        Intent intent = new Intent(getContext(), GasInsectActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
    private void DetectionTestTempInTime(NewDownRawMultipleTwoBody.list list, CountMultiple countMultiple) {
        suoPingDialog = new SuoPingDialog(getContext(),"正在检测，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        Gson gson = new Gson();
        NewDownRawMultipleTwoBody Body = new NewDownRawMultipleTwoBody();
        Body.setBody(list);
        Body.setHeaders(null);
        Body.setQuery(null);
        Body.setMethod("POST");
        Body.setUrl("/measure");
        String jsonData = gson.toJson(Body);
        Log.d("zyq", "DetectionTestTempInTime: "+jsonData);
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsonData, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                TestInTimeTwoBean test = new TestInTimeTwoBean();
                try {
                    test = gson.fromJson((String) result,TestInTimeTwoBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util.showToast(getContext(),"数据解析出错，请联系工作人员。");
                    return;
                }
                if (test.getData()!=null) {
                    suoPingDialog.dismiss();
                    adapter_Card.notifyDataSetChanged();
                    util.showToast(getContext(),"检测完成");
                    Bundle bundle2 = new Bundle();
                    List<HistoryCountMultipleBean.DataContent> list = new ArrayList<>();
                    list.add(test.getData());
                    bundle2.putParcelableArrayList("数据", (ArrayList<? extends Parcelable>) list);
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
