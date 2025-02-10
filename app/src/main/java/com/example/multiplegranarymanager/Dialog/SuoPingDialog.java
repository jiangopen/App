package com.example.multiplegranarymanager.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.multiplegranarymanager.R;


public class SuoPingDialog extends Dialog {
    TextView tvSuoPingText;
    private Context mContext;
    private String setText;
    public SuoPingDialog(@NonNull Context context, String text) {
        super(context, R.style.upLodeDialog);
        mContext = context;
        setText = text;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_suo_ping);
        //按空白处能取消锁屏
        setCancelable(false);
        tvSuoPingText = findViewById(R.id.tv_suoping_text);
        tvSuoPingText.setText(setText);
    }
}
