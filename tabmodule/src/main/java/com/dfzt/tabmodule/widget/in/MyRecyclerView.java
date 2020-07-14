package com.dfzt.tabmodule.widget.in;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //在内部的时候 不是拦截  你要分发这个事件
    private int downX,downY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("PPS"," RecyclerView  ACTION_DOWN ");
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                //不让父亲拦截我这个事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = Math.abs((int)(downX - ev.getX()));
                int moveY = Math.abs((int)(downY - ev.getY()));
                Log.e("PPS"," RecyclerView  ACTION_MOVE " + moveX + " ----- " + moveY);
                if (moveX > moveY){
                    //表示左右滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        boolean a = super.dispatchTouchEvent(ev);
        Log.e("PPS",  " RecyclerView  return  " + a);
        return a;
    }
}
