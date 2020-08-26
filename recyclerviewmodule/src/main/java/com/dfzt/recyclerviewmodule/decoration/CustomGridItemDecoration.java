package com.dfzt.recyclerviewmodule.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomGridItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    public CustomGridItemDecoration(){
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int count = parent.getChildCount();
        //获取左右的距离
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            Log.e("PPS", right +" right " + view.getRight());
            mPaint.setColor(Color.GRAY);
            c.drawRect(left, view.getBottom(), view.getRight(), view.getBottom() + 4, mPaint);
            if (view.getRight() < right) {
                mPaint.setColor(Color.GREEN);
                c.drawRect(view.getRight(), view.getTop(), view.getRight() + 4, view.getBottom() + 4, mPaint);
            }
        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,0,0);
    }
}
