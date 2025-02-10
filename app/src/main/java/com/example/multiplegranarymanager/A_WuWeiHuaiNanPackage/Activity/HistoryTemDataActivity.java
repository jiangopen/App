package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity;

import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.Token;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.History.HistoryBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.HistoryCountMultipleBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.TimeMultipe;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.SelfDialog;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;

public class HistoryTemDataActivity extends AppCompatActivity implements View.OnClickListener{
    private final String[] TXT_TITLES = new String[]{"温湿度"};
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    private String Name;
    private String deviceType;
    private String JsonData;
    private String token;
    private HistoryTemDataActivity me;
    private String fragment = "1";
    private ArrayList<GranaryListBean.Data> granarylist = new ArrayList<>();
    private TextView TXT_name,TXT_time;
    private ImageView IMG_select;
    private LinearLayout fatherView;
    private TableLayout tabLayout;
    private SelfDialog selfDialog;
    private String CommandMapId,DataType,Date;
    private long Time;
    private HistoryCountMultipleBean.DataContent DataContent;
    int selectWhich = 0;
    private com.example.multiplegranarymanager.Dialog.SuoPingDialog SuoPingDialog;
    GranaryListBean.Data selectProduct;
    private List<HistoryBean.Data> HistoryListData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_data);
        TXT_name = findViewById(R.id.name);
        TXT_time = findViewById(R.id.time);
        IMG_select = findViewById(R.id.select);
        fatherView = findViewById(R.id.fartherView);
        tabLayout = findViewById(R.id.table_layout);
        TXT_time.setOnClickListener(this);
        IMG_select.setOnClickListener(this);

        bundle = getIntent().getBundleExtra("bundle1");
        Name = getIntent().getStringExtra("仓号");
        token = getIntent().getStringExtra("token");
        deviceType = getIntent().getStringExtra("账号");
        JsonData = getIntent().getStringExtra("温度历史数据请求体");
        granarylist = bundle.getParcelableArrayList("粮仓");

        fragment = getIntent().getStringExtra("fragment");

        Log.d("zyq", "onCreate: "+granarylist.size());
        initData(JsonData, new OnInitDataFinishedListener() {
            @Override
            public void OnInitDataListener(boolean success) {
                SuoPingDialog = new SuoPingDialog(HistoryTemDataActivity.this,"加载中,请稍等......");
                OnInitBeforeCalculateTemp(Date, DataContent, new OnInitBeforeCalculateTempFinishedListner() {
                    @Override
                    public void OnInitBeforeCalculateTempListener(boolean success) {
                        SuoPingDialog.dismiss();
                    }
                });
            }
        });

        TXT_name.setText(Name);

    }
    private void showAlertDialog(ArrayList<GranaryListBean.Data> product) {
        Collections.sort(product, (o1, o2) -> o1.getGranaryName().compareTo(o2.getGranaryName()));
        List<String> NAME = new ArrayList<>();
        for (int i=0;i<product.size();i++){
            NAME.add(product.get(i).getGranaryName());
            if (product.get(i).getGranaryName().equals(TXT_name.toString())){
                selectWhich=i;
            }
        }
        Log.d("jht", "showAlertDialog: "+selectWhich);
        ArrayAdapter adapter_this = new ArrayAdapter<String>(this,R.layout.simple_list,NAME);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(adapter_this, selectWhich, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectProduct = product.get(which);
                String cangku = product.get(which).getGranaryName();
                selectWhich = which;
                Name = cangku;
                TXT_name.setText(cangku);
                dialog.dismiss();
            }
        });
        AlertDialog dialogone = builder.create();
        dialogone.show();
        dialogone.setCanceledOnTouchOutside(true);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select:
                ArrayList<GranaryListBean.Data> product = new ArrayList<>();
                for (GranaryListBean.Data data : granarylist) {
                    product.add(data);
                }
                showAlertDialog(product);
                break;
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
                            util.showToast(HistoryTemDataActivity.this,"该时间段内并无数据！");
                        } else {
                            //通过时间查询
                            for (GranaryListBean.Data data : granarylist){
                                if (data.getGranaryName().equals(Name)){
                                    selectProduct = data;
                                    ShowDialog(longstart,longend,selectProduct);
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
    public static long Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    private void ShowDialog(long longstart, long longend, GranaryListBean.Data select) {
        TimeMultipe multipe = new TimeMultipe();
        multipe.setDataType("04");
        multipe.setUrl(select.getUrl());
        multipe.setCommandMapId(select.getCommandMapId());
        multipe.setEndTime(longend);
        multipe.setStartTime(longstart);
        NewDownRawBody body = new NewDownRawBody();
        body.setBody(multipe);
        body.setHeaders("");
        body.setQuery("");
        body.setMethod("POST");
        body.setUrl("/history-data/time");
        String jsonData = gson.toJson(body);
        initDataTwo(jsonData, new OnInitDataTwoFinishedListener() {
            @Override
            public void OnInitDataTwoListener(List<HistoryBean.Data> resultList) {
                if (resultList == null) {
                    util.showToast(HistoryTemDataActivity.this, "该时间段内无记录！");
                    return;
                }
                if (resultList.size()>0) {
                    List<String> date = new ArrayList<>();
                    for (HistoryBean.Data datethis : resultList){
                        if (datethis!= null) {
                            String dateStr = datethis.getDate();
                            if (dateStr!= null) {
                                date.add(dateStr);
                            }
                        }
                    }
                    if (!date.isEmpty()) {
                        ArrayAdapter adapter = new ArrayAdapter(HistoryTemDataActivity.this, R.layout.simple_list, date);
                        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryTemDataActivity.this);
                        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (resultList.size() > which && resultList.get(which)!= null) {
                                    ShowTable(resultList.get(which).getDataContent(),resultList.get(which).getDate());
                                }
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(true);
                    } else {
                        util.showToast(HistoryTemDataActivity.this, "无法提取日期数据！");
                    }
                } else {
                    util.showToast(HistoryTemDataActivity.this,"该时间段内无记录！");
                }
            }
        });
    }
    public interface OnInitDataTwoFinishedListener {
        void OnInitDataTwoListener(List<HistoryBean.Data> resultList);
    }
    private void initDataTwo(String jsonData, final OnInitDataTwoFinishedListener listener){
        OkHttpUtil.PostNewDownRaw("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=30", jsonData, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                HistoryBean historyBean = new HistoryBean();
                try {
                    historyBean = gson.fromJson((String) result,HistoryBean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    util.showToast(HistoryTemDataActivity.this,"产品解析出问题");
                }
                if (historyBean.getData()!=null && historyBean.getData().size()>0) {
                    HistoryListData.clear();
                    for (HistoryBean.Data data : historyBean.getData()) {
                        HistoryListData.add(data);
                    }
                }
                listener.OnInitDataTwoListener(HistoryListData);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitDataTwoListener(null);
                util.showToast(HistoryTemDataActivity.this,errorMsg);
            }
        });
    }

    private void ShowTable(HistoryCountMultipleBean.DataContent dataContent, String date) {
        SuoPingDialog = new SuoPingDialog(this,"正在加载,请稍等......");
        SuoPingDialog.show();
        if (dataContent!=null){
            OnInitBeforeCalculateTemp(date,dataContent,new OnInitBeforeCalculateTempFinishedListner() {
                @Override
                public void OnInitBeforeCalculateTempListener(boolean success) {
                    SuoPingDialog.dismiss();
                }
            });
        } else {
            util.showToast(this,"列表中无数据！");
            SuoPingDialog.dismiss();
        }
    }
    public interface OnInitBeforeCalculateTempFinishedListner {
        void OnInitBeforeCalculateTempListener(boolean success);
    }
    public void OnInitBeforeCalculateTemp(String date, HistoryCountMultipleBean.DataContent tempList, final OnInitBeforeCalculateTempFinishedListner listner){
        int cen = tempList.getGranarySetting().getCeng();
        int lan = tempList.getGranarySetting().getLan();
        int z_u = tempList.getGranarySetting().getZu();
        String type = tempList.getGranarySetting().getType();
        tabLayout.removeAllViews();
        // 设置表格背景样式
        tabLayout.setBackgroundResource(R.drawable.table_border);
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
                    cell1.setText(Name);
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
                tabLayout.addView(row);
            }

            // 在表格的最后一行添加空的单元格
            TableRow emptyCellRow = new TableRow(this);
            TableRow.LayoutParams emptyCellLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            emptyCellRow.setLayoutParams(emptyCellLayoutParams);
            TextView emptyCell = new TextView(this);
            emptyCell.setGravity(Gravity.CENTER);
            emptyCell.setText(" "); // 设置为空白内容
            emptyCellRow.addView(emptyCell);
            tabLayout.addView(emptyCellRow);

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
                tabLayout.addView(row);
            }
            // 在表格的最后一行添加空的单元格
            TableRow emptyCellRowtwo = new TableRow(this);
            TableRow.LayoutParams emptyCellLayoutParamstwo = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            emptyCellRowtwo.setLayoutParams(emptyCellLayoutParamstwo);
            TextView emptyCelltwo = new TextView(this);
            emptyCelltwo.setGravity(Gravity.CENTER);
            emptyCelltwo.setText(" "); // 设置为空白内容
            emptyCellRowtwo.addView(emptyCelltwo);
            tabLayout.addView(emptyCellRowtwo);

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
                tabLayout.addView(row);
            }
            listner.OnInitBeforeCalculateTempListener(true);
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
                    cell1.setText(Name);
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
                tabLayout.addView(row);
            }

            //在表格的最后一行添加空的单元格
            TableRow emptyCellRow = new TableRow(this);
            TableRow.LayoutParams emptyCellLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            emptyCellRow.setLayoutParams(emptyCellLayoutParams);
            TextView emptyCell = new TextView(this);
            emptyCell.setGravity(Gravity.CENTER);
            emptyCell.setText(" "); // 设置为空白内容
            emptyCellRow.addView(emptyCell);
            tabLayout.addView(emptyCellRow);

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
                tabLayout.addView(row);
            }

            // 在表格的最后一行添加空的单元格
            TableRow emptyCellRowtwo = new TableRow(this);
            TableRow.LayoutParams emptyCellLayoutParamstwo = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            emptyCellRowtwo.setLayoutParams(emptyCellLayoutParamstwo);
            TextView emptyCelltwo = new TextView(this);
            emptyCelltwo.setGravity(Gravity.CENTER);
            emptyCelltwo.setText(" "); // 设置为空白内容
            emptyCellRowtwo.addView(emptyCelltwo);
            tabLayout.addView(emptyCellRowtwo);

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
                tabLayout.addView(row);
            }
            listner.OnInitBeforeCalculateTempListener(true);
        } else {
            listner.OnInitBeforeCalculateTempListener(false);
        }
    }
    public interface OnInitDataFinishedListener {
        void OnInitDataListener(boolean success);
    }
    public void initData(String jsonData, final OnInitDataFinishedListener listener){
        Log.d("zyq", "api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=30"+jsonData);
        OkHttpUtil.PostNewDownRaw("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=30", jsonData, token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                HistoryBean historyBean = new HistoryBean();
                try {
                    historyBean = gson.fromJson((String) result,HistoryBean.class);
                    Log.d("zyq", "onReqSuccess: "+result.toString());
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    util.showToast(HistoryTemDataActivity.this,"产品解析出错");
                }
                Log.d("zyq", "getData: "+historyBean.getData().size());
                if (historyBean.getData()!=null && historyBean.getData().size()>0) {
//                    bundle.putParcelable("dataContent",historyBean.getData().get(0).getDataContent());
                    DataContent = historyBean.getData().get(0).getDataContent();
//                    bundle.putString("commandMapId",historyBean.getData().get(0).getCommandMapId());
                    CommandMapId = historyBean.getData().get(0).getCommandMapId();
//                    bundle.putString("dataType",historyBean.getData().get(0).getDataType());
                    DataType = historyBean.getData().get(0).getDataType();
//                    bundle.putString("date",historyBean.getData().get(0).getDate());
                    Date = historyBean.getData().get(0).getDate();
//                    bundle.putLong("time",historyBean.getData().get(0).getTime());
//                    bundle.putString("name",Name);
//                    bundle.putParcelableArrayList("粮仓",granarylist);
                } else {
                    util.showToast(HistoryTemDataActivity.this,"当前无产品");
                }
                listener.OnInitDataListener(true);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitDataListener(false);
                util.showToast(HistoryTemDataActivity.this,errorMsg);
            }
        });
    }
}