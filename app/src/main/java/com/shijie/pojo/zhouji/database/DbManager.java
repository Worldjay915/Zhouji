package com.shijie.pojo.zhouji.database;

import android.database.sqlite.SQLiteDatabase;

import com.shijie.pojo.zhouji.application.BaseApplication;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.database
 * 创建者:  zsj
 * 创建事件: 2017/4/24 14:45
 * 描述:   实例化数据库(利用单例模式)
 */

public class DbManager {

    private static DbManager dbManager;
    private DBHelper helper;
    private SQLiteDatabase db;

    /**
     * 私有化构造器
     */
    private DbManager(){
        //创建数据库
        helper = DBHelper.getInstance(BaseApplication.getContext());
        if (db == null){
            db = helper.getWritableDatabase();
        }
    }

    /**
     * 单例 Dbmanager
     * @return
     */
    public static DbManager getDbManager(){
        if (dbManager ==null){
            dbManager = new DbManager();
        }
        return dbManager;
    }

    /**
     * 获取数据库的对象
     * @return
     */
    public SQLiteDatabase getDataBase(){
        return  db;
    }


}
