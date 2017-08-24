package com.example.administrator.girl;

import com.example.administrator.girl.data.GankData;
import com.example.administrator.girl.data.MeizhiData;
import com.example.administrator.girl.data.休息视频Data;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Observer;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.widget
 * 文件名:   GankApi
 * 创建者:   LDW
 * 创建时间: 2017/8/10  16:58
 * 描述:    TODO
 */
public interface GankApi {

    //每日数据
    //     http://gank.io/api/day/2015/08/06

    //每日数据的具体内容
    //     http://gank.io/api/data/福利/10/1
    //     http://gank.io/api/data/休息视频/10/1


    @GET("data/福利/" + DrakeetFactory.meizhiSize + "/{page}")
    Observable<MeizhiData> getMeizhiData(@Path("page") int page);

    @GET("data/休息视频/" + DrakeetFactory.gankSize + "/{page}")
    Observable<休息视频Data> get休息视频Data(@Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

}
