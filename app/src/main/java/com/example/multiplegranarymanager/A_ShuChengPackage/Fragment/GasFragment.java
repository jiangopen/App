package com.example.multiplegranarymanager.A_ShuChengPackage.Fragment;

import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.HistoryActivity.Date2TimeStamp;
import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.HistoryActivity.StrToDouble;
import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.Token;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_ShuChengPackage.Activity.HistoryActivity;
import com.example.multiplegranarymanager.A_ShuChengPackage.AllInterfaceClass;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice.DeviceData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.HistoryData.HistoryData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product.Value;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.ProductDetail.Data;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.DeviceInfos.DeviceInfos;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Body;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Headers;
import com.example.multiplegranarymanager.A_ShuChengPackage.SharedData.ShareProductDetail;
import com.example.multiplegranarymanager.A_ShuChengPackage.SharedData.SharedDeviceInfos;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.SelfDialog;
import com.example.multiplegranarymanager.Util.Util1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GasFragment extends Fragment implements View.OnClickListener{
    TableLayout tableLayout;
    FrameLayout fartherView;
    TextView cangkuname;
    TextView time;
    ImageView select;
    private SelfDialog selfDialog;
    private TextView mFooterView;
    private int selectWhich = 0;
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    private String Flag = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gas, container, false);
        tableLayout = view.findViewById(R.id.table_layout);
        fartherView = view.findViewById(R.id.fartherView);
        fartherView.setVisibility(View.GONE);
        cangkuname = view.findViewById(R.id.cangkuname);
        select = view.findViewById(R.id.select);
        time = view.findViewById(R.id.time);
        select.setOnClickListener(this);
        time.setOnClickListener(this);
        Bundle bundle = getArguments();
        Flag = bundle.getString("Flag");
        Log.d("jht", "onCreateView: "+Flag);
        cangkuname.setText(Flag);
        suoPingDialog = new SuoPingDialog(getContext(),"绘制中,请稍等......");
        suoPingDialog.show();
        initCalcuate(Flag,new HistoryActivity.OnInitCalculateFinishedListener(){
            @Override
            public void OnInitCalculateListener(DeviceData DATAONE, Map<String, Value> DATATWO) {
                if (DATAONE!=null && DATATWO!=null) {
                    DrawGasTable(DATATWO,DATAONE,Flag);
                    suoPingDialog.dismiss();
                } else {
                    suoPingDialog.dismiss();
                }
            }
        });
        return view;
    }
    private void initCalcuate(String flag, HistoryActivity.OnInitCalculateFinishedListener listener) {
        DeviceData DATAONE = new DeviceData();
        Map<String, Value> DATATWO = new HashMap<>();
        for (Data data : ShareProductDetail.ProductDetialList) {
            if (data.getProductName().equals(flag)) {
                DATATWO = data.getExtraInfo();
            }
        }
        for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList) {
            if (data.getGranaryName().equals(flag)) {
                DATAONE = data.getGasDeviceData();
            }
        }
        listener.OnInitCalculateListener(DATAONE,DATATWO);
    }
    private void DrawGasTable(Map<String, Value> gas, DeviceData data, String productName) {
        tableLayout.removeAllViews();
        // 设置表格背景样式
        tableLayout.setBackgroundResource(R.drawable.table_border);
        //获取行数
        String one = (String) gas.get("airDustPointNamesInTable").getValue();
        String[] dataArray = one.split("__");
        List<String> pointList = new ArrayList<>(Arrays.asList(dataArray));
        List<Double> Co2 = StrToDouble(data.getCo2());
        List<Double> Co2_B = StrToDouble(data.getCo2_bottom());
        List<Double> Dust = StrToDouble(data.getDust());
        List<Double> N2 = StrToDouble(data.getN2());
        List<Double> O2 = StrToDouble(data.getO2());
        List<Double> O2_B = StrToDouble(data.getO2_bottom());
        int hang = 3;
        Map<String,List<Double>> middle = new HashMap<>();
        if (Co2!=null&&Co2.size()>0){
            middle.put("CO₂",Co2);
            hang++;
        }
        if (Co2_B!=null&&Co2_B.size()>0) {
            middle.put("底部CO₂",Co2_B);
            hang++;
        }
        if (Dust!=null&&Dust.size()>0) {
            middle.put("粉尘",Dust);
            hang++;
        }
        if (N2!=null&&N2.size()>0) {
            middle.put("N₂",N2);
            hang++;
        }
        if (O2!=null&&O2.size()>0) {
            middle.put("O₂",O2);
            hang++;
        }
        if (O2_B!=null&&O2_B.size()>0) {
            middle.put("底部O₂",O2_B);
            hang++;
        }
        int lie = pointList.size()+1;

        for (int i=0;i<hang;i++){
            TableRow row = new TableRow(getContext());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            if (i==0){
                TextView cell1 = new TextView(getContext());
                cell1.setGravity(Gravity.CENTER);
                cell1.setText(productName);
                layoutParams.span = lie;
                layoutParams.column = 0;
                cell1.setLayoutParams(layoutParams);
                cell1.setBackgroundResource(R.drawable.table_border);
                row.addView(cell1);
            } else if (i==1){
                TextView cell2 = new TextView(getContext());
                cell2.setGravity(Gravity.RIGHT);
                cell2.setText("时间：" + data.getDate());
                layoutParams.span = lie;
                layoutParams.column = 0;
                cell2.setLayoutParams(layoutParams);
                cell2.setBackgroundResource(R.drawable.table_border);
                row.addView(cell2);
            } else {
                for (int j=0;j<lie;j++){
                    TextView cell = new TextView(getContext());
                    cell.setGravity(Gravity.CENTER);
                    cell.setLayoutParams(layoutParams);
                    cell.setBackgroundResource(R.drawable.table_border_two);
                    if (i==2&&j==0){
                        cell.setText("气体");
                    } else if (i==2&&j>0){
                        cell.setText(pointList.get(j-1));
                    } else if (i==3&&j==0){
                        cell.setText("CO₂");
                    } else if (i==3&&j>0){
                        if (j<=Co2.size()){
                            cell.setText(Co2.get(j-1)+"%");
                        } else {
                            cell.setText(" ");
                        }
                    } else if (i==4&&j==0){
                        cell.setText("N₂");
                    } else if (i==4&&j>0){
                        if (j<=N2.size()){
                            cell.setText(N2.get(j-1)+"%");
                        } else {
                            cell.setText(" ");
                        }
                    } else if (i==5&&j==0){
                        cell.setText("O₂");
                    } else if (i==5&&j>0){
                        if (j<=O2.size()){
                            cell.setText(O2.get(j-1)+"%");
                        } else {
                            cell.setText(" ");
                        }
                    } else if (i==6&&j==0){
                        cell.setText("粉尘");
                    } else if (i==6&&j>0){
                        if (j<=Dust.size()){
                            cell.setText(Dust.get(j-1)+"%");
                        } else {
                            cell.setText(" ");
                        }
                    } else if (i==7&&j==0){
                        cell.setText("底部CO₂");
                    } else if (i==7&&j>0){
                        if (j<=Co2_B.size()){
                            cell.setText(Co2_B.get(j-1)+"%");
                        } else {
                            cell.setText(" ");
                        }
                    } else if (i==8&&j==0){
                        cell.setText("底部O₂");
                    } else if (i==8&&j>0){
                        if (j<=O2_B.size()){
                            cell.setText(O2_B.get(j-1)+"%");
                        } else {
                            cell.setText(" ");
                        }
                    } else {
                        cell.setText("断路");
                        cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                    row.addView(cell);
                }
            }
            tableLayout.addView(row);
        }
    }
    public void initHistoryList(long startTimes, long endTimes, String deviceKey, String flag, final HistoryActivity.OnInitHistoryListFinishedListener listener) {
        AllInterfaceClass<HistoryData> one = new AllInterfaceClass<>(HistoryData.class);
        Body body = new Body();
        body.setAsc(1);
        body.setDeviceKey(deviceKey);
        body.setEndTime(endTimes);
        body.setStartTime(startTimes);
        Headers headers = new Headers();
        headers.setTokenOffline(Token);
        NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
        newDownRawBodyTWO.setMethod("POST");
        newDownRawBodyTWO.setHeaders(headers);
        newDownRawBodyTWO.setBody(body);
        newDownRawBodyTWO.setQuery(null);
        newDownRawBodyTWO.setUrl("/historyData");
        one.PostOne(newDownRawBodyTWO, "HistoryActivity/initHistoryList", new AllInterfaceClass.PostCallBack<HistoryData>() {
            @Override
            public void onSuccess(HistoryData zyq) {
                listener.OnInitHistoryListListener(zyq.getData().getDeviceData());
            }
            @Override
            public void onFailure(String zyq) {
                util.showToast(getContext(),zyq);
                listener.OnInitHistoryListListener(null);
            }
        });
    }
    private void SelectHistoryData(DeviceData deviceData, String finalFlag, final HistoryActivity.OnInitCalculateFinishedListener listener) {
        DeviceData DATAONE = new DeviceData();
        Map<String, Value> DATATWO = new HashMap<>();
        for (Data data : ShareProductDetail.ProductDetialList) {
            if (data.getProductName().equals(Flag)) {
                DATATWO = data.getExtraInfo();
            }
        }
        DATAONE = deviceData;
        listener.OnInitCalculateListener(DATAONE,DATATWO);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                            String key = null;
                            String flag = null;
                            for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList) {
                                if (data.getGranaryName().equals(cangkuname.getText())) {
                                    key = data.getGasDeviceKey();
                                    flag = "温湿水";
                                }
                            }
                            //通过时间查询
                            String finalFlag = flag;
                            initHistoryList(longstart, longend, key, flag, new HistoryActivity.OnInitHistoryListFinishedListener() {
                                @Override
                                public void OnInitHistoryListListener(List<DeviceData> result) {
                                    if (result!=null && result.size()>0) {
                                        List<String> timeList = new ArrayList<>();
                                        for (DeviceData data : result) {
                                            if (data.getDate()!=null) {
                                                timeList.add(data.getDate());
                                            }
                                        }
                                        ArrayAdapter adapter = new ArrayAdapter(getContext(),R.layout.simple_list,timeList);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                if (result.size() > which && result.get(which) != null) {
                                                    SelectHistoryData(result.get(which), finalFlag, new HistoryActivity.OnInitCalculateFinishedListener() {
                                                        @Override
                                                        public void OnInitCalculateListener(DeviceData DATAONE, Map<String, Value> DATATWO) {
                                                            if (DATAONE!=null && DATATWO!=null) {
                                                                fartherView.setVisibility(View.GONE);
                                                                DrawGasTable(DATATWO,DATAONE,Flag);
                                                                suoPingDialog.dismiss();
                                                            } else {
                                                                suoPingDialog.dismiss();
                                                                fartherView.setVisibility(View.VISIBLE);
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                        dialog.setCanceledOnTouchOutside(true);
                                    } else {
                                        util.showToast(getContext(), "无数据！");
                                    }
                                }
                            });
                        }
                    }
                });
                selfDialog.show();
                break;
            case R.id.select:
                List<String> Name = new ArrayList<>();
                for (int i=0;i<SharedDeviceInfos.DeviceInfosList.size();i++) {
                    Name.add(SharedDeviceInfos.DeviceInfosList.get(i).getGranaryName());
                    if (SharedDeviceInfos.DeviceInfosList.get(i).getGranaryName().equals(Flag)) {
                        selectWhich = i;
                    }
                }
                ArrayAdapter adapter_this = new ArrayAdapter<String>(getContext(),R.layout.simple_list,Name);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setSingleChoiceItems(adapter_this, selectWhich, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectWhich = which;
                        Flag = SharedDeviceInfos.DeviceInfosList.get(which).getGranaryName();
                        cangkuname.setText(Flag);
                        suoPingDialog = new SuoPingDialog(getContext(),"绘制中,请稍等......");
                        suoPingDialog.show();
                        initCalcuate(Flag, new HistoryActivity.OnInitCalculateFinishedListener() {
                            @Override
                            public void OnInitCalculateListener(DeviceData DATAONE, Map<String, Value> DATATWO) {
                                if (DATAONE!=null && DATATWO!=null) {
                                    fartherView.setVisibility(View.GONE);
                                    DrawGasTable(DATATWO,DATAONE,Flag);
                                    suoPingDialog.dismiss();
                                } else {
                                    suoPingDialog.dismiss();
                                    fartherView.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                        dialog.dismiss();
                    }
                });
                AlertDialog dialogone = builder.create();
                dialogone.show();
                dialogone.setCanceledOnTouchOutside(true);
                break;
            default:
                break;
        }
    }
}