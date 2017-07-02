package com.shijie.pojo.zhouji.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.adapter.MyFragmentPagerAdapter;
import com.shijie.pojo.zhouji.entity.Task;
import com.shijie.pojo.zhouji.entity.User;
import com.shijie.pojo.zhouji.fragment.FriFragment;
import com.shijie.pojo.zhouji.fragment.MonFragment;
import com.shijie.pojo.zhouji.fragment.SatFragment;
import com.shijie.pojo.zhouji.fragment.SunFragment;
import com.shijie.pojo.zhouji.fragment.ThurFragment;
import com.shijie.pojo.zhouji.fragment.TuseFragment;
import com.shijie.pojo.zhouji.fragment.WedFragment;
import com.shijie.pojo.zhouji.service.NotificationService;
import com.shijie.pojo.zhouji.utils.SetFragmentTaskData;
import com.shijie.pojo.zhouji.utils.ShareUtils;
import com.shijie.pojo.zhouji.utils.StaticClass;
import com.shijie.pojo.zhouji.utils.UtilTools;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,SetFragmentTaskData {

    @Bind(R.id.mToolBar)
    Toolbar mToolBar;
    @Bind(R.id.mTabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.mViewPager)
    ViewPager mViewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    public   MyFragmentPagerAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNav;
    private ActionBarDrawerToggle toggle;
    private ArrayList<Fragment> mFragments;
    private CircleImageView circleImageView;
    private RelativeLayout mainRelativeLayout;
    private LinearLayout nav_header;
    private int dayOfWeek;   //按钮所处的位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_slide);
        ButterKnife.bind(this);

        initData();
        initView();
        initEvent();
    }

    //初始化数据
    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new MonFragment());
        mFragments.add(new TuseFragment());
        mFragments.add(new WedFragment());
        mFragments.add(new ThurFragment());
        mFragments.add(new FriFragment());
        mFragments.add(new SatFragment());
        mFragments.add(new SunFragment());
    }

    //初始化控件
    private void initView() {

        boolean openNoti = ShareUtils.getBoolean(this, StaticClass.SWTCH_NITIFICATION, true);
        if (openNoti == true){
            startService(new Intent(this,NotificationService.class));
        }
        //tooltbar
        mToolBar.setTitle(R.string.app_name);
        mToolBar.setSubtitle(R.string.writeTask);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //设置背景颜色
        mainRelativeLayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
        //侧滑菜单
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        toggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolBar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView
        mNav = (NavigationView) findViewById(R.id.mNav);
        mNav.setNavigationItemSelectedListener(this);
        //圆形头像
        circleImageView = (CircleImageView) mNav.getHeaderView(0).findViewById(R.id.profile_image);
        nav_header= (LinearLayout) mNav.getHeaderView(0).findViewById(R.id.nav_header);

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),this,mFragments);
        //预加载
        mViewPager.setOffscreenPageLimit(mFragments.size());
        //设置适配器
        mViewPager.setAdapter(adapter);
        //tablayout 绑定 viewpager
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                 dayOfWeek = position+1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        String imgStr = ShareUtils.getString(this, StaticClass.USER_IMAGE, "");
        if (!imgStr.equals("")){
            UtilTools.getImageFromSharePre(this,circleImageView);
        }else {
            circleImageView.setBackgroundResource(R.mipmap.ic_zhou);
        }
        //设置背景颜色
        UtilTools.setBgColor(this,mainRelativeLayout);
        UtilTools.setBgColor(this,mNav);
        UtilTools.setBgColor(this,nav_header);
        UtilTools.setBgColor(this,mToolBar);
        UtilTools.setBgColor(this,mTabLayout);
    }

    private void initEvent() {

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ModifyUserActivity.class));
            }
        });

        //在toolbar上添加menu菜单
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_setting:
                        startActivity(new Intent(MainActivity.this,SettingActivity.class));
                        break;
                }
                return true;
            }
        });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;
                float leftScale = 1 - 0.3f * scale;

                mMenu.setScaleX(leftScale);
                mMenu.setScaleY( leftScale);
                mMenu.setAlpha( 0.6f + 0.4f * (1 - scale));
                mContent.setTranslationX(mMenu.getMeasuredWidth() * (1 - scale));
                mContent.setPivotX(0);
                mContent.setPivotY(mContent.getMeasuredHeight() / 2);
                mContent.invalidate();
                mContent.setScaleX(rightScale);
                mContent.setScaleY(rightScale);
            }
            @Override
            public void onDrawerOpened(View drawerView) {}
            @Override
            public void onDrawerClosed(View drawerView) {}
            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }

    //编辑按钮点击事件
    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra("dayofweek",dayOfWeek);
        intent.setClass(MainActivity.this,EditActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*关闭SQLiteStudio
        SQLiteStudioService.instance().stop();*/
        stopService(new Intent(this,NotificationService.class));

   }
    //navigation 点击事件
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_setting:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
                break;
            case R.id.nav_exit:
                exit();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 退出登录
     */
    private void exit() {
        User.logOut();
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }

    //实现 setFragmentTaskData 接口
    @Override
    public void setTaskdata(Task mtask) {
         Intent intent = new Intent();
         intent.putExtra("task", mtask);
         intent.setClass(MainActivity.this,UpdateActivity.class);
         startActivity(intent);
    }

}
