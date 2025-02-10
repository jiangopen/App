package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter;
import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity.deviceType;
import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Bean.GasInsectBean.GasInsectTestInTimeBean;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.CardGasAdapterBody;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRawBody;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;

public class GasDataAdapter extends MultiLayoutAdapter<CardGasAdapterBody> implements SectionIndexer, View.OnClickListener {
    private ArrayList<CardGasAdapterBody> datas;
    private ArrayList<CardGasAdapterBody> refreshdatas;
    private Context context;
    private int notifactionType = TYPE_NORMAL;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    public GasDataAdapter(ArrayList<CardGasAdapterBody> mDatas, int[] layoutIds, Context context) {
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
        //这里为了保证和数组中的位置对应上整体-1，以防止数组下标越界
        CardGasAdapterBody item = getItem(position-1);
        if (v.getId()== R.id.txt_test){
            //实时检测
            Log.d("zyq", "onClick: "+"点击了选通器"+item.getInspurChannelNumberId());
            NewDownRawBody newDownRawBody = new NewDownRawBody();
            newDownRawBody.setUrl("/measure");
            newDownRawBody.setMethod("POST");
            newDownRawBody.setQuery("");
            newDownRawBody.setHeaders("");
            CountMultiple countMultiple = new CountMultiple();
            countMultiple.setCommandMapId(item.getCommandMapId());
            countMultiple.setUrl(item.getUrl());
            String body =
                      "AA55"
                    + item.getInspurExtensionAddress()
                    + "0DFFFF"
                    + "0007"
                    + item.getInspurChannelNumber()    //起始通道地址
                    + "0001"                           //测试通道个数
                    + "0001"                           //通道组合
                    + item.getDetectType()             //测量模式
                    + "FFFF"
                    + "0D0A";
            countMultiple.setCommandContent(body);
            newDownRawBody.setBody(countMultiple);
            String jsonData = gson.toJson(newDownRawBody);
            initGasTestData(jsonData, new OnInitTestGasDataFinishedListener() {
                @Override
                public void OnInitTestGasListener(boolean success) {
                    if (success){
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
    public interface OnInitTestGasDataFinishedListener{
        void OnInitTestGasListener(boolean success);
    }
    public void initGasTestData(String json,final OnInitTestGasDataFinishedListener listener){
        Log.d("zyq", "GASinitInsectTestData: "+json);
        suoPingDialog = new SuoPingDialog(context,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=" + deviceType + "&bodyType=json&timeout=30", json, Token, new OkHttpUtil.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                //这里检测接收到的数据类型并不知道，很大概率会出错
                GasInsectTestInTimeBean gasTestInTimeBean = new GasInsectTestInTimeBean();
                try {
                    gasTestInTimeBean = gson.fromJson((String) result, GasInsectTestInTimeBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util.showToast(context,gasTestInTimeBean.getMsg());
                }
                if (gasTestInTimeBean.getData()!=null&&gasTestInTimeBean.getData().getDataContent()!=null&&gasTestInTimeBean.getData().getDataContent().getGasInsectList()!=null){
                    if (gasTestInTimeBean.getData().getDataContent().getGasInsectList().size()>0){
                        for (CardGasAdapterBody data : datas){
                            if (data.getCommandMapId().equals(gasTestInTimeBean.getData().getCommandMapId())){
                                data.setCO2(gasTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getCo2());
                                data.setO2(gasTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getO2());
                                data.setN2(99-gasTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getCo2()-gasTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getO2());
                                data.setPH3(gasTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getPh3());
                            }
                            suoPingDialog.dismiss();
                            util.showToast(context,"检测成功！");
                        }
                    }
                }
                if (listener!=null){
                    listener.OnInitTestGasListener(true);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitTestGasListener(false);
                suoPingDialog.dismiss();
                util.showToast(context,errorMsg);
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
            int firstChar = datas.get(i).getInspurChannelNumberId();
            if (firstChar == sectionIndex){
                return i;
            }
        }
        return -1;
    }
    @Override
    public int getSectionForPosition(int position) {
        CardGasAdapterBody item = datas.get(position);
        return item.getInspurChannelNumberId();
    }
    @Override
    protected int getItemType(int position) {
        return 0;
    }
    public void setFreshDates(ArrayList<CardGasAdapterBody> datas){
        this.refreshdatas = datas;
    }
    public ArrayList<CardGasAdapterBody> getFreshDates(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }
    @Override
    protected void coverts(BaseViewHolder holder, CardGasAdapterBody item, int position, int itemType) {
        if (item!=null){
            holder.setText(R.id.txt_name,"选通器"+item.getInspurChannelNumberId());
            TextView txt_o2 = holder.getView(R.id.txt_o2_num);
            TextView txt_n2 = holder.getView(R.id.txt_n2_num);
            TextView txt_ph3 = holder.getView(R.id.txt_ph3_num);
            TextView txt_co2 = holder.getView(R.id.txt_co2_num);
            txt_o2.setSelected(true);
            txt_n2.setSelected(true);
            txt_ph3.setSelected(true);
            txt_co2.setSelected(true);
            if (item.getO2()!=null){
                if (item.getO2()>=0){
                    holder.setText(R.id.txt_o2_num,item.getO2().toString());
                } else if (item.getO2()==255.0){
                    holder.setText(R.id.txt_o2_num,"断路");
                    holder.setTextColor(R.id.txt_o2_num, Color.RED);
                } else {
                    holder.setText(R.id.txt_o2_num,"———");
                }
            } else {
                holder.setText(R.id.txt_o2_num,"———");
            }
            if (item.getN2()!=null){
                if (item.getN2()>=0){
                    holder.setText(R.id.txt_n2_num,item.getN2().toString());
                } else if (item.getN2()==255.0){
                    holder.setText(R.id.txt_n2_num,"断路");
                    holder.setTextColor(R.id.txt_n2_num, Color.RED);
                } else {
                    holder.setText(R.id.txt_n2_num,"———");
                }
            } else {
                holder.setText(R.id.txt_n2_num,"———");
            }
            if (item.getPH3()!=null){
                if(item.getPH3()>=0){
                    holder.setText(R.id.txt_ph3_num,item.getPH3().toString());
                } else if (item.getPH3()!=255.0){
                    holder.setText(R.id.txt_ph3_num,"断路");
                    holder.setTextColor(R.id.txt_ph3_num, Color.RED);
                } else {
                    holder.setText(R.id.txt_ph3_num,"———");
                }
            } else {
                holder.setText(R.id.txt_ph3_num,"———");
            }
            if (item.getCO2()!=null){
                if (item.getCO2()>=0){
                    holder.setText(R.id.txt_co2_num,item.getCO2().toString());
                } else if (item.getCO2()!=255.0){
                    holder.setText(R.id.txt_co2_num,"断路");
                    holder.setTextColor(R.id.txt_co2_num, Color.RED);
                } else {
                    holder.setText(R.id.txt_co2_num,"———");
                }
            } else {
                holder.setText(R.id.txt_co2_num,"———");
            }
            TextView txt_test = holder.getView(R.id.txt_test);
            txt_test.setTag(item.getInspurChannelNumberId());
            txt_test.setOnClickListener(this);
        }
    }
}
