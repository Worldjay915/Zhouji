package com.shijie.pojo.zhouji.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.utils
 * 创建者:  zsj
 * 创建事件: 2017/4/27 14:55
 * 描述: 从assets文件中获取图片
 */

public class GetImageUtils {

    public static final String AssetUrl = "file:///android_asset/";
    public static final String ImgFile = "img/";

    //随机获取assets文件夹中的图片
    public static String getRandomImgNameFromAssetsFile(Context mContext){
        String randomImgName = null;
        String [] imgName = null;
        try {
             imgName = mContext.getResources().getAssets().list("img");
             Random random = new Random();
             int countImg = random.nextInt(8);//获取0-8 之间的随机数 包括0，不包括8
             L.i(countImg+"");
             randomImgName = imgName[countImg];

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "img/"+randomImgName;
    }

    //将获取到的图片名称转成Bitmap
     public static Bitmap getImageFromAssetsFile(Context mContext,String imgName) {
         Bitmap bitmap = null;

         AssetManager assetManager = mContext.getResources().getAssets();
         try {
             InputStream in = assetManager.open(imgName);
             bitmap = BitmapFactory.decodeStream(in);
             in.close();
         } catch (IOException e) {
             e.printStackTrace();
         }

         return bitmap;
     }

}
