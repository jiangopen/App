package com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;

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
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.HistoryGasInsertDataActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.HistoryMouDataActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.HistorySmartWindDataActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.HistoryTemDataActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.HistoryDataAdapter;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.HistoryCountMultipleBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CardAdapterBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.NewDownRawMultipleBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
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

public class HistoryFragment extends Fragment implements View.OnClickListener, TextWatcher, SideBar.OnTouchingLetterChangedListener{
    CustomEditText Search_Input;
    ImageView MoreList,Img_More;
    TextView mFootView;
    TextView Text_Tem,Text_Gas,Text_TongFeng,Text_Text,Text_Test;
    TextView selectedTextView = null;
    SmartRefreshLayout refreshLayout;
    LinearLayout WuShebei,infos,layout_more;
    RecyclerView mListView;
    Button btn1,btn2,btn3;
    Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    //显示或收起功能列表判断
    private boolean Img_More_Flag = true;
    private int positione = -1;
    private Util1 util = new Util1();
    private String deviceType;
    private View ve;
    private CardAdapterBody iteme;
    //    private CardDataAdapter adapter_Card;
    private HistoryDataAdapter adapter_Card;
    private HeaderViewAdapter headerViewAdapter;
    private ArrayList<CardAdapterBody> CardDataAdapterList = new ArrayList<>();
    private ArrayList<GranaryListBean.Data> granarylist = new ArrayList<>();
    private ArrayList<CardAdapterBody> mSelectitems = new ArrayList<>();
    private SuoPingDialog suoPingDialog;
    private WonderUtil mWonderUtil = new WonderUtil();
    String HistoryCountMultiple = "";
    private HistoryDataAdapter.OnItemClickListener MyItemClickListener = new HistoryDataAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, CardAdapterBody item, int position) {
            if (positione == position){
                ve = null;
                iteme = null;
                positione = -1;
            } else {
                ve = v;
                iteme = item;
                positione = position;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_history, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        //加载组件
        initView(view);
        if (bundle.getParcelableArrayList("粮仓")!=null){
            CardDataAdapterList.clear();
            granarylist = bundle.getParcelableArrayList("粮仓");
            deviceType = bundle.getString("账号");
            Log.d("jht", "granarylist: "+granarylist.size());
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
            Gson gson = new Gson();
            HistoryCountMultiple = gson.toJson(historyCountMultipleBody);
            initData(HistoryCountMultiple, new DetectionFragment.OnInitDataFinishedListener() {
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

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData(HistoryCountMultiple, new DetectionFragment.OnInitDataFinishedListener() {
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
    public void initData(String jsonData,final DetectionFragment.OnInitDataFinishedListener listener){
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsonData, new OkHttpUtilTWO.ReqCallBack() {
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
        Text_TongFeng = view.findViewById(R.id.txt_tongfeng);
        //功能显示组件
        Text_Text = view.findViewById(R.id.txt_text);
//        //全选全清功能键
//        Text_Select = view.findViewById(R.id.txt_all_select);
//        Text_Clean = view.findViewById(R.id.txt_all_clean);
        //检测功能键
        Text_Test = view.findViewById(R.id.txt_now_test);
        //设置首选为温湿度检测
        Text_Tem.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
        selectedTextView = Text_Tem;
        //对这些组件设置监听事件
        Text_Tem.setOnClickListener(this);
//        Text_Hum.setOnClickListener(this);
        Text_Gas.setOnClickListener(this);
        Text_TongFeng.setOnClickListener(this);
//        Text_Select.setOnClickListener(this);
//        Text_Clean.setOnClickListener(this);
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
    }
    private void refreshView(Context context, ArrayList<CardAdapterBody> List) {
        View vfoot = LayoutInflater.from(context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        mListView.setLayoutManager(layoutManager);
        int[] layoutIds = new int[]{R.layout.adapter_card_history};
        adapter_Card = new HistoryDataAdapter(List,layoutIds,context);
        headerViewAdapter = new HeaderViewAdapter(adapter_Card);
        headerViewAdapter.addFooterView(vfoot);
        mListView.setAdapter(headerViewAdapter);
        adapter_Card.setmOnItemClickListener(MyItemClickListener);
        mFootView.setText("共"+adapter_Card.getFreshDates().size()+"台设备");
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

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
                Text_Text.setText("温湿度");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
//            case R.id.txt_hum:
//                if (selectedTextView != null){
//                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
//                }
//                Text_Hum.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
//                selectedTextView = Text_Hum;
//                Text_Text.setText("温湿水");
//                refreshLayout.setVisibility(View.VISIBLE);
//                WuShebei.setVisibility(View.GONE);
//                break;
            case R.id.txt_gas:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_Gas.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_Gas;
                Text_Text.setText("气体虫害");
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
    private void TextInTime(String Caozuo, List<CountMultiple> body){
        if (Caozuo.equals("温湿度")) {
            if (iteme!=null){
                CountMultiple multiple = new CountMultiple();
                multiple.setUrl(iteme.getUrl());
                multiple.setCommandMapId(iteme.getCommandMapId());
                multiple.setDataType("04");
                multiple.setCount(10);
                String Name = iteme.getGranaryName();
                HistoryTempInTime(multiple, Name, "1");
            }
        } else if (Caozuo.equals("温湿水")) {
            util.showToast(getContext(),"无有效记录！");
        } else if (Caozuo.equals("气体虫害")) {
            if (iteme!=null){
                CountMultiple multiple = new CountMultiple();

                multiple.setUrl(iteme.getUrl());
                multiple.setCommandMapId(iteme.getCommandMapId());
                multiple.setDataType("11");
                multiple.setCount(100);
                String Name = iteme.getGranaryName();
                HistoryTempInTime(multiple, Name, "3");
            }
        } else if (Caozuo.equals("智能通风")) {
            util.showToast(getContext(),"无有效记录！");
        }
    }

    private void HistoryTempInTime(CountMultiple multiple, String name, String fragment) {
        NewDownRawBodyTWO body = new NewDownRawBodyTWO();
        body.setBody(multiple);
        body.setHeaders(null);
        body.setMethod("POST");
        body.setQuery(null);
        body.setUrl("/history-data/count");
        String jsonData = gson.toJson(body);
        Bundle bundle1 = new Bundle();
        bundle1.putParcelableArrayList("粮仓",granarylist);
        Intent intent;
        switch (fragment) {
            case "1":
                intent = new Intent(getContext(), HistoryTemDataActivity.class);
                break;
            case "2":
                intent = new Intent(getContext(), HistoryMouDataActivity.class);
                break;
            case "3":
                intent = new Intent(getContext(), HistoryGasInsertDataActivity.class);
                break;
            case "4":
                intent = new Intent(getContext(), HistorySmartWindDataActivity.class);
                break;
            default:
                return; // 或者处理错误情况
        }
        intent.putExtra("bundle1", bundle1);
        intent.putExtra("仓号", name);
        intent.putExtra("账号", deviceType);
        intent.putExtra("token", Token);
        intent.putExtra("温度历史数据请求体", jsonData);
        startActivity(intent);
    }

    @Override
    public void onTouchingLetterChanged(String s) {

    }
}