package com.example.administrator.girl.util;

import android.content.Context;
import android.widget.Toast;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.util
 * 文件名:   Toasts
 * 创建者:   LDW
 * 创建时间: 2017/8/13  9:46
 * 描述:    TODO
 */
public class Toasts {

    private static Context mContext;

    public Toasts() {}

    public static void register(Context sContext) {
        mContext = sContext.getApplicationContext();
    }

    //判断Context是否为空
    private static void check() {
        if (mContext == null){
            throw new NullPointerException("Must initial call ToastUtils.register(Context context) in your" +
                    "<?" + "extends Application class>");
        }
    }

    //Toast的显示
    public static void showShort(int msg) {
        check();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String msg) {
        check();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int msg) {
        check();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        check();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongX2(int msg) {
        check();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongX2(String msg) {
        check();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongX3(int msg) {
        check();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongX3(String msg) {
        check();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }


}
