package com.dfzt.recyclerviewmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dfzt.common.listener.IOnitemClickListener;
import com.dfzt.common.viewholder.BaseViewHolder;
import com.dfzt.recyclerviewmodule.R;
import com.dfzt.recyclerviewmodule.bean.DataBean;
import com.dfzt.recyclerviewmodule.databinding.DataRecyclerItem2Binding;
import com.dfzt.recyclerviewmodule.databinding.DataRecyclerItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class DataRecyclerAdapter2 extends RecyclerView.Adapter<BaseViewHolder<DataRecyclerItem2Binding>> {

    private Context mContext;
    private List<String> mList;
    private IOnitemClickListener mListener;
    public DataRecyclerAdapter2(Context mContext){
        this.mContext = mContext;
    }

    public void setList(List<String> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setListener(IOnitemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder<DataRecyclerItem2Binding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.data_recycler_item2,parent,false);
        return new BaseViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<DataRecyclerItem2Binding> holder, final int position) {
        holder.getDataBinding().setData(mList.get(position));
        holder.bind();
        holder.itemView.setOnClickListener(v->{
            if (mListener != null){
                mListener.onItemClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
