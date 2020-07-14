package com.dfzt.tabmodule;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.dfzt.base.activity.BaseActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.tabmodule.databinding.ActivityTestBinding;
import com.dfzt.tabmodule.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 *这个使用内部拦截法来解决bug （未解决）
 *
 */
public class TabInActivity extends BaseActivity<ActivityTestBinding> {


    private String [] titles = {"标签一","标签二","标签三","标签四"};
    private List<Fragment> mFragments = new ArrayList<>();
    private int mHeight = 0;//搜你想搜需要移动的高度
    private int mHintWidth = 0;//搜你想搜的控件宽度
    private int mScaleWidth = 0;//宽度缩小的距离的最大值

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    //重载沉浸式状态栏的方法
    @Override
    protected void initStatusBar() {
        //super.initStatusBar();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorAccent),0);
    }

    @Override
    protected void initData() {
        super.initData();

        initViewpager();

        initRefreshView();

        initScrollListener();

    }

    private void initScrollListener() {


        //设置滑动的监听事件
        mDataBinding.nestScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (mHeight == 0){
                    //设置上滑的参数
                    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mDataBinding.topIcon.getLayoutParams();
                    mHeight = (int)(mDataBinding.hintText2.getY() - mDataBinding.topIcon.getY());
                    mHintWidth = mDataBinding.hintText2.getMeasuredWidth();
                    mScaleWidth = mHintWidth - (int) mDataBinding.loginIcon.getX();
                }
                Log.e("PPS",mHeight + "=======" + oldScrollY + "  获取滑动的值 " + scrollY);
                boolean flag = oldScrollY > scrollY;
                if (scrollY > mHeight){
                    scrollY = mHeight;
                }
                if (scrollY == 0){
                    mTranslationAnim(scrollY,false);
                }

                ViewGroup.LayoutParams layoutParams =  mDataBinding.hintText2.getLayoutParams();
                int size = (int) (mScaleWidth * ( 0.03 * scrollY));
                if (size > mScaleWidth){
                    size = mScaleWidth;
                }

                layoutParams.width = (int) (mHintWidth - size);
                //设置宽度
                mDataBinding.hintText2.setLayoutParams(layoutParams);
                Log.e("PPS","缩小的距离 " + size);
                if (scrollY <= 100 && scrollY > oldScrollY) {
                    //向上滑动 宽度缩小
                    mTranslationAnim(scrollY,true);
                }else if (scrollY > 0 && flag){
                    //向下滑动 宽度拉大
                    mTranslationAnim(scrollY,false);

                }
            }
        });

    }

    private void initRefreshView() {
        mDataBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(()->{
                    mDataBinding.refreshLayout.setRefreshing(false);
                },1000);
            }
        });
    }

    private void initViewpager() {
        for (int i = 0; i < titles.length; i++){
            mFragments.add(TabFragment.getNewInstance());
        }
        mDataBinding.viewpager.setOffscreenPageLimit(3);
        mDataBinding.viewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        mDataBinding.tablayout.setupWithViewPager(mDataBinding.viewpager);
    }
    private int index = 0;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123){
                index++;
                mTranslationAnim(index,true);
            }
        }
    };


    private void mTranslationAnim(int x,boolean falg){
        ObjectAnimator mTranslation = null;
        if (falg){
            mTranslation = ObjectAnimator.ofFloat(
                    mDataBinding.hintText2,"translationY",
                    0 - x,
                    - x - 1);
        }else {
            mTranslation = ObjectAnimator.ofFloat(
                    mDataBinding.hintText2,"translationY",
                    - x - 1,
                    0 - x);
        }
        mTranslation.setInterpolator(new LinearInterpolator());
        mTranslation.setRepeatCount(0);
        mTranslation.setRepeatMode(ValueAnimator.RESTART);
        mTranslation.start();
        float alpha = (float) (1 - 0.01 * x);
        if (alpha < 0){
            alpha = 0;
        }
        Log.e("PPS"," alpha " + alpha);
        mDataBinding.topIcon.setAlpha(alpha);
    }

}
