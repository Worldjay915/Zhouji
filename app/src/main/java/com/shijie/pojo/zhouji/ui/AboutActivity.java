package com.shijie.pojo.zhouji.ui;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.ui
 * 创建者:  zsj
 * 创建事件: 2017/5/2 10:06
 * 描述:
 */

public class AboutActivity extends BaseActivity {

    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.mRelativeLayout)
    RelativeLayout mRelativeLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        UtilTools.setBgColor(this,mRelativeLayout);
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
