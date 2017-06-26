package com.shijie.pojo.zhouji.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.database.ImageFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.utils
 * 创建者:  zsj
 * 创建事件: 2017/4/24 17:09
 * 描述:  工具统一类
 */

public class UtilTools {

    private static String[] bgColor;
    private static int bgColorPosition;

    //设置字体
    public  static void setFonts(Context mContext, TextView textView){
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(typeface);
    }

    //TextInputLayout 错误提醒
    public static void textChangedListenser(EditText mEditText, final TextInputLayout mTextInputLayout, final String text) {

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTextInputLayout.setErrorEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 mTextInputLayout.setErrorEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString().trim())) {
                    mTextInputLayout.setError(text);
                    mTextInputLayout.setErrorEnabled(true);
                } else {
                    mTextInputLayout.setErrorEnabled(false);
                }
            }
        });
    }
    //将图片保存到SharePreferences
    public static void  putImageToSharePre(Context mContext, ImageView imageView){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte byteArr[] = baos.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArr,Base64.DEFAULT));
        ShareUtils.putString(mContext,"image_head",imageString);
    }

    public static void  getImageFromSharePre(Context mContext,ImageView imageView){
        String  imageString = ShareUtils.getString(mContext,"image_head","");
        if (!imageString.equals("")){
            byte[] bytes = Base64.decode(imageString, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            Bitmap bitmap = BitmapFactory.decodeStream(bais);
            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setImageResource(R.mipmap.ic_zhou);
        }
    }
   //设置背景
    public static void  setBgColor(Context mContext, ViewGroup viewGroup ){
        bgColor = ImageFactory.createbackgroundStringColor();
        bgColorPosition = ShareUtils.getInt(mContext, StaticClass.BGCOLOR, 13);
        viewGroup.setBackgroundColor(Color.parseColor(bgColor[bgColorPosition]));
    }

}

