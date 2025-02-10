package com.example.multiplegranarymanager.A_ShuChengPackage.Fragment;

import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.HistoryActivity.Date2TimeStamp;
import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.HistoryActivity.StrToDouble;
import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.Token;

import android.content.DialogInterface;
import android.graphics.Color;
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
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.CropBean;
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
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HumFragment extends Fragment implements View.OnClickListener{
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
        View view = inflater.inflate(R.layout.fragment_hum, container, false);
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
        cangkuname.setText(Flag);
        suoPingDialog = new SuoPingDialog(getContext(),"绘制中,请稍等......");
        suoPingDialog.show();
        initCalcuate(Flag,new HistoryActivity.OnInitCalculateFinishedListener(){
            @Override
            public void OnInitCalculateListener(DeviceData DATAONE, Map<String, Value> DATATWO) {
                if (DATAONE!=null && DATATWO!=null) {
                    fartherView.setVisibility(View.GONE);
                    DrawHumTable(DATATWO,DATAONE,Flag);
                    suoPingDialog.dismiss();
                } else {
                    suoPingDialog.dismiss();
                    fartherView.setVisibility(View.VISIBLE);
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
                DATAONE = data.getHumDeviceData();
            }
        }
        listener.OnInitCalculateListener(DATAONE,DATATWO);
    }
    private List<Double> calulate(String liangshizhonglei, List<Double> tem, List<Double> mon) {
        String json = null;
        try{
            InputStream is = getContext().getAssets().open("check.json");
            InputStreamReader isr = new InputStreamReader(is,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine())!=null){
                builder.append(line);
            }
            br.close();
            isr.close();
            json = new String(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        List<Double> waterdouble = new ArrayList<>();
        CropBean cropBean = gson.fromJson(json, CropBean.class);
        if (liangshizhonglei.equals("小麦")){
            if (tem!=null&&mon!=null){
                if (tem.size()==mon.size()){
                    for (int i=0;i<tem.size();i++){
                        if (tem.get(i)==255||mon.get(i)==255){
                            waterdouble.add(255.0);
                        } else {
                            int Tem = (int) (tem.get(i)/1);
                            int Mon = (int) (mon.get(i)/1);
                            String str;
                            if (Mon>90){
                                str = Tem+"/90";
                            } else if (Mon<20) {
                                str = Tem+"/20";
                            } else if (Tem>30) {
                                str = "30/"+Mon;
                            } else if (Tem<0) {
                                str = "0/"+Mon;
                            }else {
                                str = Tem+"/"+Mon;
                            }
                            double last;
                            if (cropBean.getDadou().get(str)!=null){
                                last = cropBean.getXiaomai().get(str);
                                waterdouble.add(last);
                            }
                        }
                    }
                } else {
                    int sizeDifference = Math.abs(tem.size() - mon.size());
                    int maxSize = Math.max(tem.size(), mon.size());
                    for (int i = 0; i < maxSize; i++) {
                        if (i < sizeDifference) {
                            waterdouble.add(255.0);
                        } else {
                            if (tem.get(i)==255||mon.get(i)==255){
                                waterdouble.add(255.0);
                            } else {
                                int Tem = (int) (tem.get(i)/1);
                                int Mon = (int) (mon.get(i)/1);
                                String str;
                                if (Mon>90){
                                    str = Tem+"/90";
                                } else if (Mon<20) {
                                    str = Tem+"/20";
                                } else if (Tem>30) {
                                    str = "30/"+Mon;
                                } else if (Tem<0) {
                                    str = "0/"+Mon;
                                }else {
                                    str = Tem+"/"+Mon;
                                }
                                double last;
                                if (cropBean.getDadou().get(str)!=null){
                                    last = cropBean.getXiaomai().get(str);
                                    waterdouble.add(last);
                                }
                            }
                        }
                    }
                }
            }
            return waterdouble;
        } else if (liangshizhonglei.equals("稻谷")) {
            if (tem.size()==mon.size()){
                for (int i=0;i<tem.size();i++){
                    double last = 255;
                    if (tem.get(i)==255||mon.get(i)==255){
                        waterdouble.add(last);
                    }else {
                        int Tem = (int) (tem.get(i)/1);
                        int Mon = (int) (mon.get(i)/1);
                        String str;
                        if (Mon>90){
                            str = Tem+"/90";
                        } else if (Mon<20) {
                            str = Tem+"/20";
                        } else if (Tem>30) {
                            str = "30/"+Mon;
                        } else if (Tem<0) {
                            str = "0/"+Mon;
                        }else {
                            str = Tem+"/"+Mon;
                        }
                        if (cropBean.getDadou().get(str)!=null){
                            last = cropBean.getDaogu().get(str);
                        }
                        waterdouble.add(last);
                    }
                }
            }
            return waterdouble;
        }else if (liangshizhonglei.equals("玉米")) {
            for (int i=0;i<tem.size();i++){
                if (tem.get(i)==255||mon.get(i)==255){
                    waterdouble.add(255.0);
                }else{
                    int Tem = (int) (tem.get(i)/1);
                    int Mon = (int) (mon.get(i)/1);
                    String str;
                    if (Mon>90){
                        str = Tem+"/90";
                    } else if (Mon<20) {
                        str = Tem+"/20";
                    } else if (Tem>30) {
                        str = "30/"+Mon;
                    } else if (Tem<0) {
                        str = "0/"+Mon;
                    }else {
                        str = Tem+"/"+Mon;
                    }
                    double last;
                    if (cropBean.getDadou().get(str)!=null){
                        last = cropBean.getYumi().get(str);
                        waterdouble.add(last);
                    }
                }
            }
            return waterdouble;
        }else if (liangshizhonglei.equals("大米")) {
            for (int i=0;i<tem.size();i++){
                if (tem.get(i)==255||mon.get(i)==255){
                    waterdouble.add(255.0);
                }else{
                    int Tem = (int) (tem.get(i)/1);
                    int Mon = (int) (mon.get(i)/1);
                    String str;
                    if (Mon>90){
                        str = Tem+"/90";
                    } else if (Mon<20) {
                        str = Tem+"/20";
                    } else if (Tem>30) {
                        str = "30/"+Mon;
                    } else if (Tem<0) {
                        str = "0/"+Mon;
                    }else {
                        str = Tem+"/"+Mon;
                    }
                    double last;
                    if (cropBean.getDadou().get(str)!=null){
                        last = cropBean.getDami().get(str);
                        waterdouble.add(last);
                    }
                }
            }
            return waterdouble;
        }else if (liangshizhonglei.equals("黍子")) {
            for (int i=0;i<tem.size();i++){
                if (tem.get(i)==255||mon.get(i)==255){
                    waterdouble.add(255.0);
                }else{
                    int Tem = (int) (tem.get(i)/1);
                    int Mon = (int) (mon.get(i)/1);
                    String str;
                    if (Mon>90){
                        str = Tem+"/90";
                    } else if (Mon<20) {
                        str = Tem+"/20";
                    } else if (Tem>30) {
                        str = "30/"+Mon;
                    } else if (Tem<0) {
                        str = "0/"+Mon;
                    }else {
                        str = Tem+"/"+Mon;
                    }
                    double last;
                    if (cropBean.getDadou().get(str)!=null){
                        last = cropBean.getShuzi().get(str);
                        waterdouble.add(last);
                    }
                }
            }
            return waterdouble;
        }else if (liangshizhonglei.equals("大豆")) {
            for (int i=0;i<tem.size();i++){
                if (tem.get(i)==255||mon.get(i)==255){
                    waterdouble.add(255.0);
                }else{
                    int Tem = (int) (tem.get(i)/1);
                    int Mon = (int) (mon.get(i)/1);
                    String str;
                    if (Mon>90){
                        str = Tem+"/90";
                    } else if (Mon<20) {
                        str = Tem+"/20";
                    } else if (Tem>30) {
                        str = "30/"+Mon;
                    } else if (Tem<0) {
                        str = "0/"+Mon;
                    }else {
                        str = Tem+"/"+Mon;
                    }
                    double last;
                    if (cropBean.getDadou().get(str)!=null){
                        last = cropBean.getDadou().get(str);
                        waterdouble.add(last);
                    }
                }
            }
            return waterdouble;
        }else {
            return waterdouble;
        }
    }
    private void DrawHumTable(Map<String, Value> mon, DeviceData data, String productName) {
        tableLayout.removeAllViews();
        // 设置表格背景样式
        tableLayout.setBackgroundResource(R.drawable.table_border);
        //获取行数
        String one = (String) mon.get("pointProfileSec").getValue();
        List<Integer> number = new ArrayList<>();
        String[] dataArray = one.split("00");
        for (String Data : dataArray){
            if (Data.matches("\\d+")) { // 使用正则表达式检查是否全为数字
                number.add(Integer.parseInt(Data));
            }
        }
        int max = Collections.max(number);
        int lie = max + 1;
        int hang = number.size() + 6;

        //处理数据，组装成二维数组
        List<Double> TEM = StrToDouble(data.getCableTemp());
        Log.e("nawmzyq", "temList: "+TEM.size());
        List<Double> MON = StrToDouble(data.getHumidityList());
        Log.e("nawmzyq", "monList: "+MON.size());
        String liangshizhonglei = mon.get("liangshizhonglei").getValue().toString();
        List<Double> Water = calulate(liangshizhonglei,TEM,MON);

        //温度列表
        double[][] temarray = new double[number.size()][max];
        int indextem = 0;
        for (int i=0;i<number.size();i++){
            for (int j=0;j<max;j++){
                if (data.getCableTemp()!=null){
                    if (indextem<data.getCableTemp().size()){
                        temarray[i][j] = TEM.get(indextem);
                        indextem++;
                    }else {
                        break;
                    }
                }else {
                    temarray[i][j] = 255;
                    indextem++;
                }
            }
        }
        double[] temmax = new double[max];
        double[] temmin = new double[max];
        double[] temavg = new double[max];
        for (int i = 0; i < max; i++) {
            double maxVal = Double.MIN_VALUE;
            double minVal = Double.MAX_VALUE;
            double sum = 0;
            int count1 = 0;
            for (int j = 0; j < number.size(); j++) {
                if (temarray[j].length > i) {
                    double value = temarray[j][i];
                    if (value != 255) {
                        maxVal = Math.max(maxVal, value);
                        minVal = Math.min(minVal, value);
                        sum += value;
                        count1++;
                    }
                }
            }
            temmax[i] = maxVal;
            temmin[i] = minVal;
            temavg[i] = count1 > 0 ? sum / count1 : Double.NaN;
        }

        //湿度列表
        double[][] monarray = new double[number.size()][max];
        int indexmon = 0;
        for (int i=0;i<number.size();i++){
            for (int j=0;j<max;j++){
                if (MON!=null){
                    if (indexmon<MON.size()){
                        monarray[i][j] = MON.get(indexmon);
                        indexmon++;
                    }else {
                        break;
                    }
                }else {
                    monarray[i][j] = 255;
                    indexmon++;
                }
            }
        }
        double[] monmax = new double[max];
        double[] monmin = new double[max];
        double[] monavg = new double[max];
        for (int i = 0; i < max; i++) {
            double maxVal = Double.MIN_VALUE;
            double minVal = Double.MAX_VALUE;
            double sum = 0;
            int count2 = 0;
            for (int j = 0; j < number.size(); j++) {
                if (monarray[j].length > i) {
                    double value = monarray[j][i];
                    if (value != 255) {
                        maxVal = Math.max(maxVal, value);
                        minVal = Math.min(minVal, value);
                        sum += value;
                        count2++;
                    }
                }
            }
            monmax[i] = maxVal;
            monmin[i] = minVal;
            monavg[i] = count2 > 0 ? sum / count2 : Double.NaN;
        }

        //水分列表
        double[][] waterarray = new double[number.size()][max];
        int indexwater = 0;
        for (int i=0;i<number.size();i++){
            for (int j=0;j<max;j++){
                if (Water!=null){
                    if (indexwater<Water.size()){
                        waterarray[i][j] = Water.get(indexwater);
                        indexwater++;
                    }else {
                        break;
                    }
                }else {
                    waterarray[i][j] = 255;
                    indexwater++;
                }
            }
        }
        double[] watermax = new double[max];
        double[] watermin = new double[max];
        double[] wateravg = new double[max];
        for (int i = 0; i < max; i++) {
            double maxVal = Double.MIN_VALUE;
            double minVal = Double.MAX_VALUE;
            double sum = 0;
            int count3 = 0;
            for (int j = 0; j < number.size(); j++) {
                if (waterarray[j].length > i) {
                    double value = waterarray[j][i];
                    if (value != 255) {
                        maxVal = Math.max(maxVal, value);
                        minVal = Math.min(minVal, value);
                        sum += value;
                        count3++;
                    }
                }
            }
            watermax[i] = maxVal;
            watermin[i] = minVal;
            wateravg[i] = count3 > 0 ? sum / count3 : Double.NaN;
        }

        //第一张表
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
                cell2.setText("温度/湿度/水分   "+"时间：" + data.getDate());
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
                    String tem1,mon1,water1;
                    DecimalFormat decimalFormat = new DecimalFormat("#.0");
                    if (i==2&&j==0){
                        cell.setText("电缆号");
                    } else if (i==2&&j>0){
                        cell.setText("第"+j+"层");
                    } else if (j==0&&i>2&&i<(hang-3)){
                        cell.setText("第"+(i-2)+"根");
                    } else if (j==0&&i==(hang-3)){
                        cell.setText("最高值");
                        cell.setTextColor(Color.WHITE);
                        cell.setBackgroundColor(Color.RED);
                    } else if (j==0&&i==(hang-2)){
                        cell.setText("最低值");
                        cell.setTextColor(Color.WHITE);
                        cell.setBackgroundColor(Color.BLUE);
                    } else if (j==0&&i==(hang-1)){
                        cell.setText("平均值");
                        cell.setTextColor(Color.WHITE);
                        cell.setBackgroundColor(Color.GREEN);
                    } else if (j>0&&i<(hang-3)&&i>2) {
                        if (temarray[i-3][j-1]!=255.0){
                            tem1 = decimalFormat.format(temarray[i-3][j-1]);
                        } else {
                            tem1 = "断路";
                            cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        }
                        if (monarray[i-3][j-1]!=255.0){
                            mon1 = decimalFormat.format(monarray[i-3][j-1]);
                        } else {
                            mon1 = "断路";
                            cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        }
                        if (waterarray[i-3][j-1]!=255.0){
                            water1 = decimalFormat.format(waterarray[i-3][j-1]);
                        } else {
                            water1 = "断路";
                            cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        }
                        cell.setText(tem1+"/"+mon1+"/"+water1);
                    } else if (j>0&&i==(hang-3)) {
                        tem1 = decimalFormat.format(temmax[j-1]);
                        mon1 = decimalFormat.format(monmax[j-1]);
                        water1 = decimalFormat.format(watermax[j-1]);
                        cell.setText(tem1+"/"+mon1+"/"+water1);
                    } else if (j>0&&i==(hang-2)) {
                        tem1 = decimalFormat.format(temmin[j-1]);
                        mon1 = decimalFormat.format(monmin[j-1]);
                        water1 = decimalFormat.format(watermin[j-1]);
                        cell.setText(tem1+"/"+mon1+"/"+water1);
                    } else if (j>0&&i==(hang-1)) {
                        tem1 = decimalFormat.format(temavg[j-1]);
                        mon1 = decimalFormat.format(monavg[j-1]);
                        water1 = decimalFormat.format(wateravg[j-1]);
                        cell.setText(tem1+"/"+mon1+"/"+water1);
                    }
                    row.addView(cell);
                }
            }
            tableLayout.addView(row);
        }

        // 在表格的最后一行添加空的单元格
        TableRow emptyCellRow = new TableRow(getContext());
        TableRow.LayoutParams emptyCellLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        emptyCellRow.setLayoutParams(emptyCellLayoutParams);
        TextView emptyCell = new TextView(getContext());
        emptyCell.setGravity(Gravity.CENTER);
        emptyCell.setText(" "); // 设置为空白内容
        emptyCellRow.addView(emptyCell);
        tableLayout.addView(emptyCellRow);

        //第二张表
        for (int i=0;i<4;i++){
            TableRow row = new TableRow(getContext());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            String tem1;
            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            for (int j=0;j<4;j++){
                TextView cell = new TextView(getContext());
                cell.setGravity(Gravity.CENTER);
                cell.setLayoutParams(layoutParams);
                cell.setBackgroundResource(R.drawable.table_border_two);
                if (i==0&&j==0){
                    cell.setText("全仓");
                } else if (i==0&&j==1) {
                    cell.setText("最高");
                    cell.setTextColor(Color.WHITE);
                    cell.setBackgroundColor(Color.RED);
                } else if (i==0&&j==2) {
                    cell.setText("最低");
                    cell.setTextColor(Color.WHITE);
                    cell.setBackgroundColor(Color.BLUE);
                } else if (i==0&&j==3) {
                    cell.setText("平均");
                    cell.setTextColor(Color.WHITE);
                    cell.setBackgroundColor(Color.GREEN);
                } else if (i==1&&j==0) {
                    cell.setText("温度");
                } else if (i==2&&j==0) {
                    cell.setText("湿度");
                } else if (i==3&&j==0) {
                    cell.setText("水分");
                } else if (i==1&&j==1) {
                    tem1 = decimalFormat.format(Arrays.stream(temmax).max().orElse(Double.MIN_VALUE));
                    cell.setText(tem1);
                } else if (i==1&&j==2) {
                    tem1 = decimalFormat.format(Arrays.stream(temmin).min().orElse(Double.MAX_VALUE));
                    cell.setText(tem1);
                } else if (i==1&&j==3) {
                    tem1 = decimalFormat.format(Arrays.stream(temavg).average().orElse(Double.NaN));
                    cell.setText(tem1);
                } else if (i==2&&j==1) {
                    tem1 = decimalFormat.format(Arrays.stream(monmax).max().orElse(Double.MIN_VALUE));
                    cell.setText(tem1);
                } else if (i==2&&j==2) {
                    tem1 = decimalFormat.format(Arrays.stream(monmin).min().orElse(Double.MAX_VALUE));
                    cell.setText(tem1);
                } else if (i==2&&j==3) {
                    tem1 = decimalFormat.format(Arrays.stream(monavg).average().orElse(Double.NaN));
                    cell.setText(tem1);
                } else if (i==3&&j==1) {
                    tem1 = decimalFormat.format(Arrays.stream(watermax).max().orElse(Double.MIN_VALUE));
                    cell.setText(tem1);
                } else if (i==3&&j==2) {
                    tem1 = decimalFormat.format(Arrays.stream(watermin).min().orElse(Double.MAX_VALUE));
                    cell.setText(tem1);
                } else if (i==3&&j==3) {
                    tem1 = decimalFormat.format(Arrays.stream(wateravg).average().orElse(Double.NaN));
                    cell.setText(tem1);
                }
                row.addView(cell);
            }
            tableLayout.addView(row);
        }

        // 在表格的最后一行添加空的单元格
        TableRow emptyCellRowtwo = new TableRow(getContext());
        TableRow.LayoutParams emptyCellLayoutParamstwo = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        emptyCellRowtwo.setLayoutParams(emptyCellLayoutParamstwo);
        TextView emptyCelltwo = new TextView(getContext());
        emptyCelltwo.setGravity(Gravity.CENTER);
        emptyCelltwo.setText(" "); // 设置为空白内容
        emptyCellRowtwo.addView(emptyCelltwo);
        tableLayout.addView(emptyCellRowtwo);

        //展示温湿水的基本信息
        for (int i=0;i<2;i++){
            TableRow row = new TableRow(getContext());
            // 设置单元格的布局参数
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            if (i==1){
                TextView cell2 = new TextView(getContext());
                cell2.setGravity(Gravity.CENTER);
                cell2.setText("备注：温度单位：℃ 湿度单位：%");
                layoutParams.span = 8;
                layoutParams.column = 0;
                cell2.setLayoutParams(layoutParams);
                cell2.setBackgroundResource(R.drawable.table_border);
                row.addView(cell2);
            } else {
                for (int j=0;j<8;j++){
                    TextView cell = new TextView(getContext());
                    cell.setGravity(Gravity.CENTER);
                    cell.setLayoutParams(layoutParams);
                    cell.setBackgroundResource(R.drawable.table_border_two);
                    if (i==0&&j==0){
                        cell.setText("室内温度");
                    } else if (i==0&&j==1) {
                        if (data.getTemp()!=null){
                            cell.setText(data.getTemp()+"℃");
                        } else {
                            cell.setText("————");
                        }
                    } else if (i==0&&j==2) {
                        cell.setText("室内湿度");
                    } else if (i==0&&j==3) {
                        if (data.getHumidity()!=null){
                            cell.setText(data.getHumidity()+"%");
                        } else {
                            cell.setText("————");
                        }
                    } else if (i==0&&j==4) {
                        cell.setText("室外温度");
                    } else if (i==0&&j==5) {
                        if (data.getTemp_out()!=null){
                            cell.setText(data.getTemp_out()+"℃");
                        } else {
                            cell.setText("————");
                        }
                    } else if (i==0&&j==6) {
                        cell.setText("室外湿度");
                    } else if (i==0&&j==7) {
                        if (data.getHumidity_out()!=null){
                            cell.setText(data.getHumidity_out()+"%");
                        } else {
                            cell.setText("————");
                        }
                    }
                    row.addView(cell);
                }
            }
            tableLayout.addView(row);
        }

        // 在表格的最后一行添加空的单元格
        TableRow emptyCellRowthree = new TableRow(getContext());
        TableRow.LayoutParams emptyCellLayoutParamsthree = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        emptyCellRowthree.setLayoutParams(emptyCellLayoutParamsthree);
        TextView emptyCellthree = new TextView(getContext());
        emptyCellthree.setGravity(Gravity.CENTER);
        emptyCellthree.setText(" "); // 设置为空白内容
        emptyCellRowthree.addView(emptyCellthree);
        tableLayout.addView(emptyCellRowthree);

        //第四张表
        for (int i=0;i<7;i++){
            TableRow row = new TableRow(getContext());
            // 设置单元格的布局参数
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            for (int j=0;j<6;j++){
                TextView cell = new TextView(getContext());
                cell.setGravity(Gravity.CENTER);
                cell.setLayoutParams(layoutParams);
                cell.setBackgroundResource(R.drawable.table_border_two);
                if (i==0&&j==0){
                    cell.setText("粮食种类");
                } else if (i==0&&j==1) {
                    if (mon.get("pinzhongmingcheng").getValue()!=null){
                        cell.setText(mon.get("pinzhongmingcheng").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==0&&j==2) {
                    cell.setText("产地");
                } else if (i==0&&j==3) {
                    if (mon.get("chandi").getValue()!=null){
                        cell.setText(mon.get("chandi").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==0&&j==4) {
                    cell.setText("收获年份");
                } else if (i==0&&j==5) {
                    if (mon.get("shouhuonianfen").getValue()!=null){
                        cell.setText(mon.get("shouhuonianfen").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==1&&j==0) {
                    cell.setText("入仓日期");
                } else if (i==1&&j==1) {
                    if (mon.get("rucangriqi").getValue()!=null){
                        cell.setText(mon.get("rucangriqi").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==1&&j==2) {
                    cell.setText("存储类型");
                } else if (i==1&&j==3) {
                    if (mon.get("cunchuleixing").getValue()!=null){
                        cell.setText(mon.get("cunchuleixing").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==1&&j==4) {
                    cell.setText("实际储量");
                } else if (i==1&&j==5) {
                    if (mon.get("shijichuliang").getValue()!=null){
                        cell.setText(mon.get("shijichuliang").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==2&&j==0) {
                    cell.setText("不完善粒");
                } else if (i==2&&j==1) {
                    if (mon.get("buwanshanli").getValue()!=null){
                        cell.setText(mon.get("buwanshanli").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==2&&j==2) {
                    cell.setText("入仓水分");
                } else if (i==2&&j==3) {
                    if (mon.get("rucangshuifen").getValue()!=null){
                        cell.setText(mon.get("rucangshuifen").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==2&&j==4) {
                    cell.setText("杂质");
                } else if (i==2&&j==5) {
                    if (mon.get("zazhi").getValue()!=null){
                        cell.setText(mon.get("zazhi").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==3&&j==0) {
                    cell.setText("当前水分");
                } else if (i==3&&j==1) {
                    if (mon.get("dangqianshuifen").getValue()!=null){
                        cell.setText(mon.get("dangqianshuifen").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==3&&j==2) {
                    cell.setText("容重/出糙");
                } else if (i==3&&j==3) {
                    if (mon.get("rongzhongchucao").getValue()!=null){
                        cell.setText(mon.get("rongzhongchucao").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==3&&j==4) {
                    cell.setText("堆位");
                } else if (i==3&&j==5) {
                    if (mon.get("duiwei").getValue()!=null){
                        cell.setText(mon.get("duiwei").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==4&&j==0) {
                    cell.setText("高温报警");
                } else if (i==4&&j==1) {
                    if (mon.get("baojingwendu1").getValue()!=null){
                        cell.setText(mon.get("baojingwendu1").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==4&&j==2) {
                    cell.setText("低温报警");
                } else if (i==4&&j==3) {
                    if (mon.get("baojingwendu0").getValue()!=null){
                        cell.setText(mon.get("baojingwendu0").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==4&&j==4) {
                    cell.setText("天气");
                } else if (i==4&&j==5) {
                    if (mon.get("tianqi").getValue()!=null){
                        cell.setText(mon.get("tianqi").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==5&&j==0) {
                    cell.setText("仓库等级");
                } else if (i==5&&j==1) {
                    if (mon.get("dengji").getValue()!=null){
                        cell.setText(mon.get("dengji").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==5&&j==2) {
                    cell.setText("保管员");
                } else if (i==5&&j==3) {
                    if (mon.get("baoguanyuan").getValue()!=null){
                        cell.setText(mon.get("baoguanyuan").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==5&&j==4) {
                    cell.setText("检测员");
                } else if (i==5&&j==5) {
                    if (mon.get("jianceyuan").getValue()!=null){
                        cell.setText(mon.get("jianceyuan").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==6) {
                    if (j==0){
                        cell.setText("粮情分析");
                    } else if (j>0){
                        if (mon.get("liangqingfenxi").getValue()!=null){
                            cell.setText(mon.get("liangqingfenxi").getValue().toString());
                        } else {
                            cell.setBackgroundResource(0);
                            cell.setText(" ");
                        }
                    }
                }
                row.addView(cell);
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
                                    key = data.getHumDeviceKey();
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
                                                                DrawHumTable(DATATWO,DATAONE,Flag);
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
                                    DrawHumTable(DATATWO,DATAONE,Flag);
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