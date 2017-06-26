package com.shijie.pojo.zhouji.utils;

import android.util.Log;

/**
 * 项目名:
 * 包名: com.example.pojo.smartapp.utils
 * 创建者:  zsj
 * 创建事件: 2017/4/10 22:15
 * 描述:  日志工具类
 */

public class L {

    public static final boolean DEBUG = true;
    public static final String  TAG = "zhoujiji";

    // 五个等级 debug info warning error  fatal

    public static void d(String text){
        if (DEBUG){
            Log.d(TAG,text);
        }

    }

    public static void i(String text){
        if (DEBUG){
            Log.d(TAG,text);
        }

    }

    public static void w(String text){
        if (DEBUG){
            Log.d(TAG,text);
        }

    }
    public static void e(String text){
        if (DEBUG){
            Log.d(TAG,text);
        }

    }





}