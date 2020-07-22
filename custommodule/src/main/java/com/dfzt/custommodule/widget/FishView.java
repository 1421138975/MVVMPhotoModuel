package com.dfzt.custommodule.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 *
 * 绘制一个移动的锦鲤
 *
 */
public class FishView extends Drawable {

    //定义画笔
    private Paint mPaint;

    //定义路径
    private Path mPath;

    public FishView(){
        mPath = new Path();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter filter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
