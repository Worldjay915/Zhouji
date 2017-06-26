package com.shijie.pojo.zhouji.entity;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.entity
 * 创建者:  zsj
 * 创建事件: 2017/4/27 16:09
 * 描述:  Priority级别 bean
 */

public class PriorityItem {

    private int ResId;
    private String priorityName;

    public PriorityItem(int resId, String priorityName) {
        ResId = resId;
        this.priorityName = priorityName;
    }

    public int getResId() {
        return ResId;
    }

    public void setResId(int resId) {
        ResId = resId;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }
}
