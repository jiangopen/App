package com.example.multiplegranarymanager.A_ShuChengPackage.Adapter;

import static com.example.multiplegranarymanager.A_ShuChengPackage.Dialog.GasInfosData.moduleName;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product.DeviceInfo;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.BaseViewHolder;
import com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView.MultiLayoutAdapter;
import com.example.multiplegranarymanager.R;

import java.util.List;

public class CardGasAdapter extends RecyclerView.Adapter<CardGasAdapter.ViewHolder>{
    private List<String> Co2;
    private List<String> N2;
    private Context context;

    public CardGasAdapter(@NonNull Context mContext, List<String> mCo2, List<String> mN2) {
        this.context = mContext;
        this.Co2 = mCo2;
        this.N2 = mN2;
//        Log.d("jhtzyq", "refreshedViewsdasdasdasdasd: "+Co2.size()+"\n"+N2.size());
    }

    @NonNull
    @Override
    public CardGasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_device_info,parent,false);
//        CardGasAdapter.ViewHolder holder = new CardGasAdapter.ViewHolder(view);
//        holder.txt_name = view.findViewById(R.id.txt_name);
//        holder.txt_co2_num = view.findViewById(R.id.txt_co2_num);
//        holder.txt_n2_num = view.findViewById(R.id.txt_n2_num);
//        holder.txt_co2_name = view.findViewById(R.id.txt_co2_name);
//        holder.txt_n2_name = view.findViewById(R.id.txt_n2_name);
//
//        holder.txt_name.setSelected(true);
//        holder.txt_co2_num.setSelected(true);
//        holder.txt_n2_num.setSelected(true);
//        holder.txt_co2_name.setSelected(true);
//        holder.txt_n2_name.setSelected(true);
//        return holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_device_info, parent, false);
        CardGasAdapter.ViewHolder holder = new CardGasAdapter.ViewHolder(view);
        holder.txt_name = view.findViewById(R.id.txt_name);
        holder.txt_co2_num = view.findViewById(R.id.txt_co2_num);
        holder.txt_n2_num = view.findViewById(R.id.txt_n2_num);
        holder.txt_co2_name = view.findViewById(R.id.txt_co2_name);
        holder.txt_n2_name = view.findViewById(R.id.txt_n2_name);

        holder.txt_name.setSelected(true);
        holder.txt_co2_num.setSelected(true);
        holder.txt_n2_num.setSelected(true);
        holder.txt_co2_name.setSelected(true);
        holder.txt_n2_name.setSelected(true);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardGasAdapter.ViewHolder holder, int position) {
        // 假设列表里存储的是对应气体的相关数值等合理数据
        holder.txt_name.setText("点"+(position+1));
        if (position < Co2.size()) {
            holder.txt_co2_num.setText(Co2.get(position)+"%");
        } else {
            holder.txt_co2_num.setVisibility(View.GONE);
            holder.txt_co2_name.setVisibility(View.GONE);
        }
        if (position < N2.size()) {
            holder.txt_n2_num.setText(N2.get(position)+"%");
        } else {
            holder.txt_n2_num.setVisibility(View.GONE);
            holder.txt_n2_name.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (Co2!=null && Co2.size()>0 && N2!=null && N2.size()>0) {
            return Math.max(Co2.size(), N2.size());
        } else if (Co2==null && N2!=null && N2.size()>0) {
            return N2.size();
        } else if (N2==null && Co2!=null && Co2.size()>0) {
            return Co2.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txt_co2_num,txt_n2_num,txt_co2_name,txt_n2_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_co2_num = itemView.findViewById(R.id.txt_co2_num);
            txt_n2_num = itemView.findViewById(R.id.txt_n2_num);
            txt_co2_name = itemView.findViewById(R.id.txt_co2_name);
            txt_n2_name = itemView.findViewById(R.id.txt_n2_name);
        }
    }
}
