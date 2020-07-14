package com.dfzt.tabmodule.widget.out;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewpager extends ViewPager {
    public MyViewpager(@NonNull Context context) {
        this(context,null);
    }

    public MyViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
