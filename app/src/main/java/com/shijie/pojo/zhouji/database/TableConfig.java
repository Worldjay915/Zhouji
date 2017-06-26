package com.shijie.pojo.zhouji.database;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.database
 * 创建者:  zsj
 * 创建事件: 2017/4/24 13:22
 * 描述:  存储一些数据库中数据表的表名，字段名等常量
 */

public class TableConfig {

    public static final String TABLE_TASK = "task";

    public static class Task{

        public static final String TASK_ID = "_id";
        public static final String TASK_TITLE = "title";
        public static final String TASK_CONTENT = "content";
        public static final String TASK_TIMESTAMP = "timeStamp";
        public static final String TASK_ICON = "icon";
        public static final String TASK_STATE = "state";
        public static final String TASK_PRIORITY = "priority";
        public static final String TASK_DAY_OF_WEEK = "dayOfWeek";
        public static final String TASK_WEEK_OF_YEAR = "weekOfYear";


    }

}
