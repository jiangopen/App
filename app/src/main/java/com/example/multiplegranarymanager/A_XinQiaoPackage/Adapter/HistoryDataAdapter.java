package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter;

import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CardAdapterBody;
import com.example.multiplegranarymanager.R;

import java.util.ArrayList;

public class HistoryDataAdapter extends MultiLayoutAdapter<CardAdapterBody> implements SectionIndexer, View.OnClickListener {
    /**
     * layoutId的顺序必须和ViewType的顺序相同，而且从零开始
     *
     * @param mDataList
     * @param layoutIds
     */
    private ArrayList<CardAdapterBody> datas;
    private ArrayList<CardAdapterBody> refreshDatas;
    public int opened = -1;//卡片是否选中标志位
    private Context context;
    private int notifactionType = TYPE_NORMAL;
    public HistoryDataAdapter(ArrayList<CardAdapterBody> mDatas, int[] layoutIds, Context context) {
        super(mDatas, layoutIds);
        datas = mDatas;
        refreshDatas = mDatas;
        this.context = context;
        if (datas == null){
            datas = new ArrayList<>(0);
        }
    }
    public void setFreshDates(ArrayList<CardAdapterBody> datas) {
        this.refreshDatas = datas;
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener {
        void onItemClick(View v,CardAdapterBody item,int position);
    }
    private OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setmOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        CardAdapterBody item = getItem(position);
        if (v.getId() == R.id.layout_adapter_card){
            if (mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(v,item,position);
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
    public int getPositionForSection(int sectionIndex) {
        for (int i=0;i<getItemCount();i++){
            char firstChar = datas.get(i).getGranaryName().charAt(0);
            if (firstChar == sectionIndex){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        CardAdapterBody item = datas.get(position);
        return item.getGranaryName().charAt(0);
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }
    public ArrayList<CardAdapterBody> getFreshDates(){
        return refreshDatas != null ? refreshDatas : new ArrayList<>();
    }

    @Override
    protected void coverts(BaseViewHolder holder, CardAdapterBody item, int position, int itemType) {
        if (item.getGranaryName()!=null) {
            holder.setText(R.id.txt_name,item.getGranaryName());
        } else {
            holder.setText(R.id.txt_name,"");
        }
         if (item.getDate()!=null) {
             holder.setText(R.id.txt_time_num,item.getDate());
         } else {
             holder.setText(R.id.txt_time_num,"----");
         }
        RelativeLayout cardView = holder.getView(R.id.layout_adapter_card);
        cardView.setOnClickListener(this);
        cardView.setTag(position);
        TextView select = holder.getView(R.id.txt_name);
        if (position == opened) {
            select.setBackgroundResource(R.drawable.card_name_bg_select_name);
            select.setTextColor(ContextCompat.getColor(context, R.color.third_top_ss));
        } else {
            select.setBackgroundResource(R.drawable.card_name_bg_unselect_name);
            select.setTextColor(ContextCompat.getColor(context, R.color.fourth_top_gz));
        }
    }
}
