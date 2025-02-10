package com.example.multiplegranarymanager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class MyWebView extends WebView {
    private boolean isLandscape = false;
    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
//    public void setLandscapeMode(boolean landscape) {
//        isLandscape = landscape;
//        requestLayout();
//    }
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (isLandscape) {
//            canvas.translate(getHeight(), 0);//getHeight()
//            canvas.rotate(90);
//            canvas.scale(1, 1);
//        }
//        super.onDraw(canvas);
//    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int parentWidth = MeasureSpec.getSize(heightMeasureSpec);
//        int parentHeight = MeasureSpec.getSize(widthMeasureSpec);
//
//        setMeasuredDimension(parentWidth, parentHeight);



//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = width * 9 / 16; //设置高度为宽度的16/9
//
//        setMeasuredDimension(width, height);


        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏时，将高度设置为宽度的一半
            height = width / 3;
        } else {
            // 竖屏时，将高度设置为宽度的16/9
            height = width * 9 / 16;
        }

        setMeasuredDimension(width, height);


//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            // 如果是横屏，交换宽高参数，以确保 WebView 可以垂直滚动
//            super.onMeasure(heightMeasureSpec, widthMeasureSpec);
//        } else {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏时，禁止左右滑动，只允许上下滑动
            getParent().requestDisallowInterceptTouchEvent(true);
        } else {
            // 竖屏时，使用默认的触摸事件处理
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.onTouchEvent(event);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }
}
