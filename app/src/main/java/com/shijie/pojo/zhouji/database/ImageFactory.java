package com.shijie.pojo.zhouji.database;

import com.shijie.pojo.zhouji.R;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.database
 * 创建者:  zsj
 * 创建事件: 2017/4/27 14:00
 * 描述:  图片存放 工厂
 */

public class ImageFactory {


    //背景图片
    private static final String[] imageNames = new String[]
            {"bg_autumn_tree-min.jpg", "bg_kites-min.png",
                    "bg_lake-min.jpg", "bg_leaves-min.jpg",
            "bg_magnolia_trees-min.jpg", "bg_solda-min.jpg",
                    "bg_tree-min.jpg", "bg_tulip-min.jpg"};

    //级别背景  日常 一般 重要 紧急
    private static final int[] priorityIcons = new int[]{
            R.drawable.ic_priority_1,
            R.drawable.ic_priority_2,
            R.drawable.ic_priority_3,
            R.drawable.ic_priority_4
    };
    //背景 颜色
    private static final int [] backgroundIntColor = new int[]{
            R.color.zhu_qing,
            R.color.chi_jin,
            R.color.qian_bai,
            R.color.ying_bai,
            R.color.su,
            R.color.yue_bai,
            R.color.shui_hong,
            R.color.cang_huang,
            R.color.ding_xiang_se,
            R.color.ai_lv,
            R.color.yu_se,
            R.color.huang_lu,
            R.color.jiang_huang
    };

//    <color name="zhu_qing">#789262</color>
//    <color name="chi_jin">#F2BE45</color>
//    <color name="qian_bai">#F0F0F4</color>
//    <color name="ying_bai">#E3F9FD</color>
//    <color name="su">#E0F0E9</color>
//    <color name="yue_bai">#D6ECF0</color>
//    <color name="shui_hong">#F3D3E7</color>
//    <color name="cang_huang">#519A73</color>
//    <color name="ding_xiang_se">#CCA4E3</color>
//    <color name="ai_lv">#A4E2C6</color>
//    <color name="yu_se">#7BCFA6</color>
//    <color name="huang_lu">#E29C45</color>
//    <color name="jiang_huang">#F0C239</color>

    //背景 颜色
    private static final String [] backgroundStringColor = new String[]{
            "#789262",
            "#F2BE45",
            "#F0F0F4",
            "#E3F9FD",
            "#E0F0E9",
            "#D6ECF0",
            "#F3D3E7",
            "#519A73",
            "#CCA4E3",
            "#A4E2C6",
            "#7BCFA6",
            "#E29C45",
            "#F0C239",
            "#F0F0F4"
    };


    //返回图片名称数组
    public static String[] createImgNames() {
        return imageNames;
    }

    //返回级别背景数组
    public static int[] createPriorityIcons() {
        return priorityIcons;
    }

    //返回级别背景颜色数组
    public static int[] createbackgroundIntColor() {
        return backgroundIntColor;
    }

    //返回级别背景颜色数组
    public static String[] createbackgroundStringColor() {
        return backgroundStringColor;
    }

}
