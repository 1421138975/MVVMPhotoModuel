package com.dfzt.custommodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dfzt.base.activity.BaseActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.custommodule.databinding.ActivityClearBinding;
import com.dfzt.custommodule.widget.ClearDrawable;

public class ClearActivity extends BaseActivity<ActivityClearBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_clear;
    }

    @Override
    protected void initStatusBar() {
        //super.initStatusBar();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorAccent),0);
    }

    @Override
    protected void initData() {
        super.initData();
        mDataBinding.clear.setImageDrawable(new ClearDrawable(mContext));
    }
}
