package com.dfzt.tabmodule.fragment;

import android.content.Context;

import com.dfzt.base.fragment.BaseFragment;
import com.dfzt.tabmodule.BR;
import com.dfzt.tabmodule.R;
import com.dfzt.tabmodule.adapter.MoreRecyclerAdapter;
import com.dfzt.tabmodule.adapter.TabAdapter;
import com.dfzt.tabmodule.databinding.FragmentTabBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

public class TabFragment extends BaseFragment<FragmentTabBinding> {

    public static TabFragment getNewInstance(){
        TabFragment tabFragment = new TabFragment();
        return tabFragment;
    }

    private List<String> mList = new ArrayList<>();
    private MoreRecyclerAdapter mAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        for (int i = 0; i < 60; i++){
            mList.add("我是第"+(i + 1)+"个Item");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void initData() {
        super.initData();

        /**
         * 这个BR.text就是R.layout.tab_fragment_item variable标签中name的值
         */
        /*mAdapter = new MoreRecyclerAdapter(mContext,R.layout.tab_fragment_item,mList,com.dfzt.tabmodule.BR.text);
        mDataBinding.recyclerview2.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerview2.setAdapter(mAdapter);*/

        TabAdapter adapter = new TabAdapter(mContext,mList);
        mDataBinding.recyclerview2.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerview2.setAdapter(adapter);
    }
}
