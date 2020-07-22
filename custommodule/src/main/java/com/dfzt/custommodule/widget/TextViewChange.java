package com.dfzt.custommodule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自定义一个文字渐变的TextView
 */

public class TextViewChange extends AppCompatTextView {

    //定一个画笔
    private Paint mPaint;
    //定一个一个Rect
    private Rect mRect;
    //定义绘制虚线的一个工具
    private DashPathEffect mPathEffect;

    //定义一个渐变的百分比
    private float mPerernt = 0f;

    private String mText = "联纸老虎跑得快";
    public TextViewChange(Context context) {
        this(context,null);
    }

    public TextViewChange(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextViewChange(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //对画笔进行初始化
    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        mRect = new Rect();

        /**
         * 绘制虚线的一个工具
         * 数组中第一个参数 每一个虚线的长度
         * 数组中第二个参数 每一个虚线之间的间隔长度
         */
        mPathEffect = new DashPathEffect(new float[] {10, 10}, 0);
    }


    public float getPerernt() {
        return mPerernt;
    }

    public void setPerernt(float perernt) {
        mPerernt = perernt;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 将文字绘制在中心位置
         */

        //1.先绘制2个中心的辅助线
        mPaint.reset();
        //绘制虚线
        mPaint.setPathEffect(mPathEffect);
        canvas.drawLine(getWidth() / 2,0,getWidth() / 2,getHeight(),mPaint);

        canvas.drawLine(0,getHeight() / 2 ,getWidth() ,getHeight() / 2,mPaint);

        drewDefText(canvas);

        //绘制红色的文字
        drawRedText(canvas);

    }

    private void drewDefText(Canvas canvas) {
        canvas.save();
        mPaint.reset();
        //绘制文字
        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(60);
        //1.获取文字的长度
        float mTextWidth = mPaint.measureText(mText);
        //获取文字的高度
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        //                  基准线往下 +值      基准线往下 -值
        float mTextHeight = metrics.descent - metrics.ascent;

        int left = (int) (((getWidth() - mTextWidth) / 2) + mTextWidth * mPerernt);

        int top = (int) (getHeight() / 2 - mTextHeight / 2);

        int right = (int) (left + mTextWidth);

        int bottom = (int) (getHeight() / 2 + mTextHeight / 2);

        //进行百分比额裁剪
        mRect.set(left,top,right,bottom);

        canvas.clipRect(mRect);

        //计算在屏幕的中心位置 getHeight() / 2 + mTextHeight / 2 - metrics.descent
        //      == getHeight() / 2 - metrics.descent / 2 - metrics.ascent /2
        //      == (getHeight() - metrics.descent - metrics.ascent) /2
        canvas.drawText(mText,(getWidth() - mTextWidth) / 2 ,(getHeight() - metrics.descent - metrics.ascent) /2,mPaint);
        canvas.restore();
    }

    private void drawRedText(Canvas canvas) {
        //绘制红色的文字
        mPaint.setColor(Color.RED);
        //1.获取文字的长度
        float mTextWidth = mPaint.measureText(mText);
        //获取文字的高度
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        //                  基准线往下 +值      基准线往下 -值
        float mTextHeight = metrics.descent - metrics.ascent;//正常文字的高度
        //获取文字的高度的一半  (metrics.descent - metrics.ascent)/ 2 - metrics.descent
        //计算在屏幕的中心位置 getHeight() / 2 + mTextHeight / 2 - metrics.descent
        //      == getHeight() / 2 - metrics.descent / 2 - metrics.ascent /2
        //      == (getHeight() - metrics.descent - metrics.ascent) /2
        int left = (int) ((getWidth() - mTextWidth) / 2);

        int top = (int) (getHeight() / 2 - mTextHeight / 2);

        int right = (int) (left + mTextWidth * mPerernt);

        int bottom = (int) (getHeight() / 2 + mTextHeight / 2);

        //进行百分比额裁剪
        mRect.set(left,top,right,bottom);

        canvas.clipRect(mRect);

        canvas.drawText(mText,left ,(getHeight() - metrics.descent - metrics.ascent) /2,mPaint);
    }
}
