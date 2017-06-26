package com.shijie.pojo.zhouji.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.utils.L;
import com.shijie.pojo.zhouji.utils.ShareUtils;
import com.shijie.pojo.zhouji.utils.StaticClass;
import com.shijie.pojo.zhouji.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/24 16:54
 * 描述:   闪屏页
 */

public class SplashActivity extends AppCompatActivity {


    @Bind(R.id.tv_splash)
    TextView tvSplash;
    @Bind(R.id.tv_splash1)
    TextView tvSplash1;
    @Bind(R.id.iv_zhouji)
    ImageView ivZhouji;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        String username = ShareUtils.getString(SplashActivity.this, "username", "");
                        L.i("username--------"+username);
                        if (username.equals("")){
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }else {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //发送handler
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 1800);
        UtilTools.setFonts(this, tvSplash);
        UtilTools.setFonts(this, tvSplash1);
        //动画
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.splash_rotate);
        ivZhouji.startAnimation(animation1);
    }


    //判断是否是第一次登陆
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst) {
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }

    }

    //禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

}
