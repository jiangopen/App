package com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.History;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.HistoryTemDataActivity.Date2TimeStamp;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.TableData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.InspurChannelNumberMap;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.GasInsectBean.GasInsectList;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.History.HistoryBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.HistoryCountMultipleBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.ChannelNumberMapBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.GasList;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.TimeMultipe;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.SelfDialog;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.SmartTableScroll;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryGasFragment extends Fragment implements View.OnClickListener{
    private ImageView IMG_select;
    private TextView TXT_name,TXT_count,TXT_time;
    private LinearLayout fatherView;
    private SmartTableScroll tableLayout;
    private ScrollView scrollView;
    private SelfDialog selfDialog;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();

    public SuoPingDialog suoPingDialog;
    private String Name;
    private GranaryListBean.Data Granary;
    private String XuanTongQi;
    private int selectWeatherId = 0;
    private ArrayList<HistoryCountMultipleBean.DataContent> DataContent;
    private ArrayList<GasList> GasList = new ArrayList<>();
    private ArrayList<InspurChannelNumberMap> ChannelNumMap = new ArrayList<>();
    private ArrayList<ChannelNumberMapBody> selectList = new ArrayList<>();
    private ArrayList<GasInsectList> GasInsectList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_gas_insect, container, false);
        bundle = getArguments();
        DataContent = bundle.getParcelableArrayList("DataContent");
        ChannelNumMap = bundle.getParcelableArrayList("ChannelNumberMap");
        Granary = bundle.getParcelable("Granary");
        for (int i=1;i<=ChannelNumMap.size();i++){
            ChannelNumberMapBody data = new ChannelNumberMapBody("选通器"+i,ChannelNumMap.get(i-1).getInspurChannelNumber());
            selectList.add(data);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        IMG_select = view.findViewById(R.id.select);
        TXT_name = view.findViewById(R.id.name);
        TXT_count = view.findViewById(R.id.count);
        TXT_time = view.findViewById(R.id.time);
        fatherView = view.findViewById(R.id.fartherView);
        tableLayout = view.findViewById(R.id.table_layout);
        scrollView = view.findViewById(R.id.view_scroll);
        TXT_name.setText("选通器1");
        fatherView.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        TXT_count.setOnClickListener(this);
        IMG_select.setOnClickListener(this);
        TXT_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.count:

                CountMultiple multiple = new CountMultiple();
                multiple.setDataType("11");
                multiple.setUrl(Granary.getUrl());
                multiple.setCommandMapId(Granary.getCommandMapId());
                multiple.setCount(100);
                NewDownRawBodyTWO body = new NewDownRawBodyTWO();
                body.setBody(multiple);
                body.setHeaders(null);
                body.setQuery(null);
                body.setMethod("POST");
                body.setUrl("/history-data/count");
                String jsonData = gson.toJson(body);
                for (ChannelNumberMapBody data : selectList) {
                    if (data.getName().equals(TXT_name.getText().toString())){
                        XuanTongQi = data.getInspurChannelNumber();
                        ShowDialogCount(jsonData,TXT_name.getText().toString());
                    }
                }
//                ShowDialogCount2(TXT_name.getText().toString(), new OnInitShowDialogCountFinishedListener() {
//                    @Override
//                    public void OninitShowDialogCountListener(boolean success) {
//
//                    }
//                });
                break;
            case R.id.select:
                List<String> name = new ArrayList<>();
                List<String> id = new ArrayList<>();
                for (ChannelNumberMapBody data : selectList) {
                    name.add(data.getName());
                    id.add(data.getInspurChannelNumber());
                }
                ShowAlertDialog(name,id);
                break;
            case R.id.time:
                selfDialog = new SelfDialog(getContext());
                selfDialog.setYesOnclickListener("提交", new SelfDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        selfDialog.dismiss();
                        //提交日期操作从日历获得
                        long longstart = Date2TimeStamp(selfDialog.getStartTime(),"yyyy-MM-dd HH:mm:ss");
                        long longend = Date2TimeStamp(selfDialog.getEndTime(),"yyyy-MM-dd HH:mm:ss");
                        //设置今天最晚的时间，startTime最小不得大过这个时间
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY,23);
                        calendar.set(Calendar.MINUTE,59);
                        calendar.set(Calendar.SECOND,59);
                        //获取今天最晚时间的毫秒数
                        long todayEndTimeInMillis = calendar.getTimeInMillis();
                        if (longstart>=todayEndTimeInMillis){
                            util.showToast(getContext(),"该时间段内并无数据！");
                        } else {
                            //通过时间查询
//                            for (GranaryListBean.Data data : Granarylist){
//                                if (data.getGranaryName().equals(Name)){
//                                    selectProduct = data;
//                                    ShowDialog(longstart,longend,selectProduct);
//                                }
//                            }
                            for (ChannelNumberMapBody data : selectList) {
                                if (data.getName().equals(TXT_name.getText().toString())){
                                    XuanTongQi = data.getInspurChannelNumber();
                                    ShowDialogTime(longstart,longend);
                                }
                            }
                        }
                    }
                });
                selfDialog.show();
                break;
            default:
                break;
        }
    }

//    public interface OnInitShowDialogCountFinishedListener {
//        void OninitShowDialogCountListener (boolean success);
//    }
//    private void ShowDialogCount2(String sName, final OnInitShowDialogCountFinishedListener listener) {
//        GasInsectList.clear();
//        for (HistoryCountMultipleBean.DataContent data : DataContent){
//            if (data.getGasInsectList()!=null && data.getGasInsectList().size()>0){
//                for (GasInsectList datatwo : data.getGasInsectList()){
//                    if (datatwo.getInspurChannelNumber().equals(XuanTongQi)){
//                        datatwo.setDate(data.get);
//                    }
//                }
//            }
//        }
//    }

    private void ShowDialogCount(String jsondata, String sName) {
        TimeData(jsondata, new OnTimeDataFinishedListener() {
            @Override
            public void OnTimeDataListener(ArrayList<GasInsectList> success) {
                suoPingDialog.dismiss();
                if (success!=null && success.size()>0) {
                    if (success.size()>10) {
                        success = new ArrayList<>(success.stream().limit(10).collect(Collectors.toList()));
                    }
                    ShowTable(success);
                } else {
//                    Log.d("zyq", "OnTimeDataListener: "+success.size());
                    util.showToast(getContext(),"该时间段内无记录！");
                    return;
                }
            }
        });
    }

    private void ShowAlertDialog(List<String> name, List<String> id) {
        ArrayAdapter adapter2 = new ArrayAdapter(getContext(),R.layout.simple_list,name);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setSingleChoiceItems(adapter2, selectWeatherId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String NAME = name.get(which);
                TXT_name.setText(NAME);
                selectWeatherId = which;
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        fatherView.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
    }

    private void ShowDialogTime(long longstart, long longend) {
        TimeMultipe multipe = new TimeMultipe();
        multipe.setDataType("11");
        multipe.setUrl(Granary.getUrl());
        multipe.setCommandMapId(Granary.getCommandMapId());
        multipe.setEndTime(longend);
        multipe.setStartTime(longstart);
        NewDownRawBodyTWO body = new NewDownRawBodyTWO();
        body.setBody(multipe);
        body.setHeaders(null);
        body.setQuery(null);
        body.setMethod("POST");
        body.setUrl("/history-data/time");
        String jsonData = gson.toJson(body);
        TimeData(jsonData, new OnTimeDataFinishedListener() {
            @Override
            public void OnTimeDataListener(ArrayList<GasInsectList> resultData) {
                suoPingDialog.dismiss();
                if (resultData!=null && resultData.size()>0) {
                    ShowTable(resultData);
                } else {
                    util.showToast(getContext(),"该时间段内无记录！");
                    return;
                }
            }
        });
    }

    private void ShowTable(ArrayList<GasInsectList> gasInsectList) {
        Log.d("zyq", "ShowTable:              "+gasInsectList.size());
        scrollView.setVisibility(View.VISIBLE);
        fatherView.setVisibility(View.GONE);
        GasList.clear();
        //创建表格
        Column<String> name = new Column<>("选通器号","name");
        Column<String> time = new Column<>("时间","time");
        Column<Double> o2 = new Column<>("氧气/%","o2");
        Column<Double> n2 = new Column<>("氮气/%","n2");
        Column<Double> ph3 = new Column<>("磷化氢/ppm","ph3");
        for (GasInsectList data : gasInsectList){
            String Name = TXT_name.getText().toString();
            String Time = data.getDate();
            Double O2 = 0.0;
            Double N2 = 0.0;
            Double PH3 = 0.0;
            if (data.getO2()!=null && data.getO2()!=-1 && data.getO2()!=255){
                O2 = data.getO2();
            } else {
                O2 = -1.0;
            }
            if (data.getN2()!=null && data.getN2()!=-1 && data.getN2()!=255){
                N2 = data.getN2();
            } else {
                N2 = -1.0;
            }
            if (data.getPh3()!=null && data.getPh3()!=-1 && data.getPh3()!=255){
                PH3 = data.getPh3();
            } else {
                PH3 = -1.0;
            }
            GasList.add(new GasList(
                    Name,
                    Time,
                    O2,
                    N2,
                    PH3
            ));
        }
        //表格数据填充
        TableData<GasList> tableData = new TableData<>("气体浓度记录表",GasList,name,time,o2,n2,ph3);
        tableLayout.setTableData(tableData);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        //设置最小宽度
        tableLayout.getConfig().setMinTableWidth(screenWidth);
        //设置是否显示顶部序号列，默认显示ABCD...
        tableLayout.getConfig().setShowXSequence(false);
        //设置是否显示左侧序号列，就是行号
        tableLayout.getConfig().setShowYSequence(false);
        //固定列标题
        tableLayout.getConfig().setFixedTitle(false);
        //是否放缩
        tableLayout.setZoom(true, 1, 0.8f);
        //列标题字边距
        tableLayout.getConfig().setColumnTitleVerticalPadding(2);
        tableLayout.getConfig().setColumnTitleHorizontalPadding(2);
        //单元格字边距
        tableLayout.getConfig().setHorizontalPadding(2);
        //设置字体样式

        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextSpSize(getContext(), 20);
        fontStyle.setTextColor(R.color.primary_dark);
        tableLayout.getConfig().setTableTitleStyle(fontStyle);
        FontStyle titleFontStyle = new FontStyle();
        //列名字号
        titleFontStyle.setTextSpSize(getContext(), 20);
        titleFontStyle.setTextColor(R.color.black);
        FontStyle contentFontStyle = new FontStyle();
        contentFontStyle.setTextSpSize(getContext(), 10);

        tableLayout.getConfig().setColumnTitleStyle(titleFontStyle);
        tableLayout.getConfig().setContentStyle(contentFontStyle);
        LineStyle lineStyle = new LineStyle(2, R.color.sixth_top_qd);
        //设置网格线样式
        tableLayout.getConfig().setColumnTitleGridStyle(lineStyle);
        tableLayout.getConfig().setContentGridStyle(new LineStyle(2, R.color.sixth_top_qd));
        tableLayout.getConfig().setSequenceGridStyle(new LineStyle(2, R.color.sixth_top_qd));
        //设置隔行显示不同颜色
        ICellBackgroundFormat<CellInfo> backgroundFormat = new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if (cellInfo.row % 2 == 0) {
                    return ContextCompat.getColor(getContext(), R.color.sixth_low_xq);
                }
                return TableConfig.INVALID_COLOR;
            }
        };
        //设置每行 标题字体和背景
        ICellBackgroundFormat<Integer> backgroundFormat2 = new BaseCellBackgroundFormat<Integer>() {
            @Override
            public int getBackGroundColor(Integer position) {
                if (position % 2 == 0) {
                    return ContextCompat.getColor(getContext(), R.color.sixth_low_xq);
                }
                return TableConfig.INVALID_COLOR;

            }

            @Override
            public int getTextColor(Integer position) {
                if (position % 2 == 0) {
                    return ContextCompat.getColor(getContext(), R.color.sixth_top_qd);
                }
                return TableConfig.INVALID_COLOR;
            }
        };
        tableLayout.getConfig().setContentCellBackgroundFormat(backgroundFormat).setYSequenceCellBgFormat(backgroundFormat2);
        tableLayout.getConfig().setContentStyle(new FontStyle(50,R.color.sixth_top_qd));
    }

    public interface OnTimeDataFinishedListener {
        void OnTimeDataListener(ArrayList<GasInsectList> success);
    }
    private void TimeData(String jsondata, final OnTimeDataFinishedListener listener) {
        suoPingDialog = new SuoPingDialog(getContext(),"加载中,请稍等......");
        suoPingDialog.show();
        GasInsectList.clear();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                HistoryBean historyBean = new HistoryBean();
                try {
                    historyBean = gson.fromJson((String) result,HistoryBean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    util.showToast(getContext(),"历史解析出现问题");
                }
                Log.d("zyq", "onReqSuccess: 1");
                if (historyBean.getData() != null && historyBean.getData().size()>0) {
                    Log.d("zyq", "onReqSuccess: 2");
                    for (HistoryBean.Data data : historyBean.getData()){
                        Log.d("zyq", "onReqSuccess: 3");
                        if (data.getDataContent() != null && data.getDataContent().getGasInsectList()!=null && data.getDataContent().getGasInsectList().size()>0) {
                            Log.d("zyq", "onReqSuccess: 4");
                            for (GasInsectList list : data.getDataContent().getGasInsectList()) {
                                Log.d("zyq", "onReqSuccess: 5"+XuanTongQi+"       "+list.getInspurChannelNumber());
                                if (list.getInspurChannelNumber().equals(XuanTongQi)){
                                    Log.d("zyq", "onReqSuccess: 6");
                                    list.setDate(data.getDate());
                                    GasInsectList.add(list);
                                }
                            }
                        } else {
                            listener.OnTimeDataListener(null);
                        }
                    }
                    listener.OnTimeDataListener(GasInsectList);
                } else {
                    listener.OnTimeDataListener(null);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnTimeDataListener(null);
                util.showToast(getContext(),errorMsg);
            }
        });
    }
}