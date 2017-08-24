package com.example.administrator.girl.data;

import com.example.administrator.girl.data.entity.Gank;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.data
 * 文件名:   GankData
 * 创建者:   LDW
 * 创建时间: 2017/8/10  16:23
 * 描述:    TODO
 */
/*数据格式
{
        "category":["iOS","Android","瞎推荐","拓展资源","福利","休息视频"],
        "error":false,
        "results":{
                  "Android":[具体属性]
                  "iOS":[具体属性]
                  "休息视频":[具体属性]
                  "拓展资源":[具体属性]
                  "瞎推荐":[具体属性]
                  "福利":[具体属性]
                  }
}
*/
public class GankData extends BaseData {


    public Result results;
    public List<String> category;


    public class Result {
        @SerializedName("Android")
        public List<Gank> androidList;

        @SerializedName("iOS")
        public List<Gank> iOSList;

        @SerializedName("休息视频")
        public List<Gank> 休息视频List;

        @SerializedName("拓展资源")
        public List<Gank> 拓展资源List;

        @SerializedName("瞎推荐")
        public List<Gank> 瞎推荐List;

        @SerializedName("福利")
        public List<Gank> 妹纸List;

        @SerializedName("App")
        public List<Gank> appList;
    }
}
