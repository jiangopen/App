package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.AirCondition;


import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.AirConditionActivity.intToHex;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;
import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.AirCondition.AirConditionStatusBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CardAirConditionAdapterBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.util.InputInfo;
import com.kongzue.dialog.v2.InputDialog;
import com.kongzue.dialog.v2.Notification;

import org.json.JSONException;

import java.util.ArrayList;

public class WuWeiAirConditionDataAdapter extends MultiLayoutAdapter<CardAirConditionAdapterBody> implements SectionIndexer, View.OnClickListener {
    private ArrayList<CardAirConditionAdapterBody> datas;
    private ArrayList<CardAirConditionAdapterBody> refreshdatas;
    private Context context;
    private String temp;
    private int notifactionType = TYPE_NORMAL;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    public WuWeiAirConditionDataAdapter(ArrayList<CardAirConditionAdapterBody> mDatas, int[] layoutIds, Context context) {
        super(mDatas, layoutIds);
        this.datas = mDatas;
        this.refreshdatas = mDatas;
        this.context = context;
        if (datas == null){
            datas = new ArrayList<>(0);
        }
    }
    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        CardAirConditionAdapterBody item = getItem(position-1);
        NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
        newDownRawBody.setUrl("/measure");
        newDownRawBody.setMethod("POST");
        newDownRawBody.setQuery(null);
        newDownRawBody.setHeaders(null);
        CountMultiple countMultiple = new CountMultiple();
        countMultiple.setCommandMapId(item.getCommandMapId());
        countMultiple.setUrl(item.getUrl());
        final String[] body = {
                  "AA55"
                + "FF"
                + "0A"
                + "FFFF"
                + "0005"
                + item.getInspurAddress()
        };
        if (v.getId()== R.id.txt_cold_open_or_close){
            Log.d("zyq", "onClick制冷: "+item.getName());
            initTempDataDialog("制冷", new OnInitTempDataFinishedListener() {
                @Override
                public void OnInitTempDataListener(String num) {
                    body[0] +=
                            "01"
                            + "02"
                            + intToHex(Integer.parseInt(num),2)
                            + "FF"
                            + "FFFF"
                            + "0D0A";
                    Log.d("zyq", "body: "+ body[0]);
                    countMultiple.setCommandContent(body[0]);
                    newDownRawBody.setBody(countMultiple);
                    String jsonData = gson.toJson(newDownRawBody);
                    initTestInTime("制冷",jsonData,item, new OnInitTestInTimeFinishedListener() {
                        @Override
                        public void OnInitTestInTimeListener(boolean success) {
                            suoPingDialog.dismiss();
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        } else if (v.getId()==R.id.txt_hot_open_or_close) {
            Log.d("zyq", "onClick制热: "+item.getName());
            initTempDataDialog("制热", new OnInitTempDataFinishedListener() {
                @Override
                public void OnInitTempDataListener(String num) {
                    body[0] +=
                              "02"
                            + "02"
                            + intToHex(Integer.parseInt(num),2)
                            + "FF"
                            + "FFFF"
                            + "0D0A";
                    Log.d("zyq", "body: "+ body[0]);
                    countMultiple.setCommandContent(body[0]);
                    newDownRawBody.setBody(countMultiple);
                    String jsonData = gson.toJson(newDownRawBody);
                    initTestInTime("制热",jsonData, item, new OnInitTestInTimeFinishedListener() {
                        @Override
                        public void OnInitTestInTimeListener(boolean success) {
                            suoPingDialog.dismiss();
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        } else if (v.getId()==R.id.txt_dehumidify_open_or_close) {
            Log.d("zyq", "onClick除湿: "+item.getName());
            initTempDataDialog("除湿", new OnInitTempDataFinishedListener() {
                @Override
                public void OnInitTempDataListener(String num) {
                    body[0] +=
                            "03"
                            + "02"
                            + intToHex(Integer.parseInt(num),2)
                            + "FF"
                            + "FFFF"
                            + "0D0A";
                    Log.d("zyq", "body: "+ body[0]);
                    countMultiple.setCommandContent(body[0]);
                    newDownRawBody.setBody(countMultiple);
                    String jsonData = gson.toJson(newDownRawBody);
                    initTestInTime("除湿",jsonData, item, new OnInitTestInTimeFinishedListener() {
                        @Override
                        public void OnInitTestInTimeListener(boolean success) {
                            suoPingDialog.dismiss();
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        } else if (v.getId()==R.id.txt_wind_open_or_close) {
            Log.d("zyq", "onClick通风: "+item.getName());
            initTempDataDialog("通风", new OnInitTempDataFinishedListener() {
                @Override
                public void OnInitTempDataListener(String num) {
                    body[0] +=
                              "04"
                            + "02"
                            + intToHex(Integer.parseInt(num),2)
                            + "FF"
                            + "FFFF"
                            + "0D0A";
                    Log.d("zyq", "body: "+ body[0]);
                    countMultiple.setCommandContent(body[0]);
                    newDownRawBody.setBody(countMultiple);
                    String jsonData = gson.toJson(newDownRawBody);
                    initTestInTime("通风",jsonData, item, new OnInitTestInTimeFinishedListener() {
                        @Override
                        public void OnInitTestInTimeListener(boolean success) {
                            suoPingDialog.dismiss();
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        } else if (v.getId()==R.id.txt_close) {
            Log.d("zyq", "onClick关机: "+item.getName());
            initTempDataDialog("关机", new OnInitTempDataFinishedListener() {
                @Override
                public void OnInitTempDataListener(String num) {
                    body[0] +=
                              "18"
                            + "01"
                            + intToHex(Integer.parseInt(num),2)
                            + "FF"
                            + "FFFF"
                            + "0D0A";
                    Log.d("zyq", "body: "+ body[0]);
                    countMultiple.setCommandContent(body[0]);
                    newDownRawBody.setBody(countMultiple);
                    String jsonData = gson.toJson(newDownRawBody);
                    initTestInTime("关机",jsonData, item, new OnInitTestInTimeFinishedListener() {
                        @Override
                        public void OnInitTestInTimeListener(boolean success) {
                            suoPingDialog.dismiss();
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }
    public interface OnInitTestInTimeFinishedListener{
        void OnInitTestInTimeListener(boolean success);
    }
    public void initTestInTime(String type, String json, CardAirConditionAdapterBody item, final OnInitTestInTimeFinishedListener listener){
        suoPingDialog = new SuoPingDialog(context,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", json, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                AirConditionStatusBean airConditionStatusBean = new AirConditionStatusBean();
                try {
                    airConditionStatusBean = gson.fromJson((String) result,AirConditionStatusBean.class);
                } catch (JsonSyntaxException e){
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util.showToast(context,airConditionStatusBean.getMsg());
                }
                if (type.equals("制冷")){
                    if (airConditionStatusBean.getData().getColdOpenStatus()!=null&&airConditionStatusBean.getData().getColdOpenStatus().size()>0){
                        if (airConditionStatusBean.getData().getColdOpenStatus().get(0).equals("00")){
                            item.setStatus("1");
                        }
                    }
                } else if (type.equals("制热")) {
                    if (airConditionStatusBean.getData().getHotOpenStatus()!=null&&airConditionStatusBean.getData().getHotOpenStatus().size()>0){
                        if (airConditionStatusBean.getData().getHotOpenStatus().get(0).equals("00")){
                            item.setStatus("2");
                        }
                    }
                } else if (type.equals("除湿")) {
                    if (airConditionStatusBean.getData().getDehumidifierOpenStatus()!=null&&airConditionStatusBean.getData().getDehumidifierOpenStatus().size()>0){
                        if (airConditionStatusBean.getData().getDehumidifierOpenStatus().get(0).equals("00")){
                            item.setStatus("3");
                        }
                    }
                } else if (type.equals("通风")) {
                    if (airConditionStatusBean.getData().getWindOpenStatus()!=null&&airConditionStatusBean.getData().getWindOpenStatus().size()>0){
                        if (airConditionStatusBean.getData().getWindOpenStatus().get(0).equals("00")){
                            item.setStatus("4");
                        }
                    }
                } else if (type.equals("关机")) {
                    if (airConditionStatusBean.getData().getCloseStatus()!=null&&airConditionStatusBean.getData().getCloseStatus().size()>0){
                        if (airConditionStatusBean.getData().getCloseStatus().get(0).equals("00")){
                            item.setStatus("0");
                        }
                    }
                }
                if (listener!=null){
                    listener.OnInitTestInTimeListener(true);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                suoPingDialog.dismiss();
                listener.OnInitTestInTimeListener(false);
                util.showToast(context,errorMsg);
            }
        });
    }
    public interface OnInitTempDataFinishedListener{
        void OnInitTempDataListener(String num);
    }
    private void initTempDataDialog(String type,final OnInitTempDataFinishedListener listener) {
        String etText = "25";
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_custom,null);
        final TextView textView = view.findViewById(R.id.tv_title_password);
        textView.setText("输入"+type+"温度"+"16~26之间");
        InputDialog.show(context, null, null, new InputDialogOkButtonClickListener() {
            @Override
            public void onClick(Dialog dialog, String inputText) {
                if (inputText.equals("")){
                    Notification.show(context,0,"输入不能为空");
                } else if (Integer.parseInt(inputText)<16||Integer.parseInt(inputText)>26) {
                    util.showToast(context,"请输入16~26之间的值");
                } else {
                    dialog.dismiss();
                    listener.OnInitTempDataListener(inputText);
                }
            }
        }).setInputInfo(new InputInfo().setMAX_LENGTH(2).setInputType(InputType.TYPE_CLASS_NUMBER))
                .setCanCancel(true)
                .setCustomView(view)
                .setDefaultInputText(etText);
    }
    @Override
    public Object[] getSections() {
        return null;
    }
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i=0;i<getItemCount();i++){
            int firstChar = datas.get(i).getName().charAt(0);
            if (firstChar == sectionIndex){
                return i;
            }
        }
        return -1;
    }
    @Override
    public int getSectionForPosition(int position) {
        CardAirConditionAdapterBody item = datas.get(position);
        return item.getName().charAt(0);
    }
    @Override
    protected int getItemType(int position) {
        return 0;
    }
    public void setFreshDatas(ArrayList<CardAirConditionAdapterBody> datas){
        this.refreshdatas = datas;
    }
    public ArrayList<CardAirConditionAdapterBody> getFreshDatas(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }
    @Override
    protected void coverts(BaseViewHolder holder, CardAirConditionAdapterBody item, int position, int itemType) {
        if (item!=null){
            holder.setText(R.id.txt_name,item.getName());
            TextView txt_tem = holder.getView(R.id.txt_tem_num);
            TextView txt_hum = holder.getView(R.id.txt_hum_num);
            TextView txt_status = holder.getView(R.id.txt_status_num);
            txt_tem.setSelected(true);
            txt_hum.setSelected(true);
            txt_status.setSelected(true);
            if (item.getTemp()==null){
                holder.setText(R.id.txt_tem_num,"——");
            } else if (item.getTemp().equals("255")) {
                holder.setText(R.id.txt_tem_num,"断路");
            } else {
                holder.setText(R.id.txt_tem_num,item.getTemp());
            }
            if (item.getHumidity()==null){
                holder.setText(R.id.txt_hum_num,"——");
            } else if (item.getTemp().equals("255")) {
                holder.setText(R.id.txt_hum_num,"断路");
            } else {
                holder.setText(R.id.txt_hum_num,item.getHumidity());
            }
            if (item.getStatus()==null){
                holder.setText(R.id.txt_status_num,"未知");
                holder.setTextColor(R.id.txt_status_num, Color.RED);
            }else if (item.getStatus().equals("0")){
                holder.setText(R.id.txt_status_num,"关机");
                holder.setTextColor(R.id.txt_status_num, Color.BLACK);
            } else if (item.getStatus().equals("1")) {
                holder.setText(R.id.txt_status_num,"制冷");
                holder.setTextColor(R.id.txt_status_num, Color.BLUE);
            } else if (item.getStatus().equals("2")) {
                holder.setText(R.id.txt_status_num,"制热");
                holder.setTextColor(R.id.txt_status_num, Color.RED);
            } else if (item.getStatus().equals("3")) {
                holder.setText(R.id.txt_status_num,"除湿");
                holder.setTextColor(R.id.txt_status_num, Color.YELLOW);
            } else if (item.getStatus().equals("4")) {
                holder.setText(R.id.txt_status_num,"通风");
                holder.setTextColor(R.id.txt_status_num, Color.GREEN);
            }
            TextView txt_cold = holder.getView(R.id.txt_cold_open_or_close);
            TextView txt_hot = holder.getView(R.id.txt_hot_open_or_close);
            TextView txt_dehumidify = holder.getView(R.id.txt_dehumidify_open_or_close);
            TextView txt_wind = holder.getView(R.id.txt_wind_open_or_close);
            TextView txt_close = holder.getView(R.id.txt_close);
            txt_cold.setTag(item.getTag());
            txt_hot.setTag(item.getTag());
            txt_dehumidify.setTag(item.getTag());
            txt_wind.setTag(item.getTag());
            txt_close.setTag(item.getTag());
            txt_cold.setOnClickListener(this);
            txt_hot.setOnClickListener(this);
            txt_dehumidify.setOnClickListener(this);
            txt_wind.setOnClickListener(this);
            txt_close.setOnClickListener(this);
        }
    }
}
