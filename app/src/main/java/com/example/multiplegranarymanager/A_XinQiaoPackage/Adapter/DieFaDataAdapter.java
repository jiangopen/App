package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter;

import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.Wind.WindStatusBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CardGasAdapterBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultipleTwo;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.WindStatusBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.SharedData.QiTiaoSharedData;
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

public class DieFaDataAdapter extends MultiLayoutAdapter<WindStatusBody> implements SectionIndexer, View.OnClickListener {
    private ArrayList<WindStatusBody> datas;
    private ArrayList<WindStatusBody> refreshdatas;
    private Context context;
    private Gson gson = new Gson();
    private Util1 util1 = new Util1();
    private SuoPingDialog suoPingDialog;
    public DieFaDataAdapter(ArrayList<WindStatusBody> Datas, int[] layoutIds, Context context) {
        super(Datas, layoutIds);
        this.datas = Datas;
        this.refreshdatas = Datas;
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
    }

    @Override
    public void onClick(View v) {

        int position = (int) v.getTag();
        WindStatusBody item = getItem(position-1);
        if (v.getId() == R.id.txt_on) {
            Log.d("jht", "onClick: "+item.getUrl());
            Log.d("jht", "onClick: "+item.getHardwareData().getName());
            if (item.getHardwareStatus().equals("01") || item.getHardwareStatus().equals("45")) {
                util1.showToast(context,"已开启，请勿重复操作！");
            } else {
                NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                newDownRawBody.setUrl("/measure");
                newDownRawBody.setMethod("POST");
                newDownRawBody.setQuery(null);
                newDownRawBody.setHeaders(null);
                CountMultiple countMultiple = new CountMultiple();
                countMultiple.setMeasureType("-1");
                countMultiple.setCommandMapId(item.getCommandMapId());

                countMultiple.setUrl(item.getUrl());
                String body = "AA55" + item.getInspurExtensionAddress() + "30" + "FFFF" + "FFFF0D0A";
                countMultiple.setCommandContent(body);
                CountMultiple.CommandJson commandJson = new CountMultiple.CommandJson();
                ArrayList<String> list = new ArrayList<>();
                list.add(item.getHardwareData().getExtensionNumber() + item.getHardwareData().getChannelNumber());
                commandJson.setIds(list);
                countMultiple.setCommandJson(commandJson);
                newDownRawBody.setBody(countMultiple);
                String jsondata = gson.toJson(newDownRawBody);
                initTestControl(item.getHardwareData().getId(), "打开", jsondata, new OnInitTestControlFinishedListener() {
                    @Override
                    public void OnInitTestControlListener(boolean success) {
                        suoPingDialog.dismiss();
                        if (success) {
                            notifyDataSetChanged();
                        } else {
                            util1.showToast(context,"请稍后操作！");
                        }
                    }
                });
            }
        } if (v.getId() == R.id.txt_off) {
            Log.d("jht", "onClick: "+item.getUrl());
            Log.d("jht", "onClick: "+item.getHardwareData().getName());
            if (item.getHardwareStatus().equals("00")) {
                util1.showToast(context,"已关闭，请勿重复操作！");
            } else {
                NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
                newDownRawBody.setUrl("/measure");
                newDownRawBody.setMethod("POST");
                newDownRawBody.setQuery(null);
                newDownRawBody.setHeaders(null);
                CountMultiple countMultiple = new CountMultiple();
                countMultiple.setMeasureType("-1");
                countMultiple.setCommandMapId(item.getCommandMapId());
                countMultiple.setUrl(item.getUrl());
                String body = "AA55" + item.getInspurExtensionAddress() + "32" + "FFFF" + "FFFF0D0A";
                countMultiple.setCommandContent(body);
                CountMultiple.CommandJson commandJson = new CountMultiple.CommandJson();
                ArrayList<String> list = new ArrayList<>();
                list.add(item.getHardwareData().getExtensionNumber() + item.getHardwareData().getChannelSecNumber());
                commandJson.setIds(list);
                countMultiple.setCommandJson(commandJson);
                newDownRawBody.setBody(countMultiple);
                String jsondata = gson.toJson(newDownRawBody);
                initTestControl(item.getHardwareData().getId(), "关闭", jsondata, new OnInitTestControlFinishedListener() {
                    @Override
                    public void OnInitTestControlListener(boolean success) {
                        suoPingDialog.dismiss();
                        if (success) {
                            notifyDataSetChanged();
                        } else {
                            util1.showToast(context,"请稍后操作！");
                        }
                    }
                });
            }
        }
    }
    public interface OnInitTestControlFinishedListener{
        void OnInitTestControlListener(boolean success);
    }
    public void initTestControl(String id,String flag,String jsondata, final OnInitTestControlFinishedListener listener) {
        suoPingDialog = new SuoPingDialog(context,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=50", jsondata, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                WindStatusBean windStatusBean = new WindStatusBean();
                try {
                    windStatusBean = gson.fromJson((String) result,WindStatusBean.class);
                } catch (JsonSyntaxException e) {
                    listener.OnInitTestControlListener(false);
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util1.showToast(context,windStatusBean.getMsg());
                }
                if (flag.equals("打开")) {
                    if (windStatusBean.getData()!=null && windStatusBean.getData().getValveOpenFlagList()!=null && windStatusBean.getData().getValveOpenFlagList().size()>0) {
                        for (WindStatusBody data : datas) {
                            if (data.getHardwareData().getId().equals(id) && windStatusBean.getData().getValveOpenFlagList().get(0).equals("01")){
                                data.setHardwareStatus("01");
                            } else if (data.getHardwareData().getId().equals(id) && windStatusBean.getData().getValveOpenFlagList().get(0).equals("00")) {
                                data.setHardwareStatus("00");
                            } else {
                                data.setHardwareStatus("00");
                            }
                        }
                    }
                } else if (flag.equals("关闭")) {
                    if (windStatusBean.getData()!=null && windStatusBean.getData().getValveCloseFlagList()!=null && windStatusBean.getData().getValveCloseFlagList().size()>0) {
                        for (WindStatusBody data : datas) {
                            if (data.getHardwareData().getId().equals(id) && windStatusBean.getData().getValveCloseFlagList().get(0).equals("01")){
                                data.setHardwareStatus("01");
                            } else if (data.getHardwareData().getId().equals(id) && windStatusBean.getData().getValveCloseFlagList().get(0).equals("00")) {
                                data.setHardwareStatus("00");
                            } else {
                                data.setHardwareStatus("01");
                            }
                        }
                    }
                }
                if (listener!=null) {
                    listener.OnInitTestControlListener(true);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitTestControlListener(false);
                suoPingDialog.dismiss();
                util1.showToast(context,errorMsg);
            }
        });
    }
    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i=0;i<getItemCount();i++) {
            int firstChar = datas.get(i).getTag();
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        WindStatusBody item = datas.get(position);
        return item.getTag();
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
        TextView fengji = holder.getView(R.id.txt_fj_num);
        fengji.setSelected(true);
        if (item.getHardwareData().getName() != null) {
            holder.setText(R.id.txt_name,item.getHardwareData().getName());
        } else {
            holder.setText(R.id.txt_name,"——");
        }

        if (item.getType() != null) {
            for (WindStatusBody data : QiTiaoSharedData.HuanLiuStatusList) {
                if (data.getType().equals(item.getType())) {
                    holder.setText(R.id.txt_fj_num,data.getHardwareData().getName());
                }
            }
        } else {
            holder.setText(R.id.txt_fj_num,"——");
        }

        if (item.getHardwareStatus() != null) {
            if (item.getHardwareStatus().equals("00")) {
                holder.setText(R.id.txt_status_num,"关闭");
            } else if (item.getHardwareStatus().equals("01") || item.getHardwareStatus().equals("45")) {
                holder.setText(R.id.txt_status_num,"开启");
            }
        } else {
            holder.setText(R.id.txt_status_num,"——");
        }

        TextView txt_on = holder.getView(R.id.txt_on);
        TextView txt_off = holder.getView(R.id.txt_off);
        txt_on.setTag(item.getTag());
        txt_off.setTag(item.getTag());
        txt_on.setOnClickListener(this);
        txt_off.setOnClickListener(this);
    }
}