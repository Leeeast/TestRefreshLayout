package com.iteast.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/1/11.
 */

public class ResultNomalHolder extends RecyclerView.ViewHolder {

    public ImageView mRivUserHead;
    public TextView mTvUserName;
    public TextView mTvAward;

    public ResultNomalHolder(View itemView) {
        super(itemView);
        mRivUserHead = itemView.findViewById(R.id.riv_user_head);
        mTvUserName = itemView.findViewById(R.id.tv_user_name);
        mTvAward = itemView.findViewById(R.id.tv_award);
    }
}