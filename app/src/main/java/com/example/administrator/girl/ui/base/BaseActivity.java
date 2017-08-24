package com.example.administrator.girl.ui.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.girl.DrakeetFactory;
import com.example.administrator.girl.GankApi;
import com.example.administrator.girl.R;
import com.example.administrator.girl.ui.AboutActivity;
import com.example.administrator.girl.ui.WebActivity;
import com.example.administrator.girl.util.Once;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui.base
 * 文件名:   BaseActivity
 * 创建者:   LDW
 * 创建时间: 2017/8/11  10:12
 * 描述:    BaseActivity主要封装了网络请求sGankIO,以及在activity onDestroy时取消当前网络的请求。
 */
public class BaseActivity extends AppCompatActivity{
    //获取GankAPI的单例
    public static final GankApi sGankIO = DrakeetFactory.getGankIOSingleton();

    //为了防止可能的内存泄露，在你的 Activity 或 Fragment 的 onDestroy 里，用 Subscription.isUnsubscribed()
    // 检查你的 Subscription 是否是 unsubscribed。如果调用了 Subscription.unsubscribe() ，
    // Unsubscribing将会对 items 停止通知给你的 Subscriber，并允许垃圾回收机制释放对象，防止任何 RxJava 造成内存泄露。
    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getmCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        return mCompositeSubscription;
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    //销毁的时候取消订阅
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
                LoginGitHub();
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoginGitHub() {
        new Once(this).show("登录GitHub帐号", () -> {
            Toast.makeText(this, "妹纸 App 并无帐号系统，这个菜单只是提前登录保留 cookies，以便于之后进 GitHub 页面 star 项目", Toast.LENGTH_LONG).show();
        });
        String url = "https://github.com/login";
        Intent intent = WebActivity.newIntent(BaseActivity.this, url,
                        "登录GitHub帐号");
        startActivity(intent);
    }

}
