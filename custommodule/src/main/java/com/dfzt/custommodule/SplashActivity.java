package com.dfzt.custommodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dfzt.base.activity.BaseActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.custommodule.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> implements View.OnClickListener {

    @Override
    protected void initStatusBar() {
        //super.initStatusBar();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        super.initData();
        mDataBinding.btn1.setOnClickListener(this);
        mDataBinding.btn2.setOnClickListener(this);
        mDataBinding.btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                startActivity(new Intent(mContext, MainActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(mContext, ClearActivity.class));
                break;
            case R.id.btn_3:
                startActivity(new Intent(mContext, SlidingActivity.class));
                break;
        }
    }
}
