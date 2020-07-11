package com.dfzt.mvvmphotoactivity.ui;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.billy.cc.core.component.CC;
import com.dfzt.base.activity.BaseActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.mvvmphotoactivity.R;
import com.dfzt.mvvmphotoactivity.databinding.ActivityMainBinding;
import com.dfzt.mvvmphotoactivity.ui.photoactivity.PhotoActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initStatusBar() {
        //super.initStatusBar();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorAccent),0);
    }

    @Override
    protected void initData() {
        super.initData();
        mDataBinding.ccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("CC路由");
            }
        });
        mDataBinding.gankioBtn.setOnClickListener(v->{
            startActivity(new Intent(mContext, PhotoActivity.class));
        });
        mDataBinding.ccBtn.setOnClickListener( v->{
            CC.obtainBuilder("TabActivity").setActionName("showActivity").build().call();
        });
    }

}
