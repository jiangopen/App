package com.example.multiplegranarymanager.A_ShuChengPackage.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice.DeviceData;
import com.example.multiplegranarymanager.A_ShuChengPackage.Body.DeviceInfos.DeviceInfos;
import com.example.multiplegranarymanager.A_ShuChengPackage.Dialog.GasInfosData;
import com.example.multiplegranarymanager.R;

import java.util.ArrayList;

public class DetectionAdapter extends RecyclerView.Adapter<DetectionAdapter.CardViewHolder> implements View.OnClickListener{
    private ArrayList<DeviceInfos> datas;
    private ArrayList<DeviceInfos> refreshdatas;

    public int flag = 0;
    private Context context;
    private GasInfosData Gas_Dialog;
    public DetectionAdapter(ArrayList<DeviceInfos> mDatas, Context context) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_adapter_shucheng,parent,false);
        DetectionAdapter.CardViewHolder holder = new DetectionAdapter.CardViewHolder(view);
        holder.txt_name = view.findViewById(R.id.tv_depot_name);
        holder.txt_type = view.findViewById(R.id.tv_depot_grain);
        holder.txt_info = view.findViewById(R.id.tv_depot_gas);
        holder.txt_tem_out = view.findViewById(R.id.tv_out_temtext);
        holder.txt_tem_int = view.findViewById(R.id.tv_in_temtext);
        holder.txt_hum_out = view.findViewById(R.id.tv_out_montext);
        holder.txt_hum_int = view.findViewById(R.id.tv_in_montext);
        holder.txt_tem_max = view.findViewById(R.id.tv_max_temtext);
        holder.txt_tem_min = view.findViewById(R.id.tv_min_temtext);
        holder.txt_tem_ave = view.findViewById(R.id.tv_avg_temtext);
        holder.txt_water = view.findViewById(R.id.tv_watertext);
        holder.txt_name.setSelected(true);
        holder.txt_type.setSelected(true);
        holder.txt_info.setSelected(true);
        holder.txt_tem_out.setSelected(true);
        holder.txt_tem_int.setSelected(true);
        holder.txt_hum_out.setSelected(true);
        holder.txt_hum_int.setSelected(true);
        holder.txt_tem_max.setSelected(true);
        holder.txt_tem_min.setSelected(true);
        holder.txt_tem_ave.setSelected(true);
        holder.txt_water.setSelected(true);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        final DeviceInfos item = datas.get(position);
        //仓名
        if (item.getGranaryName() != null) {
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

        holder.txt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getGasDeviceData()!=null) {
                    SetDialog(item.getGasDeviceData(),item.getGranaryName());
                } else {
                    SetDialog(null, item.getGranaryName());
                }

            }
        });

        if (item.getGrainType() != null) {
            holder.txt_type.setText(item.getGrainType());
        } else {
            holder.txt_type.setText("未知");
        }

        if (item.getSelected()){
            holder.txt_name.setBackgroundResource(R.drawable.card_name_bg_select_name);
            holder.txt_name.setTextColor(ContextCompat.getColor(context, R.color.third_top_ss));
        } else {
            holder.txt_name.setBackgroundResource(R.drawable.card_name_bg_unselect_name);
            holder.txt_name.setTextColor(ContextCompat.getColor(context, R.color.fourth_top_gz));
        }

        //外温
        if (item.getTemOuter()!=null&&item.getTemOuter()!=255){
            holder.txt_tem_out.setText(item.getTemOuter().toString()+"℃");
        } else if (item.getTemOuter()!=null&&item.getTemOuter()==255){
            holder.txt_tem_out.setText("断路");
            holder.txt_tem_out.setTextColor(Color.RED);
        } else {
            holder.txt_tem_out.setText("————");
        }
        //内温
        if (item.getTemInner()!=null&&item.getTemInner()!=255){
            holder.txt_tem_int.setText(item.getTemInner().toString()+"℃");
        } else if (item.getTemInner()!=null&&item.getTemInner()==255){
            holder.txt_tem_int.setText("断路");
            holder.txt_tem_int.setTextColor(Color.RED);
        } else {
            holder.txt_tem_int.setText("————");
        }
        //外湿
        if (item.getHumOuter()!=null&&item.getHumOuter()!=255){
            holder.txt_hum_out.setText(item.getHumOuter().toString()+"%");
        } else if (item.getHumOuter()!=null&&item.getHumOuter()==255){
            holder.txt_hum_out.setText("断路");
            holder.txt_hum_out.setTextColor(Color.RED);
        } else {
            holder.txt_hum_out.setText("————");
        }
        //内湿
        if (item.getHumInner()!=null&&item.getHumInner()!=255){
            holder.txt_hum_int.setText(item.getHumInner().toString()+"%");
        } else if (item.getHumInner()!=null&&item.getHumInner()==255){
            holder.txt_hum_int.setText("断路");
            holder.txt_hum_int.setTextColor(Color.RED);
        } else {
            holder.txt_hum_int.setText("————");
        }
        //最高
        if (item.getTemMax()!=null&&item.getTemMax()!=255){
            holder.txt_tem_max.setText(item.getTemMax().toString()+"%");
        } else if (item.getTemMax()!=null&&item.getTemMax()==255){
            holder.txt_tem_max.setText("断路");
            holder.txt_tem_max.setTextColor(Color.RED);
        } else {
            holder.txt_tem_max.setText("————");
        }
        //最低
        if (item.getTemMin()!=null&&item.getTemMin()!=255){
            holder.txt_tem_min.setText(item.getTemMin().toString()+"%");
        } else if (item.getTemMin()!=null&&item.getTemMin()==255){
            holder.txt_tem_min.setText("断路");
            holder.txt_tem_min.setTextColor(Color.RED);
        } else {
            holder.txt_tem_min.setText("————");
        }
        //平均
        if (item.getTemAve()!=null&&item.getTemAve()!=255){
            holder.txt_tem_ave.setText(item.getTemAve().toString()+"%");
        } else if (item.getTemAve()!=null&&item.getTemAve()==255){
            holder.txt_tem_ave.setText("断路");
            holder.txt_tem_ave.setTextColor(Color.RED);
        } else {
            holder.txt_tem_ave.setText("————");
        }
        //水份
        if (item.getShuiFen()!=null&&item.getShuiFen()!=255){
            holder.txt_water.setText(item.getShuiFen().toString()+"%");
        } else if (item.getShuiFen()!=null&&item.getShuiFen()==255){
            holder.txt_water.setText("断路");
            holder.txt_water.setTextColor(Color.RED);
        } else {
            holder.txt_water.setText("————");
        }
    }

    private void SetDialog(DeviceData gasDeviceData, String granaryName) {
        Gas_Dialog = new GasInfosData(context,gasDeviceData,granaryName);
        Gas_Dialog.show();
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
        TextView txt_type;
        TextView txt_info;
        TextView txt_tem_out;
        TextView txt_tem_int;
        TextView txt_hum_out;
        TextView txt_hum_int;
        TextView txt_tem_max;
        TextView txt_tem_min;
        TextView txt_tem_ave;
        TextView txt_water;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.tv_depot_name);
            txt_type = itemView.findViewById(R.id.tv_depot_grain);
            txt_info = itemView.findViewById(R.id.tv_depot_gas);
            txt_tem_out = itemView.findViewById(R.id.tv_out_temtext);
            txt_tem_int = itemView.findViewById(R.id.tv_in_temtext);
            txt_hum_out = itemView.findViewById(R.id.tv_out_montext);
            txt_hum_int = itemView.findViewById(R.id.tv_in_montext);
            txt_tem_max = itemView.findViewById(R.id.tv_max_temtext);
            txt_tem_min = itemView.findViewById(R.id.tv_min_temtext);
            txt_tem_ave = itemView.findViewById(R.id.tv_avg_temtext);
            txt_water = itemView.findViewById(R.id.tv_watertext);
        }
    }
    public void setFreshDates(ArrayList<DeviceInfos> datas){
        this.refreshdatas = datas;
    }
    public ArrayList<DeviceInfos> getFreshDates(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }
    @Override
    public void onClick(View v) {
        if (mOnClickSelectListener != null){
            int position = (int) v.getTag();
            mOnClickSelectListener.onClickSelectListener(position, (DeviceInfos) getItem(position));
        }
    }
    public DetectionAdapter.OnSelectListener mOnClickSelectListener;
    public void setmOnClickSelectListener(DetectionAdapter.OnSelectListener listener){
        this.mOnClickSelectListener = listener;
    }
    public interface OnSelectListener{
        void onClickSelectListener(int id,DeviceInfos body);
    }
}
