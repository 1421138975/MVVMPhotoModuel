package com.dfzt.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.dfzt.base.util.StatusBarUtil;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 *
 * 这种情况是表示只有使用dataBinding 不去网络请求的基类
 *
 * @param <V>
 */
public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {
    protected Context mContext;
    protected V mDataBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //设置沉浸式状态栏
        initStatusBar();
        //初始化dataBinding
        initDataBinding();
        initData();
    }

    private void initDataBinding() {
        mDataBinding = DataBindingUtil.setContentView(this,getLayoutId());
    }

    //获取布局文件的id
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 这个是用来修改数据的
     * @param index 下标 从1开始
     * @param data 修改的javaBean的类型
     */
    protected <T> void changeData(int index,T data){
        mDataBinding.setVariable(index,data);
    }

    protected void initStatusBar() {
        //默认是全透明
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);
    }


    protected void toast(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    //定义加载数据的一个模板方法
    protected void initData(){

    }
}
