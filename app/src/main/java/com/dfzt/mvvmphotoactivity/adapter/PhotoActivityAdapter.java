package com.dfzt.mvvmphotoactivity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dfzt.common.viewholder.BaseViewHolder;
import com.dfzt.mvvmphotoactivity.R;
import com.dfzt.mvvmphotoactivity.bean.PhotoBean;
import com.dfzt.mvvmphotoactivity.databinding.PhotoActivityItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 这个adpter 不做上拉更多功能
 *
 */
public class PhotoActivityAdapter extends RecyclerView.Adapter<BaseViewHolder<PhotoBean.ResultsBean, PhotoActivityItemBinding>> {

    private Context mContext;
    private List<PhotoBean.ResultsBean> mList;

    public PhotoActivityAdapter(Context mContext){
        this.mContext = mContext;
    }
    public void setList(List<PhotoBean.ResultsBean> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder<PhotoBean.ResultsBean, PhotoActivityItemBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.photo_activity_item, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<PhotoBean.ResultsBean, PhotoActivityItemBinding> holder, int position) {
        holder.getDataBinding().setPhotoModel(mList.get(position));
        holder.bind();
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }
}
