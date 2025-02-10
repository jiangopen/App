package com.example.multiplegranarymanager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.multiplegranarymanager.R;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SelfDialog extends Dialog implements CalendarView.OnCalendarInterceptListener, CalendarView.OnCalendarRangeSelectListener, CalendarView.OnMonthChangeListener, View.OnClickListener{
    private TextView mTextLeftDate;
    private TextView mTextLeftWeek;
    private TextView mTextRightDate;
    private TextView mTextRightWeek;
    private TextView yes;
    private TextView mTitle;
    private String yesStr;
    private CalendarView calendarViewef;
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private String startTime,endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        //按空白处取消动画
        setCanceledOnTouchOutside(true);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {
    }

    private void initData() {
        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
        Integer year = Integer.parseInt(strNow[0]);
        Integer month = Integer.parseInt(strNow[1]);
        Integer day = Integer.parseInt(strNow[2]);
        mTitle.setText(year + "年" + month + "月");
        if (yesStr != null) {
            yes.setText(yesStr);
        }
    }
    //初始化界面控件
    private void initView() {
        yes = findViewById(R.id.tv_commit);
        mTextLeftDate = findViewById(R.id.tv_left_date);
        mTextLeftWeek = findViewById(R.id.tv_left_week);
        mTextRightDate = findViewById(R.id.tv_right_date);
        mTextRightWeek = findViewById(R.id.tv_right_week);
        calendarViewef = findViewById(R.id.calendarView);
        mTitle = findViewById(R.id.tv_title);
        calendarViewef.setOnCalendarRangeSelectListener(this);
        calendarViewef.setOnMonthChangeListener(this);
        calendarViewef.setOnCalendarRangeSelectListener(this);
        findViewById(R.id.iv_clear).setOnClickListener(this);
        yes.setOnClickListener(this);
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

    public SelfDialog(@NonNull Context context) {
        super(context,R.style.testDialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                calendarViewef.clearSelectRange();
                mTextLeftWeek.setText("开始日期");
                mTextRightWeek.setText("结束日期");
                mTextLeftDate.setText("");
                mTextRightDate.setText("");
                //mCalendarView.setSelectCalendarRange(2018,10,13,2018,10,13);
                break;
            case R.id.tv_commit:
                List<Calendar> calendars = calendarViewef.getSelectCalendarRange();//选中的区间信息
                if (calendars == null || calendars.size() == 0) {
                    return;
                }
                for (Calendar c : calendars) {//c.toString()年月日 c.getScheme() 自定义事件  c.getLunar()农历
                    Log.e("SelectCalendarRange", c.toString()
                            + " -- " + c.getScheme()
                            + "  --  " + c.getLunar());
                }
//                Toast.makeText(getContext(), String.format("选择了%s个日期: %s —— %s", calendars.size(),
//                        calendars.get(0).toString(), calendars.get(calendars.size() - 1).toString()),
//                        Toast.LENGTH_SHORT).show();
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        return calendar.hasScheme();
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
        Toast.makeText(getContext(),calendar.toString() + (isClick ? "拦截不可点击" : "拦截设定为无效日期"),
                Toast.LENGTH_SHORT).show();
    }
    //添加不能选择的事件日期
    @Override
    public void onCalendarSelectOutOfRange(Calendar calendar) {

    }

    @Override
    public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {
        Toast.makeText(getContext(),calendar.toString() + (isOutOfMinRange ? "小于最小选择范围":"超过最大选择范围"),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {
        String hourStart = " 00:00:00";
        String hourEnd = " 23:59:59";
        if (!isEnd) {
            mTextLeftDate.setText(calendar.getYear() + "年" + calendar.getMonth() + "月" + calendar.getDay() + "日");
            mTextLeftWeek.setText(WEEK[calendar.getWeek()]);
            mTextRightWeek.setText("结束日期");
            mTextRightDate.setText("");
            startTime = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay() + hourStart;//"yyyy-MM-dd HH:mm:ss"
        } else {
            mTextRightDate.setText(calendar.getYear() + "年" + calendar.getMonth() + "月" + calendar.getDay() + "日");
            mTextRightWeek.setText(WEEK[calendar.getWeek()]);
            endTime = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay() + hourEnd;
        }
    }
    private static final String[] WEEK = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    @Override
    public void onMonthChange(int year, int month) {
        Log.e("onMonthChange", "  -- " + year + "  --  " + month);
        mTitle.setText(year + "年" + month + "月");
    }
    //设置确定按钮被点击的接口
    public interface onYesOnclickListener {
        public void onYesClick();
    }
    /**
     * 设置开启与结束时间的接口
     */
    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
