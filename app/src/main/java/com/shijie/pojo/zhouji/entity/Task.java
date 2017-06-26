package com.shijie.pojo.zhouji.entity;

import java.io.Serializable;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.entity
 * 创建者:  zsj
 * 创建事件: 2017/4/24 11:39
 * 描述:  任务实体bean
 */

public class Task implements Serializable{

    private  int   _id;
    //标题
    private String title;
    //内容
    private String content;
    //时间戳
    private String timeStamp;
    //图片
    private String icon;
    //已完成状态
    private int  state;
    //任务优先级
    private int priority;
    //一周中的哪一天
    private int dayOfWeek;
    //第几周
    private int weekOfYear;

    public Task() {
    }

    public Task(String title, String content, String timeStamp,
                String icon, int state, int priority, int dayOfWeek,
                int weekOfYear) {
        this.title = title;
        this.content = content;
        this.timeStamp = timeStamp;
        this.icon = icon;
        this.state = state;
        this.priority = priority;
        this.dayOfWeek = dayOfWeek;
        this.weekOfYear = weekOfYear;
    }

    public Task(int _id, String title, String content, String timeStamp,
                String icon, int state, int priority, int dayOfWeek,
                int weekOfYear) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.timeStamp = timeStamp;
        this.icon = icon;
        this.state = state;
        this.priority = priority;
        this.dayOfWeek = dayOfWeek;
        this.weekOfYear = weekOfYear;
    }

    public Task(int dayOfWeek, int weekOfYear) {
        this.dayOfWeek = dayOfWeek;
        this.weekOfYear = weekOfYear;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", timeStamp=" + timeStamp +
                ", icon='" + icon + '\'' +
                ", state=" + state +
                ", priority=" + priority +
                ", dayOfWeek=" + dayOfWeek +
                ", weekOfYear=" + weekOfYear +
                '}';
    }
}
