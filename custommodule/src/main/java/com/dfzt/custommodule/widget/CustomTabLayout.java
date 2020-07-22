package com.dfzt.custommodule.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import com.dfzt.custommodule.R;

import androidx.annotation.Nullable;
import androidx.core.view.ViewConfigurationCompat;
import androidx.databinding.DataBindingUtil;
import util.DensityUtils;

/**
 * 这个只是一个TabLayout 就是说这里我只负责你的显示和滑动 至于 字体颜色 大小 都是子View去做的
 */

public class CustomTabLayout extends ViewGroup {

    //默认文字的颜色
    private int mTextColor = 0;

    //选中文字的颜色
    private int mSelectColor = 0;


    //定义一个 每个Item的最小宽度 和 最小 高度
    private int mMinWidth;

    private int mMinHeight;

    //设置排列的模式
    private boolean mTypeModel = false;

    //定义一个参数  保存测量的最大的高度 之后 布局的时候以这个高度为基准
    private int mMaxHeight = 0;
    //定义一个参数 用来保存最大的长度
    private int mMaxWidth = 0;

    //定义一个滑动的最小距离
    private int mMinScrollSize = 0;

    //是否需要滑动
    private boolean needScroll = false;

    private float mLastTouchX,mLastTouchY;//记录上一次手指按下的时候

    private OverScroller mScroller;//View 滑动的一个工具类

    private int dx;//滑动的偏移量

    //获取父控件的长度度
    private int mParentWidth = 0;
    //获取自身测量的长度
    private int mRealWidth = 0;

    private VelocityTracker mVelocityTracker;//滑动的惯性

    private int mMaximumVelocity,mMinimumVelocity;
    public CustomTabLayout(Context context) {
        this(context,null);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context,attrs);
    }

    private void initData(Context context, AttributeSet attrs) {
       /* TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomTabLayout);
        mTypeModel = array.getBoolean(R.styleable.CustomTabLayout_model,false);
        array.recycle();*/
        mMinWidth = DensityUtils.dip2px(context,70);
        mMinHeight = DensityUtils.dip2px(context,45);
        //获取最小的滑动距离
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMinScrollSize = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mScroller = new OverScroller(context);
    }

    private void initVelocityTracker(){
        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    //先测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMaxWidth = 0;
        //获取模式 => 32位的int类型的高2为  widthMeasureSpec => 32位的int类型吗
        int mWidthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int mHeightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);

        //获取长和宽
        int mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int mHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        //获取子View的数量
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View mChild = getChildAt(i);
            //测量子View
            measureChild(mChild,widthMeasureSpec,heightMeasureSpec);
            //获取测量到后的子view的长度
            int viewMeasuredWidth = mChild.getMeasuredWidth();
            //获取测量到后的子view的宽度
            int viewMeasuredHeight = mChild.getMeasuredHeight();
            LayoutParams mChildLp = mChild.getLayoutParams();
            //保存测量后 最大的高度
            if (mHeightMeasureMode == MeasureSpec.EXACTLY){
                //这个表示精确的高度
                mMaxHeight = mHeightSize;
            }else {
                mMaxHeight = Math.max(mMinHeight,viewMeasuredHeight);
            }

            mChildLp.height = mMaxHeight;
            mChildLp.width = Math.max(mMinWidth,viewMeasuredWidth);

            //累加长度
            mMaxWidth += Math.max(mMinWidth,viewMeasuredWidth);

        }

        if (mMaxWidth > mWidthSize){
            //表示需要滑动
            needScroll = true;
        }
        mRealWidth = mMaxWidth;//获取自身的长度
        mParentWidth = mWidthSize;//获取父控件的长度
        setMeasuredDimension(mMaxWidth,mMaxHeight);

    }

    //再布局
    @Override
    protected void onLayout(boolean b, int l, int t, int r, int bo) {
        Log.e("PPS"," OnLayout ");
       /* int childCount = getChildCount();
        int width = getMeasuredWidth();
        for (int i = 0; i < childCount; i++) {
            View mChild = getChildAt(i);
            mChild.layout( i * mMinWidth ,0,(i + 1) * mMinWidth ,getMeasuredHeight());
            int finalI = i;

        }*/

        int childCount = getChildCount();
        int parentHeight = getMeasuredHeight();
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = parentHeight;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int viewWidth = childView.getMeasuredWidth() ;
            Log.e("PPS",mParentWidth+"  viewWidth = " + viewWidth);
            left = i * viewWidth ;
            right = left + viewWidth;
            childView.layout(left,top,right,bottom);
            int finalI = i;
            /*childView.setOnClickListener(view -> {
                scrolltoFun(finalI * viewWidth);
            });*/
        }
    }



    //拦截 我需要在水平滑动的时候去拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isScrolled = false;
        float curX = ev.getX();
        float curY = ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                isScrolled = false;
                mLastTouchY = curX;
                mLastTouchX = curY;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = mLastTouchX - curX;
                float moveY = mLastTouchY - curY;
                if (Math.abs(moveX) > Math.abs(moveY) && Math.abs(moveX) > mMinScrollSize){
                    //表示水平滑动 拦截这个事件  自己去消费
                    isScrolled = true;
                }else {
                    isScrolled = false;
                }
                break;
        }
        return isScrolled;
    }

    private float mLastX;//滑动的
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!needScroll){
            //表示不需要滑动
            return super.onTouchEvent(event);
        }
        //获取当前手指所在的位置
        initVelocityTracker();
        mVelocityTracker.addMovement(event);
        float mCurrentX = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = mCurrentX;
                break;
            case MotionEvent.ACTION_MOVE:
                //判断边界
                dx = (int) (mLastX - mCurrentX);//记录当前滑动了多少距离

                //开始滑动 //直接调用 scrollTo
                //scrolltoFun(dx);

                //采用OverScroller来实现
                mScroller.startScroll(mScroller.getFinalX(),0,dx,0);
                invalidate();
                mLastX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int initialVelocity = (int) velocityTracker.getXVelocity();

                if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
                    fling(-initialVelocity);
                } else if (mScroller.springBack(getScrollX(), getScrollY(), 0,  (mRealWidth - mParentWidth), 0,
                       0)) {
                    postInvalidateOnAnimation();
                }
                break;
        }
        return true;
    }

    public void fling(int velocityX) {
        if (getChildCount() > 0) {
            int width = mParentWidth;
            int right = mRealWidth;

            mScroller.fling(getScrollX(), getScrollY(), velocityX, 0, 0, Math.max(0, right - width), 0,
                    0, width / 2, 0);

            postInvalidateOnAnimation();
        }
    }

    private void scrolltoFun(int dx) {
        //getScrollX() //获取上一次滑动的X的大小
        int scrollX = getScrollX() + dx; //本次需要的偏移量

        if (scrollX > mRealWidth - mParentWidth){
            scrollX = mRealWidth - mParentWidth;
        }
        if (scrollX < 0){
            //不滑动
            scrollX = 0;
        }
        scrollTo(scrollX,0);
    }

    /**
     * 使用Scroller的时候就要重写这个方法
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            // true 表示当前还在滑动的过程  false 表示已经滑动完成了
            int currX = mScroller.getCurrX();

            if (currX > mRealWidth - mParentWidth){
                currX = mRealWidth - mParentWidth;
            }
            if (currX < 0){
                //不滑动
                currX = 0;
            }
            scrollTo(currX,0);
            postInvalidate();
        }
    }


}
