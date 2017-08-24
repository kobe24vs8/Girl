package com.example.administrator.girl.ui.base;

import com.example.administrator.girl.widget.MultiSwipeRefreshLayout;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.ui.base
 * 文件名:   SwipeRefreshLayer
 * 创建者:   LDW
 * 创建时间: 2017/8/11  10:29
 * 描述:    刷新时的数据接口
 */
public interface SwipeRefreshLayer {
    void requestDataRefresh();

    void setRefresh(boolean refresh);

    void setProgressViewOffset(boolean scale, int start, int end);

    void setCanChildScrollUpCallback(MultiSwipeRefreshLayout.CanChildScrollUpCallback callback);

}
