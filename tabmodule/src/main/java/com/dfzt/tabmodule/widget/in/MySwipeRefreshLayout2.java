package com.dfzt.tabmodule.widget.in;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MySwipeRefreshLayout2 extends SwipeRefreshLayout {
    public MySwipeRefreshLayout2(@NonNull Context context) {
        this(context,null);
    }

    public MySwipeRefreshLayout2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //使用内部拦截发拦截事件的分发
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("PPS"," onInterceptTouchEvent " + ev.getAction());
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            super.onInterceptTouchEvent(ev);
            return false;
        }
        return true;
    }
}
