package com.example.administrator.girl.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.administrator.girl.R;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui.base
 * 文件名:   ToolbarActivity
 * 创建者:   LDW
 * 创建时间: 2017/8/11  10:20
 * 描述:    设置了当前Activity的actionbar,可以给子类覆盖重写是否可以显示返回上一个Activity按钮，
 *          有显示隐藏AppBarLayout方法
 */
public abstract class ToolbarActivity extends BaseActivity {

    public AppBarLayout mAppBar;
    public Toolbar mToolBar;

    //判断是否需要隐藏ToolBar
    private boolean mIsHidden = false;

    abstract protected int provideContentViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
        mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        if (mAppBar == null || mToolBar == null) {
            throw new IllegalStateException(
                    "The subclass of ToolbarActivity must contain a toolbar.");
        }
        mToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //Toolbar代替来的ActionBar
        setSupportActionBar(mToolBar);
        if (canBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                //显示返回的箭头，并可通过onOptionsItemSelected()进行监听，其资源ID为android.R.id.home
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    public boolean canBack() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //返回键销毁程序
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    //设置透明度
    public void setAppBarAlpha(float alpha) {
        mAppBar.setAlpha(alpha);
    }

    public void hideOrShowToolbar() {
        mAppBar.animate()
                .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden = !mIsHidden;
    }
}
