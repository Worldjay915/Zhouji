package com.shijie.pojo.zhouji.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.utils
 * 创建者:  zsj
 * 创建事件: 2017/4/14 10:48
 * 描述:   Picasso 的 封装
 */

public class PicassoUtils {

    public static void loadImageView(Context mContext, String url, ImageView mImageView){
        Picasso.with(mContext).load(url).config(Bitmap.Config.RGB_565).into(mImageView);
    }

    public static void loadImageViewBySize(Context mContext, String url,int width,int height, ImageView mImageView){
        Picasso.with(mContext).load(url).resize(width, height).centerCrop().into(mImageView);

    }

    public static void loadImageViewByDefault(Context mContext, String url,int loadImg,int errorImg, ImageView mImageView){

        Picasso.with(mContext).load(url).placeholder(loadImg).error(errorImg).into(mImageView);
    }

    public  static void loadImageViewByCut(Context mContext, String url, ImageView mImageView){
        Picasso.with(mContext).load(url).transform(new CropSquareTransformation()).into(mImageView);
    }

    public static class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "zsj"; }
    }


}
