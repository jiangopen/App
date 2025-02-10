package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.Wind.HuaiNan;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;
import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.WindTestInTimeBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindStatusBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.SharedData.SharedData;
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

import org.json.JSONException;

import java.util.ArrayList;

public class MiBiFaAdapter extends MultiLayoutAdapter<WindStatusBody> implements SectionIndexer, View.OnClickListener {
    private ArrayList<WindStatusBody> datas;
    private ArrayList<WindStatusBody> refreshdatas;
    private Context context;
    private int notifactionType = TYPE_NORMAL;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;

    public MiBiFaAdapter(ArrayList<WindStatusBody> mDataList, int[] layoutIds, Context context) {
        super(mDataList, layoutIds);
        this.datas = mDataList;
        this.refreshdatas = mDataList;
        this.context = context;
        if (datas == null){
            datas = new ArrayList<>(0);
        }
    }

    @Override
    public void onClick(View v) {
        Log.d("jht", "WindStatusList_FengJi: "+ SharedData.WindStatusList_FengJi.size());
        int position = (int) v.getTag();
        WindStatusBody item = getItem(position-1);
        NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
        newDownRawBody.setUrl("/measure");
        newDownRawBody.setMethod("POST");
        newDownRawBody.setQuery(null);
        newDownRawBody.setHeaders(null);
        CountMultiple countMultiple = new CountMultiple();
        countMultiple.setCommandMapId(item.getCommandMapId());
        countMultiple.setUrl(item.getUrl());
        String jsonData = "";
        String body =
                  "AA55"
                + item.getInspurExtensionAddress() 
                + "09FFFF"
                + "0002"
                + item.getHardwareData().getInspurAddress();
        if (v.getId()== R.id.txt_open){
            Log.d("jht", "onClick: "+item.getHardwareData().getName());
            if (item.getHardwareStatus().equals("01")){
                Log.d("jht", "onClick: 2222222");
                body +=
                          "01"
                        + "FFFF0D0A";
                countMultiple.setCommandContent(body);
                newDownRawBody.setBody(countMultiple);
                jsonData = gson.toJson(newDownRawBody);
                String status = "01";
                initTestInTime(status, jsonData, new OnInitTestInTimeFinishedListener() {
                    @Override
                    public void OnInitTestInTimeListener(boolean success, String msg) {
                        suoPingDialog.dismiss();
                        if (success){
                            item.setHardwareStatus(msg);
                            notifyDataSetChanged();
                        } else {
                            util.showToast(context,"操作失败，请检查硬件连接");
                        }
                    }
                });
            } else {
                util.showToast(context,"状态未知，请稍后尝试！");
            }
        } else if (v.getId()==R.id.txt_close) {
            if (item.getHardwareStatus().equals("02")||item.getHardwareStatus().equals("45")){
                for (WindStatusBody data : SharedData.WindStatusList_FengJi){
                    if (data.getType().equals("02")){
                        if (data.getID().equals(item.getID())){
                            if (data.getHardwareStatus().equals("01")){
                                body+=
                                          "00"
                                        + "FFFF0D0A";
                                countMultiple.setCommandContent(body);
                                newDownRawBody.setBody(countMultiple);
                                jsonData = gson.toJson(newDownRawBody);
                                String status = "00";
                                initTestInTime(status, jsonData, new OnInitTestInTimeFinishedListener() {
                                    @Override
                                    public void OnInitTestInTimeListener(boolean success, String msg) {
                                        suoPingDialog.dismiss();
                                        if (success){
                                            item.setHardwareStatus(msg);
                                            notifyDataSetChanged();
                                        } else {
                                            util.showToast(context,"操作失败，请检查硬件连接");
                                        }
                                    }
                                });
                            } else if (data.getHardwareStatus().equals("02")||data.getHardwareStatus().equals("45")) {
                                util.showToast(context,"请关闭对应的风机后再试！");
                            } else {
                                util.showToast(context,"状态未知，请稍后再试(2)！");
                            }
                        } else {
                            if (data.getHardwareStatus().equals("01")){
                                body+=
                                        "00"
                                                + "FFFF0D0A";
                                countMultiple.setCommandContent(body);
                                newDownRawBody.setBody(countMultiple);
                                jsonData = gson.toJson(newDownRawBody);
                                String status = "00";
                                initTestInTime(status, jsonData, new OnInitTestInTimeFinishedListener() {
                                    @Override
                                    public void OnInitTestInTimeListener(boolean success, String msg) {
                                        suoPingDialog.dismiss();
                                        if (success){
                                            item.setHardwareStatus(msg);
                                            notifyDataSetChanged();
                                        } else {
                                            util.showToast(context,"操作失败，请检查硬件连接");
                                        }
                                    }
                                });
                            } else {
                                util.showToast(context,"状态未知，请稍后再试(2)！");
                            }
                        }
                    }
                }
            } else {
                util.showToast(context,"状态未知，请稍后尝试(1)！");
            }
        } else if (v.getId()==R.id.txt_pause) {
            if (item.getHardwareStatus().equals("01")||item.getHardwareStatus().equals("02")){
                body+=
                         "03"
                        + "FFFF0D0A";
                countMultiple.setCommandContent(body);
                newDownRawBody.setBody(countMultiple);
                jsonData = gson.toJson(newDownRawBody);
                String status = "03";
                initTestInTime(status, jsonData, new OnInitTestInTimeFinishedListener() {
                    @Override
                    public void OnInitTestInTimeListener(boolean success, String msg) {
                        suoPingDialog.dismiss();
                        if (success){
                            item.setHardwareStatus(msg);
                            notifyDataSetChanged();
                        } else {
                            util.showToast(context,"操作失败，请检查硬件连接");
                        }
                    }
                });
            }
        }
    }

    public interface OnInitTestInTimeFinishedListener{
        void OnInitTestInTimeListener(boolean success,String msg);
    }
    //这里未来大概率会有问题，问题的方面应该是打开关闭操作获取信息状态不明确，一直都只是用getWindOpenStatus状态来判断的，getWindCloseStatus并没有用上
    public void initTestInTime(String status,String json,final OnInitTestInTimeFinishedListener listener){
        suoPingDialog = new SuoPingDialog(context,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=30", json, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindTestInTimeBean windTestInTimeBean = new WindTestInTimeBean();
                try {
                    windTestInTimeBean = gson.fromJson((String) result,WindTestInTimeBean.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    util.showToast(context,"密闭阀操作出错");
                }
                if (windTestInTimeBean.getData()!=null&&windTestInTimeBean.getData().getWindOpenStatus()!=null&&windTestInTimeBean.getData().getWindOpenStatus().size()>0){
                    if (windTestInTimeBean.getData().getWindOpenStatus().get(0).equals("00")){
                        if (status.equals("00")){
                            listener.OnInitTestInTimeListener(true,"01");
                        } else if (status.equals("01")){
                            listener.OnInitTestInTimeListener(true,"02");
                        } else if (status.equals("03")) {
                            listener.OnInitTestInTimeListener(true,"45");
                        } else {
                            listener.OnInitTestInTimeListener(false,"");
                        }
                    } else {
                        listener.OnInitTestInTimeListener(false,"");
                    }
                } else {
                    listener.OnInitTestInTimeListener(false,"");
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                util.showToast(context,errorMsg);
                listener.OnInitTestInTimeListener(false,"");
            }
        });
    }
    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i=0;i<getItemCount();i++){
            int firstChar = datas.get(i).getHardwareData().getName().charAt(0);
            if (firstChar == sectionIndex){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        WindStatusBody item = datas.get(position);
        return item.getHardwareData().getName().charAt(0);
    }
    public void setFreshDates(ArrayList<WindStatusBody> datas){
        this.refreshdatas = datas;
    }

    public ArrayList<WindStatusBody> getFreshDates(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }
    @Override
    protected int getItemType(int position) {
        return 0;
    }
    @Override
    protected void coverts(BaseViewHolder holder, WindStatusBody item, int position, int itemType) {
        if (item!=null){
            if (item.getType().equals("01")){
                if (item.getHardwareData().getName()!=null){
                    holder.setText(R.id.txt_name,item.getHardwareData().getName());
                } else {
                    holder.setText(R.id.txt_name,"——");
                }
                if (item.getHardwareData()!=null){
                    if (item.getHardwareStatus().equals("02")){
                        holder.setText(R.id.txt_status,"开启");
                        holder.setTextColor(R.id.txt_status, Color.GREEN);
                    } else if (item.getHardwareStatus().equals("01")){
                        holder.setText(R.id.txt_status,"关闭");
                        holder.setTextColor(R.id.txt_status, Color.RED);
                    } else if (item.getHardwareStatus().equals("45")){
                        holder.setText(R.id.txt_status,"半开");
                        holder.setTextColor(R.id.txt_status, Color.BLUE);
                    } else {
                        holder.setText(R.id.txt_status,"未知");
                        holder.setTextColor(R.id.txt_status, Color.BLACK);
                    }
                }
            }
        }
        TextView txt_open = holder.getView(R.id.txt_open);
        TextView txt_close = holder.getView(R.id.txt_close);
        TextView txt_pause = holder.getView(R.id.txt_pause);
        txt_open.setTag(item.getTag());
        txt_close.setTag(item.getTag());
        txt_pause.setTag(item.getTag());
        txt_open.setOnClickListener(this);
        txt_close.setOnClickListener(this);
        txt_pause.setOnClickListener(this);
    }
}
