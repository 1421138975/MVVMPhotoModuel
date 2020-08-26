package com.dfzt.custommodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dfzt.base.activity.BaseActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.custommodule.databinding.ActivitySlidingBinding;

/**
 *
 * 滑动验证
 *
 */
public class SlidingActivity extends BaseActivity<ActivitySlidingBinding> {

    @Override
    protected void initStatusBar() {
        //super.initStatusBar();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sliding;
    }
}
