package com.example.administrator.girl.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.girl.R;
import com.example.administrator.girl.ui.adapter.GankPagerAdapter;
import com.example.administrator.girl.ui.base.ToolbarActivity;
import com.example.administrator.girl.util.Dates;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui
 * 文件名:   GankActivity
 * 创建者:   LDW
 * 创建时间: 2017/8/22  16:14
 * 描述:    TODO
 */
public class GankActivity extends ToolbarActivity implements ViewPager.OnPageChangeListener {

    public static final String EXTRA_GANK_DATE = "gank_date";

    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    GankPagerAdapter mPagerAdapter;
    Date mGankDate;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //获得MainActivity传递过来的时间数据
        mGankDate = (Date) getIntent().getSerializableExtra(EXTRA_GANK_DATE);
        setTitle(Dates.toDate(mGankDate));
        initViewPager();
        initTabLayout();
    }

    private void initViewPager() {
        mPagerAdapter = new GankPagerAdapter(getSupportFragmentManager(), mGankDate);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.addOnPageChangeListener(this);
    }


    private void initTabLayout() {
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTitle(Dates.toDate(mGankDate, -position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
