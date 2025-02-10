package com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.HuoWei;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_XinQiaoPackage.SharedData.CommandGetSharedData;
import com.example.multiplegranarymanager.R;

public class GranaryInfoFragment extends Fragment {
    private TextView txt_edit;
    private EditText
            txt_type,
            txt_name,
            txt_date,
            txt_long,
            txt_weight,
            txt_height,
            txt_qiang,
            txt_door_num,
            txt_door_height,
            txt_door_weight,
            txt_fangding,
            txt_fangjia,
            txt_yangao,
            txt_ding_height,
            txt_fengdao_type,
            txt_dimian,
            txt_tongcangwaijing,
            txt_tongcangneijing,
            txt_tongfengkoushuliang,
            txt_zhouliufengjishu,
            txt_shejirongliang,
            txt_hedingrongliang,
            txt_kongqicangmixing,
            txt_duiliangxiangao,
            txt_cangqianghoudu,
            txt_cangneitiji,
            txt_gerecuoshi,
            txt_baoguanyuan;
    private String url,commandMapId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_granary_info, container, false);

        initView(view);

        Bundle bundle = getArguments();
        assert bundle != null;
        url = bundle.getString("url");
        commandMapId = bundle.getString("commandMapId");

        initDrawGranaryInfo(new OnInitDrawGranaryInfoFinishedListener() {
            @Override
            public void OnInitDrawGranaryInfoListener(boolean success) {

            }
        });

        return view;
    }
    public interface OnInitDrawGranaryInfoFinishedListener {
        void OnInitDrawGranaryInfoListener(boolean success);
    }
    private void initDrawGranaryInfo(final OnInitDrawGranaryInfoFinishedListener listener){
        String type = CommandGetSharedData.GRANARY.getData().getGranarySetting().getGrainType();
        if (type.equals("PFC")) {
            txt_type.setText("平房仓");
        } else if (type.equals("TC")) {
            txt_type.setText("筒仓");
        } else {
            txt_type.setText("——");
        }

        txt_name.setText(CommandGetSharedData.GRANARY.getData().getGranarySetting().getName());

        txt_date.setText("——");

        txt_long.setText("——");

//        txt_
    }
    private void initView(View view) {

        txt_type = view.findViewById(R.id.txt_type);
        txt_name = view.findViewById(R.id.txt_name);
        txt_date = view.findViewById(R.id.txt_date);

        txt_long = view.findViewById(R.id.txt_long);
        txt_weight = view.findViewById(R.id.txt_weight);
        txt_height = view.findViewById(R.id.txt_height);

        txt_qiang = view.findViewById(R.id.txt_qiang);
        txt_door_num = view.findViewById(R.id.txt_door_num);
        txt_door_height = view.findViewById(R.id.txt_door_height);

        txt_door_weight = view.findViewById(R.id.txt_door_weight);
        txt_fangding = view.findViewById(R.id.txt_fangding);
        txt_fangjia = view.findViewById(R.id.txt_fangjia);

        txt_yangao = view.findViewById(R.id.txt_yangao);
        txt_ding_height = view.findViewById(R.id.txt_ding_height);
        txt_fengdao_type = view.findViewById(R.id.txt_fengdao_type);

        txt_dimian = view.findViewById(R.id.txt_dimian);
        txt_tongcangwaijing = view.findViewById(R.id.txt_tongcangwaijing);
        txt_tongcangneijing = view.findViewById(R.id.txt_tongcangneijing);

        txt_tongfengkoushuliang = view.findViewById(R.id.txt_tongfengkoushuliang);
        txt_zhouliufengjishu = view.findViewById(R.id.txt_zhouliufengjishu);
        txt_shejirongliang = view.findViewById(R.id.txt_shejirongliang);

        txt_hedingrongliang = view.findViewById(R.id.txt_hedingrongliang);
        txt_kongqicangmixing = view.findViewById(R.id.txt_kongqicangmixing);
        txt_duiliangxiangao = view.findViewById(R.id.txt_duiliangxiangao);

        txt_cangqianghoudu = view.findViewById(R.id.txt_cangqianghoudu);
        txt_cangneitiji = view.findViewById(R.id.txt_cangneitiji);
        txt_gerecuoshi = view.findViewById(R.id.txt_gerecuoshi);

        txt_baoguanyuan = view.findViewById(R.id.txt_baoguanyuan);

        txt_edit =  view.findViewById(R.id.txt_edit);
        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}