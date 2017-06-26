package com.shijie.pojo.zhouji.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.adapter.GuideAdapter;
import com.shijie.pojo.zhouji.utils.L;
import com.shijie.pojo.zhouji.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/24 17:16
 * 描述:  引导页
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.mViewPager)
    ViewPager mViewPager;
    @Bind(R.id.point1)
    ImageView point1;
    @Bind(R.id.point2)
    ImageView point2;
    @Bind(R.id.point3)
    ImageView point3;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    //放View的容器
    private List<View> mList = new ArrayList<>();
    private View view1,view2,view3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {

        setPointImg(true,false,false);

        view1 = View.inflate(this,R.layout.pager_item_one,null);
        view2 = View.inflate(this,R.layout.pager_item_two,null);
        view3 = View.inflate(this,R.layout.pager_item_three,null);
        TextView pageOne= (TextView) view1.findViewById(R.id.page_one);
        TextView pageTwo= (TextView) view2.findViewById(R.id.page_two);
        TextView pageThree= (TextView) view3.findViewById(R.id.page_three);
        UtilTools.setFonts(this,pageOne);
        UtilTools.setFonts(this,pageTwo);
        UtilTools.setFonts(this,pageThree);

        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        ivBack.setOnClickListener(this);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //为viewpager设置适配器
        mViewPager.setAdapter(new GuideAdapter(this,mList));
        //viewPager 设置划定事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //选中点击事件
            @Override
            public void onPageSelected(int position) {
                L.i("position"+position);
                switch (position){
                    case  0:
                        setPointImg(true,false,false);
                        ivBack.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false,true,false);
                        ivBack.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false,false,true);
                        ivBack.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_start:
            case R.id.iv_back:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    //设置小圆点是否选中
    public void setPointImg(boolean isChecked1,boolean isChecked2,boolean isChecked3) {
        if (isChecked1) {
            point1.setBackgroundResource(R.drawable.point_on);
        } else {
            point1.setBackgroundResource(R.drawable.point_off);
        }
        if (isChecked2) {
            point2.setBackgroundResource(R.drawable.point_on);
        } else {
            point2.setBackgroundResource(R.drawable.point_off);
        }
        if (isChecked3) {
            point3.setBackgroundResource(R.drawable.point_on);
        } else {
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }
}
