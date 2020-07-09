package com.dfzt.base.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dfzt.base.activity.IBaseView;
import com.dfzt.base.loadsir.EmptyCallback;
import com.dfzt.base.loadsir.ErrorCallback;
import com.dfzt.base.loadsir.LoadingCallback;
import com.dfzt.base.viewmodel.IMvvmBaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

public abstract class BaseMvvmFragment<V extends ViewDataBinding,VM extends IMvvmBaseViewModel>
        extends BaseFragment<V> implements IBaseView {
    protected VM mViewModel;
    private String TAG = "FRAGMENT";
    //网络请求的各种不同的状态
    private LoadService mLoadService;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = getViewModel();
        //绑定UI
        if (mViewModel != null){
            mViewModel.attachUI(this);
        }
    }

    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
    }

    //重新获取数据
    protected abstract void onRetryBtnClick();

    @Override
    public void showContent() {
        //加载完成 显示文本
        if(mLoadService != null){
            mLoadService.showSuccess();
        }
    }

    @Override
    public void showLoading() {
        //正在加载
        if(mLoadService != null){
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void onRefreshEmpty() {
        //刷新为空
        if(mLoadService != null){
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        //加载失败
        if(mLoadService != null){
            mLoadService.showCallback(ErrorCallback.class);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (mViewModel != null && mViewModel.isUIAttached())
            mViewModel.detachUI();
        Log.d(TAG, this + ": " + "onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, this + ": " + "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, this + ": " + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, this + ": " + "onResume");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, this + ": " + "onDestroy");
        super.onDestroy();
    }
    public abstract VM getViewModel();
}
