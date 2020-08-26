package com.dfzt.recyclerviewmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.dfzt.base.activity.BaseActivity;
import com.dfzt.base.util.StatusBarUtil;
import com.dfzt.recyclerviewmodule.adapter.DataRecyclerAdapter;
import com.dfzt.recyclerviewmodule.adapter.DataRecyclerAdapter2;
import com.dfzt.recyclerviewmodule.databinding.ActivityGrildBinding;
import com.dfzt.recyclerviewmodule.decoration.CustomGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends BaseActivity<ActivityGrildBinding> {
    private List<String> mList = new ArrayList<>();
    private DataRecyclerAdapter2 mAdapter;
    @Override
    protected void initStatusBar() {
        //super.initStatusBar();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorPrimary),0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grild;
    }

    @Override
    protected void initData() {
        super.initData();
        initListData();
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new DataRecyclerAdapter2(mContext);
        mAdapter.setList(mList);
        mDataBinding.recycler.setLayoutManager(new GridLayoutManager(mContext,2));
        mDataBinding.recycler.addItemDecoration(new CustomGridItemDecoration());
        mDataBinding.recycler.setAdapter(mAdapter);
    }

    private void initListData() {
        for (int i = 0; i < 53; i++){
            mList.add("陈钰琪 "+ (i+1) +" 个item");
        }
    }
}
