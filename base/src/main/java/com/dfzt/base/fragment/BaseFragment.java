package com.dfzt.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<V extends ViewDataBinding> extends Fragment {
    protected Context mContext;
    protected V mDataBinding;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    //获取布局文件的方法
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 这个是用来修改数据的
     * @param index 下标 从1开始
     * @param data 修改的javaBean的类型
     */
    /*protected <T> void changeData(int index,T data){
        mDataBinding.setVariable(index,data);
    }*/


    protected void initData(){

    }
}
