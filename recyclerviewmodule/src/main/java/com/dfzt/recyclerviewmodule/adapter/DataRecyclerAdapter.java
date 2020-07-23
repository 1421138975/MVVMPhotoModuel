package com.dfzt.recyclerviewmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dfzt.common.listener.IOnitemClickListener;
import com.dfzt.common.viewholder.BaseViewHolder;
import com.dfzt.recyclerviewmodule.R;
import com.dfzt.recyclerviewmodule.bean.DataBean;
import com.dfzt.recyclerviewmodule.databinding.DataRecyclerItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class DataRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder<DataRecyclerItemBinding>> {

    private Context mContext;
    private List<DataBean> mList;
    private IOnitemClickListener mListener;
    public DataRecyclerAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setList(List<DataBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setListener(IOnitemClickListener listener) {
        mListener = listener;
    }

    /**
     * 是否是组的第一个item
     * @param position
     * @return
     */
    public boolean isGroupHead(int position){
        if (position == 0){
            return true;
        }else {
            //与前面哪一个名称相互比较
            if (getGroupName(position).equals(getGroupName(position - 1))){
                return false;
            }else {
                return true;
            }
        }
    }

    public String getGroupName(int position){
        return mList.get(position).getGroupName();
    }


    @NonNull
    @Override
    public BaseViewHolder<DataRecyclerItemBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.data_recycler_item,parent,false);
        return new BaseViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<DataRecyclerItemBinding> holder, final int position) {
        holder.getDataBinding().setDataBean(mList.get(position));
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
