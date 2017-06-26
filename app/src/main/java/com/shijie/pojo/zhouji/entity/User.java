package com.shijie.pojo.zhouji.entity;

import cn.bmob.v3.BmobUser;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.entity
 * 创建者:  zsj
 * 创建事件: 2017/4/25 11:09
 * 描述:
 */

public class User extends BmobUser {

    private boolean sex;
    private String  desc;

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
