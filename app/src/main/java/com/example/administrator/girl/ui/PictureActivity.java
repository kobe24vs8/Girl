package com.example.administrator.girl.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.girl.R;
import com.example.administrator.girl.ui.base.ToolbarActivity;
import com.example.administrator.girl.util.RxMezhi;
import com.example.administrator.girl.util.Share;
import com.example.administrator.girl.util.Toasts;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import uk.co.senab.photoview.PhotoViewAttacher;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui
 * 文件名:   PictureActivity
 * 创建者:   LDW
 * 创建时间: 2017/8/12  15:37
 * 描述:    TODO
 */
public class PictureActivity  extends ToolbarActivity{

    public static final String TRANSIT_PIC = "picture";
    public static final String PICTURE_DESC = "picture_desc";
    public static final String PICTURE_URL = "picture_url";

    PhotoViewAttacher mPhotoViewAttacher;


    String mImageDesc, mImageUrl;

    private Unbinder mBind;

    @BindView(R.id.picture)
    ImageView mImageView;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_picture;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保存引用，方便释放
        mBind = ButterKnife.bind(this);
        //注册Toasts，便于使用
        Toasts.register(getApplicationContext());
        //数据接收
        parseIntent();
        //过渡动画设置
        ViewCompat.setTransitionName(mImageView, TRANSIT_PIC);
        //加载图片
        Picasso.with(this).load(mImageUrl).into(mImageView);
        //设置图片的标题
        setTitle(mImageDesc);
        //设置透明度
        setAppBarAlpha(0.7f);
        //设置图片的缩放,点击保存等
        setupPhotoAttacher();
    }

    private void setupPhotoAttacher() {
        mPhotoViewAttacher = new PhotoViewAttacher(mImageView);
        //短点击隐藏标题栏
        mPhotoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                hideOrShowToolbar();
            }
        });
        //长点击保存图片
        mPhotoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(PictureActivity.this)
                        .setMessage("保存到手机?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveImageToGallery();
                                dialog.dismiss();
                            }
                        }).show();
                return true;
            }
        });

    }

    //图片的保存
    private void saveImageToGallery() {
        Subscription s = RxMezhi.saveImageAndGetPathObservable(this, mImageUrl, mImageDesc)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    File appDir = new File(Environment.getExternalStorageDirectory(), "111111");

//                    Log.d("TAG", appDir.getAbsolutePath());
                    String msg = String.format("图片保存到%s文件夹下", appDir.getAbsolutePath());
                    Toasts.showShort(msg);
 //                   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                }, error -> Toasts.showShort(error.getMessage() + "再试试...."));
        //取消订阅，防止内存泄漏
        addSubscription(s);
    }

    //MainActivity向PictureActivity传递desc和url数据
    public static Intent newIntent(Context context, String desc, String url) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(PictureActivity.PICTURE_DESC, desc);
        intent.putExtra(PictureActivity.PICTURE_URL, url);
//        Log.d("TAG", "desc..........." + desc);
//        Log.d("TAG", "url..........." + url);
        return intent;
    }

    //接收数据
    private void parseIntent() {
        mImageDesc = getIntent().getStringExtra(PICTURE_DESC);
        mImageUrl = getIntent().getStringExtra(PICTURE_URL);
    }

    //加载menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }

    //Item按钮的操作(分享和保存)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                RxMezhi.saveImageAndGetPathObservable(this, mImageUrl, mImageDesc)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(uri -> Share.shareImage(this, uri,
                                getString(R.string.share_meizhi_to)));
                return true;
            case R.id.action_save:
                saveImageToGallery();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPhotoViewAttacher.cleanup();
        //释放所有的引用
        mBind.unbind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
