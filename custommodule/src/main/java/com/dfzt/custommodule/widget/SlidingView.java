package com.dfzt.custommodule.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dfzt.custommodule.R;

import java.util.Random;

import androidx.annotation.Nullable;
import util.DensityUtils;

public class SlidingView extends View {
    //定义画笔
    private Paint mPaint;
    //定义路径
    private Path mPath;
    //获取整个控件的宽度和高度
    private int mWidth,mHeight;
    //定义下面拖动的高度
    private int mDragHeight;
    //定义图片和下面拖动的距离
    private int mDragMarginTop;
    //定义图片的高度
    private int mTopBitmapHeight;
    private Context mContext;

    //上方的图片
    private Bitmap mTopBitmap;
    //随机裁剪一个位置
    private Random mRandom;
    //裁剪区域的位置宽高
    private int mClipWidth,mClipHeight;
    //裁剪区域的左右上下的坐标
    private int mClipLeft,mClipTop,mClipRight,mClipBottom;
    private int mRandomWidth;
    private float mTouchX,mTouchY;//手指按下时的坐标
    //下面滑动的文字提示
    private String mHintText = "滑动滑块进行验证";
    //滑块的长度和高度
    private int mDragRectWidth,mDragRectHeight;
    //滑块左上右下的坐标
    private int mRectLeft,mRectTop,mRectRight,mRectBottom;
    //判断是否手指是否按在滑块上面
    private boolean isTouched = false;
    //X滑动的距离
    private float mMoveXSize = 0;

    //图片的id
    private int mBitmapRes;
    public SlidingView(Context context) {
        this(context,null);
    }

    public SlidingView(Context context, @Nullable AttributeSet attrs) {
       this(context, attrs,0);
    }

    public SlidingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context);
    }

    //做一些列的初始化操作
    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);// 对位图进行滤波处理

        mDragMarginTop = DensityUtils.dip2px(context,10);

        mRandom = new Random();
        //随机选取4张图片
        int index = mRandom.nextInt(4);
        if (index == 0){
            mBitmapRes = R.mipmap.sild_back;
        }else if (index == 1){
            mBitmapRes = R.mipmap.sild_back2;
        }else if (index == 2){
            mBitmapRes = R.mipmap.sild_back3;
        }else if (index == 3){
            mBitmapRes = R.mipmap.sild_back4;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);// 对位图进行滤波处理
        //绘制头部图片
        canvas.drawBitmap(mTopBitmap,0 + getPaddingLeft(),0 + getPaddingTop(),mPaint);

        //在图片中裁剪出一个空白的部分
        clipEmptyRect(canvas);

        //在裁剪出一个图片出来 然后移动到开始的位置
        clipBitmapRect(canvas);

        //绘制底部的矩形和文字提示
        drawBottomRect(canvas);

        //绘制滑块
        drawDragRect(canvas);
    }

    private void drawDragRect(Canvas canvas) {
        mRectLeft = (int) mMoveXSize;
        mRectRight = mRectLeft + mDragRectWidth;
        mRectTop = mHeight - (mDragHeight - mDragRectHeight) / 2 - mDragRectHeight;
        mRectBottom = mHeight - (mDragHeight - mDragRectHeight) / 2;
        mPaint.setColor(getResources().getColor(R.color.drag_rect_color));
        canvas.drawRect(mRectLeft,mRectTop,mRectRight,mRectBottom,mPaint);
    }

    private void drawBottomRect(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        canvas.drawRect(0 + getPaddingLeft(),mTopBitmapHeight + getPaddingTop() + mDragMarginTop,
                mWidth,mHeight,mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(DensityUtils.px2sp(mContext,80));

        //获取文字的宽度
        float textWidth = mPaint.measureText(mHintText);
        //获取文字的高度
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        //文字的高度       基准线往下             基准线往上
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        // -  fontMetrics.descent - fontMetrics.ascent
        canvas.drawText(mHintText,(mWidth - textWidth) / 2 ,(mHeight - mDragHeight / 2  + textHeight / 2 - fontMetrics.descent) ,mPaint);
    }

    private void clipEmptyRect(Canvas canvas) {
        canvas.save();
        canvas.clipRect(mClipLeft,mClipTop,mClipRight,mClipBottom);
        canvas.drawColor(getResources().getColor(R.color.white));
        canvas.restore();
    }

    private void clipBitmapRect(Canvas canvas) {
        canvas.save();
        mPaint.setColor(Color.GRAY);
        canvas.translate( -mClipLeft + mMoveXSize,0);
        canvas.clipRect(mClipLeft,mClipTop,mClipRight,mClipBottom);
        canvas.drawBitmap(mTopBitmap,0 + getPaddingLeft(),0 + getPaddingTop(),mPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //当前控件的实际宽度
        mWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        //当前空间的实际高度
        //mHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        if (mHeight == 0){
            mHeight = DensityUtils.dip2px(mContext,300);
        }
        //获取下面拖动的位置
        mDragHeight = DensityUtils.dip2px(mContext,50);
        //获取上面图片的高度
        mTopBitmapHeight = mHeight - mDragHeight - mDragMarginTop;

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mBitmapRes);
        mTopBitmap = Bitmap.createScaledBitmap(bitmap,mWidth,mTopBitmapHeight,true);

        if (mTopBitmap != bitmap){
            bitmap.recycle();
        }

        mClipWidth = DensityUtils.dip2px(mContext,50);
        mClipHeight = DensityUtils.dip2px(mContext,40);

        //随机产生一个数据 然后更具这个数据去设定裁剪区域的位置
        int mRandomWidth = mRandom.nextInt(mWidth / 2) + mWidth / 2;
        int mRandomHeight = mRandom.nextInt(300);
        //定义裁剪区域的位置

        if ((mRandomHeight + mClipHeight) > mTopBitmapHeight){
            //表示往下走 超出了界限
            mClipBottom = mRandomHeight;
            mClipTop = mRandomHeight - mClipHeight;
        }else {
            mClipBottom = mRandomHeight + mClipHeight;
            mClipTop = mRandomHeight;
        }

        if (mRandomWidth + mClipWidth > mWidth){
            //表示超过了空间的宽度
            mClipRight = mRandomWidth;
            mClipLeft = mRandomWidth - mClipWidth;
        }else {
            mClipRight  = mRandomWidth + mClipWidth;
            mClipLeft = mRandomWidth;
        }
        mDragRectWidth = mClipWidth;
        mDragRectHeight = (int) (mDragHeight * 0.7);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float mMoveX,mMoveY;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //手指按下的时候
                mTouchX = event.getX();
                mTouchY = event.getY();
                Log.e("PPS"," mTouchX " + mTouchX + "  :: " + mTouchY);
                Log.e("PPS"," mRectLeft " + mRectLeft + "  :: " + mRectRight);
                Log.e("PPS"," mRectLeft " + (mRectLeft <= mTouchX) + "  :: " + (mTouchX <= mRectRight));
                Log.e("PPS"," mRectTop " + mRectTop + "  :: " + mRectBottom);
                Log.e("PPS"," mRectTop " + (mRectTop <= mTouchY) + "  :: " + (mTouchY <= mRectBottom));
                if(mRectLeft <= mTouchX && mTouchX <= mRectRight && mRectTop <= mTouchY && mTouchY <= mRectBottom ){
                    isTouched = true;
                }else {
                    isTouched = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //手指移动的时候
                Log.e("PPS"," isTouched " + isTouched);
                if (isTouched){

                    mMoveX = event.getX();
                    mMoveY = event.getY();

                   // if(mRectTop <= mMoveY && mMoveY <= mRectBottom ){
                        //表示移动的距离
                        mMoveXSize = mMoveX - mTouchX;
                        float mMoveYSize = mMoveY - mTouchY;
                        invalidate();

                        //mTouchX = mMoveX;
                        //mTouchY = mMoveY;
                    //}
                }
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起的时候
                if (Math.abs(mMoveXSize - mClipLeft) <10){
                    Log.e("PPS"," 验证成功 " );
                }else {
                    mMoveXSize = 0;
                    invalidate();
                }
                break;
        }

        return isTouched;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
}
