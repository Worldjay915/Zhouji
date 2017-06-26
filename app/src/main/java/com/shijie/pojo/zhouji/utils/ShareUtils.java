package com.shijie.pojo.zhouji.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.utils
 * 创建者:  zsj
 * 创建事件: 2017/4/10 22:44
 * 描述:  SharedPreferences方法封装
 */

public class ShareUtils {
    public static final String NAME = "Config";
    //键 值
    public static void putString(Context mContext,String key,String value){
       SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
       sp.edit().putString(key, value).commit();
    }
    //键 默认值
    public static String  getString(Context mContext,String key, String defvalue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
       return  sp.getString(key,defvalue);
    }

    //键 值
    public static void putInt(Context mContext,String key,int value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }
    //键 默认值
    public static int  getInt(Context mContext,String key, int defvalue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return  sp.getInt(key,defvalue);
    }

    //键 值
    public static void putBoolean(Context mContext,String key,boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
    //键 默认值
    public static boolean  getBoolean(Context mContext,String key, boolean defvalue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return  sp.getBoolean(key,defvalue);
    }
    //删除单个
    public static void deleteOne(Context mContext,String key){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
    //删除全部
    public static void deleteAll(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
