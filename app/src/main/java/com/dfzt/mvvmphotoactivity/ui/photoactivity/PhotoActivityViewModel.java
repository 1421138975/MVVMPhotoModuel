package com.dfzt.mvvmphotoactivity.ui.photoactivity;

import com.dfzt.base.activity.IBaseView;
import com.dfzt.base.viewmodel.MvvmBaseViewModel;
import com.dfzt.mvvmphotoactivity.bean.PhotoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * MVVM中的 VM层
 */

public class PhotoActivityViewModel extends MvvmBaseViewModel<PhotoActivityViewModel.IPhotoActivityView,PhotoActivityModel>
        implements PhotoBaseModel.PhotoModelListener<List<PhotoBean.ResultsBean>>  {
    private List<PhotoBean.ResultsBean> mPhotos = new ArrayList<>();

    public PhotoActivityViewModel(){
        model = new PhotoActivityModel();
        model.register(this);
    }

    @Override
    public void loadFinish(List<PhotoBean.ResultsBean> data,boolean isRefresh,boolean isEmpty,boolean isFirstLoad) {
        if (getPageView() != null){
            if (isEmpty) { //获取的数据为空
                if (isRefresh) {
                    //表示没有获取到数据
                    if (isFirstLoad) {
                        getPageView().onRefreshEmpty();
                    }else {
                        getPageView().onRefreshDataEmpty();
                    }
                }else{
                    //上拉到最底部了
                    getPageView().loadMoreEmpty();
                }
            }else {
                if (isRefresh) {
                    //表示是第一次获取数据  或者说上拉刷新
                    mPhotos.clear();
                    mPhotos.addAll(0, data);
                } else {
                    mPhotos.addAll(data);
                }
                //回调数据获取成功
                getPageView().getPhotoSuccess(mPhotos);
            }
        }
    }

    @Override
    public void loadFailed(String msg) {
        if (getPageView() != null){
            getPageView().onRefreshFailure(msg);
        }
    }

    public void refreshData(){
        model.refreshData();
    }

    public void loadMoreData(){
        model.loadMoreData();
    }

    public void getPhoto() {
        model.loadData();
    }

    @Override
    public void detachUI() {
        super.detachUI();
        model.unRegister(this);
    }

    public interface IPhotoActivityView extends IBaseView {
        /**
         * 获取数据成功
         * @param photos
         */
        void getPhotoSuccess(List<PhotoBean.ResultsBean> photos);

        /**
         * 没有更多数据了
         */
        void loadMoreEmpty();

        /**
         * 下拉刷新为null
         */
        void onRefreshDataEmpty();
    }

}
