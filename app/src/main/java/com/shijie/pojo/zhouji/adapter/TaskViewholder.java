package com.shijie.pojo.zhouji.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijie.pojo.zhouji.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.adapter
 * 创建者:  zsj
 * 创建事件: 2017/4/26 22:45
 * 描述:  task_item 记录 viewholder
 */

public class TaskViewholder extends RecyclerView.ViewHolder {

    public CircleImageView circleImageView;
    public TextView tv_title;
    public TextView tv_sub ;
    public ImageView img_priority;
    public LinearLayout ll_finished_mask;

    public TaskViewholder(View itemView) {
        super(itemView);
        circleImageView= (CircleImageView) itemView.findViewById(R.id.profile_image);
        tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        tv_sub = (TextView) itemView.findViewById(R.id.tv_sub);
        img_priority = (ImageView) itemView.findViewById(R.id.img_priority);
        ll_finished_mask = (LinearLayout) itemView.findViewById(R.id.ll_finished_mask);
    }
}
