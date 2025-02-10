package com.example.multiplegranarymanager.A_XinQiaoPackage.Fragment.HuoWei;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.multiplegranarymanager.R;

public class InfoFragment extends Fragment {
    private TextView txt_edit;
    private EditText
            txt_type,
            txt_liangyouxingzhi,
            txt_liangquanguishu,
            txt_cangxing,
            txt_shejirongliang,
            txt_rukushijian,
            txt_chenghuoweishijian,
            txt_shouhuoniandu,
            txt_shijishuliang,
            txt_guanlifangshi,
            txt_chandi,
            txt_jiage,
            txt_cunchufangshi,
            txt_baozhuangcunliangbaoshu,
            txt_sancunliangyoutiji,
            txt_chang,
            txt_kuan,
            txt_zhijing,
            txt_gao,
            txt_pingjunbaozhong,
            txt_dengji,
            txt_rongliangchucao,
            txt_rucangshuifen,
            txt_rucangzazhi,
            txt_buwanshanliguwaicaomi,
            txt_huanglimi,
            txt_zhengjingmili,
            txt_yingduzhishu,
            txt_tyxfldjxc,
            txt_huhun,
            txt_ymcmxt,
            txt_sezeqiwei;
    private String url,commandMapId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        initView(view);

        Bundle bundle = getArguments();
        assert bundle != null;
        url = bundle.getString("url");
        commandMapId = bundle.getString("commandMapId");

        initDrawInfo(new OnInitDrawInfoFinishedListener() {
            @Override
            public void OnInitDrawInfoListener(boolean success) {

            }
        });

        return view;
    }
    public interface OnInitDrawInfoFinishedListener{
        void OnInitDrawInfoListener(boolean success);
    }
    private void initDrawInfo(final OnInitDrawInfoFinishedListener listener) {

    }
    private void initView(View view) {
        txt_type = view.findViewById(R.id.txt_type);
        txt_liangyouxingzhi  = view.findViewById(R.id.txt_liangyouxingzhi);
        txt_liangquanguishu = view.findViewById(R.id.txt_liangquanguishu);

        txt_cangxing = view.findViewById(R.id.txt_cangxing);
        txt_shejirongliang = view.findViewById(R.id.txt_shejirongliang);
        txt_rukushijian = view.findViewById(R.id.txt_rukushijian);

        txt_chenghuoweishijian = view.findViewById(R.id.txt_chenghuoweishijian);
        txt_shouhuoniandu = view.findViewById(R.id.txt_shouhuoniandu);
        txt_shijishuliang = view.findViewById(R.id.txt_shijishuliang);

        txt_guanlifangshi = view.findViewById(R.id.txt_guanlifangshi);
        txt_chandi = view.findViewById(R.id.txt_chandi);
        txt_jiage = view.findViewById(R.id.txt_jiage);

        txt_cunchufangshi = view.findViewById(R.id.txt_cunchufangshi);
        txt_baozhuangcunliangbaoshu = view.findViewById(R.id.txt_baozhuangcunliangbaoshu);
        txt_sancunliangyoutiji = view.findViewById(R.id.txt_sancunliangyoutiji);

        txt_chang = view.findViewById(R.id.txt_chang);
        txt_kuan = view.findViewById(R.id.txt_kuan);
        txt_zhijing = view.findViewById(R.id.txt_zhijing);

        txt_gao = view.findViewById(R.id.txt_gao);
        txt_pingjunbaozhong = view.findViewById(R.id.txt_pingjunbaozhong);
        txt_dengji = view.findViewById(R.id.txt_dengji);

        txt_rongliangchucao = view.findViewById(R.id.txt_rongliangchucao);
        txt_rucangshuifen = view.findViewById(R.id.txt_rucangshuifen);
        txt_rucangzazhi = view.findViewById(R.id.txt_rucangzazhi);

        txt_buwanshanliguwaicaomi = view.findViewById(R.id.txt_buwanshanliguwaicaomi);
        txt_huanglimi = view.findViewById(R.id.txt_huanglimi);
        txt_zhengjingmili = view.findViewById(R.id.txt_zhengjingmili);

        txt_yingduzhishu = view.findViewById(R.id.txt_yingduzhishu);
        txt_tyxfldjxc = view.findViewById(R.id.txt_tyxfldjxc);
        txt_huhun = view.findViewById(R.id.txt_huhun);

        txt_ymcmxt = view.findViewById(R.id.txt_ymcmxt);
        txt_sezeqiwei = view.findViewById(R.id.txt_sezeqiwei);

        txt_edit =  view.findViewById(R.id.txt_edit);
        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}