package com.example.multiplegranarymanager.Style;

import static android.graphics.Color.BLACK;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.blanke.lib.MaterialProgressDrawable;
import com.blanke.lib.ProgressButton;
import com.example.multiplegranarymanager.R;
import com.github.ybq.android.spinkit.style.Wave;

public class ProBtn extends ProgressButton {
    private MaterialProgressDrawable mProgressDrawable;
    private int progressPadding;
    private boolean isLoading;
    private CharSequence text;
    private int h;
    private int v;
    private Wave mWaveDrawable = new Wave();//我自己修改新的样式
    public ProBtn(Context context) {
        this(context, (AttributeSet) null);
        this.init();
    }

    public ProBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.progressPadding = 0;
        this.isLoading = false;
        this.init();
    }

    public ProBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.progressPadding = 0;
        this.isLoading = false;
        this.init();
    }

    private void init() {
        mWaveDrawable.setColor(getResources().getColor(R.color.color2));
        this.mProgressDrawable = new MaterialProgressDrawable(this.getContext(), this);
        this.mProgressDrawable.setColorSchemeColors(BLACK);//new int[]{-16776961}
        this.mProgressDrawable.setAlpha(255);
        this.mProgressDrawable.setBackgroundColor(BLACK);
        this.post(new Runnable() {
            public void run() {
                ProBtn.this.initValue();
            }
        });
    }

    private void initValue() {
        this.h = this.getRight() - this.getLeft();
        this.v = this.getBottom() - this.getTop();
        int l = (this.h - this.v) / 2;
        int r = l + this.v;
        int t = 0;
        int b = this.v;
        if (this.v > this.h) {
            l = 0;
            r = this.h;
            t = (this.v - this.h) / 2;
            b = t + this.h;
            t -= this.getPaddingTop();
            b -= this.getPaddingTop();
        } else {
            l -= this.getPaddingLeft();
            r -= this.getPaddingLeft();
        }

        l += this.progressPadding;
        r -= this.progressPadding;
        t += this.progressPadding;
        b -= this.progressPadding;
        this.mWaveDrawable.setBounds(l, t, r, b);
    }

    private void setProgressDrawable() {
        if (this.v > this.h) {
            this.setCompoundDrawables((Drawable) null, this.mWaveDrawable, (Drawable) null, (Drawable) null);
        } else {
            this.setCompoundDrawables(this.mWaveDrawable, (Drawable) null, (Drawable) null, (Drawable) null);
        }

    }

    private void setProgressLoading(boolean loading) {
        if (loading) {
            this.setProgressDrawable();
            this.mWaveDrawable.start();
        } else {
            this.mWaveDrawable.stop();
            this.setCompoundDrawables((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        }

    }

    public void setLoading(boolean isLoading) {
        if (this.isLoading != isLoading) {
            this.isLoading = isLoading;
            this.setProgressLoading(isLoading);
            this.setClickable(!isLoading);
            if (isLoading) {
                this.text = this.getText();
                this.setText("");
            } else {
                this.setText(this.text);
            }
        }

    }

    public boolean performClick() {
        boolean re = super.performClick();
        if (re) {
            this.setLoading(true);
        }

        return re;
    }

    public MaterialProgressDrawable getProgressDrawable() {
        return this.mProgressDrawable;
    }

    public int getProgressPadding() {
        return this.progressPadding;
    }

    public void setProgressPadding(int progressPadding) {
        this.progressPadding = progressPadding;
    }

    public boolean getLoading() {
        return this.isLoading;
    }
}

