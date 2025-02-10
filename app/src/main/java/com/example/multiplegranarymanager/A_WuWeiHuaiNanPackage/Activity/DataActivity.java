package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.HistoryCountMultipleBean;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.OptionalDouble;

public class DataActivity extends AppCompatActivity {
    private TextView TXT_name;
    private LinearLayout fatherView;
    private TableLayout tableLayout;
    private Bundle bundle = new Bundle();
    private Util1 util = new Util1();
    private Gson gson = new Gson();
    private SuoPingDialog suoPingDialog;
    private HistoryCountMultipleBean.DataContent DataContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        TXT_name = findViewById(R.id.name);
        fatherView = findViewById(R.id.fartherView);
        tableLayout = findViewById(R.id.table_layout);
        bundle = getIntent().getBundleExtra("bundle2");
        DataContent = bundle.getParcelable("数据");
        TXT_name.setText(DataContent.getGranarySetting().getName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String date = sdf.format(now);
        suoPingDialog = new SuoPingDialog(this,"加载中,请稍等......");
        suoPingDialog.show();
        initDrawTable(date, DataContent, new OnInitDrawTableFinishedListener() {
            @Override
            public void OnInitDrawTableListener(boolean success) {
                suoPingDialog.dismiss();
            }
        });
    }
    public interface OnInitDrawTableFinishedListener {
        void OnInitDrawTableListener(boolean success);
    }
    public void initDrawTable(String date, HistoryCountMultipleBean.DataContent tempList,final OnInitDrawTableFinishedListener listener) {
        int cen = tempList.getGranarySetting().getCeng();
        int lan = tempList.getGranarySetting().getLan();
        int z_u = tempList.getGranarySetting().getZu();
        String type = tempList.getGranarySetting().getType();
        tableLayout.removeAllViews();
        // 设置表格背景样式
        tableLayout.setBackgroundResource(R.drawable.table_border);
        if (type.equals("PFC")) {
            int x = (lan*cen)+3;
            //获取列数
            int y = z_u + 2;
            //按照数据创建二维数组
            double[][] array = new double[x-3][z_u];
            int index = 0;//数据列表的索引
            for (int a=0;a<z_u;a++){
                for (int b=0;b<x-3;b++){
                    if (index<tempList.getTempList().size()){
                        array[b][a] = tempList.getTempList().get(index);
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
                    cell1.setText(tempList.getGranarySetting().getName());
                    layoutParams.span = y;
                    layoutParams.column = 0;
                    cell1.setLayoutParams(layoutParams);
                    cell1.setBackgroundResource(R.drawable.table_border);
                    row.addView(cell1);
                } else if (i==1){
                    TextView cell2 = new TextView(this);
                    cell2.setGravity(Gravity.RIGHT);
                    cell2.setText("时间：" + date);
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
                            if ((i-2)%cen==0){
                                k = cen;
                            } else {
                                k = (i-2)%cen;
                            }
                            cell.setText("第"+k+"层");
                        } else if (j==0&&i>2) {
                            cell.setText("第"+(((i-3)/cen)+1)+"缆");
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
            int ytwo = cen+2;
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
                            int hang = j-2+(cen*a);
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
                            int hang = j-2+(cen*a);
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
                            int hang = j-2+(cen*a);
                            OptionalDouble optionalavg = Arrays
                                    .stream(array[hang]).filter(q -> q != 255)
                                    .average();

                            if (optionalavg.isPresent()){
                                double avgC = optionalavg
                                        .orElse(Double.NEGATIVE_INFINITY);
//                                rowIndex = rowIndex + avgC;
//                                rowIndex = rowIndex/lan;
                                DecimalFormat decimalFormat = new DecimalFormat("#.0");
//                                String formattedNumber = decimalFormat.format(rowIndex);
                                String formattedNumber = decimalFormat.format(avgC);
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
                            if (tempList.getTempInner()!=null){
                                cell.setText(tempList.getTempInner()+"℃");
                            } else {
                                cell.setText("————");
                            }
                        } else if (i==0&&j==2) {
                            cell.setText("室内湿度");
                        } else if (i==0&&j==3) {
                            if (tempList.getHumidityInner()!=null){
                                cell.setText(tempList.getHumidityInner()+"%");
                            } else {
                                cell.setText("————");
                            }
                        } else if (i==0&&j==4) {
                            cell.setText("室外温度");
                        } else if (i==0&&j==5) {
                            if (tempList.getTempOut()!=null){
                                cell.setText(tempList.getTempOut()+"℃");
                            } else {
                                cell.setText("————");
                            }
                        } else if (i==0&&j==6) {
                            cell.setText("室外湿度");
                        } else if (i==0&&j==7) {
                            if (tempList.getHumidityOut()!=null){
                                cell.setText(tempList.getHumidityOut()+"%");
                            } else {
                                cell.setText("————");
                            }
                        }
                        row.addView(cell);
                    }
                }
                tableLayout.addView(row);
            }
            listener.OnInitDrawTableListener(true);
        } else if (type.equals("TC")) {
            //获取行数
            int x = z_u+3;
            //获取列数
            int y = cen+1;
            //按照数据创建二维数组
            double[][] array = new double[z_u][cen];
            int index = 0;//数据列表的索引
            for (int a=0;a<cen;a++){
                for (int b=0;b<z_u;b++){
                    if (index<tempList.getTempList().size()){
                        array[b][a] = tempList.getTempList().get(index);
                        index++;
                    } else {
                        break;
                    }
                }
            }
            //动态创建表格和单元格
            for (int i=0;i<x;i++){//行
                TableRow row = new TableRow(this);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(layoutParams);
                row.removeAllViews();//移除上一次循环添加的单元格
                if (i==0){
                    TextView cell1 = new TextView(this);
                    cell1.setGravity(Gravity.CENTER);
                    cell1.setText(tempList.getGranarySetting().getName());
                    layoutParams.span = y;
                    layoutParams.column = 0;
                    cell1.setLayoutParams(layoutParams);
                    cell1.setBackgroundResource(R.drawable.table_border);
                    row.addView(cell1);
                } else if (i==1) {
                    TextView cell2 = new TextView(this);
                    cell2.setGravity(Gravity.RIGHT);
                    cell2.setText("时间："+ date);
                    layoutParams.span = y;
                    layoutParams.column = 0;
                    cell2.setLayoutParams(layoutParams);
                    cell2.setBackgroundResource(R.drawable.table_border);
                    row.addView(cell2);
                } else {
                    for (int j=0;j<y;j++){
                        TextView cell = new TextView(this);
                        cell.setGravity(Gravity.CENTER);
                        if (j==0&&i==2){
                            cell.setText("电缆号");
                        } else if (j==0&&i>2){
                            cell.setText("第"+((i-3)+1)+"根");
                        } else if (i==2&&j>0) {
                            cell.setText("第"+(j)+"层");
                        } else if (i>2&&j>0){
                            double math = array[i - 3][j - 1];
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

            //在表格的最后一行添加空的单元格
            TableRow emptyCellRow = new TableRow(this);
            TableRow.LayoutParams emptyCellLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            emptyCellRow.setLayoutParams(emptyCellLayoutParams);
            TextView emptyCell = new TextView(this);
            emptyCell.setGravity(Gravity.CENTER);
            emptyCell.setText(" "); // 设置为空白内容
            emptyCellRow.addView(emptyCell);
            tableLayout.addView(emptyCellRow);

            int xtwo = 4;
            int ytwo = cen + 2;
            double max = tempList.getTempList().stream().reduce(Double.MAX_VALUE,(a,b)->a<b?a:b);
            double min = tempList.getTempList().stream().reduce(Double.MIN_VALUE,(a,b)->a>b?a:b);
            double sum = 0;//求和
            int count = 0;//符合条件的元素数量
            for (int i=0;i < array.length;i++){
                for (int j=0;j<array[i].length;j++){
                    if (array[i][j]!=255){//排除特定值
                        if (array[i][j] > max){
                            max = array[i][j];
                        }
                        if (array[i][j] < min){
                            min = array[i][j];
                        }
                        sum += array[i][j];
                        count++;
                    }
                }
            }
            //计算每一层的最大最小值和平均值
            double average = sum / count;//计算平均值
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
                        double[] arrayList = new double[z_u];
                        for (int middle=0;middle<z_u;middle++){
                            arrayList[middle] = array[middle][j-2];
                        }
                        OptionalDouble optionalMax = Arrays.stream(arrayList)
                                .filter(q -> q != 255)
                                .max();
                        if (optionalMax.isPresent()){
                            double maxc = optionalMax.orElse(Double.NEGATIVE_INFINITY);
                            if (maxc>rowIndex){
                                rowIndex = maxc;
                            }
                            cell.setText(Double.toString(rowIndex));
                        } else {
                            cell.setText("断路");
                            cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        }
                    } else if (i==2&&j>1){
                        double rowIndex = 100;
                        double[] arrayList = new double[z_u];
                        for (int middle=0;middle<z_u;middle++){
                            arrayList[middle] = array[middle][j-2];
                        }
                        OptionalDouble optionalMin = Arrays.stream(arrayList)
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
                    } else if (i==3&&j>1){
                        double rowIndex = 0;
                        double[] arrayList = new double[z_u];
                        for (int middle=0;middle<z_u;middle++){
                            arrayList[middle] = array[middle][j-2];
                        }
                        OptionalDouble optionalavg = Arrays
                                .stream(arrayList).filter(q -> q != 255)
                                .average();
                        if (optionalavg.isPresent()){
                            double avgC = optionalavg
                                    .orElse(Double.NEGATIVE_INFINITY);
                            rowIndex = rowIndex + avgC;
                            DecimalFormat decimalFormat = new DecimalFormat("#.0");
                            String formattedNumber = decimalFormat.format(rowIndex);
                            cell.setText(formattedNumber);
                        } else {
                            cell.setText("断路");
                            cell.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
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
                //设置单元格的布局参数
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
                            if (tempList.getTempInner()!=null&&tempList.getTempInner().equals("255")){
                                cell.setText(tempList.getTempInner()+"℃");
                            } else {
                                cell.setText("————");
                            }
                        } else if (i==0&&j==2) {
                            cell.setText("室内湿度");
                        } else if (i==0&&j==3) {
                            if (tempList.getHumidityInner()!=null&&tempList.getHumidityInner().equals("255")){
                                cell.setText(tempList.getHumidityInner()+"%");
                            } else {
                                cell.setText("————");
                            }
                        } else if (i==0&&j==4) {
                            cell.setText("室外温度");
                        } else if (i==0&&j==5) {
                            if (tempList.getTempOut()!=null&&tempList.getTempOut().equals("255")){
                                cell.setText(tempList.getTempOut()+"℃");
                            } else {
                                cell.setText("————");
                            }
                        } else if (i==0&&j==6) {
                            cell.setText("室外湿度");
                        } else if (i==0&&j==7) {
                            if (tempList.getHumidityOut()!=null&&tempList.getHumidityOut().equals("255")){
                                cell.setText(tempList.getHumidityOut()+"%");
                            } else {
                                cell.setText("————");
                            }
                        }
                        row.addView(cell);
                    }
                }
                tableLayout.addView(row);
            }
            listener.OnInitDrawTableListener(true);
        } else {
            listener.OnInitDrawTableListener(false);
        }
    }
}