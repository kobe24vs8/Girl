package com.example.administrator.girl.data;

import com.example.administrator.girl.data.BaseData;
import com.example.administrator.girl.data.entity.DGank;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.data.entity
 * 文件名:   DGankData
 * 创建者:   LDW
 * 创建时间: 2017/8/10  16:32
 * 描述:    TODO
 */
public class DGankData extends BaseData {

    @Expose
    public List<DGank> results = new ArrayList<DGank>();
}
