package com.dfzt.tabmodule.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dfzt.common.viewholder.BaseViewHolder;
import com.dfzt.tabmodule.BR;
import com.dfzt.tabmodule.databinding.TabFragmentItemBinding;
import com.dfzt.tabmodule.databinding.TabFragmentItemBindingImpl;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 * 定一个一个万能的RecyclerView的适配器 只能支持一种类型
 *
 * @param <T>传递的数据类型
 */
public class MoreRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private int  layoutId;
    private List<T> mList;
    private int dataIndex;//这就是你xml中对应的bean的name 在BR中对应的id
    public MoreRecyclerAdapter(Context mContext,int layout,List<T> datas,int dataIndex){
        this.mContext = mContext;
        this.layoutId = layout;
        this.mList = datas;
        this.dataIndex = dataIndex;
    }

    public void setList(List<T> datas){
        this.mList = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                layoutId,parent,false);
        return new BaseViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        //TabFragmentItemBindingImpl.setVariable()
        holder.getDataBinding().setVariable(dataIndex ,mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
