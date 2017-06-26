package com.shijie.pojo.zhouji.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.database
 * 创建者:  zsj
 * 创建事件: 2017/4/24 13:38
 * 描述:    创建数据库
 */

public class DBHelper extends SQLiteOpenHelper{

    private  static  DBHelper helper;

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //为了简化构造器的使用，我们自定义一个构造器 ，私有化
    private DBHelper(Context context,String name){
        this(context,name,null,1); //传入Context和数据库的名称，调用上面那个构造器
    }

    //将自定义的数据库创建单例
    public static synchronized DBHelper getInstance(Context context){
        if (helper ==null){
            helper = new DBHelper(context,"task");
        }
        return helper;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table if not exists "+TableConfig.TABLE_TASK+"(" +
                TableConfig.Task.TASK_ID+" integer not null primary key autoincrement," +
                TableConfig.Task.TASK_TITLE+" varchar(20)," +
                TableConfig.Task.TASK_CONTENT+" varchar(50)," +
                TableConfig.Task.TASK_TIMESTAMP+" integer," +
                TableConfig.Task.TASK_ICON+" varchar(20)," +
                TableConfig.Task.TASK_STATE+" integer," +
                TableConfig.Task.TASK_PRIORITY+" integer," +
                TableConfig.Task.TASK_DAY_OF_WEEK+" integer," +
                TableConfig.Task.TASK_WEEK_OF_YEAR+" integer );";

        sqLiteDatabase.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
