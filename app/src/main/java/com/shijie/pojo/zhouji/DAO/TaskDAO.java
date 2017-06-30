package com.shijie.pojo.zhouji.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shijie.pojo.zhouji.database.DbManager;
import com.shijie.pojo.zhouji.database.TableConfig;
import com.shijie.pojo.zhouji.entity.Task;
import com.shijie.pojo.zhouji.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.DAO
 * 创建者:  zsj
 * 创建事件: 2017/4/24 16:00
 * 描述:  数据库DAO 操作 增删查改
 */

public class TaskDAO {
    private DbManager dbManager = null;
    private SQLiteDatabase  db = null;

    /**
     *  创建数据库
     */
    public TaskDAO(){

        dbManager = DbManager.getDbManager();
        db = dbManager.getDataBase();
    }


    /**
     * 添加任务
     */
    public void addTask(String title,String content,String timeStamp,String icon,
                        int state,int priority,int dayOfWeek,int weekOfYear){

        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("content",content);
        values.put("timeStamp",timeStamp);
        values.put("icon",icon);
        values.put("state",state);
        values.put("priority",priority);
        values.put("dayOfWeek",dayOfWeek);
        values.put("weekOfYear",weekOfYear);
        db.insert(TableConfig.TABLE_TASK,null,values);
        L.i("insert success");

    }

    /**
     * 更新任务
     */
    public void updateTask(int TaskId,String title,String content,String timeStamp,String icon,
                           int state,int priority,int dayOfWeek,int weekOfYear){

        ContentValues values = new ContentValues();
       // values.put("taskId",TaskId);
        values.put("title",title);
        values.put("content",content);
        values.put("timeStamp",timeStamp);
        values.put("icon",icon);
        values.put("state",state);
        values.put("priority",priority);
        values.put("dayOfWeek",dayOfWeek);
        values.put("weekOfYear",weekOfYear);
        db.update(TableConfig.TABLE_TASK,values,"_id = ?",new String[]{TaskId+""});
    }

    /**
     * 删除任务
     */
    public void deleteTask(int taskId){
        db.delete(TableConfig.TABLE_TASK," _id = ?",new String[]{taskId+""});
    }

    /**
     * 返回 第几周的第几天的任务
     */
    public List<Task> query(int DayOfWeek,int WeekOfYear){
        int i = 0;
        List<Task> taskList =new ArrayList<>();
        Cursor cursor = db.query(TableConfig.TABLE_TASK,null,"dayOfWeek=? and weekOfYear=?",new String[]{DayOfWeek+"",WeekOfYear+""}, null, null, null, null);
        while (cursor.moveToNext()){
            L.i("查询第"+i+1+"次");
            int  _id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String timeStamp = cursor.getString(3);
            String icon = cursor.getString(4);
            int state = cursor.getInt(5);
            int priority = cursor.getInt(6);
            int dayOfWeek = cursor.getInt(7);
            int weekOfYear = cursor.getInt(8);
            Task task = new Task(_id,title,content,timeStamp,icon,state,priority,dayOfWeek,weekOfYear);
            taskList.add(task);
            i++;
        }
        L.i("查询完毕,共查询"+i+"次");
        cursor.close();
        return taskList;
    }

    /**
     * 返回所有dayofweek的任务task
     * @param DayOfWeek
     * @return
     */
    public List<Task> query(int DayOfWeek){
        int i = 0;
        List<Task> taskList =new ArrayList<>();

        Cursor cursor = db.query(TableConfig.TABLE_TASK,null,"dayOfWeek=?",new String[]{DayOfWeek+""}, null, null, null, null);

        while (cursor.moveToNext()){
            L.i("查询第"+i+1+"次");
            int  _id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String timeStamp = cursor.getString(3);
            String icon = cursor.getString(4);
            int state = cursor.getInt(5);
            int priority = cursor.getInt(6);
            int dayOfWeek = cursor.getInt(7);
            int weekOfYear = cursor.getInt(8);
            Task task = new Task(_id,title,content,timeStamp,icon,state,priority,dayOfWeek,weekOfYear);
            taskList.add(task);
            i++;
        }
        L.i("查询完毕,共查询"+i+"次");
        cursor.close();
        return taskList;

    }

    public boolean deleteAll(){
        int delete = db.delete(TableConfig.TABLE_TASK, null, null);
        L.i("--------"+delete);
        if (delete > 0){
            return  true;
        }
        return false;
    }

}
