package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;
import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.Wind.HuaiNan.MiBiFaAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.WindTestInTimeBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindStatusBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Util.OkHttpUtilTWO;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBodyTWO;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class LightAdapter extends MultiLayoutAdapter<WindStatusBody> implements SectionIndexer, View.OnClickListener {
    private ArrayList<WindStatusBody> datas;
    private ArrayList<WindStatusBody> refreshdatas;
    private Context context;
    private int notifactionType = TYPE_NORMAL;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    /**
     * layoutId的顺序必须和ViewType的顺序相同，而且从零开始
     *
     * @param mDataList
     * @param layoutIds
     */
    public LightAdapter(ArrayList<WindStatusBody> mDataList, int[] layoutIds, Context context) {
        super(mDataList, layoutIds);
        this.datas = mDataList;
        this.refreshdatas = mDataList;
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        WindStatusBody item = getItem(position-1);
        NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
        newDownRawBody.setUrl("/measure");
        newDownRawBody.setMethod("POST");
        newDownRawBody.setQuery(null);
        newDownRawBody.setHeaders(null);
        CountMultiple countMultiple = new CountMultiple();
        countMultiple.setCommandMapId(item.getCommandMapId());
        countMultiple.setMeasureType("-1");
        countMultiple.setUrl(item.getUrl());
        String jsonData = "";
        String body =
                  "AA55"
                + item.getInspurExtensionAddress()
                + "09FFFF"
                + "0002"
                + item.getHardwareData().getInspurAddress();
        if (v.getId() == R.id.txt_open) {
            if (item.getHardwareStatus().equals("02")) {
                util.showToast(context,"已开，请不要重复操作！");
            } else if (item.getHardwareStatus().equals("01")) {
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
                        if (success) {
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
        } else if (v.getId() == R.id.txt_close) {
            if (item.getHardwareStatus().equals("01")) {
                util.showToast(context,"已关，请不要重复操作！");
            } else if (item.getHardwareStatus().equals("02")) {
                body +=
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
                        if (success) {
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
        }
    }
    public interface OnInitTestInTimeFinishedListener {
        void OnInitTestInTimeListener(boolean success, String msg);
    }
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
                    util.showToast(context,"照明操作出错");
                }
                if (status.equals("01")) {
                    if (windTestInTimeBean.getData()!=null&&windTestInTimeBean.getData().getLightOpenStatus()!=null&&windTestInTimeBean.getData().getLightOpenStatus().size()>0){
                        if (windTestInTimeBean.getData().getLightOpenStatus().get(0).equals("00")){
                            if (status.equals("00")){
                                listener.OnInitTestInTimeListener(true,"01");
                            } else if (status.equals("01")){
                                listener.OnInitTestInTimeListener(true,"02");
                            } else {
                                listener.OnInitTestInTimeListener(false,"");
                            }
                        } else {
                            listener.OnInitTestInTimeListener(false,"");
                        }
                    } else {
                        listener.OnInitTestInTimeListener(false,"");
                    }
                } else if (status.equals("00")) {
                    if (windTestInTimeBean.getData()!=null&&windTestInTimeBean.getData().getLightCloseStatus()!=null&&windTestInTimeBean.getData().getLightCloseStatus().size()>0){
                        if (windTestInTimeBean.getData().getLightCloseStatus().get(0).equals("00")){
                            if (status.equals("00")){
                                listener.OnInitTestInTimeListener(true,"01");
                            } else if (status.equals("01")){
                                listener.OnInitTestInTimeListener(true,"02");
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

    @Override
    protected int getItemType(int position) {
        return 0;
    }
    public void setFreshDates(ArrayList<WindStatusBody> datas){
        this.refreshdatas = datas;
    }

    public ArrayList<WindStatusBody> getFreshDates(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }
    @Override
    protected void coverts(BaseViewHolder holder, WindStatusBody item, int position, int itemType) {
        if (item!=null) {
            if (item.getHardwareData().getName()!=null) {
                holder.setText(R.id.txt_name,item.getHardwareData().getName());
            } else {
                holder.setText(R.id.txt_name,"——");
            }
            if (item.getHardwareData()!=null) {
                if (item.getHardwareStatus().equals("02")){
                    holder.setText(R.id.txt_status,"开启");
                    holder.setTextColor(R.id.txt_status, Color.GREEN);
                } else if (item.getHardwareStatus().equals("01")){
                    holder.setText(R.id.txt_status,"关闭");
                    holder.setTextColor(R.id.txt_status, Color.RED);
                } else {
                    holder.setText(R.id.txt_status,"未知");
                    holder.setTextColor(R.id.txt_status, Color.BLACK);
                }
            }
        }
        TextView txt_open = holder.getView(R.id.txt_open);
        TextView txt_close = holder.getView(R.id.txt_close);
        txt_open.setTag(item.getTag());
        txt_close.setTag(item.getTag());
        txt_open.setOnClickListener(this);
        txt_close.setOnClickListener(this);
    }
}
