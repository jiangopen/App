package com.example.multiplegranarymanager.A_TestPackage.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.Body.QiTiao.QiTiaoBody;
import com.example.multiplegranarymanager.R;

import java.util.ArrayList;

public class CardQiTiaoDataAdapter extends MultiLayoutAdapter<QiTiaoBody> implements SectionIndexer, View.OnClickListener {
    /**
     * layoutId的顺序必须和ViewType的顺序相同，而且从零开始
     *
     * @param mDataList
     * @param layoutIds
     */
    private ArrayList<QiTiaoBody> datas;
    private ArrayList<QiTiaoBody> refreshdatas;
    private Context context;
    public int opened = -1;//卡片是否选中标志位
    public CardQiTiaoDataAdapter(ArrayList<QiTiaoBody> mDatas, int[] layoutIds, Context context) {
        super(mDatas, layoutIds);
        this.datas = mDatas;
        this.refreshdatas = mDatas;
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        QiTiaoBody item = getItem(position);
        if (v.getId() == R.id.layout_adapter_card){
            if (mOnItemClickListener != null){
                mOnItemClickListener.OnItemClick(v,item,position);
            }
            if (opened == position){
                opened = -1;
                notifyDataSetChanged();
            } else {
                opened = position;
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i=0;i<getItemCount();i++){
            int firstChar = datas.get(i).getProductName().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        QiTiaoBody item = datas.get(position);
        return item.getProductName().charAt(0);
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }

    public void setFreshDatas(ArrayList<QiTiaoBody> datas){
        this.refreshdatas = datas;
    }
    public ArrayList<QiTiaoBody> getFreshDatas(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }
    public interface OnItemClickListener{
        void OnItemClick(View v,QiTiaoBody item, int position);
    }
    private OnItemClickListener mOnItemClickListener;//申明自定义接口
    public void setmOnItemClickListener(CardQiTiaoDataAdapter.OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
    @Override
    protected void coverts(BaseViewHolder holder, QiTiaoBody item, int position, int itemType) {
        if (item.getProductName()!=null){
            holder.setText(R.id.txt_name,item.getProductName());
        } else {
            holder.setText(R.id.txt_name,"--");
        }
        TextView name = holder.getView(R.id.txt_name);
        if (position==opened){
            name.setBackgroundResource(R.drawable.card_name_bg_select_name);
            name.setTextColor(ContextCompat.getColor(context,R.color.third_top_ss));
        } else {
            name.setBackgroundResource(R.drawable.card_name_bg_unselect_name);
            name.setTextColor(ContextCompat.getColor(context,R.color.fourth_top_gz));
        }

        if (item.getDate()!=null){
            holder.setText(R.id.txt_time_num,item.getDate());
        } else {
            holder.setText(R.id.txt_time_num,"--");
        }

        TextView txt_name_N2_num = holder.getView(R.id.txt_n2_num);
        TextView txt_name_Pa_num = holder.getView(R.id.txt_pressure_num);
        TextView txt_N2_Pa_num = holder.getView(R.id.txt_n2pressure_num);
        TextView txt_N2_CD_num = holder.getView(R.id.txt_chundu_num);
        TextView txt_N2_WD_num = holder.getView(R.id.txt_tem_num);
        TextView txt_N2_LL_num = holder.getView(R.id.txt_liuliang_num);
        TextView txt_N2_LJ_num = holder.getView(R.id.txt_sum_num);
        txt_name_N2_num.setSelected(true);
        txt_name_Pa_num.setSelected(true);
        txt_N2_Pa_num.setSelected(true);
        txt_N2_CD_num.setSelected(true);
        txt_N2_WD_num.setSelected(true);
        txt_N2_LL_num.setSelected(true);
        txt_N2_LJ_num.setSelected(true);

        if (item.getQiTiaoStatus_list_02().equals("00")){
            holder.setText(R.id.txt_qitiao_status,"手动模式");
        } else if (item.getQiTiaoStatus_list_02().equals("01")) {
            holder.setText(R.id.txt_qitiao_status,"自动气调 ——> 下充气法");
        } else if (item.getQiTiaoStatus_list_02().equals("02")) {
            holder.setText(R.id.txt_qitiao_status,"自动气调 ——> 上充气法");
        } else if (item.getQiTiaoStatus_list_02().equals("03")) {
            holder.setText(R.id.txt_qitiao_status,"自动气调 ——> 环流");
        } else if (item.getQiTiaoStatus_list_02().equals("04")) {
            holder.setText(R.id.txt_qitiao_status,"自动气调 ——> 气密性检测");
        } else {
            holder.setText(R.id.txt_qitiao_status,"--");
        }

        if (item.getN2QiJiStatus_list_05().equals("0")){
            holder.setText(R.id.txt_n2qiji_status,"关");
        } else if (item.getN2QiJiStatus_list_05().equals("1")) {
            holder.setText(R.id.txt_n2qiji_status,"开");
        } else {
            holder.setText(R.id.txt_n2qiji_status,"--");
        }

        if (item.getN2_list_00()!=null){
            holder.setText(R.id.txt_n2_num,item.getN2_list_00());
        } else {
            holder.setText(R.id.txt_n2_num,"--");
        }

        if (item.getPressure_list_01()!=null){
            holder.setText(R.id.txt_pressure_num,item.getPressure_list_01());
        } else {
            holder.setText(R.id.txt_pressure_num,"--");
        }

        if (item.getN2QiJiStatus_list_05()!=null){
            holder.setText(R.id.txt_n2pressure_num,item.getN2QiJiStatus_list_05());
        } else {
            holder.setText(R.id.txt_n2pressure_num,"--");
        }

        if (item.getChunDu_list_06()!=null){
            holder.setText(R.id.txt_chundu_num,item.getChunDu_list_06());
        } else {
            holder.setText(R.id.txt_chundu_num,"--");
        }

        if (item.getTemperature_list_09()!=null){
            holder.setText(R.id.txt_tem_num,item.getTemperature_list_09());
        } else {
            holder.setText(R.id.txt_tem_num,"--");
        }

        if (item.getLiuLiang_list_07()!=null){
            holder.setText(R.id.txt_liuliang_num,item.getLiuLiang_list_07());
        } else {
            holder.setText(R.id.txt_liuliang_num,"--");
        }

        if (item.getLiuLiangNum_list_10()!=null){
            holder.setText(R.id.txt_sum_num,item.getLiuLiangNum_list_10());
        } else {
            holder.setText(R.id.txt_sum_num,"--");
        }
        RelativeLayout cardView = holder.getView(R.id.layout_adapter_card);
        cardView.setOnClickListener(this);
        cardView.setTag(position);
    }
}
