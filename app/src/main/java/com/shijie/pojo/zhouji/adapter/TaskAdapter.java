package com.shijie.pojo.zhouji.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.database.ImageFactory;
import com.shijie.pojo.zhouji.entity.Task;
import com.shijie.pojo.zhouji.utils.GetImageUtils;
import com.shijie.pojo.zhouji.utils.L;
import com.shijie.pojo.zhouji.utils.PicassoUtils;
import com.shijie.pojo.zhouji.utils.StaticClass;

import java.util.List;


/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.adapter
 * 创建者:  zsj
 * 创建事件: 2017/4/26 22:53
 * 描述:  task记录Adapter
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewholder> {

    private Context mContext;
    private List<Task> mList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private TaskViewholder holder;
    private LinearLayoutManager layoutManager;


    public TaskAdapter(Context mContext,List<Task> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }
    //自定义接口，实现点击事件
    public interface OnItemClickListener {

        void OnClickItem(View view, int position);

        void OnLongClickItem(View view, int position);
    }

    //注入接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public TaskViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_item_recycler,parent,false);
        holder = new TaskViewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TaskViewholder holder, final int position) {
        //利用picasso 进行图片加载
        String iconName = mList.get(position).getIcon();
        String ImgUrl = GetImageUtils.AssetUrl+iconName;
        PicassoUtils.loadImageView(mContext,ImgUrl,holder.circleImageView);

        holder.tv_title.setText(mList.get(position).getTitle());
        holder.tv_sub.setText(mList.get(position).getContent());
        //事件级别显示
        int[] priorityIcons = ImageFactory.createPriorityIcons();
        holder.img_priority.setImageResource(priorityIcons[mList.get(position).getPriority()]);
        //显示任务是否完成
        int state = mList.get(position).getState();
        if (state == StaticClass.STATE_NOT_FINISH){
            holder.ll_finished_mask.setVisibility(View.INVISIBLE);
        }else {
            holder.ll_finished_mask.setVisibility(View.VISIBLE);
        }
        holder.itemView.setTag(position);
        //设置点击回调
        if (onItemClickListener != null) {
            //短点击
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    L.i("onClick--------------");
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.OnClickItem(holder.itemView, pos);
                }
            });
            //长点击
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    L.i("onLongClick--------------");
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.OnLongClickItem(holder.itemView, pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //移除item
    public void removeTask(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void refreshTask(int position, List<Task> mList,RecyclerView mRecyclerView){
        int state = mList.get(position).getState();
        //通过ViewHolder找出缓存的对应控件
        TaskViewholder viewHolder = (TaskViewholder)mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(position));
        if (state == StaticClass.STATE_NOT_FINISH){
            viewHolder.ll_finished_mask.setVisibility(View.GONE);
            //notifyItemChanged(position);
        }else {
            viewHolder.ll_finished_mask.setVisibility(View.VISIBLE);
           // notifyItemChanged(position);
        }
    }

    public void addTask(Task task){
        L.i("refreshNextDayTask");
        mList.add(task);
        notifyItemInserted(mList.size()-mList.size()+2);
    }



}
