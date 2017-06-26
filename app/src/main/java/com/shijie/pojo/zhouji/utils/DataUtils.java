package com.shijie.pojo.zhouji.utils;

import java.util.Calendar;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.utils
 * 创建者:  zsj
 * 创建事件: 2017/4/27 20:12
 * 描述:  获取时间 工具类
 */

public class DataUtils {

    //获取当前日期
    public static String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return  year+"/"+month+"/"+dayOfMonth  ;

    }
    //获取星期几  Sun. - Sat.  1 - 7
    public static int getDayOfWeek(){
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int day = dayOfWeek -1;
        if (day == 0){
            return 7;
        }
        return day;
    }

    //获取一年中的第几周
    public static int getWeekOfYear(){
        Calendar calendar = Calendar.getInstance();
        return  calendar.get(Calendar.WEEK_OF_YEAR);
    }

}
