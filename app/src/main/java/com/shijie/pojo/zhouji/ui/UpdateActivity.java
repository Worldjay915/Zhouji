package com.shijie.pojo.zhouji.ui;

import android.graphics.Color;
import android.os.Bundle;
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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijie.pojo.zhouji.DAO.TaskDAO;
import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.adapter.PriorityAdapter;
import com.shijie.pojo.zhouji.database.ImageFactory;
import com.shijie.pojo.zhouji.entity.Task;
import com.shijie.pojo.zhouji.utils.GetImageUtils;
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

public class UpdateActivity extends AppCompatActivity {


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
    @Bind(R.id.iv_bg)
    ImageView ivBg;
    @Bind(R.id.mNestedScrollView)
    NestedScrollView mNestedScrollView;
    @Bind(R.id.mCoordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;

    private PriorityAdapter adapter;
    private boolean flag;  //点击判断标签
    private Task mtask;
    private int priority;
    private String newTitle;
    private String newContent;
    private int taskId = -1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initView() {

        //设置toolbar
        mToolBar.setTitle(R.string.seeOrModify);
        mToolBar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        UtilTools.setBgColor(this,mCoordinatorLayout);

        ctb.setTitle(getString(R.string.seeOrModify));
        ctb.setExpandedTitleColor(Color.WHITE);
        ctb.setCollapsedTitleTextColor(Color.WHITE);

        recyclerChoosePriority.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        adapter = new PriorityAdapter(this);
        recyclerChoosePriority.setAdapter(adapter);
        recyclerChoosePriority.setItemAnimator(new DefaultItemAnimator());

    }

    private void initEvent() {

        //获取intent中的数据
        mtask = (Task) getIntent().getSerializableExtra("task");

        int[] priorityIcons = ImageFactory.createPriorityIcons();
        taskId = mtask.get_id();
        //显示背景图片
        ivBg.setImageBitmap(GetImageUtils.getImageFromAssetsFile(this, mtask.getIcon()));
        //数据初始化
        etTitle.setText(mtask.getTitle());
        etContent.setText(mtask.getContent());
        ivCurrPriority.setImageResource(priorityIcons[mtask.getPriority()]);
        tvDate.setText(mtask.getTimeStamp());

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
                //Toast.makeText(EditActivity.this, "你点击了"+position, Toast.LENGTH_SHORT).show();
                int[] priorityIcons = ImageFactory.createPriorityIcons();
                ivCurrPriority.setImageResource(priorityIcons[position]);
                llPriorityAll.setVisibility(View.GONE);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDAO taskDAO = new TaskDAO();
                newTitle = etTitle.getText().toString().trim();
                newContent = etContent.getText().toString().trim();
                taskDAO.updateTask(taskId, newTitle, newContent, mtask.getTimeStamp(), mtask.getIcon(), priority,
                        mtask.getState(), mtask.getDayOfWeek(), mtask.getWeekOfYear());

                //snackbar
                Snackbar.make(ctb, R.string.updateSuccess, Snackbar.LENGTH_SHORT).setActionTextColor(Color.GREEN).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
