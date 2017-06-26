package com.shijie.pojo.zhouji.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shijie.pojo.zhouji.DAO.TaskDAO;
import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.adapter.TaskAdapter;
import com.shijie.pojo.zhouji.entity.Task;
import com.shijie.pojo.zhouji.utils.L;
import com.shijie.pojo.zhouji.utils.SetFragmentTaskData;
import com.shijie.pojo.zhouji.utils.ShareUtils;
import com.shijie.pojo.zhouji.utils.StaticClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.fragment
 * 创建者:  zsj
 * 创建事件: 2017/4/25 14:51
 * 描述:
 */

public class SatFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList = new ArrayList<>();
    private SetFragmentTaskData setFragmentTaskData;
    private TaskDAO taskDAO;
    private int layout;
    private SatBroadcastReceiver receiver = new SatBroadcastReceiver();
    /**
     * 这是与activity相关联的方法
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setFragmentTaskData = (SetFragmentTaskData) context;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcast();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saturday, null);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //获得当前周
        int currentWeek = ShareUtils.getInt(getActivity(), StaticClass.CURRENT_WEEK, 0);
        L.i("onResume()------fragment");
        //数据库查询
        taskDAO = new TaskDAO();
        if (currentWeek == 0 ){
            taskList = taskDAO.query(6);
        }else {
            taskList = taskDAO.query(6,currentWeek);
        }
        adapter = new TaskAdapter(getActivity(), taskList);

        layout = ShareUtils.getInt(getActivity(), StaticClass.LAYOUT, StaticClass.LINEARLAYOUT);

        if (layout == StaticClass.LINEARLAYOUT){
            mRecyclerView.setLayoutManager(new
                    LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        }else if (layout == StaticClass.GRIDLAYOUT){
            mRecyclerView.setLayoutManager(new
                    GridLayoutManager(getActivity(),2));
        }
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initEvent();
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
    }

    private void initEvent() {

        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void OnClickItem(View view, int position) {
                //向宿主activity(MainActivity) 发送消息
                Task task = taskList.get(position);
                setFragmentTaskData.setTaskdata(task);
            }

            @Override
            public void OnLongClickItem(View view, int position) {

                final Task task = taskList.get(position);
                showBottomSheetDialog(task,position);
            }
        });
    }

    private void showBottomSheetDialog(final Task task, final int position) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View viewDialog = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet,null);
        //标记任务已完成
        viewDialog.findViewById(R.id.ll_action_flag_task).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //获取状态
                        int state = task.getState();
                        if (state == StaticClass.STATE_NOT_FINISH) {
                            taskDAO.updateTask(task.get_id(), task.getTitle(), task.getContent(),
                                    task.getTimeStamp(), task.getIcon(), StaticClass.STATE_FINISH,
                                    task.getPriority(), task.getDayOfWeek(), task.getWeekOfYear());
                        } else if (state == StaticClass.STATE_FINISH) {
                            taskDAO.updateTask(task.get_id(), task.getTitle(), task.getContent(),
                                    task.getTimeStamp(), task.getIcon(), StaticClass.STATE_NOT_FINISH,
                                    task.getPriority(), task.getDayOfWeek(), task.getWeekOfYear());
                        }

                        TaskDAO taskDAO = new TaskDAO();
                        int currentWeek = ShareUtils.getInt(getActivity(), StaticClass.CURRENT_WEEK, 0);
                        if (currentWeek == 0 ){
                            taskList = taskDAO.query(6);
                        }else {
                            taskList = taskDAO.query(6,currentWeek);
                        }
                        adapter.refreshTask(position,taskList,mRecyclerView);
                        bottomSheetDialog.dismiss();
                    }
                });
        //任务推迟到下一天
        viewDialog.findViewById(R.id.ll_action_put_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //更新周几
                int nextDay = task.getDayOfWeek()+1;
                if (nextDay>7){ nextDay = 1;}
                taskDAO.updateTask(task.get_id(),task.getTitle(),task.getContent(),
                        task.getTimeStamp(),task.getIcon(), task.getState(),
                        task.getPriority(),nextDay,task.getWeekOfYear());
                adapter.removeTask(position);
                Intent intent = new Intent("Sun");
                intent.putExtra("task",task);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                bottomSheetDialog.dismiss();
            }
        });
        //编辑任务，跳转到任务更新页面
        viewDialog.findViewById(R.id.ll_action_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                setFragmentTaskData.setTaskdata(task);
            }
        });
        //删除任务
        viewDialog.findViewById(R.id.ll_action_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = task.get_id();
                L.i(id+"-----------------");
                taskDAO.deleteTask(id);
                adapter.removeTask(position);
                bottomSheetDialog.dismiss();

            }
        });

        bottomSheetDialog.setContentView(viewDialog);
        bottomSheetDialog.show();

    }

    public void registerBroadcast(){
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("Sat");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    public class SatBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Task  task = (Task) intent.getSerializableExtra("task");
            if (intent.getAction().equals("Sat")){
                adapter.addTask(task);
            }
        }
    }

}
