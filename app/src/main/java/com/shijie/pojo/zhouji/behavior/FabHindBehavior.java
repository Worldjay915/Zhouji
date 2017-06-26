package com.shijie.pojo.zhouji.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.behavior
 * 创建者:  zsj
 * 创建事件: 2017/4/26 21:20
 * 描述:   自定义 floatingActionButton 的 behavior
 */

public class FabHindBehavior extends FloatingActionButton.Behavior {

    private static final String TAG = "FabHideShowBehaviour";

    public FabHindBehavior() {
    }

    public FabHindBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        Log.i(TAG, "onNestedScroll: " + dyConsumed + " : " + dyUnconsumed);

        if ((dyUnconsumed  < 0) && child.isShown()) child.show();
        else if ((dyConsumed == 0 || dyUnconsumed > 0) && !child.isShown()) child.show();

    }
}
