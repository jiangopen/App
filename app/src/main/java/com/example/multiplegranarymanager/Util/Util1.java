package com.example.multiplegranarymanager.Util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

public class Util1 {
    private static Toast toast;

    /**
     * 这样就只会创建一次Toast对象,多次点击就不会出现多次显示了,因为只有一个toast对象
     */
    public static void showToast(Context context, String sr) {
        if (toast == null) {
            toast = Toast.makeText(context, sr, Toast.LENGTH_SHORT);
        } else {
            toast.setText(sr);//当第二次点击的时候就只会设置文字
        }
        toast.show();
    }

    //解决水平滑动布局和scrollview的滑动冲突
    public static void solveScrollConflict(View view, final ScrollView scrollView) {
        if (view != null) {
            view.setOnTouchListener(new View.OnTouchListener() {
                float ratio = 1.2f; //水平和竖直方向滑动的灵敏度,偏大是水平方向灵敏
                float x0 = 0f;
                float y0 = 0f;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            x0 = event.getX();
                            y0 = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float dx = Math.abs(event.getX() - x0);
                            float dy = Math.abs(event.getY() - y0);
                            x0 = event.getX();
                            y0 = event.getY();
                            scrollView.requestDisallowInterceptTouchEvent(dx * ratio > dy);
                            break;
                    }
                    return false;
                }
            });
        }
    }
}
