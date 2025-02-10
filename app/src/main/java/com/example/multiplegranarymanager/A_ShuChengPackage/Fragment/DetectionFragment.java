package com.example.multiplegranarymanager.A_ShuChengPackage.Fragment;

import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.HistoryActivity.DoubleToStr;
import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_ShuChengPackage.Activity.MainActivity.suoPingDialog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_ShuChengPackage.Activity.DataTestActivity;
import com.example.multiplegranarymanager.A_ShuChengPackage.Activity.WindActivity;
import com.example.multiplegranarymanager.A_ShuChengPackage.Adapter.DetectionAdapter;
import com.example.multiplegranarymanager.A_ShuChengPackage.AllInterfaceClass;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice.BatchDeviceData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice.DeviceData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice.DeviceData2;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.CropBean;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.NewDownRaw.CommandType02;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.NewDownRaw.CommandType03;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.SmartWind.Data;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.SmartWind.SmartWindData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Body;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.DeviceInfos.DeviceInfos;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Headers;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.NewDownRaw.Query;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.SubServerParamsBXZJProfile.SubServerParamsBXZJProfile;
import com.example.multiplegranarymanager.A_ShuChengPackage.SharedData.ShareSmartWindInfos;
import com.example.multiplegranarymanager.A_ShuChengPackage.SharedData.SharedDeviceInfos;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.CustomEditText;
import com.example.multiplegranarymanager.Util.Util1;
import com.example.multiplegranarymanager.Util.WonderUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class DetectionFragment extends Fragment implements View.OnClickListener{
    CustomEditText Search_Input;
    ImageView MoreList,Img_More;
    TextView mFooterView;
    TextView Text_Tem,Text_Hum,Text_Gas,Text_TongFeng,Text_Text,Text_Select,Text_Clean,Text_Test;
    TextView selectedTextView = null;
    SmartRefreshLayout refreshLayout;
    LinearLayout WuShebei,infos,layout_more;
    RecyclerView mListView;
    Button btn1,btn2,btn3;
    Bundle bundle = new Bundle();
    //显示或收起功能列表判断
    private boolean Img_More_Flag = true;
    private int positione = -1;
    private Util1 util = new Util1();
    private DetectionAdapter mAdapter;
    private HeaderViewAdapter headerViewAdapter;
    private WonderUtil mWonderUtil = new WonderUtil();
    private String TAG_FLAG = "温湿度";
    private Gson gson = new Gson();
    final String[] TAG = {null};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detextion_3, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        ShareSmartWindInfos.SmartWindList.clear();
        suoPingDialog = new SuoPingDialog(getContext(),"正在获取数据，请稍等......");
        suoPingDialog.show();
        initView(view);
        initTem(new InitTemFinishedListener() {
            @Override
            public void InitTemListener() {
                initHum(new InitHumFinishedListener() {
                    @Override
                    public void InitHumListener() {
                        initGas(new InitGasFinishedListener() {
                            @Override
                            public void InitGasListener() {
                                //按名字排序
                                Collections.sort(SharedDeviceInfos.DeviceInfosList, new Comparator<DeviceInfos>() {
                                    @Override
                                    public int compare(DeviceInfos o1, DeviceInfos o2) {
                                        return mWonderUtil.compareTo(o1.getGranaryName(), o2.getGranaryName());
                                    }
                                });
                                Log.d("jhtzyq", "InitGasListener: okokokokokokokokokokokokok");
                                suoPingDialog.dismiss();
                                refreshView();
                            }
                        });
                    }
                });
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                suoPingDialog = new SuoPingDialog(getContext(),"正在获取数据，请稍等......");
                suoPingDialog.show();
                initTem(new InitTemFinishedListener() {
                    @Override
                    public void InitTemListener() {
                        initHum(new InitHumFinishedListener() {
                            @Override
                            public void InitHumListener() {
                                initGas(new InitGasFinishedListener() {
                                    @Override
                                    public void InitGasListener() {
                                        //按名字排序
                                        Collections.sort(SharedDeviceInfos.DeviceInfosList, new Comparator<DeviceInfos>() {
                                            @Override
                                            public int compare(DeviceInfos o1, DeviceInfos o2) {
                                                return mWonderUtil.compareTo(o1.getGranaryName(), o2.getGranaryName());
                                            }
                                        });
                                        Log.d("jhtzyq", "InitGasListener: okokokokokokokokokokokokok");
                                        suoPingDialog.dismiss();
                                        refreshView();
                                    }
                                });
                            }
                        });
                    }
                });
                refreshLayout.finishRefresh(1500/*,false*/);//传入false表示刷新失败
                util.showToast(getContext(),"刷新成功");
            }
        });
        return view;
    }
    private void refreshView() {
        View fv = LayoutInflater.from(getContext()).inflate(R.layout.item_list_contact_count,null,false);
        mFooterView = fv.findViewById(R.id.tv_foot);
        mFooterView.setText("正在加载数据......");
        mFooterView.setGravity(Gravity.CENTER_HORIZONTAL);
        int[] layoutIds = new int[]{R.layout.card_view_adapter_shucheng};
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        mListView.setLayoutManager(layoutManager);
        mAdapter = new DetectionAdapter(SharedDeviceInfos.DeviceInfosList,getContext());
        headerViewAdapter = new HeaderViewAdapter(mAdapter);
        headerViewAdapter.addFooterView(fv);
        mListView.setAdapter(headerViewAdapter);
        mFooterView.setText("共"+mAdapter.getFreshDates().size()+"台设备");
    }
    public interface InitTemFinishedListener {
        void InitTemListener();
    }
    private void initTem(final InitTemFinishedListener listener) {
        Log.d("jhtzyq", "InitGasListener: qqqqqq");
        int totalTasks = SharedDeviceInfos.DeviceInfosList.size();
        final int[] completedTasks = {0};
        for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList){
            AllInterfaceClass<BatchDeviceData> one = new AllInterfaceClass<>(BatchDeviceData.class);
            Body body = new Body();
            ArrayList<String> list = new ArrayList<>();
            list.add(data.getTemDeviceKey());
            body.setProductKey(data.getProductKey());
            body.setDeviceKeyList(list);
            Headers headers = new Headers();
            headers.setTokenOffline(Token);
            NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
            newDownRawBodyTWO.setMethod("POST");
            newDownRawBodyTWO.setHeaders(headers);
            newDownRawBodyTWO.setBody(body);
            newDownRawBodyTWO.setQuery(null);
            newDownRawBodyTWO.setUrl("/batchDeviceData");
            one.PostOne(newDownRawBodyTWO, "MainActivity/initTem", new AllInterfaceClass.PostCallBack<BatchDeviceData>() {
                @Override
                public void onSuccess(BatchDeviceData zyq) {
                    if (zyq.getData()!=null && zyq.getData().getDeviceData()!=null && zyq.getData().getDeviceData().size()>0) {
                        data.setTemDeviceData(zyq.getData().getDeviceData().get(0));
                        if (zyq.getData().getDeviceData().get(0).getTemp()!=null) {
                            data.setTemInner(Double.valueOf(zyq.getData().getDeviceData().get(0).getTemp()));
                        } else {
                            data.setTemInner(255.0);
                        }
                        if (zyq.getData().getDeviceData().get(0).getTemp_out()!=null) {
                            data.setTemOuter(Double.valueOf(zyq.getData().getDeviceData().get(0).getTemp_out()));
                        } else {
                            data.setTemOuter(255.0);
                        }
                        if (zyq.getData().getDeviceData().get(0).getHumidity()!=null) {
                            data.setHumInner(Double.valueOf(zyq.getData().getDeviceData().get(0).getHumidity()));
                        } else {
                            data.setHumInner(255.0);
                        }
                        if (zyq.getData().getDeviceData().get(0).getHumidity_out()!=null) {
                            data.setHumOuter(Double.valueOf(zyq.getData().getDeviceData().get(0).getHumidity_out()));
                        } else {
                            data.setHumOuter(255.0);
                        }
                        if (zyq.getData().getDeviceData().get(0).getCableTemp()!=null && zyq.getData().getDeviceData().get(0).getCableTemp().size()>0) {
                            List<Double> result = MaxMinAve(zyq.getData().getDeviceData().get(0).getCableTemp());
                            if (result!=null && result.size()>0){
                                data.setTemMax(result.get(0));
                                data.setTemMin(result.get(1));
                                data.setTemAve(result.get(2));
                            } else {
                                data.setTemMax(255.0);
                                data.setTemMin(255.0);
                                data.setTemAve(255.0);
                            }
                        } else {
                            data.setTemMax(255.0);
                            data.setTemMin(255.0);
                            data.setTemAve(255.0);
                        }
                    }
                    completedTasks[0]++;
                    if (completedTasks[0] == totalTasks) {
                        listener.InitTemListener(); // 假设InitTemListener是无参数的回调方法，根据实际情况调整参数
                    }
                }

                @Override
                public void onFailure(String zyq) {
                    util.showToast(getContext(),zyq);
                    completedTasks[0]++;
                    if (completedTasks[0] == totalTasks) {
                        listener.InitTemListener(); // 假设InitTemListener是无参数的回调方法，根据实际情况调整参数
                    }
                }
            });
        }
    }
    private List<Double> MaxMinAve(List<String> list) {
        List<Double> result = new ArrayList<>();
        List<Double> zyq = new ArrayList<>();
        for (String str : list) {
            Double doubleValue = null;
            try {
                doubleValue = Double.parseDouble(str);
                zyq.add(doubleValue);
            } catch (NumberFormatException e) {
                // 处理解析失败的情况，比如记录日志，跳过这个无法转换的值等
                Log.e("MainActivity", "Failed to parse string to double: " + str, e);
                continue;
            }
        }
        Iterator<Double> iterator = zyq.iterator();
        while (iterator.hasNext()) {
            Double value = iterator.next();
            if (value == 255.0){
                iterator.remove();
            }
        }
        if (zyq.size()>0) {
            Double max = Collections.max(zyq);
            Double min = Collections.min(zyq);
            if (!zyq.isEmpty()) {
                DecimalFormat nb2 = new DecimalFormat("0.0");
                Double avg = zyq.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
                result.add(max);
                result.add(min);
                result.add(Double.valueOf(nb2.format(avg)));
            } else {
                // 可以根据业务需求决定为空时result列表的默认赋值情况，比如添加默认的NaN值等
                result.add(255.0);
                result.add(255.0);
                result.add(255.0);
            }
        }
        return result;
    }
    public interface InitHumFinishedListener {
        void InitHumListener();
    }
    private void initHum(final InitHumFinishedListener listener) {
        int totalTasks = SharedDeviceInfos.DeviceInfosList.size();
        final int[] completedTasks = {0};
        for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList){
            if (data.getHumDeviceKey()!=null) {
                AllInterfaceClass<BatchDeviceData> one = new AllInterfaceClass<>(BatchDeviceData.class);
                Body body = new Body();
                ArrayList<String> list = new ArrayList<>();
                list.add(data.getHumDeviceKey());
                body.setProductKey(data.getProductKey());
                body.setDeviceKeyList(list);
                Headers headers = new Headers();
                headers.setTokenOffline(Token);
                NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
                newDownRawBodyTWO.setMethod("POST");
                newDownRawBodyTWO.setHeaders(headers);
                newDownRawBodyTWO.setBody(body);
                newDownRawBodyTWO.setQuery(null);
                newDownRawBodyTWO.setUrl("/batchDeviceData");
                one.PostOne(newDownRawBodyTWO, "MainActivity/initTem", new AllInterfaceClass.PostCallBack<BatchDeviceData>() {
                    @Override
                    public void onSuccess(BatchDeviceData zyq) {
                        List<Double> JHT = new ArrayList<>();
                        List<Double> ZYQ = new ArrayList<>();
                        if (zyq.getData()!=null && zyq.getData().getDeviceData()!=null && zyq.getData().getDeviceData().size()>0) {
                            data.setHumDeviceData(zyq.getData().getDeviceData().get(0));
//                            if (zyq.getData().getDeviceData().get(0).getHumidity()!=null) {
//                                data.setHumInner(Double.valueOf(zyq.getData().getDeviceData().get(0).getHumidity()));
//                            } else {
//                                data.setHumInner(255.0);
//                            }
//                            if (zyq.getData().getDeviceData().get(0).getHumidity_out()!=null) {
//                                data.setHumOuter(Double.valueOf(zyq.getData().getDeviceData().get(0).getHumidity_out()));
//                            } else {
//                                data.setHumOuter(255.0);
//                            }
                            if (zyq.getData().getDeviceData().get(0).getHumidityList()!=null && zyq.getData().getDeviceData().get(0).getCableTemp()!=null) {
                                for (String str : zyq.getData().getDeviceData().get(0).getHumidityList()) {
                                    Double doubleValue = null;
                                    try {
                                        doubleValue = Double.parseDouble(str);
                                        JHT.add(doubleValue);
                                    } catch (NumberFormatException e) {
                                        // 处理解析失败的情况，比如记录日志，跳过这个无法转换的值等
                                        Log.e("MainActivity", "Failed to parse string to double: " + str, e);
                                        continue;
                                    }
                                }
                                Iterator<Double> iterator = JHT.iterator();
                                while (iterator.hasNext()) {
                                    Double value = iterator.next();
                                    if (value == 255.0){
                                        iterator.remove();
                                    }
                                }
                                for (String str : zyq.getData().getDeviceData().get(0).getCableTemp()) {
                                    Double doubleValue = null;
                                    try {
                                        doubleValue = Double.parseDouble(str);
                                        ZYQ.add(doubleValue);
                                    } catch (NumberFormatException e) {
                                        // 处理解析失败的情况，比如记录日志，跳过这个无法转换的值等
                                        Log.e("MainActivity", "Failed to parse string to double: " + str, e);
                                        continue;
                                    }
                                }
                                Iterator<Double> iterator2 = ZYQ.iterator();
                                while (iterator2.hasNext()) {
                                    Double value = iterator2.next();
                                    if (value == 255.0){
                                        iterator2.remove();
                                    }
                                }
                                calulate(data.getGrainType(), ZYQ, JHT, new OnInitCalulateFinishedListener() {
                                    @Override
                                    public void OnInitCalulateListener(List<Double> listresult) {
                                        DecimalFormat nb2 = new DecimalFormat("0.0");
                                        double watercount = listresult.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
                                        data.setShuiFen(Double.valueOf(nb2.format(watercount)));
                                    }
                                });
                            } else {
                                data.setShuiFen(255.0);
                            }
                        }
                        completedTasks[0]++;
                        if (completedTasks[0] == totalTasks) {
                            listener.InitHumListener(); // 假设InitTemListener是无参数的回调方法，根据实际情况调整参数
                        }
                    }

                    @Override
                    public void onFailure(String zyq) {
                        util.showToast(getContext(),zyq);
                        completedTasks[0]++;
                        if (completedTasks[0] == totalTasks) {
                            listener.InitHumListener(); // 假设InitTemListener是无参数的回调方法，根据实际情况调整参数
                        }
                    }
                });
            } else {
                completedTasks[0]++;
                if (completedTasks[0] == totalTasks) {
                    listener.InitHumListener(); // 假设InitTemListener是无参数的回调方法，根据实际情况调整参数
                }
            }
        }
    }
    public interface OnInitCalulateFinishedListener {
        void OnInitCalulateListener(List<Double> listresult);
    }
    private void calulate(String liangshizhonglei, List<Double> tem, List<Double> mon, final OnInitCalulateFinishedListener listener) {
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
        if (liangshizhonglei!=null && liangshizhonglei.equals("小麦")){
            if (tem.size()==mon.size()){
                for (int i=0;i<tem.size();i++){
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
            if (listener != null){
                listener.OnInitCalulateListener(waterdouble);
            }
        } else if (liangshizhonglei!=null && liangshizhonglei.equals("稻谷")) {
            if (tem.size()==mon.size()){
                for (int i=0;i<tem.size();i++){
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
                        last = cropBean.getDaogu().get(str);
                        waterdouble.add(last);
                    }
                }
            }
            if (listener != null){
                listener.OnInitCalulateListener(waterdouble);
            }
        }else if (liangshizhonglei!=null && liangshizhonglei.equals("玉米")) {
            for (int i=0;i<tem.size();i++){
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
            if (listener != null){
                listener.OnInitCalulateListener(waterdouble);
            }
        }else if (liangshizhonglei!=null && liangshizhonglei.equals("大米")) {
            for (int i=0;i<tem.size();i++){
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
            if (listener != null){
                listener.OnInitCalulateListener(waterdouble);
            }
        }else if (liangshizhonglei!=null && liangshizhonglei.equals("黍子")) {
            for (int i=0;i<tem.size();i++){
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
            if (listener != null){
                listener.OnInitCalulateListener(waterdouble);
            }
        }else if (liangshizhonglei!=null && liangshizhonglei.equals("大豆")) {
            for (int i=0;i<tem.size();i++){
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
            if (listener != null){
                listener.OnInitCalulateListener(waterdouble);
            }
        }else {
            if (listener != null){
                listener.OnInitCalulateListener(waterdouble);
            }
        }
    }
    public interface InitGasFinishedListener {
        void InitGasListener();
    }
    private void initGas(final InitGasFinishedListener listener) {
        int totalTasks = SharedDeviceInfos.DeviceInfosList.size();
        final int[] completedTasks = {0};
        for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList){
            if (data.getGasDeviceKey()!=null) {
                AllInterfaceClass<BatchDeviceData> one = new AllInterfaceClass<>(BatchDeviceData.class);
                Body body = new Body();
                ArrayList<String> list = new ArrayList<>();
                list.add(data.getGasDeviceKey());
                body.setProductKey(data.getProductKey());
                body.setDeviceKeyList(list);
                Headers headers = new Headers();
                headers.setTokenOffline(Token);
                NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
                newDownRawBodyTWO.setMethod("POST");
                newDownRawBodyTWO.setHeaders(headers);
                newDownRawBodyTWO.setBody(body);
                newDownRawBodyTWO.setQuery(null);
                newDownRawBodyTWO.setUrl("/batchDeviceData");
                one.PostOne(newDownRawBodyTWO, "MainActivity/initTem", new AllInterfaceClass.PostCallBack<BatchDeviceData>() {
                    @Override
                    public void onSuccess(BatchDeviceData zyq) {
                        if (zyq.getData()!=null && zyq.getData().getDeviceData()!=null && zyq.getData().getDeviceData().size()>0) {
                            data.setGasDeviceData(zyq.getData().getDeviceData().get(0));
                        }
                        completedTasks[0]++;
                        if (completedTasks[0] == totalTasks) {
                            listener.InitGasListener(); // 假设InitTemListener是无参数的回调方法，根据实际情况调整参数
                        }
                    }

                    @Override
                    public void onFailure(String zyq) {
                        util.showToast(getContext(),zyq);
                        completedTasks[0]++;
                        if (completedTasks[0] == totalTasks) {
                            listener.InitGasListener(); // 假设InitTemListener是无参数的回调方法，根据实际情况调整参数
                        }
                    }
                });
            } else {
                completedTasks[0]++;
                if (completedTasks[0] == totalTasks) {
                    listener.InitGasListener(); // 假设InitTemListener是无参数的回调方法，根据实际情况调整参数
                }
            }
        }
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
        Text_Hum = view.findViewById(R.id.txt_hum);
        Text_Gas = view.findViewById(R.id.txt_gas);
        Text_TongFeng = view.findViewById(R.id.txt_tongfeng);
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
        Text_Hum.setOnClickListener(this);
        Text_Gas.setOnClickListener(this);
        Text_TongFeng.setOnClickListener(this);
        Text_Select.setOnClickListener(this);
        Text_Clean.setOnClickListener(this);
        Text_Test.setOnClickListener(this);
        Img_More.setOnClickListener(this);
        //刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshView(getContext(),CardDataAdapterList);
                refreshLayout.finishRefresh(1500);
                util.showToast(getContext(),"刷新成功");
            }
        });
        //设置Header为贝塞尔雷达样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()).setEnableHorizontalDrag(true));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_tem:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_Tem.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_Tem;
                Text_Text.setText("温湿度检测");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_hum:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_Hum.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_Hum;
                Text_Text.setText("温湿水检测");
                refreshLayout.setVisibility(View.VISIBLE);
                WuShebei.setVisibility(View.GONE);
                break;
            case R.id.txt_gas:
                if (selectedTextView != null){
                    selectedTextView.setBackgroundColor(getResources().getColor(R.color.china_color_020_mnh));
                }
                Text_Gas.setBackgroundColor(getResources().getColor(R.color.china_color_009_yb));
                selectedTextView = Text_Gas;
                Text_Text.setText("气体检测");
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
            case R.id.txt_all_select:
                Text_Clean.setVisibility(View.VISIBLE);
                Text_Select.setVisibility(View.GONE);
                for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList){
                    if (!data.getSelected()){
                        data.setSelected(true);
                    }
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.txt_all_clean:
                Text_Clean.setVisibility(View.GONE);
                Text_Select.setVisibility(View.VISIBLE);
                for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList){
                    if (data.getSelected()){
                        data.setSelected(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.txt_now_test:
                String CaoZuo = Text_Text.getText().toString();
                Log.d("jht", "CaoZuo: "+CaoZuo);
                List<DeviceInfos> body = new ArrayList<>();
                for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList) {
                    if (data.getSelected()) {
                        body.add(data);
                    }
                }
                TextInTime(CaoZuo,body);
                break;
            case R.id.img_more:
                if (Img_More_Flag){
                    layout_more.setVisibility(View.VISIBLE);
                    Img_More.setImageResource(R.drawable.arrow_left_in_main);
                    Img_More_Flag = false;
                    if (mAdapter!=null) {
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    layout_more.setVisibility(View.GONE);
                    Img_More.setImageResource(R.drawable.arrow_right_in_main);
                    Img_More_Flag = true;
                    if (mAdapter!=null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            default:
                break;
        }
    }
    private void TextInTime(String caoZuo, List<DeviceInfos> list) {
        suoPingDialog = new SuoPingDialog(getContext(),"正在操作，请稍等......");
        suoPingDialog.show();

        if (caoZuo.equals("温湿度检测")) {
            if (list.size()>0) {
                AllInterfaceClass<CommandType02> one = new AllInterfaceClass<>(CommandType02.class);
                Body body = new Body();
                body.setCommandType("02");
                body.setModuleName(list.get(0).getModuleName());
                List<SubServerParamsBXZJProfile> profiles = new ArrayList<>();
                for (DeviceInfos data : list) {
                    if (data.getTemDeviceKey() != null) {
                        SubServerParamsBXZJProfile params = new SubServerParamsBXZJProfile();
                        params.setId(data.getGranaryId());
                        params.setMeasure(Arrays.asList("temp", "temphumi"));
                        params.setNickname(data.getTemDeviceKey());
                        profiles.add(params);
                    }
                }
                for (DeviceInfos data : list) {
                    if (data.getTemDeviceKey() != null) {
                        TAG[0] = data.getGranaryName();
                        break;
                    }
                }

                if (profiles == null) return;
                body.setProfile(profiles);
                Headers headers = new Headers();
                headers.setTokenOffline(Token);
                Query query = new Query();
                query.setBodyType("json");
                query.setDeviceType("LQBXZJ");
                query.setTimeout(5);
                NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
                newDownRawBodyTWO.setBody(body);
                newDownRawBodyTWO.setQuery(query);
                newDownRawBodyTWO.setHeaders(headers);
                newDownRawBodyTWO.setMethod("POST");
                newDownRawBodyTWO.setUrl("/newDownRaw");
                initTest(one, newDownRawBodyTWO, new InitTestFinishedListener() {
                    @Override
                    public void InitTestListener(boolean success, String flag) {
                        TAG_FLAG = "温湿度";
                        InitMeasureIdTask initMeasureIdTask = new InitMeasureIdTask();
                        initMeasureIdTask.execute(caoZuo,flag);
                    }
                });
            } else {
                suoPingDialog.dismiss();
                util.showToast(getContext(),"请至少选择一个仓");
            }
        } else if (caoZuo.equals("温湿水检测")) {
            if (list.size()>0) {
                AllInterfaceClass<CommandType02> one = new AllInterfaceClass<>(CommandType02.class);
                Body body = new Body();
                body.setCommandType("02");
                body.setModuleName(list.get(0).getModuleName());
                List<SubServerParamsBXZJProfile> profiles = new ArrayList<>();
                SubServerParamsBXZJProfile params = new SubServerParamsBXZJProfile();
                for (DeviceInfos data : list) {
                    if (data.getHumDeviceKey() != null) {
                        params.setId(data.getGranaryId());
                        params.setMeasure(Arrays.asList("shuifen", "temphumi"));
                        params.setNickname(data.getHumDeviceKey());
                        profiles.add(params);
                    }
                }
                for (DeviceInfos data : list) {
                    if (data.getHumDeviceKey() != null) {
                        TAG[0] = data.getGranaryName();
                        break;
                    }
                }
                if (profiles == null) return;
                body.setProfile(profiles);
                Headers headers = new Headers();
                headers.setTokenOffline(Token);
                Query query = new Query();
                query.setBodyType("json");
                query.setDeviceType("LQBXZJ");
                query.setTimeout(5);
                NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
                newDownRawBodyTWO.setBody(body);
                newDownRawBodyTWO.setQuery(query);
                newDownRawBodyTWO.setHeaders(headers);
                newDownRawBodyTWO.setMethod("POST");
                newDownRawBodyTWO.setUrl("/newDownRaw");
                String finalTAG1 = TAG[0];
                initTest(one, newDownRawBodyTWO, new InitTestFinishedListener() {
                    @Override
                    public void InitTestListener(boolean success, String flag) {
                        TAG_FLAG = "温湿水";
                        InitMeasureIdTask initMeasureIdTask = new InitMeasureIdTask();
                        initMeasureIdTask.execute(caoZuo,flag);
                    }
                });
            } else {
                suoPingDialog.dismiss();
                util.showToast(getContext(),"请至少选择一个仓");
            }
        } else if (caoZuo.equals("气体检测")) {
            if (list.size()>0) {
                AllInterfaceClass<CommandType02> one = new AllInterfaceClass<>(CommandType02.class);
                Body body = new Body();
                body.setCommandType("02");
                body.setModuleName(list.get(0).getModuleName());
                List<SubServerParamsBXZJProfile> profiles = new ArrayList<>();
                SubServerParamsBXZJProfile params = new SubServerParamsBXZJProfile();
                for (DeviceInfos data : list) {
                    if (data.getGasDeviceKey() != null) {
                        params.setId(data.getGranaryId());
                        params.setMeasure(Arrays.asList("air", "downair"));
                        params.setNickname(data.getGasDeviceKey());
                        profiles.add(params);
                    }
                }
                for (DeviceInfos data : list) {
                    if (data.getGasDeviceKey() != null) {
                        TAG[0] = data.getGranaryName();
                        break;
                    }
                }
                if (profiles == null) return;
                body.setProfile(profiles);
                Headers headers = new Headers();
                headers.setTokenOffline(Token);
                Query query = new Query();
                query.setBodyType("json");
                query.setDeviceType("LQBXZJ");
                query.setTimeout(5);
                NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
                newDownRawBodyTWO.setBody(body);
                newDownRawBodyTWO.setQuery(query);
                newDownRawBodyTWO.setHeaders(headers);
                newDownRawBodyTWO.setMethod("POST");
                newDownRawBodyTWO.setUrl("/newDownRaw");
                initTest(one, newDownRawBodyTWO, new InitTestFinishedListener() {
                    @Override
                    public void InitTestListener(boolean success, String flag) {
                        if (success && flag!=null) {
                            TAG_FLAG = "气体";
                            InitMeasureIdTask initMeasureIdTask = new InitMeasureIdTask();
                            initMeasureIdTask.execute(caoZuo,flag);
                        }
                    }
                });
            } else {
                suoPingDialog.dismiss();
                util.showToast(getContext(),"请至少选择一个仓");
            }
        } else if (caoZuo.equals("通风控制")) {

            if (list.size()==1){
                for (DeviceInfos data : SharedDeviceInfos.DeviceInfosList) {
                    if (data.getWindDeviceKey()!=null && data.getWindDeviceData()!=null) {
                        Data info = new Data();
                        SmartWindData smartWindData = new SmartWindData();
                        String res = gson.toJson(data.getWindDeviceData().getExtraInfo().get("smartWindInfo").getValue());
                        smartWindData = gson.fromJson(res, SmartWindData.class);
                        info.setDeviceinfo(smartWindData);
                        info.setDevicekey(data.getWindDeviceKey());
                        info.setModuleName(data.getModuleName());
                        info.setProductkey(data.getProductKey());
                        info.setGranaryId(info.getGranaryId());
                        info.setGranaryName(data.getGranaryName());
                        ShareSmartWindInfos.SmartWindList.add(info);
                    }
                }
                suoPingDialog.dismiss();
                TAG[0] = list.get(0).getGranaryName();
                Intent intent = new Intent(getContext(), WindActivity.class);
                intent.putExtra("Name",TAG[0]);
                startActivity(intent);
            } else if (list.size()>1) {
                suoPingDialog.dismiss();
                util.showToast(getContext(),"请只选择一个仓");
            } else {
                suoPingDialog.dismiss();
                util.showToast(getContext(),"请至少选择一个仓");
            }
        }

    }
    public interface InitTestFinishedListener {
        void InitTestListener(boolean success,String flag);
    }
    public void initTest(AllInterfaceClass<CommandType02> one,NewDownRawBodyTWO str, final InitTestFinishedListener listener) {
        one.PostThr(str, "DetectionFragemnt/TextInTime", new AllInterfaceClass.PostCallBack<CommandType02>() {
            @Override
            public void onSuccess(CommandType02 zyq) {
                if (zyq.getData()!=null && zyq.getData().getMeasureId()!=null) {
                    listener.InitTestListener(true,zyq.getData().getMeasureId());
                } else {
                    suoPingDialog.dismiss();
                    listener.InitTestListener(false,null);
                    util.showToast(getContext(),"检测失败，请检查现场设备。");
                }
            }
            @Override
            public void onFailure(String zyq) {
                suoPingDialog.dismiss();
                listener.InitTestListener(false,null);
                util.showToast(getContext(),zyq);
            }
        });
    }
    public class InitMeasureIdTask extends AsyncTask<String,Double,CommandType03> {
        private boolean flag = true;
        @Override
        protected CommandType03 doInBackground(String... params) {
            String caozuo = params[0];
            String measure = params[1];
            AllInterfaceClass<CommandType03> one = new AllInterfaceClass<>(CommandType03.class);
            Body body = new Body();
            body.setMeasureId(measure);
            body.setCommandType("03");
            Headers headers = new Headers();
            headers.setTokenOffline(Token);
            Query query = new Query();
            query.setTimeout(5);
            query.setDeviceType("LQBXZJ");
            query.setBodyType("json");
            NewDownRawBodyTWO newDownRawBodyTWO = new NewDownRawBodyTWO();
            newDownRawBodyTWO.setBody(body);
            newDownRawBodyTWO.setQuery(query);
            newDownRawBodyTWO.setHeaders(headers);
            newDownRawBodyTWO.setMethod("POST");
            newDownRawBodyTWO.setUrl("/newDownRaw");
            final CommandType03[] res = {null};
            while (flag) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.err.println("线程睡眠被中断，异常信息：" + e.getMessage());
                }
                synchronized (this) {
                    one.PostOne(newDownRawBodyTWO, "DetectionFragment/initMeasureId", new AllInterfaceClass.PostCallBack<CommandType03>() {
                        @Override
                        public void onSuccess(CommandType03 zyq) {
                            if (zyq!=null && zyq.getCode()!=200){
                                flag = false;
                                res[0] = zyq;
                            }
                            if (zyq!=null && zyq.getData().getProgress()>=1.0){
                                // 假设res.getData().getProgress()返回0 - 100的进度值，将其传递给onProgressUpdate方法
                                publishProgress(zyq.getData().getProgress());
                                if (zyq.getData().getProgress()>=1){
                                    for (DeviceData2 data1 : zyq.getData().getData()) {
                                        if (caozuo.equals("温湿度检测")) {
                                            for (DeviceInfos data2 : SharedDeviceInfos.DeviceInfosList) {
                                                if (data2.getTemDeviceKey()!=null && data2.getTemDeviceKey().equals(data1.getDeviceKey())) {
                                                    if (data1.getDate()!=null) {
                                                        data2.getTemDeviceData().setDate(data1.getDate());
                                                    }
                                                    if (data1.getCableTemp()!=null){
                                                        data2.getTemDeviceData().setCableTemp(DoubleToStr(data1.getCableTemp()));
                                                    }
                                                    if (data1.getHumidity_out()!=null){
                                                        data2.getTemDeviceData().setHumidity_out(String.valueOf(data1.getHumidity_out()));
                                                    }
                                                    if (data1.getHumidity()!=null){
                                                        data2.getTemDeviceData().setHumidity(String.valueOf(data1.getHumidity()));
                                                    }
                                                    if (data1.getTemp_out()!=null){
                                                        data2.getTemDeviceData().setTemp_out(String.valueOf(data1.getTemp_out()));
                                                    }
                                                    if (data1.getTemp_out()!=null){
                                                        data2.getTemDeviceData().setTemp(String.valueOf(data1.getTemp_out()));
                                                    }
                                                }
                                            }
                                        } else if (caozuo.equals("温湿水检测")) {
                                            for (DeviceInfos data2 : SharedDeviceInfos.DeviceInfosList) {
                                                if (data2.getHumDeviceKey()!=null && data2.getHumDeviceKey().equals(data1.getDeviceKey())) {
                                                    if (data1.getDate()!=null){
                                                        data2.getHumDeviceData().setDate(data1.getDate());
                                                    }
                                                    if (data1.getCableTemp()!=null){
                                                        data2.getHumDeviceData().setCableTemp(DoubleToStr(data1.getCableTemp()));
                                                    }
                                                    if (data1.getHumidity_out()!=null){
                                                        data2.getHumDeviceData().setHumidity_out(String.valueOf(data1.getHumidity_out()));
                                                    }
                                                    if (data1.getHumidityList()!=null){
                                                        data2.getHumDeviceData().setHumidityList(DoubleToStr(data1.getHumidityList()));
                                                    }
                                                    if (data1.getHumidity()!=null){
                                                        data2.getHumDeviceData().setHumidity(String.valueOf(data1.getHumidity()));
                                                    }
                                                    if (data1.getTemp_out()!=null){
                                                        data2.getHumDeviceData().setTemp_out(String.valueOf(data1.getTemp_out()));
                                                    }
                                                    if (data1.getTemp()!=null){
                                                        data2.getHumDeviceData().setTemp(String.valueOf(data1.getTemp()));
                                                    }
                                                }
                                            }
                                        } else if (caozuo.equals("气体检测")) {
                                            for (DeviceInfos data2 : SharedDeviceInfos.DeviceInfosList) {
                                                if (data2.getGasDeviceKey()!=null && data2.getGasDeviceKey().equals(data1.getDeviceKey())) {
                                                    if (data1.getDate()!=null){
                                                        data2.getGasDeviceData().setDate(data1.getDate());
                                                    }
                                                    if (data1.getCo2()!=null){
                                                        data2.getGasDeviceData().setCo2(DoubleToStr(data1.getCo2()));
                                                    }
                                                    if (data1.getCo2_bottom()!=null){
                                                        data2.getGasDeviceData().setCo2_bottom(DoubleToStr(data1.getCo2_bottom()));
                                                    }
                                                    if (data1.getN2()!=null){
                                                        data2.getGasDeviceData().setN2(DoubleToStr(data1.getN2()));
                                                    }
                                                    if (data1.getO2()!=null){
                                                        data2.getGasDeviceData().setO2(DoubleToStr(data1.getO2()));
                                                    }
                                                    if (data1.getO2_bottom()!=null){
                                                        data2.getGasDeviceData().setO2_bottom(DoubleToStr(data1.getO2_bottom()));
                                                    }
                                                    if (data1.getDust()!=null){
                                                        data2.getGasDeviceData().setDust(DoubleToStr(data1.getDust()));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    flag = false;
                                    res[0] = zyq;
                                }
                            }
                        }

                        @Override
                        public void onFailure(String zyq) {
                            flag = false;
                            res[0] = null;
                        }
                    });
                }
            }
            return res[0];
        }
        @Override
        protected void onPostExecute(CommandType03 result) {
            super.onPostExecute(result);
            if (result != null) {
                mAdapter.notifyDataSetChanged();
                Intent intent = new Intent(getContext(),DataTestActivity.class);
                suoPingDialog.dismiss();
                mAdapter.notifyDataSetChanged();
                util.showToast(getContext(), "实测成功！");
                intent.putExtra("TAG", TAG_FLAG);
                if (TAG_FLAG.equals("温湿度")) {
                    intent.putExtra("fragment","1");
                } else if (TAG_FLAG.equals("温湿水")) {
                    intent.putExtra("fragment","2");
                } else if (TAG_FLAG.equals("气体")) {
                    intent.putExtra("fragment","3");
                }
                intent.putExtra("Flag", TAG[0]);
                intent.putExtra("TAG_FLAG",TAG_FLAG);
                startActivity(intent);
            } else {
                suoPingDialog.dismiss();
                util.showToast(getContext(), "实测出现问题，请稍后重试");
            }
        }
        public void startInitMeasureIdTask(String caozuo, String measure) {
            InitMeasureIdTask task = new InitMeasureIdTask();
            task.execute(caozuo, measure);
        }
    }
}