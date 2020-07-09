package com.dfzt.base.viewmodel;

import com.dfzt.base.model.SuperBaseModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import androidx.lifecycle.ViewModel;

/**
 * 这里就是VM
 * @param <V> ViewDataBinding
 * @param <M> 业务逻辑层
 */
public class MvvmBaseViewModel<V , M extends SuperBaseModel> extends ViewModel implements IMvvmBaseViewModel<V> {
    private Reference<V> mUIRef;
    protected M model;

    @Override
    public void attachUI(V view) {
        //弱引用
        mUIRef = new WeakReference<>(view);
    }

    @Override
    public V getPageView() {
        if (mUIRef == null) {
            return null;
        }
        return mUIRef.get();
    }

    @Override
    public boolean isUIAttached() {
        return mUIRef != null && mUIRef.get() != null;
    }

    @Override
    public void detachUI() {
        if (mUIRef != null) {
            mUIRef.clear();
            mUIRef = null;

        }
        if(model != null) {
            model.cancel();

        }
    }
}

