package com.dfzt.mvvmphotoactivity.ui.photoactivity;

import android.util.Log;

import com.dfzt.base.model.SuperBaseModel;

import java.lang.ref.WeakReference;

public abstract class PhotoBaseModel<T> extends SuperBaseModel<T> {

    /**
     * 加载网络数据成功  通知所有的注册者刷新数据
     */
    protected void loadSuccess(T data,boolean isRefresh,boolean isEmpty,boolean isFirstLoad){
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IModelListener> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() instanceof PhotoModelListener) {
                        PhotoModelListener listenerItem = (PhotoModelListener) weakListener.get();
                        if (listenerItem != null) {
                            Log.e("PPS","  PHOTO SUCCESS");
                            listenerItem.loadFinish(data,isRefresh,isEmpty,isFirstLoad);
                        }
                    }
                }
                /** 如果我们需要缓存数据，加载成功，让我们保存他到preference */
                if (getCachedPreferenceKey() != null) {
                    saveDataToPreference(data);
                }
            }, 0);
        }
    }


    /**
     *  加载网络数据失败
     *  通知所有的注册着加载结果
     *  */
    protected void loadFail(final String prompt) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IModelListener> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() instanceof PhotoModelListener) {
                        PhotoModelListener listenerItem = (PhotoModelListener) weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.loadFailed(prompt);
                        }
                    }
                }
            }, 0);
        }
    }

    @Override
    protected void notifyCachedData(T data) {
        loadSuccess(data,true,false,true);
    }


    public interface PhotoModelListener<T> extends IModelListener{
        /**
         * 获取图片成功
         * @param data 获取的数据
         * @param isRefresh 是否下拉刷新数据 或者说第一获取数据
         * @param isEmpty 获取的数据是否为空 ==== 下拉到了底部
         * @param  isFirstLoad  表示是否是第一次加载
         */
        void loadFinish(T data,boolean isRefresh,boolean isEmpty,boolean isFirstLoad);

        /**
         * 获取失败
         * @param msg 失败的原因
         */
        void loadFailed(String msg);
    }
}
