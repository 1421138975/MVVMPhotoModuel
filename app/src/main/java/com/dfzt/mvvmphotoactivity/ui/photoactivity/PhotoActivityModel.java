package com.dfzt.mvvmphotoactivity.ui.photoactivity;

import android.util.Log;

import com.dfzt.mvvmphotoactivity.api.PhotoApi;
import com.dfzt.mvvmphotoactivity.api.PhotoApiInterface;
import com.dfzt.mvvmphotoactivity.bean.PhotoBean;
import com.dfzt.network.base.BaseNetwork;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * MVVM  中的 M层
 */
public class PhotoActivityModel extends PhotoBaseModel<List<PhotoBean.ResultsBean>> {

    private int pageSize = 20;
    private int pageIndex = 13;
    private boolean isRefresh = true;
    private boolean isFirstLoad = true;
    /**
     * 加载更多
     */
    protected void loadMoreData(){
        pageIndex++;
        isRefresh = false;
        isFirstLoad = false;
        if (pageIndex < 22) { //这里是我模拟的已经到最底部了
            loadData();
        }else {
            loadSuccess(null,isRefresh,true,isFirstLoad);
        }
    }

    /**
     * 下拉刷新
     */
    protected void refreshData(){
        isRefresh = true;
        isFirstLoad = false;
        //loadData();
        loadSuccess(null,isRefresh,true,isFirstLoad);
    }

    //获取数据的方法
    @Override
    protected void loadData() {
        PhotoApi.getInstance().getServiceApi(PhotoApiInterface.class)
                .getPhotos(pageSize,pageIndex).compose(BaseNetwork.applySchedulers(new Observer<PhotoBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(PhotoBean bean) {
                Log.e("PPS"," onNext ");
                if (!bean.isError()) {
                    loadSuccess(bean.getResults(),isRefresh,false,isFirstLoad);
                }else {
                    //可以说下拉到底部 (举个列子)
                    loadSuccess(bean.getResults(),isRefresh,true,isFirstLoad);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("PPS"," onError " + e.getMessage());
                if (pageIndex > 2){
                    pageIndex--;
                }
                loadFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
