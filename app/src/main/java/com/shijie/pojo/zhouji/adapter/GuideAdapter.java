package com.shijie.pojo.zhouji.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.adapter
 * 创建者:  zsj
 * 创建事件: 2017/4/24 22:36
 * 描述:  viewPager 适配器
 */

public class GuideAdapter extends PagerAdapter{

    private Context mContext;
    private List<View> mList;

    public GuideAdapter(Context mContext,List<View> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

}
