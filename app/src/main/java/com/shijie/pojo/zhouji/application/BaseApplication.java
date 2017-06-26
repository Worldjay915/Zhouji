package com.shijie.pojo.zhouji.application;

import android.app.Application;
import android.content.Context;

import com.shijie.pojo.zhouji.utils.StaticClass;
import com.umeng.analytics.MobclickAgent;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.application
 * 创建者:  zsj
 * 创建事件: 2017/4/24 13:33
 * 描述:
 */

public class BaseApplication extends Application {

    private static BaseApplication mApplication;

    /*
     * 获取Context
     * @return 返回Context的对象
     */
    public static Context getContext(){
        return mApplication.getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplication = this;

        //设置该app的主题根据时间不同显示
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        //打开SQLiteStudio
        SQLiteStudioService.instance().start(this);
        //Bmob  初始化
        Bmob.initialize(this, StaticClass.BOMB_APPID);
        //设置BmobConfig
        BmobConfig config =new BmobConfig.Builder(this)
                .setApplicationId(StaticClass.BOMB_APPID)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(500*1024)
                .build();
        Bmob.initialize(config);
        initYoumeng();

    }

    public void initYoumeng(){
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
    }


}
