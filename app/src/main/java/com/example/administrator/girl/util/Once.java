package com.example.administrator.girl.util;

import android.content.Context;
import android.content.SharedPreferences;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.util
 * 文件名:   Once
 * 创建者:   LDW
 * 创建时间: 2017/8/13  16:45
 * 描述:    TODO
 */
public class Once {

    SharedPreferences mSharedPreferences;
    Context mContext;


    public Once(Context context) {
        mSharedPreferences = context.getSharedPreferences("once", Context.MODE_PRIVATE);
        mContext = context;
    }


    //数据的保存
    public void show(String tagKey, OnceCallback callback) {
        boolean isSecondTime = mSharedPreferences.getBoolean(tagKey, false);
        if (!isSecondTime) {
            callback.onOnce();
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(tagKey, true);
            editor.apply();
        }
    }


    public void show(int tagKeyResId, OnceCallback callback) {
        show(mContext.getString(tagKeyResId), callback);
    }


    public interface OnceCallback {
        void onOnce();
    }
}
