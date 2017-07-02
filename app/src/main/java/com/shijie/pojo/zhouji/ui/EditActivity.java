package com.shijie.pojo.zhouji.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijie.pojo.zhouji.DAO.TaskDAO;
import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.adapter.PriorityAdapter;
import com.shijie.pojo.zhouji.database.ImageFactory;
import com.shijie.pojo.zhouji.utils.DataUtils;
import com.shijie.pojo.zhouji.utils.GetImageUtils;
import com.shijie.pojo.zhouji.utils.L;
import com.shijie.pojo.zhouji.utils.StaticClass;
import com.shijie.pojo.zhouji.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/25 16:00
 * 描述:  任务编辑界面
 */

public class EditActivity extends AppCompatActivity {


    @Bind(R.id.mToolBar)
    Toolbar mToolBar;
    @Bind(R.id.ctb)
    CollapsingToolbarLayout ctb;
    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.mTextInputTitle)
    TextInputLayout mTextInputTitle;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.mTextInputContent)
    TextInputLayout mTextInputContent;
    @Bind(R.id.tv_date)    //日期
    TextView tvDate;
    @Bind(R.id.iv_curr_priority)  //默认显示的级别
    ImageView ivCurrPriority;
    @Bind(R.id.ll_priority)
    LinearLayout llPriority;
    @Bind(R.id.recycler_choose_priority)
    RecyclerView recyclerChoosePriority;
    @Bind(R.id.ll_priority_all)
    LinearLayout llPriorityAll;   //全部显示的等级 LnearLayout
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.iv_bg)   //背景图片
    ImageView ivBg;
    @Bind(R.id.mAppBarLayout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.mNestedScrollView)
    NestedScrollView mNestedScrollView;
    @Bind(R.id.mCoordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    private PriorityAdapter adapter;
    private boolean flag;  //点击判断标签
    private String currentDate;  //当前时间
    private String randomImgName;  //随机的图片
    private int dayOfWeek;    //周几
    private int weekOfYear;    //第几周
    private int priority = StaticClass.PRIORITY_NORMAL;   //任务等级
    private int state = StaticClass.STATE_NOT_FINISH;   //默认未完成

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initView() {

        dayOfWeek = (int) getIntent().getSerializableExtra("dayofweek");
        L.i("---------------"+dayOfWeek);
        weekOfYear = DataUtils.getWeekOfYear();

        mToolBar.setTitle(R.string.startWrite);
        mToolBar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(mToolBar);

        UtilTools.setBgColor(this,mCoordinatorLayout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        currentDate = DataUtils.getCurrentDate();
        tvDate.setText(currentDate);

        ctb.setTitle(getString(R.string.startWrite));
        ctb.setExpandedTitleColor(Color.WHITE);
        ctb.setCollapsedTitleTextColor(Color.WHITE);

        recyclerChoosePriority.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        adapter = new PriorityAdapter(this);
        recyclerChoosePriority.setAdapter(adapter);
        recyclerChoosePriority.setItemAnimator(new DefaultItemAnimator());


    }

    private void initEvent() {

        //获取当前周几 第几周
//        if (DataUtils.getDayOfWeek() != 1) {
//            dayOfWeek = DataUtils.getDayOfWeek() - 1;
//        } else {
//            dayOfWeek = DataUtils.getDayOfWeek() + 6;
//        }
        //随机显示背景图片
        randomImgName = GetImageUtils.getRandomImgNameFromAssetsFile(this);
        L.i(randomImgName);
        ivBg.setImageBitmap(GetImageUtils.getImageFromAssetsFile(this, randomImgName));

        //toolbar 导航图标点击事件
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.VISIBLE);
            }
        });
        //默认flag时时true
        flag = true;
        //默认等级点击事件
        ivCurrPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.VISIBLE);
                if (flag) {
                    llPriorityAll.setVisibility(View.VISIBLE);
                    flag = false;
                } else {
                    llPriorityAll.setVisibility(View.GONE);
                    flag = true;
                }

            }
        });

        //priorityAll 点击事件
        adapter.setOnItemClickListener(new PriorityAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

                priority = position;

               // Toast.makeText(EditActivity.this, "你点击了" + position, Toast.LENGTH_SHORT).show();
                int[] priorityIcons = ImageFactory.createPriorityIcons();
                ivCurrPriority.setImageResource(priorityIcons[position]);
                llPriorityAll.setVisibility(View.GONE);
            }
        });

        //保存任务数据
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                //加入判断
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                    Snackbar.make(ctb, R.string.writeTitleAndContent, Snackbar.LENGTH_SHORT).show();
                } else {
                    TaskDAO taskDAO = new TaskDAO();
                    taskDAO.addTask(title, content, currentDate, randomImgName,
                            state, priority, dayOfWeek, weekOfYear);
                    //snackbar
                    Snackbar.make(ctb, R.string.saveSuccess, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
