package com.dfzt.tabmodule.widget.in;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class MyInNestedScrollView extends NestedScrollView {
    private ViewGroup mViewpager;
    private ViewGroup mConstraintLayout;
    private ViewGroup mTablayout;
    private View mHintText;
    private int marginTopSize = 0;

    //记录手指按下时的坐标
    private int pointX,pointY;
    public MyInNestedScrollView(@NonNull Context context) {
        this(context,null);
    }

    public MyInNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyInNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("PPS"," MyInNestedScrollView onInterceptTouchEvent  ACTION_DOWN " + ev.getAction());
        if (ev.getAction() == MotionEvent.ACTION_DOWN){

            pointX = (int) ev.getX();
            pointY = (int) ev.getY();
            return false;
        }else if (ev.getAction() == MotionEvent.ACTION_MOVE){
            int moveX = Math.abs((int) (pointX - ev.getX()));
            int moveY = Math.abs((int) (pointY - ev.getY()));
            super.onInterceptTouchEvent(ev);
            if (moveX > moveY ){
                //左右滑动的时候不拦截
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("PPS"," NestedScrollView  ACTION_DOWN ");
                pointX = (int) ev.getX();
                pointY = (int) ev.getY();
                //不允许父控件拦截我
                //getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:

                int moveX = Math.abs((int) (pointX - ev.getX()));
                int moveY = Math.abs((int) (pointY - ev.getY()));
                Log.e("PPS"," NestedScrollView  ACTION_MOVE  moveX" + moveX + "-----" +moveY);
                //如果 Y轴移动的距离 > X轴移动的距离 这种情况是上下滑动  判断我是否滑动到了顶部
                if (moveX > moveY ){
                    //左右滑动的时候不拦截
                    //getParent().requestDisallowInterceptTouchEvent(false);

                }else if (moveX < moveY) {

                }
                break;
        }
        boolean a = super.dispatchTouchEvent(ev);
        Log.e("PPS",  " ViewPager  return  " + a);
        return a;
    }
}
