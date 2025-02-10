package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.CardAdapterBody;
import com.example.multiplegranarymanager.A_XinQiaoPackage.Body.EnergyBody;
import com.example.multiplegranarymanager.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EnergyCardDataAdapter extends RecyclerView.Adapter<EnergyCardDataAdapter.CardViewHolder> {
    private ArrayList<EnergyBody> datas;
    private ArrayList<EnergyBody> refreshdatas;
    public int flag = 0;
    private Context context;

    public EnergyCardDataAdapter(ArrayList<EnergyBody> mDatas, Context context) {
        this.datas = mDatas;
        this.refreshdatas = mDatas;
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
    }
    public int getPositionForSection(int section) {
        for (int i=0;i<getItemCount();i++){
            char firstChar = datas.get(i).getName().charAt(0);
            if (firstChar == section){
                return i;
            }
        }
        return -1;
    }
    @NonNull
    @Override
    public EnergyCardDataAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_energy_card,parent,false);
        CardViewHolder holder = new CardViewHolder(view);
        holder.txt_energy_name = view.findViewById(R.id.txt_energy_name);
        holder.txt_name = view.findViewById(R.id.name);
        holder.txt_granary_num = view.findViewById(R.id.txt_granary_num);
        holder.txt_energy_sum = view.findViewById(R.id.txt_energy_sum);
        holder.txt_HZ_num = view.findViewById(R.id.txt_HZ_num);
        holder.txt_yin = view.findViewById(R.id.txt_yin);
        holder.txt_A_L_num = view.findViewById(R.id.txt_A_L_num);
        holder.txt_A_U_num = view.findViewById(R.id.txt_A_U_num);
        holder.txt_B_L_num = view.findViewById(R.id.txt_B_L_num);
        holder.txt_B_U_num = view.findViewById(R.id.txt_B_U_num);
        holder.txt_C_L_num = view.findViewById(R.id.txt_C_L_num);
        holder.txt_C_U_num = view.findViewById(R.id.txt_C_U_num);
        holder.txt_name = view.findViewById(R.id.name);
        holder.txt_energy_name.setSelected(true);
        holder.txt_granary_num.setSelected(true);
        holder.txt_energy_sum.setSelected(true);
        holder.txt_HZ_num.setSelected(true);
        holder.txt_yin.setSelected(true);
        holder.txt_A_L_num.setSelected(true);
        holder.txt_A_U_num.setSelected(true);
        holder.txt_B_L_num.setSelected(true);
        holder.txt_B_U_num.setSelected(true);
        holder.txt_C_L_num.setSelected(true);
        holder.txt_C_U_num.setSelected(true);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EnergyCardDataAdapter.CardViewHolder holder, int position) {
        final EnergyBody item = datas.get(position);
        DecimalFormat nf = new DecimalFormat("0.0");
        float num = 0;
        //仓名
        if (item.getName()!=null) {
            holder.txt_name.setText(item.getName());
        } else {
            holder.txt_name.setText("——");
        }

        if (item.getGranaryName()!=null) {
            holder.txt_granary_num.setText(item.getGranaryName());
        } else {
            holder.txt_granary_num.setText("——");
        }
//        Log.d("zyq", "onBindViewHolder: "+item.getActiveElectricEnergy());
        if (item.getActiveElectricEnergy()!=null) {
            Log.d("zyq", "onBindViewHolder: "+item.getActiveElectricEnergy());
//            num = Float.valueOf(item.getActiveElectricEnergy());
//            holder.txt_energy_sum.setText(nf.format(num).toString()+"KWH");
            holder.txt_energy_sum.setText(item.getActiveElectricEnergy().toString()+"KWH");
        } else {
            holder.txt_energy_sum.setText("——KWH");
        }

        if (item.getaI()!=null) {
            num = Float.valueOf(item.getaI());
            holder.txt_A_L_num.setText(nf.format(num)+"A");
        } else {
            holder.txt_A_L_num.setText("——A");
        }
        if (item.getaU()!=null) {
            num = Float.valueOf(item.getaU());
            holder.txt_A_U_num.setText(nf.format(num)+"V");
        } else {
            holder.txt_A_U_num.setText("——V");
        }
        if (item.getbI()!=null) {
            num = Float.valueOf(item.getbI());
            holder.txt_B_L_num.setText(nf.format(num)+"A");
        } else {
            holder.txt_B_L_num.setText("——A");
        }
        if (item.getbU()!=null) {
            num = Float.valueOf(item.getbU());
            holder.txt_B_U_num.setText(nf.format(num)+"V");
        } else {
            holder.txt_B_U_num.setText("——V");
        }
        if (item.getcI()!=null) {
            num = Float.valueOf(item.getcI());
            holder.txt_C_L_num.setText(nf.format(num)+"A");
        } else {
            holder.txt_C_L_num.setText("——A");
        }
        if (item.getcU()!=null) {
            num = Float.valueOf(item.getcU());
            holder.txt_C_U_num.setText(nf.format(num)+"V");
        } else {
            holder.txt_C_U_num.setText("——V");
        }
        if (item.getFrequency()!=null) {
            num = Float.valueOf(item.getFrequency());
            holder.txt_HZ_num.setText(nf.format(num)+"Hz");
        } else {
            holder.txt_HZ_num.setText("——Hz");
        }
        if (item.getPowerFactor()!=null) {
            num = Float.valueOf(item.getPowerFactor());
            holder.txt_yin.setText(nf.format(num));
        } else {
            holder.txt_yin.setText("——");
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
        TextView txt_energy_name,txt_name,txt_granary_num, txt_energy_sum, txt_HZ_num, txt_yin, txt_A_L_num, txt_A_U_num, txt_B_L_num, txt_B_U_num, txt_C_L_num, txt_C_U_num;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_energy_name = itemView.findViewById(R.id.txt_energy_name);
            txt_name = itemView.findViewById(R.id.name);
            txt_granary_num = itemView.findViewById(R.id.txt_granary_num);
            txt_energy_sum = itemView.findViewById(R.id.txt_energy_sum);
            txt_HZ_num = itemView.findViewById(R.id.txt_HZ_num);
            txt_yin = itemView.findViewById(R.id.txt_yin);
            txt_A_L_num = itemView.findViewById(R.id.txt_A_L_num);
            txt_A_U_num = itemView.findViewById(R.id.txt_A_U_num);
            txt_B_L_num = itemView.findViewById(R.id.txt_B_L_num);
            txt_B_U_num = itemView.findViewById(R.id.txt_B_U_num);
            txt_C_L_num = itemView.findViewById(R.id.txt_C_L_num);
            txt_C_U_num = itemView.findViewById(R.id.txt_C_U_num);
        }
    }
    public void setFreshDates(ArrayList<EnergyBody> datas){
        this.refreshdatas = datas;
    }
    public ArrayList<EnergyBody> getFreshDates(){
        return refreshdatas != null ? refreshdatas : new ArrayList<>();
    }
}
