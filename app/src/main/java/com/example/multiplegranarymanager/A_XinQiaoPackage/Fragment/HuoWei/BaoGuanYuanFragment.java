package com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.HuoWei;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.multiplegranarymanager.R;

public class BaoGuanYuanFragment extends Fragment {
    private TextView txt_edit;
    private EditText
            txt_name,
            txt_gender,
            txt_from,
            txt_birth,
            txt_dang,
            txt_date,
            txt_xueli,
            txt_school,
            txt_zhuanye,
            txt_zhiyezige,
            txt_bianhao,
            txt_baoguanjibie;
    private String url,commandMapId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bao_guan_yuan, container, false);

        initView(view);

        Bundle bundle = getArguments();
        assert bundle != null;
        url = bundle.getString("url");
        commandMapId = bundle.getString("commandMapId");

        initDrawBaoGuanYuan(new OnInitDrawBaoGuanYuanFinishedListener() {
            @Override
            public void OnInitDrawBaoGuanYuanListener(boolean success) {

            }
        });

        return view;
    }

    public interface OnInitDrawBaoGuanYuanFinishedListener {
        void OnInitDrawBaoGuanYuanListener(boolean success);
    }

    private void initDrawBaoGuanYuan(final OnInitDrawBaoGuanYuanFinishedListener listener) {

    }

    private void initView(View view) {
        txt_name = view.findViewById(R.id.txt_name);
        txt_gender = view.findViewById(R.id.txt_gender);
        txt_from = view.findViewById(R.id.txt_from);

        txt_birth = view.findViewById(R.id.txt_birth);
        txt_dang = view.findViewById(R.id.txt_dang);
        txt_date = view.findViewById(R.id.txt_date);

        txt_xueli = view.findViewById(R.id.txt_xueli);
        txt_school = view.findViewById(R.id.txt_school);
        txt_zhuanye = view.findViewById(R.id.txt_zhuanye);

        txt_zhiyezige = view.findViewById(R.id.txt_zhiyezige);
        txt_bianhao = view.findViewById(R.id.txt_bianhao);
        txt_baoguanjibie = view.findViewById(R.id.txt_baoguanjibie);

        txt_edit =  view.findViewById(R.id.txt_edit);
        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}