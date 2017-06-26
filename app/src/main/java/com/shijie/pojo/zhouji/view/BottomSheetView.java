package com.shijie.pojo.zhouji.view;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.view
 * 创建者:  zsj
 * 创建事件: 2017/5/1 11:30
 * 描述: 底部弹出框
 */

public class BottomSheetView {

    /*public BottomSheetView() {
    }

    private  SetFragmentTaskData setFragmentTaskData;
    private  TaskDAO taskDAO;
    private  RecyclerView mRecyclerView;
    private  DataBaseTools dataBaseTools ;

    public void showBottomSheetDialog(final Context mContext, final RecyclerView mRecyclerView, final Task task, final int page) {
        dataBaseTools = new DataBaseTools();
        taskDAO = new TaskDAO();
        setFragmentTaskData = (SetFragmentTaskData) mContext;

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        View viewDialog = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet,null);
        //标记任务已完成
        viewDialog.findViewById(R.id.ll_action_flag_task).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //获取状态
                        int state = task.getState();
                        L.i("state"+state+"--------------"+task.getTitle()+task.getContent()+task.getIcon());
                        if (state == StaticClass.STATE_NOT_FINISH) {
                            taskDAO.updateTask(task.get_id(), task.getTitle(), task.getContent(),
                                    task.getTimeStamp(), task.getIcon(), StaticClass.STATE_FINISH,
                                    task.getPriority(), task.getDayOfWeek(), task.getWeekOfYear());
                        } else if (state == StaticClass.STATE_FINISH) {
                            taskDAO.updateTask(task.get_id(), task.getTitle(), task.getContent(),
                                    task.getTimeStamp(), task.getIcon(), StaticClass.STATE_NOT_FINISH,
                                    task.getPriority(), task.getDayOfWeek(), task.getWeekOfYear());
                        }
                        //数据重新查询,更新数据
                        dataBaseTools.showDataView(mContext,mRecyclerView,page);
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

                dataBaseTools.showDataView(mContext,mRecyclerView,page);
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
                dataBaseTools.showDataView(mContext,mRecyclerView,page);
                bottomSheetDialog.dismiss();

            }
        });*/

      //  bottomSheetDialog.setContentView(viewDialog);
        //bottomSheetDialog.setOwnerActivity(getActivity());
       // bottomSheetDialog.show();

    }

    /*
    *  private void showBottomSheetDialog(final Task task) {
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
                        //数据重新查询,更新数据
                        DataBaseTools.showDataView(getActivity(),mRecyclerView,1);
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

                DataBaseTools.showDataView(getActivity(),mRecyclerView,1);
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
                DataBaseTools.showDataView(getActivity(),mRecyclerView,1);
                bottomSheetDialog.dismiss();

            }
        });

        bottomSheetDialog.setContentView(viewDialog);
        //bottomSheetDialog.setOwnerActivity(getActivity());
        bottomSheetDialog.show();

    }
    * */



