package com.shijie.pojo.zhouji.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.adapter
 * 创建者:  zsj
 * 创建事件: 2017/4/25 15:18
 * 描述:   viewpager适配器
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String [] titles = new String[]{"周一","周二","周三","周四","周五","周六","周日"};
    private List<Fragment> mList;
    private FragmentManager fm;

    public MyFragmentPagerAdapter(FragmentManager fm, Context mContext, List<Fragment> mList) {
        super(fm);
        this.fm = fm;
        this.mContext = mContext;
        this.mList = mList;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        //L.i(position+"--------"+"position");
       // monFragment = new MonFragment();
        return mList.get(position);
    }

    @Override
    public int getCount() {
       // L.i(titles.length+"getCount()");
        return mList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
