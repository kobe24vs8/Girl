package com.example.administrator.girl.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.administrator.girl.R;
import com.example.administrator.girl.ui.base.BaseActivity;
import com.example.administrator.girl.ui.base.ToolbarActivity;
import com.example.administrator.girl.util.Androids;
import com.example.administrator.girl.util.Toasts;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui
 * 文件名:   WebActivity
 * 创建者:   LDW
 * 创建时间: 2017/8/13  15:43
 * 描述:    TODO
 */
public class WebActivity extends ToolbarActivity{

    //用于活动之间的数据传递的键值
    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_TITLE = "extra_title";


    private String mUrl, mTitle;

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.text_switcher)
    TextSwitcher mTextSwitcher;

    private Unbinder unbinder;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
    }

    //ActionBar的返回键显示
    @Override
    public boolean canBack() {
        return true;
    }

    //活动进程之间传递数据
    public static Intent newIntent(Context mContext, String extraUrl, String extraTitle) {
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra(WebActivity.EXTRA_URL, extraUrl);
        intent.putExtra(WebActivity.EXTRA_TITLE, extraTitle);
        return intent;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        //Toasts注册
        Toasts.register(this);
        //接收数据
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);

//        Log.d("TAG", "URL............" + mUrl);
//        Log.d("TAG", "TITLE.........." + mTitle);

        //设置浏览器的格式
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(true);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new LoveClient());

        mWebView.loadUrl(mUrl);

        //显示返回按钮
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置TextSwicher
        mTextSwitcher.setFactory(() ->{
            TextView textView = new TextView(this);
            textView.setTextAppearance(R.style.WebTitle);
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.postDelayed(() -> textView.setSelected(true), 1738);
            return textView;
        });

        mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        if (mTitle != null) setTitle(mTitle);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTextSwitcher.setText(title);
    }

    //当按下BACK键时，会被onKeyDown捕获，判断是BACK键，则执行exit方法。
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mWebView.reload();
                return true;
            case R.id.action_copy_url:
                Androids.copyToClipBoard(this, mWebView.getUrl(), "复制成功");
                return true;
            case R.id.action_open_url:
                //在浏览器里打开链接
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(mUrl);
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toasts.showLong("打开失败，没有找到可以打开该链接的其它应用!");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }


    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    private class LoveClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null)
            mWebView.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null)
            mWebView.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null)
            mWebView.destroy();
        unbinder.unbind();
    }

}
