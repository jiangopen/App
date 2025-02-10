package com.example.multiplegranarymanager.A_ShuChengPackage.Activity;

import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.Token;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_ShuChengPackage.AllInterfaceClass;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice.DeviceData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.CropBean;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.HistoryData.HistoryData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product.Value;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.ProductDetail.Data;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Body;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.DeviceInfos.DeviceInfos;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{
    TextView title,name,time;
    ImageView select;
    FrameLayout fatherView;
    TableLayout tableLayout;
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    private SuoPingDialog suoPingDialog;
    private SelfDialog selfDialog;
    private int selectWhich = 0;
    private String TITLE = "请稍等", NAME = "无";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tem);
        title = findViewById(R.id.title);
        name = findViewById(R.id.cangkuname);
        time = findViewById(R.id.time);
        select = findViewById(R.id.select);
        time.setOnClickListener(this);
        select.setOnClickListener(this);
        fatherView = findViewById(R.id.fartherView);
        tableLayout = findViewById(R.id.table_layout);
        TITLE = getIntent().getStringExtra("flag");
        NAME = getIntent().getStringExtra("granaryName");
        title.setText(TITLE);
        name.setText(NAME);
        suoPingDialog = new SuoPingDialog(this,"绘制中,请稍等......");
        suoPingDialog.show();
        initCalculate(TITLE, new OnInitCalculateFinishedListener() {
            @Override
            public void OnInitCalculateListener(DeviceData DATAONE, Map<String, Value> DATATWO) {
                if (DATAONE!=null && DATATWO!=null) {
                    fatherView.setVisibility(View.GONE);
                    if (TITLE.equals("温湿度")) {
                        DrawTemTable(DATATWO,DATAONE,NAME);
                        suoPingDialog.dismiss();
                    } else if (TITLE.equals("温湿水")) {
                        DrawHumTable(DATATWO,DATAONE,NAME);
                        suoPingDialog.dismiss();
                    } else if (TITLE.equals("气体")) {
                        DrawGasTable(DATATWO,DATAONE,NAME);
                        suoPingDialog.dismiss();
                    }
                } else {
                    suoPingDialog.dismiss();
                    fatherView.setVisibility(View.VISIBLE);
                }
            }
        });
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
            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            if (i==0){
                TextView cell1 = new TextView(this);
                cell1.setGravity(Gravity.CENTER);
                cell1.setText(productName);
                layoutParams.span = lie;
                layoutParams.column = 0;
                cell1.setLayoutParams(layoutParams);
                cell1.setBackgroundResource(R.drawable.table_border);
                row.addView(cell1);
            } else if (i==1){
                TextView cell2 = new TextView(this);
                cell2.setGravity(Gravity.RIGHT);
                cell2.setText("时间：" + data.getDate());
                layoutParams.span = lie;
                layoutParams.column = 0;
                cell2.setLayoutParams(layoutParams);
                cell2.setBackgroundResource(R.drawable.table_border);
                row.addView(cell2);
            } else {
                for (int j=0;j<lie;j++){
                    TextView cell = new TextView(this);
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
            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            if (i==0){
                TextView cell1 = new TextView(this);
                cell1.setGravity(Gravity.CENTER);
                cell1.setText(productName);
                layoutParams.span = lie;
                layoutParams.column = 0;
                cell1.setLayoutParams(layoutParams);
                cell1.setBackgroundResource(R.drawable.table_border);
                row.addView(cell1);
            } else if (i==1){
                TextView cell2 = new TextView(this);
                cell2.setGravity(Gravity.RIGHT);
                cell2.setText("温度/湿度/水分   "+"时间：" + data.getDate());
                layoutParams.span = lie;
                layoutParams.column = 0;
                cell2.setLayoutParams(layoutParams);
                cell2.setBackgroundResource(R.drawable.table_border);
                row.addView(cell2);
            } else {
                for (int j=0;j<lie;j++){
                    TextView cell = new TextView(this);
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
        TableRow emptyCellRow = new TableRow(this);
        TableRow.LayoutParams emptyCellLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        emptyCellRow.setLayoutParams(emptyCellLayoutParams);
        TextView emptyCell = new TextView(this);
        emptyCell.setGravity(Gravity.CENTER);
        emptyCell.setText(" "); // 设置为空白内容
        emptyCellRow.addView(emptyCell);
        tableLayout.addView(emptyCellRow);

        //第二张表
        for (int i=0;i<4;i++){
            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            String tem1;
            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            for (int j=0;j<4;j++){
                TextView cell = new TextView(this);
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
        TableRow emptyCellRowtwo = new TableRow(this);
        TableRow.LayoutParams emptyCellLayoutParamstwo = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        emptyCellRowtwo.setLayoutParams(emptyCellLayoutParamstwo);
        TextView emptyCelltwo = new TextView(this);
        emptyCelltwo.setGravity(Gravity.CENTER);
        emptyCelltwo.setText(" "); // 设置为空白内容
        emptyCellRowtwo.addView(emptyCelltwo);
        tableLayout.addView(emptyCellRowtwo);

        //展示温湿水的基本信息
        for (int i=0;i<2;i++){
            TableRow row = new TableRow(this);
            // 设置单元格的布局参数
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            if (i==1){
                TextView cell2 = new TextView(this);
                cell2.setGravity(Gravity.CENTER);
                cell2.setText("备注：温度单位：℃ 湿度单位：%");
                layoutParams.span = 8;
                layoutParams.column = 0;
                cell2.setLayoutParams(layoutParams);
                cell2.setBackgroundResource(R.drawable.table_border);
                row.addView(cell2);
            } else {
                for (int j=0;j<8;j++){
                    TextView cell = new TextView(this);
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
        TableRow emptyCellRowthree = new TableRow(this);
        TableRow.LayoutParams emptyCellLayoutParamsthree = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        emptyCellRowthree.setLayoutParams(emptyCellLayoutParamsthree);
        TextView emptyCellthree = new TextView(this);
        emptyCellthree.setGravity(Gravity.CENTER);
        emptyCellthree.setText(" "); // 设置为空白内容
        emptyCellRowthree.addView(emptyCellthree);
        tableLayout.addView(emptyCellRowthree);

        //第四张表
        for (int i=0;i<7;i++){
            TableRow row = new TableRow(this);
            // 设置单元格的布局参数
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            for (int j=0;j<6;j++){
                TextView cell = new TextView(this);
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
    private List<Double> calulate(String liangshizhonglei, List<Double> tem, List<Double> mon) {
        String json = null;
        try{
            InputStream is = this.getAssets().open("check.json");
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
    private void DrawTemTable(Map<String, Value> tem, DeviceData data, String productName) {
        tableLayout.removeAllViews();
        // 设置表格背景样式
        tableLayout.setBackgroundResource(R.drawable.table_border);
        //将温度列表转换为double
        List<Double> list = StrToDouble(data.getCableTemp());
        //获取行数
        String one = (String) tem.get("lan").getValue();
        int lan = Integer.parseInt(one);
        String two = (String) tem.get("ceng").getValue();
        int ceng = Integer.parseInt(two);
        int x = (lan*ceng)+3;
        //获取列数
        String three = (String) tem.get("zu").getValue();
        int zu = Integer.parseInt(three);
        int y = zu + 2;
        //按照数据创建二维数组
        double[][] array = new double[x-3][zu];
        int index = 0;//数据列表的索引
        for (int a=0;a<zu;a++){
            for (int b=0;b<x-3;b++){
                if (index<list.size()){
                    array[b][a] = list.get(index);
                    index++;
                } else {
                    break;
                }
            }
        }
        //动态创建表格和单元格
        for (int i=0;i<x;i++){//行
            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            if (i==0){
                TextView cell1 = new TextView(this);
                cell1.setGravity(Gravity.CENTER);
                cell1.setText(productName);
                layoutParams.span = y;
                layoutParams.column = 0;
                cell1.setLayoutParams(layoutParams);
                cell1.setBackgroundResource(R.drawable.table_border);
                row.addView(cell1);
            } else if (i==1){
                TextView cell2 = new TextView(this);
                cell2.setGravity(Gravity.RIGHT);
                cell2.setText("时间：" + data.getDate());
                layoutParams.span = y;
                layoutParams.column = 0;
                cell2.setLayoutParams(layoutParams);
                cell2.setBackgroundResource(R.drawable.table_border);
                row.addView(cell2);
            } else {
                for (int j=0;j<y;j++){//列
                    TextView cell = new TextView(this);
                    cell.setGravity(Gravity.CENTER);
                    if (j==0&&i==2) {
                        cell.setText("电缆号");
                    } else if (i==2&&j==1) {
                        cell.setText("层");
                    } else if (j==1&&i>2) {
                        int k = 0;
                        if ((i-2)%ceng==0){
                            k = ceng;
                        } else {
                            k = (i-2)%ceng;
                        }
                        cell.setText("第"+k+"层");
                    } else if (j==0&&i>2) {
                        cell.setText("第"+(((i-3)/ceng)+1)+"缆");
                    } else if (i==2&&j>1) {
                        cell.setText("第"+(j-1)+"组");
                    } else if (i>2&&j>1){
                        double math = array[i - 3][j - 2];
                        if (255.0 == math){
                            cell.setText("断路");
                            cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        } else {
                            cell.setText(Double.toString(math));
                        }
                    } else {
                        cell.setText(" ");
                    }
                    cell.setBackgroundResource(R.drawable.table_border);
                    row.addView(cell);
                }
            }
            tableLayout.addView(row);
        }

        // 在表格的最后一行添加空的单元格
        TableRow emptyCellRow = new TableRow(this);
        TableRow.LayoutParams emptyCellLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        emptyCellRow.setLayoutParams(emptyCellLayoutParams);
        TextView emptyCell = new TextView(this);
        emptyCell.setGravity(Gravity.CENTER);
        emptyCell.setText(" "); // 设置为空白内容
        emptyCellRow.addView(emptyCell);
        tableLayout.addView(emptyCellRow);

        int xtwo = 4;
        int ytwo = ceng+2;
        // 计算最大值、最小值和平均值
        double max = Double.MIN_VALUE;  // 初始化最大值为最小的可能值
        double min = Double.MAX_VALUE;  // 初始化最小值为最大的可能值
        double sum = 0;  // 求和
        int count = 0;  // 符合条件的元素数量
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != 255) {  // 排除特定值
                    if (array[i][j] > max) {
                        max = array[i][j];
                    }
                    if (array[i][j] < min) {
                        min = array[i][j];
                    }
                    sum += array[i][j];
                    count++;
                }
            }
        }
        //计算每一层的最大最小值和平均值
        double average = sum / count;  // 计算平均值
        for (int i=0;i<xtwo;i++){
            TableRow row = new TableRow(this);
            // 设置单元格的布局参数
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            for (int j=0;j<ytwo;j++){
                TextView cell = new TextView(this);
                cell.setGravity(Gravity.CENTER);
                cell.setLayoutParams(layoutParams);
                cell.setBackgroundResource(R.drawable.table_border_two);
                if (i==0&&j==0){
                    cell.setText("统计");
                } else if (i==1&&j==0) {
                    cell.setText("最高温度");
                    cell.setTextColor(Color.WHITE);
                    cell.setBackgroundColor(Color.RED);
                } else if (i==2&&j==0) {
                    cell.setText("最低温度");
                    cell.setTextColor(Color.WHITE);
                    cell.setBackgroundColor(Color.BLUE);
                } else if (i==3&&j==0) {
                    cell.setText("平均温度");

                    cell.setTextColor(Color.WHITE);
                    cell.setBackgroundColor(Color.GREEN);
                } else if (i==0&&j==1) {
                    cell.setText("全仓");
                } else if (i==0&&j>1) {
                    cell.setText("第"+(j-1)+"层");
                } else if (i==1&&j==1) {
                    if (count>0){
                        cell.setText(Double.toString(max));
                    } else {
                        cell.setText("断路");
                        cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                } else if (i==2&&j==1) {
                    if (count>0){
                        cell.setText(Double.toString(min));
                    } else {
                        cell.setText("断路");
                        cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                } else if (i==3&&j==1) {
                    if (count>0){
                        DecimalFormat decimalFormat = new DecimalFormat("#.0");
                        String formattedNumber = decimalFormat.format(average);
                        cell.setText(formattedNumber);
                    } else {
                        cell.setText("断路");
                        cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                } else if (i==1&&j>1) {
                    double rowIndex = 0;
                    for (int a=0;a<lan;a++){
                        int hang = j-2+(ceng*a);
                        OptionalDouble optionalMax = Arrays.stream(array[hang])
                                .filter(q -> q != 255)
                                .max();
                        if (optionalMax.isPresent()){
                            double maxC = optionalMax
                                    .orElse(Double.NEGATIVE_INFINITY);
                            if (maxC>rowIndex){
                                rowIndex = maxC;
                            }
                            cell.setText(Double.toString(rowIndex));
                        } else {
                            cell.setText("断路");
                            cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        }
                    }
                } else if (i==2&&j>1){
                    double rowIndex = 100;
                    for (int a=0;a<lan;a++){
                        int hang = j-2+(ceng*a);
                        OptionalDouble optionalMin = Arrays.stream(array[hang])
                                .filter(q -> q != 255)
                                .min();
                        if (optionalMin.isPresent()){
                            double minC = optionalMin
                                    .orElse(Double.NEGATIVE_INFINITY);
                            if (minC<rowIndex){
                                rowIndex = minC;
                            }
                            cell.setText(Double.toString(rowIndex));
                        } else {
                            cell.setText("断路");
                            cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        }
                    }
                } else if (i==3&&j>1){
                    double rowIndex = 0;
                    for (int a=0;a<lan;a++){
                        int hang = j-2+(ceng*a);
                        OptionalDouble optionalavg = Arrays
                                .stream(array[hang]).filter(q -> q != 255)
                                .average();
                        if (optionalavg.isPresent()){
                            double avgC = optionalavg
                                    .orElse(Double.NEGATIVE_INFINITY);
                            rowIndex = rowIndex + avgC;
                            rowIndex = rowIndex/lan;
                            DecimalFormat decimalFormat = new DecimalFormat("#.0");
                            String formattedNumber = decimalFormat.format(rowIndex);
                            cell.setText(formattedNumber);
                        } else {
                            cell.setText("断路");
                            cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        }
                    }
                }
                row.addView(cell);
            }
            tableLayout.addView(row);
        }

        // 在表格的最后一行添加空的单元格
        TableRow emptyCellRowtwo = new TableRow(this);
        TableRow.LayoutParams emptyCellLayoutParamstwo = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        emptyCellRowtwo.setLayoutParams(emptyCellLayoutParamstwo);
        TextView emptyCelltwo = new TextView(this);
        emptyCelltwo.setGravity(Gravity.CENTER);
        emptyCelltwo.setText(" "); // 设置为空白内容
        emptyCellRowtwo.addView(emptyCelltwo);
        tableLayout.addView(emptyCellRowtwo);

        //展示温湿水的基本信息
        for (int i=0;i<2;i++){
            TableRow row = new TableRow(this);
            // 设置单元格的布局参数
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            if (i==1){
                TextView cell2 = new TextView(this);
                cell2.setGravity(Gravity.CENTER);
                cell2.setText("备注：温度单位：℃ 湿度单位：%");
                layoutParams.span = 8;
                layoutParams.column = 0;
                cell2.setLayoutParams(layoutParams);
                cell2.setBackgroundResource(R.drawable.table_border);
                row.addView(cell2);
            } else {
                for (int j=0;j<8;j++){
                    TextView cell = new TextView(this);
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
        TableRow emptyCellRowthree = new TableRow(this);
        TableRow.LayoutParams emptyCellLayoutParamsthree = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        emptyCellRowthree.setLayoutParams(emptyCellLayoutParamsthree);
        TextView emptyCellthree = new TextView(this);
        emptyCellthree.setGravity(Gravity.CENTER);
        emptyCellthree.setText(" "); // 设置为空白内容
        emptyCellRowthree.addView(emptyCellthree);
        tableLayout.addView(emptyCellRowthree);

        for (int i=0;i<7;i++){
            TableRow row = new TableRow(this);

            // 设置单元格的布局参数
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            for (int j=0;j<6;j++){
                TextView cell = new TextView(this);
                cell.setGravity(Gravity.CENTER);
                cell.setLayoutParams(layoutParams);
                cell.setBackgroundResource(R.drawable.table_border_two);
                if (i==0&&j==0){
                    cell.setText("粮食种类");
                } else if (i==0&&j==1) {
                    if (tem.get("pinzhongmingcheng").getValue()!=null){
                        cell.setText(tem.get("pinzhongmingcheng").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==0&&j==2) {
                    cell.setText("产地");
                } else if (i==0&&j==3) {
                    if (tem.get("chandi").getValue()!=null){
                        cell.setText(tem.get("chandi").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==0&&j==4) {
                    cell.setText("收获年份");
                } else if (i==0&&j==5) {
                    if (tem.get("shouhuonianfen").getValue()!=null){
                        cell.setText(tem.get("shouhuonianfen").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==1&&j==0) {
                    cell.setText("入仓日期");
                } else if (i==1&&j==1) {
                    if (tem.get("rucangriqi").getValue()!=null){
                        cell.setText(tem.get("rucangriqi").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==1&&j==2) {
                    cell.setText("存储类型");
                } else if (i==1&&j==3) {
                    if (tem.get("cunchuleixing").getValue()!=null){
                        cell.setText(tem.get("cunchuleixing").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==1&&j==4) {
                    cell.setText("实际储量");
                } else if (i==1&&j==5) {
                    if (tem.get("shijichuliang").getValue()!=null){
                        cell.setText(tem.get("shijichuliang").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==2&&j==0) {
                    cell.setText("不完善粒");
                } else if (i==2&&j==1) {
                    if (tem.get("buwanshanli").getValue()!=null){
                        cell.setText(tem.get("buwanshanli").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==2&&j==2) {
                    cell.setText("入仓水分");
                } else if (i==2&&j==3) {
                    if (tem.get("rucangshuifen").getValue()!=null){
                        cell.setText(tem.get("rucangshuifen").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==2&&j==4) {
                    cell.setText("杂质");
                } else if (i==2&&j==5) {
                    if (tem.get("zazhi").getValue()!=null){
                        cell.setText(tem.get("zazhi").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==3&&j==0) {
                    cell.setText("当前水分");
                } else if (i==3&&j==1) {
                    if (tem.get("dangqianshuifen").getValue()!=null){
                        cell.setText(tem.get("dangqianshuifen").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==3&&j==2) {
                    cell.setText("容重/出糙");
                } else if (i==3&&j==3) {
                    if (tem.get("rongzhongchucao").getValue()!=null){
                        cell.setText(tem.get("rongzhongchucao").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==3&&j==4) {
                    cell.setText("堆位");
                } else if (i==3&&j==5) {
                    if (tem.get("duiwei").getValue()!=null){
                        cell.setText(tem.get("duiwei").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==4&&j==0) {
                    cell.setText("高温报警");
                } else if (i==4&&j==1) {

                    if (tem.get("baojingwendu1").getValue()!=null){
                        cell.setText(tem.get("baojingwendu1").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==4&&j==2) {
                    cell.setText("低温报警");
                } else if (i==4&&j==3) {
                    if (tem.get("baojingwendu0").getValue()!=null){
                        cell.setText(tem.get("baojingwendu0").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==4&&j==4) {
                    cell.setText("天气");
                } else if (i==4&&j==5) {
                    if (tem.get("tianqi").getValue()!=null){
                        cell.setText(tem.get("tianqi").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==5&&j==0) {
                    cell.setText("仓库等级");
                } else if (i==5&&j==1) {
                    if (tem.get("dengji").getValue()!=null){
                        cell.setText(tem.get("dengji").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==5&&j==2) {
                    cell.setText("保管员");
                } else if (i==5&&j==3) {
                    if (tem.get("baoguanyuan").getValue()!=null){
                        cell.setText(tem.get("baoguanyuan").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==5&&j==4) {
                    cell.setText("检测员");
                } else if (i==5&&j==5) {
                    if (tem.get("jianceyuan").getValue()!=null){
                        cell.setText(tem.get("jianceyuan").getValue().toString());
                    } else {
                        cell.setText(" ");
                    }
                } else if (i==6) {
                    if (j==0){
                        cell.setText("粮情分析");
                    } else if (j>0){
                        if (tem.get("liangqingfenxi").getValue()!=null){
                            cell.setText(tem.get("liangqingfenxi").getValue().toString());
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
    public interface OnInitCalculateFinishedListener {
        void OnInitCalculateListener(DeviceData DATAONE, Map<String, Value> DATATWO);
    }
    public void initCalculate(String flag, final OnInitCalculateFinishedListener listener) {
        DeviceData DATAONE = new DeviceData();
        Map<String, Value> DATATWO = new HashMap<>();
        for (Data data : ShareProductDetail.ProductDetialList) {
            if (data.getProductName().equals(NAME)) {
                DATATWO = data.getExtraInfo();
            }
        }
        if (flag.equals("温湿度")) {
            for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList) {
                if (data.getGranaryName().equals(NAME)) {
                    DATAONE = data.getTemDeviceData();
                }
            }
            listener.OnInitCalculateListener(DATAONE,DATATWO);
        } else if (flag.equals("温湿水")) {
            for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList) {
                if (data.getGranaryName().equals(NAME)) {
                    DATAONE = data.getHumDeviceData();
                }
            }
            listener.OnInitCalculateListener(DATAONE,DATATWO);
        } else if (flag.equals("气体")) {
            for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList) {
                if (data.getGranaryName().equals(NAME)) {
                    DATAONE = data.getGasDeviceData();
                }
            }
            listener.OnInitCalculateListener(DATAONE,DATATWO);
        } else {
            listener.OnInitCalculateListener(null,null);
        }
    }
    public static List<Double> StrToDouble(List<String> list) {
        if (list!=null && list.size()>0) {
            Log.d("zyq", "StrToDouble: list     "+list);
            List<Double> zyq = new ArrayList<>();
            for (String str : list) {
                Double doubleValue = null;
                try {
                    doubleValue = Double.parseDouble(str);
                    zyq.add(doubleValue);
                } catch (NumberFormatException e) {
                    // 处理解析失败的情况，比如记录日志，跳过这个无法转换的值等
                    zyq.add(255.0);
                    continue;
                }
            }
            Log.d("zyq", "StrToDouble: result   "+zyq);
            return zyq;
        } else {
            return null;
        }
    }
    public static List<String> DoubleToStr(List<Double> list) {
        if (list!=null && list.size()>0) {
            Log.d("zyq", "StrToDouble: list     "+list);
            List<String> zyq = new ArrayList<>();
            for (Double str : list) {
                String doubleValue = null;
                try {
                    doubleValue = String.valueOf(str);
                    zyq.add(doubleValue);
                } catch (NumberFormatException e) {
                    // 处理解析失败的情况，比如记录日志，跳过这个无法转换的值等
                    zyq.add("255.0");
                    continue;
                }
            }
            Log.d("zyq", "StrToDouble: result   "+zyq);
            return zyq;
        } else {
            return null;
        }
    }
    public static long Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public interface OnInitHistoryListFinishedListener {
        void OnInitHistoryListListener(List<DeviceData> result);
    }
    public void initHistoryList(long startTimes, long endTimes, String deviceKey, String flag, final OnInitHistoryListFinishedListener listener) {
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
                util.showToast(HistoryActivity.this,zyq);
                listener.OnInitHistoryListListener(null);
            }
        });
    }
    private void SelectHistoryData(DeviceData deviceData, String finalFlag, final OnInitCalculateFinishedListener listener) {
        DeviceData DATAONE = new DeviceData();
        Map<String, Value> DATATWO = new HashMap<>();
        for (Data data : ShareProductDetail.ProductDetialList) {
            if (data.getProductName().equals(NAME)) {
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
                selfDialog = new SelfDialog(this);
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
                            util.showToast(HistoryActivity.this,"该时间段内并无数据！");
                        } else {
                            String key = null;
                            String flag = null;
                            for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList) {
                                if (data.getGranaryName().equals(name.getText()) && title.getText().equals("温湿度")) {
                                    key = data.getTemDeviceKey();
                                    flag = "温湿度";
                                } else if (data.getGranaryName().equals(name.getText()) && title.getText().equals("温湿水")) {
                                    key = data.getHumDeviceKey();
                                    flag = "温湿水";
                                } else if (data.getGranaryName().equals(name.getText()) && title.getText().equals("气体")) {
                                    key = data.getGasDeviceKey();
                                    flag = "气体";
                                }
                            }
                            //通过时间查询
                            String finalFlag = flag;
                            initHistoryList(longstart, longend, key, flag, new OnInitHistoryListFinishedListener() {
                                @Override
                                public void OnInitHistoryListListener(List<DeviceData> result) {
                                    if (result!=null && result.size()>0) {
                                        List<String> timeList = new ArrayList<>();
                                        for (DeviceData data : result) {
                                            if (data.getDate()!=null) {
                                                timeList.add(data.getDate());
                                            }
                                        }
                                        ArrayAdapter adapter = new ArrayAdapter(HistoryActivity.this,R.layout.simple_list,timeList);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                                        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                if (result.size() > which && result.get(which) != null) {
                                                    SelectHistoryData(result.get(which), finalFlag, new OnInitCalculateFinishedListener() {
                                                        @Override
                                                        public void OnInitCalculateListener(DeviceData DATAONE, Map<String, Value> DATATWO) {
                                                            if (DATAONE!=null && DATATWO!=null) {
                                                                fatherView.setVisibility(View.GONE);
                                                                if (TITLE.equals("温湿度")) {
                                                                    DrawTemTable(DATATWO,DATAONE,NAME);
                                                                    suoPingDialog.dismiss();
                                                                } else if (TITLE.equals("温湿水")) {
                                                                    DrawHumTable(DATATWO,DATAONE,NAME);
                                                                    suoPingDialog.dismiss();
                                                                } else if (TITLE.equals("气体")) {
                                                                    DrawGasTable(DATATWO,DATAONE,NAME);
                                                                    suoPingDialog.dismiss();
                                                                }
                                                            } else {
                                                                suoPingDialog.dismiss();
                                                                fatherView.setVisibility(View.VISIBLE);
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
                                        util.showToast(HistoryActivity.this, "无数据！");
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
                    if (SharedDeviceInfos.DeviceInfosList.get(i).getGranaryName().equals(NAME)) {
                        selectWhich = i;
                    }
                }
                ArrayAdapter adapter_this = new ArrayAdapter<String>(this,R.layout.simple_list,Name);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setSingleChoiceItems(adapter_this, selectWhich, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectWhich = which;
                        NAME = SharedDeviceInfos.DeviceInfosList.get(which).getGranaryName();
                        name.setText(NAME);
                        suoPingDialog = new SuoPingDialog(HistoryActivity.this,"绘制中,请稍等......");
                        suoPingDialog.show();
                        initCalculate(TITLE, new OnInitCalculateFinishedListener() {
                            @Override
                            public void OnInitCalculateListener(DeviceData DATAONE, Map<String, Value> DATATWO) {
                                if (DATAONE!=null && DATATWO!=null) {
                                    fatherView.setVisibility(View.GONE);
                                    if (TITLE.equals("温湿度")) {
                                        DrawTemTable(DATATWO,DATAONE,NAME);
                                        suoPingDialog.dismiss();
                                    } else if (TITLE.equals("温湿水")) {
                                        DrawHumTable(DATATWO,DATAONE,NAME);
                                        suoPingDialog.dismiss();
                                    } else if (TITLE.equals("气体")) {
                                        DrawGasTable(DATATWO,DATAONE,NAME);
                                        suoPingDialog.dismiss();
                                    }
                                } else {
                                    suoPingDialog.dismiss();
                                    fatherView.setVisibility(View.VISIBLE);
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