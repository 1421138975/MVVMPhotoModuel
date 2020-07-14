package com.dfzt.tabmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dfzt.common.viewholder.BaseViewHolder;
import com.dfzt.tabmodule.R;
import com.dfzt.tabmodule.databinding.TabFragmentItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class TabAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<String> mList;

    public TabAdapter(Context mContext,List<String> mList){
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.tab_fragment_item,parent,false);
        return new BaseViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ((TabFragmentItemBinding)holder.getDataBinding()).setText(mList.get(position));
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
