package com.example.administrator.girl;

import android.app.Application;
import android.content.Context;

import com.litesuits.orm.LiteOrm;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl
 * 文件名:   App
 * 创建者:   LDW
 * 创建时间: 2017/8/11  10:50
 * 描述:    创建数据库，用于数据的保存
 */
public class App extends Application {
    //数据库名
    private final static String DB_NAME = "gank.db";
    public static Context mContext;
    public static LiteOrm sDb;

    //  解释一下创建数据库Cascade 和 Single 两种模式的的区别：
    //LiteOrm是基类，是基础。有两个孩子实现：Cascade 和 Single。
    //如果使用Single，效率最高，只保存当前Model，简单的首选。
    //如果使用Cascade实例，将会无限级联操作（不会死循环），将所有与这个Model相关的实体、关系都保存下来。
    // 重点介绍下Cascade，举个例子，一个学校有10个学院（一对多），一个学院10个专业，一个专业10个班级，
    // 一个班级10个老师，一个老师10个学生（多对多关系）。只要调用cascade.save( school ); 那么这个学校，
    // 以及10 0000个学生 ，和他们的关系，都保存下来了。 删除也一样，都删了
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        sDb = LiteOrm.newSingleInstance(mContext, DB_NAME);
        if (BuildConfig.DEBUG) {
            sDb.setDebugged(true);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
