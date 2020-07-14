package com.dfzt.tabmodule.widget.in;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

public class MyConstraintLayout extends ConstraintLayout {
    public MyConstraintLayout(Context context) {
        this(context,null);
    }

    public MyConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        //应为SwipeRefreshLayout中这个方法做了判断
        ViewCompat.setNestedScrollingEnabled(this,true);//这个方法就是让SwipeRefreshLayout要执行super.requestDisallowInterceptTouchEvent(disallowIntercept);
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
}
