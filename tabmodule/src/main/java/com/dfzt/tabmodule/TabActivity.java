package com.dfzt.tabmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dfzt.base.activity.BaseActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.tabmodule.databinding.ActivityTabBinding;

public class TabActivity extends BaseActivity<ActivityTabBinding> {

    private String [] titles = {"标签一","标签二","标签三","标签四"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab;
    }

    //重载沉浸式状态栏的方法
    @Override
    protected void initStatusBar() {
        //super.initStatusBar();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorAccent),100);
    }

    @Override
    protected void initData() {
        super.initData();

    }
}
