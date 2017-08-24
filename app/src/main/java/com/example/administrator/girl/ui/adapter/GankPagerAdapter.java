package com.example.administrator.girl.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.girl.DrakeetFactory;
import com.example.administrator.girl.ui.GankFragment;

import java.util.Calendar;
import java.util.Date;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui.adapter
 * 文件名:   GankPagerAdapter
 * 创建者:   LDW
 * 创建时间: 2017/8/23  9:53
 * 描述:    TODO
 */
public class GankPagerAdapter extends FragmentPagerAdapter {

    //FragmentPagerAdapter类内的每一个生成的 Fragment 都将保存在内存之中,一次取得10组数据保存到内存中

    Date mDate;

    public GankPagerAdapter(FragmentManager fm, Date date) {
        super(fm);
        mDate = date;
    }

    @Override
    public Fragment getItem(int position) {
        //初始化Calendar对象
        Calendar calendar = Calendar.getInstance();
        //对象指定到当前的日期
        calendar.setTime(mDate);
        //在Calendar对象的日期上减少position数值
        calendar.add(Calendar.DATE, -position);
        return GankFragment.newInstance(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public int getCount() {
        //每次取得的数据长度
        return DrakeetFactory.gankSize;
    }
}
