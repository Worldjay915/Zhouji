package com.shijie.pojo.zhouji.utils;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.utils
 * 创建者:  zsj
 * 创建事件: 2017/4/24 16:38
 * 描述:   数据、常量
 */

public class StaticClass {

    //闪屏延时
    public  static  final  int  HANDLER_SPLASH = 1001;
    //判断程序是否是第一次运行
    public  static  final  String  SHARE_IS_FIRST = "isFirst";
    //bomb appid
    public static final String BOMB_APPID = "027f0a62289c3882eb4ef5ca98470475";
    //任务完成状态
    public static final int STATE_FINISH = 1;
    //任务未完成状态
    public static final int STATE_NOT_FINISH = 0;

    //任务的等级 正常 一般 重要 紧急
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_COMMON = 1;
    public static final int PRIORITY_IMPORTANT = 2;
    public static final int PRIORITY_URGENT = 3;
    //布局方式
    public  static final  int  LINEARLAYOUT = 1;
    public  static final  int  GRIDLAYOUT = 2;
    //背景颜色
    public  static final  String BGCOLOR = "bgcolor";
    //布局排列
    public  static final  String LAYOUT = "layout";
    //当前第几周
    public static final String  CURRENT_WEEK= "current_week";
    //switch按选中状态
    public static final String  SWTCH_OPEN_WEEK= "open_week";
    public static final String  SWTCH_DAY_NIGHT= "day_night";
    public static final String  SWTCH_NITIFICATION= "notification";
    public static final String  IS_OPEN_NITIFICATION = "isOpen";
    //用户信息
    public  static  final  String  USERNAME = "username";
    public  static  final  String  DESC = "desc";
    //用户头像
    public static final String USER_IMAGE = "image_head";
    //bmob 备份数据库
    public static final String CLOUD_BACK = "cloud_back";
    //友盟统计
    public  static  final String YOUMENG_APPID = "592969a5ae1bf843c00010aa";
}
