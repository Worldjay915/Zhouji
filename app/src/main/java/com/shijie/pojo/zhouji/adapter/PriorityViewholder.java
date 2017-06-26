package com.shijie.pojo.zhouji.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shijie.pojo.zhouji.R;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.adapter
 * 创建者:  zsj
 * 创建事件: 2017/4/27 15:44
 * 描述:   task 级别 viewholder
 */

public class PriorityViewholder extends RecyclerView.ViewHolder{

    public ImageView iv_priority_icon;
    public TextView  tv_priority_text;


    public PriorityViewholder(View itemView) {
        super(itemView);
        iv_priority_icon = (ImageView) itemView.findViewById(R.id.iv_priority_icon);
        tv_priority_text = (TextView) itemView.findViewById(R.id.tv_priority_text);
    }
}
