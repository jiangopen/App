package com.example.multiplegranarymanager.A_ShuChengPackage.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.UserAlertInfos.AlertInfo;
import com.example.multiplegranarymanager.R;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyAlartAdapter extends SwipeRecyclerView.Adapter<MyAlartAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<AlertInfo> mDatas;
    private LayoutInflater mInflater;
    public boolean flage = false;//是否为编辑状态
    public int index = 0;//编辑状态选中个数
    public MyAlartAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(this.mContext);
    }
    public void notifyAdapter(List<AlertInfo> myLiveList,boolean isAdd){
        if (!isAdd){
            this.mDatas = myLiveList;
        } else {
            this.mDatas.addAll(myLiveList);
            Log.e("MyAlartAdapter", mDatas.size()+"条");
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View convertView;
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_data,parent,false);
        MyViewHolder holder = new MyViewHolder(convertView);
        holder.textTitle = convertView.findViewById(R.id.text_title);
        holder.textDesc = convertView.findViewById(R.id.text_desc);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AlertInfo dataBean = mDatas.get(position);
        if (dataBean != null){
            holder.textTitle.setText(dataBean.getContent());
            String timeString = TimeStamp2Data(String.valueOf(dataBean.getTime()),"yyyy-MM-dd HH:mm:ss");
            holder.textDesc.setText(timeString);
            holder.itemView.setTag(position);
            //根据isSelected来设置CheckBox的现实状况
            if (flage){
                holder.materialItemImg.setVisibility(View.VISIBLE);
            } else {
                holder.materialItemImg.setVisibility(View.GONE);
            }
            if (dataBean.isCheck){
                holder.materialItemImg.setImageResource(R.drawable.zzz_yesselect);
            } else {
                holder.materialItemImg.setImageResource(R.drawable.zzz_unselect);
            }
            holder.itemView.setOnClickListener(this);
        }
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param valueOf 时间戳 如：“147986256465”;
     * @param s       要格式化的格式 默认：“yyyy-MM-dd HH:mm:ss”;
     * @return 返回结果 如：“2023-12-04 10:37:56”;
     * */
    public static String TimeStamp2Data(String valueOf, String s) {
        if (TextUtils.isEmpty(s)){
            s = "yyyy-MM-dd HH:mm:ss";
        }
        long timestamp = Long.parseLong(valueOf)*1000;
        String date = new SimpleDateFormat(s, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0:mDatas.size();
    }

    public Object getItem(int i){
        return mDatas == null ? null:mDatas.get(i);
    }

    @Override
    public long getItemId(int position){
        return super.getItemId(position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            int position = (int) v.getTag();
            mOnItemClickListener.onItemClickListener(position, (AlertInfo) getItem(position));
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle;
        TextView textDesc;
        ImageView materialItemImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textDesc = itemView.findViewById(R.id.text_desc);
            materialItemImg = itemView.findViewById(R.id.material_item_img);
        }
    }

    private OnSwipeListener mOnItemClickListener;//声明自定义的接口

    //定义方法并传给外面的使用者
    public void setmOnItemClickListener(OnSwipeListener listener){
        this.mOnItemClickListener = listener;
    }

    //在adapter中暴露一个Item的点击事件的接口
    public interface OnSwipeListener {
        void onItemClickListener(int pos,AlertInfo myLiveList);
    }
}
