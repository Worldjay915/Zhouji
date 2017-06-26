package com.shijie.pojo.zhouji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.database.ImageFactory;
import com.shijie.pojo.zhouji.entity.PriorityItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.adapter
 * 创建者:  zsj
 * 创建事件: 2017/4/27 15:56
 * 描述:   级别 适配器
 */

public class PriorityAdapter extends RecyclerView.Adapter<PriorityViewholder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<PriorityItem> mList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public PriorityAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    //注入自定义点击事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public PriorityViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.priority_item,parent,false);
        PriorityViewholder viewholder = new PriorityViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final PriorityViewholder holder, int position) {
        holder.iv_priority_icon.setImageResource(mList.get(position).getResId());
        holder.tv_priority_text.setText(mList.get(position).getPriorityName());
        holder.iv_priority_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                onItemClickListener.OnItemClick(holder.itemView,pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //给集合里添加内容
    {
        int[] priorityIcons = ImageFactory.createPriorityIcons();
        mList.add(new PriorityItem(priorityIcons[0],"正常"));
        mList.add(new PriorityItem(priorityIcons[1],"一般"));
        mList.add(new PriorityItem(priorityIcons[2],"重要"));
        mList.add(new PriorityItem(priorityIcons[3],"紧急"));
    }

    public interface OnItemClickListener{
        void OnItemClick(View view,int position);
    }

}
