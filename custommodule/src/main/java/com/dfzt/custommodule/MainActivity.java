package com.dfzt.custommodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;

import com.dfzt.base.activity.BaseActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.custommodule.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initStatusBar() {
        //super.initStatusBar();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void initData() {
        super.initData();
        mHandler.postDelayed(()->{
            ObjectAnimator.ofFloat(mDataBinding.text,"perernt",0f,1f).setDuration(8000).start();
        },2000);
    }

    private Handler mHandler = new Handler();
}
