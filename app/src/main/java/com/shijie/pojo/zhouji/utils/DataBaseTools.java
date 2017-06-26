package com.shijie.pojo.zhouji.utils;

import android.content.Context;

import com.shijie.pojo.zhouji.DAO.TaskDAO;
import com.shijie.pojo.zhouji.entity.Task;

import java.util.List;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.utils
 * 创建者:  zsj
 * 创建事件: 2017/5/2 16:10
 * 描述: 数据库查询数据显示到 RecyclerView
 */

public class DataBaseTools {

    private  TaskDAO taskDAO;
    private  List<Task> taskList;

    public  List<Task> showDataView(Context mContext,int dayOfWeek){

        //获得当前周
        int currentWeek = ShareUtils.getInt(mContext, StaticClass.CURRENT_WEEK, 0);
        //数据库查询
        taskDAO = new TaskDAO();
        if (currentWeek == 0 ){
            taskList = taskDAO.query(dayOfWeek);
        }else {
            taskList = taskDAO.query(dayOfWeek,currentWeek);
        }
        return taskList;
    }

}
