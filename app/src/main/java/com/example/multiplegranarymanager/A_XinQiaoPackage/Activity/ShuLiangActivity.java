package com.example.multiplegranarymanager.A_XinQiaoPackage.Activity;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.HistoryTemDataActivity.Date2TimeStamp;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.WindActivity.intToHex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.CommandMapBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.GranarySettingData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.CommandMap.HardwareListData;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.History.HistoryBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.HistoryCountMultipleBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ShuLiangTestInTime22Bean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.ShuLiangTestInTimeBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.NewDownRawMultipleBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.ShuLiangBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.SmartLockBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.TimeMultipe;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindAddressBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.Granary.GranaryListBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.SelfDialog;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ShuLiangActivity extends AppCompatActivity implements View.OnClickListener{
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    private String Name;
    private String JsonData;
    private ArrayList<GranaryListBean.Data> granarylist = new ArrayList<>();
    private TextView TXT_name,TXT_time,TXT_test,TXT_params;
    private ImageView IMG_select;
    private TableLayout tabLayout;
    private LinearLayout fatherView;
    private String commandMapId;
    int selectWhich = 0;
    private String url;
    private SelfDialog selfDialog;
    GranaryListBean.Data selectProduct;
    private String TEST_01 = "",TEST_02 = "",PARAMS = "",SUBMIT = "";
    private Double ALL = 0.0;
    private GranarySettingData granarySettingData = new GranarySettingData();
    private com.example.multiplegranarymanager.Dialog.SuoPingDialog suoPingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shu_liang);
        TXT_name = findViewById(R.id.name);
        TXT_time = findViewById(R.id.time);
        TXT_test = findViewById(R.id.txt_test);
        TXT_params = findViewById(R.id.txt_params);
        IMG_select = findViewById(R.id.txt_select);
        TXT_time.setOnClickListener(this);
        TXT_test.setOnClickListener(this);
        IMG_select.setOnClickListener(this);
        TXT_params.setOnClickListener(this);
        tabLayout = findViewById(R.id.table_layout);
        bundle = getIntent().getBundleExtra("bundle");
        Name = bundle.getString("granaryName");
        JsonData = bundle.getString("数量请求体");
        granarylist = bundle.getParcelableArrayList("粮仓");
        commandMapId = bundle.getString("commandMapId");
        url = bundle.getString("url");
        TXT_name.setText(Name);
        suoPingDialog = new SuoPingDialog(this,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        initShuLiang(JsonData, new OnInitShuLiangDataFinishedListener() {
            @Override
            public void OnInitShuLiangListener(boolean success) {
                if (success) {
                    NewDownRawBodyTWO historyCountMultipleBody = new NewDownRawBodyTWO();
                    historyCountMultipleBody.setUrl("/history-data/count");
                    historyCountMultipleBody.setMethod("POST");
                    historyCountMultipleBody.setHeaders(null);
                    historyCountMultipleBody.setQuery(null);
                    CountMultiple multiple = new CountMultiple();
                    multiple.setCount(1);
                    multiple.setDataType("23_");
                    multiple.setUrl(url);
                    multiple.setCommandMapId(commandMapId);
                    historyCountMultipleBody.setBody(multiple);
                    String jsondata = gson.toJson(historyCountMultipleBody);
                    initTest(jsondata, new OnInitTestDataFinishedListener() {
                        @Override
                        public void OnInitTestListener(boolean success, ShuLiangBody body) {
//                            suoPingDialog.dismiss();
//                            DrawTable(body);
                            if (success && body!=null) {
                                suoPingDialog.dismiss();
                                Log.d("jht", "OnInitTestListener: here01");
                                DrawTable(body);
                            } else {
                                suoPingDialog.dismiss();
                            }
                        }
                    });
                } else {
                    suoPingDialog.dismiss();
                }
            }
        });
    }
    private void DrawTable(ShuLiangBody body) {
        tabLayout.removeAllViews();
        // 设置表格背景样式
        tabLayout.setBackgroundResource(R.drawable.table_border);
        int x = 4;//获取行
        int y = 6;//获取列
        for (int i=0;i<x;i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);
            row.removeAllViews(); // 移除上一次循环添加的单元格
            if (i==0){
                TextView cell1 = new TextView(this);
                cell1.setGravity(Gravity.CENTER);
                cell1.setText(Name+"各部分参数");
                layoutParams.span = y;
                layoutParams.column = 0;
                cell1.setLayoutParams(layoutParams);
                cell1.setBackgroundResource(R.drawable.table_border);
                row.addView(cell1);
            } else {
                for (int j=0;j<y;j++) {
                    TextView cell = new TextView(this);
                    cell.setGravity(Gravity.CENTER);
                    if (i==1 && j==0) {
                        cell.setText("高度");
                    } else if (i==1 && j==1) {
                        if (body.getHeight() != null) {
                            cell.setText(body.getHeight().toString()+"mm");
                        } else {
                            cell.setText("——");
                        }
                    } else if (i==1 && j==2) {
                        cell.setText("体积");
                    } else if (i==1 && j==3) {
                        if (body.getVolume() != null) {
                            cell.setText(body.getVolume().toString()+"m³");
                        } else {
                            cell.setText("——");
                        }

                    } else if (i==1 && j==4) {
                        cell.setText("记录时间");
                    } else if (i==1 && j==5) {
                        if (body.getDate() != null) {
                            cell.setText(body.getDate());
                        } else {
                            cell.setText("——");
                        }
                    } else if (i==2 && j==0) {
                        cell.setText("粮食种类");
                    } else if (i==2 && j==1) {
                        if (body.getGrainType() != null) {
                            cell.setText(body.getGrainType());
                        } else {
                            cell.setText("——");
                        }
                    } else if (i==2 && j==2) {
                        cell.setText("粮仓类型");
                    } else if (i==2 && j==3) {
                        if (body.getType().equals("PFC")) {
                            cell.setText("平房仓");
                        } else if (body.getType().equals("TC")) {
                            cell.setText("筒仓");
                        } else {
                            cell.setText("——");
                        }
                    } else if (i==2 && j==4) {
                        cell.setText("保管员");
                    } else if (i==2 && j==5) {
                        if (body.getBaoguanyuan() != null) {
                            cell.setText(body.getBaoguanyuan());
                        } else {
                            cell.setText("——");
                        }
                    } else if (i==3 && j==0) {
                        cell.setText("总容量");
                    } else if (i==3 && j==1) {
                        if (body.getAll_v() != null) {
                            cell.setText(body.getAll_v().toString()+"吨");
                        } else {
                            cell.setText("——");
                        }
                    } else if (i==3 && j==2) {
                        cell.setText("已用容量");
                    } else if (i==3 && j==3) {
                        if (body.getMass() != null) {
                            cell.setText(body.getMass().toString()+"吨");
                        } else {
                            cell.setText("——");
                        }
                    } else if (i==3 && j==4) {
                        cell.setText("剩余容量");
                    } else if (i==3 && j==5) {
                        if (body.getCha_v() != null) {
                            cell.setText(body.getCha_v().toString()+"吨");
                        } else {
                            cell.setText("——");
                        }
                    }
                    cell.setBackgroundResource(R.drawable.table_border);
                    row.addView(cell);
                }
            }
            tabLayout.addView(row);
        }
    }
    public interface OnInitTestTimeFinishedListener{
        void OnInitTestTimeListener(ArrayList<HistoryBean.Data> bodylist);
    }
    public void initTestTime(String jsondata, final OnInitTestTimeFinishedListener listener) {
        OkHttpUtilTWO.PostNewDownRawTWO("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                HistoryBean historyBean = new HistoryBean();
                try {
                    historyBean = gson.fromJson((String) result,HistoryBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitTestTimeListener(null);
                    e.printStackTrace();
                    util.showToast(ShuLiangActivity.this,"产品解析出错");
                }
                if (Name.equals("P31仓")) {
                    ALL = 6550.0;
                } else if (Name.equals("P32仓")) {
                    ALL = 7400.0;
                } else if (Name.equals("P33仓")) {
                    ALL = 6550.0;
                } else if (Name.equals("P34仓")) {
                    ALL = 7400.0;
                } else if (Name.equals("P35仓")) {
                    ALL = 6550.0;
                } else if (Name.equals("P36仓")) {
                    ALL = 6550.0;
                }
                if (historyBean.getData()!=null && historyBean.getData().size()>0) {
                    ArrayList<HistoryBean.Data> bodylist = new ArrayList<>();
                    for (HistoryBean.Data data : historyBean.getData()) {
                        bodylist.add(data);
                    }
                    listener.OnInitTestTimeListener(bodylist);
                } else {
                    listener.OnInitTestTimeListener(null);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitTestTimeListener(null);
                util.showToast(ShuLiangActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitTestDataFinishedListener {
        void OnInitTestListener(boolean success, ShuLiangBody body);
    }
    public void initTest(String jsondata, final OnInitTestDataFinishedListener listener) {
        Log.d("jht", "initTest: "+ jsondata);
        OkHttpUtilTWO.PostNewDownRawTWO("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                HistoryBean historyBean = new HistoryBean();
                try {
                    historyBean = gson.fromJson((String) result,HistoryBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitTestListener(false,null);
                    e.printStackTrace();
                    util.showToast(ShuLiangActivity.this,"产品解析出错");
                }
                if (Name.equals("P31仓")) {
                    ALL = 6550.0;
                } else if (Name.equals("P32仓")) {
                    ALL = 7400.0;
                } else if (Name.equals("P33仓")) {
                    ALL = 6550.0;
                } else if (Name.equals("P34仓")) {
                    ALL = 7400.0;
                } else if (Name.equals("P35仓")) {
                    ALL = 6550.0;
                } else if (Name.equals("P36仓")) {
                    ALL = 6550.0;
                }
                if (historyBean.getData()!=null && historyBean.getData().size()>0) {
                    if (historyBean.getData().get(0) != null) {
                        if (historyBean.getData().get(0).getDataContent()!=null) {
                            ShuLiangBody shuLiangBody = new ShuLiangBody(
                                    Name,
                                    historyBean.getData().get(0).getDataContent().getHeight(),
                                    historyBean.getData().get(0).getDataContent().getMass(),
                                    historyBean.getData().get(0).getDataContent().getVolume(),
                                    historyBean.getData().get(0).getDate(),
                                    granarySettingData.getInfoCard().getBaoguanyuan(),
                                    granarySettingData.getGrainType(),
                                    ALL,
                                    ALL-historyBean.getData().get(0).getDataContent().getMass(),
                                    granarySettingData.getType()
                            );
                            listener.OnInitTestListener(true,shuLiangBody);
                        } else {
                            listener.OnInitTestListener(false,null);
                        }
                    } else {
                        listener.OnInitTestListener(false,null);
                    }
                } else {
                    listener.OnInitTestListener(false,null);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitTestListener(false,null);
                util.showToast(ShuLiangActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitShuLiangDataFinishedListener {
        void OnInitShuLiangListener(boolean success);
    }
    public void initShuLiang(String jsondata, final OnInitShuLiangDataFinishedListener listener) {
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                CommandMapBean commandMapBean = new CommandMapBean();
                try {
                    commandMapBean = gson.fromJson((String) result,CommandMapBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitShuLiangListener(false);
                    e.printStackTrace();
                    util.showToast(ShuLiangActivity.this,commandMapBean.getMsg());
                    return;
                }
                if (commandMapBean.getData().getData()!=null) {
                    if (commandMapBean.getData().getData().getCommandList()!=null && commandMapBean.getData().getData().getCommandList().size()>0) {
                        for (CommandListData data : commandMapBean.getData().getData().getCommandList()) {
                            if (data.getInspurControlType().equals("20")) {
                                TEST_01 = data.getInspurExtensionAddress();
                            }
                            if (data.getInspurControlType().equals("22")) {
                                TEST_02 = data.getInspurExtensionAddress();
                            }
                            if (data.getInspurControlType().equals("26")) {
                                PARAMS = data.getInspurExtensionAddress();
                            }
                            if (data.getInspurControlType().equals("28")) {
                                SUBMIT = data.getInspurExtensionAddress();
                            }
                        }
                    }
                    if (commandMapBean.getData().getData().getGranarySetting()!=null) {
                        granarySettingData = commandMapBean.getData().getData().getGranarySetting();
                    }
//                    if (commandMapBean.getData().getData().getHardwareList()!=null&&commandMapBean.getData().getData().getHardwareList().size()>0) {
//                        for (HardwareListData data : commandMapBean.getData().getData().getHardwareList()) {
//                            if (data.getType().equals("03")) {
//                                WindAddressBody windAddressBody = new WindAddressBody(
//                                        data.getInspurAddress()
//                                );
//                                lightbody.add(windAddressBody);
//                            }
//                        }
//                    }
                }
                listener.OnInitShuLiangListener(true);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitShuLiangListener(true);
                util.showToast(ShuLiangActivity.this,errorMsg);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time:
//                selfDialog = new SelfDialog(this);
//                selfDialog.setYesOnclickListener("提交", new SelfDialog.onYesOnclickListener() {
//                    @Override
//                    public void onYesClick() {
//                        selfDialog.dismiss();
//                        //提交日期操作从日历获得
//                        long longstart = Date2TimeStamp(selfDialog.getStartTime(),"yyyy-MM-dd HH:mm:ss");
//                        long longend = Date2TimeStamp(selfDialog.getEndTime(),"yyyy-MM-dd HH:mm:ss");
//                        //设置今天最晚的时间，startTime最小不得大过这个时间
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.set(Calendar.HOUR_OF_DAY,23);
//                        calendar.set(Calendar.MINUTE,59);
//                        calendar.set(Calendar.SECOND,59);
//                        //获取今天最晚时间的毫秒数
//                        long todayEndTimeInMillis = calendar.getTimeInMillis();
//                        if (longstart>=todayEndTimeInMillis){
//                            util.showToast(ShuLiangActivity.this,"该时间段内并无数据！");
//                        } else {
//                            //通过时间查询
//                            for (GranaryListBean.Data data : granarylist){
//                                if (data.getGranaryName().equals(Name)){
//                                    selectProduct = data;
//                                    ShowDialog(longstart,longend,selectProduct);
//                                }
//                            }
//                        }
//                    }
//                });
//                selfDialog.show();
                suoPingDialog = new SuoPingDialog(this,"正在操作，请稍等......");
                suoPingDialog.setCancelable(true);
                suoPingDialog.show();
                NewDownRawBodyTWO historyCountMultipleBody = new NewDownRawBodyTWO();
                historyCountMultipleBody.setUrl("/history-data/count");
                historyCountMultipleBody.setMethod("POST");
                historyCountMultipleBody.setHeaders(null);
                historyCountMultipleBody.setQuery(null);
                CountMultiple multiple = new CountMultiple();
                multiple.setCount(10);
                multiple.setDataType("23_");
                multiple.setUrl(url);
                multiple.setCommandMapId(commandMapId);
                historyCountMultipleBody.setBody(multiple);
                String jsondata = gson.toJson(historyCountMultipleBody);
                initTestTime(jsondata, new OnInitTestTimeFinishedListener() {
                    @Override
                    public void OnInitTestTimeListener(ArrayList<HistoryBean.Data> bodylist) {
                        suoPingDialog.dismiss();
                        if (bodylist == null) {
                            util.showToast(ShuLiangActivity.this, "无记录！");
                            return;
                        }
                        if (bodylist.size()>0) {
                            List<String> date = new ArrayList<>();
                            for (HistoryBean.Data data : bodylist) {
                                String dateStr = data.getDate();
                                if (dateStr!= null) {
                                    date.add(dateStr);
                                }
                            }
                            if (!date.isEmpty()) {
                                ArrayAdapter adapter = new ArrayAdapter(ShuLiangActivity.this, R.layout.simple_list, date);
                                AlertDialog.Builder builder = new AlertDialog.Builder(ShuLiangActivity.this);
                                builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (bodylist.size() > which && bodylist.get(which)!= null) {
                                            ShuLiangBody one = new ShuLiangBody(
                                                    Name,
                                                    bodylist.get(which).getDataContent().getHeight(),
                                                    bodylist.get(which).getDataContent().getMass(),
                                                    bodylist.get(which).getDataContent().getVolume(),
                                                    bodylist.get(which).getDate(),
                                                    granarySettingData.getInfoCard().getBaoguanyuan(),
                                                    granarySettingData.getGrainType(),
                                                    ALL,
                                                    ALL-bodylist.get(which).getDataContent().getMass(),
                                                    granarySettingData.getType()
                                            );
                                            DrawTable(one);
                                        }
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                dialog.setCanceledOnTouchOutside(true);
                            }
                        }
                    }
                });
                break;
            case R.id.txt_test:
                suoPingDialog = new SuoPingDialog(ShuLiangActivity.this,"正在操作，请稍等......");
                suoPingDialog.setCancelable(true);
                suoPingDialog.show();
                NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                newDownRawBody.setUrl("/measure");
                newDownRawBody.setMethod("POST");
                newDownRawBody.setQuery(null);
                newDownRawBody.setHeaders(null);
                CountMultiple countMultiple = new CountMultiple();
                countMultiple.setCommandMapId(commandMapId);
                countMultiple.setUrl(url);
                countMultiple.setMeasureType("-1");
                String addresslist = "";
                String inspurAddressList = "AA55" + TEST_01 + "20" + "FFFF"+ "FFFF0D0A";
                countMultiple.setCommandContent(inspurAddressList);
                Log.d("wan", "OnInitLightDataListener: "+inspurAddressList);
                newDownRawBody.setBody(countMultiple);
                String jsondata2 = gson.toJson(newDownRawBody);
                initTestInTime20(jsondata2, new OnInitTestInTime20FinishedListener() {
                    @Override
                    public void OnInitTestInTime20Listener(boolean success) {
                        if (success) {
                            NewDownRawBodyTWO newDownRawBody2 = new NewDownRawBodyTWO();
                            newDownRawBody2.setUrl("/measure");
                            newDownRawBody2.setMethod("POST");
                            newDownRawBody2.setQuery(null);
                            newDownRawBody2.setHeaders(null);
                            CountMultiple countMultiple2 = new CountMultiple();
                            countMultiple2.setCommandMapId(commandMapId);
                            countMultiple2.setUrl(url);
                            countMultiple2.setMeasureType("-1");
                            String inspurAddressList2 = "AA55" + TEST_02 + "22" + "FFFF"+ "FFFF0D0A";
                            countMultiple2.setCommandContent(inspurAddressList2);
                            Log.d("wan", "OnInitLightDataListener: "+inspurAddressList2);
                            newDownRawBody2.setBody(countMultiple2);
                            String jsondata3 = gson.toJson(newDownRawBody2);
                            initTestInTime22(jsondata3, new OnInitTestInTime22FinishedListener() {
                                @Override
                                public void OnInitTestInTime22Listener(boolean sucess, ShuLiangBody body) {
                                    suoPingDialog.dismiss();
                                    if (sucess && body!=null) {
                                        DrawTable(body);
                                    } else {
                                        util.showToast(ShuLiangActivity.this,"检测失败！");
                                    }
                                }
                            });
                        } else {
                            suoPingDialog.dismiss();
                            util.showToast(ShuLiangActivity.this,"启动失败！");
                        }
                    }
                });
                break;
            case R.id.txt_select:
                ArrayList<GranaryListBean.Data> product = new ArrayList<>();
                for (GranaryListBean.Data data : granarylist) {
                    product.add(data);
                }
                showAlertDialog(product);
                break;
            case R.id.txt_params:
                util.showToast(ShuLiangActivity.this,"更新中......");
                break;
            default:
                break;
        }
    }
    public interface OnInitTestInTime22FinishedListener{
        void OnInitTestInTime22Listener(boolean sucess, ShuLiangBody body);
    }
    public void initTestInTime22(String jsondata, final OnInitTestInTime22FinishedListener listener) {
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                ShuLiangTestInTime22Bean res = new ShuLiangTestInTime22Bean();
                try {
                    res = gson.fromJson((String) result,ShuLiangTestInTime22Bean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitTestInTime22Listener(false,null);
                    e.printStackTrace();
                    util.showToast(ShuLiangActivity.this,"数据解析出错，请稍后再试！");
                    return;
                }
                if (res.getCode()==200 && res.getData()!=null) {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String time = now.format(formatter);
                    ShuLiangBody shuLiangBody = new ShuLiangBody(
                            Name,
                            res.getData().getHeight(),
                            res.getData().getMass(),
                            res.getData().getVolume(),
                            time,
                            granarySettingData.getInfoCard().getBaoguanyuan(),
                            granarySettingData.getGrainType(),
                            ALL,
                            ALL-res.getData().getMass(),
                            granarySettingData.getType()
                    );
                    listener.OnInitTestInTime22Listener(true,shuLiangBody);
                } else {
                    listener.OnInitTestInTime22Listener(false,null);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitTestInTime22Listener(false,null);
                util.showToast(ShuLiangActivity.this,errorMsg);
            }
        });
    }
    public interface OnInitTestInTime20FinishedListener{
        void OnInitTestInTime20Listener(boolean success);
    }
    public void initTestInTime20(String jsondata, final OnInitTestInTime20FinishedListener listener) {
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                ShuLiangTestInTimeBean res = new ShuLiangTestInTimeBean();
                try {
                    res = gson.fromJson((String) result,ShuLiangTestInTimeBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitTestInTime20Listener(false);
                    e.printStackTrace();
                    util.showToast(ShuLiangActivity.this,"数据解析出错，请稍后再试！");
                    return;
                }
                if (res.getCode()==200 && res.getData().getStartFlag().equals("00")) {
                    listener.OnInitTestInTime20Listener(true);
                } else {
                    listener.OnInitTestInTime20Listener(false);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitTestInTime20Listener(false);
                util.showToast(ShuLiangActivity.this,errorMsg);
            }
        });
    }

//    private void ShowDialog(long longstart, long longend, GranaryListBean.Data select) {
//        TimeMultipe multipe = new TimeMultipe();
//        multipe.setDataType("23_");
//        multipe.setUrl(select.getUrl());
//        multipe.setCommandMapId(select.getCommandMapId());
//        multipe.setEndTime(longend);
//        multipe.setStartTime(longstart);
//        NewDownRawBodyTWO body = new NewDownRawBodyTWO();
//        body.setBody(multipe);
//        body.setHeaders(null);
//        body.setQuery(null);
//        body.setMethod("POST");
//        body.setUrl("/history-data/time");
//        String jsonData = gson.toJson(body);
//        Log.d("jht", "ShowDialog: " + jsonData);
//    }
//    public interface OnInitDataTimeFinishedListener {
//        void OnInitDataTimeListener(boolean success);
//    }
//    public void initTime(String jsondata, final OnInitDataTimeFinishedListener listener) {
//        OkHttpUtilTWO.PostNewDownRawTWO("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", jsondata, new OkHttpUtilTWO.ReqCallBack() {
//            @Override
//            public void onReqSuccess(Object result) throws JSONException {
//
//            }
//
//            @Override
//            public void onReqFailed(String errorMsg) {
//
//            }
//        });
//    }
    private void showAlertDialog(ArrayList<GranaryListBean.Data> product) {
        Collections.sort(product, (o1, o2) -> o1.getGranaryName().compareTo(o2.getGranaryName()));
        List<String> NAME = new ArrayList<>();
        for (int i=0;i<product.size();i++){
            NAME.add(product.get(i).getGranaryName());
            if (product.get(i).getGranaryName().equals(TXT_name.toString())){
                selectWhich=i;
            }
        }
        ArrayAdapter adapter_this = new ArrayAdapter<String>(this,R.layout.simple_list,NAME);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(adapter_this, selectWhich, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cangku = product.get(which).getGranaryName();
                selectWhich = which;
                Name = cangku;
                url = product.get(which).getUrl();
                commandMapId = product.get(which).getCommandMapId();
                TXT_name.setText(cangku);
                NewDownRawBodyTWO Body = new NewDownRawBodyTWO();
                CountMultiple multiple = new CountMultiple();
                multiple.setUrl(url);
                multiple.setCommandMapId(commandMapId);
                Body.setBody(multiple);
                Body.setHeaders(null);
                Body.setQuery(null);
                Body.setMethod("POST");
                Body.setUrl("/command-map/get");
                String jsonData = gson.toJson(Body);
                suoPingDialog = new SuoPingDialog(ShuLiangActivity.this,"正在操作，请稍等......");
                suoPingDialog.setCancelable(true);
                suoPingDialog.show();
                initShuLiang(jsonData, new OnInitShuLiangDataFinishedListener() {
                    @Override
                    public void OnInitShuLiangListener(boolean success) {
                        if (success) {
                            NewDownRawBodyTWO historyCountMultipleBody = new NewDownRawBodyTWO();
                            historyCountMultipleBody.setUrl("/history-data/count");
                            historyCountMultipleBody.setMethod("POST");
                            historyCountMultipleBody.setHeaders(null);
                            historyCountMultipleBody.setQuery(null);
                            CountMultiple multiple = new CountMultiple();
                            multiple.setCount(1);
                            multiple.setDataType("23_");
                            multiple.setUrl(url);
                            multiple.setCommandMapId(commandMapId);
                            historyCountMultipleBody.setBody(multiple);
                            String jsondata = gson.toJson(historyCountMultipleBody);
                            initTest(jsondata, new OnInitTestDataFinishedListener() {
                                @Override
                                public void OnInitTestListener(boolean success, ShuLiangBody body) {
//                            suoPingDialog.dismiss();
//                            DrawTable(body);
                                    if (success && body!=null) {
                                        suoPingDialog.dismiss();
                                        Log.d("jht", "OnInitTestListener: here01");
                                        DrawTable(body);
                                    } else {
                                        suoPingDialog.dismiss();
                                    }
                                }
                            });
                        } else {
                            suoPingDialog.dismiss();
                        }
                    }
                });
                dialog.dismiss();
            }
        });
        AlertDialog dialogone = builder.create();
        dialogone.show();
        dialogone.setCanceledOnTouchOutside(true);
    }
}