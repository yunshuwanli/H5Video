package com.yswl.priv.h5vedioapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


/**
 * 进度条
 *
 * @author nixn@yunhetong.net
 */
public class WebViewProgressBar extends View {
    public static final String TAG = "WebViewProgressBar";
    public int mScreenW = -1;
    public int mNewDrawWidth = 0;
    public Paint mPaint = new Paint();

    public WebViewProgressBar(Context context) {
        super(context);
        init();
    }

    public WebViewProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WebViewProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // public WebViewProgressBar(Context context, AttributeSet attrs,
    // int defStyleAttr, int defStyleRes) {
    // super(context, attrs, defStyleAttr, defStyleRes);
    // init();
    // }

    public void setProgress(int newProgress) {
        if (newProgress == 100) {
            this.setVisibility(View.GONE);
        } else {
            if (this.getVisibility() != View.VISIBLE) {
                this.setVisibility(View.VISIBLE);
            }
        }
        if (mScreenW == -1) {
            init();
        }
        mNewDrawWidth = newProgress * mScreenW / 100;
        mPaint.setColor(Color.GREEN);
        this.invalidate();
    }

    private void init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenW = outMetrics.widthPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, mNewDrawWidth, 5, mPaint);
    }
}
