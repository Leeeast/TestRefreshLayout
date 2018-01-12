package com.iteast.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by Administrator on 2018/1/11.
 */

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_NOMAL = 0;
    public static final int TYPE_EMPTY = 1;

    public List<String> mUserInfo;
    public OnClickListener mOnClickListener;

    public ResultAdapter(List<String> userInfos, OnClickListener lisnter) {
        this.mUserInfo = userInfos;
        this.mOnClickListener = lisnter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_EMPTY:
                // View viewEmpyt = LayoutInflater.from(parent.getContext()).inflate(R.layout.aq_item_result_empty, parent, false);
                View view = new View(parent.getContext());
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ZhiboUIUtils.dip2px(parent.getContext(), 74), 10);
                view.setLayoutParams(layoutParams);
                return new ResultEmptyHolder(view);
            case TYPE_NOMAL:
                View viewNormal = LayoutInflater.from(parent.getContext()).inflate(R.layout.aq_item_result, parent, false);
                return new ResultNomalHolder(viewNormal);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_EMPTY:
                //TODO 控制空布局宽高
                break;
            case TYPE_NOMAL:
                final String s = mUserInfo.get((position + 1) / 2);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnClickListener != null) {
                            mOnClickListener.onClick(s);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mUserInfo.size() * 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_NOMAL;
        } else {
            return TYPE_EMPTY;
        }
    }

    public interface OnClickListener {
        void onClick(String info);
    }
}
