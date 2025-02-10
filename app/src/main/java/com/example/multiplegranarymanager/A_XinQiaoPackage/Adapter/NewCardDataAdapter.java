package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CardAdapterBody;
import com.example.multiplegranarymanager.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NewCardDataAdapter extends RecyclerView.Adapter<NewCardDataAdapter.CardViewHolder> implements View.OnClickListener {
    private ArrayList<CardAdapterBody> datas;
    private ArrayList<CardAdapterBody> refreshdatas;
    public int flag = 0;
    private Context context;
    public NewCardDataAdapter(ArrayList<CardAdapterBody> mDatas, Context context) {
        this.datas = mDatas;
        this.refreshdatas = mDatas;
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
    }
    public int getPositionForSection(int section) {
        for (int i=0;i<getItemCount();i++){
            char firstChar = datas.get(i).getGranaryName().charAt(0);
            if (firstChar == section){
                return i;
            }
        }
        return -1;
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_adapter_data,parent,false);
        CardViewHolder holder = new CardViewHolder(view);
        holder.txt_name = view.findViewById(R.id.name);
        holder.txt_time = view.findViewById(R.id.txt_time_num);
        holder.txt_tem_out = view.findViewById(R.id.txt_tem_out_num);
        holder.txt_tem_int = view.findViewById(R.id.txt_tem_in_num);
        holder.txt_hum_out = view.findViewById(R.id.txt_hum_out_num);
        holder.txt_hum_int = view.findViewById(R.id.txt_hum_in_num);
        holder.txt_tem_max = view.findViewById(R.id.txt_tem_max_num);
        holder.txt_tem_min = view.findViewById(R.id.txt_tem_min_num);
        holder.txt_tem_ave = view.findViewById(R.id.txt_tem_ave_num);
        holder.txt_name.setSelected(true);
        holder.txt_time.setSelected(true);
        holder.txt_tem_out.setSelected(true);
        holder.txt_tem_int.setSelected(true);
        holder.txt_hum_out.setSelected(true);
        holder.txt_hum_int.setSelected(true);
        holder.txt_tem_max.setSelected(true);
        holder.txt_tem_min.setSelected(true);
        holder.txt_tem_ave.setSelected(true);
        return holder;
    }
    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        final CardAdapterBody item = datas.get(position);
        //仓名
        if (item.getGranaryName()!=null){
            holder.txt_name.setText(item.getGranaryName());
        } else {
            holder.txt_name.setText("未知");
        }
        holder.txt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getSelected()){
                    item.setSelected(false);
                    holder.txt_name.setBackgroundResource(R.drawable.card_name_bg_unselect_name);
                    holder.txt_name.setTextColor(ContextCompat.getColor(context, R.color.fourth_top_gz));
                } else {
                    item.setSelected(true);
                    holder.txt_name.setBackgroundResource(R.drawable.card_name_bg_select_name);
                    holder.txt_name.setTextColor(ContextCompat.getColor(context, R.color.third_top_ss));
                }
            }
        });
        if (item.getSelected()){
            holder.txt_name.setBackgroundResource(R.drawable.card_name_bg_select_name);
            holder.txt_name.setTextColor(ContextCompat.getColor(context, R.color.third_top_ss));
        } else {
            holder.txt_name.setBackgroundResource(R.drawable.card_name_bg_unselect_name);
            holder.txt_name.setTextColor(ContextCompat.getColor(context, R.color.fourth_top_gz));
        }
        //时间
        if (item.getDate()!=null){
            holder.txt_time.setText(item.getDate());
        } else {
            holder.txt_time.setText("————");
        }
        //外温
        if (item.getTempOut()!=null&&item.getTempOut()!=255){
            holder.txt_tem_out.setText(item.getTempOut().toString()+"℃");
        } else if (item.getTempOut()!=null&&item.getTempOut()==255){
            holder.txt_tem_out.setText("断路");
            holder.txt_tem_out.setTextColor(Color.RED);
        } else {
            holder.txt_tem_out.setText("————");
        }
        //内温
        if (item.getTempInner()!=null&&item.getTempInner()!=255){
            holder.txt_tem_int.setText(item.getTempInner().toString()+"℃");
        } else if (item.getTempInner()!=null&&item.getTempInner()==255){
            holder.txt_tem_int.setText("断路");
            holder.txt_tem_int.setTextColor(Color.RED);
        } else {
            holder.txt_tem_int.setText("————");
        }
        //外湿
        if (item.getHumidityOut()!=null&&item.getHumidityOut()!=255){
            holder.txt_hum_out.setText(item.getHumidityOut().toString()+"%");
        } else if (item.getHumidityOut()!=null&&item.getHumidityOut()==255){
            holder.txt_hum_out.setText("断路");
            holder.txt_hum_out.setTextColor(Color.RED);
        } else {
            holder.txt_hum_out.setText("————");
        }
        //内湿
        if (item.getHumidityInner()!=null&&item.getHumidityInner()!=255){
            holder.txt_hum_int.setText(item.getHumidityInner().toString()+"%");
        } else if (item.getHumidityInner()!=null&&item.getHumidityInner()==255){
            holder.txt_hum_int.setText("断路");
            holder.txt_hum_int.setTextColor(Color.RED);
        } else {
            holder.txt_hum_int.setText("————");
        }
        //温度列表
        if (item.getTempList()!=null&&item.getTempList().size()>0){
            List<Double> TemList = item.getTempList();
            DecimalFormat nf = new DecimalFormat("0.0");
            //利用stream去除列表中的元素
            TemList = TemList
                    .stream()
                    .filter(value -> value!=255)
                    .collect(Collectors.toList());
            if (TemList.size()>0){
                Double max = Collections.max(TemList);
                Double min = Collections.min(TemList);
                Double ave = TemList
                        .stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0);
                //最高温
                holder.txt_tem_max.setText(nf.format(max)+"℃");
                //最低温
                holder.txt_tem_min.setText(nf.format(min)+"℃");
                //平均温
                holder.txt_tem_ave.setText(nf.format(ave)+"℃");
            } else {
                //最高温
                holder.txt_tem_max.setText("断路");
                holder.txt_tem_max.setTextColor(Color.RED);
                //最低温
                holder.txt_tem_min.setText("断路");
                holder.txt_tem_min.setTextColor(Color.RED);
                //平均温
                holder.txt_tem_ave.setText("断路");
                holder.txt_tem_ave.setTextColor(Color.RED);
            }
        } else {
            //最高温
            holder.txt_tem_max.setText("————");
            //最低温
            holder.txt_tem_min.setText("————");
            //平均温
            holder.txt_tem_ave.setText("————");
        }

    }
    @Override
    public int getItemCount() {
        return datas == null ? 0:datas.size();
    }
    public Object getItem(int i){
        return datas == null ? null : datas.get(i);
    }
    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        TextView txt_time;
        TextView txt_tem_out;
        TextView txt_tem_int;
        TextView txt_hum_out;
        TextView txt_hum_int;
        TextView txt_tem_max;
        TextView txt_tem_min;
        TextView txt_tem_ave;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.name);
            txt_time = itemView.findViewById(R.id.txt_time_num);
            txt_tem_out = itemView.findViewById(R.id.txt_tem_out_num);
            txt_tem_int = itemView.findViewById(R.id.txt_tem_in_num);
            txt_hum_out = itemView.findViewById(R.id.txt_hum_out_num);
            txt_hum_int = itemView.findViewById(R.id.txt_hum_in_num);
            txt_tem_max = itemView.findViewById(R.id.txt_tem_max_num);
            txt_tem_min = itemView.findViewById(R.id.txt_tem_min_num);
            txt_tem_ave = itemView.findViewById(R.id.txt_tem_ave_num);
        }
    }
    public void setFreshDates(ArrayList<CardAdapterBody> datas){
        this.refreshdatas = datas;
    }
    public ArrayList<CardAdapterBody> getFreshDates(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }
    @Override
    public void onClick(View v) {
        if (mOnClickSelectListener != null){
            int position = (int) v.getTag();
            mOnClickSelectListener.onClickSelectListener(position, (CardAdapterBody) getItem(position));
        }
    }
    public OnSelectListener mOnClickSelectListener;
    public void setmOnClickSelectListener(OnSelectListener listener){
        this.mOnClickSelectListener = listener;
    }
    public interface OnSelectListener{
        void onClickSelectListener(int id,CardAdapterBody body);
    }
}
