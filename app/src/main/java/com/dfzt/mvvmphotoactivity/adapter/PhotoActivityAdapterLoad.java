package com.dfzt.mvvmphotoactivity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dfzt.common.constant.Constant;
import com.dfzt.common.databinding.RecyclerviewFootItemBinding;
import com.dfzt.common.viewholder.BaseViewHolder;
import com.dfzt.mvvmphotoactivity.R;
import com.dfzt.mvvmphotoactivity.bean.PhotoBean;
import com.dfzt.mvvmphotoactivity.databinding.PhotoActivityItem2Binding;
import com.dfzt.mvvmphotoactivity.databinding.PhotoActivityItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 这个adpter 不做上拉更多功能
 *
 */
public class PhotoActivityAdapterLoad extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<PhotoBean.ResultsBean> mList;
    private int CONTENT_ITEM = 0;
    private int FOOE_ITEM = 1;
    private String LOAD_STATE = Constant.LOADING_FINISH;

    public PhotoActivityAdapterLoad(Context mContext){
        this.mContext = mContext;
    }

    public void setList(List<PhotoBean.ResultsBean> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setLoadState(String state){
        this.LOAD_STATE = state;
        notifyDataSetChanged();//刷新最后一个
    }

    public String getLoadState(){
        return LOAD_STATE;
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size() + 1;
        }
        return 0 + 1;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == CONTENT_ITEM) {
            PhotoActivityItem2Binding mContentBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.photo_activity_item2, parent, false);
            return new BaseViewHolder<PhotoActivityItem2Binding>(mContentBinding);
        }else{
            RecyclerviewFootItemBinding mFootBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.recyclerview_foot_item, parent, false);
            return new BaseViewHolder<RecyclerviewFootItemBinding>(mFootBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder.getDataBinding() instanceof PhotoActivityItem2Binding){
            //正常的内容
            ((PhotoActivityItem2Binding)holder.getDataBinding()).setPhotoModel(mList.get(position));
        }else if (holder.getDataBinding() instanceof RecyclerviewFootItemBinding) {
            //更新的内容
            ((RecyclerviewFootItemBinding)holder.getDataBinding()).setState(LOAD_STATE);
        }
        holder.bind();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()){
            return FOOE_ITEM;//底部上拉
        }else{
            return CONTENT_ITEM;//内容
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == FOOE_ITEM ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }
}
