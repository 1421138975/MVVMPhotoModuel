package com.dfzt.custommodule.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyFrameLayout extends FrameLayout {
    private Paint mPaint;
    private Path mPath;
    private float mTouchX,mTouchY;
    private float mCurentSize = 0;
    private float mEndSzie = 1;
    public MyFrameLayout(@NonNull Context context) {
        this(context,null);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchX = event.getX();
        mTouchY = event.getY();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,40).setDuration(200);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                mEndSzie = 1;
                mCurentSize = (float) animator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {


            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mEndSzie = 0;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mTouchX,mTouchY,mEndSzie * (20 + mCurentSize),mPaint);
    }
}
