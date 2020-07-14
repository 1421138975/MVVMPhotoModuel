package com.dfzt.common.viewholder;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 通用的一个item
 * @param <V>
 */

public class BaseViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private V mDataBinding;
    public BaseViewHolder(V dataBinding) {
        super(dataBinding.getRoot());
        mDataBinding = dataBinding;
    }

    public void bind(){
        //绑定数据
        mDataBinding.executePendingBindings();
    }

    public V getDataBinding(){
        return mDataBinding;
    }
}
