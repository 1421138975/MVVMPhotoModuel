package com.dfzt.custommodule;

import android.graphics.Camera;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dfzt.base.activity.BaseActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.custommodule.databinding.ActivityCustomTabBinding;
import com.google.android.material.tabs.TabLayout;

public class CustomTabActivity extends BaseActivity<ActivityCustomTabBinding> {

    private String titles [] = {"菜单一","菜单二","菜单三","菜单四","菜单五","菜单六","菜单七","菜单八","菜单九","菜单十"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_tab;
    }

    @Override
    protected void initStatusBar() {
        //super.initStatusBar();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorAccent),0);
    }

    @Override
    protected void initData() {
        super.initData();
        /*mDataBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < titles.length; i++){
            mDataBinding.tablayout.addTab(mDataBinding.tablayout.newTab().setText(titles[i]));
        }*/

        for (int i = 0; i < titles.length; i++){
            TextView textView = new TextView(mContext);
            textView.setText(titles[i]);
            textView.setGravity(Gravity.CENTER);
            mDataBinding.tablayout.addView(textView);
        }



    }
}
