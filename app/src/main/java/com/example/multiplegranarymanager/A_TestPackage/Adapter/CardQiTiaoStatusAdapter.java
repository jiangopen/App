package com.example.multiplegranarymanager.A_TestPackage.Adapter;

import static com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.Token;
import static com.example.multiplegranarymanager.A_TestPackage.Activity.MainActivity.deviceType;
import static com.example.multiplegranarymanager.Body.QiTiao.N2AllStatusBody.*;
import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.Bean.NewDownRaw.MeasureId02Bean;
import com.example.multiplegranarymanager.Bean.QiTiao.QiTiaoBean;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRaw02Body;
import com.example.multiplegranarymanager.Body.NewDownRaw.NewDownRaw03Body;
import com.example.multiplegranarymanager.Body.NewDownRaw.Profile;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.Dialog.SuoPingDialog;
import com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple.GasControlFunction.N2InputFragment;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.OkHttpUtil;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardQiTiaoStatusAdapter extends MultiLayoutAdapter<Status> implements SectionIndexer, View.OnClickListener {
    private List<Status> datas;
    private List<Status> refreshdatas;
    private Fragment context;
    private int notifactionType = TYPE_NORMAL;
    private Gson gson = new Gson();
    private Util1 util = new Util1();
    private SuoPingDialog suoPingDialog;
    public CardQiTiaoStatusAdapter(List<Status> mDatas, int[] layoutIds, Fragment context) {
        super(mDatas, layoutIds);
        this.datas = mDatas;
        this.refreshdatas = mDatas;
        this.context = context;
        if (datas==null){
            datas = new ArrayList<>(0);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Status item = getItem(position-1);
        String caozuo = "";
        if (item.getModuile().equals("00")){
            if (v.getId()==R.id.txt_open){
                if (item.getStatus().equals("")){
                    util.showToast(context.getContext(),"状态未知，请稍后再试！");
                } else if (item.getStatus().equals("0")) {
//                    item.setStatus("1");
//                    notifyDataSetChanged();
//                    ((N2InputFragment)context).updateStatus(item);
                    Profile profile = new Profile();
                    profile.setId(item.getGranaryId());
                    profile.setNickname(item.getNickName());
                    if (item.getName().contains("F")) {
                        profile.setData(Collections.singletonList("0" + item.getTag()));
                        caozuo = "qitiaofamenguan";
                        profile.setMeasure(Collections.singletonList(caozuo));
                    } else if (item.getName().contains("M")) {
                        if (item.getName().equals("M1")){
                            profile.setData(Collections.singletonList("01"));
                        } else if (item.getName().equals("M2")) {
                            profile.setData(Collections.singletonList("02"));
                        }
                        caozuo = "qitiaofengjiguan";
                        profile.setMeasure(Collections.singletonList(caozuo));
                    }
                    NewDownRaw02Body body = new NewDownRaw02Body(
                            "02",
                            item.getMoudleName(),
                            Collections.singletonList(profile)
                    );
                    String jsondata = gson.toJson(body);
                    initCaoZuo(item,"关", jsondata);
                } else if (item.getStatus().equals("1")) {
                    util.showToast(context.getContext(),"已打开，请不要重复操作！");
                }
            } else if (v.getId()==R.id.txt_close) {
                if (item.getStatus().equals("")){
                    util.showToast(context.getContext(),"状态未知，请稍后再试！");
                } else if (item.getStatus().equals("0")) {
                    util.showToast(context.getContext(),"已关闭，请不要重复操作！");
                } else if (item.getStatus().equals("1")) {
//                    item.setStatus("0");
//                    notifyDataSetChanged();
//                    ((N2InputFragment)context).updateStatus(item);
                    Profile profile = new Profile();
                    profile.setId(item.getGranaryId());
                    profile.setNickname(item.getNickName());

                    if (item.getName().contains("F")) {
                        profile.setData(Collections.singletonList("0" + item.getTag()));
                         caozuo = "qitiaofamenkai";
                        profile.setMeasure(Collections.singletonList(caozuo));
                    } else if (item.getName().contains("M")) {
                        if (item.getName().equals("M1")){
                            profile.setData(Collections.singletonList("01"));
                        } else if (item.getName().equals("M2")) {
                            profile.setData(Collections.singletonList("02"));
                        }
                        caozuo = "qitiaofengjikai";
                        profile.setMeasure(Collections.singletonList(caozuo));
                    }
                    NewDownRaw02Body body = new NewDownRaw02Body(
                            "02",
                            item.getMoudleName(),
                            Collections.singletonList(profile)
                    );
                    String jsondata = gson.toJson(body);
                    initCaoZuo(item,"开", jsondata);
                }
            }
        } else if (item.getModuile().equals("04")){
            util.showToast(context.getContext(),"正在气密性检测，请稍后操作！");
        } else {
            util.showToast(context.getContext(),"目前为智能模式，请切换为手动模式！");
        }

    }
    public interface OnInitCaoZuoFinishedListener{
        void InItCaoZuoListener(boolean success);
    }
    public void initCaoZuo(Status item, String caozuo,String json){
        final Handler handler = new Handler(Looper.getMainLooper());
        suoPingDialog = new SuoPingDialog(context.getContext(), "正在操作，请稍等......");
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
                    util.showToast(context.getContext(), "CardQiTiaoStatusAdapter读取出错");
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
                                        util.showToast(context.getContext(), "CardQiTiaoStatusAdapter获取数据出错");
                                    }
                                    if (qiTiaoBean.getData().getProgress()<1){
                                        return;
                                    } else {
                                        if (qiTiaoBean.getData().getData()!=null&&qiTiaoBean.getData().getData().size()>0){
                                            if (qiTiaoBean.getData().getData().get(0).getHardwareData().get(0)!=null){
                                                if (qiTiaoBean.getData().getData().get(0).getHardwareData().get(0).equals("00")){
                                                    if (caozuo.equals("开")){
                                                        util.showToast(context.getContext(), item.getName()+"已开");
                                                        item.setStatus("0");
                                                    } else if (caozuo.equals("关")){
                                                        util.showToast(context.getContext(), item.getName()+"已关");
                                                        item.setStatus("1");
                                                    }
                                                } else {
                                                    util.showToast(context.getContext(), "操作失败，请稍后再试！");
                                                }
                                            }
                                        }
                                    }
                                    suoPingDialog.dismiss();
                                    notifyDataSetChanged();
                                    ((N2InputFragment)context).updateStatus(item);
                                }

                                @Override
                                public void onReqFailed(String errorMsg) {
                                    util.showToast(context.getContext(), errorMsg);
                                }
                            });
                        }
                    };
                    handler.postDelayed(pollRunnable,2000);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                util.showToast(context.getContext(), errorMsg);
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
            int firstChar = datas.get(i).getName().charAt(0);
            if (firstChar == sectionIndex){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        Status item = datas.get(position);
        return item.getName().charAt(0);
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }
    public void setFreshDatas(ArrayList<Status> datas){
        this.refreshdatas = datas;
    }
    public List<Status> getFreshDatas(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }
    @Override
    protected void coverts(BaseViewHolder holder, Status item, int position, int itemType) {
        if (item!=null){
            if (item.getName()!=null){
                holder.setText(R.id.txt_name,item.getName());
            } else {
                holder.setText(R.id.txt_name,"——");
            }
            if (item.getStatus()!=null){
                if (item.getStatus().equals("0")){
                    holder.setViewVisibility(R.id.txt_status_open,View.GONE);
                    holder.setViewVisibility(R.id.txt_status_close,View.VISIBLE);
                    holder.setViewVisibility(R.id.txt_status_none,View.GONE);
                } else if (item.getStatus().equals("1")) {
                    holder.setViewVisibility(R.id.txt_status_open,View.VISIBLE);
                    holder.setViewVisibility(R.id.txt_status_close,View.GONE);
                    holder.setViewVisibility(R.id.txt_status_none,View.GONE);
                } else {
                    holder.setViewVisibility(R.id.txt_status_open,View.GONE);
                    holder.setViewVisibility(R.id.txt_status_close,View.GONE);
                    holder.setViewVisibility(R.id.txt_status_none,View.VISIBLE);
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
