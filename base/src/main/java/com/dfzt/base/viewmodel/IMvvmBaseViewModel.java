package com.dfzt.base.viewmodel;

public interface IMvvmBaseViewModel<V> {
    //绑定View
    void attachUI(V view);

    //获取V
    V getPageView();

    //判断UI是否已经销毁了
    boolean isUIAttached();

    //销毁
    void detachUI();
}
