package com.example.administrator.girl.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.girl.App;
import com.example.administrator.girl.GankApi;
import com.example.administrator.girl.R;
import com.example.administrator.girl.data.MeizhiData;
import com.example.administrator.girl.data.entity.Gank;
import com.example.administrator.girl.data.entity.Meizhi;
import com.example.administrator.girl.data.休息视频Data;
import com.example.administrator.girl.func.OnMeizhiTouchListener;
import com.example.administrator.girl.ui.adapter.MeizhiListAdapter;
import com.example.administrator.girl.ui.base.SwipeRefreshBaseActivity;
import com.example.administrator.girl.util.Dates;
import com.example.administrator.girl.util.Toasts;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends SwipeRefreshBaseActivity {

    private static final int PRELOAD_SIZE = 6;

    @BindView(R.id.list) RecyclerView mRecyclerView;

    private List<Meizhi> mMeizhiList;
    private MeizhiListAdapter mMeizhiListAdapter;

    //数据刷新的页数，每页10个数据
    private int mPage = 1;
    private boolean mIsFirstTimeTouchBottom = true;

    private boolean mMeizhiBeTouched;

    private Unbinder unbinder;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        Toasts.register(this);
        mMeizhiList = new ArrayList<>();
        //LiteOrm存储数据
        QueryBuilder query = new QueryBuilder(Meizhi.class);
        //当第一列相同时使用该列降序排序
        query.appendOrderDescBy("publishedAt");
        query.limit(0, 10);
        mMeizhiList.addAll(App.sDb.<Meizhi>query(query));

/*
        for (Meizhi m : mMeizhiList) {
            Log.d("TAG", m.url);
            Log.d("TAG", m.desc);
        }
*/
        setupRecyclerView();

    }

    //延迟时间，等待程序正式启动之后的一段时间再进行数据的加载
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loadData(true);
    }


    /**
     * 获取服务器的数据
     * @param b
     */
    private void loadData(final boolean b) {
        //每次进行数据解析的时候，必须进行清零
        mLastVideoIndex = 0;
        // zip: 将两个retrofit接口请求后得到的两个数据源Observable<MeizhiData>  Observable<休息视频Data>进行合并
        Observable<MeizhiData> meizhiDataObservable = sGankIO.getMeizhiData(mPage);
        Observable<休息视频Data> 休息视频DataObservable = sGankIO.get休息视频Data(mPage);
        Observable<MeizhiData> meizhiWith休息视频Observable =
                Observable.zip(meizhiDataObservable, 休息视频DataObservable, new Func2<MeizhiData, 休息视频Data, MeizhiData>() {
                    //把meizhi信息和休息视频信息整合成一个新的对象
                    @Override
                    public MeizhiData call(MeizhiData meizhiData, 休息视频Data 休息视频Data) {
                        for (Meizhi meizhi : meizhiData.results) {
                            meizhi.desc = meizhi.desc + " " + getFirstVideoDesc(meizhi.publishedAt, 休息视频Data.results);

//                            Log.d("TAG", getFirstVideoDesc(meizhi.publishedAt, 休息视频Data.results));
                        }
                        return meizhiData;
                    }
                });
        //对 MeizhiWith休息视频 对象进行排序
        /**
         *
         * 先把Observable<MeizhiData>数据源转化为Observable<List<Meizhi>>，从对外发一个MeizhiData对象变成对外发射一个List<Meizhi>对象；
         * 再把Observale<List<Meizhi>>转化为Observable<Meizhi>数据源，变成了对外发射出1个Meizhi对象;
         * 对这1个Meizhi对象基于publishDate进行排序；
         *
         */
        meizhiWith休息视频Observable.map(new Func1<MeizhiData, List<Meizhi>>() {
            @Override
            public List<Meizhi> call(MeizhiData meizhiData) {

                return meizhiData.results;
            }
        }).flatMap(new Func1<List<Meizhi>, Observable<Meizhi>>() {
            @Override
            public Observable<Meizhi> call(List<Meizhi> meizhis) {
                return Observable.from(meizhis);
            }
        }).toSortedList(new Func2<Meizhi, Meizhi, Integer>() {
            @Override
            public Integer call(Meizhi meizhi, Meizhi meizhi2) {
                return meizhi2.publishedAt.compareTo(meizhi.publishedAt);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .finallyDo(new Action0() {
                    @Override
                    public void call() {
                        setRefresh(false);
                    }
                })
                .subscribe(new Subscriber<List<Meizhi>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Meizhi> meizhis) {
                if (b) {
                    mMeizhiList.clear();
                }
/*
                for (Meizhi m : meizhis) {
                    Log.d("TAG", "Page：" + mPage);
                    Log.d("TAG", "desc：" + m.desc);
                    Log.d("TAG", "url：" + m.url);
                }
*/
                mMeizhiList.addAll(meizhis);
                //更新ui
                mMeizhiListAdapter.notifyDataSetChanged();
                //刷新数据
                setRefresh(false);
            }
        });
    }


    private int mLastVideoIndex = 0;

    private String getFirstVideoDesc(Date publishedAt, List<Gank> results) {
        String videoDesc = "";
        for (int i = mLastVideoIndex; i < results.size(); i++) {
            Gank video = results.get(i);
            if (video.publishedAt == null)
                video.publishedAt = video.createdAt;
            //判断时间是否相同
            if (Dates.isTheSameDay(publishedAt, video.publishedAt)){
                videoDesc = video.desc;
                mLastVideoIndex = i;
                break;
            }
            //Log.d("TAG", videoDesc);
        }

        return videoDesc;
    }


    private void setupRecyclerView() {
        //StaggeredGridLayoutManager第一个参数是布局列数，第二个参数是布局排列的方向
        StaggeredGridLayoutManager layoutManger = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManger);
        mMeizhiListAdapter = new MeizhiListAdapter(this, mMeizhiList);
        //完成适配器的关联
        mRecyclerView.setAdapter(mMeizhiListAdapter);

        mRecyclerView.addOnScrollListener(getBottomListener(layoutManger));
        mMeizhiListAdapter.setOnMeizhiTouchListener(getOnMeizhiTouchListener());
    }

    private OnMeizhiTouchListener getOnMeizhiTouchListener() {
        return (v, meizhiView, card, meizhi) -> {
            if (meizhi == null)
                return;
            //判断v是否meizhiView，是否点击
            if (v == meizhiView && ! mMeizhiBeTouched) {
                mMeizhiBeTouched = true;
                Picasso.with(this).load(meizhi.url).fetch(new Callback() {

                    @Override public void onSuccess() {
                        mMeizhiBeTouched = false;
                        startPictureActivity(meizhi, meizhiView);
                    }

                    @Override public void onError() {mMeizhiBeTouched = false;}
                });
            } else if (v == card) {
                startGankActivity(meizhi.publishedAt);
            }
        };
    }

    //视频信息的加载
    private void startGankActivity(Date publishedAt) {
        Intent intent = new Intent(this, GankActivity.class);
        //数据传递
        intent.putExtra(GankActivity.EXTRA_GANK_DATE, publishedAt);
        startActivity(intent);
    }

    //图片的加载
    private void startPictureActivity(Meizhi meizhi, View meizhiView) {

        Intent intent = PictureActivity.newIntent(MainActivity.this, meizhi.desc, meizhi.url);
        //转场动画（过渡动画）
        ActivityOptionsCompat optionCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this, meizhiView, PictureActivity.TRANSIT_PIC);
        try{
            ActivityCompat.startActivity(MainActivity.this, intent, optionCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(intent);
        }
/*
        Intent intent = new Intent(MainActivity.this, PictureActivity.class);
        intent.putExtra("title", meizhi.desc);
        intent.putExtra("url", meizhi.url);
        startActivity(intent);
        Log.d("TAG", "url..........." + meizhi.url);
        Log.d("TAG", "desc..........." + meizhi.desc);*/
    }


    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        mPage = 1;
        //加载数据
        loadData(true);
    }

    //监听是否到达底部，通进行加载下一页的数据
    RecyclerView.OnScrollListener getBottomListener(final StaggeredGridLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                boolean isBottom =
//                        layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
 //                               mMeizhiListAdapter.getItemCount() - PRELOAD_SIZE;
                //computeVerticalScrollExtent()是当前屏幕显示的区域高度
                // computeVerticalScrollOffset() 是当前屏幕之前滑过的距离，
                // computeVerticalScrollRange()是整个View控件的高度。
                boolean isBottom = recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                        >= recyclerView.computeVerticalScrollRange();

//                Log.d("TAG", "isBottom " + isBottom);
//                Log.d("TAG", "isbottom " + isbotton);
                if (isBottom) {
//                        mSwipeRefreshLayout.setRefreshing(true);
                        mPage += 1;
                        //加载新数据，必须把上一次mMeizhiList数据清空，否则出现数据覆盖
                        loadData(false);
                }

            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_trending:
                open_Github_trending();
                return true;
            case R.id.action_notifiable:
                Toasts.showLong("没有做这方面的工作!");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //GitHub热门
    private void open_Github_trending() {
        String url = "https://github.com/trending";
        String title = "今日 GitHub 热门";
        Intent intent = WebActivity.newIntent(this, url, title);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消所有控件绑定
        unbinder.unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
