package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter;
import static com.example.multiplegranarymanager.A_XinQiaoPackage.Activity.MainActivity.deviceType;
import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Activity.MainActivity;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Bean.GasInsectBean.GasInsectTestInTimeBean;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CardInsectAdapterBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultiple;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CountMultipleTwo;
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

public class InsectDataAdapter extends MultiLayoutAdapter<CardInsectAdapterBody> implements SectionIndexer, View.OnClickListener {
    private ArrayList<CardInsectAdapterBody> datas;
    private ArrayList<CardInsectAdapterBody> refreshdatas;
    private Context context;
    private int notifactionType = TYPE_NORMAL;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    public InsectDataAdapter(ArrayList<CardInsectAdapterBody> mDataList, int[] layoutIds, Context context) {
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
        //这里为了保证和数组位置对应上整体-1，以防止数组下标越界并对应点击数组位置
        CardInsectAdapterBody item = getItem(position-1);
        if (v.getId()== R.id.txt_test) {
            //实时检测
            NewDownRawBodyTWO newDownRawBody = new NewDownRawBodyTWO();
            newDownRawBody.setUrl("/measure");
            newDownRawBody.setMethod("POST");
            newDownRawBody.setQuery(null);
            newDownRawBody.setHeaders(null);
            CountMultipleTwo countMultiple = new CountMultipleTwo();
            countMultiple.setCommandMapId(item.getCommandMapId());
            countMultiple.setUrl(item.getUrl());
            String body = "AA55"
                        + item.getInspurExtensionAddress()
                        + "0DFFFF"
                        + "0007"
                        + item.getInspurChannelNumber()    //起始通道地址
                        + "0001"                           //测试通道个数
                        + "0001"                           //通道组合
//                        + item.getDetectType()             //测量模式
                        + "07"
                        + "FFFF"
                        + "0D0A";
            countMultiple.setCommandContent(body);
            countMultiple.setMeasureType("-1");
            newDownRawBody.setBody(countMultiple);
            String jsonData = gson.toJson(newDownRawBody);
            initInsectTestData(jsonData, new OnInitTestInsectDataFinishedListener() {
                @Override
                public void OnInitTestInsectListener(boolean success) {
                    if (success){
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
    public interface OnInitTestInsectDataFinishedListener{
        void OnInitTestInsectListener(boolean success);
    }
    public void initInsectTestData(String json, final OnInitTestInsectDataFinishedListener listener){
        Log.d("jht", "initInsectTestData: " + json);
        suoPingDialog = new SuoPingDialog(context,"正在操作，请稍等......");
        suoPingDialog.setCancelable(true);
        suoPingDialog.show();
        OkHttpUtilTWO.PostNewDownRaw("mic-service-data/down?micServiceId=" + deviceType + "&timeOut=50", json, new OkHttpUtilTWO.ReqCallBack() {
            @Override
            public void onReqSuccess(Object result) throws JSONException {
                //这里检测接收到的数据类型并不知道，很大概率会出错
                GasInsectTestInTimeBean insectTestInTimeBean = new GasInsectTestInTimeBean();
                try {
                    insectTestInTimeBean = gson.fromJson((String) result,GasInsectTestInTimeBean.class);
                } catch (JsonSyntaxException e) {
                    suoPingDialog.dismiss();
                    e.printStackTrace();
                    util.showToast(context, insectTestInTimeBean.getMsg());
                }
                if (insectTestInTimeBean.getData()!=null&&insectTestInTimeBean.getData().getDataContent()!=null&&insectTestInTimeBean.getData().getDataContent().getGasInsectList()!=null){
                    if (insectTestInTimeBean.getData().getDataContent().getGasInsectList().size()>0){
                        for (CardInsectAdapterBody data : datas){
                            if (data.getCommandMapId().equals(insectTestInTimeBean.getData().getCommandMapId())){
                                data.setInsect(insectTestInTimeBean.getData().getDataContent().getGasInsectList().get(0).getInsect());
                            }
                            suoPingDialog.dismiss();
                            util.showToast(context,"检测成功！");
                        }
                    }
                }
                if (listener!=null){
                    listener.OnInitTestInsectListener(true);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.OnInitTestInsectListener(false);
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
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public int getSectionForPosition(int position) {
        CardInsectAdapterBody item = datas.get(position);
        return item.getInspurChannelNumberId();
    }
    @Override
    protected int getItemType(int position) {
        return 0;
    }
    public void setFreshDates(ArrayList<CardInsectAdapterBody> datas){
        this.refreshdatas = datas;
    }
    public ArrayList<CardInsectAdapterBody> getFreshDates(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }
    @Override
    protected void coverts(BaseViewHolder holder, CardInsectAdapterBody item, int position, int itemType) {
        if (item != null) {
            holder.setText(R.id.txt_name,"选通器"+item.getInspurChannelNumberId());
            TextView txt_Insect = holder.getView(R.id.txt_insect_num);
            txt_Insect.setSelected(true);
            if (item.getInsect()>=0){
                holder.setText(R.id.txt_insect_num,String.valueOf(item.getInsect())+"头");
            } else if (item.getInsect()==-1) {
                holder.setText(R.id.txt_insect_num,"———");
                holder.setTextColor(R.id.txt_insect_num, Color.RED);
            } else {
                holder.setText(R.id.txt_insect_num,"———");
            }
            TextView txt_test = holder.getView(R.id.txt_test);
            txt_test.setTag(item.getInspurChannelNumberId());
            txt_test.setOnClickListener(this);
        }
    }
}
