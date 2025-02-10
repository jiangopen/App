package com.example.multiplegranarymanager.A_ShuChengPackage.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplegranarymanager.A_ShuChengPackage.Adapter.CardGasAdapter;
import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.BatchDevice.DeviceData;
import com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Adapter.RecyclerView.HeaderViewAdapter;
import com.example.multiplegranarymanager.R;

public class GasInfosData extends Dialog {
    private Context mContext;
    private DeviceData mdata;
    TextView Name,no;
    ImageView Cha;
    RecyclerView listView;
    private TextView mFootView;
    private CardGasAdapter adapter_gas;
    private HeaderViewAdapter headerViewAdapter;
    public static String moduleName;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_device);
        //按空白处能取消动画
        setCanceledOnTouchOutside(true);
        //初始化界面控件
        initView();
//        //控件的填值
//        initPut();
//        //判断控件的显示
//        initShow();
        Name.setText(moduleName+"气体信息");
        if (mdata!=null) {
            no.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            refreshedView(mContext);
        } else {
            no.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        Cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void refreshedView(Context context) {
        GridLayoutManager layoutManager = new GridLayoutManager(context,1);
        listView.setLayoutManager(layoutManager);
//        Log.d("jhtzyq", "refreshedView: "+mdata.getCo2().size()+"\n"+mdata.getN2().size());
        adapter_gas = new CardGasAdapter(context,mdata.getCo2(),mdata.getN2());
        headerViewAdapter = new HeaderViewAdapter(adapter_gas);
        listView.setAdapter(headerViewAdapter);
    }

    private void initView() {
        Name = findViewById(R.id.name);
        no = findViewById(R.id.no);
        Cha = findViewById(R.id.iv_cha);
        listView = findViewById(R.id.member);
    }

    public GasInfosData(@NonNull Context context, DeviceData data, String ModuleName) {
        super(context, R.style.deviceDetailDialog);
        this.mContext = context;
        this.mdata = data;
        this.moduleName = ModuleName;
    }
}
