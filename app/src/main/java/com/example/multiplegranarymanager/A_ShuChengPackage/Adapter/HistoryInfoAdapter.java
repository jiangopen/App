package com.example.multiplegranarymanager.A_ShuChengPackage.Adapter;

import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplegranarymanager.A_ShuChengPackage.Body.DeviceInfos.DeviceInfos;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.HistoryDataAdapter;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CardAdapterBody;
import com.example.multiplegranarymanager.R;

import java.util.ArrayList;

public class HistoryInfoAdapter extends MultiLayoutAdapter<DeviceInfos> implements SectionIndexer, View.OnClickListener {
    private ArrayList<DeviceInfos> datas;
    private ArrayList<DeviceInfos> refreshDatas;
    public int opened = -1;
    private Context context;
    private int notifactionType = TYPE_NORMAL;
    public HistoryInfoAdapter(ArrayList<DeviceInfos> mDatas, int[] layoutIds, Context context) {
        super(mDatas,layoutIds);
        datas = mDatas;
        refreshDatas = mDatas;
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
    }
    public void setFreshDates(ArrayList<DeviceInfos> datas) {
        this.refreshDatas = datas;
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener {
        void onItemClick(View v,DeviceInfos item,int position);
    }
    private OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setmOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        DeviceInfos item = getItem(position);
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
        DeviceInfos item = datas.get(position);
        return item.getGranaryName().charAt(0);
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }
    public ArrayList<DeviceInfos> getFreshDates(){
        return refreshDatas != null ? refreshDatas : new ArrayList<>();
    }
    @Override
    protected void coverts(BaseViewHolder holder, DeviceInfos item, int position, int itemType) {
        if (item.getGranaryName() != null) {
            holder.setText(R.id.txt_name,item.getGranaryName());
        } else {
            holder.setText(R.id.txt_name,"");
        }
        if (item.getTemDeviceData() != null) {
            if (item.getTemDeviceData().getDate() != null) {
                holder.setText(R.id.txt_tem_num,item.getTemDeviceData().getDate());
            } else {
                holder.setText(R.id.txt_tem_num,"————");
            }
        } else {
            holder.setText(R.id.txt_tem_num,"————");
        }
        if (item.getHumDeviceData() != null) {
            if (item.getHumDeviceData().getDate() != null) {
                holder.setText(R.id.txt_hum_num,item.getHumDeviceData().getDate());
            } else {
                holder.setText(R.id.txt_hum_num,"————");
            }
        } else {
            holder.setText(R.id.txt_hum_num,"————");
        }
        if (item.getGasDeviceData() != null) {
            if (item.getGasDeviceData().getDate() != null) {
                holder.setText(R.id.txt_gas_num,item.getGasDeviceData().getDate());
            } else {
                holder.setText(R.id.txt_gas_num,"————");
            }
        } else {
            holder.setText(R.id.txt_gas_num,"————");
        }
        LinearLayout cardView = holder.getView(R.id.layout_adapter_card);
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
