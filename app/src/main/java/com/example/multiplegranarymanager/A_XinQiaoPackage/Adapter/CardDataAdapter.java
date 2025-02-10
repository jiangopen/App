package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter;

import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;

import android.content.Context;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body.CardAdapterBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.R;

import java.util.ArrayList;

public class CardDataAdapter extends MultiLayoutAdapter<CardAdapterBody> implements SectionIndexer, View.OnClickListener {
    /**
     * layoutId的顺序必须和ViewType的顺序相同，而且从零开始
     *
     * @param mDataList
     * @param layoutIds
     */
    private ArrayList<CardAdapterBody> datas;
    private ArrayList<CardAdapterBody> refreshdatas;
    private ArrayList<CardAdapterBody> mSelectedItems = new ArrayList<>();
    private Context context;
    private int opened = -1;//卡片是否选中的标记
    private int notifactionType = TYPE_NORMAL;

    public CardDataAdapter(ArrayList<CardAdapterBody> mDatas, int[] layoutIds, Context context) {
        super(mDatas, layoutIds);
        this.datas = mDatas;
        this.refreshdatas = mDatas;
        this.context = context;
        if (datas == null){
            datas = new ArrayList<>(0);
        }
    }
    public interface OnItemClickListener {
        void onItemSelectClick(View v, CardAdapterBody item, int position);
    }
    public OnItemClickListener mOnMultiSelectListener;
    public void setmOnMultiSelectListener(OnItemClickListener listener){
        this.mOnMultiSelectListener = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();//getTag()获取数据
        if (mSelectedItems.contains(datas.get(position))){
            mSelectedItems.remove(datas.get(position));
        } else {
            mSelectedItems.add(datas.get(position));
        }
        notifyDataSetChanged();
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

    public void setFreshDates(ArrayList<CardAdapterBody> data){
        this.refreshdatas = data;
    }

    public ArrayList<CardAdapterBody> getFreshDates(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }

    @Override
    protected void coverts(BaseViewHolder holder, CardAdapterBody item, int position, int itemType) {
        TextView select = holder.getView(R.id.name);
        holder.setText(R.id.name,item.getGranaryName());
        if (mSelectedItems.contains(item)){
            select.setBackgroundResource(R.drawable.card_name_bg_select_name);
            select.setTextColor(ContextCompat.getColor(context, R.color.third_top_ss));
        } else {
            select.setBackgroundResource(R.drawable.card_name_bg_unselect_name);
            select.setTextColor(ContextCompat.getColor(context, R.color.fourth_top_gz));
        }

    }
}
