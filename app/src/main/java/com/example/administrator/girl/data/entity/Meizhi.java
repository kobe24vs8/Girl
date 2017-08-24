package com.example.administrator.girl.data.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.data.entity
 * 文件名:   Meizhi
 * 创建者:   LDW
 * 创建时间: 2017/8/10  16:16
 * 描述:    TODO
 */
@Table("meizhis")
public class Meizhi extends Soul{

    @Column("url") public String url;
    @Column("type") public String type;
    @Column("desc") public String desc;
    @Column("who") public String who;
    @Column("used") public boolean used;
    @Column("createdAt") public Date createdAt;
    @Column("updatedAt") public Date updatedAt;
    @Column("publishedAt") public Date publishedAt;
    //图片的宽高
    @Column("imageWidth") public int imageWidth;
    @Column("imageHeight") public int imageHeight;
}
