package com.example.multiplegranarymanager.A_ShuChengPackage.Fragment;

import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.HistoryActivity.Date2TimeStamp;
import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.HistoryActivity.StrToDouble;
import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.Token;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

public class TemFragment extends Fragment implements View.OnClickListener{
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
        View view = inflater.inflate(R.layout.fragment_tem, container, false);
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
                    DrawTemTable(DATATWO,DATAONE,Flag);
                    suoPingDialog.dismiss();
                } else {
                    suoPingDialog.dismiss();
                    fartherView.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
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
            TableRow row = new TableRow(getContext());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            if (i==0){
                TextView cell1 = new TextView(getContext());
                cell1.setGravity(Gravity.CENTER);
                cell1.setText(productName);
                layoutParams.span = y;
                layoutParams.column = 0;
                cell1.setLayoutParams(layoutParams);
                cell1.setBackgroundResource(R.drawable.table_border);
                row.addView(cell1);
            } else if (i==1){
                TextView cell2 = new TextView(getContext());
                cell2.setGravity(Gravity.RIGHT);
                cell2.setText("时间：" + data.getDate());
                layoutParams.span = y;
                layoutParams.column = 0;
                cell2.setLayoutParams(layoutParams);
                cell2.setBackgroundResource(R.drawable.table_border);
                row.addView(cell2);
            } else {
                for (int j=0;j<y;j++){//列
                    TextView cell = new TextView(getContext());
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
        TableRow emptyCellRow = new TableRow(getContext());
        TableRow.LayoutParams emptyCellLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        emptyCellRow.setLayoutParams(emptyCellLayoutParams);
        TextView emptyCell = new TextView(getContext());
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
            TableRow row = new TableRow(getContext());
            // 设置单元格的布局参数
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.width = TableRow.LayoutParams.MATCH_PARENT; // 设置宽度为200像素
            layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT; // 设置高度为自适应内容
            layoutParams.weight = 1; // 设置权重，用于控制单元格在行中的比例
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            for (int j=0;j<ytwo;j++){
                TextView cell = new TextView(getContext());
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
                DATAONE = data.getTemDeviceData();
            }
        }
        listener.OnInitCalculateListener(DATAONE,DATATWO);
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
                                    key = data.getTemDeviceKey();
                                    flag = "温湿度";
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
                                                                DrawTemTable(DATATWO,DATAONE,Flag);
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
                                    DrawTemTable(DATATWO,DATAONE,Flag);
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