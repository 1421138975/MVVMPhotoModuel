package com.dfzt.recyclerviewmodule.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dfzt.recyclerviewmodule.adapter.DataRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import util.DensityUtils;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private int groupHeadHeight ;
    private Paint headPaint;
    private Rect textRect;
    private Context mContext;
    public CustomItemDecoration(Context mContext){
        this.mContext = mContext;
        groupHeadHeight = DensityUtils.dip2px(mContext,50);
        headPaint = new Paint();
        headPaint.setColor(Color.RED);
        headPaint.setAntiAlias(true);
        textRect = new Rect();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //一般都是使用这个方法
        if (parent.getAdapter() instanceof DataRecyclerAdapter){
            DataRecyclerAdapter mAdapter = (DataRecyclerAdapter) parent.getAdapter();
            //获取看的见的item
            int count = parent.getChildCount();
            //获取左右的距离
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            //TODO parent.getClipToPadding()  表示是否在padding中绘制 true 表示不能
            for (int i = 0; i < count; i++) {
                View view = parent.getChildAt(i);
                //获取当前view所在的下标
                int position = parent.getChildLayoutPosition(view);
                if (mAdapter.isGroupHead(position) &&
                        view.getTop() - groupHeadHeight - parent.getPaddingTop() >= 0){
                    //表示是头部  绘制
                    headPaint.setColor(Color.RED);
                    c.drawRect(left,view.getTop() - groupHeadHeight,right,view.getTop(),headPaint);
                    String name = mAdapter.getGroupName(position);
                    headPaint.setColor(Color.WHITE);
                    headPaint.setStyle(Paint.Style.FILL);
                    headPaint.setTextSize(DensityUtils.sp2px(mContext,20));
                    headPaint.getTextBounds(name,0,name.length(),textRect);
                    c.drawText(name,left + 20,view.getTop() - groupHeadHeight / 2 + textRect.height() / 2 , headPaint);

                }else if (view.getTop() - groupHeadHeight - parent.getPaddingTop() >= 0){
                    headPaint.setColor(Color.RED);
                    c.drawRect(left,view.getTop() - 1,right,view.getTop(),headPaint);
                }

            }

        }

    }

    //这个绘制是在itemview的绘制之后绘制 会覆盖itemview
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getAdapter() instanceof DataRecyclerAdapter) {
            DataRecyclerAdapter mAdapter = (DataRecyclerAdapter) parent.getAdapter();
            //获取可见区域的第一个view的item
            int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
            Log.e("PPS","position ==== "  +position);
            //获取可见区域的第一个itemview
            View itemView = parent.findViewHolderForAdapterPosition(position).itemView;
            //获取左右的距离
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int top = parent.getPaddingTop();
            //表示下一个item是一个新的组 需要网上滑动
            boolean groupHead = mAdapter.isGroupHead(position + 1);
            if (groupHead ){
                c.save();
                int bottom = Math.min(groupHeadHeight,itemView.getBottom() - parent.getPaddingTop());
                //裁剪当前画布 让 文字绘制只能在这个画布中画  超过了就不显示
                c.clipRect(left,top,right,top + bottom);
                //移动的距离慢慢减少
                c.drawRect(left,top,right,top + bottom,headPaint);
                String name = mAdapter.getGroupName(position);
                headPaint.setColor(Color.WHITE);
                headPaint.setStyle(Paint.Style.FILL);
                headPaint.setTextSize(DensityUtils.sp2px(mContext,20));
                headPaint.getTextBounds(name,0,name.length(),textRect);
                int textY = top + bottom - groupHeadHeight / 2
                        + textRect.height() / 2;
                c.drawText(name,left + 20,textY, headPaint);
                c.restore();
            }else {
                //表示上面的头部不变
                headPaint.setColor(Color.RED);
                c.drawRect(left,top,right,top + groupHeadHeight,headPaint);
                String name = mAdapter.getGroupName(position);
                headPaint.setColor(Color.WHITE);
                headPaint.setStyle(Paint.Style.FILL);
                headPaint.setTextSize(DensityUtils.sp2px(mContext,20));
                headPaint.getTextBounds(name,0,name.length(),textRect);
                c.drawText(name,left + 20,top + groupHeadHeight / 2 + textRect.height() / 2 , headPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //如果是头部 预留更多的地方
        if (parent.getAdapter() instanceof DataRecyclerAdapter) {
            DataRecyclerAdapter mAdapter = (DataRecyclerAdapter) parent.getAdapter();
            int position = parent.getChildLayoutPosition(view);
            boolean isGroupHead = mAdapter.isGroupHead(position);
            //判断item是否是头部
            if (isGroupHead){
                //表示是头部
                outRect.set(0,groupHeadHeight , 0, 0);
            }else {
                //表示不是头部
                outRect.set(0, 1, 0, 0);
            }

        }
    }


    public int getGroupHeadHeight(){
        return groupHeadHeight;
    }

}
