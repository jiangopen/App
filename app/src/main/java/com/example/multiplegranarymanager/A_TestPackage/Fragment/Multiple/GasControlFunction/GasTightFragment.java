package com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.GasControlFunction;

import static com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.deviceType;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.multiplegranarymanager.Bean.NewDownRaw.MeasureId02Bean;
import com.example.multiplegranarymanager.Bean.QiTiao.QiTiaoBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRaw02Body;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRaw03Body;
import com.example.multiplegranarymanager.Body.NewDownRaw.Profile;
import com.example.multiplegranarymanager.Body.QiTiao.QiTiaoBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GasTightFragment extends Fragment {
    TextView txt_test,txt_status,txt_class,txt_time,txt_gas_pa,txt_test_modile;
    EditText txt_start_pa,txt_stop_pa,txt_final_pa;
    private boolean flag = false;
    private AlertDialog Dialog;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    private Handler handler = new Handler();
    private Bundle bundle = new Bundle();
    private int seconds = 0;
    private Runnable runnable;
    private QiTiaoBody qiTiaoBody;
    private int Start_Pa,Stop_Pa,Final_Pa;
    private float Timing = 0;
    private Timer timer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function_gas_tight, container, false);
        initView(view);
        bundle = getArguments();
        if (bundle!=null){
            qiTiaoBody = bundle.getParcelable("params");
            txt_gas_pa.setText(qiTiaoBody.getPressure_list_01());
        }
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (flag){
                    STARTTIMER();
                }
            }
        };
        timer.scheduleAtFixedRate(task,0,10*1000);
        return view;
    }
    public void inputNews(String status){
        StopTimer();
        flag = false;
        txt_status.setText("待机中");
        String timing = String.valueOf(Timing);
        txt_time.setText(timing);
        if (status.equals("平房仓气密性")) {
            if (Timing>=300){
                txt_class.setText("一级");
            } else if (Timing>=240&&Timing<300) {
                txt_class.setText("二级");
            } else if (Timing>=120&&Timing<240) {
                txt_class.setText("三级");
            }
        } else if (status.equals("内薄膜密封粮堆气密性")) {
            if (Timing>=300){
                txt_class.setText("一级");
            } else if (Timing>=150&&Timing<300) {
                txt_class.setText("二级");
            } else if (Timing>=90&&Timing<150) {
                txt_class.setText("三级");
            }
        } else {
            txt_class.setText("标准未知");
        }
    }
    private void initView(View view) {
        txt_test = view.findViewById(R.id.txt_test);
        txt_status = view.findViewById(R.id.txt_status);
        txt_class = view.findViewById(R.id.txt_class);
        txt_time = view.findViewById(R.id.txt_time);
        txt_gas_pa = view.findViewById(R.id.txt_gas_pa);
        txt_start_pa = view.findViewById(R.id.txt_start_pa);
        txt_stop_pa = view.findViewById(R.id.txt_stop_pa);
        txt_final_pa = view.findViewById(R.id.txt_final_pa);
        txt_test_modile = view.findViewById(R.id.txt_test_modile);
        txt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_start_pa.getText().toString().trim().equals("")||txt_stop_pa.getText().toString().trim().equals("")||txt_final_pa.getText().toString().trim().equals("")){
                    util.showToast(getContext(),"请不要输入空值！");
                } else {
                    suoPingDialog = new SuoPingDialog(getContext(), "正在操作，请稍等......");
                    suoPingDialog.setCancelable(true);
                    suoPingDialog.show();
                    initGasPaData("zhuangtai", new OninitGasPaDataFinishedListener() {
                        @Override
                        public void OninitGasPaDataListener(String result) {
                            suoPingDialog.dismiss();
                            if (result.equals("00")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("确认信息");
                                builder.setMessage("开始气压:"+txt_start_pa.getText()+"Pa\n停排气压:"+txt_stop_pa.getText()+"Pa\n结束气压:"+txt_final_pa.getText()+"Pa");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        initGasTight(txt_start_pa.getText().toString(),txt_stop_pa.getText().toString(),txt_final_pa.getText().toString());
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                //创建并显示对话框
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else if (result.equals("01")){
                                util.showToast(getContext(),"正在进行下冲气，请稍后再试！");
                            } else if (result.equals("02")) {
                                util.showToast(getContext(),"正在进行上冲气，请稍后再试！");
                            } else if (result.equals("03")) {
                                util.showToast(getContext(),"正在进行环流，请稍后再试！");
                            } else if (result.equals("04")) {
                                util.showToast(getContext(),"有设备正在进行气密性检测，请稍后再试！");
                            }
                        }
                    });
                }
            }
        });
        txt_test_modile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Dialog != null && Dialog.isShowing()) {
                    Dialog.dismiss();
                }
                // 创建AlertDialog的构建器
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // 获取自定义布局视图
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_button, null);
                // 设置自定义视图
                builder.setView(dialogView);
                TextView txt_up = dialogView.findViewById(R.id.txt_handle);
                TextView txt_down = dialogView.findViewById(R.id.txt_smart);
                txt_up.setText("平房仓气密性");
                txt_down.setText("内薄膜密封粮堆气密性");
                txt_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_test_modile.setText("平房仓气密性");
                        Dialog.dismiss();
                    }
                });
                txt_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_test_modile.setText("内薄膜密封粮堆气密性");
                        Dialog.dismiss();
                    }
                });
                // 创建并显示对话框
                Dialog = builder.create();
                Dialog.setCancelable(true); // 允许用户点击对话框外部区域关闭对话框
                Dialog.show();
            }
        });
    }

    private void initGasTight(String start, String stop, String last) {
        final Handler handler = new Handler(Looper.getMainLooper());
        suoPingDialog = new SuoPingDialog(getContext(), "正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        Profile profile = new Profile();
        profile.setId(qiTiaoBody.getGranaryId());
        profile.setNickname(qiTiaoBody.getNickName());
        ArrayList<String> data = new ArrayList<>();
        data.add(start);
        data.add(stop);
        data.add(last);
        profile.setData(data);
        profile.setMeasure(Collections.singletonList("qitiaoqimixing"));
        NewDownRaw02Body body = new NewDownRaw02Body(
                "02",
                qiTiaoBody.getMoudleName(),
                Collections.singletonList(profile)
        );
        String json = gson.toJson(body);
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=5", json, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                Log.d("jht", "onReqSuccess: there1");
                MeasureId02Bean measureId02Bean = new MeasureId02Bean();
                try {
                    measureId02Bean = gson.fromJson((String) result,MeasureId02Bean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    util.showToast(getContext(),"GasTightFragment读取出错");
                }
                if (measureId02Bean.getData()!=null&&measureId02Bean.getData().getMeasureId()!=null) {
                    String measureId = measureId02Bean.getData().getMeasureId();
                    final Runnable pollRunnable = new Runnable() {
                        @Override
                        public void run() {
                            sendQuest(start,stop,last,measureId);
                        }
                    };
                    handler.postDelayed(pollRunnable,4000);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                util.showToast(getContext(), errorMsg);
            }
        });
    }

    public void sendQuest(String start, String stop, String last,String measureId){
        NewDownRaw03Body newDownRaw03Body = new NewDownRaw03Body(
                "03",
                measureId
        );
        String gsonData = gson.toJson(newDownRaw03Body);
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=30", gsonData, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                Log.d("jht", "onReqSuccess: there2");
                QiTiaoBean qiTiaoBean = new QiTiaoBean();
                try {
                    qiTiaoBean = gson.fromJson((String) result,QiTiaoBean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    util.showToast(getContext(),"N2InputFragment获取数据出错");
                }
                if (qiTiaoBean.getData().getProgress()<1){
                    Log.d("jht", "onReqSuccess: there6");
                    sendQuest(start,stop,last,measureId);
                } else {
                    if (qiTiaoBean.getData().getData()!=null&&qiTiaoBean.getData().getData().size()>0) {
                        Log.d("jht", "onReqSuccess: there3");
                        if (qiTiaoBean.getData().getData().get(0).getHardwareData().get(0).equals("01")) {
                            Log.d("jht", "onReqSuccess: there4");
                            flag = true;
                            txt_status.setText("检测中");
                            Start_Pa = Integer.parseInt(start);
                            Stop_Pa = Integer.parseInt(stop);
                            Final_Pa = Integer.parseInt(last);
                            suoPingDialog.dismiss();
                        } else {
                            Log.d("jht", "onReqSuccess: there5");
                            suoPingDialog.dismiss();
                            util.showToast(getContext(), "操作失败，请稍后再试！");
                        }
                    }
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                util.showToast(getContext(), errorMsg);
            }
        });

    }

    private void STARTTIMER() {
        final String[] now_pa = new String[1];
        initGasPaData("qiya",new OninitGasPaDataFinishedListener() {
            @Override
            public void OninitGasPaDataListener(String result) {
                now_pa[0] = result;
                if (now_pa[0].equals("null")){
                    util.showToast(getContext(),"数据检测出错");
                } else {
                    double Now_Pa = Double.parseDouble(now_pa[0]);
                    if (Now_Pa<=Start_Pa) {
                        StartTimer();
                    }
                    if (Now_Pa>=Final_Pa) {
                        inputNews(txt_test_modile.getText().toString());
                    }
//                    if (txt_test_modile.getText().toString().equals("平房仓气密性")){
//                        if (Now_Pa<=Start_Pa) {
//                            StartTimer();
//                        }
//                        if (Now_Pa<=Final_Pa) {
//                            int pressure = Start_Pa - Final_Pa;
//                            inputNews(pressure);
//
//
//                        }
//                    } else if (txt_test_modile.getText().toString().equals("内薄膜密封粮堆气密性")) {
//                        if (Now_Pa>=Start_Pa) {
//                            StartTimer();
//                        }
//                        if (Now_Pa>=Final_Pa) {
//                            int pressure = Start_Pa - Final_Pa;
//                            inputNews(pressure);
//                        }
//                    }
                }
            }
        });
    }
    public interface OninitGasPaDataFinishedListener{
        void OninitGasPaDataListener(String result);
    }
    private void initGasPaData(String backData, final OninitGasPaDataFinishedListener listener) {
        final Handler handler1 = new Handler(Looper.getMainLooper());
        Profile profile = new Profile();
        profile.setId(qiTiaoBody.getGranaryId());
        List<String> measure = new ArrayList<>();
        measure.add("qitiaoread");
        profile.setMeasure(measure);
        profile.setData(null);
        profile.setNickname(qiTiaoBody.getNickName());
        List<Profile> infos = new ArrayList<>();
        infos.add(profile);
        NewDownRaw02Body newDownRawBody = new NewDownRaw02Body(
                "02",
                qiTiaoBody.getMoudleName(),
                infos
        );
        String jsonData = gson.toJson(newDownRawBody);
        Log.d("zyq", "initGasPaData: "+jsonData);
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=5", jsonData, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                MeasureId02Bean measureId02Bean = new MeasureId02Bean();
                try {
                    measureId02Bean = gson.fromJson((String) result,MeasureId02Bean.class);
                    Log.d("zyq", "initGasPaData: "+result);
                } catch (JsonSyntaxException e){
                    e.printStackTrace();
                    util.showToast(getContext(),"GasControlFragment读取出错");
                }
                if (measureId02Bean.getData()!=null&&measureId02Bean.getData().getMeasureId()!=null) {
                    String measureId = measureId02Bean.getData().getMeasureId();
                    final Runnable pollRunnable = new Runnable() {
                        @Override
                        public void run() {
                            NewDownRaw03Body newDownRaw03Body = new NewDownRaw03Body(
                                    "03",
                                    measureId
                            );
                            String gsonData = gson.toJson(newDownRaw03Body);
                            OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=5", gsonData, Token, new OkHttpUtil.ReqCallBack() {
                                @Override
                                public void onReqSuccess(Object result) throws JSONException {
                                    QiTiaoBean qiTiaoRead = new QiTiaoBean();
                                    try {
                                        qiTiaoRead = gson.fromJson((String) result, QiTiaoBean.class);
                                    } catch (JsonSyntaxException e){
                                        e.printStackTrace();
                                        util.showToast(getContext(),"获取数据出错");
                                    }
                                    if (qiTiaoRead.getData().getProgress()<1){
                                        return;
                                    } else {
                                        if (qiTiaoRead.getData().getData()!=null&&qiTiaoRead.getData().getData().size()>0) {
                                            txt_gas_pa.setText(qiTiaoRead.getData().getData().get(0).getHardwareData().get(1));
                                            Timing = Float.parseFloat(qiTiaoRead.getData().getData().get(0).getHardwareData().get(11));
                                            if (backData.equals("qiya")) {
                                                listener.OninitGasPaDataListener(qiTiaoRead.getData().getData().get(0).getHardwareData().get(1));
                                            } else if (backData.equals("zhuangtai")) {
                                                listener.OninitGasPaDataListener(qiTiaoRead.getData().getData().get(0).getHardwareData().get(2));
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onReqFailed(String errorMsg) {
                                    suoPingDialog.dismiss();
                                    listener.OninitGasPaDataListener("null");
                                    util.showToast(getContext(),errorMsg);
                                }
                            });
                        }
                    };
                    handler1.postDelayed(pollRunnable,2000);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OninitGasPaDataListener("null");
                util.showToast(getContext(),errorMsg);
            }
        });
    }

    private void StopTimer() {
        if (runnable != null) {
            handler.removeCallbacks(runnable); // 停止计时器
        }
    }
    private void StartTimer() {
        runnable = new Runnable() {
            @Override
            public void run() {
                seconds++;
                txt_time.setText(String.valueOf(seconds));
                handler.postDelayed(this, 1000); // 每秒更新一次
            }
        };
        handler.post(runnable); // 启动计时器
    }
}