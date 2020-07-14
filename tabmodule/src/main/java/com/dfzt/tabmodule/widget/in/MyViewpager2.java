package com.dfzt.tabmodule.widget.in;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

public class MyViewpager2 extends ViewPager {
    public MyViewpager2(@NonNull Context context) {
        this(context,null);
    }

    public MyViewpager2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //在内部的时候 不是拦截  你要分发这个事件
    private int downX,downY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("PPS"," ViewPager  ACTION_DOWN ");
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                //不让父亲拦截我这个事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = Math.abs((int)(downX - ev.getX()));
                int moveY = Math.abs((int)(downY - ev.getY()));
                Log.e("PPS"," ViewPager  ACTION_MOVE " + moveX + " ----- " + moveY);
                if (moveX < moveY){
                    //表示上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        boolean a = super.dispatchTouchEvent(ev);
        Log.e("PPS",  " ViewPager  return  " + a);
        return a;
    }
}
