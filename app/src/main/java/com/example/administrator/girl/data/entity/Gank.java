package com.example.administrator.girl.data.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.data.entity
 * 文件名:   Gank
 * 创建者:   LDW
 * 创建时间: 2017/8/10  16:09
 * 描述:    TODO
 */

//Table相当于表，解决类名与表名不对应
@Table("ganks")
public class Gank extends Soul {

    //数据中具体的属性

    //Column解决属性名与字段名不对应
    @Column("url") public String url;
    @Column("type") public String type;
    @Column("desc") public String desc;
    @Column("who") public String who;
    @Column("used") public boolean used;
    @Column("createdAt") public Date createdAt;
    @Column("updatedAt") public Date updatedAt;
    @Column("publishedAt") public Date publishedAt;

}
