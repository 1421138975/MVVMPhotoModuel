package com.dfzt.base.activity;

/**
 * Created by PPS on 2020/07/08.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public interface IBaseView {
    //加载成功 显示类容
    void showContent();

    //正在加载
    void showLoading();

    //刷新为空
    void onRefreshEmpty();

    //加载失败
    void onRefreshFailure(String message);
}
