package com.dfzt.tabmodule.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.widget.NestedScrollView;

public class MyNestedScrollView extends NestedScrollView {
    private ViewGroup mViewpager;
    private ViewGroup mConstraintLayout;
    private ViewGroup mTablayout;
    private View mHintText;
    private int marginTopSize = 0;
    public MyNestedScrollView(@NonNull Context context) {
        this(context,null);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mConstraintLayout = (ViewGroup) getChildAt(0);
        //获取的是Viewpager
        mViewpager = (ViewGroup) mConstraintLayout.getChildAt(2);
        mTablayout = (ViewGroup) mConstraintLayout.getChildAt(1);
        mHintText = mConstraintLayout.getChildAt(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        marginTopSize = 0;
        MarginLayoutParams compat = (MarginLayoutParams) mTablayout.getLayoutParams();
        marginTopSize += compat.topMargin;
        MarginLayoutParams mHintTextLp = (MarginLayoutParams) mHintText.getLayoutParams();
        marginTopSize += mHintTextLp.topMargin;
        //测量这个view的大小
        measureChild(mViewpager,widthMeasureSpec,heightMeasureSpec);
        //获取tablayout
        measureChild(mTablayout,widthMeasureSpec,heightMeasureSpec);
        //获取Viewpager的参数
        ViewGroup.LayoutParams lp = mViewpager.getLayoutParams();
        lp.height = getMeasuredHeight() - mTablayout.getMeasuredHeight();
        mViewpager.setLayoutParams(lp);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //super.onNestedPreScroll(target, dx, dy, consumed, type);
        //这里先让自己滑动
        boolean needScroll = dy > 0 && getScrollY() < mHintText.getMeasuredHeight() + marginTopSize;
        if (needScroll) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

}
