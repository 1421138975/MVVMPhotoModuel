package com.dfzt.custommodule.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.dfzt.custommodule.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClearDrawable extends Drawable {
    private Context mContext;
    private Paint mPaint;
    private Path mPath;

    //定义 圆的半径
    private float mCircleRadius = 150;

    //定义背景的宽度
    private float mMaxSize = 2.5f * mCircleRadius;

    //定义X的线条的长度
    private float mXLength = 0.8f * mCircleRadius;
    private float mXStock = 5f;
    //定义√的短边的长度
    private float mGShort = 0.3f * mCircleRadius;
    //定义√的短边的长度
    private float mGLong = 0.5f * mCircleRadius;
    private float mGStock = 8f;
    //绘制背景的bitmap
    private Bitmap mBgBitmap;
    private Bitmap mCircleBitmap;

    //图片的几种状态
    private int DEFALUTE_STATE = 0;//默认状态
    private int ROTATION_END = 1;//表示转圈完成的状态
    private int X_SCALE_END = 2;//X缩小完成的动画
    private int POINT_MOVE_END = 3;//点点移动完成后


    private int STATE = DEFALUTE_STATE;

    //获取中心点的位置
    private float mCenterX = mMaxSize / 2;
    private float mCenterY = mMaxSize / 2;

    private PointF mPointF;

    private float mRotationSize = 0;
    //当前旋转的角度
    private float mCurrentSize = 0;
    //X缩放的百分比
    private float mXScaleSize = 1;
    //点点移动的距离
    private int mPointMoveSize = 0;
    //勾勾长度的百分比
    private float mGGSzie = 0;
    PointF ggPointF = null;
    public ClearDrawable( Context mContext){
        this.mContext = mContext;
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();

        mPointF = new PointF();

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.bg);

        mBgBitmap = Bitmap.createScaledBitmap(bitmap,(int) mMaxSize,(int) mMaxSize,true);

        if (mBgBitmap != bitmap){
            bitmap.recycle();
        }

        Bitmap bitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.circle);

        mCircleBitmap = Bitmap.createScaledBitmap(bitmap2,(int) mCircleRadius * 2,(int) mCircleRadius * 2,true);

        if (mCircleBitmap != bitmap2){
            bitmap2.recycle();
        }
        startAnim();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mPointF.set(mCenterX,mCenterY);//中心点的坐标
        //绘制背景
        drawBackground(canvas);
        //绘制圆圈
        drawCircleBg(canvas);

        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.YELLOW);
        //绘制辅助线
        mPaint.setPathEffect(new DashPathEffect(new float[]{10,5},0));
        canvas.drawLine(0,mCenterY,mMaxSize,mCenterY,mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(mCenterX,0,mCenterX,mMaxSize,mPaint);

        mPaint.reset();
        mPaint.setColor(Color.WHITE);

        //绘制X

        drawX(canvas);

        if (STATE == POINT_MOVE_END){
            //画勾勾
            //勾勾转折点的坐标 mPointF.x,mPointF.y + mPointMoveSize
            if (ggPointF == null){
                ggPointF = new PointF(mPointF.x,mPointF.y + mPointMoveSize);
            }
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(mGStock);
            //获取左边定点的坐标 长度是 mGShort 与 x轴的角度为 135°
            PointF mGSPointF = accordPointF(ggPointF,mGShort * mGGSzie,135);
            //获取右边定点的坐标 长度是 mGLong 与 x轴的角度为 45°
            PointF mGLPointF = accordPointF(ggPointF,mGLong * mGGSzie,45);

            canvas.drawLine(ggPointF.x,ggPointF.y,mGSPointF.x,mGSPointF.y,mPaint);
            canvas.drawLine(ggPointF.x,ggPointF.y,mGLPointF.x,mGLPointF.y,mPaint);

        }

    }


    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(mBgBitmap,0,0,mPaint);

    }

    private void drawCircleBg(Canvas canvas) {
        canvas.save();

        canvas.rotate(mRotationSize + mCurrentSize,mPointF.x,mPointF.y);
        canvas.drawBitmap(mCircleBitmap,mMaxSize / 2 - mCircleRadius,mMaxSize / 2 - mCircleRadius,mPaint);

        canvas.restore();
    }

    //绘制XX  应为X的角度是x轴方向的45度
    private void drawX(Canvas canvas) {
        canvas.save();
        if ((mXLength / 2 * mXScaleSize) < mXStock){
            canvas.drawCircle(mPointF.x,mPointF.y + mPointMoveSize,mXStock,mPaint);
        }else {
            //获取右上角点的坐标
            PointF centerPoint = mPointF;//中心点的坐标
            //获取X的4个点的坐标
            PointF mTopRightPoint = accordPointF(centerPoint, mXLength / 2 * mXScaleSize, 45);
            PointF mBottomRightPoint = accordPointF(centerPoint, mXLength / 2 * mXScaleSize, 315);
            PointF mBottomLeftPoint = accordPointF(centerPoint, mXLength / 2 * mXScaleSize, 225);
            PointF mTopLeftPoint = accordPointF(centerPoint, mXLength / 2 * mXScaleSize, 135);


            mPaint.setStrokeWidth(mXStock);
            mPaint.setColor(Color.WHITE);
            mPaint.setStrokeCap(Paint.Cap.ROUND);

            canvas.drawLine(mTopRightPoint.x, mTopRightPoint.y, mBottomLeftPoint.x, mBottomLeftPoint.y, mPaint);
            canvas.drawLine(mTopLeftPoint.x, mTopLeftPoint.y, mBottomRightPoint.x, mBottomRightPoint.y, mPaint);
        }
        canvas.restore();
    }


    /**
     * 根据一个点的坐标  长度 角度 获取另一个点的坐标
     * @param pointF 根据点的坐标
     * @param length 2个点之间的距离
     * @param angle 2个点之间与x轴的角度
     * @return
     * ∠C是90°
     * sinA 是∠A 的对边 / 斜边
     * cosA 是∠A 的邻边 / 斜边
     */
    public PointF accordPointF(PointF pointF,float length,float angle){
        float x = (float) (Math.cos(Math.toRadians(angle)) * length);
        float y = (float) (Math.sin(Math.toRadians(angle - 180)) * length);
        return new PointF(pointF.x + x,pointF.y + y);
    }


    /**
     * @param startPoint 起始点坐标
     * @param length     要求的点到起始点的直线距离 -- 线长
     * @param angle      鱼当前的朝向角度
     * @return
     */
    public PointF calculatePoint(PointF startPoint, float length, float angle) {
        // x坐标
        float deltaX = (float) (Math.cos(Math.toRadians(angle)) * length);
        // y坐标
        float deltaY = (float) (Math.sin(Math.toRadians(angle - 180)) * length);

        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
    }

    @Override
    public void setAlpha(int i) {
        mPaint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter filter) {
        mPaint.setColorFilter(filter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) mMaxSize;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) mMaxSize;
    }

    //启动清除动画
    public void startAnim(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,-1080);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(0);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                mCurrentSize = (float) animator.getAnimatedValue();
                invalidateSelf();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //启动一个新的动画  把X 变为 一个圆

                startXToO();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    //启动一个动画将X == 一个圆
    private void startXToO() {
        ValueAnimator mXValueAnimator = ValueAnimator.ofFloat(1,0f);
        mXValueAnimator.setDuration(800);
        mXValueAnimator.setInterpolator(new LinearInterpolator());
        mXValueAnimator.setRepeatCount(0);
        mXValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mXValueAnimator.start();
        mXValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                mXScaleSize = (float) animator.getAnimatedValue();
                invalidateSelf();
            }
        });

        mXValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                STATE = X_SCALE_END;
                //启动一个动画 把中间这个圆点 上下移动一下

                starXMove();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }
    //X 边为点后 上下移动的动画
    private void starXMove() {
        ValueAnimator animator = ValueAnimator.ofInt(0, -10, 28);
        animator.setDuration(1500);
        animator.setRepeatCount(0);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();

        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                mPointMoveSize = (int) animator.getAnimatedValue();
                invalidateSelf();
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //这个动画结束之后 画一个勾勾
                STATE = POINT_MOVE_END;
                starGGAnim();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void starGGAnim() {
        ValueAnimator mXValueAnimator = ValueAnimator.ofFloat(0,1f);
        mXValueAnimator.setDuration(800);
        mXValueAnimator.setInterpolator(new LinearInterpolator());
        mXValueAnimator.setRepeatCount(0);
        mXValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mXValueAnimator.start();
        mXValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                mGGSzie = (float) animator.getAnimatedValue();
                invalidateSelf();
            }
        });

        mXValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mHandler.postDelayed(()->{
                    STATE = DEFALUTE_STATE;
                     mRotationSize = 0;
                    //当前旋转的角度
                    mCurrentSize = 0;
                    //X缩放的百分比
                    mXScaleSize = 1;
                    //点点移动的距离
                    mPointMoveSize = 0;
                    //勾勾长度的百分比
                    mGGSzie = 0;
                    invalidateSelf();
                },3000);

                mHandler.postDelayed(()->{
                    startAnim();
                },4000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private Handler mHandler = new Handler();
}
