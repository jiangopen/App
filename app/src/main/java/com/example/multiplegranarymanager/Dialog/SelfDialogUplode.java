package com.example.multiplegranarymanager.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.multiplegranarymanager.R;


public class SelfDialogUplode extends Dialog implements
        View.OnClickListener{
    private TextView yes;//确定按钮
    private TextView no;//取消按钮
    private TextView mMessage;
    //确定文本和取消文本的显示内容
    private String yesStr;
    private String noStr;
    private String message;
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private onNoOnclickListener noOnclickListener;//确定按钮被点击了的监听器
    //private String startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_download);
        //按空白处能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        //yes = findViewById(R.id.tv_commit);
        //ivClose = (ImageView) findViewById(R.id.iv_close);
        mMessage = (TextView) findViewById(R.id.tv_msg);
        no = (TextView) findViewById(R.id.tv_btn1);
        yes = (TextView) findViewById(R.id.tv_btn2);

        //findViewById(R.id.iv_close).setOnClickListener(this);
        /*findViewById(R.id.iv_reduce).setOnClickListener(this);
        findViewById(R.id.iv_increase).setOnClickListener(this);*/
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    private void initData() {
        //如果设置按钮的文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        if (noStr != null) {
            no.setText(noStr);
        }
    }

    private void initEvent() {
        mMessage.setText(message);
    }
    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }
    public void Message(String str){
        message = str;
    }

    //弹框在整个界面上的亚子
    public SelfDialogUplode(@NonNull Context context) {
        super(context, R.style.upLodeDialog);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*case R.id.iv_close:

                //mCalendarView.setSelectCalendarRange(2018,10,13,2018,10,13);
                break;*/
            case R.id.tv_btn2:

                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
                break;
            case R.id.tv_btn1:

                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            default:
                break;
        }
    }

    /**
     * 设置确定按钮被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
}
