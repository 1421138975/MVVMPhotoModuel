package com.dfzt.mvvmphotoactivity.ui.photoactivity;

import android.util.Log;
import android.view.ViewGroup;

import com.dfzt.base.activity.BaseMvvmActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.common.constant.Constant;
import com.dfzt.common.listener.EndlessRecyclerOnScrollListener;
import com.dfzt.mvvmphotoactivity.R;
import com.dfzt.mvvmphotoactivity.adapter.PhotoActivityAdapter;
import com.dfzt.mvvmphotoactivity.adapter.PhotoActivityAdapterLoad;
import com.dfzt.mvvmphotoactivity.bean.PhotoBean;
import com.dfzt.mvvmphotoactivity.databinding.ActivityPhotoBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class PhotoActivity extends BaseMvvmActivity<ActivityPhotoBinding, PhotoActivityViewModel>
        implements PhotoActivityViewModel.IPhotoActivityView {
    private PhotoActivityAdapterLoad mAdapter;
    private List<PhotoBean.ResultsBean> mList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void onRetryBtnClick() {
        //没有网络的重试
        mViewModel.getPhoto();
        showContent();
    }

    @Override
    public PhotoActivityViewModel getViewModel() {
        return new PhotoActivityViewModel();
    }

    @Override
    public void getPhotoSuccess(List<PhotoBean.ResultsBean> photos) {
        mList.clear();
        mList.addAll(photos);
        mAdapter.setList(mList);
        mAdapter.setLoadState(Constant.LOADING_FINISH);
        for (PhotoBean.ResultsBean bean : mList) {
            Log.e("PPS","utl = " + bean.getUrl());
        }
        mDataBinding.refreshLayout.setRefreshing(false);
        showContent();
    }

    @Override
    public void loadMoreEmpty() {
        mAdapter.setLoadState(Constant.LOADING_END);
    }

    @Override
    public void onRefreshDataEmpty() {
        toast("已经是最新的数据了");
        mDataBinding.refreshLayout.setRefreshing(false);
    }


    @Override
    protected void initNetData() {
        super.initNetData();
        initAdapter();
        setLoadSir(mDataBinding.refreshLayout);
        mViewModel.getPhoto();
        showLoading();
    }

    private void initAdapter() {
        mDataBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refreshData();
            }
        });

        mAdapter = new PhotoActivityAdapterLoad(mContext);
        mAdapter.setList(mList);
        mDataBinding.photoRecycler.setLayoutManager(new GridLayoutManager(mContext,2));
        mDataBinding.photoRecycler.setAdapter(mAdapter);
        mDataBinding.photoRecycler.addOnScrollListener(new EndlessRecyclerOnScrollListener(){

            @Override
            public void onLoadMore() {
                if (mAdapter.getLoadState().equals(Constant.LOADING_FINISH)){
                    //上拉刷新
                    mAdapter.setLoadState(Constant.LOADING_STATE);
                    mViewModel.loadMoreData();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDataBinding.title.post(()->{
            ViewGroup.LayoutParams params = mDataBinding.statusBar.getLayoutParams();
            params.height = StatusBarUtil.getStatusBarHeight(mContext);
            mDataBinding.statusBar.setLayoutParams(params);
        });
    }
}
