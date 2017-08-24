package com.example.administrator.girl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl
 * 文件名:   DrakeetRetrofit
 * 创建者:   LDW
 * 创建时间: 2017/8/10  17:21
 * 描述:    TODO
 */
public class DrakeetRetrofit {
    final GankApi gankService;
    final DrakeetApi drakeetService;

    //Gson处理Date
    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    DrakeetRetrofit() {

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://gank.io/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Retrofit gankRest = builder.build();
        builder.baseUrl("https://leancloud.cn:443/1.1/classes/");
        Retrofit drakeetRest = builder.build();
        gankService = gankRest.create(GankApi.class);
        drakeetService = drakeetRest.create(DrakeetApi.class);
    }

    public GankApi getGankService() {
        return gankService;
    }

    public DrakeetApi getDrakeetService() {
        return drakeetService;
    }
}
