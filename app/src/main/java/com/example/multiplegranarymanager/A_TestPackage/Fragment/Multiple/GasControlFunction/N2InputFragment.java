package com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.GasControlFunction;

import static com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.deviceType;
import static com.example.multiplegranarymanager.Body.QiTiao.N2AllStatusBody.*;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.CardQiTiaoStatusAdapter;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.Bean.NewDownRaw.MeasureId02Bean;
import com.example.multiplegranarymanager.Bean.QiTiao.QiTiaoBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRaw02Body;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRaw03Body;
import com.example.multiplegranarymanager.Body.NewDownRaw.Profile;
import com.example.multiplegranarymanager.Body.QiTiao.N2AllStatusBody;
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

public class N2InputFragment extends Fragment {
    TextView mFootView;
    ImageView img_fengji_m2_1,img_2,img_f4_3,img_fengji_m1_4,img_5,img_f3_6,img_f2_7,img_fengji_m1_8,img_9,img_f5_10,img_f1_11,img_12;
    ImageView arrow_left_m1_1,arrow_right_m1_1,arrow_up_5,arrow_down_5,arrow_down_f5_10,arrow_left_f3_6,arrow_up_f1_11,arrow_up_f2_7,arrow_up_f4_3,arrow_down_f4_3,arrow_right_m1_4,arrow_right_f4_3;
    TextView txt_modile,txt_status_n2,txt_zhidanji;
    RecyclerView recyclerView;
    private AlertDialog Dialog;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    private Bundle bundle = new Bundle();
    private HeaderViewAdapter headerViewAdapter;
    private QiTiaoBody qiTiaoBody;
    private CardQiTiaoStatusAdapter adapter_Status;
    private ArrayList<Status> allStatus = new ArrayList<>();
    private String N2Id = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function_n2_input, container, false);
        initView(view);
        bundle = getArguments();
        if (bundle!=null){
            qiTiaoBody = bundle.getParcelable("params");
            allStatus.clear();
            if (qiTiaoBody.getGranaryFen()!=null){
                N2Id = qiTiaoBody.getGranaryFen();
            }
            int tag = 1;
            if (qiTiaoBody.getFaMengStatus_list_03()!=null){
                String FaMenNum = qiTiaoBody.getFaMengStatus_list_03();
                for (int i=0;i<FaMenNum.length();i++){
                    Status status = new Status(
                            tag,
                            qiTiaoBody.getQiTiaoStatus_list_02(),
                            "F"+(i+1),
                            qiTiaoBody.getMoudleName(),
                            qiTiaoBody.getGranaryId(),
                            qiTiaoBody.getNickName(),
                            qiTiaoBody.getProductKey(),
                            qiTiaoBody.getDeviceKey(),
                            String.valueOf(FaMenNum.charAt(i))
                    );
                    allStatus.add(status);
                    tag++;
                }
            }
            if (qiTiaoBody.getFengJiStatus_list_04()!=null){
                String FengJiNum = qiTiaoBody.getFengJiStatus_list_04();
                for (int i=0;i<FengJiNum.length();i++){
                    Status status = new Status(
                            tag,
                            qiTiaoBody.getQiTiaoStatus_list_02(),
                            "M"+(i+1),
                            qiTiaoBody.getMoudleName(),
                            qiTiaoBody.getGranaryId(),
                            qiTiaoBody.getNickName(),
                            qiTiaoBody.getProductKey(),
                            qiTiaoBody.getDeviceKey(),
                            String.valueOf(FengJiNum.charAt(i))
                    );
                    allStatus.add(status);
                    tag++;
                }
            }
            N2AllStatusBody body = new N2AllStatusBody(
                    qiTiaoBody.getQiTiaoStatus_list_02(),
                    qiTiaoBody.getN2QiJiStatus_list_05(),
                    allStatus
            );
            initStatus(body, new OnInitStatusFinishedListener() {
                @Override
                public void OnInitStatusListener(boolean success) {
                    initImage(body.getAllStatus());
                    refreshView(getContext(),body.getAllStatus());
                }
            });
        }
        return view;
    }

    private void refreshView(Context context, ArrayList<Status> allStatus) {
        View vfoot = LayoutInflater.from(context).inflate(R.layout.item_list_contact_count,null,false);
        mFootView = vfoot.findViewById(R.id.tv_foot);
        mFootView.setText("加载中......");
        mFootView.setGravity(Gravity.CENTER_HORIZONTAL);
        int[] layoutIds = new int[]{R.layout.adapter_card_n2};
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter_Status = new CardQiTiaoStatusAdapter(allStatus,layoutIds,this);
        headerViewAdapter = new HeaderViewAdapter(adapter_Status);
        headerViewAdapter.addFooterView(vfoot);
        recyclerView.setAdapter(headerViewAdapter);
        mFootView.setText("共"+adapter_Status.getFreshDatas().size()+"台设备");
    }
    public void updateStatus(Status data) {
        for (Status dataitem : allStatus) {
            if (dataitem.getName().equals(data.getName())){
                dataitem.setStatus(data.getStatus());
                dataitem.setName(data.getName());
                initImage(allStatus);
            }
        }
    }
    public interface OnInitStatusFinishedListener{
        void OnInitStatusListener(boolean success);
    }
    public void initStatus(N2AllStatusBody Body, final OnInitStatusFinishedListener listener){

        if (Body.getQiTiaoMuoShi()==null) {
            txt_modile.setText("未知");
        } else if (Body.getQiTiaoMuoShi().equals("00")){
            txt_modile.setText("手动模式");
        } else if (Body.getQiTiaoMuoShi().equals("01")) {
            txt_modile.setText("智能模式——>自动上充");
        } else if (Body.getQiTiaoMuoShi().equals("02")) {
            txt_modile.setText("智能模式——>自动下充");
        } else if (Body.getQiTiaoMuoShi().equals("03")) {
            txt_modile.setText("智能模式——>环流");
        } else if (Body.getQiTiaoMuoShi().equals("04")) {
            txt_modile.setText("自动气密性检测");
        } else {
            txt_modile.setText("未知");
        }

        if (Body.getDanQiJi().equals("0")){
            txt_status_n2.setText("关闭");
            txt_status_n2.setTextColor(Color.RED);
        } else if (Body.getDanQiJi().equals("0")) {
            txt_status_n2.setText("开启");
            txt_status_n2.setTextColor(Color.GREEN);
        } else {
            txt_status_n2.setText("未知");
            txt_status_n2.setTextColor(Color.BLACK);
        }

        listener.OnInitStatusListener(true);
    }
    private void initView(View view) {

        img_fengji_m2_1 = view.findViewById(R.id.img_fengji_m2_1);
        img_2 = view.findViewById(R.id.img_2);
        img_f4_3 = view.findViewById(R.id.img_f4_3);
        img_fengji_m1_4 = view.findViewById(R.id.img_fengji_m1_4);
        img_5 = view.findViewById(R.id.img_5);
        img_f3_6 = view.findViewById(R.id.img_f3_6);
        img_f2_7 = view.findViewById(R.id.img_f2_7);
        img_fengji_m1_8 = view.findViewById(R.id.img_fengji_m1_8);
        img_9 = view.findViewById(R.id.img_9);
        img_f5_10 = view.findViewById(R.id.img_f5_10);
        img_f1_11 = view.findViewById(R.id.img_f1_11);
        img_12 = view.findViewById(R.id.img_12);

        txt_modile = view.findViewById(R.id.txt_modile);
        txt_status_n2 = view.findViewById(R.id.txt_status_n2);
        txt_zhidanji = view.findViewById(R.id.txt_zhidanji);

        recyclerView = view.findViewById(R.id.recyclerview);

        arrow_left_m1_1 = view.findViewById(R.id.txt_arrow_left_m1_1);
        arrow_right_m1_1 = view.findViewById(R.id.txt_arrow_right_m1_1);
        arrow_up_5 = view.findViewById(R.id.txt_arrow_up_5);
        arrow_down_5 = view.findViewById(R.id.txt_arrow_down_5);
        arrow_down_f5_10 = view.findViewById(R.id.txt_arrow_down_f5_10);
        arrow_left_f3_6 = view.findViewById(R.id.txt_arrow_left_f3_6);
        arrow_up_f1_11 = view.findViewById(R.id.txt_arrow_up_f1_11);
        arrow_up_f2_7 = view.findViewById(R.id.txt_arrow_up_f2_7);
        arrow_up_f4_3 = view.findViewById(R.id.txt_arrow_up_f4_3);
        arrow_down_f4_3 = view.findViewById(R.id.txt_arrow_down_f4_3);
        arrow_right_f4_3 = view.findViewById(R.id.txt_arrow_right_f4_3);
        arrow_right_m1_4 = view.findViewById(R.id.txt_arrow_right_m1_4);

        txt_zhidanji.setSelected(true);

        txt_modile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFirstClickDialog();
            }
        });
        txt_status_n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qiTiaoBody.getQiTiaoStatus_list_02().equals("04")){
                    util.showToast(getContext(),"正在进行密闭性检测，请不要操作！");
                } else {
                    String measure = "";
                    if (txt_status_n2.getText().equals("关闭")){
                        measure = "qitiaodanqijikai";
                        initN2(measure,"关闭");
                    } else if (txt_status_n2.getText().equals("开启")) {
                        measure = "qitiaodanqijiguan";
                        initN2(measure,"开启");
                    } else if (txt_status_n2.getText().equals("未知")) {
                        util.showToast(getContext(),"状态未知，请获取状态后再试");
                    }
                }
            }
        });
    }
    public void initN2(String data, String status){
        Profile profile = new Profile();
        profile.setId(qiTiaoBody.getGranaryId());
        profile.setNickname(qiTiaoBody.getNickName());
        profile.setData(Collections.singletonList(N2Id));
        profile.setMeasure(Collections.singletonList(data));
        NewDownRaw02Body newDownRaw02Body = new NewDownRaw02Body(
                "02",
                qiTiaoBody.getMoudleName(),
                Collections.singletonList(profile)
        );
        String json = gson.toJson(newDownRaw02Body);
        final Handler handler = new Handler(Looper.getMainLooper());
        suoPingDialog = new SuoPingDialog(getContext(), "正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=5", json, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                MeasureId02Bean measureId02Bean = new MeasureId02Bean();
                try {
                    measureId02Bean = gson.fromJson((String) result,MeasureId02Bean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    util.showToast(getContext(),"N2InputFragment读取出错");
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
                                    QiTiaoBean qiTiaoBean = new QiTiaoBean();
                                    try {
                                        qiTiaoBean = gson.fromJson((String) result,QiTiaoBean.class);
                                    } catch (JsonSyntaxException e) {
                                        e.printStackTrace();
                                        util.showToast(getContext(),"N2InputFragment获取数据出错");
                                    }
                                    if (qiTiaoBean.getData().getProgress()<1){
                                        return;
                                    } else {
                                        if (qiTiaoBean.getData().getData()!=null&&qiTiaoBean.getData().getData().size()>0) {
                                            if (qiTiaoBean.getData().getData().get(0).getHardwareData().get(0).equals("00")) {
                                                if (status.equals("开启")){
                                                    txt_status_n2.setText("关闭");
                                                    txt_status_n2.setTextColor(Color.RED);
                                                } else if (status.equals("关闭")){
                                                    txt_status_n2.setText("开启");
                                                    txt_status_n2.setTextColor(Color.GREEN);
                                                }
                                            } else {
                                                util.showToast(getContext(), "操作失败，请稍后再试！");
                                            }
                                        }
                                    }
                                    suoPingDialog.dismiss();
                                }

                                @Override
                                public void onReqFailed(String errorMsg) {
                                    util.showToast(getContext(), errorMsg);
                                }
                            });
                        }
                    };
                    handler.postDelayed(pollRunnable,2000);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                util.showToast(getContext(), errorMsg);
            }
        });
    }
    private void showFirstClickDialog() {
        // 创建AlertDialog的构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // 获取自定义布局视图
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_button, null);
        // 设置自定义视图
        builder.setView(dialogView);
        TextView txt_handle = dialogView.findViewById(R.id.txt_handle);
        TextView txt_smart = dialogView.findViewById(R.id.txt_smart);
        txt_handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qiTiaoBody.getQiTiaoStatus_list_02().equals("00")){
                    Dialog.dismiss();
                    util.showToast(getContext(),"已是手动模式，请不要重复操作！");
                } else if (qiTiaoBody.getQiTiaoStatus_list_02().equals("04")) {
                    Dialog.dismiss();
                    util.showToast(getContext(),"正在进行密闭性检测，请不要操作！");
                } else {
                    initQiTiaoStatus("00","0","0");
                }
//                adapter_Status.notifyDataSetChanged();
            }
        });
        txt_smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartModile(); // 弹出第二个提示框
            }
        });
        // 创建并显示对话框
        Dialog = builder.create();
        Dialog.setCancelable(true); // 允许用户点击对话框外部区域关闭对话框
        Dialog.show();
    }
    private void SmartModile() {
        if (Dialog != null && Dialog.isShowing()) {
            Dialog.dismiss();
        }
        // 创建AlertDialog的构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // 获取自定义布局视图
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_button_modile, null);
        // 设置自定义视图
        builder.setView(dialogView);
        TextView txt_up = dialogView.findViewById(R.id.txt_handle);
        TextView txt_down = dialogView.findViewById(R.id.txt_smart);
        TextView txt_circle = dialogView.findViewById(R.id.txt_circle);
        txt_up.setText("上充气法");
        txt_down.setText("下充气法");
        txt_circle.setText("环流");
        txt_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qiTiaoBody.getQiTiaoStatus_list_02().equals("02")){
                    Dialog.dismiss();
                    util.showToast(getContext(),"已是上充气法，请不要重复操作！");
                } else if (qiTiaoBody.getQiTiaoStatus_list_02().equals("04")) {
                    Dialog.dismiss();
                    util.showToast(getContext(),"正在进行密闭性检测，请不要操作！");
                } else {
                    SetDialog(txt_up);
                }
            }
        });
        txt_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qiTiaoBody.getQiTiaoStatus_list_02().equals("01")){
                    Dialog.dismiss();
                    util.showToast(getContext(),"已是下充气法，请不要重复操作！");
                } else if (qiTiaoBody.getQiTiaoStatus_list_02().equals("04")) {
                    Dialog.dismiss();
                    util.showToast(getContext(),"正在进行密闭性检测，请不要操作！");
                } else {
                    SetDialog(txt_down);
                }
            }
        });
        //环流充气的方法等同于下充气法
        txt_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qiTiaoBody.getQiTiaoStatus_list_02().equals("01")){
                    Dialog.dismiss();
                    util.showToast(getContext(),"已是环流，请不要重复操作！");
                } else if (qiTiaoBody.getQiTiaoStatus_list_02().equals("04")) {
                    Dialog.dismiss();
                    util.showToast(getContext(),"正在进行密闭性检测，请不要操作！");
                } else {
                    SetDialog(txt_circle);
                }
            }
        });
        // 创建并显示对话框
        Dialog = builder.create();
        Dialog.setCancelable(true); // 允许用户点击对话框外部区域关闭对话框
        Dialog.show();
    }

    private void SetDialog(TextView txt) {
        if (Dialog != null && Dialog.isShowing()) {
            Dialog.dismiss();
        }
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_n2_tiaojian,null);
        final EditText DanQiNongDu = view.findViewById(R.id.et_1);
        final EditText Pressure = view.findViewById(R.id.et_2);

        new AlertDialog.Builder(getContext())
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tiaojian_1 = DanQiNongDu.getText().toString();
                        String tiaojian_2 = Pressure.getText().toString();
                        if (!tiaojian_1.isEmpty()&&!tiaojian_2.isEmpty()){
                            int value1 = Integer.parseInt(tiaojian_1);
                            if (value1 >= 0 && value1 <= 100) {
                                if (txt.getText().equals("上充气法")) {
                                    initQiTiaoStatus("02", tiaojian_1, tiaojian_2);
                                    adapter_Status.notifyDataSetChanged();
                                } else if (txt.getText().equals("下充气法")) {
                                    initQiTiaoStatus("01", tiaojian_1, tiaojian_2);
                                    adapter_Status.notifyDataSetChanged();
                                } else if (txt.getText().equals("环流")) {
                                    initQiTiaoStatus("03", tiaojian_1, tiaojian_2);
                                    adapter_Status.notifyDataSetChanged();
                                }
                            } else {
                                util.showToast(getContext(), "氮气浓度请输入0~100之间的值");
                            }
                        } else {
                            util.showToast(getContext(),"请不要输入空值");
                        }
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }

    private void GasInCircle(String tiaojian1, String tiaojian2) {
        arrow_left_m1_1.setVisibility(View.GONE);
        arrow_right_m1_1.setVisibility(View.VISIBLE);
        arrow_up_5.setVisibility(View.GONE);
        arrow_down_5.setVisibility(View.VISIBLE);
        arrow_down_f5_10.setVisibility(View.VISIBLE);
        arrow_left_f3_6.setVisibility(View.GONE);
        arrow_up_f1_11.setVisibility(View.VISIBLE);
        arrow_up_f2_7.setVisibility(View.VISIBLE);
        arrow_up_f4_3.setVisibility(View.VISIBLE);
        arrow_down_f4_3.setVisibility(View.GONE);
        arrow_right_m1_4.setVisibility(View.GONE);
        arrow_right_f4_3.setVisibility(View.GONE);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(arrow_right_m1_1,"translationX",-150f,50f);
        animator1.setDuration(2000);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.start();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(arrow_down_5,"translationY",-100f,50f);
        animator2.setDuration(2000);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.start();
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(arrow_down_f5_10,"translationY",-100f,50f);
        animator3.setDuration(2000);
        animator3.setRepeatCount(ValueAnimator.INFINITE);
        animator3.setRepeatMode(ValueAnimator.REVERSE);
        animator3.start();
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(arrow_up_f1_11,"translationY",-100f,50f);
        animator4.setDuration(2000);
        animator4.setRepeatCount(ValueAnimator.INFINITE);
        animator4.setRepeatMode(ValueAnimator.REVERSE);
        animator4.start();
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(arrow_up_f2_7,"translationY",-100f,50f);
        animator5.setDuration(2000);
        animator5.setRepeatCount(ValueAnimator.INFINITE);
        animator5.setRepeatMode(ValueAnimator.REVERSE);
        animator5.start();
        ObjectAnimator animator6 = ObjectAnimator.ofFloat(arrow_up_f4_3,"translationY",-50f,50f);
        animator6.setDuration(2000);
        animator6.setRepeatCount(ValueAnimator.INFINITE);
        animator6.setRepeatMode(ValueAnimator.REVERSE);
        animator6.start();
        txt_modile.setText("智能模式->环流");
        for (Status data : allStatus){
            data.setModuile("03");
            if (data.getName().equals("F1")||data.getName().equals("F2")||data.getName().equals("F5")){
                data.setStatus("1");
            } else if (data.getName().equals("F3")||data.getName().equals("F4")||data.getName().equals("M1")) {
                data.setStatus("0");
            } else if (data.getName().equals("M2")) {
                data.setStatus("1");
            }
        }
        Dialog.dismiss();
        initImage(allStatus);
    }

    private void GasInDown(String finalTiaojian_1, String finalTiaojian_2) {
        arrow_left_m1_1.setVisibility(View.GONE);
        arrow_right_m1_1.setVisibility(View.VISIBLE);
        arrow_up_5.setVisibility(View.GONE);
        arrow_down_5.setVisibility(View.VISIBLE);
        arrow_down_f5_10.setVisibility(View.VISIBLE);
        arrow_left_f3_6.setVisibility(View.GONE);
        arrow_up_f1_11.setVisibility(View.VISIBLE);
        arrow_up_f2_7.setVisibility(View.VISIBLE);
        arrow_up_f4_3.setVisibility(View.VISIBLE);
        arrow_down_f4_3.setVisibility(View.GONE);
        arrow_right_m1_4.setVisibility(View.GONE);
        arrow_right_f4_3.setVisibility(View.GONE);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(arrow_right_m1_1,"translationX",-150f,50f);
        animator1.setDuration(2000);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.start();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(arrow_down_5,"translationY",-100f,50f);
        animator2.setDuration(2000);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.start();
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(arrow_down_f5_10,"translationY",-100f,50f);
        animator3.setDuration(2000);
        animator3.setRepeatCount(ValueAnimator.INFINITE);
        animator3.setRepeatMode(ValueAnimator.REVERSE);
        animator3.start();
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(arrow_up_f1_11,"translationY",-100f,50f);
        animator4.setDuration(2000);
        animator4.setRepeatCount(ValueAnimator.INFINITE);
        animator4.setRepeatMode(ValueAnimator.REVERSE);
        animator4.start();
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(arrow_up_f2_7,"translationY",-100f,50f);
        animator5.setDuration(2000);
        animator5.setRepeatCount(ValueAnimator.INFINITE);
        animator5.setRepeatMode(ValueAnimator.REVERSE);
        animator5.start();
        ObjectAnimator animator6 = ObjectAnimator.ofFloat(arrow_up_f4_3,"translationY",-50f,50f);
        animator6.setDuration(2000);
        animator6.setRepeatCount(ValueAnimator.INFINITE);
        animator6.setRepeatMode(ValueAnimator.REVERSE);
        animator6.start();
        txt_modile.setText("智能模式->下充气法");
        for (Status data : allStatus){
            data.setModuile("01");
            if (data.getName().equals("F1")||data.getName().equals("F2")||data.getName().equals("F5")){
                data.setStatus("1");
            } else if (data.getName().equals("F3")||data.getName().equals("F4")||data.getName().equals("M1")) {
                data.setStatus("0");
            } else if (data.getName().equals("M2")) {
                data.setStatus("1");
            }
        }
        Dialog.dismiss();
        initImage(allStatus);
    }

    private void GasInUp(String finalTiaojian_, String finalTiaojian_1) {
        txt_modile.setText("智能模式->上充气法");
        arrow_left_m1_1.setVisibility(View.VISIBLE);

        arrow_right_m1_1.setVisibility(View.GONE);
        arrow_up_5.setVisibility(View.VISIBLE);
        arrow_down_5.setVisibility(View.GONE);
        arrow_down_f5_10.setVisibility(View.GONE);
        arrow_left_f3_6.setVisibility(View.VISIBLE);
        arrow_up_f1_11.setVisibility(View.VISIBLE);
        arrow_up_f2_7.setVisibility(View.GONE);
        arrow_up_f4_3.setVisibility(View.GONE);
        arrow_down_f4_3.setVisibility(View.VISIBLE);
        arrow_right_m1_4.setVisibility(View.VISIBLE);
        arrow_right_f4_3.setVisibility(View.VISIBLE);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(arrow_left_m1_1,"translationX",-150f,50f);
        animator1.setDuration(2000);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.start();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(arrow_up_5,"translationY",-100f,50f);
        animator2.setDuration(2000);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.start();
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(arrow_left_f3_6,"translationX",-100f,150f);
        animator3.setDuration(2000);
        animator3.setRepeatCount(ValueAnimator.INFINITE);
        animator3.setRepeatMode(ValueAnimator.REVERSE);
        animator3.start();
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(arrow_up_f1_11,"translationY",-100f,100f);
        animator4.setDuration(2000);
        animator4.setRepeatCount(ValueAnimator.INFINITE);
        animator4.setRepeatMode(ValueAnimator.REVERSE);
        animator4.start();
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(arrow_down_f4_3,"translationY",-100f,50f);
        animator5.setDuration(2000);
        animator5.setRepeatCount(ValueAnimator.INFINITE);
        animator5.setRepeatMode(ValueAnimator.REVERSE);
        animator5.start();
        ObjectAnimator animator6 = ObjectAnimator.ofFloat(arrow_right_m1_4,"translationX",-50f,100f);
        animator6.setDuration(2000);
        animator6.setRepeatCount(ValueAnimator.INFINITE);
        animator6.setRepeatMode(ValueAnimator.REVERSE);
        animator6.start();
        ObjectAnimator animator7 = ObjectAnimator.ofFloat(arrow_right_f4_3,"translationX",-100f,100f);
        animator7.setDuration(2000);
        animator7.setRepeatCount(ValueAnimator.INFINITE);
        animator7.setRepeatMode(ValueAnimator.REVERSE);
        animator7.start();
        for (Status data : allStatus){
            data.setModuile("02");
            if (data.getName().equals("F1")||data.getName().equals("F3")||data.getName().equals("F4")){
                data.setStatus("1");
            } else if (data.getName().equals("F2")||data.getName().equals("F5")) {
                data.setStatus("0");
            } else if (data.getName().equals("M1")||data.getName().equals("M2")) {
                data.setStatus("1");
            }
        }
        Dialog.dismiss();
        initImage(allStatus);
    }

    private void HandModile() {
        Dialog.dismiss();
        arrow_left_m1_1.setVisibility(View.GONE);
        arrow_right_m1_1.setVisibility(View.GONE);
        arrow_up_5.setVisibility(View.GONE);
        arrow_down_5.setVisibility(View.GONE);
        arrow_down_f5_10.setVisibility(View.GONE);
        arrow_left_f3_6.setVisibility(View.GONE);
        arrow_up_f1_11.setVisibility(View.GONE);
        arrow_up_f2_7.setVisibility(View.GONE);
        arrow_up_f4_3.setVisibility(View.GONE);
        arrow_down_f4_3.setVisibility(View.GONE);
        arrow_right_m1_4.setVisibility(View.GONE);
        arrow_right_f4_3.setVisibility(View.GONE);
        txt_modile.setText("手动模式");
        for (Status data : allStatus){
            data.setModuile("00");
        }
        initImage(allStatus);
    }
    public void initQiTiaoStatus(String status,String CN2,String pressure){

        final Handler handler = new Handler(Looper.getMainLooper());
        suoPingDialog = new SuoPingDialog(getContext(), "正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        Profile profile = new Profile();
        profile.setId(qiTiaoBody.getGranaryId());
        profile.setNickname(qiTiaoBody.getNickName());
        ArrayList<String> list = new ArrayList<>();
        list.add(status);
        //切换为手动模式时，氮气浓度和压力指标不需要填写，这里默认为0
        list.add(CN2);
        list.add(pressure);
        profile.setData(list);
        profile.setMeasure(Collections.singletonList("qitiaotype"));
        NewDownRaw02Body newDownRaw02Body = new NewDownRaw02Body(
                "02",
                qiTiaoBody.getMoudleName(),
                Collections.singletonList(profile)
        );
        String json = gson.toJson(newDownRaw02Body);
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=5", json, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                MeasureId02Bean measureId02Bean = new MeasureId02Bean();
                try {
                    measureId02Bean = gson.fromJson((String) result,MeasureId02Bean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    util.showToast(getContext(), "CardQiTiaoStatusAdapter读取出错");
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
                            String gsondata = gson.toJson(newDownRaw03Body);
                            OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=5", gsondata, Token, new OkHttpUtil.ReqCallBack() {
                                @Override
                                public void onReqSuccess(Object result) throws JSONException {
                                    QiTiaoBean qiTiaoBean = new QiTiaoBean();
                                    try {
                                        qiTiaoBean = gson.fromJson((String) result,QiTiaoBean.class);
                                    } catch (JsonSyntaxException e) {
                                        e.printStackTrace();
                                        util.showToast(getContext(), "CardQiTiaoStatusAdapter获取数据出错");
                                    }
                                    if (qiTiaoBean.getData().getProgress()<1){
                                        return;
                                    } else {
                                        if (qiTiaoBean.getData().getData()!=null&&qiTiaoBean.getData().getData().size()>0) {
                                            if (qiTiaoBean.getData().getData().get(0).getHardwareData().get(0)!=null) {
                                                if (qiTiaoBean.getData().getData().get(0).getHardwareData().get(0).equals("00")) {
                                                    if (status.equals("00")){
                                                        HandModile();
                                                    } else if (status.equals("01")){
                                                        GasInDown(CN2, pressure);
                                                    } else if (status.equals("02")){
                                                        GasInUp(CN2, pressure);
                                                    } else if (status.equals("03")){
                                                        GasInCircle(CN2, pressure);
                                                    }
                                                }
                                            } else {
                                                util.showToast(getContext(), "操作失败，请稍后再试！");
                                            }
                                        }
                                    }
                                    suoPingDialog.dismiss();
                                    adapter_Status.notifyDataSetChanged();
                                }

                                @Override
                                public void onReqFailed(String errorMsg) {
                                    suoPingDialog.dismiss();
                                    util.showToast(getContext(), errorMsg);
                                }
                            });
                        }
                    };
                    handler.postDelayed(pollRunnable,2000);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                util.showToast(getContext(), errorMsg);
            }
        });
    }
    private void initImage(List<Status> statusBodiesList) {
        for (Status data : statusBodiesList){
            if (data.getName().equals("F1")){
                if (data.getStatus().equals("0")){
                    img_f1_11.setImageResource(R.drawable.zzzz_one_11_f1);
                } else if (data.getStatus().equals("1")) {
                    img_f1_11.setImageResource(R.drawable.zzzz_two_11_f1);
                }
            }
            if (data.getName().equals("F2")){
                if (data.getStatus().equals("0")){
                    img_f2_7.setImageResource(R.drawable.zzzz_one_7_f2);
                } else if (data.getStatus().equals("1")) {
                    img_f2_7.setImageResource(R.drawable.zzzz_two_7_f2);
                }
            }
            if (data.getName().equals("F3")){
                if (data.getStatus().equals("0")){
                    img_f3_6.setImageResource(R.drawable.zzzz_one_6_f3);
                } else if (data.getStatus().equals("1")) {
                    img_f3_6.setImageResource(R.drawable.zzzz_two_6_f3);
                }
            }
            if (data.getName().equals("F4")){
                if (data.getStatus().equals("0")){
                    img_f4_3.setImageResource(R.drawable.zzzz_one_3_f4);
                } else if (data.getStatus().equals("1")) {
                    img_f4_3.setImageResource(R.drawable.zzzz_two_3_f4);
                }
            }
            if (data.getName().equals("F5")){
                if (data.getStatus().equals("0")){
                    img_f5_10.setImageResource(R.drawable.zzzz_one_10_f5);
                } else if (data.getStatus().equals("1")) {
                    img_f5_10.setImageResource(R.drawable.zzzz_two_10_f5);
                }
            }
            if (data.getName().equals("M1")){
                if (data.getStatus().equals("0")){
                    img_fengji_m1_4.setImageResource(R.drawable.zzzz_one_4_fengji_m1);
                    img_fengji_m1_8.setImageResource(R.drawable.zzzz_one_8_fengji_m1);
                } else if (data.getStatus().equals("1")) {
                    img_fengji_m1_4.setImageResource(R.drawable.zzzz_two_4_fengji_m1);
                    img_fengji_m1_8.setImageResource(R.drawable.zzzz_two_8_fengji_m1);
                }
            }
            if (data.getName().equals("M2")){
                if (data.getStatus().equals("0")){
                    img_fengji_m2_1.setImageResource(R.drawable.zzzz_one_1_fengji_m2);
                } else if (data.getStatus().equals("1")) {
                    img_fengji_m2_1.setImageResource(R.drawable.zzzz_two_1_fengji_m2);
                }
            }
        }
    }
}