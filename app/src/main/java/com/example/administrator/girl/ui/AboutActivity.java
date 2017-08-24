package com.example.administrator.girl.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.girl.BuildConfig;
import com.example.administrator.girl.R;
import com.example.administrator.girl.ui.base.BaseActivity;
import com.example.administrator.girl.ui.base.ToolbarActivity;
import com.example.administrator.girl.util.Share;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui
 * 文件名:   AboutActivity
 * 创建者:   LDW
 * 创建时间: 2017/8/13  12:25
 * 描述:    TODO
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.version)
    TextView mVerdion;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout mCollapseToolbar;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        unbinder = ButterKnife.bind(this);
        //设置版本号
        setupVersionName();
        //设置标题名
        mCollapseToolbar.setTitle("Girl");
        setSupportActionBar(mToolBar);
        //显示返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.onBackPressed();
            }
        });
    }

    private void setupVersionName() {
        mVerdion.setText("Version " + BuildConfig.VERSION_NAME);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                Share.share(this, R.string.share_text);
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
