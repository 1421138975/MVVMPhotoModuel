package com.dfzt.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.dfzt.base.util.StatusBarUtil;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 这种方法控制不了外边距和内边距
 */
public class MyConstraintLayout extends ConstraintLayout {
    private Context mContext;
    public MyConstraintLayout(Context context) {
        this(context,null);
    }

    public MyConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View view = getChildAt(0);
        measureChild(view,widthMeasureSpec,heightMeasureSpec);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(mContext);
        params.width = getMeasuredWidth();
        view.setLayoutParams(params);
    }
}
