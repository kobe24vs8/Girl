package com.example.administrator.girl;

import com.example.administrator.girl.data.DGankData;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl
 * 文件名:   DrakeetApi
 * 创建者:   LDW
 * 创建时间: 2017/8/10  16:47
 * 描述:    TODO
 */
public interface DrakeetApi {

    @Headers({ "X-LC-Id: 0azfScvBLCC9tAGRAwIhcC40", "X-LC-Key: gAuE93qAusvP8gk1VW8DtOUb", "Content-Type: application/json" })
    @GET("Gank?limit=1")
    Observable<DGankData> getDGankData(@Query("where") String where);

}
