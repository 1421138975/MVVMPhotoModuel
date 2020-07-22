package com.dfzt.tabmodule.widget.out;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    public MySwipeRefreshLayout(@NonNull Context context) {
        this(context,null);
    }

    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int downX,downY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) (downX - ev.getX()); //判断X移动的距离
                int moveY = (int) (downY - ev.getY()); //判断Y移动的距离
                if (Math.abs(moveX) > Math.abs(moveY)){
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e("PPS"," ACTION_UP ");
                setEnabled(true);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
