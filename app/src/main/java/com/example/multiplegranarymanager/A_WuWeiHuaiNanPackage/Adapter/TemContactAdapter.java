package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter;

import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;

import android.content.Context;
import android.view.View;
import android.widget.SectionIndexer;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.TemGranaryBody;

import java.util.ArrayList;

public class TemContactAdapter extends MultiLayoutAdapter<TemGranaryBody> implements SectionIndexer, View.OnClickListener {
    /**
     * layoutId的顺序必须和ViewType的顺序相同，而且从零开始
     *
     * @param mDataList
     * @param layoutIds
     */
    private ArrayList<TemGranaryBody> datas;
    private ArrayList<TemGranaryBody> refreshdatas;
    public int opened = -1;//卡片是否选中标志位
    private Context context;
    private int notifactionType = TYPE_NORMAL;
    public TemContactAdapter(ArrayList<TemGranaryBody> mDatas, int[] layoutIds, Context context) {
        super(mDatas,layoutIds);
        this.datas = mDatas;
//        Log.d("jht", "LockContactAdapter.datas: "+datas.size());
        this.refreshdatas = mDatas;
        this.context = context;
        if (datas == null){
            datas = new ArrayList<>(0);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();//getTag()获取数据
        TemGranaryBody item = getItem(position);
//        if (v.getId() == R.id.card_adapter_layout){
//            if (mOnItemClickListener != null){
//                mOnItemClickListener.onItemClick(v,item,position);
//            }
//            if (opened == position){
//                opened = -1;
//                notifyDataSetChanged();
//            }else {
//                opened = position;
//                notifyDataSetChanged();
//            }
//        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i=0;i<getItemCount();i++){
//            char firstChar = datas.get(i).getProductName().charAt(0);
//            if (firstChar == section){
//                return i;
//            }
        }
        return -1;
//        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        TemGranaryBody item = datas.get(position);
//        return item.getProductName().charAt(0);
        return 0;
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }

    @Override
    protected void coverts(BaseViewHolder holder, TemGranaryBody item, int position, int itemType) {
//        Log.d("jht", "coverts: "+"zidahdsi");
//        OnLine(item, new OnLineFinished() {
//            @Override
//            public void OnLineFinishedListener(boolean success) {
//                if (success){
//                    if (item.getStatus().equals("01")){
//                        holder.setViewVisibility(R.id.layout_lixian,View.GONE);
//                        holder.setViewVisibility(R.id.layout_zaixian,View.VISIBLE);
//                    } else {
//                        holder.setViewVisibility(R.id.layout_lixian,View.VISIBLE);
//                        holder.setViewVisibility(R.id.layout_zaixian,View.GONE);
//                    }
//                } else {
//                    holder.setViewVisibility(R.id.layout_lixian,View.VISIBLE);
//                    holder.setViewVisibility(R.id.layout_zaixian,View.GONE);
//                }
//
//            }
//        });
//        TextView select = holder.getView(R.id.name_shebei);
//        holder.setText(R.id.name_shebei,item.getProductName());
//        if (position == opened){
//            select.setBackgroundResource(R.drawable.card_name_bg_select_name);
//            select.setTextColor(ContextCompat.getColor(context, R.color.third_top_ss));
//
//        } else {
//            select.setBackgroundResource(R.drawable.card_name_bg_unselect_name);
//            select.setTextColor(ContextCompat.getColor(context, R.color.fourth_top_gz));
//        }
//        TextView math_n2_1 = holder.getView(R.id.tv_N2_math_1);
//        TextView math_n2_2 = holder.getView(R.id.tv_N2_math_2);
////        TextView math_o2_1 = holder.getView(R.id.tv_O2_math_1);
////        TextView math_o2_2 = holder.getView(R.id.tv_O2_math_2);
//        TextView math_ph3_1 = holder.getView(R.id.tv_PH3_math_1);
//        TextView math_ph3_2 = holder.getView(R.id.tv_PH3_math_2);
//        math_n2_1.setSelected(true);
//        math_n2_2.setSelected(true);
////        math_o2_1.setSelected(true);
////        math_o2_2.setSelected(true);
//        math_ph3_1.setSelected(true);
//        math_ph3_2.setSelected(true);
//        if (item.getStatus()!=null){
//            if (item.getStatus().equals("01")){
//                holder.setViewVisibility(R.id.layout_lixian,View.GONE);
//                holder.setViewVisibility(R.id.layout_zaixian,View.VISIBLE);
//                //#.##为不强制两位小数
//                DecimalFormat df = new DecimalFormat("0.0");
//                if (item.getmCardLockData()!=null){
//                    if (item.getmCardLockData().getStatus_door().equals("0")){
//                        holder.setViewVisibility(R.id.layout_lock_off,View.GONE);
//                        holder.setViewVisibility(R.id.layout_lock_on,View.VISIBLE);
//                    } else if (item.getmCardLockData().getStatus_door().equals("1")){
//                        holder.setViewVisibility(R.id.layout_lock_off,View.VISIBLE);
//                        holder.setViewVisibility(R.id.layout_lock_on,View.GONE);
//                    }
//                    if (item.getmCardLockData().getPointN2_1()>100||item.getmCardLockData().getPointN2_1()<0){
//                        holder.setText(R.id.tv_N2_math_1,"断路");
//                        holder.setTextColor(R.id.tv_N2_math_1, Color.RED);
////                        holder.setTextColor(R.id.tv_O2_math_1, Color.RED);
//                    } else {
//                        holder.setText(R.id.tv_N2_math_1, df.format(99-item.getmCardLockData().getPointN2_1())+"%");
////                        holder.setText(R.id.tv_O2_math_1, df.format(item.getmCardLockData().getPointN2_1())+"%");
//                    }
//                    if (item.getmCardLockData().getPointN2_2()>100||item.getmCardLockData().getPointN2_2()<0){
//                        holder.setText(R.id.tv_N2_math_2,"断路");
//                        holder.setTextColor(R.id.tv_N2_math_2, Color.RED);
////                        holder.setTextColor(R.id.tv_O2_math_2, Color.RED);
//                    } else {
//                        holder.setText(R.id.tv_N2_math_2, df.format(99-item.getmCardLockData().getPointN2_2())+"%");
////                        holder.setText(R.id.tv_O2_math_2, df.format(item.getmCardLockData().getPointN2_2())+"%");
//                    }
//                    if (item.getmCardLockData().getPointPH3_1()>100||item.getmCardLockData().getPointPH3_1()<0){
//                        holder.setText(R.id.tv_PH3_math_1,"断路");
//                        holder.setTextColor(R.id.tv_PH3_math_1, Color.RED);
//                    } else {
//                        holder.setText(R.id.tv_PH3_math_1, item.getmCardLockData().getPointPH3_1().toString()+"ppm");
//                    }
//                    if (item.getmCardLockData().getPointPH3_2()>100||item.getmCardLockData().getPointPH3_2()<0){
//                        holder.setText(R.id.tv_PH3_math_2,"断路");
//                        holder.setTextColor(R.id.tv_PH3_math_2, Color.RED);
//                    } else {
//                        holder.setText(R.id.tv_PH3_math_2,item.getmCardLockData().getPointPH3_2().toString()+"ppm");
//                    }
//                }
//            } else {
//                holder.setViewVisibility(R.id.layout_lixian,View.VISIBLE);
//                holder.setViewVisibility(R.id.layout_zaixian,View.GONE);
//            }
//        } else {
//            holder.setViewVisibility(R.id.layout_lixian,View.VISIBLE);
//            holder.setViewVisibility(R.id.layout_zaixian,View.GONE);
//        }
//
//        RelativeLayout cardView = holder.getView(R.id.card_adapter_layout);
//        cardView.setOnClickListener(this);
//        cardView.setTag(position);
//    }
//    public interface OnLineFinished {
//        void OnLineFinishedListener(boolean success);
//    }
//    private void OnLine(SmartLockBody item,final OnLineFinished listener) {
//        //判断设备是否在线
//        Gson gson = new Gson();
//        OnLineOrDisBody newLine = new OnLineOrDisBody();
//        newLine.setCommandType("01");
//        newLine.setModuleNameList(Collections.singletonList(item.getModulename()));
//        String gsonData = gson.toJson(newLine);
//        OkHttpUtil.Post1("api/v1/newDownRaw?deviceType=LQZJ&bodyType=json&timeout=5", gsonData, Token, new OkHttpUtil.ReqCallBack() {
//            public void onReqSuccess(Object result) {
//                Gson gson = new Gson();
//                OnLineOrDisBean onLine = new OnLineOrDisBean();
//                try {
//                    onLine = gson.fromJson((String) result,OnLineOrDisBean.class);
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                    return;
//                }
//                if (onLine.getData().getStatusList().size()>0){
//                    if (onLine.getData().getStatusList().get(0).getStatus()!=null){
//                        item.setStatus(onLine.getData().getStatusList().get(0).getStatus());
//                    }
//                }
//                listener.OnLineFinishedListener(true);
//            }
//            @Override
//            public void onReqFailed(String errorMsg) {
//                listener.OnLineFinishedListener(false);
//            }
//        });
    }

    public void setFreshDates(ArrayList<TemGranaryBody> datas){
        this.refreshdatas = datas;
    }
    public ArrayList<TemGranaryBody> getFreshDates(){
        return refreshdatas != null ? refreshdatas:new ArrayList<>();
    }
    public interface OnItemClickListener{
        void onItemClick(View v, TemGranaryBody item, int position);
    }
    private OnItemClickListener mOnItemClickListener;//声明自定义接口
    //定义方法并传给外面使用
    public void setmOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}

