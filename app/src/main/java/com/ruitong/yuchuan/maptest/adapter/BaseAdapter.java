package com.ruitong.yuchuan.maptest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author sineom
 * @version 1.0
 * @time 2017/8/19 11:49
 * @des ${TODO}
 * @updateAuthor ${Author}
 * @updataTIme 2017/8/19
 * @updataDes ${描述更新内容}
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected ArrayList<T> mDatas;
    protected LayoutInflater mInflater;

    public BaseAdapter(Context context, int layoutId, ArrayList<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    public void updataRecyclerView(ArrayList<T> datas) {
        if (datas == null) {
            mDatas = new ArrayList<>();
        } else {
            mDatas = datas;
        }
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = BaseViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
      //  holder.updatePosition(position);
        convert(holder, position,mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public abstract void convert(BaseViewHolder holder, int position, T t);

}
