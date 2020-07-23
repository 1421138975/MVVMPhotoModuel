package com.dfzt.recyclerviewmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.dfzt.base.activity.BaseActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.common.listener.IOnitemClickListener;
import com.dfzt.recyclerviewmodule.adapter.DataRecyclerAdapter;
import com.dfzt.recyclerviewmodule.bean.DataBean;
import com.dfzt.recyclerviewmodule.databinding.ActivityMainBinding;
import com.dfzt.recyclerviewmodule.decoration.CustomItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecyclerActivity extends BaseActivity<ActivityMainBinding> {

    private List<DataBean> mList = new ArrayList<>();
    private DataRecyclerAdapter mAdapter;
    private boolean isNeedClick = true;
    @Override
    protected void initStatusBar() {
        //super.initStatusBar();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorPrimary),0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();

        initDataBean();

        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new DataRecyclerAdapter(mContext);
        mAdapter.setList(mList);
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        //自定义recyclerview的下划线
        CustomItemDecoration itemDecoration = new CustomItemDecoration(mContext);
        mDataBinding.recyclerview.addItemDecoration(itemDecoration);
        mDataBinding.recyclerview.setAdapter(mAdapter);
        //这里就是处理点击事件 点击吸顶位置的时候会触发下面的item的点击事件
        //      应为这个吸顶始终是在头部的 只要判断是指点击的位置 是否是处于吸顶的位置 处于的话 就拦截这次点击事件
        mDataBinding.recyclerview.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                if (e.getY() > itemDecoration.getGroupHeadHeight() + rv.getPaddingTop()){
                   return false;
                }else {
                    return true;
                }

            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        mAdapter.setListener(new IOnitemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                //获取可见区域的第一个view的item
                if (isNeedClick) {
                    Toast.makeText(mContext, mList.get(position).getName(), Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private void initDataBean() {

        for (int i = 0; i < 3;i++){
            for (int j = 0 ; j < 20; j++){
                if (i  == 1){
                    mList.add(new DataBean("鞠婧祎 " + (j + 1),"分组鞠婧祎",1));
                }else if (i  == 2){
                    mList.add(new DataBean("阚清子 " + (j + 1),"分组阚清子",2));
                }else {
                    mList.add(new DataBean("陈钰琪 " + (j + 1),"分组陈钰琪",0));
                }
            }
        }
        for (int i = 0; i < 50; i++){

        }

        Collections.sort(mList, new Comparator<DataBean>() {
            @Override
            public int compare(DataBean bean, DataBean t1) {
                if (bean.getGroupIndex() < t1.getGroupIndex()){
                    return -1;
                }else if (bean.getGroupIndex() > t1.getGroupIndex()){
                    return 1;
                }else {
                    return 0;
                }
            }
        });
    }
}
