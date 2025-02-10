package com.example.multiplegranarymanager.A_ShuChengPackage.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.multiplegranarymanager.A_ShuChengPackage.Bean.UserAlertInfos.AlertInfo;
import com.example.multiplegranarymanager.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmDetailActivity extends AppCompatActivity {
    TextView titleText;
    TextView tvSubject;
    TextView level;
    TextView time;
    TextView Content;
    private String position;
    private AlertInfo AlertInfo;//预警信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);
        titleText = findViewById(R.id.title_text);
        tvSubject = findViewById(R.id.tv_subject);
        level = findViewById(R.id.level);
        time = findViewById(R.id.time);
        Content = findViewById(R.id.Content);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
        Intent intent = getIntent();
        try {
            position = intent.getStringExtra("position");
            AlertInfo = (AlertInfo) intent.getSerializableExtra("oneAlertInfo");
            assert AlertInfo!=null;
        } catch (Exception e){
            e.printStackTrace();
        }
        tvSubject.setText(AlertInfo.getSubject());
        level.setText(AlertInfo.getLevel());
        time.setText(TimeStamp2Date(String.valueOf(AlertInfo.getTime()),"yyyy-MM-dd HH:mm:ss"));
        Content.setText("\u3000\u3000"+AlertInfo.getContent());
    }
    public static String TimeStamp2Date(String timestampString, String formats){
        if (TextUtils.isEmpty(formats)){
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = Long.parseLong(timestampString)*1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }
}