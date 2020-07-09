package com.dfzt.base.activity;

import android.os.Bundle;
import android.view.View;

import com.dfzt.base.loadsir.EmptyCallback;
import com.dfzt.base.loadsir.ErrorCallback;
import com.dfzt.base.loadsir.LoadingCallback;
import com.dfzt.base.loadsir.SuccessCallback;
import com.dfzt.base.viewmodel.IMvvmBaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

/**
 * 这个就是做网络请求的activity的基类
 * @param <V>  ViewDataBinding
 * @param <VM> ViewModel
 */
public abstract class BaseMvvmActivity<V extends ViewDataBinding,VM extends IMvvmBaseViewModel>
        extends BaseActivity<V> implements IBaseView {
    protected VM mViewModel;
    //网络请求的各种不同的状态
    private LoadService mLoadService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        initNetData();
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

    protected void initNetData(){

    }

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
    protected void onDestroy() {
        super.onDestroy();
        //銷毀UI
        if (mViewModel != null && mViewModel.isUIAttached()) {
            mViewModel.detachUI();
        }
    }

    /**
     * 用户自己设置VM 然后我这边去获取
     */
    public abstract VM getViewModel();


}
