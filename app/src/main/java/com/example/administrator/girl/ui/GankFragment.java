package com.example.administrator.girl.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.girl.R;
import com.example.administrator.girl.data.GankData;
import com.example.administrator.girl.data.entity.Gank;
import com.example.administrator.girl.ui.adapter.GankListAdapter;
import com.example.administrator.girl.ui.base.BaseActivity;
import com.example.administrator.girl.util.Share;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui
 * 文件名:   GankFragment
 * 创建者:   LDW
 * 创建时间: 2017/8/23  9:51
 * 描述:    TODO
 */
public class GankFragment extends Fragment {

    private String TAG = "GankFragment";
    private static final String ARG_YEAR = "year";
    private static final String ARG_MONTH = "month";
    private static final String ARG_DAY = "day";

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    int mYear, mMonth, mDay;
    List<Gank> mGankList;
    GankListAdapter mAdapter;

    Subscription mSubscription;


    public static GankFragment newInstance(int year, int month, int day) {
        GankFragment fragment = new GankFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_YEAR, year);
        bundle.putInt(ARG_MONTH, month);
        bundle.putInt(ARG_DAY, day);
        fragment.setArguments(bundle);
        return fragment;
    }

    public GankFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGankList = new ArrayList<>();
        mAdapter = new GankListAdapter(mGankList);
        parseArguments();
        //对原来的Fragment进行中断保存
        setRetainInstance(true);
        //Fragment中的onCreateOptionsMenu生效必须先调用setHasOptionsMenu方法
        setHasOptionsMenu(true);
    }

    //获取时间
    private void parseArguments() {
        Bundle bundle = getArguments();
        mYear = bundle.getInt(ARG_YEAR);
        mMonth = bundle.getInt(ARG_MONTH);
        mDay = bundle.getInt(ARG_DAY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGankList.size() == 0)
            loadData();
    }

    private void loadData() {
        mSubscription = BaseActivity.sGankIO
                .getGankData(mYear, mMonth, mDay)
                .map(data -> data.results)
                .map(this::addAllResults)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    mAdapter.notifyDataSetChanged();
                }, Throwable::printStackTrace);
    }

    private List<Gank> addAllResults(GankData.Result results) {
        if (results.androidList != null) mGankList.addAll(results.androidList);
        if (results.iOSList != null) mGankList.addAll(results.iOSList);
        if (results.appList != null) mGankList.addAll(results.appList);
        if (results.拓展资源List != null) mGankList.addAll(results.拓展资源List);
        if (results.瞎推荐List != null) mGankList.addAll(results.瞎推荐List);
        if (results.休息视频List != null) mGankList.addAll(0, results.休息视频List);
        return mGankList;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (mGankList.size() != 0) {
                    Gank gank = mGankList.get(0);
                    String shareText = gank.desc + gank.url +
                            "http://fir.im/mengmeizhi";
                    Share.share(getActivity(), shareText);
                } else {
                    Share.share(getContext(), R.string.share_text);
                }
                return true;
            case R.id.action_subject:
                openTodaySubject();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openTodaySubject() {
        String url = "http://gank.io/" +
                String.format("%s/%s/%s", mYear, mMonth, mDay);
        Intent intent = WebActivity.newIntent(getActivity(), url,
                "今日肥皂家话题");
        startActivity(intent);
    }
}
